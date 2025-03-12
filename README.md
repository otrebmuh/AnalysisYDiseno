# Sistema de Gestión de Usuarios

Este proyecto es un ejemplo educativo que demuestra la implementación de una aplicación Java utilizando Spring Boot y JavaFX, siguiendo las mejores prácticas de diseño y arquitectura de software.

## Descripción

El sistema implementa una aplicación de gestión de usuarios con las siguientes funcionalidades:
- Agregar usuarios al sistema
- Listar usuarios existentes
- Gestión de grupos de usuarios
- Interfaz gráfica desarrollada con JavaFX

## Arquitectura

El proyecto sigue una arquitectura en capas:
- **Presentación**: Implementada con JavaFX para la interfaz de usuario
- **Negocio**: Servicios que implementan la lógica de negocio
- **Datos**: Repositorios para la persistencia de datos

## Requisitos Previos

- Java JDK 11 o superior
- Maven 3.6 o superior
- IDE compatible con Spring Boot (recomendado: Eclipse, IntelliJ IDEA o VS Code)

## Configuración del Entorno

1. Clonar el repositorio:
```bash
git clone [URL_DEL_REPOSITORIO]
cd AnalysisYDiseno
```

2. Compilar el proyecto:
```bash
mvn clean install
```

## Ejecutar la Aplicación

1. Desde la línea de comandos:
```bash
mvn spring-boot:run
```

2. Desde un IDE:
- Importar el proyecto como proyecto Maven
- Ejecutar la clase `ProyectoApplication` como aplicación Java

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── mx/uam/ayd/proyecto/
│   │       ├── datos/           # Capa de persistencia
│   │       ├── negocio/         # Lógica de negocio
│   │       │   └── modelo/      # Entidades de dominio
│   │       └── presentacion/    # Interfaces de usuario
│   └── resources/              # Configuración y recursos
└── test/                      # Pruebas unitarias
```

## Características Principales

- Arquitectura en capas bien definida
- Implementación de patrones de diseño
- Pruebas unitarias con JUnit y Mockito
- Persistencia con JPA/Hibernate
- Interfaz gráfica moderna con JavaFX

## Desarrollo

El proyecto está estructurado siguiendo las mejores prácticas de diseño:
- Inversión de Control (IoC)
- Inyección de Dependencias
- Principios SOLID
- Pruebas unitarias

## Base de Datos

El proyecto utiliza una base de datos H2 en memoria para desarrollo:
- Console H2: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Usuario: sa
- Password: (vacío)

## Pruebas

Para ejecutar las pruebas unitarias:
```bash
mvn test
```

## Documentación Adicional

El proyecto incluye diagramas y documentación adicional en la carpeta `docs/`:
- Diagramas de secuencia
- Diagramas de paquetes
- Documentación de la arquitectura

## Contribuir

1. Fork del repositorio
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit de tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## Licencia

Este proyecto es un ejemplo educativo y está disponible bajo la licencia MIT.

## Contacto

Humberto Cervantes Maceda - UAM Iztapalapa 