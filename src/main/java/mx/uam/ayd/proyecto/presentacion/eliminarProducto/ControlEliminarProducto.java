package mx.uam.ayd.proyecto.presentacion.eliminarProducto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mx.uam.ayd.proyecto.negocio.ServicioProducto;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.presentacion.gestionProductos.VentanaGestionProductos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@Component
public class ControlEliminarProducto {

    @Autowired
    private ServicioProducto servicioProducto;

    @Autowired
    private VentanaEliminarProducto ventana;

    private VentanaGestionProductos ventanaPadre;

    /**
     * Inicia la ventana de eliminación con referencia a la ventana padre.
     */
    public void inicia(VentanaGestionProductos ventanaPadre) {
        this.ventanaPadre = ventanaPadre;

        // Obtener todos los productos
        List<Producto> productos = servicioProducto.getAll();
        ventana.llenaProductos(productos);

        // Listener para el botón de eliminar
        ventana.agregarListenerEliminar(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        // Listener para el botón de cancelar
        ventana.agregarListenerCancelar(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.dispose();
            }
        });

        ventana.setVisible(true);
    }

    /**
     * Elimina un producto seleccionado y actualiza la tabla en la ventana padre.
     */
    private void eliminarProducto() {
        String nombre = ventana.getProductoSeleccionado();

        if (nombre == null || nombre.isEmpty()) {
            ventana.mostrarMensaje("Por favor selecciona un producto.");
            return;
        }

        if (!ventana.confirmarEliminacion(nombre)) {
            return;
        }

        Producto producto = servicioProducto.obtenerPorNombre(nombre);
        if (producto == null) {
            ventana.mostrarMensaje("No se encontró el producto.");
            return;
        }

        try {
            boolean eliminado = servicioProducto.eliminar(producto.getIdProducto());

            if (eliminado) {
                ventana.mostrarMensaje("Producto eliminado exitosamente.");
                ventana.dispose();
                ventanaPadre.actualizarTablaProductos(); // ACTUALIZAR TABLA EN TIEMPO REAL
            } else {
                ventana.mostrarMensaje("No se pudo eliminar el producto.");
            }
        } catch (Exception e) {
            ventana.mostrarMensaje("Error: No se pudo eliminar el producto porque está relacionado con otros registros.");
        }
    }
}
