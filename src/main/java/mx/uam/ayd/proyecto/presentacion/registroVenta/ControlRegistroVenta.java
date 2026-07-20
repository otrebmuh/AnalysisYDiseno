package mx.uam.ayd.proyecto.presentacion.registroVenta;

import java.util.List;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioVenta;
import mx.uam.ayd.proyecto.negocio.modelo.DescripcionVenta;

@Component
public class ControlRegistroVenta {

    // Dependencias
    private final ServicioVenta servicioVenta;
    private final VentanaCarrito ventanaCarrito;
    private final VentanaCobro ventanaCobro;

    // Estado del proceso de venta recibido de HU-05
    private List<DescripcionVenta> carritoRecibido;
    private double totalVenta;

    @Autowired
    public ControlRegistroVenta(ServicioVenta servicioVenta, VentanaCarrito v1, VentanaCobro v2) {
        this.servicioVenta = servicioVenta;
        this.ventanaCarrito = v1;
        this.ventanaCobro = v2;
    }

    @PostConstruct
    public void init() {
        ventanaCarrito.setControl(this);
        ventanaCobro.setControl(this);
    }

    public void iniciaConCarrito(List<DescripcionVenta> carrito) {
        this.carritoRecibido = carrito;
        this.totalVenta = calcularTotal(carrito);
        
        // Muestra la ventana de confirmación con los datos ya cargados
        ventanaCarrito.muestra(carritoRecibido, totalVenta);
    }

    private double calcularTotal(List<DescripcionVenta> detalles) {
        return detalles.stream()
                .mapToDouble(d -> d.getPrecioUnitario() * d.getCantidad())
                .sum();
    }

    /**
     * Valida reglas de negocio antes de permitir el cobro.
     */
    public void procesarConfirmacionVenta() {
        // RN-04: Validar precios antes de habilitar pantalla de cobro
        for (DescripcionVenta d : carritoRecibido) {
            if (d.getPrecioUnitario() <= 0) {
                ventanaCarrito.muestraDialogoConMensaje("RN-04: Todo precio asignado debe ser estrictamente mayor a cero.");
                return;
            }
        }
        // Habilita la pantalla de cobro si los precios son válidos
        ventanaCobro.muestra(totalVenta);
    }

    /**
     * Calcula el cambio
     */
    public void calcularCambio(double efectivoRecibido) {
        if (efectivoRecibido < totalVenta) {
            ventanaCobro.actualizaCambio(0, "Efectivo insuficiente");
        } else {
            double cambio = efectivoRecibido - totalVenta;
            ventanaCobro.actualizaCambio(cambio, null);
        }
    }

    /**
     * Finaliza la compra
     */
    public void finalizarCompra(double efectivoRecibido) {
        try {
            // El servicio registra la venta y descuenta stock automáticamente (RN-10)
            servicioVenta.registrarVenta(carritoRecibido, efectivoRecibido);
            
            // Mensaje de éxito obligatori
            ventanaCobro.muestraDialogoConMensaje("La venta ha sido exitosa.");
            termina();
            
        } catch (Exception ex) {
            ventanaCobro.muestraDialogoConMensaje("Error al registrar: " + ex.getMessage());
        }
    }

    public void termina() {
        ventanaCobro.setVisible(false);
        ventanaCarrito.setVisible(false);
    }
}