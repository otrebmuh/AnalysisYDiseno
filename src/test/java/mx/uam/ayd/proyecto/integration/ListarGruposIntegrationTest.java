package mx.uam.ayd.proyecto.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import mx.uam.ayd.proyecto.BaseIntegrationTest;
import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.negocio.ServicioGrupo;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;
import mx.uam.ayd.proyecto.presentacion.listarGrupos.ControlListarGrupos;

//@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ListarGruposIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ControlListarGrupos controlListarGrupos;

    @Autowired
    private ServicioGrupo servicioGrupo;

    @Autowired
    private GrupoRepository grupoRepository;

    //@Test
    @Transactional
    public void testListarGrupos() {
        // Given
        String nombreGrupo1 = "Test Group 1";
        String nombreGrupo2 = "Test Group 2";

        // Create test groups
        Grupo grupo1 = new Grupo();
        grupo1.setNombre(nombreGrupo1);
        grupoRepository.save(grupo1);

        Grupo grupo2 = new Grupo();
        grupo2.setNombre(nombreGrupo2);
        grupoRepository.save(grupo2);

        // Add some users to groups
        Usuario usuario1 = new Usuario();
        usuario1.setNombre("User1");
        usuario1.setApellido("Test");
        grupo1.addUsuario(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("User2");
        usuario2.setApellido("Test");
        grupo2.addUsuario(usuario2);

        grupoRepository.save(grupo1);
        grupoRepository.save(grupo2);

        // When
        List<Grupo> grupos = servicioGrupo.recuperaGrupos();

        // Then
        assertNotNull(grupos);
        assertEquals(2, grupos.size());

        // Verify group 1
        Grupo savedGrupo1 = grupos.stream()
            .filter(g -> g.getNombre().equals(nombreGrupo1))
            .findFirst()
            .orElse(null);
        assertNotNull(savedGrupo1);
        assertEquals(1, savedGrupo1.getUsuarios().size());
        assertTrue(savedGrupo1.getUsuarios().stream()
            .anyMatch(u -> u.getNombre().equals("User1")));

        // Verify group 2
        Grupo savedGrupo2 = grupos.stream()
            .filter(g -> g.getNombre().equals(nombreGrupo2))
            .findFirst()
            .orElse(null);
        assertNotNull(savedGrupo2);
        assertEquals(1, savedGrupo2.getUsuarios().size());
        assertTrue(savedGrupo2.getUsuarios().stream()
            .anyMatch(u -> u.getNombre().equals("User2")));
    }
} 