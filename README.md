# CRduels

Apuestas clash

## Arquitectura por capas

El proyecto está organizado siguiendo tres capas principales:

- **Domain**: contiene las entidades del dominio y las interfaces de repositorio.
- **Application**: incluye los servicios de aplicación que orquestan la lógica de negocio.
- **Infrastructure**: expone los controladores REST y la implementación de los repositorios utilizando Spring Data JPA.

Esta separación permite mantener un código más limpio y facilita la evolución de cada capa de manera independiente.
