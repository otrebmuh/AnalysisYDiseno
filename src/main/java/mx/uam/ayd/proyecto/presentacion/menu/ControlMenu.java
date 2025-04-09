package mx.uam.ayd.proyecto.presentacion.menu;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.datos.SucursalRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Empleado;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;
import mx.uam.ayd.proyecto.presentacion.gestionInventario.ControladorGestionInventario;
import mx.uam.ayd.proyecto.presentacion.loggin.ControlLoggin;
import mx.uam.ayd.proyecto.presentacion.mostrarInventario.ControladorMostrarInventario;
import mx.uam.ayd.proyecto.presentacion.visualizarSolicitudesAbastecimiento.ControlVisualizarSolicitudesAbastecimiento;

@Component
public class ControlMenu {

    @Autowired
    VentanaMenu ventanaMenu;
    @Autowired
    private ControladorGestionInventario controlGestionInventario;
    @Autowired
    private ControladorMostrarInventario controladorMostrarInventario;  
    @Autowired
    private ControlVisualizarSolicitudesAbastecimiento controlSolicitudesAbastecimiento;

    private ControlLoggin controlLoggin;

    Sucursal sucursal;
    Empleado empleado;
    Usuario usuario;
    
    public void inicia(ControlLoggin controlLoggin) {
        Logger.getGlobal().info("Recibiendo controlLogin");
        this.controlLoggin = controlLoggin;
        ventanaMenu.muestra(this);
    }

    public void inicia(ControlLoggin controlLoggin, Usuario usuario) {
        this.controlLoggin = controlLoggin;
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
    
    /**
     * Método que arranca la historia de usuario "visualizar solicitudes de abastecimiento"
     */
    public void mostrarSolicitudesAbastecimiento() {
        controlSolicitudesAbastecimiento.inicia();
    }

    public void cerrarSesion() {
        usuario = null;
        empleado = null;
        sucursal = null;
        controlLoggin.inicia();
        ventanaMenu.dispose();
    }
}
