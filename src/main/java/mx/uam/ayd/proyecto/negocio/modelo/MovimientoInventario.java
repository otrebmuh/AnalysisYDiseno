package mx.uam.ayd.proyecto.negocio.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad de negocio MovimientoInventario.
 *
 * Representa una entrada, salida o modificación realizada sobre
 * las existencias de un producto.
 *
 * @author Yamelin, Guillermo, Dydier, Yael, Sheyla
 */
@Entity
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMovimiento;

    private LocalDateTime fecha;

    private String tipoMovimiento;

    private int cantidad;

    private int existenciaAnterior;

    private int existenciaActual;

    private String observacion;

    /**
     * Constructor vacío requerido por JPA.
     */
    public MovimientoInventario() {
    }

    /**
     * @return identificador del movimiento
     */
    public long getIdMovimiento() {
        return idMovimiento;
    }

    /**
     * @param idMovimiento identificador del movimiento
     */
    public void setIdMovimiento(long idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    /**
     * @return fecha y hora del movimiento
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * @param fecha fecha y hora del movimiento
     */
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    /**
     * @return tipo de movimiento
     */
    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * @param tipoMovimiento tipo de movimiento
     */
    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    /**
     * @return cantidad involucrada en el movimiento
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad cantidad involucrada en el movimiento
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return existencia antes del movimiento
     */
    public int getExistenciaAnterior() {
        return existenciaAnterior;
    }

    /**
     * @param existenciaAnterior existencia antes del movimiento
     */
    public void setExistenciaAnterior(int existenciaAnterior) {
        this.existenciaAnterior = existenciaAnterior;
    }

    /**
     * @return existencia después del movimiento
     */
    public int getExistenciaActual() {
        return existenciaActual;
    }

    /**
     * @param existenciaActual existencia después del movimiento
     */
    public void setExistenciaActual(int existenciaActual) {
        this.existenciaActual = existenciaActual;
    }

    /**
     * @return observación asociada al movimiento
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion observación asociada al movimiento
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        MovimientoInventario other = (MovimientoInventario) obj;

        return idMovimiento == other.idMovimiento;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(idMovimiento);
    }

    @Override
    public String toString() {
        return "MovimientoInventario [idMovimiento=" + idMovimiento
                + ", fecha=" + fecha
                + ", tipoMovimiento=" + tipoMovimiento
                + ", cantidad=" + cantidad
                + ", existenciaAnterior=" + existenciaAnterior
                + ", existenciaActual=" + existenciaActual
                + ", observacion=" + observacion
                + "]";
    }
}