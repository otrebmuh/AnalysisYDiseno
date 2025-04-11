package mx.uam.ayd.proyecto.negocio.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Data;

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
    
    private Boolean atendida = false;

    @OneToMany(mappedBy = "solicitudReabastecimiento", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
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
