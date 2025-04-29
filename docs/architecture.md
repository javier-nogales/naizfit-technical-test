## Architecture

This document describes the architecture of the Naizfit Community Backend API Service, including its layering, bounded contexts, key components, and data flow. It serves as a guide for developers and architects.

### 1. Layers structure
```
+---------------------------------------------------------+
|                    Presentation Layer                   |
|  (interfaceapi: Servlet, Router, Controllers, DTOs)     |
+---------------------------------------------------------+
                 |                   ^
                 | maps JSON ↔ DTOs  |
                 v                   |
+---------------------------------------------------------+
|                  Application Layer                      |
| (application: Services, Commands, Application DTOs)      |
+---------------------------------------------------------+
                 |                   ^
                 | invokes           |
                 v                   |
+---------------------------------------------------------+
|                     Domain Layer                        |
| (domain: Entities, Value Objects, Repositories,         |
|  Domain Services/Policies, Domain Events, Exceptions)   |
+---------------------------------------------------------+
                 |                   ^
                 | implemented by    |
                 v                   |
+---------------------------------------------------------+
|                  Infrastructure Layer                   |
| (infrastructure: Repo impls, EventPublisher, DI,        |
|  Docker, Tomcat, Guice modules)                         |
+---------------------------------------------------------+
```

### 2. Technology Stack

- Java 17, Jakarta Servlet API, Google Guice for DI
- Jackson for JSON (with ParameterNamesModule, JavaTimeModule)
- JUnit 5, HttpClient for integration tests, Maven Surefire
- Docker + Tomcat for local deployment

### 3. Data Flow: Example “Create Tester”

1. **HTTP Request**: `POST /api/admin/testers` with JSON body → handled by `ApiServlet` → delegated to `Router`.
2. **Routing**: `Router` matches path → invokes `TesterAdminController.create(...)`, mapping JSON to `CreateTesterRequest`.
3. **Controller → Application**: Controller converts request DTO to `CreateTesterCommand` and calls `TesterApplicationService.createTester(cmd)`.
4. **Application Service**: orchestrates:
   - Validates command
   - Creates new `Tester` aggregate (calls `Tester.create(...)`)
   - Persists via `TesterRepository.save(...)`
   - Publishes `TesterRegistered` Domain Event via `DomainEventPublisher`
   - Returns `TesterId`
5. **Controller Response**: Controller sends `201 Created` with `Location: /api/admin/testers/{id}`.
6. **Infrastructure**: InMemory implementations handle storage and event capturing (or Kafka in future).


### 4. Core Domain and Aggregate Roots

### Testing BC

- **Aggregate Root: `Tester`**
  - Entities: `Tester` (id, name, email, password, birthdate, sex)  
  - Value Objects: `TesterId`, `Name`, `Email`, `Password`, `Birthdate`, `Sex`  
  - Domain Events: `TesterRegistered`, `TesterUpdated`, `TesterDeleted`, `TesterPasswordChanged`
  - **Entity: `measure`** (nested inside Product aggregate)

- **Aggregate Root: `Test`**
  - Entities: `Test` (id, testerId, productSku, size, opinion, status)
  - Value Objects: `TestId`, `FitOpinion`, `Size`
  - Domain Events: `TestCreated`, `TestOpinionRecorded`, `TestClosed`

- **Aggregate Root: `Product`**
  - Entities: `Product` (sku, sizes, pictures, brand, color)
  - Value Objects: `Sku`, `Size`, `Pictures`, `Color`, `Logo`  
  - Domain Events: `ProductCreated`, `ProductSizeAdded`, `ProductUpdated`
  - **Entity: `Brand`** (nested inside Product aggregate)

### 5. Next Steps
- **Validate tester duplicated email in creation process**
- Implement all end points
- Implement Domain tests
- Secure Admin API with JWT filter.
- Publish OpenAPI/Swagger spec.


