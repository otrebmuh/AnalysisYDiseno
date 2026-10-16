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
     * Busca un producto por su clave única de negocio (RN-08).
     * Indispensable para la identificación en la HU-03.
     * @param clave La clave única del producto
     * @return el producto o null si no existe
     */
    public Producto findByClave(String clave);

    /**
     * Busca un producto específico por su idProducto.
     * Indispensable para la actualización de precios en la HU09.
     * @param idProducto El identificador único del producto
     * @return el producto o null si no existe
     */
    public Producto findByIdProducto(long idProducto);
}