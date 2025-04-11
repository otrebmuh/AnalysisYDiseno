package mx.uam.ayd.proyecto.negocio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.datos.EmpleadoRepository;
import mx.uam.ayd.proyecto.datos.SucursalRepository;
import mx.uam.ayd.proyecto.datos.VentaRepository;
import mx.uam.ayd.proyecto.datos.ProductoRepository;
import mx.uam.ayd.proyecto.datos.DetalleVentaRepository;
import mx.uam.ayd.proyecto.datos.InventarioRepository;

import mx.uam.ayd.proyecto.negocio.modelo.Venta;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.negocio.modelo.DetalleVenta;
import mx.uam.ayd.proyecto.negocio.modelo.Empleado;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;
import mx.uam.ayd.proyecto.negocio.modelo.Inventario;

@Slf4j
@Service
public class ServicioVenta {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    /**
     * Inicia una nueva venta
     * 
     * @param idEmpleado
     * @param idSucursal
     * @return ID de la venta creada
     */
    public Long nuevaVenta(Long idEmpleado, Long idSucursal) {


        Optional<Empleado> empleadoOpt = empleadoRepository.findById(idEmpleado);
        Optional<Sucursal> sucursalOpt = sucursalRepository.findById(idSucursal);

        if (!empleadoOpt.isPresent() || !sucursalOpt.isPresent()) {
            throw new IllegalArgumentException("Empleado o Sucursal no encontrados");
        }

        Venta venta = new Venta();
        venta.setEmpleado(empleadoOpt.get());
        venta.setSucursal(sucursalOpt.get());

        venta = ventaRepository.save(venta);

        log.info("Venta creada con ID: " + venta.getId());

        return venta.getId();
    }

    /**
     * Agrega un producto a la venta
     * 
     * @param idVenta
     * @param codigoProducto
     * @param cantidad
     * @return Cantidad agregada
     */
    public int agregarProductoAVenta(Long idVenta, String codigoProducto, int cantidad) {

        Optional<Venta> ventaOpt = ventaRepository.findById(idVenta);
        if (!ventaOpt.isPresent()) {
            throw new IllegalArgumentException("Venta no encontrada");
        }

        Optional<Producto> productoOpt = productoRepository.findByCodigo(codigoProducto);
        if (!productoOpt.isPresent()) {
            throw new IllegalArgumentException("Producto no encontrado");
        }

        Venta venta = ventaOpt.get();
        Producto producto = productoOpt.get();

        Optional<Inventario> inventarioOpt = inventarioRepository
                .findBySucursalAndProducto(venta.getSucursal(), producto);

        if (!inventarioOpt.isPresent() || inventarioOpt.get().getStock() < cantidad) {
            throw new IllegalArgumentException("Inventario insuficiente");
        }

        //Comentare esta parte por que en el caso de cancelacion no deberia modificar el inventario
        // Reducir cantidad en inventario
        //Inventario inventario = inventarioOpt.get();
        //inventario.setStock(inventario.getStock() - cantidad);  //En el diagrama de secuencias dice setCantidad pero creo que se refiera al Stock
        //inventarioRepository.save(inventario);





        // Crear detalle de venta
        DetalleVenta detalle = new DetalleVenta();
        detalle.setVenta(venta);
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(producto.getPrecio());




        detalle.setSubtotal(producto.getPrecio() * cantidad);

        detalleVentaRepository.save(detalle);


        


        log.info("Producto agregado: " + producto.getCodigo() + " Cantidad: " + cantidad);

        return cantidad;
    }

    /**
     * Finaliza la venta y reduce el inventario
     * 
     * @param idVenta
     * @return true si la venta se finalizó correctamente, false en caso contrario
     */
    public boolean finalizarVenta(Long idVenta) {

        Optional<Venta> ventaOpt = ventaRepository.findById(idVenta);

        if (!ventaOpt.isPresent()) {
            throw new IllegalArgumentException("Venta no encontrada");
        }

        Venta venta = ventaOpt.get();

        //Se hace la suma de todas las cantidades de los productos que coinciden con la venta
        List<DetalleVenta> detalles = detalleVentaRepository.findByVenta_IdVenta(idVenta);
        int totalCantidad = detalles.stream()
                .mapToInt(DetalleVenta::getCantidad)
                .sum();
        
        //Actualizar Inventario
        actualizarStockVenta(idVenta);

        //Se hace la suma de los productos que se agregaron en la venta
        List<DetalleVenta> detalles_total = detalleVentaRepository.findByVenta_IdVenta(idVenta);
        Double totalPrecio = detalles_total.stream()
                .mapToDouble(DetalleVenta::getSubtotal)
                .sum();
        
                
        //Se actualiza la venta con la informacion correspondiente
        venta.setCantidadProductos(totalCantidad);
        venta.setPrecioTotal(totalPrecio);

        venta.setFinalizada(true);
        venta.setFecha(new java.util.Date());
        ventaRepository.save(venta);

        log.info("Venta finalizada con ID: " + idVenta);

        return true;
    }

    public void actualizarStockVenta(Long idVenta) {
        Venta venta = ventaRepository.findByIdVenta(idVenta);
        if (venta == null) {
            return;
        }

        List<DetalleVenta> detalles = detalleVentaRepository.findByVenta_IdVenta(idVenta);

        for (DetalleVenta detalle : detalles) {
            Producto producto = detalle.getProducto();
            Sucursal sucursal = venta.getSucursal();
            int cantidadVendida = detalle.getCantidad();

            Optional<Inventario> inventarioOpt = inventarioRepository.findBySucursalAndProducto(sucursal, producto);

            if (inventarioOpt.isPresent()) {
                Inventario inventario = inventarioOpt.get();

                if (inventario.tieneStock(cantidadVendida)) {
                    inventario.disminuirStock(cantidadVendida);
                    inventarioRepository.save(inventario);
                } else {
                    System.out.println("❌ Stock insuficiente para producto: " + producto.getNombre());
                }
            } else {
                System.out.println("❌ No se encontró inventario para producto " + producto.getNombre()
                        + " en sucursal " + sucursal.getNombre());
            }
        }
    }

    
    public Long obtenerUltimoIdDetalleVenta() {
        Long ultimoId = detalleVentaRepository.findUltimoId();
        return ultimoId;
    }

    public void eliminarDetalleVenta(Long idDetalle) {
        detalleVentaRepository.deleteById(idDetalle-1L);//-1L para que no se elimine el detalle de venta con id 0, ya que no existe
        log.info("Detalle de venta eliminado con ID: " + idDetalle);

    }
        
}
//Comentario para poder hacer commit despues de haber agregado todos los modelos, repositorios y ServicioVentaS
