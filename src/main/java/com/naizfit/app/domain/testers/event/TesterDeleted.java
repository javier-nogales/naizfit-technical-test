package com.naizfit.app.domain.testers.event;

import java.time.Instant;

import com.naizfit.app.domain.testers.vo.TesterId;

public record TesterDeleted(TesterId testerId,
	    					Instant occurredAt) {
}
