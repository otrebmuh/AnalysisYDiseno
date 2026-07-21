package mx.uam.ayd.proyecto.negocio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.uam.ayd.proyecto.datos.ProductoRepository;
import mx.uam.ayd.proyecto.datos.VentaRepository;
import mx.uam.ayd.proyecto.negocio.modelo.DescripcionVenta;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.negocio.modelo.Venta;

@Service
public class ServicioVenta {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Registra una nueva venta, calcula el cambio y actualiza el stock
     *
     * @param detalles Lista de productos y cantidades procesadas en la HU-05.
     * @param montoRecibido Cantidad con la que pagó el cliente
     * @return El objeto Venta persistido.
     */
    @Transactional // Asegura que si algo falla, no se descuente el stock ni se guarde la venta
    public Venta registrarVenta(List<DescripcionVenta> detalles, double montoRecibido) {
        
        double totalVenta = 0;

        //Procesar cada detalle para actualizar el inventario
        for (DescripcionVenta detalle : detalles) {
            Producto producto = detalle.getProducto();
            
            // Recuperación y actualización técnica según la Guía
            int nuevaExistencia = producto.getExistenciaActual() - detalle.getCantidad();
            producto.setExistenciaActual(nuevaExistencia);
            
            // Persistir el cambio en el stock del producto
            productoRepository.save(producto);
            
            totalVenta += (detalle.getPrecioUnitario() * detalle.getCantidad());
        }

        //Instanciar y llenar el objeto Venta
        Venta nuevaVenta = new Venta();
        nuevaVenta.setFecha(LocalDateTime.now());
        nuevaVenta.setTotal(totalVenta);
        nuevaVenta.setPago(montoRecibido);
        nuevaVenta.setCambio(montoRecibido - totalVenta);
        
        // Vincular los detalles a la venta
        for (DescripcionVenta detalle : detalles) {
            nuevaVenta.addDetalle(detalle);
        }

        //Persistir la venta en el repositorio
        return ventaRepository.save(nuevaVenta);
    }
}