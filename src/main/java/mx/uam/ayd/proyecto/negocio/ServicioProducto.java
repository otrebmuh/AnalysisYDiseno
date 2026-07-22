package mx.uam.ayd.proyecto.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.uam.ayd.proyecto.datos.ProductoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

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
 * Busca un producto por su clave.
 *
 * @param clave Clave del producto.
 * @return Producto encontrado o null si no existe.
 */
public Producto buscaProductoPorClave(String clave) {

    log.info("Buscando producto por clave: {}", clave);

    return productoRepository.findByClave(clave);
}

/**
 * Registra la entrada de mercancía.
 *
 * @param clave Clave del producto.
 * @param cantidad Cantidad a agregar.
 * @return true si se actualizó correctamente, false si no existe.
 */
public boolean registrarMercancia(String clave, int cantidad) {

    Producto producto = productoRepository.findByClave(clave);

    if (producto == null) {
        return false;
    }

    producto.setExistenciaActual(
            producto.getExistenciaActual() + cantidad);

    productoRepository.save(producto);

    log.info("Se registró entrada de {} unidades para el producto {}",
            cantidad, clave);

    return true;
}

}