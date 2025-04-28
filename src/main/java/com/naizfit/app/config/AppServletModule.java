package com.naizfit.app.config;

import com.google.inject.servlet.ServletModule;
import com.naizfit.app.interfaceapi.ApiServlet;

public class AppServletModule 
	 extends ServletModule {

	@Override
    protected void configureServlets() {
		
		// security filter
		
		// servlet
        serve("/api/*").with(ApiServlet.class);
    }
	
}
