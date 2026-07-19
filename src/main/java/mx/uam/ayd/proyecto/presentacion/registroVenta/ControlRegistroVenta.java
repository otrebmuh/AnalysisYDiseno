package mx.uam.ayd.proyecto.presentacion.registroVenta;

import java.util.List;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioVenta;
import mx.uam.ayd.proyecto.negocio.modelo.DescripcionVenta;
import mx.uam.ayd.proyecto.negocio.modelo.Venta;

/**
 * Módulo de control para la HU-04: Registro de Ventas.
 * Ajustado para recibir el carrito lleno desde la HU-05.
 */
@Component
public class ControlRegistroVenta {

    // Dependencias (Se elimina ServicioProducto ya que HU-05 hace la búsqueda)
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
        // Conexión bidireccional estándar [3]
        ventanaCarrito.setControl(this);
        ventanaCobro.setControl(this);
    }

    /**
     * MODIFICACIÓN CLAVE: Nuevo punto de entrada de la HU.
     * Es invocado por el controlador de la HU-05 al seleccionar "opción de compra" [1].
     */
    public void iniciaConCarrito(List<DescripcionVenta> carrito) {
        this.carritoRecibido = carrito;
        this.totalVenta = calcularTotal(carrito);
        
        // Muestra la ventana de confirmación con los datos ya cargados [2]
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
        // RN-04: Validar precios antes de habilitar pantalla de cobro [4, 5]
        for (DescripcionVenta d : carritoRecibido) {
            if (d.getPrecioUnitario() <= 0) {
                ventanaCarrito.muestraDialogoConMensaje("RN-04: Todo precio asignado debe ser estrictamente mayor a cero.");
                return;
            }
        }
        // Habilita la pantalla de cobro si los precios son válidos [4]
        ventanaCobro.muestra(totalVenta);
    }

    /**
     * Calcula el cambio para el Escenario 1 [5].
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
     * Finaliza la compra y ejecuta la persistencia.
     */
    public void finalizarCompra(double efectivoRecibido) {
        try {
            // El servicio registra la venta y descuenta stock automáticamente (RN-10) [6-8]
            servicioVenta.registrarVenta(carritoRecibido, efectivoRecibido);
            
            // Mensaje de éxito obligatorio [4]
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