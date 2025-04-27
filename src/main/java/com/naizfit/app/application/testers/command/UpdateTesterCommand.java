package com.naizfit.app.application.testers.command;

import com.naizfit.app.domain.shared.vo.Email;
import com.naizfit.app.domain.shared.vo.Name;
import com.naizfit.app.domain.testers.vo.TesterId;

public record UpdateTesterCommand(
		  TesterId id,
		  Name name,
		  Email email
		) {}
