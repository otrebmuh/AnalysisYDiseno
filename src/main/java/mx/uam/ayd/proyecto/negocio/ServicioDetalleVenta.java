package mx.uam.ayd.proyecto.negocio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.DetalleVentaRepository;
import mx.uam.ayd.proyecto.datos.ProductoRepository;
import mx.uam.ayd.proyecto.datos.VentaRepository;
import mx.uam.ayd.proyecto.negocio.modelo.DetalleVenta;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.negocio.modelo.Venta;

@Service
public class ServicioDetalleVenta {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    /**
     * Obtiene todos los detalles de venta registrados
     * 
     * @return Lista de detalles de venta
     */
    public List<DetalleVenta> getAll() {
        return StreamSupport.stream(detalleVentaRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un detalle de venta por su ID
     * 
     * @param id ID del detalle de venta
     * @return DetalleVenta si existe, null si no
     */
    public DetalleVenta obtenerPorId(Long id) {
        Optional<DetalleVenta> detalle = detalleVentaRepository.findById(id);
        return detalle.orElse(null);
    }

    /**
     * Obtiene los detalles de una venta específica
     * 
     * @param idVenta ID de la venta
     * @return Lista de detalles de venta
     */
    /*
     * public List<DetalleVenta> obtenerPorVenta(Long idVenta) {
     * return detalleVentaRepository.findByIdVenta(idVenta);
     * }
     */

    /**
     * Registra un nuevo detalle de venta
     * 
     * @param detalleVenta Detalle de venta a registrar
     * @return Detalle de venta registrado
     */
    public DetalleVenta crear(DetalleVenta detalleVenta) {
        // Validar que la venta existe
        if (detalleVenta.getVenta() != null && detalleVenta.getVenta().getIdVenta() != null) {
            Venta venta = ventaRepository.findById(detalleVenta.getVenta().getIdVenta())
                    .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
            detalleVenta.setVenta(venta);
        }

        // Validar que el producto existe
        if (detalleVenta.getProducto() != null && detalleVenta.getProducto().getIdProducto() != null) {
            Producto producto = productoRepository.findById(detalleVenta.getProducto().getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            detalleVenta.setProducto(producto);
        }

        // Calcular subtotal
        detalleVenta.calcularSubtotal();

        return detalleVentaRepository.save(detalleVenta);
    }

    /**
     * Actualiza un detalle de venta existente
     * 
     * @param detalleVenta Detalle de venta a actualizar
     * @return Detalle de venta actualizado
     */
    public DetalleVenta actualizar(DetalleVenta detalleVenta) {
        if (!detalleVentaRepository.existsById(detalleVenta.getIdDetalleVenta())) {
            return null;
        }

        // Validar que la venta existe
        if (detalleVenta.getVenta() != null && detalleVenta.getVenta().getIdVenta() != null) {
            Venta venta = ventaRepository.findById(detalleVenta.getVenta().getIdVenta())
                    .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
            detalleVenta.setVenta(venta);
        }

        // Validar que el producto existe
        if (detalleVenta.getProducto() != null && detalleVenta.getProducto().getIdProducto() != null) {
            Producto producto = productoRepository.findById(detalleVenta.getProducto().getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            detalleVenta.setProducto(producto);
        }

        // Calcular subtotal
        detalleVenta.calcularSubtotal();

        return detalleVentaRepository.save(detalleVenta);
    }

    /**
     * Elimina un detalle de venta
     * 
     * @param id ID del detalle de venta a eliminar
     * @return true si se eliminó, false si no existía
     */
    public boolean eliminar(Long id) {
        if (!detalleVentaRepository.existsById(id)) {
            return false;
        }
        detalleVentaRepository.deleteById(id);
        return true;
    }

    /**
     * Valida que los campos del detalle de venta sean correctos
     * 
     * @param detalleVenta Detalle de venta a validar
     * @return true si es válido, false si no
     */
    public boolean validarDetalleVenta(DetalleVenta detalleVenta) {
        if (detalleVenta == null)
            return false;
        if (detalleVenta.getVenta() == null || detalleVenta.getVenta().getIdVenta() == null)
            return false;
        if (detalleVenta.getProducto() == null || detalleVenta.getProducto().getIdProducto() == null)
            return false;
        if (detalleVenta.getCantidad() == null || detalleVenta.getCantidad() <= 0)
            return false;
        if (detalleVenta.getPrecioUnitario() == null || detalleVenta.getPrecioUnitario() <= 0)
            return false;
        return true;
    }
}