package com.naizfit.app.application.testers.dto;

import java.util.Objects;

import com.naizfit.app.domain.shared.vo.Email;
import com.naizfit.app.domain.shared.vo.Name;
import com.naizfit.app.domain.shared.vo.Sex;
import com.naizfit.app.domain.testers.Tester;
import com.naizfit.app.domain.testers.vo.Birthdate;
import com.naizfit.app.domain.testers.vo.TesterId;

public record TesterDto(TesterId id,
						Name name,
						Email email,
						Birthdate birthdate,
						Sex sex) {
	
	public static TesterDto fromDomain(final Tester tester) {
		
		Objects.requireNonNull(tester, "Tester must not be null");
		return new TesterDto(tester.getId(),
							 tester.getName(),
							 tester.getEmail(),
							 tester.getBirthdate(),
							 tester.getSex());
	}
}
