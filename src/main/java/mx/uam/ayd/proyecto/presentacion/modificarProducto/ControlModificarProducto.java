package mx.uam.ayd.proyecto.presentacion.modificarProducto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioProducto;
import mx.uam.ayd.proyecto.negocio.ServicioLaboratorio;
import mx.uam.ayd.proyecto.negocio.ServicioIngrediente;
import mx.uam.ayd.proyecto.negocio.ServicioCategoriaProducto;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.negocio.modelo.Laboratorio;
import mx.uam.ayd.proyecto.negocio.modelo.Ingrediente;
import mx.uam.ayd.proyecto.negocio.modelo.CategoriaProducto;

import java.util.List;

@Component
public class ControlModificarProducto {

    @Autowired
    private ServicioProducto servicioProducto;
    @Autowired
    private ServicioLaboratorio servicioLaboratorio;
    @Autowired
    private ServicioIngrediente servicioIngrediente;
    @Autowired
    private ServicioCategoriaProducto servicioCategoria;

    private VentanaModificarProducto ventana;
    private Producto productoActual;

    public void inicia(Producto productoAModificar) {
        List<Laboratorio> laboratorios = servicioLaboratorio.getALL();
        List<Ingrediente> ingredientes = servicioIngrediente.getAll();
        List<CategoriaProducto> categorias = servicioCategoria.obtenerTodas();

        ventana = new VentanaModificarProducto(null, laboratorios, ingredientes, categorias);
        this.productoActual = productoAModificar;
        
        // Cargar los datos del producto en la ventana
        cargarDatosProducto();
        
        ventana.agregarListenerModificar(e -> modificarProducto());
        ventana.setVisible(true);
    }

    private void cargarDatosProducto() {
        if (productoActual != null) {
            ventana.setCodigo(productoActual.getCodigo());
            ventana.setNombre(productoActual.getNombre());
            ventana.setDescripcion(productoActual.getDescripcion());
            ventana.setContenido(productoActual.getContenido());
            ventana.setPrecio(productoActual.getPrecio());
            productoActual.setReceta(ventana.getReceta());
            ventana.setLaboratorio(productoActual.getLaboratorio());
            ventana.setIngrediente(productoActual.getIngrediente());
            ventana.setCategoria(productoActual.getCategoria());
        }
    }

    private void modificarProducto() {
        try {
            // Actualizar los campos del producto con los valores del formulario
            productoActual.setNombre(ventana.getNombre());
            productoActual.setDescripcion(ventana.getDescripcion());
            productoActual.setContenido(ventana.getContenido());
            productoActual.setPrecio(ventana.getPrecio());
            productoActual.setReceta(ventana.getReceta());
            productoActual.setLaboratorio(ventana.getLaboratorio());
            productoActual.setIngrediente(ventana.getIngrediente());
            productoActual.setCategoria(ventana.getCategoria());

            // Validar y actualizar
            if (!servicioProducto.validarProducto(productoActual)) {
                ventana.mostrarError("Los datos del producto no son válidos. Revisa los campos.");
                return;
            }

            Producto actualizado = servicioProducto.actualizar(productoActual);

            if (actualizado != null) {
                ventana.mostrarMensaje("Producto modificado correctamente.");
                ventana.dispose();
            } else {
                ventana.mostrarError("Error: no se pudo actualizar el producto.");
            }

        } catch (NumberFormatException ex) {
            ventana.mostrarError("Error en el formato del precio: " + ex.getMessage());
        } catch (Exception ex) {
            ventana.mostrarError("Error al modificar el producto: " + ex.getMessage());
        }
    }
}