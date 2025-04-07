package mx.uam.ayd.proyecto.negocio.modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

class GrupoTest {

	private Grupo grupoPrueba;
	private Usuario usuarioPrueba;

	@BeforeEach
	void setUp() {
		// Initialize test objects
		grupoPrueba = new Grupo();
		grupoPrueba.setNombre("CK01");
		
		usuarioPrueba = new Usuario();
		usuarioPrueba.setNombre("Juan");
		usuarioPrueba.setApellido("Perez");
		usuarioPrueba.setEdad(20);
	}
	
	@Test
	@DisplayName("Debería agregar un usuario exitosamente al grupo")
	void testAgregarUsuarioExitosamente() {
		// When
		boolean resultado = grupoPrueba.addUsuario(usuarioPrueba);
		
		// Then
		assertTrue(resultado, "Debería poder agregar un usuario al grupo");
		assertTrue(grupoPrueba.getUsuarios().contains(usuarioPrueba), 
			"El usuario debería estar en la lista de usuarios del grupo");
	}
	
	@Test
	@DisplayName("No debería permitir agregar el mismo usuario dos veces")
	void testNoAgregarUsuarioDuplicado() {
		// Given
		grupoPrueba.addUsuario(usuarioPrueba);
		
		// When
		boolean resultado = grupoPrueba.addUsuario(usuarioPrueba);
		
		// Then
		assertFalse(resultado, "No debería permitir agregar un usuario duplicado");
		assertEquals(1, grupoPrueba.getUsuarios().size(), 
			"El grupo debería tener solo una instancia del usuario");
	}
	
	@Test
	@DisplayName("Debería lanzar IllegalArgumentException al agregar usuario nulo")
	void testAgregarUsuarioNulo() {
		// When/Then
		assertThrows(IllegalArgumentException.class, 
			() -> grupoPrueba.addUsuario(null),
			"Debería lanzar IllegalArgumentException al agregar usuario nulo");
	}
	
	@Test
	@DisplayName("Debería remover un usuario existente del grupo")
	void testRemoverUsuarioExistente() {
		// Given
		grupoPrueba.addUsuario(usuarioPrueba);
		
		// When
		boolean resultado = grupoPrueba.removeUsuario(usuarioPrueba);
		
		// Then
		assertTrue(resultado, "Debería poder remover un usuario existente");
		assertFalse(grupoPrueba.getUsuarios().contains(usuarioPrueba), 
			"El usuario no debería estar en la lista de usuarios del grupo");
	}
	
	@Test
	@DisplayName("No debería remover un usuario que no existe en el grupo")
	void testRemoverUsuarioInexistente() {
		// When
		boolean resultado = grupoPrueba.removeUsuario(usuarioPrueba);
		
		// Then
		assertFalse(resultado, "No debería poder remover un usuario que no existe");
		assertTrue(grupoPrueba.getUsuarios().isEmpty(), 
			"El grupo debería estar vacío");
	}
	
	@Test
	@DisplayName("Debería lanzar IllegalArgumentException al remover usuario nulo")
	void testRemoverUsuarioNulo() {
		// When/Then
		assertThrows(IllegalArgumentException.class, 
			() -> grupoPrueba.removeUsuario(null),
			"Debería lanzar IllegalArgumentException al remover usuario nulo");
	}
}
