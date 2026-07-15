package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad de negocio Inventario
 *
 * @author Yamelin, Guillermo, Dydier, Yael, Sheyla
 *
 */
@Entity // Esto le dice a Spring que esta es una entidad persistente
public class Inventario {

    @Id // Esto le dice a Spring que este es el identificador
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Le dice a Spring que genere el id
    private long idProducto;

    private int existenciaActual;

    private int existenciaDisponible;

    private int stockMinimo;

    /**
     * @return the idProducto
     */
    public long getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto the idProducto to set
     */
    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * @return the existenciaActual
     */
    public int getExistenciaActual() {
        return existenciaActual;
    }

    /**
     * @param existenciaActual the existenciaActual to set
     */
    public void setExistenciaActual(int existenciaActual) {
        this.existenciaActual = existenciaActual;
    }

    /**
     * @return the existenciaDisponible
     */
    public int getExistenciaDisponible() {
        return existenciaDisponible;
    }

    /**
     * @param existenciaDisponible the existenciaDisponible to set
     */
    public void setExistenciaDisponible(int existenciaDisponible) {
        this.existenciaDisponible = existenciaDisponible;
    }

    /**
     * @return the stockMinimo
     */
    public int getStockMinimo() {
        return stockMinimo;
    }

    /**
     * @param stockMinimo the stockMinimo to set
     */
    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    // ==========================================
    // Métodos de negocio requeridos por el UML
    // ==========================================

    /**
     * Método para obtener la existencia actual
     * @return la existencia actual del inventario
     */
    public int obtenerExistenciaActual() {
        return this.existenciaActual;
    }

    /**
     * Método para actualizar la existencia
     * @param cantidad el nuevo valor para la existencia actual
     */
    public void actualizarExistencia(int cantidad) {
        this.existenciaActual = cantidad;
    }

    // ==========================================
    // Métodos estándar (equals, hashCode, toString)
    // ==========================================

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
        Inventario other = (Inventario) obj;
        return idProducto == other.idProducto;
    }

    @Override
    public int hashCode() {
        return (int) (31 * idProducto);
    }

    @Override
    public String toString() {
        return "Inventario [idProducto=" + idProducto + ", existenciaActual=" + existenciaActual
                + ", existenciaDisponible=" + existenciaDisponible + ", stockMinimo=" + stockMinimo + "]";
    }
}