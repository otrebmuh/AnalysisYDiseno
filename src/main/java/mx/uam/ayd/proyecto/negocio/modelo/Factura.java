package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad de negocio Usuario
 *
 * @author JAVITOS 
 *
 */
@Entity // Esto le dice a Spring que esta es una entidad persistente

public class Factura {

    @Id // Esto le dice a Spring que este es el identificador
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Le dice a Spring que genere el id
    private long idFactura;

    private String nombre;

    private Double montoTotal;

    private Double saldoPendiente;

    //  private String status
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
     * @return the idFactura
     */
    public long getIdFactura() {
        return idFactura;
    }

    /**
     * @param idFactura the idFactura to set
     */
    public void setIdFactura(long idFactura) {
        this.idFactura = idFactura;
    }

    /**
     * @return the montoTotal
     */
    public Double getmontoTotal() {
        return montoTotal;
    }

    /**
     * @param montoTotal the montoTotal to set
     */
    public Double setmontoTotal(Double montoTotal) {
        return montoTotal;
    }

    /**
     * @return the saldoPendiente
     */
    public Double getsaldoPendiente() {
        return saldoPendiente;
    }

    /**
     * @param saldoPendiente the saldoPendiente to set
     */
    public Double setsaldoPendiente(Double montoTotal) {
        return saldoPendiente;
    }

    /*
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
        Usuario other = (Usuario) obj;
        return idUsuario == other.idUsuario;
    }

    @Override
    public int hashCode() {
        return (int) (31 * idUsuario);
    }

    @Override
    public String toString() {
        return "Usuario [idUsuario=" + idUsuario + ", nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad + "]";
    }*/
}
