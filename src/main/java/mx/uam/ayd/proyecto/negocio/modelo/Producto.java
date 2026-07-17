package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * 
 * @author Yamelin, Guillermo, Dydier, Yael, Sheyla
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

    private Object categoria; 

    private Double precioCompra; 

    private int existenciaActual; 

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

    public Object getCategoria() {
        return categoria;
    }

    public void setCategoria(Object categoria) {
        this.categoria = categoria;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public int getExistenciaActual() {
        return existenciaActual;
    }

    public void setExistenciaActual(int existenciaActual) {
        this.existenciaActual = existenciaActual;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Producto other = (Producto) obj;
        return idProducto == other.idProducto;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(idProducto);
    }

    @Override
        public String toString() {
        return "Producto{" +
        "id=" + idProducto +
        ", nombre='" + nombre + '\'' +
        ", precioVenta=" + precio +
        ", precioCompra=" + precioCompra +
        ", stock=" + existenciaActual +
        '}';
        }
}