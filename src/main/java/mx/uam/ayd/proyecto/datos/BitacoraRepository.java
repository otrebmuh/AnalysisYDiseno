
package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Bitacora;

/**
 * Repositorio para el registro de acciones en Bitácora a
 * @author KEVIN DYDIER *
 */
public interface BitacoraRepository extends CrudRepository <Bitacora, Long> {
	
	/**
	 * Encuentra registros de bitácora asociados a un producto específico
	 * @param idProducto
	 * @return lista de eventos relacionados al producto
	 */
	public java.util.List<Bitacora> findByIdProducto(long idProducto);

}