# Proyecto Análisis y Diseño

Este proyecto es una aplicación JavaFX con Spring Boot que implementa un sistema de gestión de usuarios y grupos. Es un proyecto para un curso de Analisis y Diseño.

## Tecnologías Utilizadas

- **Spring Boot 3.2.12** - Framework de aplicación
- **Java 17** - Lenguaje de programación
- **JavaFX 21.0.2** - Framework de interfaz gráfica
- **Spring Data JPA** - Persistencia de datos
- **H2 Database** - Base de datos en memoria
- **Maven** - Gestión de dependencias
- **JUnit 5** - Framework de pruebas
- **TestFX** - Pruebas de interfaz gráfica

## Requisitos del Sistema

- **Java 17 o superior** (JDK 17+)
- **Maven 3.6+**

## Instalación y Configuración

### 1. Verificar Java
```bash
java -version
```
Asegúrate de tener Java 17 o superior instalado.

### 2. Clonar el repositorio
```bash
git clone <url-del-repositorio>
cd AnalysisYDiseno
```

### 3. Compilar el proyecto
```bash
mvn clean compile
```

### 4. Ejecutar la aplicación
```bash
mvn javafx:run
```

O alternativamente:
```bash
mvn spring-boot:run
```

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/mx/uam/ayd/proyecto/
│   │   ├── datos/           # Capa de datos (Repositorios)
│   │   ├── negocio/         # Capa de negocio (Servicios y Modelos)
│   │   ├── presentacion/    # Capa de presentación (JavaFX)
│   │   └── ProyectoApplication.java
│   └── resources/
│       └── application.yml  # Configuración de la aplicación
└── test/
    ├── java/                # Pruebas unitarias e integración
    └── resources/
        └── application-test.properties
```

## Funcionalidades

- **Agregar Usuario**: Crear nuevos usuarios en el sistema
- **Listar Usuarios**: Ver todos los usuarios registrados
- **Listar Grupos**: Ver todos los grupos disponibles

## Ejecutar Pruebas

```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar pruebas con cobertura
mvn jacoco:report
```

## Configuración de Base de Datos

La aplicación utiliza H2 Database en memoria por defecto. La consola H2 está habilitada en:
- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: jdbc:h2:mem:testdb
- **Usuario**: sa
- **Contraseña**: (vacía)

## Migración de Spring Boot 2.x a 3.x

Este proyecto ha sido migrado de Spring Boot 2.7.3 a 3.2.12. Los principales cambios incluyen:

### Cambios Realizados:
1. **Actualización de Spring Boot**: 2.7.3 → 3.2.12
2. **Actualización de Java**: 11 → 17
3. **Actualización de JavaFX**: 17.0.2 → 21.0.2
4. **Migración de JPA**: `javax.persistence` → `jakarta.persistence`
5. **Actualización de dependencias de prueba**

### Beneficios de la Migración:
- Mejor rendimiento y estabilidad
- Soporte para características modernas de Java
- Mejoras de seguridad
- Soporte extendido hasta 2025

## Desarrollo

### Agregar nuevas funcionalidades:
1. Crear el modelo en `negocio/modelo/`
2. Crear el repositorio en `datos/`
3. Crear el servicio en `negocio/`
4. Crear la interfaz en `presentacion/`
5. Agregar pruebas en `test/`

## Licencia

Este proyecto es parte del curso de Análisis y Diseño de Software.

Contactar a Humberto Cervantes de la UAM Iztapalapa en hcm@xanum.uam.mx
