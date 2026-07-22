package mx.uam.ayd.proyecto.negocio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.uam.ayd.proyecto.datos.ProductoRepository;
import mx.uam.ayd.proyecto.datos.VentaRepository;
import mx.uam.ayd.proyecto.negocio.modelo.DescripcionVenta;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.negocio.modelo.Venta;

/**
 * @author KEVIN DYDIER
 */
@Service
public class ServicioVenta {

    // Define a static logger field
    private static final Logger log = LoggerFactory.getLogger(ServicioVenta.class);

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    @Autowired
    public ServicioVenta(VentaRepository ventaRepository, ProductoRepository productoRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
    }

    /**
     * Registra una venta completa y actualiza el inventario.
     *
     * @param detalles Lista de productos y cantidades seleccionados.
     * @return La venta registrada.
     * @throws IllegalArgumentException si la lista es nula, vacía o viola reglas de negocio.
     */

///////////////////////////////////7Validación de Reglas de Negocio

    public Venta registrarVenta(List<DescripcionVenta> detalles) {

        // Validar que la lista de detalles no sea nula o vacía
        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("No se puede registrar una venta sin productos");
        }

        double totalVenta = 0;
        double gananciaVenta = 0;

        for (DescripcionVenta detalle : detalles) {
            Producto producto = detalle.getProducto();

            if (producto == null) {
                throw new IllegalArgumentException("Uno de los productos en la lista no existe");
            }

            //El precio debe ser mayor a cero RN-04
            if (detalle.getPrecioUnitario() <= 0) {
                throw new IllegalArgumentException("El precio de " + producto.getNombre() + " debe ser mayor a cero");
            }

            //No hay transacción si no hay stock suficiente RN-09
            if (producto.getExistenciaActual() < detalle.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para " + producto.getNombre() +". Disponibles: " + producto.getExistenciaActual());
            }
        }

        // Se validaron correctamente las reglas de negocio

        // Bitácora del inicio de la operación
        log.info("Registrando nueva venta con " + detalles.size() + " partidas.");

        //cREAR VENTA
        Venta venta = new Venta();
        venta.setDate(LocalDateTime.now());

        for (DescripcionVenta detalle : detalles) {
            Producto producto = detalle.getProducto();

            //Actualiza el Stock
            int nuevoStock = producto.getExistenciaActual() - detalle.getCantidad();
            producto.setExistenciaActual(nuevoStock);
            
            // Esto es el update
            productoRepository.save(producto);

            // Cálculos para ele total y ganancia
            totalVenta += (detalle.getPrecioUnitario() * detalle.getCantidad());
            gananciaVenta += (detalle.getPrecioUnitario() - producto.getPrecioCompra()) * detalle.getCantidad();

            venta.agregarDetalle(detalle);
        }

        venta.setTotal(totalVenta);

        //Persistencia definitiva de la venta
        Venta ventaRegistrada = ventaRepository.save(venta);
        
        log.info("Venta registrada exitosamente con ID: " + ventaRegistrada.getIdVenta() + ". Ganancia obtenida: $" + gananciaVenta);

        return ventaRegistrada;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Recupera todas las ventas
     *
     * @return Lista de ventas
     */
    public List<Venta> recuperaVentas() {
        List<Venta> ventas = new ArrayList<>();
        for (Venta v : ventaRepository.findAll()) {
            ventas.add(v);
        }
        return ventas;
    }
}