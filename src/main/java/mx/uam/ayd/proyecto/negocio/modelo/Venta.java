package mx.uam.ayd.proyecto.negocio.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;


@Entity
@Data
public class Venta {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idVenta;
    
    private Date fecha;
    private long idEmpleado;

    @OneToMany(targetEntity = DVenta.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idDVenta")
    private List<DVenta> detalles= new ArrayList<>();

}
