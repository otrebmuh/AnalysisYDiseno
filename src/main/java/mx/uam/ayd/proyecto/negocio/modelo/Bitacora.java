package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

/**
 * Entidad de negocio Bitacora
 *
 * @author Yamelin Larios Nepomuseno
 *
 */
@Entity // Esto le dice a Spring que esta es una entidad persistente para la base de datos
public class Bitacora { // Clase pública para que las demás capas interactúen entre sí

    @Id // Define que este atributo es la Llave Primaria de la tabla en la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el ID se autoincrementará solo en la BD
    private long idBitacora; 

    // Atributos privados (Principio de Encapsulamiento)
    // Clases (variables) ocupadas para la HU-09
    private double precioAnterior;
    private double precioNuevo;
    
    // Clases ocupadas para la HU-10
    private int idDevolucion;
    private int cantidad;
    private String motivo;

    // Clases compartidas (Se ocupan para HU-09 como para HU-10)
    private int idProducto;     
    private LocalDateTime fechaHora;    


    // MÉTODOS DE ACCESO: GETTERS
    // Los Getters leen o recuperan el valor de un atributo. No modifican nada, solo "muestran" el dato.

    public long getIdBitacora() {
        return idBitacora;
    }

    public double getPrecioAnterior() {
         return precioAnterior;
    }

    public double getPrecioNuevo() {
        return precioNuevo;
    }

    public int getIdDevolucion() {
        return idDevolucion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getMotivo() {
        return motivo;
    }

    public int getIdProducto() {
         return idProducto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }


    // MÉTODOS DE ACCESO: SETTERS
    // Los Setters establecen, asignan o modifican el valor de un atributo privado desde el exterior.

    public void setIdBitacora(long idBitacora) {
        this.idBitacora = idBitacora;
    }

    public void setPrecioAnterior(double precioAnterior) {
        this.precioAnterior = precioAnterior;
    }
    
    public void setPrecioNuevo(double precioNuevo) {
        this.precioNuevo = precioNuevo;
    }
    
    public void setIdDevolucion(int idDevolucion) {
        this.idDevolucion = idDevolucion;
    }
   
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }


    //          MÉTODOS SOBREESCRITOS (@Override)

    /**
     * @Override indica que estamos rediseñando un método que ya viene de la clase padre Object.
     * El método 'equals' sirve para comparar si dos objetos del tipo Bitacora son exactamente iguales.
     * En este caso, Hibernate y Spring determinan que si dos bitácoras tienen el mismo 'idBitacora', son el mismo registro.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) { // Si apuntan a la misma dirección de memoria, son iguales
            return true;
        }
        if (obj == null) { // Si el otro objeto es nulo, no son iguales
            return false;
        }
        if (getClass() != obj.getClass()) { // Si son de clases diferentes, no son iguales
            return false;
        }
        Bitacora other = (Bitacora) obj;
        return idBitacora == other.idBitacora; // Compara finalmente sus llaves primarias
    }

    /**
     * El método 'hashCode' genera un número entero único (un hash) basado en la llave primaria.
     * Es utilizado por Java para optimizar búsquedas si almacenas estos objetos en colecciones como Sets o Mapas.
     */
    @Override
    public int hashCode() {
        return (int) (31 * idBitacora);
    }

    /**
     * El método 'toString' convierte el objeto y todos sus atributos actuales en una cadena de texto (String).
     * Es sumamente útil para hacer depuración (debugging) e imprimir en la consola qué datos lleva tu objeto.
     */
    @Override
    public String toString() {
        return "Bitacora [idBitacora=" + idBitacora + ", precioAnterior=" + precioAnterior + ", precioNuevo="
                + precioNuevo + ", idDevolucion=" + idDevolucion + ", idProducto=" + idProducto + ", fechaHora="
                + fechaHora + ", cantidad=" + cantidad + ", motivo=" + motivo + "]";
    }
}