package mx.uam.ayd.proyecto.negocio.modelo;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInventario;

    @ManyToOne
    @JoinColumn(name = "idSucursal", nullable = false)
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;

    private Integer stock;

    public boolean tieneStock(int cantidad) {
        return stock != null && stock >= cantidad;
    }

    public void disminuirStock(int cantidad) {
        if (stock != null && cantidad > 0) {
            stock -= cantidad;
        }
    }

    public void agregarStock(int cantidad) {
        if (stock == null) {
            stock = 0;
        }
        if (cantidad > 0) {
            stock += cantidad;
        }
    }
}
