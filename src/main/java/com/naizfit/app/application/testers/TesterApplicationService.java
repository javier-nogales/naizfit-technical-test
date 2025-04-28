package com.naizfit.app.application.testers;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;

import com.google.inject.Inject;
import com.naizfit.app.application.testers.command.CreateTesterCommand;
import com.naizfit.app.application.testers.command.UpdatePasswordCommand;
import com.naizfit.app.application.testers.command.UpdateTesterCommand;
import com.naizfit.app.application.testers.dto.TesterDto;
import com.naizfit.app.domain.DomainEventPublisher;
import com.naizfit.app.domain.shared.exception.NotFoundException;
import com.naizfit.app.domain.testers.Tester;
import com.naizfit.app.domain.testers.TesterRepository;
import com.naizfit.app.domain.testers.event.TesterDeleted;
import com.naizfit.app.domain.testers.event.TesterPasswordChanged;
import com.naizfit.app.domain.testers.event.TesterRegistered;
import com.naizfit.app.domain.testers.event.TesterUpdated;
import com.naizfit.app.domain.testers.vo.Password;
import com.naizfit.app.domain.testers.vo.TesterId;

public class TesterApplicationService {

	// ───── SERVICES ────────────────────────────────────────────────────────
	private final DomainEventPublisher eventPublisher;
	private final TesterRepository testerRepository;
	
	// ───── CONSTRUCTOR ─────────────────────────────────────────────────────
	@Inject
	public TesterApplicationService(final DomainEventPublisher eventPublisher,
									final TesterRepository testerRepository) {
		
		this.eventPublisher = eventPublisher;
		this.testerRepository = testerRepository;
	}
	
	// ───── SERVICE METHODS ─────────────────────────────────────────────────
	/**
	 * Create new Tester
	 * @param cmd
	 * @return
	 */
	public TesterId createTester(final CreateTesterCommand cmd) {
		// encrypt 
		Password password = encryptPassword(cmd.rawPassword());
		
		// create
		Tester tester = Tester.create(cmd.name(),
									  cmd.email(),
									  password,
									  cmd.birthdate(),
									  cmd.sex());
		
		// TODO: verify if email exists 
		
		// save
		testerRepository.save(tester);
		
		// publish
		eventPublisher.publish(new TesterRegistered(tester.getId(),
													tester.getEmail(),
													tester.getName(),
													Instant.now()));
		
		return tester.getId();
		
	}
	/**
	 * Load a Tester by id
	 * @param id
	 * @return
	 */
	public TesterDto findTesterById(final TesterId id) {
		
		return testerRepository.findById(id)
							   .map(TesterDto::fromDomain)
							   .orElseThrow(() -> new NotFoundException("Tester", id));
	}
	/**
	 * Load all Testers
	 * @return
	 */
	public List<TesterDto> findAllTesters() {
		
		return testerRepository.findAll().stream()
                			   .map(TesterDto::fromDomain)
                			   .toList();
	}
	/**
	 * Update existing Tester
	 * @param cmd
	 */
	public void updateTester(final UpdateTesterCommand cmd) {
		
		// get
        Tester tester = testerRepository.findById(cmd.id())
        								.orElseThrow(() -> new NotFoundException("Tester", cmd.id()));

        // update
        Tester updated = Tester.reconstitute(tester.getId(),
        									 cmd.name(), 
        									 cmd.email(),
        									 tester.getPassword(),
        									 tester.getBirthdate(),
        									 tester.getSex(),
        									 tester.getTestsDone(),
        									 tester.getMeasures());

        // save
        testerRepository.save(updated);
        
        // publish
        eventPublisher.publish(new TesterUpdated(updated.getId(),
        										 updated.getName(),
        										 updated.getEmail(),
        										 Instant.now()));
	}
	/**
	 * Update Tester password
	 * @param cmd
	 */
	public void updatePassword(final UpdatePasswordCommand cmd) {
		
	    // get
	    Tester tester = testerRepository.findById(cmd.id())
	    								.orElseThrow(() -> new NotFoundException("Tester", cmd.id()));
	    
	    // encrypt password 
	 	Password password = encryptPassword(cmd.newPassword()
	 										   .toString());
	    
	    // update password
	    Tester updated = Tester.reconstitute(tester.getId(),
	    									 tester.getName(),
	    									 tester.getEmail(),
	    									 password,  				// update
	    									 tester.getBirthdate(),
	    									 tester.getSex(),
	    									 tester.getTestsDone(),
	    									 tester.getMeasures());
	    
	    // save
	    testerRepository.save(updated);
	    
	    // publish
	    eventPublisher.publish(new TesterPasswordChanged(updated.getId(),
	    												 Instant.now()));
	}
	/**
	 * Remove Tester
	 * @param id
	 */
	public void deleteTester(TesterId id) {
		// validate if exists
        if (testerRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Tester", id);
        }

        // delete
        testerRepository.delete(id);
        
        // publish
        eventPublisher.publish(new TesterDeleted(
            id,
            Instant.now()
        ));
	}
																			
	// ───── PRIVATE METHODS ─────────────────────────────────────────────────
	private Password encryptPassword(String rawPassword) {
		
		String salt = BCrypt.gensalt(10);
		String hashed = BCrypt.hashpw(rawPassword, salt);
		
		return new Password(hashed);
	}
}
