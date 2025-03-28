package mx.uam.ayd.proyecto.negocio.modelo;

import javax.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Laboratorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLaboratorio;

    private String nombre;

    @OneToMany(mappedBy = "laboratorio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos;

    // Constructor sin argumentos requerido por JPA
    public Laboratorio() {}

    // Constructor con parámetros
    public Laboratorio(String nombre) {
        this.nombre = nombre;
    }
}
