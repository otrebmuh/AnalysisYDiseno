
package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad de negocio DescripcionVenta (Detalle de la venta)
 * 
 * @author Kevin Dydier López Flores
 */
@Entity 
public class DescripcionVenta {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long idDetalle;

    private int cantidad; 

    private double precioUnitario;

    public DescripcionVenta() {
    }

    public long getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(long idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DescripcionVenta other = (DescripcionVenta) obj;
        return idDetalle == other.idDetalle;
    }

    @Override
    public int hashCode() {
        return (int) (31 * idDetalle);
    }

    @Override
    public String toString() {
        return "DescripcionVenta [idDetalle=" + idDetalle + ", cantidad=" + cantidad + ", precioUnitario=" + precioUnitario + "]";
    }
}