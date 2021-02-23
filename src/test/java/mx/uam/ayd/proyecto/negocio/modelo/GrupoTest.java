package mx.uam.ayd.proyecto.negocio.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * Prueba unitaria de la entidad Grupo
 * 
 * @author humbertocervantes
 *
 */
class GrupoTest {

	private Grupo grupo = new Grupo(); // Este es el elemento que probamos

	
	@BeforeEach
	void setUp() throws Exception {
		// Este método se ejecuta antes de la ejecución
		// de cada metodo de prueba, es útil para
		// establecer las precondiciones generales
		
		grupo.setNombre("CK01"); // Esto también es una precondición

		

	}

	@AfterEach
	void tearDown() throws Exception {
		// Este método se ejecuta después de la ejecución
		// de cada método de prueba, es útil para
		// dejar todo como estaba antes de la prueba
	}

	@Test
	void testAddUsuario() {
		
		// Precondiciones específicas para este caso de prueba
		Usuario usuario;
		usuario = new Usuario();
		usuario.setNombre("Juan");
		usuario.setApellido("Perez");
		usuario.setEdad(20);

		
		
		// Caso de prueba 1: Corroborar que addUsuario funciona correctamente si no hay usuarios en el grupo
		boolean resultado = grupo.addUsuario(usuario);
		
		assertEquals(true,resultado); // assert permite corroborar que el valor que regresó es el que espero
		
		// Caso de prueba 2: Corroborar que no es posible agregar un mismo usuario dos veces al grupo
		resultado = grupo.addUsuario(usuario);

		assertEquals(false,resultado);
		
		// Caso de Prueba 3: Corroborar que no se puede pasar null como parámetro
		Assertions.assertThrows(IllegalArgumentException.class, () -> {

			grupo.addUsuario(null);


		});
		
	}
	

}
