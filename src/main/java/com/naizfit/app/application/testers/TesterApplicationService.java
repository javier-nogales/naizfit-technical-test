package com.naizfit.app.application.testers;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.google.inject.Inject;
import com.naizfit.app.application.testers.command.CreateTesterCommand;
import com.naizfit.app.application.testers.command.UpdateTesterCommand;
import com.naizfit.app.application.testers.dto.TesterDto;
import com.naizfit.app.domain.testers.Tester;
import com.naizfit.app.domain.testers.TesterRepository;
import com.naizfit.app.domain.testers.vo.Password;
import com.naizfit.app.domain.testers.vo.TesterId;

public class TesterApplicationService {

	private final TesterRepository testers;
	
	@Inject
	public TesterApplicationService(final TesterRepository testers) {
		
		this.testers = testers;
	}
	
	public TesterId createTester(CreateTesterCommand cmd) {
		
		String salt = BCrypt.gensalt(10);
		String hashed = BCrypt.hashpw(cmd.rawPassword(), salt);
		Password password = new Password(hashed);
		
		Tester tester = Tester.create(cmd.name(),
									  cmd.email(),
									  password,
									  cmd.birthdate(),
									  cmd.sex());
		
		testers.save(tester);
		
		return tester.getId();
		
	}
	public TesterDto findTesterById(TesterId id) {
		throw new RuntimeException("Not implemented yet!");
	}
	public List<TesterDto> findAllTesters() {
		return testers.findAll().stream()
                				.map(TesterDto::fromDomain)
                				.toList();
	}
	public void updateTester(UpdateTesterCommand cmd) {
		throw new RuntimeException("Not implemented yet!");
	}
	public void deleteTester(TesterId id) {
		throw new RuntimeException("Not implemented yet!");
	}
}
