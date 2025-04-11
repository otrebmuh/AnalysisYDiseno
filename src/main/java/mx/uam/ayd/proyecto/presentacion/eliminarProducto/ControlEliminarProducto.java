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
                ventana.dispose();  // Cerrar la ventana de eliminación
            }
        });

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

        // Confirmar la eliminación del producto
        if (!ventana.confirmarEliminacion(nombre)) {
            return;
        }

        // Buscar el producto por su nombre
        Producto producto = servicioProducto.obtenerPorNombre(nombre);
        if (producto == null) {
            ventana.mostrarMensaje("No se encontró el producto.");
            return;
        }

        try {
            // Intentar eliminar el producto
            boolean eliminado = servicioProducto.eliminar(producto.getIdProducto());

            if (eliminado) {
                ventana.mostrarMensaje("Producto eliminado exitosamente.");
                ventana.dispose();  // Cerrar ventana tras éxito
            } else {
                ventana.mostrarMensaje("No se pudo eliminar el producto.");
            }
        } catch (Exception e) {
            // Manejo de excepciones si hay problemas con las relaciones de base de datos
            ventana.mostrarMensaje("Error: No se pudo eliminar el producto porque está relacionado con otros registros.");
        }
    }
}
