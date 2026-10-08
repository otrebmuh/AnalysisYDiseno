package mx.uam.ayd.proyecto.negocio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;

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
    public void testLanzaExcepcionCuandoNombreEsNulo() {
        // Given
        // No hay precondiciones

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> servicioGrupo.creaGrupo(null));

    }


    @Test
    public void testCreaGrupoCorrectamente() {
        // Given
        when(grupoRepository.save(any(Grupo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Grupo grupo = servicioGrupo.creaGrupo("Grupo de prueba");
    
        // Then
        assertNotNull(grupo);
    }

    @Test
    public void testFallaCrearGrupoYaHayUnoExistente() {
        // Given
        Grupo grupoExistente = new Grupo();
        grupoExistente.setNombre("Prueba");

        when(grupoRepository.findByNombre("Prueba")).thenReturn(grupoExistente);

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> servicioGrupo.creaGrupo("Prueba"));

    }

} 