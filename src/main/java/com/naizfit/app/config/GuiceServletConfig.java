package com.naizfit.app.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.naizfit.app.interfaceapi.ApiServlet;

public class GuiceServletConfig 
	 extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
    	
        return Guice.createInjector(
        		new AppServletModule(),
        		new AppModule()
        );
    }
}
