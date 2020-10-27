package mx.uam.ayd.proyecto.presentacion.agregarUsuario;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioGrupo;
import mx.uam.ayd.proyecto.negocio.ServicioUsuario;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;

/**
 * 
 * Módulo de control para la historia de usuario AgregarUsuario
 * 
 * @author humbertocervantes
 *
 */
@Component
public class ControlAgregarUsuario {
	
	@Autowired
	private ServicioUsuario servicioUsuario;
	
	@Autowired
	private ServicioGrupo servicioGrupo;
	
	@Autowired
	private VentanaAgregarUsuario ventana;
	
	/**
	 * Inicia la historia de usuario
	 * 
	 */
	public void inicia() {
		
		List <Grupo> grupos = servicioGrupo.recuperaGrupos();
		
		ventana.muestra(this, grupos);
		
	}

	public void agregaUsuario(String nombre, String apellido, String grupo) {

		try {
			servicioUsuario.agregaUsuario(nombre, apellido, grupo);
			ventana.muestraDialogoConMensaje("Usuario agregado exitosamente");
		} catch(Exception ex) {
			ventana.muestraDialogoConMensaje("Error al agregar usuario: "+ex.getMessage());
		}
		
		termina();
		
	}
	
	/**
	 * Termina la historia de usuario
	 * 
	 */
	public void termina() {
		ventana.setVisible(false);		
	}

}
