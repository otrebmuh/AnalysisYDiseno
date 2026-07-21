package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * @author Kevin Dydier López Flores
 */
@Entity
public class DescripcionVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDetalle;

    private int cantidad;


    private Double precioUnitario;

//////////////////////////////////////////////////////////////////////////////////////////////////////77
    @ManyToOne
    private Producto producto;


    public DescripcionVenta() {
    }

    // --- Getters y Setters ---

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

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

/////////////////////////////////////////////////////////////////////////////////////7777

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DescripcionVenta other = (DescripcionVenta) obj;
        return idDetalle == other.idDetalle;
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(idDetalle);
    }

    @Override
    public String toString() {
        return "DescripcionVenta{" +
                "id=" + idDetalle +
                ", producto=" + (producto != null ? producto.getNombre() : "null") +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                '}';
    }
}