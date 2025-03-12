# Sequence Diagram: Crear Grupo Flow

Este diagrama de secuencia ilustra el flujo de interacciones que ocurren cuando un usuario hace clic en el botón "Crear Grupo" en la aplicación.

```mermaid
sequenceDiagram
    actor User
    participant VP as VentanaPrincipal
    participant CP as ControlPrincipal
    participant CCG as ControlCrearGrupo
    participant VCG as VentanaCrearGrupo
    participant SG as ServicioGrupo
    participant DB as BD/Repositories

    User->>VP: Click "Crear Grupo"
    VP->>CP: crearGrupo()
    CP->>CCG: inicia()
    CCG->>VCG: muestra(this)
    VCG->>VCG: initializeUI()
    VCG->>VCG: stage.show()
    
    Note over User,VCG: User fills form and submits
    User->>VCG: Enter nombre
    User->>VCG: Click "Crear" button
    
    VCG->>VCG: Validate form fields
    VCG->>CCG: creaGrupo(nombre)
    
    CCG->>SG: creaGrupo(nombre)
    SG->>DB: findByNombre(nombre)
    DB-->>SG: null (grupo doesn't exist)
    
    SG->>SG: Create new Grupo
    SG->>DB: save(grupo)
    DB-->>SG: saved Grupo
    SG-->>CCG: Grupo
    
    CCG->>VCG: muestraDialogoConMensaje("Grupo creado exitosamente")
    VCG->>User: Show success dialog
    
    CCG->>CCG: termina()
    CCG->>VCG: setVisible(false)
    VCG->>VCG: stage.hide()
```

## Explicación de la Secuencia

1. **Interacción del Usuario y Flujo Inicial**:
   - El usuario hace clic en el botón "Crear Grupo" en la ventana principal
   - La solicitud fluye desde VentanaPrincipal → ControlPrincipal → ControlCrearGrupo
   - VentanaCrearGrupo se inicializa y muestra el formulario al usuario

2. **Envío y Procesamiento del Formulario**:
   - El usuario ingresa el nombre del grupo y hace clic en el botón "Crear"
   - VentanaCrearGrupo valida el campo del formulario
   - Si es válido, llama a ControlCrearGrupo.creaGrupo() con el nombre del grupo

3. **Lógica de Negocio y Persistencia de Datos**:
   - ServicioGrupo verifica si el grupo ya existe
   - Si no existe, crea un nuevo grupo
   - Guarda el nuevo grupo en la base de datos

4. **Confirmación y Finalización**:
   - Se muestra un mensaje de éxito al usuario
   - La ventana se cierra y el control regresa a la ventana principal de la aplicación 