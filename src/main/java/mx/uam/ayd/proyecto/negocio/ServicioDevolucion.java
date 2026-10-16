package mx.uam.ayd.proyecto.negocio;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.uam.ayd.proyecto.datos.DevolucionRepository;
import mx.uam.ayd.proyecto.datos.ProductoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Devolucion;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

/**
 * Servicio encargado de la lógica de negocio para la devolución de productos dañados a proveedores (HU10).
 *
 * @author Yamelin Larios Nepomuseno
 */
@Service
public class ServicioDevolucion {

    private static final Logger log = LoggerFactory.getLogger(ServicioDevolucion.class);

    private final DevolucionRepository devolucionRepository;
    private final ProductoRepository productoRepository;

    @Autowired
    public ServicioDevolucion(DevolucionRepository devolucionRepository, ProductoRepository productoRepository) {
        this.devolucionRepository = devolucionRepository;
        this.productoRepository = productoRepository;
    }

    /**
     * Registra la devolución de un producto dañado al proveedor y actualiza el inventario (HU10).
     *
     * @param idProducto Identificador del producto a devolver
     * @param cantidad Cantidad de unidades defectuosas a devolver
     * @param motivo Explicación o causa del daño/devolución
     * @return El objeto Devolucion persistido
     */
    @Transactional
    public Devolucion registrarDevolucionDanado(long idProducto, int cantidad, String motivo) {
        log.info("Iniciando proceso de devolución por daño para producto ID: {}, Cantidad: {}", idProducto, cantidad);

        // Validaciones de negocio
        if (cantidad <= 0) {
            log.warn("La cantidad a devolver debe ser mayor a cero: {}", cantidad);
            throw new IllegalArgumentException("La cantidad a devolver debe ser mayor a cero.");
        }

        if (motivo == null || motivo.trim().isEmpty()) {
            log.warn("El motivo de la devolución es obligatorio");
            throw new IllegalArgumentException("Debes especificar el motivo de la devolución por daño.");
        }

        Producto producto = productoRepository.findByIdProducto(idProducto);
        if (producto == null) {
            log.warn("No se encontró el producto con ID: {}", idProducto);
            throw new IllegalArgumentException("No se encontró ningún producto registrado con el ID: " + idProducto);
        }

        if (producto.getExistenciaActual() < cantidad) {
            log.warn("Existencia insuficiente para devolver. Disponible: {}, Solicitado: {}", 
                    producto.getExistenciaActual(), cantidad);
            throw new IllegalArgumentException("No hay suficiente stock disponible para devolver esa cantidad. Stock actual: " 
                    + producto.getExistenciaActual());
        }

        // 1. Descontar la mercancía dañada del inventario
        int nuevaExistencia = producto.getExistenciaActual() - cantidad;
        producto.setExistenciaActual(nuevaExistencia);
        productoRepository.save(producto);

        // 2. Crear y guardar el registro de devolución
        Devolucion devolucion = new Devolucion();
        devolucion.setIdProducto(idProducto);
        devolucion.setCantidad(cantidad);
        devolucion.setMotivo(motivo);
        devolucion.setTipoDevolucion("DAÑADO");
        devolucion.setFecha(LocalDateTime.now()); // Correcto: se utiliza setFecha() en lugar de setFechaHora()

        Devolucion devolucionGuardada = devolucionRepository.save(devolucion);
        log.info("Devolución registrada exitosamente con ID: {} para el producto {}", 
                devolucionGuardada.getIdDevolucion(), producto.getNombre());

        return devolucionGuardada;
    }
}