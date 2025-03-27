package mx.uam.ayd.proyecto.negocio.modelo;

import javax.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class CategoriaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;

    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;
}

