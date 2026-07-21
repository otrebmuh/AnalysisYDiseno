package mx.uam.ayd.proyecto.presentacion.principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import mx.uam.ayd.proyecto.negocio.ServicioProducto;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

@Component
public class ControlConsultarInventario {

    private final ServicioProducto servicioProducto;
    private final VentanaConsultarInventario ventana;

    @Autowired
    public ControlConsultarInventario(
            ServicioProducto servicioProducto,
            VentanaConsultarInventario ventana) {

        this.servicioProducto = servicioProducto;
        this.ventana = ventana;
    }

    @PostConstruct
    public void init() {
        ventana.setControl(this);
    }

    public void inicia() {

        try {
            Iterable<Producto> productos =
                    servicioProducto.recuperaProductos();

            ventana.muestra(productos);

        } catch (Exception ex) {
            ventana.muestraDialogoConMensaje(
                    "Error al recuperar los productos: "
                            + ex.getMessage());
        }
    }

    public void muestraTodos() {
        inicia();
    }

    public void buscaProducto(String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {
            ventana.muestraDialogoConMensaje(
                    "Escribe el nombre del producto");
            return;
        }

        try {
            Producto producto =
                    servicioProducto.buscaProducto(nombre.trim());

            if (producto == null) {
                ventana.muestraDialogoConMensaje(
                        "No se encontró un producto con el nombre: "
                                + nombre);
                return;
            }

            ventana.muestraProducto(producto);

        } catch (Exception ex) {
            ventana.muestraDialogoConMensaje(
                    "Error al buscar el producto: "
                            + ex.getMessage());
        }
    }

    public void termina() {
        ventana.setVisible(false);
    }
}