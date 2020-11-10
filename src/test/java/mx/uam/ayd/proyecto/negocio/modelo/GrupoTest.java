package mx.uam.ayd.proyecto.negocio.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GrupoTest {

	private Usuario usuario;
	
	@BeforeEach
	void setUp() throws Exception {
		// Este método se ejecuta antes de la ejecución
		// de cada metodo de prueba, es útil para
		// establecer las precondiciones
		
		usuario = new Usuario();
		usuario.setNombre("Juan");
		usuario.setApellido("Perez");
		usuario.setEdad(20);

	}

	@AfterEach
	void tearDown() throws Exception {
		// Este método se ejecuta después de la ejecución
		// de cada método de prueba, es útil para
		// dejar todo como estaba antes de la prueba
	}

	@Test
	void testAddUsuario() {
		
		Grupo grupo = new Grupo();
		grupo.setNombre("CK01");
		
		// Prueba 1: Corroborar que addUsuario funciona correctamente si no hay usuarios en el grupo
		boolean resultado = grupo.addUsuario(usuario);
		
		assertEquals(true,resultado);
		
		// Prueba 2: Corroborar que no es posible agregar un mismo usuario dos veces al grupo
		resultado = grupo.addUsuario(usuario);

		assertEquals(false,resultado);
		
		// Prueba 3: Corroborar que no se puede pasar null como parámetro
		Assertions.assertThrows(IllegalArgumentException.class, () -> {

			grupo.addUsuario(null);


		});
		
		

		
	}

}
