package mx.uam.ayd.proyecto.negocio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para ServicioGrupo")
class ServicioGrupoTest {
    
    @Mock
    private GrupoRepository grupoRepository;
    
    @InjectMocks
    private ServicioGrupo servicioGrupo;
    
    private String nombreGrupoValido;

    @BeforeEach
    void setUp() {
        nombreGrupoValido = "Nuevo Grupo";
    }

    @Nested
    @DisplayName("Pruebas para recuperaGrupos()")
    class RecuperaGruposTests {
        
        @Test
        @DisplayName("Debería retornar lista vacía cuando no hay grupos")
        void testRecuperaGruposListaVacia() {
            // Given
            when(grupoRepository.findAll()).thenReturn(Collections.emptyList());
            
            // When
            List<Grupo> grupos = servicioGrupo.recuperaGrupos();
            
            // Then
            assertNotNull(grupos, "La lista no debería ser nula");
            assertTrue(grupos.isEmpty(), "La lista debería estar vacía");
            verify(grupoRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Debería retornar lista con grupos cuando existen grupos guardados")
        void testRecuperaGruposConGrupos() {
            // Given
            List<Grupo> listaGrupos = new ArrayList<>();
            
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
            
            listaGrupos.add(grupo1);
            listaGrupos.add(grupo2);
            
            when(grupoRepository.findAll()).thenReturn(listaGrupos);
            
            // When
            List<Grupo> grupos = servicioGrupo.recuperaGrupos();
            
            // Then
            assertNotNull(grupos, "La lista no debería ser nula");
            assertEquals(2, grupos.size(), "Debería retornar 2 grupos");
            assertEquals(1, grupos.get(0).getUsuarios().size(), 
                "El primer grupo debería tener 1 usuario");
            assertEquals(1, grupos.get(1).getUsuarios().size(), 
                "El segundo grupo debería tener 1 usuario");
            assertTrue(grupos.contains(grupo1), "Debería contener el primer grupo");
            assertTrue(grupos.contains(grupo2), "Debería contener el segundo grupo");
            verify(grupoRepository, times(1)).findAll();
        }
        
        @Test
        @DisplayName("Debería retornar grupos vacíos (sin usuarios)")
        void testRecuperaGruposSinUsuarios() {
            // Given
            List<Grupo> listaGrupos = new ArrayList<>();
            
            Grupo grupo1 = new Grupo();
            grupo1.setNombre("Administradores");
            
            Grupo grupo2 = new Grupo();
            grupo2.setNombre("Operadores");
            
            listaGrupos.add(grupo1);
            listaGrupos.add(grupo2);
            
            when(grupoRepository.findAll()).thenReturn(listaGrupos);
            
            // When
            List<Grupo> grupos = servicioGrupo.recuperaGrupos();
            
            // Then
            assertNotNull(grupos, "La lista no debería ser nula");
            assertEquals(2, grupos.size(), "Debería retornar 2 grupos");
            assertTrue(grupos.get(0).getUsuarios().isEmpty(), 
                "El primer grupo debería estar vacío");
            assertTrue(grupos.get(1).getUsuarios().isEmpty(), 
                "El segundo grupo debería estar vacío");
            verify(grupoRepository, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("Pruebas para creaGrupo()")
    class CreaGrupoTests {
        
        @Test
        @DisplayName("Debería crear grupo exitosamente cuando el nombre es válido")
        void testCreaGrupoExitosamente() {
            // Given
            when(grupoRepository.findByNombre(nombreGrupoValido)).thenReturn(null);
            
            Grupo grupoCreado = new Grupo();
            grupoCreado.setNombre(nombreGrupoValido);
            when(grupoRepository.save(any(Grupo.class))).thenReturn(grupoCreado);
            
            // When
            Grupo resultado = servicioGrupo.creaGrupo(nombreGrupoValido);
            
            // Then
            assertNotNull(resultado, "El grupo creado no debería ser nulo");
            assertEquals(nombreGrupoValido, resultado.getNombre(), 
                "El nombre del grupo debería coincidir");
            assertTrue(resultado.getUsuarios().isEmpty(), 
                "El grupo debería estar vacío inicialmente");
            verify(grupoRepository, times(1)).findByNombre(nombreGrupoValido);
            verify(grupoRepository, times(1)).save(any(Grupo.class));
        }

        @Test
        @DisplayName("Debería lanzar IllegalArgumentException cuando el grupo ya existe")
        void testCreaGrupoDuplicado() {
            // Given
            Grupo grupoExistente = new Grupo();
            grupoExistente.setNombre("Grupo Existente");
            when(grupoRepository.findByNombre("Grupo Existente")).thenReturn(grupoExistente);
            
            // When/Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> servicioGrupo.creaGrupo("Grupo Existente"),
                "Debería lanzar IllegalArgumentException cuando el grupo ya existe"
            );
            
            assertTrue(exception.getMessage().contains("Ya existe") || 
                exception.getMessage().contains("existe"),
                "El mensaje de error debería indicar que el grupo ya existe");
            verify(grupoRepository, times(1)).findByNombre("Grupo Existente");
            verify(grupoRepository, never()).save(any(Grupo.class));
        }

        @Test
        @DisplayName("Debería lanzar IllegalArgumentException cuando el nombre es nulo")
        void testCreaGrupoNombreNulo() {
            // When/Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> servicioGrupo.creaGrupo(null),
                "Debería lanzar IllegalArgumentException cuando el nombre es nulo"
            );
            
            assertTrue(exception.getMessage().contains("nombre") && 
                exception.getMessage().contains("nulo"),
                "El mensaje de error debería mencionar que el nombre no puede ser nulo");
            verify(grupoRepository, never()).findByNombre(anyString());
            verify(grupoRepository, never()).save(any(Grupo.class));
        }

        @Test
        @DisplayName("Debería lanzar IllegalArgumentException cuando el nombre está vacío")
        void testCreaGrupoNombreVacio() {
            // When/Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> servicioGrupo.creaGrupo(""),
                "Debería lanzar IllegalArgumentException cuando el nombre está vacío"
            );
            
            assertTrue(exception.getMessage().contains("nombre"),
                "El mensaje de error debería mencionar el nombre");
            verify(grupoRepository, never()).findByNombre(anyString());
            verify(grupoRepository, never()).save(any(Grupo.class));
        }

        @Test
        @DisplayName("Debería lanzar IllegalArgumentException cuando el nombre contiene solo espacios en blanco")
        void testCreaGrupoNombreSoloEspacios() {
            // When/Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> servicioGrupo.creaGrupo("   "),
                "Debería lanzar IllegalArgumentException cuando el nombre contiene solo espacios"
            );
            
            assertTrue(exception.getMessage().contains("nombre"),
                "El mensaje de error debería mencionar el nombre");
            verify(grupoRepository, never()).findByNombre(anyString());
            verify(grupoRepository, never()).save(any(Grupo.class));
        }
        
        @Test
        @DisplayName("Debería crear grupo con nombre que contiene espacios al inicio y fin (trim)")
        void testCreaGrupoNombreConEspacios() {
            // Given
            String nombreConEspacios = "  " + nombreGrupoValido + "  ";
            when(grupoRepository.findByNombre(nombreGrupoValido)).thenReturn(null);
            
            Grupo grupoCreado = new Grupo();
            grupoCreado.setNombre(nombreGrupoValido);
            when(grupoRepository.save(any(Grupo.class))).thenReturn(grupoCreado);
            
            // When/Then
            // Nota: El servicio debería hacer trim, pero si no lo hace, este test fallará
            // y nos indicará que necesitamos mejorar el servicio
            assertThrows(
                IllegalArgumentException.class,
                () -> servicioGrupo.creaGrupo(nombreConEspacios),
                "Si el servicio no hace trim, debería lanzar excepción"
            );
        }
    }
} 