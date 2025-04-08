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
     * Inicia la ventana y carga productos
     */
    public void inicia() {
        List<Producto> productos = servicioProducto.getAll();
        ventana.llenaProductos(productos);

        ventana.agregarListenerEliminar(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        ventana.agregarListenerCancelar(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.dispose();
            }
        });

        ventana.setVisible(true);
    }

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

        boolean eliminado = servicioProducto.eliminar(producto.getIdProducto());

        if (eliminado) {
            ventana.mostrarMensaje("Producto eliminado exitosamente.");
            ventana.dispose(); // cerrar ventana tras éxito
        } else {
            ventana.mostrarMensaje("No se pudo eliminar el producto.");
        }
    }
}
