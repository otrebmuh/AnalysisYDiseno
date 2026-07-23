package mx.uam.ayd.proyecto.presentacion.registrarMercancia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import mx.uam.ayd.proyecto.negocio.ServicioProducto;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

@Component
public class ControlRegistrarMercancia {

    private final ServicioProducto servicioProducto;
    private final VentanaRegistrarMercancia ventana;

    @Autowired
    public ControlRegistrarMercancia(ServicioProducto servicioProducto,
            VentanaRegistrarMercancia ventana) {

        this.servicioProducto = servicioProducto;
        this.ventana = ventana;
    }

    @PostConstruct
    public void init() {
        ventana.setControlRegistrarMercancia(this);
    }

    /**
     * Inicia la ventana.
     */
    public void inicia() {
        ventana.muestra();
    }

    /**
     * Busca un producto por su clave.
     */
    public Producto buscarProducto(String clave) {
        return servicioProducto.buscaProductoPorClave(clave);
    }

    /**
     * Registra la entrada de mercancía para un prducto en especifico.
     */
    public boolean registrarMercancia(String clave, int cantidad) {
        return servicioProducto.registrarMercancia(clave, cantidad);
    }
}