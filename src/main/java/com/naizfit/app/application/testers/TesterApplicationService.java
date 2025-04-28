package com.naizfit.app.application.testers;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.mindrot.jbcrypt.BCrypt;

import com.google.inject.Inject;
import com.naizfit.app.application.testers.command.CreateTesterCommand;
import com.naizfit.app.application.testers.command.UpdatePasswordCommand;
import com.naizfit.app.application.testers.command.UpdateTesterCommand;
import com.naizfit.app.application.testers.dto.TesterDto;
import com.naizfit.app.domain.DomainEventPublisher;
import com.naizfit.app.domain.testers.Tester;
import com.naizfit.app.domain.testers.TesterRepository;
import com.naizfit.app.domain.testers.event.TesterDeleted;
import com.naizfit.app.domain.testers.event.TesterPasswordChanged;
import com.naizfit.app.domain.testers.event.TesterRegistered;
import com.naizfit.app.domain.testers.event.TesterUpdated;
import com.naizfit.app.domain.testers.vo.Password;
import com.naizfit.app.domain.testers.vo.TesterId;

public class TesterApplicationService {

	private final DomainEventPublisher eventPublisher;
	private final TesterRepository testerRepository;
	
	@Inject
	public TesterApplicationService(final DomainEventPublisher eventPublisher,
									final TesterRepository testerRepository) {
		
		this.eventPublisher = eventPublisher;
		this.testerRepository = testerRepository;
	}
	
	public TesterId createTester(CreateTesterCommand cmd) {
		// encrypt password 
		String salt = BCrypt.gensalt(10);
		String hashed = BCrypt.hashpw(cmd.rawPassword(), salt);
		Password password = new Password(hashed);
		
		// create
		Tester tester = Tester.create(cmd.name(),
									  cmd.email(),
									  password,
									  cmd.birthdate(),
									  cmd.sex());
		
		// save
		testerRepository.save(tester);
		
		// publish
		eventPublisher.publish(new TesterRegistered(tester.getId(),
													tester.getEmail(),
													tester.getName(),
													Instant.now()));
		
		return tester.getId();
		
	}
	public TesterDto findTesterById(TesterId id) {
		
		Tester tester = testerRepository.findById(id)
										.orElseThrow(() -> new NoSuchElementException("Tester id=" + id));
		return TesterDto.fromDomain(tester);
	}
	public List<TesterDto> findAllTesters() {
		
		return testerRepository.findAll().stream()
                			   .map(TesterDto::fromDomain)
                			   .toList();
	}
	public void updateTester(UpdateTesterCommand cmd) {
		
		// get
        Tester tester = testerRepository.findById(cmd.id())
        								.orElseThrow(() -> new NoSuchElementException("Tester id=" + cmd.id()));

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
	public void updatePassword(UpdatePasswordCommand cmd) {
		
	    // get
	    Tester tester = testerRepository.findById(cmd.id())
	    								.orElseThrow(() -> new NoSuchElementException("Tester id=" + cmd.id()));
	    
	    // update password
	    Tester updated = Tester.reconstitute(tester.getId(),
	    									 tester.getName(),
	    									 tester.getEmail(),
	    									 cmd.newPassword(),  // update
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


	public void deleteTester(TesterId id) {
		// validate if exists
        if (testerRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Tester id=" + id);
        }

        // delete
        testerRepository.delete(id);
        
        // publish
        eventPublisher.publish(new TesterDeleted(
            id,
            Instant.now()
        ));
	}
}
