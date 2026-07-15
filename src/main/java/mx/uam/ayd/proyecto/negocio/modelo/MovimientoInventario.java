package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

/**
 * Entidad de negocio MovimientoInventario
 *
 * @author Yamelin, Guillermo, Dydier, Yael, Sheyla
 *
 */
@Entity // Esto le dice a Spring que esta es una entidad persistente
public class MovimientoInventario {

    @Id // Esto le dice a Spring que este es el identificador
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Le dice a Spring que genere el id
    private long idmovimiento;

    private LocalDateTime fecha;

    private String tipoMoviento;

    private int cantidades;

    private int existenciasanteriores;

    private int existenciasActual;

    private String obersevacion;

    /**
     * @return the idmovimiento
     */
    public long getIdmovimiento() {
        return idmovimiento;
    }

    /**
     * @param idmovimiento the idmovimiento to set
     */
    public void setIdmovimiento(long idmovimiento) {
        this.idmovimiento = idmovimiento;
    }

    /**
     * @return the fecha
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the tipoMoviento
     */
    public String getTipoMoviento() {
        return tipoMoviento;
    }

    /**
     * @param tipoMoviento the tipoMoviento to set
     */
    public void setTipoMoviento(String tipoMoviento) {
        this.tipoMoviento = tipoMoviento;
    }

    /**
     * @return the cantidades
     */
    public int getCantidades() {
        return cantidades;
    }

    /**
     * @param cantidades the cantidades to set
     */
    public void setCantidades(int cantidades) {
        this.cantidades = cantidades;
    }

    /**
     * @return the existenciasanteriores
     */
    public int getExistenciasanteriores() {
        return existenciasanteriores;
    }

    /**
     * @param existenciasanteriores the existenciasanteriores to set
     */
    public void setExistenciasanteriores(int existenciasanteriores) {
        this.existenciasanteriores = existenciasanteriores;
    }

    /**
     * @return the existenciasActual
     */
    public int getExistenciasActual() {
        return existenciasActual;
    }

    /**
     * @param existenciasActual the existenciasActual to set
     */
    public void setExistenciasActual(int existenciasActual) {
        this.existenciasActual = existenciasActual;
    }

    /**
     * @return the obersevacion
     */
    public String getObersevacion() {
        return obersevacion;
    }

    /**
     * @param obersevacion the obersevacion to set
     */
    public void setObersevacion(String obersevacion) {
        this.obersevacion = obersevacion;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MovimientoInventario other = (MovimientoInventario) obj;
        return idmovimiento == other.idmovimiento;
    }

    @Override
    public int hashCode() {
        return (int) (31 * idmovimiento);
    }

    @Override
    public String toString() {
        return "MovimientoInventario [idmovimiento=" + idmovimiento + ", fecha=" + fecha + ", tipoMoviento="
                + tipoMoviento + ", cantidades=" + cantidades + ", existenciasanteriores=" + existenciasanteriores
                + ", existenciasActual=" + existenciasActual + ", obersevacion=" + obersevacion + "]";
    }
}