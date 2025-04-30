## Mi presentación del proyecto

Este es mi ejercicio técnico para la [prueba técnica](https://www.notion.so/naizfit/Prueba-t-cnica-1d74271a30c880839c14ef4cd884e411). He construido desde cero un servicio backend siguiendo principios de Arquitectura Limpia y Domain-Driven Design (DDD), sin apoyarme en frameworks web pesados, para demostrar un control total sobre cada capa.

### Decisiones de diseño

- **Java + Guice + Servlet manual**

    - Inyección de dependencias con Google Guice.
    - Un único ApiServlet que delega en un Router POJO capaz de extraer path-vars y despachar a controladores.
    - Cero “magia” de Spring-MVC: todo el flujo `HTTP → DTO → Command ApplicationService → Dominio → Repo → HTTP` es explícito.

    Creo que se puede **diseñar en "local"** el core de una aplicación altamente escalable sin acoplarnos a ningun framework. De está forma partimos de un punto mas desacoplado y no podemos cometer algun error de acoplamiento aunqe el siguiente paso sea implementar dicho framework. Ademas de ligereza en desplieges para pruebas.

- **JSON plano para VOs**

    - Anotaciones @JsonCreator(mode=DELEGATING) y @JsonValue en los records unifield para que Jackson serialice/deserialice directamente strings (p.ej. "name":"Javier Nogales").


## Documentación
- [technical-plan](technical-plan.md)
- [architecture](architecture.md)
- [api-endpoints](api-endpoints.md)
