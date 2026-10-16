package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad de negocio Vendedor
 * 
 * @author Kevin Dydier López Flores
 */
@Entity // Le dice a Spring que esta es una entidad persistente para la BD
public class Vendedor {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincrementable
    private long idEmpleado;

    private String nombreCompleto;

    private int age; 

    private String tipoVendedor;

    private String telefono; 

    private double salario; 

    private String numeroEmpleado;

    /**
     * Constructor vacío requerido por JPA
     */
    public Vendedor() {
    }

    /**
     * @return the idEmpleado
     */
    public long getIdEmpleado() {
        return idEmpleado;
    }

    /**
     * @param idEmpleado the idEmpleado to set
     */
    public void setIdEmpleado(long idEmpleado) {
        this.idEmpleado = idEmpleado;
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
     * @return the edad
     */
    public int getEdad() {
        return age;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(int edad) {
        this.age = edad;
    }

    /**
     * @return the tipoVendedor
     */
    public String getTipoVendedor() {
        return tipoVendedor;
    }

    /**
     * @param tipoVendedor the tipoVendedor to set
     */
    public void setTipoVendedor(String tipoVendedor) {
        this.tipoVendedor = tipoVendedor;
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
     * @return the salario
     */
    public double getSalario() {
        return salario;
    }

    /**
     * @param salario the salario to set
     */
    public void setSalario(double salario) {
        this.salario = salario;
    }

    /**
     * @return the numeroEmpleado
     */
    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    /**
     * @param numeroEmpleado the numeroEmpleado to set
     */
    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
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
        Vendedor other = (Vendedor) obj;
        return idEmpleado == other.idEmpleado;
    }

    @Override
    public int hashCode() {
        return (int) (31 * idEmpleado);
    }

    @Override
    public String toString() {
        return "Vendedor [idEmpleado=" + idEmpleado + ", nombreCompleto=" + nombreCompleto + ", tipoVendedor=" 
                + tipoVendedor + ", telefono=" + telefono + ", salario=" + salario + ", numeroEmpleado=" + numeroEmpleado + "]";
    }
}
