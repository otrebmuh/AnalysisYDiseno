package mx.uam.ayd.proyecto.negocio.modelo;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class DetallesSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetallesSolicitud;

    @ManyToOne
    @JoinColumn(name = "idSolicitudReabastecimiento", nullable = false)
    private SolicitudReabastecimiento solicitudReabastecimiento;

    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;

    private Integer cantidad;
}
