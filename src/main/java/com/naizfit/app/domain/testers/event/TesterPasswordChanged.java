package com.naizfit.app.domain.testers.event;

import java.time.Instant;

import com.naizfit.app.domain.testers.vo.TesterId;

public record TesterPasswordChanged(TesterId testerId,
	    							Instant occurredAt) {

}
