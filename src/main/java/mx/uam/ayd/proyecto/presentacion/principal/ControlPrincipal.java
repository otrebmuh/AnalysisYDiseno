package mx.uam.ayd.proyecto.presentacion.principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.presentacion.agregarUsuario.ControlAgregarUsuario;
import mx.uam.ayd.proyecto.presentacion.listarUsuarios.ControlListarUsuarios;
import mx.uam.ayd.proyecto.presentacion.listarGrupos.ControlListarGrupos;

/**
 * Esta clase lleva el flujo de control de la ventana principal
 * 
 * @author humbertocervantes
 *
 */
@Component
public class ControlPrincipal {

	private final ControlAgregarUsuario controlAgregarUsuario;
	private final ControlListarUsuarios controlListarUsuarios;
	private final ControlListarGrupos controlListarGrupos;
	private final VentanaPrincipal ventana;
	
	@Autowired
	public ControlPrincipal(
			ControlAgregarUsuario controlAgregarUsuario,
			ControlListarUsuarios controlListarUsuarios,
			ControlListarGrupos controlListarGrupos,
			VentanaPrincipal ventana) {
		this.controlAgregarUsuario = controlAgregarUsuario;
		this.controlListarUsuarios = controlListarUsuarios;
		this.controlListarGrupos = controlListarGrupos;
		this.ventana = ventana;
	}
	
	/**
	 * Inicia el flujo de control de la ventana principal
	 * 
	 */
	public void inicia() {
		ventana.muestra(this);
	}

	/**
	 * Método que arranca la historia de usuario "agregar usuario"
	 * 
	 */
	public void agregarUsuario() {
		controlAgregarUsuario.inicia();
	}
	
	/**
	 * Método que arranca la historia de usuario "listar usuarios"
	 * 
	 */
	public void listarUsuarios() {
		controlListarUsuarios.inicia();
	}

	/**
	 * Método que arranca la historia de usuario "listar grupos"
	 * 
	 */
	public void listarGrupos() {
		controlListarGrupos.inicia();
	}
}
