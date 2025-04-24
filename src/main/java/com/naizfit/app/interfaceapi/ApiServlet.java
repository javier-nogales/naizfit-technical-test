package com.naizfit.app.interfaceapi;

import java.io.IOException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Singleton
public class ApiServlet extends HttpServlet {

	private static final long serialVersionUID = -7841564740226850700L;
	
	private Router _router;
	
	@Inject
	public ApiServlet(final Router router) {
		_router = router;
	}

	@Override
	protected void service(final HttpServletRequest req, 
						   final HttpServletResponse resp) throws IOException {
		String method = req.getMethod();
		String path = req.getPathInfo();
		_router.route(method, path, req, resp);
	}

}
