package mx.uam.ayd.proyecto.presentacion.listarInventario;

import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioInventario;
import mx.uam.ayd.proyecto.negocio.modelo.Inventario;

@Component
public class ControlListarInventario {

    private final ServicioInventario servicioInventario;

    private final VentanaListarInventario ventana;

    @Autowired
    public ControlListarInventario(ServicioInventario servicioInventario,
            VentanaListarInventario ventana) {

        this.servicioInventario = servicioInventario;
        this.ventana = ventana;
    }

    /**
     * Conecta la ventana con este controlador.
     */
    @PostConstruct
    public void init() {
        ventana.setControlListarInventario(this);
    }

    /**
     * Inicia la Historia de Usuario.
     */
    public void inicia() {

        List<Inventario> inventarios = servicioInventario.obtenerProductosBajoStock();

        ventana.muestra(inventarios);
    }
}
