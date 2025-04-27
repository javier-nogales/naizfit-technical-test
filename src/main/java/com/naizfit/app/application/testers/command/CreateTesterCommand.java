package com.naizfit.app.application.testers.command;

import com.naizfit.app.domain.shared.vo.Email;
import com.naizfit.app.domain.shared.vo.Name;
import com.naizfit.app.domain.shared.vo.Sex;
import com.naizfit.app.domain.testers.vo.Birthdate;

public record CreateTesterCommand(
		  Name name,
		  Email email,
		  String rawPassword,
		  Birthdate birthdate,
		  Sex sex
		) {}