package mx.uam.ayd.proyecto.negocio.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GrupoTest {

	private Grupo grupoPrueba;

	@BeforeEach
	void setUp() {
		// Este método se ejecuta antes de que se ejecute cada
		// método de prueba
		
		grupoPrueba = new Grupo();
	}
	


	
	@Test
	void testAddUsuario() {
		
		// Caso de prueba 1: Se agrega un usuario exitosamente al grupo
		grupoPrueba.setNombre("CK01");
		
		Usuario usuarioPrueba = new Usuario();
		usuarioPrueba.setNombre("Juan");
		usuarioPrueba.setApellido("Perez");
		usuarioPrueba.setEdad(20);
		
		boolean resultado = grupoPrueba.addUsuario(usuarioPrueba);
		
			 
		assertEquals(true,resultado); // Valor esperado, valor obtenido
		
		// Caso de prueba 2: Se trata de agregar un usuario que ya estaba en el grupo
		
		// Nota: aquí grupoPrueba ya tiene al usuarioPrueba
		
		resultado = grupoPrueba.addUsuario(usuarioPrueba);
		
		assertEquals(false,resultado,"no se puede agregar dos veces un usuario al grupo"); // Valor esperado, valor obtenido
		
		
		// Caso de prueba 3: Se le pasa un null como parámetro
		
		// Checamos que genere un IllegalArgumentException
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			grupoPrueba.addUsuario(null);
		});
	}
	
	@Test
	void testRemoveUsuario() {
				
		// Caso de prueba 1
		
		// Caso de prueba 2
		
		// Caso de prueba n
		
		
	}

}
