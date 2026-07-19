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
////////////////////////////////////////////////////////////////////////////////////////77
@Service
public class ServicioVenta {

    private static final Logger log = LoggerFactory.getLogger(ServicioVenta.class);

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    @Autowired
    public ServicioVenta(VentaRepository ventaRepository, ProductoRepository productoRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
    }

    /**
     * Registra una venta completa, calcula el cambio y actualiza el inventario.
     *
     * @param detalles Lista de productos y cantidades seleccionados.
     * @param montoRecibido Cantidad de dinero con la que paga el cliente.
     * @return La venta registrada con sus cálculos de cambio.
     * @throws IllegalArgumentException si hay errores de validación o pago insuficiente.
     */
    public Venta registrarVenta(List<DescripcionVenta> detalles, double montoRecibido) {


        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("No se puede registrar una venta sin productos");
        }

        double totalVenta = 0;
        double gananciaVenta = 0;
//////////////////////////////////////////////////////////////////////////// VALIDAR REGLAS DE NEGOCIO
        for (DescripcionVenta detalle : detalles) {
            Producto producto = detalle.getProducto();

            if (producto == null) {
                throw new IllegalArgumentException("Uno de los productos en la lista no existe");
            }

            // El precio debe ser mayor a cero RN-04
            if (detalle.getPrecioUnitario() <= 0) {
                throw new IllegalArgumentException("El precio de " + producto.getNombre() + " debe ser mayor a cero");
            }

            // No hay transacción si no hay stock suficiente RN-09
            if (producto.getExistenciaActual() < detalle.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para " + producto.getNombre() + ". Disponibles: " + producto.getExistenciaActual());
            }
            
            totalVenta += (detalle.getPrecioUnitario() * detalle.getCantidad());
        }

        //VALIDACIÓN DEL PAGO
        if (montoRecibido < totalVenta) {
            throw new IllegalArgumentException("El monto recibido ($" + montoRecibido + ") es insuficiente para cubrir el total de $" + totalVenta);
        }

        log.info("Registrando nueva venta con " + detalles.size() + " partidas.");

        //CREAR OBJETO VENTA Y CALCULAR CAMBIO
        Venta venta = new Venta();
        venta.setDate(LocalDateTime.now());
        venta.setTotal(totalVenta);
        venta.setMontoRecibido(montoRecibido);
        venta.setCambio(montoRecibido - totalVenta); // Escenario 1

        //ACTUALIZACIÓN DE STOCK Y PROCESAMIENTO
        for (DescripcionVenta detalle : detalles) {
            Producto producto = detalle.getProducto();

            // Actualiza el Stock en el objeto
            int nuevoStock = producto.getExistenciaActual() - detalle.getCantidad();
            producto.setExistenciaActual(nuevoStock);
            
            // Persistir el cambio de stock (Update) [5]
            productoRepository.save(producto);

            // Cálculos para utilidad (Opcional según Guía de Implementación) [1]
            gananciaVenta += (detalle.getPrecioUnitario() - producto.getPrecioCompra()) * detalle.getCantidad();

            venta.agregarDetalle(detalle);
        }

        //PERSISTENCIA DEFINITIVA DE LA VENTA
        Venta ventaRegistrada = ventaRepository.save(venta);
        
        log.info("Venta registrada exitosamente con ID: " + ventaRegistrada.getIdVenta() +  ". Cambio a entregar: $" + ventaRegistrada.getCambio() +
". Ganancia obtenida: $" + gananciaVenta);

        return ventaRegistrada;
    }

    /**
     * Recupera todas las ventas realizadas.
     *
     * @return Lista de ventas registradas en el historial.
     */
    public List<Venta> recuperaVentas() {
        List<Venta> ventas = new ArrayList<>();
        for (Venta v : ventaRepository.findAll()) {
            ventas.add(v);
        }
        return ventas;
    }
}