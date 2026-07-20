
package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

/**
 * Repositorio para la entidad Producto.
 * @author JAVITOS
 */
public interface ProductoRepository extends CrudRepository<Producto, Long> {

    /**
     * Busca un producto específico por su nombre.
     * @param nombre El nombre del producto (ej. "Martillo")
     * @return
     */
    public Producto findByNombre(String nombre);
}

 