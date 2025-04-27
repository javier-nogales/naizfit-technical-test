package com.naizfit.app.interfaceapi;

import java.io.IOException;

import com.google.inject.Inject;
import com.naizfit.app.interfaceapi.controllers.TesterAdminController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Router {
	
	private final PingController pingController;
	private final TesterAdminController testerAdminController;
	
	@Inject
	public Router (final PingController pingController,
				   final TesterAdminController testerAdminController) {
		
		this.pingController = pingController;
		this.testerAdminController = testerAdminController;
	}

	public void route(final String method, 
					  final String path, 
					  final HttpServletRequest req, 
					  final HttpServletResponse resp) throws IOException {
		
		System.out.println(">>> Routing... " + path + ", " + method);
		
		// TODO: create best router handler.
		
		switch(method + " " + path) {
			case "GET" -> {
                if ("/admin/testers".equals(path)) {
                    testerAdminController.list(req, resp);
                }
                else if (path.startsWith("/admin/testers/")) {
                    // GET /admin/testers/{id}
                    String id = path.substring("/admin/testers/".length());
                    req.setAttribute("id", id);
                    testerAdminController.getById(req, resp);
                }
                else if ("/ping".equals(path)) {
                    pingController.ping(req, resp);
                }
                else {
                    notFound(resp, path);
                }
            }
            case "POST" -> {
                if ("/admin/testers".equals(path)) {
                    testerAdminController.create(req, resp);
                }
                else {
                    notFound(resp, path);
                }
            }
            case "PUT" -> {
                if (path.startsWith("/admin/testers/")) {
                    // PUT /admin/testers/{id}
                    String id = path.substring("/admin/testers/".length());
                    req.setAttribute("id", id);
                    testerAdminController.update(req, resp);
                }
                else {
                    notFound(resp, path);
                }
            }
            case "DELETE" -> {
                if (path.startsWith("/admin/testers/")) {
                    // DELETE /admin/testers/{id}
                    String id = path.substring("/admin/testers/".length());
                    req.setAttribute("id", id);
                    testerAdminController.delete(req, resp);
                }
                else {
                    notFound(resp, path);
                }
            }
            default -> notFound(resp, path);
        }
    
		
	}
	
	private void notFound(HttpServletResponse resp, String path) throws IOException {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        resp.getWriter().write("Ruta no encontrada: " + path);
    }

}
