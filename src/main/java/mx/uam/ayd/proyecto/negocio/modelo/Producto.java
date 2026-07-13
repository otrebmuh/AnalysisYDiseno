package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;

@Entity

public class Producto {

    @GeneratedValue(strategy = GenerationType.IDENTITY) // Le dice a Spring que genere el id
    private long idProducto;

    private String nombre;

    private String tipoProducto;

    private Double precio;

    private String marca;

    //private categoria objeto;
    /**
     * @return the idProducto
     */
    public long getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto the idUsuario to set
     */
    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String gettipoProducto() {
        return tipoProducto;
    }

    /**
     * @param tipoProducto the apellido to set
     */
    public void settipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    /**
     * @return the edad
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * @param precio the edad to set
     */
    public Double setPrecio(Double precio) {
        this.precio = precio;
        return precio;
    }
}
