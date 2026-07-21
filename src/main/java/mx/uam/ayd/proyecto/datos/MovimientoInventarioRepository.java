package mx.uam.ayd.proyecto.datos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.uam.ayd.proyecto.negocio.modelo.MovimientoInventario;

/**
 * Repositorio para acceder a los movimientos de inventario.
 *
 * @author Yamelin, Guillermo, Dydier, Yael, Sheyla
 */
@Repository
public interface MovimientoInventarioRepository
        extends CrudRepository<MovimientoInventario, Long> {

    /**
     * Recupera los movimientos cuyo tipo contiene el texto indicado.
     *
     * Por ejemplo:
     * ENTRADA, SALIDA o AJUSTE.
     *
     * @param tipoMovimiento texto utilizado como filtro
     * @return lista de movimientos encontrados
     */
    List<MovimientoInventario>
            findByTipoMovimientoContainingIgnoreCase(String tipoMovimiento);

    /**
     * Recupera los movimientos relacionados con un producto,
     * usando el nombre del producto como filtro.
     *
     * @param nombre nombre del producto
     * @return lista de movimientos encontrados
     */
    List<MovimientoInventario>
            findByProductoNombreContainingIgnoreCase(String nombre);

    /**
     * Recupera todos los movimientos ordenados por fecha descendente.
     *
     * @return lista de movimientos ordenados por fecha descendente
     */
    List<MovimientoInventario> findAllByOrderByFechaDesc();

    /**
     * Recupera los movimientos filtrados por tipo y ordenados
     * desde el más reciente.
     *
     * @param tipoMovimiento tipo del movimiento
     * @return lista de movimientos ordenados por fecha descendente
     */
    List<MovimientoInventario>
            findByTipoMovimientoContainingIgnoreCaseOrderByFechaDesc(
                    String tipoMovimiento);
}