package mx.uam.ayd.proyecto.presentacion.loggin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.presentacion.menu.ControlMenu;
import mx.uam.ayd.proyecto.presentacion.menu.VentanaMenu;



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
		// TODO Auto-generated method stub
		//Tendria que ser la iniciazcion aqui pero por alguna razon no me deja
		controlMenu.inicia();

	}

}
