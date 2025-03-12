# Sequence Diagram: Agregar Usuario Flow

Este diagrama de secuencia ilustra el flujo de interacciones que ocurren cuando un usuario hace clic en el botón "Agregar Usuario" en la aplicación.

```mermaid
sequenceDiagram
    actor User
    participant VP as VentanaPrincipal
    participant CP as ControlPrincipal
    participant CAU as ControlAgregarUsuario
    participant VAU as VentanaAgregarUsuario
    participant SG as ServicioGrupo
    participant SU as ServicioUsuario
    participant DB as BD/Repositories

    User->>VP: Click "Agregar Usuario"
    VP->>CP: agregarUsuario()
    CP->>CAU: inicia()
    CAU->>SG: recuperaGrupos()
    SG->>DB: findAll()
    DB-->>SG: List<Grupo>
    SG-->>CAU: List<Grupo>
    CAU->>VAU: muestra(this, grupos)
    VAU->>VAU: initializeUI()
    VAU->>VAU: stage.show()
    
    Note over User,VAU: User fills form and submits
    User->>VAU: Enter nombre, apellido
    User->>VAU: Select grupo from combobox
    User->>VAU: Click "Agregar" button
    
    VAU->>VAU: Validate form fields
    VAU->>CAU: agregaUsuario(nombre, apellido, grupo)
    
    CAU->>SU: agregaUsuario(nombre, apellido, nombreGrupo)
    SU->>DB: findByNombreAndApellido(nombre, apellido)
    DB-->>SU: null (user doesn't exist)
    SU->>DB: findByNombre(nombreGrupo)
    DB-->>SU: Grupo
    
    SU->>SU: Create new Usuario
    SU->>SU: grupo.addUsuario(usuario)
    SU->>DB: grupoRepository.save(grupo)
    DB-->>SU: saved Grupo with Usuario
    SU-->>CAU: Usuario
    
    CAU->>VAU: muestraDialogoConMensaje("Usuario agregado exitosamente")
    VAU->>User: Show success dialog
    
    CAU->>CAU: termina()
    CAU->>VAU: setVisible(false)
    VAU->>VAU: stage.hide()
```

## Explicación de la Secuencia

1. **Interacción del Usuario y Flujo Inicial**:
   - El usuario hace clic en el botón "Agregar Usuario" en la ventana principal
   - La solicitud fluye desde VentanaPrincipal → ControlPrincipal → ControlAgregarUsuario
   - ControlAgregarUsuario recupera la lista de grupos de ServicioGrupo
   - VentanaAgregarUsuario se inicializa y muestra el formulario al usuario

2. **Envío y Procesamiento del Formulario**:
   - El usuario ingresa información y hace clic en el botón "Agregar"
   - VentanaAgregarUsuario valida los campos del formulario
   - Si son válidos, llama a ControlAgregarUsuario.agregaUsuario() con los datos del formulario

3. **Lógica de Negocio y Persistencia de Datos**:
   - ServicioUsuario verifica si el usuario ya existe
   - Recupera el grupo seleccionado
   - Crea un nuevo usuario y lo agrega al grupo
   - Guarda el grupo actualizado en la base de datos

4. **Confirmación y Finalización**:
   - Se muestra un mensaje de éxito al usuario
   - La ventana se cierra y el control regresa a la ventana principal de la aplicación 