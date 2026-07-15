package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

/**
 * Entidad de negocio Bitacora
 *
 * @author Yamelin, Guillermo, Dydier, Yael, Sheyla
 *
 */
@Entity // Esto le dice a Spring que esta es una entidad persistente
public class Bitacora {

    @Id // Esto le dice a Spring que este es el identificador
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Le dice a Spring que genere el id
    private long idBitacora;

    private double precioAnterior;

    private double precioNuevo;

    private int idDevolucion;

    private int idProducto;

    private LocalDateTime fechaHora;

    private int cantidad;

    private String motivo;

    /**
     * @return the idBitacora
     */
    public long getIdBitacora() {
        return idBitacora;
    }

    /**
     * @param idBitacora the idBitacora to set
     */
    public void setIdBitacora(long idBitacora) {
        this.idBitacora = idBitacora;
    }

    /**
     * @return the precioAnterior
     */
    public double getPrecioAnterior() {
        return precioAnterior;
    }

    /**
     * @param precioAnterior the precioAnterior to set
     */
    public void setPrecioAnterior(double precioAnterior) {
        this.precioAnterior = precioAnterior;
    }

    /**
     * @return the precioNuevo
     */
    public double getPrecioNuevo() {
        return precioNuevo;
    }

    /**
     * @param precioNuevo the precioNuevo to set
     */
    public void setPrecioNuevo(double precioNuevo) {
        this.precioNuevo = precioNuevo;
    }

    /**
     * @return the idDevolucion
     */
    public int getIdDevolucion() {
        return idDevolucion;
    }

    /**
     * @param idDevolucion the idDevolucion to set
     */
    public void setIdDevolucion(int idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    /**
     * @return the idProducto
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto the idProducto to set
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * @return the fechaHora
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * @param fechaHora the fechaHora to set
     */
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the motivo
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * @param motivo the motivo to set
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
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
        Bitacora other = (Bitacora) obj;
        return idBitacora == other.idBitacora;
    }

    @Override
    public int hashCode() {
        return (int) (31 * idBitacora);
    }

    @Override
    public String toString() {
        return "Bitacora [idBitacora=" + idBitacora + ", precioAnterior=" + precioAnterior + ", precioNuevo="
                + precioNuevo + ", idDevolucion=" + idDevolucion + ", idProducto=" + idProducto + ", fechaHora="
                + fechaHora + ", cantidad=" + cantidad + ", motivo=" + motivo + "]";
    }
}