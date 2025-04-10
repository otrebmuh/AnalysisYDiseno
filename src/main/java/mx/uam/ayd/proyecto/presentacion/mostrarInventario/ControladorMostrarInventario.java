package mx.uam.ayd.proyecto.presentacion.mostrarInventario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mx.uam.ayd.proyecto.negocio.ServicioInventario;
import mx.uam.ayd.proyecto.negocio.modelo.Inventario;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;
import mx.uam.ayd.proyecto.presentacion.solicitarReabastecimiento.ControladorSolicitarReabastecimiento;

@Component
public class ControladorMostrarInventario {

    @Autowired
    private ServicioInventario servicioInventario;
    
    @Autowired
    private VentanaMostrarInventario ventana;
    
    @Autowired
    private ControladorSolicitarReabastecimiento controladorSolicitarReabastecimiento;

    private Sucursal sucursal;

    public void inicia(Sucursal sucursal) {
        this.sucursal = sucursal;
        List<Inventario> inventario = servicioInventario.obtenerInventario(sucursal);
        ventana.actualizarTabla(inventario);
        ventana.muestra(this);
    }

    public void solicitarReabastecimiento() {
        controladorSolicitarReabastecimiento.inicia(sucursal);
    }
}