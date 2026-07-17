package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
/**
 * Entidad de negocio Producto
 *
 * @author Yamelin, Guillermo, Dydier, Yael, Sheyla
 *
 */
@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProducto;

    private String nombre;

    private String tipoProducto;

    private Double precio;

    private String marca;

    // Constructor vacío requerido por JPA
    public Producto() {
    }

    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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

        Producto other = (Producto) obj;

        return idProducto == other.idProducto;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(idProducto);
    }

    @Override
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto +", nombre='" + nombre + '\'' +", tipoProducto='" + tipoProducto + '\'' +", precio=" + precio +", marca='" + marca + '\'' +'}';
    }
}