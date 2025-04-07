package mx.uam.ayd.proyecto.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import mx.uam.ayd.proyecto.BaseIntegrationTest;
import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.datos.UsuarioRepository;
import mx.uam.ayd.proyecto.negocio.ServicioUsuario;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;
import mx.uam.ayd.proyecto.presentacion.agregarUsuario.ControlAgregarUsuario;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AgregarUsuarioIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ControlAgregarUsuario controlAgregarUsuario;

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
        try {
            servicioUsuario.agregaUsuario(nombre, apellido, nombreGrupo);
        } catch (IllegalArgumentException e) {
            assertEquals("No se encontró el grupo", e.getMessage());
        }
    }
} 