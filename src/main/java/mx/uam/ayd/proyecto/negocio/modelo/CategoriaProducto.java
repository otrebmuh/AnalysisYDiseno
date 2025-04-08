package mx.uam.ayd.proyecto.negocio.modelo;

import javax.persistence.*;
import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;  

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class CategoriaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;

    @Column(unique = true, nullable = false)
    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    private List<Producto> productos = new ArrayList<>();

    public void agregarProducto(Producto producto) {
        if (producto != null && !productos.contains(producto)) {
            productos.add(producto);
            producto.setCategoria(this);
        }
    }

    public void eliminarProducto(Producto producto) {
        if (producto != null) {
            productos.remove(producto);
            producto.setCategoria(null);
        }
    }

    @Override
    public String toString() {
        return nombre;
    }
}
