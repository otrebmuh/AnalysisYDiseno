package mx.uam.ayd.proyecto.presentacion.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.datos.SucursalRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Empleado;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;
import mx.uam.ayd.proyecto.presentacion.mostrarInventario.ControladorMostrarInventario;

@Component
public class ControlMenu {

    @Autowired
    VentanaMenu ventanaMenu;

    @Autowired
    ControladorMostrarInventario controladorMostrarInventario;  

    Sucursal sucursal;
    Empleado empleado;
    
    @Autowired
    private SucursalRepository sucursalRepository;

    public void inicia() {
        ventanaMenu.muestra(this);
    }


    public void mostrarMostrarInventario() {
        if(empleado == null) {
            sucursal = sucursalRepository.findByIdSucursal(1L).orElse(null);
        }
        controladorMostrarInventario.inicia(sucursal);
    }
    
}
