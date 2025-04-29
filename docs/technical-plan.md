## Technical-Test Plan

This document have ideas, decisions, tasks and during technical-test development. 



### Infrastructure
- [x] Review documentation
- [x] Create GitHub repo
- [x] Create maven proyect
- [x] Create minimal test with Jakarta servlet
- [x] Configure Guice and create minimal test without JakartaServlet
- [x] Create integration test (manual) for http://localhost:8080/naizfit-tester-api/api/ping (reponse: pong)
- [x] Refactor: Reorganize github repo structure
- [x] Create Docker
- [x] Create build-and-run.sh
- [x] Create tag [`v0.1.0-infra`](https://github.com/javier-nogales/naizfit-technical-test/tree/v0.1.0-infra)
- [x] Add milestone info to README
- [x] Update Guice bindings (persistence, events, etc...)
- [x] Refactor Router: find better and elegant solution
- [x] Implement erro response 404, etc...
 
### Domain modeling
- [x] Modeling Testers agregate (without events)
- [ ] Refactor VO validation in more steps: (ie) null and empty 
- [x] Refactor VO names: quit sufix "Type" NameType --only--> Name
- [x] Modeling Product agregate (without events)
- [x] Modeling Tests agregate (without events)
- [x] Address/study domain event logic
- [x] Design solution
- [x] Implement domain events
- [x] Create specific id types like TesterID, MeasureID, TestId, ProductID, BrandID
- [x] Implement Domain exceptions (NotFoundException, etc...)

### ApplicationService
- [x] Create CRUD methods for Tester
- [x] Create Tester DTO
- [x] Define and create Tester commands
- [ ] Not allow duplicate Tester email


### Testing
- [x] Run again and start testing endpoints (curl)
- [ ] create unitary test for domain Tester
- [x] create unitary test for TesterApplicationServiceTest
- [x] Create Integragion tests for Testers

### Documentation
- [x] Update README
- [x] Create architecture doc
- [x] Create api-endpoints doc
- [x] Create presentation_es doc
