package com.naizfit.app.config;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.ServletModule;
import com.naizfit.app.interfaceapi.ApiServlet;
import com.naizfit.app.interfaceapi.PingController;
import com.naizfit.app.interfaceapi.Router;

public class AppModule extends AbstractModule {
	
    @Override
    protected void configure() {
    	
        install(new ServletModule() {
            @Override
            protected void configureServlets() {
                serve("/api/*").with(ApiServlet.class);
            }
        });
        
        // routes
        bind(Router.class);
        bind(PingController.class);
    }
}

