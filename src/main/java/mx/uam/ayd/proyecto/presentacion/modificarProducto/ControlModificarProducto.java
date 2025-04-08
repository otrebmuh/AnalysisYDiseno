package mx.uam.ayd.proyecto.presentacion.modificarProducto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mx.uam.ayd.proyecto.negocio.ServicioProducto;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

@Component
public class ControlModificarProducto {

    @Autowired
    private ServicioProducto servicioProducto;

    private VentanaModificarProducto ventana;

    public void inicia() {
        ventana = new VentanaModificarProducto(null);
        ventana.agregarListenerModificar(e -> modificarProducto());
        ventana.setVisible(true);
    }

    private void modificarProducto() {
        try {
            String nombre = ventana.getNombre();

            Producto producto = servicioProducto.obtenerPorNombre(nombre);

            if (producto == null) {
                ventana.mostrarMensaje("No se encontró ningún producto con el nombre: " + nombre);
                return;
            }

            // Actualizar los campos del producto con los valores del formulario
            producto.setDescripcion(ventana.getDescripcion());
            producto.setContenido(ventana.getContenido());
            producto.setReceta(Boolean.parseBoolean(ventana.getReceta()));
            // Nota: puedes agregar lógica adicional si el laboratorio, ingrediente o categoría se seleccionan por ID o nombre

            // Validar y actualizar
            if (!servicioProducto.validarProducto(producto)) {
                ventana.mostrarMensaje("Los datos del producto no son válidos. Revisa los campos.");
                return;
            }

            Producto actualizado = servicioProducto.actualizar(producto);

            if (actualizado != null) {
                ventana.mostrarMensaje("Producto modificado correctamente.");
                ventana.dispose();
            } else {
                ventana.mostrarMensaje("Error: no se pudo actualizar el producto.");
            }

        } catch (Exception ex) {
            ventana.mostrarMensaje("Error al modificar el producto: " + ex.getMessage());
        }
    }
}
