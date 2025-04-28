package com.naizfit.app.interfaceapi.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.naizfit.app.application.testers.TesterApplicationService;
import com.naizfit.app.application.testers.command.CreateTesterCommand;
import com.naizfit.app.application.testers.dto.TesterDto;
import com.naizfit.app.domain.testers.vo.TesterId;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TesterAdminController {

	private final TesterApplicationService testerService;
    private final ObjectMapper objectMapper;

	@Inject
	public TesterAdminController(final TesterApplicationService testerService,
								 final ObjectMapper objectMapper) {
		
		this.testerService = testerService;
		this.objectMapper = objectMapper;
	}

	public void create(HttpServletRequest req, HttpServletResponse res) throws StreamReadException, DatabindException, IOException {
		CreateTesterCommand cmd = objectMapper.readValue(req.getInputStream(),
										            	 CreateTesterCommand.class);
		TesterId id = testerService.createTester(cmd);
		
		res.setStatus(HttpServletResponse.SC_CREATED);
        res.setHeader("Location", "/admin/testers/" + id);
	}

	public void list(HttpServletRequest req, HttpServletResponse res) throws IOException {
        List<TesterDto> dtos = testerService.findAllTesters();
        
        res.setContentType("application/json");
        objectMapper.writeValue(res.getOutputStream(), dtos);
    }

	public void getById(HttpServletRequest req, HttpServletResponse res, Map<String,String> pathVars) throws StreamWriteException, DatabindException, IOException {
		
		/////
		String idString = pathVars.get("id");
	    TesterId id = new TesterId(UUID.fromString(idString));

	    /////
	    TesterDto dto = testerService.findTesterById(id);

	    /////
	    res.setStatus(HttpServletResponse.SC_OK);
	    res.setContentType("application/json");
	    objectMapper.writeValue(res.getOutputStream(), dto);
		
	}

	public void update(HttpServletRequest req, HttpServletResponse resp, Map<String,String> pathVars) {
		// TODO Auto-generated method stub
		
	}

	public void delete(HttpServletRequest req, HttpServletResponse resp, Map<String,String> pathVars) {
		// TODO Auto-generated method stub
		
	}
}
