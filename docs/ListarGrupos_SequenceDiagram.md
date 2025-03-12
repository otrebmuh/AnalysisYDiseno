# Sequence Diagram: Listar Grupos Flow

Este diagrama de secuencia ilustra el flujo de interacciones que ocurren cuando un usuario hace clic en el botón "Listar Grupos" en la aplicación.

```mermaid
sequenceDiagram
    actor User
    participant VP as VentanaPrincipal
    participant CP as ControlPrincipal
    participant CLG as ControlListarGrupos
    participant VLG as VentanaListarGrupos
    participant SG as ServicioGrupo
    participant DB as BD/Repositories

    User->>VP: Click "Listar Grupos"
    VP->>CP: listarGrupos()
    CP->>CLG: inicia()
    
    CLG->>SG: recuperaGrupos()
    SG->>DB: findAll()
    DB-->>SG: List<Grupo>
    
    Note over SG: Por cada grupo, obtiene<br/>número de usuarios
    
    SG-->>CLG: List<Grupo>
    CLG->>VLG: muestra(this, grupos)
    VLG->>VLG: initializeUI()
    
    Note over VLG: Configura tabla con columnas:<br/>- ID<br/>- Nombre<br/>- Número de Usuarios
    
    VLG->>VLG: Configura TableView
    VLG->>VLG: stage.show()
    VLG-->>User: Muestra ventana con grupos

    Note over User,VLG: Usuario revisa la información
    
    User->>VLG: Click "Cerrar"
    VLG->>VLG: stage.close()
```

## Explicación de la Secuencia

1. **Inicio del Caso de Uso**:
   - El usuario hace clic en el botón "Listar Grupos" en la ventana principal
   - La solicitud se propaga desde VentanaPrincipal → ControlPrincipal → ControlListarGrupos

2. **Recuperación de Datos**:
   - ControlListarGrupos solicita la lista de grupos a ServicioGrupo
   - ServicioGrupo obtiene los datos del repositorio
   - Para cada grupo, se calcula el número de usuarios asociados

3. **Presentación de Datos**:
   - VentanaListarGrupos inicializa la interfaz de usuario
   - Se configura una tabla con columnas para:
     * ID del grupo
     * Nombre del grupo
     * Número de usuarios en el grupo
   - Se muestra la ventana con los datos

4. **Finalización**:
   - El usuario revisa la información presentada
   - Al hacer clic en "Cerrar", la ventana se cierra y el control vuelve a la ventana principal 