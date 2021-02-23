package mx.uam.ayd.proyecto.presentacion;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ControlAgregarUsuarioTest {

	@Autowired
	private ControlAgregarUsuario controlAgregarUsuario;
	
	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	
	@BeforeAll
	static public void prepare() {
			
	    System.setProperty("java.awt.headless", "false");
	    	    
	    
	}
	
	@BeforeEach
	public void setUp() {
			
	    System.setProperty("java.awt.headless", "false");
	    
	    Grupo ck51 = new Grupo();
	    ck51.setNombre("CK51");
	    
	    grupoRepository.save(ck51);
	    
	    
	}
	
	
	@Test
	public void testAgregaUsuario() { // agregaUsuario
		

		
		String nombre = "Juan";
		String apellido = "Perez";
		String grupo = "CK51";
		
		controlAgregarUsuario.agregaUsuario(nombre,apellido,grupo);
		
		Usuario usuario = usuarioRepository.findByNombreAndApellido(nombre,apellido);

		assertNotNull(usuario);

	}
}
