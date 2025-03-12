package mx.uam.ayd.proyecto.negocio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

@ExtendWith(MockitoExtension.class)
class ServicioGrupoTest {
    
    @Mock
    private GrupoRepository grupoRepository;
    
    @InjectMocks
    private ServicioGrupo servicioGrupo;

    @Test
    void testRecuperaGrupos() {
        // Caso 1: No hay grupos guardados, regresa lista vacía
        List<Grupo> grupos = servicioGrupo.recuperaGrupos();
        assertEquals(0, grupos.size());

        // Caso 2: Hay grupos guardados, regresa lista con grupos
        ArrayList<Grupo> lista = new ArrayList<>();

        Grupo grupo1 = new Grupo();
        grupo1.setNombre("Administradores");

        Grupo grupo2 = new Grupo();
        grupo2.setNombre("Operadores");

        // Agregar algunos usuarios a los grupos
        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Juan");
        usuario1.setApellido("Pérez");
        
        Usuario usuario2 = new Usuario();
        usuario2.setNombre("María");
        usuario2.setApellido("Ramírez");

        grupo1.addUsuario(usuario1);
        grupo2.addUsuario(usuario2);

        lista.add(grupo1);
        lista.add(grupo2);

        when(grupoRepository.findAll()).thenReturn(lista);

        grupos = servicioGrupo.recuperaGrupos();
        assertEquals(2, grupos.size());
        assertEquals(1, grupos.get(0).getUsuarios().size());
        assertEquals(1, grupos.get(1).getUsuarios().size());
    }

    @Test
    void testCreaGrupo() {
        // Caso 1: Crear grupo con nombre válido
        String nombreGrupo = "Nuevo Grupo";
        when(grupoRepository.findByNombre(nombreGrupo)).thenReturn(null);
        
        Grupo grupoCreado = new Grupo();
        grupoCreado.setNombre(nombreGrupo);
        when(grupoRepository.save(org.mockito.ArgumentMatchers.any(Grupo.class))).thenReturn(grupoCreado);

        Grupo resultado = servicioGrupo.creaGrupo(nombreGrupo);
        assertNotNull(resultado);
        assertEquals(nombreGrupo, resultado.getNombre());

        // Caso 2: Intentar crear grupo con nombre que ya existe
        Grupo grupoExistente = new Grupo();
        grupoExistente.setNombre("Grupo Existente");
        when(grupoRepository.findByNombre("Grupo Existente")).thenReturn(grupoExistente);

        assertThrows(IllegalArgumentException.class, () -> {
            servicioGrupo.creaGrupo("Grupo Existente");
        });

        // Caso 3: Intentar crear grupo con nombre nulo
        assertThrows(IllegalArgumentException.class, () -> {
            servicioGrupo.creaGrupo(null);
        });

        // Caso 4: Intentar crear grupo con nombre vacío
        assertThrows(IllegalArgumentException.class, () -> {
            servicioGrupo.creaGrupo("");
        });
    }
} 