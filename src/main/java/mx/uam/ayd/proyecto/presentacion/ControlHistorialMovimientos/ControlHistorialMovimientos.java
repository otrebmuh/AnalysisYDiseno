package mx.uam.ayd.proyecto.presentacion.ControlHistorialMovimientos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioMovimientoInventario;
import mx.uam.ayd.proyecto.negocio.modelo.MovimientoInventario;
import mx.uam.ayd.proyecto.negocio.modelo.Venta;

/**
 * Controlador de la ventana Historial de Movimientos.
 *
 * Se encarga de comunicar la interfaz con la lógica de negocio.
 *
 * @author Yamelin, Guillermo, Dydier, Yael, Sheyla
 */
@Component
public class ControlHistorialMovimientos {

    @Autowired
    private ServicioMovimientoInventario servicioMovimientoInventario;

    /**
     * Inicia la ventana.
     */
    public void inicia() {

        Venta.setControl(this);
        Venta.muestra();

        cargarMovimientos();
    }

    /**
     * Recupera todos los movimientos y los muestra.
     */
    public void cargarMovimientos() {

        List<MovimientoInventario> movimientos =
                servicioMovimientoInventario.obtenerMovimientos();

        Venta.muestraMovimientos(movimientos);
    }

    /**
     * Busca movimientos usando un filtro.
     *
     * @param filtro texto escrito por el usuario
     */
    public void buscarMovimiento(String filtro) {

        List<MovimientoInventario> movimientos =
                servicioMovimientoInventario.buscarMovimiento(filtro);

        Venta.muestraMovimientos(movimientos);
    }

    /**
     * Consulta el detalle de un movimiento.
     *
     * @param idMovimiento identificador
     * @return movimiento encontrado
     */
    public MovimientoInventario consultarDetalleMovimiento(long idMovimiento) {

        return servicioMovimientoInventario
                .consultarDetalleMovimiento(idMovimiento);
    }

}