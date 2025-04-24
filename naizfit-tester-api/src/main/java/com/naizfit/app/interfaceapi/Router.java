package com.naizfit.app.interfaceapi;

import java.io.IOException;

import com.google.inject.Inject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Router {
	
	final PingController _pingController;
	
	@Inject
	public Router (final PingController pingController) {
		_pingController = pingController;
	}

	public void route(final String method, 
					  final String path, 
					  final HttpServletRequest req, 
					  final HttpServletResponse resp) throws IOException {

		System.out.println(">>> Routing... " + path + ", " + method);
		
		switch(method + " " + path) {
			case "GET /ping" ->  _pingController.ping(req, resp);
			default -> {
	            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
	            resp.getWriter().write("Ruta no encontrada: " + path);
	        }
		
		};
		
	}

}
