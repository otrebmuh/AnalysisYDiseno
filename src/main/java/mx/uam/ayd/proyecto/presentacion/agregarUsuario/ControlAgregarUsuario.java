package mx.uam.ayd.proyecto.presentacion.agregarUsuario;

import java.util.List;

import jakarta.annotation.PostConstruct;
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
	
	private final ServicioUsuario servicioUsuario;
	private final ServicioGrupo servicioGrupo;
	private final VentanaAgregarUsuario ventana;
	
	@Autowired
	public ControlAgregarUsuario(
			ServicioUsuario servicioUsuario,
			ServicioGrupo servicioGrupo,
			VentanaAgregarUsuario ventana) {
		this.servicioUsuario = servicioUsuario;
		this.servicioGrupo = servicioGrupo;
		this.ventana = ventana;
	}
	
	/**
	 * Método que se ejecuta después de la construcción del bean
	 * y realiza la conexión bidireccional entre el control y la ventana
	 */
	@PostConstruct
	public void init() {
		ventana.setControlAgregarUsuario(this);
	}
	
	/**
	 * Inicia la historia de usuario
	 * 
	 */
	public void inicia() {
		List <Grupo> grupos = servicioGrupo.recuperaGrupos();
		ventana.muestra(grupos);
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
