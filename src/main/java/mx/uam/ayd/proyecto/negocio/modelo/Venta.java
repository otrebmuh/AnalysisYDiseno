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

* @author Kevin Dydier López Flores
*/
@Entity
public class Venta {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idVenta;

    private Double total;

    private LocalDateTime date;

    private int idVendedor;

@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
@JoinColumn(name = "idVenta")
    private List<DescripcionVenta> productos = new ArrayList<>();
    public Venta() {
    }

        public void agregarDetalle(DescripcionVenta detalle) {
        this.productos.add(detalle);
        }

        public long getIdVenta() {
        return idVenta;
        }

        public void setIdVenta(long idVenta) {
        this.idVenta = idVenta;
        }

        public Double getTotal() {
        return total;
        }

        public void setTotal(Double total) {
        this.total = total;
        }

        public LocalDateTime getDate() {
        return date;
        }

        public void setDate(LocalDateTime date) {
        this.date = date;
        }

        public int getIdVendedor() {
        return idVendedor;
        }

        public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
        }

        public List<DescripcionVenta> getProductos() {
        return productos;
        }

        public void setProductos(List<DescripcionVenta> productos) {
        this.productos = productos;
        }

    @Override
    public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Venta other = (Venta) obj;
    return idVenta == other.idVenta;
    }

    @Override
    public int hashCode() {
    return Long.hashCode(idVenta);
    }
    
    @Override
    public String toString() {
    return "Venta{" +
    "id=" + idVenta +
    ", total=" + total +
    ", date=" + date +
    ", idVendedor=" + idVendedor +
    '}';
    }
    }