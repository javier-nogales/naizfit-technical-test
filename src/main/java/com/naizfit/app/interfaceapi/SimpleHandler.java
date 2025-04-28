package com.naizfit.app.interfaceapi;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface SimpleHandler {
	
	void handle(HttpServletRequest req,
				HttpServletResponse res) throws Exception;
}

