package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import mx.uam.ayd.proyecto.presentacion.ControlHistorialMovimientos.ControlHistorialMovimientos;

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

    private Double total = 0.0;

    private LocalDateTime date;

    private int idVendedor;

    private double montoRecibido;
    
    private double cambio;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idVenta")
    private List<DescripcionVenta> productos = new ArrayList<>();

    public Venta() {
    }
/**
     * Agrega un producto y su cantidad a la venta actual.
     * Crea un nuevo objeto DescripcionVenta, lo asocia al producto y actualiza
     * el total de la venta.
     
     * @param producto El objeto Producto que se va a agregar a la venta.
     * @param cantidad La cantidad de unidades del producto.
     * @return true si el producto se agregó correctamente; false si el producto es nulo o la cantidad es menor o igual a cero.
     */

    public boolean agregaProducto(Producto producto, int cantidad) {
        if (producto == null || cantidad <= 0) {    //Valida que el producto exista y que la cantidad sea mayor a 0
            return false;
        }

        // Crea el detalle/descripción de la venta
        DescripcionVenta detalle = new DescripcionVenta();
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);

        // Agrega a la lista
        this.productos.add(detalle);

        // Recalcula el total de la venta
        calculaTotal();

        return true;
    }

    public void calculaTotal() {
        double acumulado = 0.0;
        for (DescripcionVenta detalle : productos) {
            if (detalle.getProducto() != null) {
                acumulado += detalle.getProducto().getPrecio() * detalle.getCantidad();
            }
        }
        //Asigna el valor final a total
        this.total = acumulado;
    }

    public void agregarDetalle(DescripcionVenta detalle) {
        this.productos.add(detalle);
    }

  /////////////////////////////////////////////////////////////////////////7 metodos que se ocupan en el servicio venta
    
    
    public void addDetalle(DescripcionVenta detalle) {
        this.agregarDetalle(detalle);
    }

    
    public void setFecha(LocalDateTime fecha) {
        this.date = fecha;
    }

    
    public void setPago(double pago) {
        this.montoRecibido = pago;
    }

    ////////////////////////////////////////////////////////////////////////////////////////7

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

    public double getMontoRecibido() {
        return montoRecibido;
    }

    public void setMontoRecibido(double montoRecibido) {
        this.montoRecibido = montoRecibido;
    }

    public double getCambio() {
        return cambio;
    }

    public void setCambio(double cambio) {
        this.cambio = cambio;
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
                ", pago=" + montoRecibido +
                ", cambio=" + cambio +
                ", date=" + date +
                ", idVendedor=" + idVendedor +
                '}';
    }
    public static void muestraMovimientos(List<MovimientoInventario> movimientos) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'muestraMovimientos'");
    }
    public static void muestra() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'muestra'");
    }
    public static void setControl(ControlHistorialMovimientos controlHistorialMovimientos) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setControl'");
    }
}