package mx.uam.ayd.proyecto.negocio.modelo;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class SolicitudReabastecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSolicitudReabastecimiento;

    @ManyToOne
    @JoinColumn(name = "idSucursal", nullable = false)
    private Sucursal sucursal;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @OneToMany(mappedBy = "solicitudReabastecimiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallesSolicitud> detalles = new ArrayList<>();

    // Métodos para gestionar la relación bidireccional
    public void agregarDetalle(DetallesSolicitud detalle) {
        detalles.add(detalle);
        detalle.setSolicitudReabastecimiento(this);
    }

    public void removerDetalle(DetallesSolicitud detalle) {
        detalles.remove(detalle);
        detalle.setSolicitudReabastecimiento(null);
    }
    
    // Método para obtener el ID
    public Long getId() {
        return idSolicitudReabastecimiento;
    }
}
