package mx.uam.ayd.proyecto.presentacion.actualizarPrecio;

import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioProducto;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

/**
 * Controlador de la ventana Actualizar Precio (HU09).
 *
 * Se encarga de comunicar la interfaz con la lógica de negocio.
 *
 * @author Yamelin Larios Nepomuseno
 */
@Component
public class ControlActualizarPrecio {

    private final ServicioProducto servicioProducto;
    private final VentanaActualizarPrecio ventanaActualizarPrecio;

    /**
     * Constructor utilizado por Spring para inyectar dependencias.
     *
     * @param servicioProducto servicio de productos
     * @param ventanaActualizarPrecio ventana de actualizar precio
     */
    public ControlActualizarPrecio(
            ServicioProducto servicioProducto,
            VentanaActualizarPrecio ventanaActualizarPrecio) {

        this.servicioProducto = servicioProducto;
        this.ventanaActualizarPrecio = ventanaActualizarPrecio;
    }

    /**
     * Inicia la ventana de actualización de precios.
     */
    public void inicia() {
        ventanaActualizarPrecio.setControl(this);
        ventanaActualizarPrecio.muestra();
    }

    /**
     * Busca un producto por su ID para mostrar sus datos en la ventana.
     *
     * @param idProducto identificador del producto
     * @return producto encontrado o null si no existe
     */
    public Producto buscarProducto(long idProducto) {
        return servicioProducto.buscarProductoPorId(idProducto);
    }

    /**
     * Solicita la actualización del precio de un producto.
     *
     * @param idProducto identificador del producto
     * @param nuevoPrecio nuevo precio a asignar
     * @return producto actualizado
     */
    public Producto actualizarPrecio(long idProducto, double nuevoPrecio) {
        return servicioProducto.actualizarPrecioProducto(idProducto, nuevoPrecio);
    }
}