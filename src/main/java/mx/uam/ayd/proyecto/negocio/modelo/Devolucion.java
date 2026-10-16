package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

/**
 * Entidad de negocio Devolucion
 * 
 * @author Kevin Dydier López Flores
 */
@Entity // Define esta clase como una entidad persistente en Spring Boot
public class Devolucion {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincrementable
    private long idDevolucion;

    private String motivo;

    private String tipoDevolucion;

    private LocalDateTime fecha; 

    private int idProducto;

    private int idProveedor;

    private String numeroEmpleado;

    /**
     * Constructor vacío requerido por JPA
     */
    public Devolucion() {
    }

    /**
     * @return the idDevolucion
     */
    public long getIdDevolucion() {
        return idDevolucion;
    }

    /**
     * @param idDevolucion the idDevolucion to set
     */
    public void setIdDevolucion(long idDevolucion) {
        this.idDevolucion = idDevolucion;
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

    /**
     * @return the tipoDevolucion
     */
    public String getTipoDevolucion() {
        return tipoDevolucion;
    }

    /**
     * @param tipoDevolucion the tipoDevolucion to set
     */
    public void setTipoDevolucion(String tipoDevolucion) {
        this.tipoDevolucion = tipoDevolucion;
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
     * @return the idProveedor
     */
    public int getIdProveedor() {
        return idProveedor;
    }

    /**
     * @param idProveedor the idProveedor to set
     */
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    /**
     * @return the numeroEmpleado
     */
    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    /**
     * @param numeroEmpleado the numeroEmpleado to set
     */
    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
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
        Devolucion other = (Devolucion) obj;
        return idDevolucion == other.idDevolucion;
    }

    @Override
    public int hashCode() {
        return (int) (31 * idDevolucion);
    }

    @Override
    public String toString() {
        return "Devolucion [idDevolucion=" + idDevolucion + ", motivo=" + motivo + ", tipoDevolucion=" + tipoDevolucion 
                + ", fecha=" + fecha + ", idProducto=" + idProducto + ", idProveedor=" + idProveedor 
                + ", numeroEmpleado=" + numeroEmpleado + "]";
    }
}
