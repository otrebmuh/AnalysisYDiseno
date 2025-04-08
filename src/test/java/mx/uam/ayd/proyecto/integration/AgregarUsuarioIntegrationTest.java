package mx.uam.ayd.proyecto.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import mx.uam.ayd.proyecto.BaseIntegrationTest;
import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.datos.UsuarioRepository;
import mx.uam.ayd.proyecto.negocio.ServicioUsuario;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

public class AgregarUsuarioIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @Transactional
    public void testAgregarUsuario() {
        // Given
        String nombre = "Test";
        String apellido = "User";
        String nombreGrupo = "Test Group";

        // Create a test group
        Grupo grupo = new Grupo();
        grupo.setNombre(nombreGrupo);
        grupoRepository.save(grupo);

        // When
        Usuario usuario = servicioUsuario.agregaUsuario(nombre, apellido, nombreGrupo);

        // Then
        assertNotNull(usuario);
        assertEquals(nombre, usuario.getNombre());
        assertEquals(apellido, usuario.getApellido());

        // Verify in database
        Usuario savedUsuario = usuarioRepository.findByNombreAndApellido(nombre, apellido);
        assertNotNull(savedUsuario);

        // Verify group association
        Grupo savedGrupo = grupoRepository.findByNombre(nombreGrupo);
        assertNotNull(savedGrupo);
        assertTrue(savedGrupo.getUsuarios().contains(savedUsuario));
    }

    @Test
    @Transactional
    public void testAgregarUsuarioConGrupoInexistente() {
        // Given
        String nombre = "Test";
        String apellido = "User";
        String nombreGrupo = "NonExistentGroup";

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> servicioUsuario.agregaUsuario(nombre, apellido, nombreGrupo));
        
        assertEquals("No se encontró el grupo", exception.getMessage());
    }

    @Test
    @Transactional
    public void testAgregarUsuarioConDatosInvalidos() {
        // Given
        String nombre = "";
        String apellido = "User";
        String nombreGrupo = "Test Group";

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> servicioUsuario.agregaUsuario(nombre, apellido, nombreGrupo));
        
        assertEquals("El nombre no puede ser nulo o vacío", exception.getMessage());
    }
} 