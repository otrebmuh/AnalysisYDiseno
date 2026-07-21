package mx.uam.ayd.proyecto.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.uam.ayd.proyecto.datos.ProductoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
/**
 * Servicio encargado de la lógica de negocio de los productos.
 *
 * @author Javitos
 */
@Service
public class ServicioProducto {

    // Logger
    private static final Logger log = LoggerFactory.getLogger(ServicioProducto.class);

    // Repositorio
    private final ProductoRepository productoRepository;

    @Autowired
    public ServicioProducto(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Recupera todos los productos registrados.
     * @return Todos los productos.
     */
    public Iterable<Producto> recuperaProductos() {
        log.info("Recuperando todos los productos");
        return productoRepository.findAll();
    }

    /**
     * Busca un producto por su nombre.
     * @param nombre Nombre del producto.
     * @return Producto encontrado o null si no existe.
     */
    public Producto buscaProducto(String nombre) {
        log.info("Buscando producto por nombre: {}", nombre);
        return productoRepository.findByNombre(nombre);
    }

    /**
     * Verifica si hay suficiente stock disponible para la venta.
     * Corresponde a la llamada verificaDisponibilidad(producto, cantidad)
     * 
     * @param producto El producto a verificar.
     * @param cantidad La cantidad solicitada por el cliente.
     * @return true si hay suficiente stock, false en caso contrario.
     */
    public boolean verificaDisponibilidad(Producto producto, int cantidad) {
        log.info("Verificando disponibilidad para el producto: {} (Cantidad solicitada: {})", 
                 producto != null ? producto.getNombre() : "null", cantidad);

        if (producto == null || cantidad <= 0) {
            log.warn("Producto nulo o cantidad inválida");
            return false;
        }

        // Verifica si el stock existente cubre la cantidad deseada
        boolean disponible = producto.getExistenciaActual() >= cantidad;
        
        if (!disponible) {
            log.warn("Stock insuficiente para {}. Disponible: {}, Solicitado: {}", 
                     producto.getNombre(), producto.getExistenciaActual(), cantidad);
        }

        return disponible;
    }
}