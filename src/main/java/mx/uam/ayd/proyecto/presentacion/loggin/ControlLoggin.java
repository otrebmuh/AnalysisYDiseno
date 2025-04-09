package mx.uam.ayd.proyecto.presentacion.loggin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioUsuario;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;
import mx.uam.ayd.proyecto.presentacion.menu.ControlMenu;

/**
 * Esta clase lleva el flujo de control de la ventana loggin 
 * 
 * @author humbertocervantes
 *
 */
@Component
public class ControlLoggin {

	@Autowired
	private VentanaLoggin ventanaLoggin;
	
	@Autowired
	private ControlMenu controlMenu;

	@Autowired
	private ServicioUsuario servicioUsuario;
	
	/**
	 * Inicia el flujo de control de la ventana principal
	 * 
	 */
	public void inicia() {
//		VentanaLoggin ventanaLoggin = new VentanaLoggin();
//		ventanaLoggin.Vista();
//		ventanaLoggin.setVisible(true);
		ventanaLoggin.muestra(this);
	}

	public void iniciaMenu() {
		controlMenu.inicia();
		ventanaLoggin.dispose();
	}

	public void login(String nombre, String contrasena) {
		Usuario usuario = servicioUsuario.login(nombre, contrasena);
		
		if(usuario != null) {
			controlMenu.inicia(usuario);
			ventanaLoggin.dispose();
		} else {
			ventanaLoggin.muestraDialogoConMensaje("Usuario o contrasena incorrectos");
		}
	}

}
