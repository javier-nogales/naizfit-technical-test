package com.naizfit.app.applicaton.testers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.naizfit.app.application.testers.TesterApplicationService;
import com.naizfit.app.application.testers.command.CreateTesterCommand;
import com.naizfit.app.application.testers.command.UpdateTesterCommand;
import com.naizfit.app.application.testers.dto.TesterDto;
import com.naizfit.app.domain.shared.exception.NotFoundException;
import com.naizfit.app.domain.shared.vo.Email;
import com.naizfit.app.domain.shared.vo.Name;
import com.naizfit.app.domain.shared.vo.Sex;
import com.naizfit.app.domain.testers.TesterRepository;
import com.naizfit.app.domain.testers.event.TesterDeleted;
import com.naizfit.app.domain.testers.event.TesterRegistered;
import com.naizfit.app.domain.testers.vo.Birthdate;
import com.naizfit.app.domain.testers.vo.TesterId;
import com.naizfit.app.infrastrucutre.InMemoryDomainEventPublisher;
import com.naizfit.app.infrastrucutre.InMemoryTesterRepository;

class TesterApplicationServiceTest {
	
    private TesterRepository testerRepository;
    private InMemoryDomainEventPublisher domainEventPublisher;
    private TesterApplicationService testerAplicationService;

    @BeforeEach
    void setUp() {
        testerRepository = new InMemoryTesterRepository();
        domainEventPublisher = new InMemoryDomainEventPublisher();
        testerAplicationService = new TesterApplicationService(domainEventPublisher, testerRepository);
    }

    @Test
    void createTesterPublishesEventAndSaves() {
    	
        CreateTesterCommand cmd = new CreateTesterCommand(new Name("Maria Nogales"),
        												  new Email("maria.nog76@gmail.com"),
        												  "secret",
        												  new Birthdate(LocalDate.of(1990,1,1)),
        												  Sex.MALE);
        
        TesterId id = testerAplicationService.createTester(cmd);
        assertNotNull(id);
        
        // repository contains
        TesterDto dto = testerAplicationService.findTesterById(id);
        assertEquals("Maria Nogales", dto.name().value());
        
        // event published
        List<Object> evts = domainEventPublisher.getPublishedEvents();
        assertTrue(evts.stream().anyMatch(e -> e instanceof TesterRegistered));
    }

    @Test
    void findNonexistentThrows() {
    	
        TesterId random = new TesterId(UUID.randomUUID());
        assertThrows(NotFoundException.class, () -> testerAplicationService.findTesterById(random));
    }

    @Test
    void updateTesterPublishesUpdatedEvent() {
    	
        // first create
        CreateTesterCommand create = new CreateTesterCommand(new Name("Javier Nogales"),
        													 new Email("javier.nog76@gmail.com"),
        													 "secret",
        													 new Birthdate(LocalDate.of(1985,5,5)), 
        													 Sex.MALE);
        TesterId id = testerAplicationService.createTester(create);
        domainEventPublisher.clear();
        
        // update
        UpdateTesterCommand cmd = new UpdateTesterCommand(
            id, new Name("Javi"), new Email("javi.nog76@gmail.com")
        );
        testerAplicationService.updateTester(cmd);
        TesterDto updated = testerAplicationService.findTesterById(id);
        
        assertEquals("Javi", updated.name().value());
        assertTrue(domainEventPublisher.getPublishedEvents().stream().anyMatch(e -> e instanceof com.naizfit.app.domain.testers.event.TesterUpdated));
    }

    @Test
    void deleteTesterPublishesDeletedEvent() {
    	
        CreateTesterCommand create = new CreateTesterCommand(new Name("Carol"),
        													 new Email("carol.76@gmail.com"),
        													 "secret",
        													 new Birthdate(LocalDate.of(1992,2,2)),
        													 Sex.FAMALE);
        
        TesterId id = testerAplicationService.createTester(create);
        domainEventPublisher.clear();
        testerAplicationService.deleteTester(id);
        assertThrows(NotFoundException.class, () -> testerAplicationService.findTesterById(id));
        assertTrue(domainEventPublisher.getPublishedEvents().stream().anyMatch(e -> e instanceof TesterDeleted));
    }
}
