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
	
    private TesterRepository repo;
    private InMemoryDomainEventPublisher publisher;
    private TesterApplicationService service;

    @BeforeEach
    void setUp() {
        repo = new InMemoryTesterRepository();
        publisher = new InMemoryDomainEventPublisher();
        service = new TesterApplicationService(publisher, repo);
    }

    @Test
    void createTesterPublishesEventAndSaves() {
        CreateTesterCommand cmd = new CreateTesterCommand(
            new Name("Alice"),
            new Email("alice@example.com"),
            "secret",
            new Birthdate(LocalDate.of(1990,1,1)),
            Sex.FAMALE
        );
        TesterId id = service.createTester(cmd);
        assertNotNull(id);
        // repository contains
        TesterDto dto = service.findTesterById(id);
        assertEquals("Alice", dto.name().value());
        // event published
        List<Object> evts = publisher.getPublishedEvents();
        assertTrue(evts.stream().anyMatch(e -> e instanceof TesterRegistered));
    }

    @Test
    void findNonexistentThrows() {
        TesterId random = new TesterId(UUID.randomUUID());
        assertThrows(NotFoundException.class, () -> service.findTesterById(random));
    }

    @Test
    void updateTesterPublishesUpdatedEvent() {
        // first create
        CreateTesterCommand create = new CreateTesterCommand(new Name("Bob"),
        													 new Email("bob@example.com"),
        													 "pwd",
        													 new Birthdate(LocalDate.of(1985,5,5)), 
        													 Sex.MALE);
        TesterId id = service.createTester(create);
        publisher.clear();
        // update
        UpdateTesterCommand cmd = new UpdateTesterCommand(
            id, new Name("Bobby"), new Email("bobby@example.com")
        );
        service.updateTester(cmd);
        TesterDto updated = service.findTesterById(id);
        assertEquals("Bobby", updated.name().value());
        assertTrue(publisher.getPublishedEvents().stream().anyMatch(e -> e instanceof com.naizfit.app.domain.testers.event.TesterUpdated));
    }

    @Test
    void deleteTesterPublishesDeletedEvent() {
        CreateTesterCommand create = new CreateTesterCommand(new Name("Carol"),
        													 new Email("carol@example.com"),
        													 "pwd",
        													 new Birthdate(LocalDate.of(1992,2,2)),
        													 Sex.FAMALE);
        TesterId id = service.createTester(create);
        publisher.clear();
        service.deleteTester(id);
        assertThrows(NotFoundException.class, () -> service.findTesterById(id));
        assertTrue(publisher.getPublishedEvents().stream().anyMatch(e -> e instanceof TesterDeleted));
    }
}
