package mx.uam.ayd.proyecto.presentacion.devolucionProducto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioDevolucion;
import mx.uam.ayd.proyecto.negocio.ServicioProducto;
import mx.uam.ayd.proyecto.negocio.modelo.Devolucion;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

/**
 * Controlador de presentación para coordinar el flujo de la HU-10.
 *
 * @author Yamelin Larios Nepomuseno
 */
@Component
public class ControlDevolucionProducto {

    private static final Logger log = LoggerFactory.getLogger(ControlDevolucionProducto.class);

    private final ServicioDevolucion servicioDevolucion;
    private final ServicioProducto servicioProducto;
    private final VentanaDevolucionProducto ventana;

    @Autowired
    public ControlDevolucionProducto(ServicioDevolucion servicioDevolucion, 
                                     ServicioProducto servicioProducto, 
                                     VentanaDevolucionProducto ventana) {
        this.servicioDevolucion = servicioDevolucion;
        this.servicioProducto = servicioProducto;
        this.ventana = ventana;
    }

    /**
     * Inicia la historia de usuario mostrando la ventana.
     */
    public void inicia() {
        log.info("Iniciando flujo de HU-10: Devolución de productos dañados");
        ventana.setControl(this);
        ventana.muestra();
    }

    /**
     * Busca la información de un producto para previsualizarlo en la vista.
     */
    public void buscarProducto(long idProducto) {
        try {
            Producto producto = servicioProducto.buscarProductoPorId(idProducto);
            ventana.muestraProductoEncontrado(producto);
        } catch (Exception e) {
            log.error("Error al buscar producto por ID: {}", idProducto, e);
            ventana.muestraError("Error al consultar la base de datos.");
        }
    }

    /**
     * Delega la lógica de negocio para procesar la devolución.
     */
    public void registrarDevolucion(long idProducto, int cantidad, String motivo) {
        try {
            Devolucion devolucion = servicioDevolucion.registrarDevolucionDanado(idProducto, cantidad, motivo);
            ventana.muestraDevolucionExitosa("Devolución registrada correctamente con Folio #" + devolucion.getIdDevolucion());
        } catch (IllegalArgumentException e) {
            log.warn("Validación fallida en devolución: {}", e.getMessage());
            ventana.muestraError(e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado al registrar devolución", e);
            ventana.muestraError("Ocurrió un error inesperado al procesar la devolución.");
        }
    }
}