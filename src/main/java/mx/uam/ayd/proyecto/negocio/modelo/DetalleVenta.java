package mx.uam.ayd.proyecto.negocio.modelo;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleVenta;

    @ManyToOne
    @JoinColumn(name = "idVenta", nullable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;

    private Integer cantidad;

    private Double precioUnitario;

    private Double subtotal;

    // Método para calcular el subtotal
    public void calcularSubtotal() {
        if (cantidad != null && precioUnitario != null) {
            this.subtotal = cantidad * precioUnitario;
        } else {
            this.subtotal = 0.0;
        }
    }
}
