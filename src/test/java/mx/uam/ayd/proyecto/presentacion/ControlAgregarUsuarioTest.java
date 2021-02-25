package mx.uam.ayd.proyecto.presentacion;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.datos.UsuarioRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;
import mx.uam.ayd.proyecto.presentacion.agregarUsuario.ControlAgregarUsuario;

/**
 * 
 * Prueba de integración
 * 
 * @author humbertocervantes
 *
 */
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ControlAgregarUsuarioTest {

	@Autowired
	private ControlAgregarUsuario controlAgregarUsuario; // Este es el elemento que voy a probar
	
	// Los siguientes son únicamente para poder acceder a la BD
	
	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	
	/**
	 * Este método solo se ejecuta una vez antes de todos los
	 * casos de prueba
	 * 
	 */
	@BeforeAll
	static public void prepare() {
			
		// Esto es necesario para evitar la excepción al arrancar la prueba
	    System.setProperty("java.awt.headless", "false");
	    	    
	    
	}
	
	@BeforeEach
	public void setUp() {
			
		// Creamos un grupo y lo agregamos a la BD
	    Grupo administradores = new Grupo();
	    administradores.setNombre("Prueba");
	    
	    grupoRepository.save(administradores);
	    
	    
	}
	

	@AfterEach
	public void tearDown() {
			
		// Limpiamos la BD
	    grupoRepository.deleteAll();
	    
	    
	}

	
	@Test
	public void testAgregaUsuario() { // agregaUsuario
		
		String nombre = "Juan";
		String apellido = "Perez";
		String grupo = "Prueba";
		
		// No ponemos assert aquí por que el control es de tipo void
		controlAgregarUsuario.agregaUsuario(nombre,apellido,grupo);
		
		// Recuperamos el usuario de la BD
		Usuario usuario = usuarioRepository.findByNombreAndApellido(nombre,apellido);

		assertNotNull(usuario);

	}
}
