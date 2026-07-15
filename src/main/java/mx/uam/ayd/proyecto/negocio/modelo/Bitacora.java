package mx.uam.ayd.proyecto.negocio.modelo;

import org.springframework.context.annotation.Primary;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;

/**
 * Entidad de negocio Bitacora (Registro de actividades)
 * 
 * @author Larios Nepomuseno Yamelin
 */
@Entity
public class Bitacora {

    @Id //llave primaria para que el sistema sepa qué registro está modificando o eliminando.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //crear el ID a la base de datos
    private long idDevolucion;
    private int idProducto;
    private int cantidad;
    private String motivo;
    private Double precioAnterior;
    private Double precioNuevo;

    // Constructor vacío requerido por JPA
    public Bitacora() {
    }

    public long getIdDevolucion() {
        return idDevolucion;
    }

    public void setIdDevolucion(long idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Double getPrecioAnterior() {
        return precioAnterior;
    }

    public void setPrecioAnterior(Double precioAnterior) {
        this.precioAnterior = precioAnterior;
    }

    public Double getPrecioNuevo() {
        return precioNuevo;
    }

    public void setPrecioNuevo(Double precioNuevo) {
        this.precioNuevo = precioNuevo;
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

        return idDevolucion == other.idDevolucion;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(idDevolucion);
    }

    @Override
    public String toString() {
        return "Bitacora{" + "idDevolucion=" + idDevolucion + ", idProducto=" + idProducto + ", cantidad=" + cantidad + ", motivo='" + motivo + '\'' + ", precioAnterior=" + precioAnterior + ", precioNuevo=" + precioNuevo + '}';
    }
}