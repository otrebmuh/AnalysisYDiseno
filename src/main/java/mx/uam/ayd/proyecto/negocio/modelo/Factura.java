package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad de negocio Factura
 *
 * @author Yamelin, Guillermo, Dydier, Yael, Sheyla
 *
 */
@Entity // Esto le dice a Spring que esta es una entidad persistente
public class Factura {

    @Id // Esto le dice a Spring que este es el identificador
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Le dice a Spring que genere el id
    private long idFactura;

    private int idProveedor;

    private double montoTotal;

    private double saldoPendiente;

    private String estado;

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
     * @return the idProveedor
     */
    public int getIdProveedor() {
        return idProveedor;
    }

    /**
     * @param idProveedor the idProveedor to set
     */
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    /**
     * @return the montoTotal
     */
    public double getMontoTotal() {
        return montoTotal;
    }

    /**
     * @param montoTotal the montoTotal to set
     */
    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    /**
     * @return the saldoPendiente
     */
    public double getSaldoPendiente() {
        return saldoPendiente;
    }

    /**
     * @param saldoPendiente the saldoPendiente to set
     */
    public void setSaldoPendiente(double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
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
        Factura other = (Factura) obj;
        return idFactura == other.idFactura;
    }

    @Override
    public int hashCode() {
        return (int) (31 * idFactura);
    }

    @Override
    public String toString() {
        return "Factura [idFactura=" + idFactura + ", idProveedor=" + idProveedor + ", montoTotal=" + montoTotal
                + ", saldoPendiente=" + saldoPendiente + ", estado=" + estado + "]";
    }
}