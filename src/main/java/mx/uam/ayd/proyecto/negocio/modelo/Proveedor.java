package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad de negocio Proveedor
 *
 * @author Yamelin, Guillermo, Dydier, Yael, Sheyla
 *
 */
@Entity // Esto le dice a Spring que esta es una entidad persistente
public class Proveedor {

    @Id // Esto le dice a Spring que este es el identificador
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Le dice a Spring que genere el id
    private long idProveedor;

    private String nombreCompleto;

    private String corporativo;

    private int idDevolucion;

    private String telefono;

    private String tipoProveedor;

    /**
     * @return the idProveedor
     */
    public long getIdProveedor() {
        return idProveedor;
    }

    /**
     * @param idProveedor the idProveedor to set
     */
    public void setIdProveedor(long idProveedor) {
        this.idProveedor = idProveedor;
    }

    /**
     * @return the nombreCompleto
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * @param nombreCompleto the nombreCompleto to set
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * @return the corporativo
     */
    public String getCorporativo() {
        return corporativo;
    }

    /**
     * @param corporativo the corporativo to set
     */
    public void setCorporativo(String corporativo) {
        this.corporativo = corporativo;
    }

    /**
     * @return the idDevolucion
     */
    public int getIdDevolucion() {
        return idDevolucion;
    }

    /**
     * @param idDevolucion the idDevolucion to set
     */
    public void setIdDevolucion(int idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the tipoProveedor
     */
    public String getTipoProveedor() {
        return tipoProveedor;
    }

    /**
     * @param tipoProveedor the tipoProveedor to set
     */
    public void setTipoProveedor(String tipoProveedor) {
        this.tipoProveedor = tipoProveedor;
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
        Proveedor other = (Proveedor) obj;
        return idProveedor == other.idProveedor;
    }

    @Override
    public int hashCode() {
        return (int) (31 * idProveedor);
    }

    @Override
    public String toString() {
        return "Proveedor [idProveedor=" + idProveedor + ", nombreCompleto=" + nombreCompleto + ", corporativo="
                + corporativo + ", idDevolucion=" + idDevolucion + ", telefono=" + telefono + ", tipoProveedor="
                + tipoProveedor + "]";
    }
}