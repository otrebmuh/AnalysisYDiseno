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
import mx.uam.ayd.proyecto.datos.UsuarioRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para ServicioUsuario")
class ServicioUsuarioTest {
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Mock
	private GrupoRepository grupoRepository;
	
	@InjectMocks
	private ServicioUsuario servicioUsuario;
	
	private Grupo grupoPrueba;
	private Usuario usuarioPrueba;
	private String nombreValido;
	private String apellidoValido;
	private String nombreGrupoValido;

	@BeforeEach
	void setUp() {
		nombreValido = "Juan";
		apellidoValido = "Pérez";
		nombreGrupoValido = "Administradores";
		
		grupoPrueba = new Grupo();
		grupoPrueba.setNombre(nombreGrupoValido);
		
		usuarioPrueba = new Usuario();
		usuarioPrueba.setNombre(nombreValido);
		usuarioPrueba.setApellido(apellidoValido);
	}

	@Nested
	@DisplayName("Pruebas para recuperaUsuarios()")
	class RecuperaUsuariosTests {
		
		@Test
		@DisplayName("Debería retornar lista vacía cuando no hay usuarios")
		void testRecuperaUsuariosListaVacia() {
			// Given
			when(usuarioRepository.findAll()).thenReturn(Collections.emptyList());
			
			// When
			List<Usuario> usuarios = servicioUsuario.recuperaUsuarios();
			
			// Then
			assertNotNull(usuarios, "La lista no debería ser nula");
			assertTrue(usuarios.isEmpty(), "La lista debería estar vacía");
			verify(usuarioRepository, times(1)).findAll();
		}

		@Test
		@DisplayName("Debería retornar lista con usuarios cuando existen usuarios guardados")
		void testRecuperaUsuariosConUsuarios() {
			// Given
			List<Usuario> listaUsuarios = new ArrayList<>();
			Usuario usuario1 = new Usuario();
			usuario1.setNombre("Juan");
			usuario1.setApellido("Perez");
			
			Usuario usuario2 = new Usuario();
			usuario2.setNombre("María");
			usuario2.setApellido("Ramírez");
			
			listaUsuarios.add(usuario1);
			listaUsuarios.add(usuario2);
			
			when(usuarioRepository.findAll()).thenReturn(listaUsuarios);
			
			// When
			List<Usuario> usuarios = servicioUsuario.recuperaUsuarios();
			
			// Then
			assertNotNull(usuarios, "La lista no debería ser nula");
			assertEquals(2, usuarios.size(), "Debería retornar 2 usuarios");
			assertTrue(usuarios.contains(usuario1), "Debería contener el primer usuario");
			assertTrue(usuarios.contains(usuario2), "Debería contener el segundo usuario");
			verify(usuarioRepository, times(1)).findAll();
		}
	}

	@Nested
	@DisplayName("Pruebas para agregaUsuario()")
	class AgregaUsuarioTests {
		
		@Test
		@DisplayName("Debería agregar usuario exitosamente cuando todos los datos son válidos")
		void testAgregaUsuarioExitosamente() {
			// Given
			when(usuarioRepository.findByNombreAndApellido(nombreValido, apellidoValido))
				.thenReturn(null);
			when(grupoRepository.findByNombre(nombreGrupoValido))
				.thenReturn(grupoPrueba);
			when(grupoRepository.save(any(Grupo.class)))
				.thenReturn(grupoPrueba);
			
			// When
			Usuario resultado = servicioUsuario.agregaUsuario(nombreValido, apellidoValido, nombreGrupoValido);
			
			// Then
			assertNotNull(resultado, "El usuario creado no debería ser nulo");
			assertEquals(nombreValido, resultado.getNombre(), "El nombre debería coincidir");
			assertEquals(apellidoValido, resultado.getApellido(), "El apellido debería coincidir");
			verify(usuarioRepository, times(1)).findByNombreAndApellido(nombreValido, apellidoValido);
			verify(grupoRepository, times(1)).findByNombre(nombreGrupoValido);
			verify(grupoRepository, times(1)).save(any(Grupo.class));
		}

		@Test
		@DisplayName("Debería lanzar IllegalArgumentException cuando el usuario ya existe")
		void testAgregaUsuarioDuplicado() {
			// Given
			Usuario usuarioExistente = new Usuario();
			usuarioExistente.setNombre("María");
			usuarioExistente.setApellido("Ramírez");
			
			when(usuarioRepository.findByNombreAndApellido("María", "Ramírez"))
				.thenReturn(usuarioExistente);
			
			// When/Then
			IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> servicioUsuario.agregaUsuario("María", "Ramírez", nombreGrupoValido),
				"Debería lanzar IllegalArgumentException cuando el usuario ya existe"
			);
			
			assertTrue(exception.getMessage().contains("ya existe"), 
				"El mensaje de error debería indicar que el usuario ya existe");
			verify(usuarioRepository, times(1)).findByNombreAndApellido("María", "Ramírez");
			verify(grupoRepository, never()).findByNombre(anyString());
			verify(grupoRepository, never()).save(any(Grupo.class));
		}

		@Test
		@DisplayName("Debería lanzar IllegalArgumentException cuando el grupo no existe")
		void testAgregaUsuarioGrupoInexistente() {
			// Given
			when(usuarioRepository.findByNombreAndApellido("Pedro", "López"))
				.thenReturn(null);
			when(grupoRepository.findByNombre("Grupo Inexistente"))
				.thenReturn(null);
			
			// When/Then
			IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> servicioUsuario.agregaUsuario("Pedro", "López", "Grupo Inexistente"),
				"Debería lanzar IllegalArgumentException cuando el grupo no existe"
			);
			
			assertTrue(exception.getMessage().contains("No se encontró el grupo") || 
				exception.getMessage().contains("grupo"),
				"El mensaje de error debería indicar que el grupo no existe");
			verify(usuarioRepository, times(1)).findByNombreAndApellido("Pedro", "López");
			verify(grupoRepository, times(1)).findByNombre("Grupo Inexistente");
			verify(grupoRepository, never()).save(any(Grupo.class));
		}

		@Test
		@DisplayName("Debería lanzar IllegalArgumentException cuando el nombre es nulo")
		void testAgregaUsuarioNombreNulo() {
			// When/Then
			IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> servicioUsuario.agregaUsuario(null, apellidoValido, nombreGrupoValido),
				"Debería lanzar IllegalArgumentException cuando el nombre es nulo"
			);
			
			assertTrue(exception.getMessage().contains("nombre") && 
				exception.getMessage().contains("nulo"),
				"El mensaje de error debería mencionar que el nombre no puede ser nulo");
			verify(usuarioRepository, never()).findByNombreAndApellido(anyString(), anyString());
		}

		@Test
		@DisplayName("Debería lanzar IllegalArgumentException cuando el apellido es nulo")
		void testAgregaUsuarioApellidoNulo() {
			// When/Then
			IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> servicioUsuario.agregaUsuario(nombreValido, null, nombreGrupoValido),
				"Debería lanzar IllegalArgumentException cuando el apellido es nulo"
			);
			
			assertTrue(exception.getMessage().contains("apellido") && 
				exception.getMessage().contains("nulo"),
				"El mensaje de error debería mencionar que el apellido no puede ser nulo");
			verify(usuarioRepository, never()).findByNombreAndApellido(anyString(), anyString());
		}

		@Test
		@DisplayName("Debería lanzar IllegalArgumentException cuando el nombre del grupo es nulo")
		void testAgregaUsuarioNombreGrupoNulo() {
			// When/Then
			IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> servicioUsuario.agregaUsuario(nombreValido, apellidoValido, null),
				"Debería lanzar IllegalArgumentException cuando el nombre del grupo es nulo"
			);
			
			assertTrue(exception.getMessage().contains("grupo") && 
				exception.getMessage().contains("nulo"),
				"El mensaje de error debería mencionar que el nombre del grupo no puede ser nulo");
			verify(usuarioRepository, never()).findByNombreAndApellido(anyString(), anyString());
		}

		@Test
		@DisplayName("Debería lanzar IllegalArgumentException cuando el nombre está vacío")
		void testAgregaUsuarioNombreVacio() {
			// When/Then
			IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> servicioUsuario.agregaUsuario("", apellidoValido, nombreGrupoValido),
				"Debería lanzar IllegalArgumentException cuando el nombre está vacío"
			);
			
			assertTrue(exception.getMessage().contains("nombre"),
				"El mensaje de error debería mencionar el nombre");
			verify(usuarioRepository, never()).findByNombreAndApellido(anyString(), anyString());
		}

		@Test
		@DisplayName("Debería lanzar IllegalArgumentException cuando el apellido está vacío")
		void testAgregaUsuarioApellidoVacio() {
			// When/Then
			IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> servicioUsuario.agregaUsuario(nombreValido, "", nombreGrupoValido),
				"Debería lanzar IllegalArgumentException cuando el apellido está vacío"
			);
			
			assertTrue(exception.getMessage().contains("apellido"),
				"El mensaje de error debería mencionar el apellido");
			verify(usuarioRepository, never()).findByNombreAndApellido(anyString(), anyString());
		}

		@Test
		@DisplayName("Debería lanzar IllegalArgumentException cuando el nombre del grupo está vacío")
		void testAgregaUsuarioNombreGrupoVacio() {
			// When/Then
			IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> servicioUsuario.agregaUsuario(nombreValido, apellidoValido, ""),
				"Debería lanzar IllegalArgumentException cuando el nombre del grupo está vacío"
			);
			
			assertTrue(exception.getMessage().contains("grupo"),
				"El mensaje de error debería mencionar el grupo");
			verify(usuarioRepository, never()).findByNombreAndApellido(anyString(), anyString());
		}

		@Test
		@DisplayName("Debería lanzar IllegalArgumentException cuando el nombre contiene solo espacios en blanco")
		void testAgregaUsuarioNombreSoloEspacios() {
			// When/Then
			IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> servicioUsuario.agregaUsuario("   ", apellidoValido, nombreGrupoValido),
				"Debería lanzar IllegalArgumentException cuando el nombre contiene solo espacios"
			);
			
			assertTrue(exception.getMessage().contains("nombre"),
				"El mensaje de error debería mencionar el nombre");
			verify(usuarioRepository, never()).findByNombreAndApellido(anyString(), anyString());
		}

		@Test
		@DisplayName("Debería lanzar IllegalArgumentException cuando el apellido contiene solo espacios en blanco")
		void testAgregaUsuarioApellidoSoloEspacios() {
			// When/Then
			IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> servicioUsuario.agregaUsuario(nombreValido, "   ", nombreGrupoValido),
				"Debería lanzar IllegalArgumentException cuando el apellido contiene solo espacios"
			);
			
			assertTrue(exception.getMessage().contains("apellido"),
				"El mensaje de error debería mencionar el apellido");
			verify(usuarioRepository, never()).findByNombreAndApellido(anyString(), anyString());
		}

		@Test
		@DisplayName("Debería lanzar IllegalArgumentException cuando el nombre del grupo contiene solo espacios en blanco")
		void testAgregaUsuarioNombreGrupoSoloEspacios() {
			// When/Then
			IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> servicioUsuario.agregaUsuario(nombreValido, apellidoValido, "   "),
				"Debería lanzar IllegalArgumentException cuando el nombre del grupo contiene solo espacios"
			);
			
			assertTrue(exception.getMessage().contains("grupo"),
				"El mensaje de error debería mencionar el grupo");
			verify(usuarioRepository, never()).findByNombreAndApellido(anyString(), anyString());
		}
	}
}
