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
    private Double precio; // Usado para HU09 (Precio de Venta a actualizar)
    private String marca;
    private String categoria; // Se cambió de Object a String para evitar problemas de persistencia en JPA
    
    // Atributos colaborativos (HU de tus compañeros)
    private Double precioCompra; 
    private int existenciaActual; 

    // Constructor por defecto requerido por JPA
    public Producto() {
    }

    //          MÉTODOS DE ACCESO: GETTERS

    public long getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getMarca() {
        return marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public int getExistenciaActual() {
        return existenciaActual;
    }


    //          MÉTODOS DE ACCESO: SETTERS

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public void setExistenciaActual(int existenciaActual) {
        this.existenciaActual = existenciaActual;
    }


    //          MÉTODOS SOBREESCRITOS (@Override)

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