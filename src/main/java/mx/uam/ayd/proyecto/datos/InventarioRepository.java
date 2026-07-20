package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Inventario;

/**
 * Repositorio para la entidad Inventario.
 */
public interface InventarioRepository extends CrudRepository<Inventario, Long> {

}