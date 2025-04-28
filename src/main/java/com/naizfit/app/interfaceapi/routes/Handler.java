package com.naizfit.app.interfaceapi.routes;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@FunctionalInterface
interface Handler {
	
    void handle(HttpServletRequest req,
    			HttpServletResponse res, 
    			Map<String,String> pathVars) throws Exception;
}
