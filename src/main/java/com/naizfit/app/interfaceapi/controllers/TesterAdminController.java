package com.naizfit.app.interfaceapi.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.naizfit.app.application.testers.TesterApplicationService;
import com.naizfit.app.application.testers.command.CreateTesterCommand;
import com.naizfit.app.application.testers.command.UpdatePasswordCommand;
import com.naizfit.app.application.testers.command.UpdateTesterCommand;
import com.naizfit.app.application.testers.dto.TesterDto;
import com.naizfit.app.domain.shared.exception.NotFoundException;
import com.naizfit.app.domain.shared.vo.Email;
import com.naizfit.app.domain.shared.vo.Name;
import com.naizfit.app.domain.testers.vo.Password;
import com.naizfit.app.domain.testers.vo.TesterId;
import com.naizfit.app.interfaceapi.dto.UpdatePasswordRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TesterAdminController {

	// ───── SERVICES ────────────────────────────────────────────────────────
	private final TesterApplicationService testerService;
    private final ObjectMapper objectMapper;

    // ───── CONSTRUCTOR ─────────────────────────────────────────────────────
	@Inject
	public TesterAdminController(final TesterApplicationService testerService,
								 final ObjectMapper objectMapper) {
		
		this.testerService = testerService;
		this.objectMapper = objectMapper;
	}

	
	public void create(final HttpServletRequest req, 
					   final HttpServletResponse res) throws StreamReadException, DatabindException, IOException {
		
		CreateTesterCommand cmd = objectMapper.readValue(req.getInputStream(),
										            	 CreateTesterCommand.class);
		TesterId id = testerService.createTester(cmd);
		
		res.setStatus(HttpServletResponse.SC_CREATED);
        res.setHeader("Location", "/admin/testers/" + id);
	}

	public void list(final HttpServletRequest req, 
					 final HttpServletResponse res) throws IOException {
		
        List<TesterDto> dtos = testerService.findAllTesters();
        
        res.setContentType("application/json");
        objectMapper.writeValue(res.getOutputStream(), dtos);
    }

	public void getById(final HttpServletRequest req, 
						final HttpServletResponse res, 
						final Map<String,String> pathVars) throws StreamWriteException, DatabindException, IOException {
		
		/////
		String idString = pathVars.get("id");
	    TesterId id = new TesterId(UUID.fromString(idString));

	    try {
	    	/////
	    	TesterDto dto = testerService.findTesterById(id);

		    /////
		    res.setStatus(HttpServletResponse.SC_OK);
		    res.setContentType("application/json");
		    objectMapper.writeValue(res.getOutputStream(), dto);
		} catch (NotFoundException e) {
			
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			// TODO: send a body like "{ "error": "Tester id=… not found" }"
		}
		
	}

	public void update(final HttpServletRequest req, 
					   final HttpServletResponse res, 
					   final Map<String,String> pathVars) throws IOException {
		
		/////
		String idString = pathVars.get("id");
		TesterId id = new TesterId(UUID.fromString(idString));
		
		/////
		var node = objectMapper.readTree(req.getInputStream());
		Name name = new Name(node.get("name").asText());
		Email email = new Email(node.get("email").asText());
		
		/////
		UpdateTesterCommand cmd = new UpdateTesterCommand(id, name, email);
		testerService.updateTester(cmd);
		
		/////
		res.setStatus(HttpServletResponse.SC_NO_CONTENT);
		
	}

	public void delete(final HttpServletRequest req, 
					   final HttpServletResponse res, 
					   final Map<String,String> pathVars) {
		/////
		String idString = pathVars.get("id");
        TesterId id = new TesterId(UUID.fromString(idString));
        
        /////
        testerService.deleteTester(id);

        /////
        res.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
	
	public void updatePassword(final HttpServletRequest req,
            				   final HttpServletResponse res,
            				   final Map<String,String> pathVars) throws IOException {
		/////
		String idString = pathVars.get("id");
		TesterId id = new TesterId(UUID.fromString(idString));
	
		UpdatePasswordRequest apiDto = objectMapper.readValue(req.getInputStream(),
													   		  UpdatePasswordRequest.class);
		UpdatePasswordCommand cmd = new UpdatePasswordCommand(id,
															  new Password(apiDto.newPassword()));

		/////
		testerService.updatePassword(cmd);
		
		/////
		res.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
}
