package mx.uam.ayd.proyecto.negocio.modelo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Ingrediente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIngrediente;

    @NonNull
    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @OneToMany(mappedBy = "ingrediente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    private List<Producto> productos = new ArrayList<>();

    /**
     * Constructor con parámetros requeridos
     * @param nombre Nombre del ingrediente (obligatorio)
     * @param descripcion Descripción del ingrediente (opcional)
     */
    public Ingrediente(@NonNull String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /**
     * Agrega un producto al ingrediente
     * @param producto Producto a agregar
     */
    public void agregarProducto(Producto producto) {
        if (producto != null) {
            producto.setIngrediente(this);
            productos.add(producto);
        }
    }

    /**
     * Elimina un producto del ingrediente
     * @param producto Producto a eliminar
     */
    public void eliminarProducto(Producto producto) {
        if (producto != null) {
            producto.setIngrediente(null);
            productos.remove(producto);
        }
    }

    /**
     * Verifica si el ingrediente es válido
     * @return true si es válido, false si no
     */
    public boolean isValid() {
        return nombre != null && !nombre.trim().isEmpty();
    }
}
