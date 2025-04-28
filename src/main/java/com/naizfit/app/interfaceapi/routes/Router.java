package com.naizfit.app.interfaceapi.routes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import com.google.inject.Inject;
import com.naizfit.app.interfaceapi.controllers.PingController;
import com.naizfit.app.interfaceapi.controllers.TesterAdminController;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Router {
	
	private final List<Route> routes = new ArrayList<>();
	
	private final PingController pingController;
	private final TesterAdminController testerAdminController;
	
	@Inject
	public Router (final PingController pingController,
				   final TesterAdminController testerAdminController) {
		
		this.pingController = pingController;
		this.testerAdminController = testerAdminController;
		
		loadRoutes();
	}
	
    public void route(final HttpServletRequest req,
    				  final HttpServletResponse res) throws ServletException, IOException {
    	
        String requestMethod = req.getMethod();
        String path = req.getPathInfo();
        
        for (Route route : routes) {
        	
            Matcher m = route.match(requestMethod, path);
            
            if (m != null) {
                Map<String,String> pathVars = route.extractPathVariables(m);
                try {
                    route.getHandler().handle(req, res, pathVars);
                } catch (Exception e) {
                    throw new ServletException(e);
                }
                return;
            }
        }
        
        res.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
    
    private void loadRoutes() {
    	// pin
		routes.add(new Route("GET",	   "/ping",							pingController::ping));
		
		// Admin API for Testers
        routes.add(new Route("POST",   "/admin/testers", 				testerAdminController::create));
        routes.add(new Route("GET",    "/admin/testers", 				testerAdminController::list));
        routes.add(new Route("GET",    "/admin/testers/:id", 			testerAdminController::getById));
        routes.add(new Route("PUT",    "/admin/testers/:id", 			testerAdminController::update));
        routes.add(new Route("PUT",    "/admin/testers/:id/password", 	testerAdminController::updatePassword));
        routes.add(new Route("DELETE", "/admin/testers/:id", 			testerAdminController::delete));
    }

}
