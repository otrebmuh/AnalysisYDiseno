package mx.uam.ayd.proyecto.presentacion.gestionProductos;

import org.springframework.beans.factory.annotation.Autowired;

public class ControlGestionProductos {

    @Autowired
    private VentanaGestionProductos ventanaGestionProductos;

    public void inicia() {
        ventanaGestionProductos.muestra();
    }
    
}
