package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad venta
 * @author Kevin Dydier López Flores
 */
@Entity
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idVenta;

    private LocalDateTime fecha;

    private double total;

    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idVenta") // Crea la llave foránea en la tabla DescripcionVenta
    private List<DescripcionVenta> detalles = new ArrayList<>();

    public Venta() {
    }

    public void agregarDetalle(DescripcionVenta detalle) {
        this.detalles.add(detalle);
    }

    // Getters y Setters
    public long getIdVenta() {
        return idVenta; }
    public void setIdVenta(long idVenta) {
        this.idVenta = idVenta; }

    public LocalDateTime getFecha() {
        return fecha; }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha; }

    public double getTotal() {
        return total; }
    public void setTotal(double total) {
        this.total = total; }

    public List<DescripcionVenta> getDetalles() {
        return detalles; }
    public void setDetalles(List<DescripcionVenta> detalles) {
        this.detalles = detalles; }

    @Override
    public String toString() {
        return "Venta [id=" + idVenta + ", total=" + total + ", fecha=" + fecha + "]";
    }
}
