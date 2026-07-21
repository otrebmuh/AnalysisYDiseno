package mx.uam.ayd.proyecto.presentacion.ControlHistorialMovimientos;

import java.util.List;

import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioMovimientoInventario;
import mx.uam.ayd.proyecto.negocio.modelo.MovimientoInventario;

/**
 * Controlador de la ventana Historial de Movimientos.
 *
 * Se encarga de comunicar la interfaz con la lógica de negocio.
 *
 * @author Yael Mora Simón
 */
@Component
public class ControlHistorialMovimientos {

    private final ServicioMovimientoInventario
            servicioMovimientoInventario;

    private final VentanaHistorialMovimientos
            ventanaHistorialMovimientos;

    /**
     * Constructor utilizado por Spring para inyectar dependencias.
     *
     * @param servicioMovimientoInventario servicio de movimientos
     * @param ventanaHistorialMovimientos ventana del historial
     */
    public ControlHistorialMovimientos(
            ServicioMovimientoInventario servicioMovimientoInventario,
            VentanaHistorialMovimientos ventanaHistorialMovimientos) {

        this.servicioMovimientoInventario =
                servicioMovimientoInventario;

        this.ventanaHistorialMovimientos =
                ventanaHistorialMovimientos;
    }

    /**
     * Inicia la ventana y carga los movimientos registrados.
     */
    public void inicia() {

        ventanaHistorialMovimientos.setControl(this);

        ventanaHistorialMovimientos.muestra();

        cargarMovimientos();
    }

    /**
     * Recupera todos los movimientos y los muestra en la ventana.
     */
    public void cargarMovimientos() {

        List<MovimientoInventario> movimientos =
                servicioMovimientoInventario.obtenerMovimientos();

        ventanaHistorialMovimientos
                .muestraMovimientos(movimientos);
    }

    /**
     * Busca movimientos utilizando el filtro recibido.
     *
     * @param filtro texto escrito por el usuario
     */
    public void buscarMovimiento(String filtro) {

        List<MovimientoInventario> movimientos =
                servicioMovimientoInventario
                        .buscarMovimiento(filtro);

        ventanaHistorialMovimientos
                .muestraMovimientos(movimientos);
    }

    /**
     * Consulta el detalle de un movimiento.
     *
     * @param idMovimiento identificador del movimiento
     * @return movimiento encontrado o null
     */
    public MovimientoInventario consultarDetalleMovimiento(
            long idMovimiento) {

        return servicioMovimientoInventario
                .consultarDetalleMovimiento(idMovimiento);
    }
}