package mx.uam.ayd.proyecto.presentacion.venta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mx.uam.ayd.proyecto.negocio.ServicioVenta;

@Component
public class ControlVenta {
    private VistaVenta vista;
    private Long idVentaActual;

    @Autowired
    private ServicioVenta servicioVenta;

    public ControlVenta() {
        vista = new VistaVenta();
    }

    public void inicia() {
        // Hardcoded values for now - these should come from authentication/session
        Long idEmpleado = 1L;
        Long idSucursal = 1L;
        
        try {
            idVentaActual = servicioVenta.nuevaVenta(idEmpleado, idSucursal);
            vista.muestra(this);
        } catch (IllegalArgumentException e) {
            vista.muestraError("Error al iniciar venta: " + e.getMessage());
        }
    }

    public void agregarProductoAVenta(String codigo, int cantidad) {
        try {
            int cantidadAgregada = servicioVenta.agregarProductoAVenta(idVentaActual, codigo, cantidad);
            if (cantidadAgregada > 0) {
                vista.actualizaTabla(); // La vista deberá obtener los datos actualizados
            }
        } catch (IllegalArgumentException e) {
            vista.muestraError("Error al agregar producto: " + e.getMessage());
        }
    }

    public void confirmarVenta() {
        try {
            boolean resultado = servicioVenta.finalizarVenta(idVentaActual);
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
        vista.dispose();
    }
}
