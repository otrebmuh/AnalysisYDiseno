package mx.uam.ayd.proyecto.negocio.modelo;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;

    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String numeroEmpleado;
    private String correoElectronico;
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "idSucursal")
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "idTipoEmpleado")
    private TipoEmpleado tipo;
}