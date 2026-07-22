package mx.uam.ayd.proyecto.negocio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.uam.ayd.proyecto.datos.BitacoraRepository;
import mx.uam.ayd.proyecto.datos.InventarioRepository;
import mx.uam.ayd.proyecto.datos.ProductoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Bitacora;
import mx.uam.ayd.proyecto.negocio.modelo.Inventario;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

/**
 *
 * @author Kevin Dydier
 */
@Service
public class ServicioInventario {

    private static final Logger log = LoggerFactory.getLogger(ServicioInventario.class);

    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;
    private final BitacoraRepository bitacoraRepository;

    @Autowired
    public ServicioInventario(InventarioRepository inventarioRepository, ProductoRepository productoRepository, BitacoraRepository bitacoraRepository) {
        this.inventarioRepository = inventarioRepository;
        this.productoRepository = productoRepository;
        this.bitacoraRepository = bitacoraRepository;
    }

    /**
     *
     * @return Lista de productos que requieren reabastecimiento (resaltados en rojo).
     */
    public List<Producto> consultarAlertas() {
        log.info("Iniciando consulta de alertas de inventario...");
        List<Producto> productosConAlerta = new ArrayList<>();

        //Recuperar todos los registros de inventario
        Iterable<Inventario> todosLosInventarios = inventarioRepository.findAll();

        for (Inventario item : todosLosInventarios) {
            //Validar Stock Mínimo
            if (item.getExistenciaActual() <= item.getStockMinimo()) {
                
                //Recuperar el producto para obtener su Clave Única
                Producto producto = productoRepository.findById(item.getIdProducto()).orElse(null);

                if (producto != null) {
                    productosConAlerta.add(producto);
                    
                    //Registrar el evento en la Bitácora
                    registrarEventoAlerta(producto.getIdProducto(), producto.getClave());
                }
            }
        }

        log.info("Consulta finalizada. Se encontraron {} productos con stock bajo.", productosConAlerta.size());
        return productosConAlerta;
    }


    //Métod para encapsular el registro de acciones de control

    private void registrarEventoAlerta(long idProducto, String claveProducto) {
        Bitacora evento = new Bitacora();
        evento.setIdProducto(idProducto);
        evento.setFechaHora(LocalDateTime.now());
        evento.setMotivo("Emisión de alerta visual por stock bajo: " + claveProducto);
        
        bitacoraRepository.save(evento);
        log.debug("Evento de alerta registrado para producto: {}", claveProducto);
    }
    
    /**
     * Recupera el detalle de un producto específico para la vista.
     *
     * @param idProducto
     * @return El producto solicitado
     */
    public Producto obtenerDetalleProducto(long idProducto) {
        return productoRepository.findById(idProducto).orElse(null);
    }
     
    /**
 * Recupera todos los registros del inventario.
 *
 * @return Lista de inventarios.
 */
public List<Inventario> recuperaInventario() {

    List<Inventario> inventarios = new ArrayList<>();

    for (Inventario inventario : inventarioRepository.findAll()) {
        inventarios.add(inventario);
    }

    return inventarios;
}

/**
 * Recupera únicamente los productos con bajo stock.
 *
 * @return Lista de inventarios con bajo stock.
 */
public List<Inventario> obtenerProductosBajoStock() {

    List<Inventario> productosBajoStock = new ArrayList<>();

    for (Inventario inventario : inventarioRepository.findAll()) {

        if (inventario.getExistenciaActual() <= inventario.getStockMinimo()) {
            productosBajoStock.add(inventario);
        }
    }

    return productosBajoStock;
}

}
