package mx.uam.ayd.proyecto.presentacion.menu;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.datos.SucursalRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Empleado;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;
import mx.uam.ayd.proyecto.presentacion.gestionInventario.ControladorGestionInventario;

import mx.uam.ayd.proyecto.presentacion.mostrarInventario.ControladorMostrarInventario;

@Component
public class ControlMenu {

    @Autowired
    VentanaMenu ventanaMenu;
    @Autowired
    private ControladorGestionInventario controlGestionInventario;
    @Autowired
    ControladorMostrarInventario controladorMostrarInventario;  

    Sucursal sucursal;
    Empleado empleado;
    Usuario usuario;
    
    public void inicia() {
        ventanaMenu.muestra(this);
    }

    public void inicia(Usuario usuario) {
        this.usuario = usuario;
        this.empleado = usuario.getEmpleado();
        this.sucursal = empleado.getSucursal();
        ventanaMenu.muestra(this);
    }
    
    public void mostrarGestionInventario() {
        controlGestionInventario.inicia(sucursal);
    }
    
    public void mostrarMostrarInventario() {
        controladorMostrarInventario.inicia(sucursal);
    }
}
