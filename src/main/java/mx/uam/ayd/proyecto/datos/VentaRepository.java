
package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Venta;

/**
 * Repositorio para la entidad Venta.
 * @author JAVITOS
 */
public interface VentaRepository extends CrudRepository<Venta, Long> {
}

