package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;


/**
 * Repositorio para la entidad Producto.
 * @author JAVITOS, Yamelin Larios Nepomuseno
 */
public interface ProductoRepository extends CrudRepository<Producto, Long> {

    /**
     * Busca un producto específico por su nombre.
     * @param nombre El nombre del producto (ej. "Martillo")
     * @return
     */
    public Producto findByNombre(String nombre);

    /**
     * Busca un producto específico por su idProducto (Necesario para HU09 - Actualizar Precios)
     * @param idProducto El identificador único del producto
     * @return El objeto Producto encontrado o null
     */
    public Producto findByIdProducto(long idProducto);
}