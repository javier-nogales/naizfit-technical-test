package com.naizfit.app.interfaceapi;

import java.io.IOException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Singleton
public class ApiServlet 
	 extends HttpServlet {

	private static final long serialVersionUID = -7841564740226850700L;
	
	private Router router;
	
	@Inject
	public ApiServlet(final Router router) {
		this.router = router;
	}

	@Override
	protected void service(final HttpServletRequest req, 
						   final HttpServletResponse res) throws IOException, ServletException {
		
		try {
            router.route(req, res);
        } catch (Exception e) {
            throw new ServletException(e);
        }
	}

}
