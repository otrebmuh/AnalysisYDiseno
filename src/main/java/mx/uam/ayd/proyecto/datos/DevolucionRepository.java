package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

import mx.uam.ayd.proyecto.negocio.modelo.Devolucion;

/**
 * Repositorio para la gestión de Devoluciones (HU-10).
 * 
 * @author Yamelin Larios Nepomuseno
 */
public interface DevolucionRepository extends CrudRepository<Devolucion, Long> {

    /**
     * Encuentra todas las devoluciones asociadas a un producto por su ID.
     * 
     * @param productoId identificador único del producto
     * @return lista de devoluciones asociadas al producto
     */
    public List<Devolucion> findByProductoId(Long productoId);

}