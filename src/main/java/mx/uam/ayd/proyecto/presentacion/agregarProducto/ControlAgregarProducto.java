package mx.uam.ayd.proyecto.presentacion.agregarProducto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioProducto;
import mx.uam.ayd.proyecto.negocio.ServicioCategoriaProducto;
import mx.uam.ayd.proyecto.negocio.ServicioIngrediente;
import mx.uam.ayd.proyecto.negocio.ServicioLaboratorio;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.negocio.modelo.CategoriaProducto;
import mx.uam.ayd.proyecto.negocio.modelo.Ingrediente;
import mx.uam.ayd.proyecto.negocio.modelo.Laboratorio;

@Component
public class ControlAgregarProducto {

    @Autowired
    private ServicioProducto servicioProducto;
    @Autowired
    private ServicioLaboratorio servicioLaboratorio;
    @Autowired
    private ServicioIngrediente servicioIngrediente;
    @Autowired
    private ServicioCategoriaProducto servicioCategoria;

    private VentanaAgregarProducto ventana;

    public void inicia() {
        List<Laboratorio> laboratorios = servicioLaboratorio.getALL();
        List<Ingrediente> ingredientes = servicioIngrediente.getAll();
        List<CategoriaProducto> categorias = servicioCategoria.obtenerTodas();

        ventana = new VentanaAgregarProducto(this, laboratorios, ingredientes, categorias);
        ventana.setVisible(true);
    }

    public void agregarProducto(String codigo, String nombre, String descripcion, String contenido, boolean receta, 
                                Laboratorio laboratorio, Ingrediente ingrediente, CategoriaProducto categoria) {
        Producto producto = new Producto();
        producto.setCodigo(codigo);
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setContenido(contenido);
        producto.setReceta(receta);
        producto.setLaboratorio(laboratorio);
        producto.setIngrediente(ingrediente);
        producto.setCategoria(categoria);

        if (servicioProducto.validarProducto(producto)) {
            servicioProducto.crear(producto);
            ventana.mostrarMensaje("Producto agregado correctamente.");
            ventana.dispose();
        } else {
            ventana.mostrarError("Datos inválidos. Verifica los campos.");
        }
    }
}
