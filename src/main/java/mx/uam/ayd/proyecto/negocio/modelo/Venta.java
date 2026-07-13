package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidad de negocio Venta
 * 
 * @author Lopez Flores Kevin Dydier 
 */
@Entity // Le dice a Spring que esta es una entidad persistente
public class Venta {

    @Id // El identificador único
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincrementable en la BD
    private long idVenta;

    private double total;

    private LocalDateTime fecha; // Corrección: Usar tipos de fecha reales en lugar de String

    private int idVendedor; // Representa el ID del usuario/empleado que realizó la venta

    /**
     * Constructor vacío requerido por JPA
     */
    public Venta() {
    }

    /**
     * @return the idVenta
     */
    public long getIdVenta() {
        return idVenta;
    }

    /**
     * @param idVenta the idVenta to set
     */
    public void setIdVenta(long idVenta) {
        this.idVenta = idVenta;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
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
     * @return the idVendedor
     */
    public int getIdVendedor() {
        return idVendedor;
    }

    /**
     * @param idVendedor the idVendedor to set
     */
    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
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
        Venta other = (Venta) obj;
        return idVenta == other.idVenta;
    }

    @Override
    public int hashCode() {
        return (int) (31 * idVenta);
    }

    @Override
    public String toString() {
        return "Venta [idVenta=" + idVenta + ", total=" + total + ", fecha=" + fecha + ", idVendedor=" + idVendedor + "]";
    }
}

