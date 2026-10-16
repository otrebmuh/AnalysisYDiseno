package mx.uam.ayd.proyecto.negocio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.MovimientoInventarioRepository;
import mx.uam.ayd.proyecto.negocio.modelo.MovimientoInventario;

/**
 * Servicio encargado de la lógica de negocio de los movimientos
 * de inventario.
 *
 * @author Yael Mora Simón
 */
@Service
public class ServicioMovimientoInventario {

    @Autowired
    private MovimientoInventarioRepository movimientoInventarioRepository;

    /**
     * Recupera todos los movimientos registrados.
     *
     * @return lista de movimientos
     */
    public List<MovimientoInventario> obtenerMovimientos() {

        return movimientoInventarioRepository.findAllByOrderByFechaDesc();
    }

    /**
     * Busca movimientos por tipo.
     *
     * @param filtro texto del filtro
     * @return lista de movimientos encontrados
     */
    public List<MovimientoInventario> buscarMovimiento(String filtro) {

        if (filtro == null || filtro.trim().isEmpty()) {
            return obtenerMovimientos();
        }

        return movimientoInventarioRepository
                .findByTipoMovimientoContainingIgnoreCaseOrderByFechaDesc(filtro);
    }

    /**
     * Recupera un movimiento por su identificador.
     *
     * @param idMovimiento identificador del movimiento
     * @return movimiento encontrado o null
     */
    public MovimientoInventario consultarDetalleMovimiento(long idMovimiento) {

        Optional<MovimientoInventario> movimiento =
                movimientoInventarioRepository.findById(idMovimiento);

        return movimiento.orElse(null);
    }

}