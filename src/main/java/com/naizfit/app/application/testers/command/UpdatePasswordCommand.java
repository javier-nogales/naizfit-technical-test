package com.naizfit.app.application.testers.command;

import com.naizfit.app.domain.testers.vo.Password;
import com.naizfit.app.domain.testers.vo.TesterId;

public record UpdatePasswordCommand(TesterId id,
									Password newPassword) {

}
