package com.naizfit.app.domain.testers.event;

import java.time.Instant;

import com.naizfit.app.domain.shared.vo.Email;
import com.naizfit.app.domain.shared.vo.Name;
import com.naizfit.app.domain.testers.vo.TesterId;

public record TesterUpdated(TesterId testerId,
						    Name name,
						    Email email,
						    Instant occurredAt) {
}
