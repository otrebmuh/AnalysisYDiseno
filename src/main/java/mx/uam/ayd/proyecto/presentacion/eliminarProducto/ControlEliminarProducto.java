package mx.uam.ayd.proyecto.presentacion.eliminarProducto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mx.uam.ayd.proyecto.negocio.ServicioProducto;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@Component
public class ControlEliminarProducto {

    @Autowired
    private ServicioProducto servicioProducto;

    @Autowired
    private VentanaEliminarProducto ventana;

    /**
     * Inicia la ventana y carga la lista de productos.
     */
    public void inicia() {
        // Obtener todos los productos
        List<Producto> productos = servicioProducto.getAll();
        ventana.llenaProductos(productos);

        // Listener para el botón de eliminar
        ventana.agregarListenerEliminar(e -> eliminarProducto());

        // Listener para el botón de cancelar
        ventana.agregarListenerCancelar(e -> ventana.dispose());

        // Mostrar la ventana
        ventana.setVisible(true);
    }

    /**
     * Elimina un producto seleccionado de la lista.
     */
    private void eliminarProducto() {
        // Obtener el nombre del producto seleccionado
        String nombre = ventana.getProductoSeleccionado();

        // Validación de selección
        if (nombre == null || nombre.isEmpty()) {
            ventana.mostrarMensaje("Por favor selecciona un producto.");
            return;
        }

        // Buscar el producto por su nombre
        Producto producto = servicioProducto.obtenerPorNombre(nombre);
        if (producto == null) {
            ventana.mostrarMensaje("No se encontró el producto.");
            return;
        }

        // Confirmar la eliminación del producto
        if (!ventana.confirmarEliminacion(nombre)) {
            return;
        }

        try {
            // Intentar eliminar el producto usando el ID
            boolean eliminado = servicioProducto.eliminar(producto.getIdProducto());

            if (eliminado) {
                ventana.mostrarMensaje("Producto eliminado exitosamente.");
                // Actualizar la lista de productos después de eliminar
                List<Producto> productosActualizados = servicioProducto.getAll();
                ventana.llenaProductos(productosActualizados);
            } else {
                ventana.mostrarMensaje("No se pudo eliminar el producto.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ventana.mostrarMensaje("Error: No se pudo eliminar el producto. " + e.getMessage());
        }
    }
}