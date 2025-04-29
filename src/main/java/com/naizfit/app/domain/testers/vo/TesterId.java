package com.naizfit.app.domain.testers.vo;

import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonValue;

public record TesterId(UUID value) {
	
    public TesterId {
        Objects.requireNonNull(value, "TesterId must not be null");
    }
    
    @JsonValue
    public UUID value() {
        return value;
    }

    public static TesterId newId() {
        return new TesterId(UUID.randomUUID());
    }
    
    @Override 
    public String toString() {
    	return value.toString();
    }
}