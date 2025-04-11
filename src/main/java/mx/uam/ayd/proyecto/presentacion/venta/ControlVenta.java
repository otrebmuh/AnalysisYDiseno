package mx.uam.ayd.proyecto.presentacion.venta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioProducto;
import mx.uam.ayd.proyecto.negocio.ServicioVenta;
import mx.uam.ayd.proyecto.negocio.modelo.Empleado;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;

@Component
public class ControlVenta {

    @Autowired
    private VistaVenta vista;

    private Long idVentaActual;

    @Autowired
    private ServicioVenta servicioVenta;

    @Autowired
    private ServicioProducto servicioProducto;

    private Sucursal sucursal;
    private Empleado empleado;
    
    /* 

    public ControlVenta() {
        // No crear la vista aquí
        inicia(sucursal);
    }
    */

    public void inicia(Sucursal sucursal, Empleado empleado) {
        // Inicializar la vista aquí
        //if (vista == null) {
            //vista = new VistaVenta();
        //}

        this.sucursal = sucursal;
        this.empleado = empleado;
        
        // Hardcoded values for now - these should come from authentication/session
        Long idEmpleado = 1L;
        Long idSucursal = 1L;
        
        try {
            idVentaActual = servicioVenta.nuevaVenta(empleado.getIdEmpleado(), sucursal.getIdSucursal());
            vista.muestra(this);
            vista.setCajeroResponsable(empleado.getNombre());
        } catch (IllegalArgumentException e) {
            vista.muestraError("Error al iniciar venta: " + e.getMessage());
        }
    }

    public void agregarProductoAVenta(String codigo, int cantidad) {
        try {
            int cantidadAgregada = servicioVenta.agregarProductoAVenta(idVentaActual, codigo, cantidad);
            if (cantidadAgregada > 0) {
                Object[] elemento = BuscaryAgregar(codigo, cantidadAgregada);
                vista.actualizaTabla(elemento);
            }
        } catch (IllegalArgumentException e) {
            vista.muestraError("Error al agregar producto: " + e.getMessage());
        }
    }

    public void confirmarVenta() {
        try {
            boolean resultado = servicioVenta.finalizarVenta(idVentaActual);
            //boolean resultado = true; // Simulando el resultado de la venta
            if (resultado) {
                vista.muestraExito("Venta realizada con éxito");
                vista.dispose();
            } else {
                vista.muestraError("No se pudo finalizar la venta");
            }
        } catch (IllegalArgumentException e) {
            vista.muestraError("Error al finalizar venta: " + e.getMessage());
        }
    }

    public void cancelarVenta() {
        // In a real implementation, we might want to notify the service to cancel the venta
        
        vista.limpiarTabla();
        vista.dispose();
    }

    

    public Object[] BuscaryAgregar(String codigo, int cantidad) {
        Long ultimoIdDetalleVenta = servicioVenta.obtenerUltimoIdDetalleVenta();

        Producto producto = servicioProducto.obtenerPorCodigo(codigo);
        Object[] detalleProducto = {producto.getCodigo(),producto.getNombre(), producto.getContenido(), producto.getDescripcion(), producto.getPrecio(), cantidad,producto.getPrecio() * cantidad,"Eliminar",ultimoIdDetalleVenta}; //Aqui al principio va el codigo
        return detalleProducto;
        
    }

    public void eliminarDetalleVenta(Long idDetalle) {
        try {
            servicioVenta.eliminarDetalleVenta(idDetalle);
            //vista.limpiarTabla();
            vista.muestraExito("Detalle de venta eliminado con éxito");
        } catch (IllegalArgumentException e) {
            vista.muestraError("Error al eliminar detalle de venta: " + e.getMessage());
        }
    }
        
}
