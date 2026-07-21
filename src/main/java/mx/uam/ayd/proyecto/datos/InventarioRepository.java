package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Inventario;

/**
 * Repositorio para el control de Inventario
 *
 * @author KEVIN DYDIER, yael Mora Simón
 */
public interface InventarioRepository extends CrudRepository<Inventario, Long> {

    /**
     * Recupera el registro de inventario de un producto específico.
     *
     * @param idProducto
     * @return el registro de inventario
     */
    public Inventario findByIdProducto(long idProducto);

}
