package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

/**
 * Entidad de negocio Devolucion
 * 
 * @author Yamelin Larios Nepomuseno
 */
@Entity
public class Devolucion {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDevolucion;

    private String motivo;

    private String tipoDevolucion;

    private LocalDateTime fecha; 

    private long idProducto;

    private int cantidad;

    private int idProveedor;

    private String numeroEmpleado;

    /**
     * Constructor vacío requerido por JPA
     */
    public Devolucion() {
    }

    // Getters y Setters

    public long getIdDevolucion() {
        return idDevolucion;
    }

    public void setIdDevolucion(long idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getTipoDevolucion() {
        return tipoDevolucion;
    }

    public void setTipoDevolucion(String tipoDevolucion) {
        this.tipoDevolucion = tipoDevolucion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
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
                + ", fecha=" + fecha + ", idProducto=" + idProducto + ", cantidad=" + cantidad 
                + ", idProveedor=" + idProveedor + ", numeroEmpleado=" + numeroEmpleado + "]";
    }
}