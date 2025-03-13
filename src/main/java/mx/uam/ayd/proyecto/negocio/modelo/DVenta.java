package mx.uam.ayd.proyecto.negocio.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class DVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDVenta;
    
    @ManyToOne
    @JoinColumn(name="idVenta")
    Venta venta;

    @ManyToOne
    @JoinColumn(name="idProducto")
    Producto producto;

    private int cantidad;
    private float precio;

}
