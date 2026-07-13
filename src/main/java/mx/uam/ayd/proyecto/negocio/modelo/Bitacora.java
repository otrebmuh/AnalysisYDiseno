package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;

@Entity

public class Bitacora {

    @GeneratedValue(strategy = GenerationType.IDENTITY) // Le dice a Spring que genere el id
    private int idRegistro;
    private int Devolucion;
    private int Producto;
    //private Date fechaHora;
    private int cantidad;
    private String motivo;
    private String descripcion;

    //private categoria objeto;
    /**
     * @return the idRegistro
     */
    public int getIdRegistro() {
        return idRegistro;
    }

    /**
     * @param idRegistro the idRegistro to set
     */
    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
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
