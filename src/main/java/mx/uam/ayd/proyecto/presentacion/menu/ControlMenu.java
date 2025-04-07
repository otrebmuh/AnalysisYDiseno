package mx.uam.ayd.proyecto.presentacion.menu;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.datos.SucursalRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Empleado;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;
import mx.uam.ayd.proyecto.presentacion.gestionInventario.ControladorGestionInventario;

@Component
public class ControlMenu {

    @Autowired
    VentanaMenu ventanaMenu;
    @Autowired
    private ControladorGestionInventario controlGestionInventario;
    private Empleado empleado;
    @Autowired
    private SucursalRepository sucursalRepository;

    public void inicia() {
        ventanaMenu.muestra(this);
    }

    public void inicia(Empleado empleado) {
        this.empleado = empleado;
        ventanaMenu.muestra(this);
    }
    
    public void mostrarGestionInventario() {
        Sucursal sucursal = null;
        try {
            Optional<Sucursal> sucursalOpt = sucursalRepository.findByIdSucursal(1L);
            sucursal = sucursalOpt.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        controlGestionInventario.inicia(sucursal);
    }
}
