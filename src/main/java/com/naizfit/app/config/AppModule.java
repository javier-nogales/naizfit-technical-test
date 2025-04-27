package com.naizfit.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
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
    
    @Provides @Singleton
    public ObjectMapper provideObjectMapper() {

        ObjectMapper mapper = new ObjectMapper();
        // LocalDate, LocalDateTime...
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // For Optional<T>
        mapper.registerModule(new Jdk8Module());
        // Serialize records/constructores
        mapper.registerModule(new ParameterNamesModule());
        
        return mapper;
    }
}

