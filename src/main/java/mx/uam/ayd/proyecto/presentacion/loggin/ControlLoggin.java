package mx.uam.ayd.proyecto.presentacion.loggin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



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

}
