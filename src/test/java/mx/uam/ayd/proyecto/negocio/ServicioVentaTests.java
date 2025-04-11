package mx.uam.ayd.proyecto.negocio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

@ExtendWith(MockitoExtension.class)
class ServicioVentaTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private DetalleVentaRepository detalleVentaRepository;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private ServicioVenta servicio;

    /**
     * Prueba para el método nuevaVenta()
     */
    @Test
    void testNuevaVenta() {
        // Prueba 1: Crear una venta con datos válidos
    
        // Creamos los objetos necesarios
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(1L);
        
        Sucursal sucursal = new Sucursal();
        sucursal.setIdSucursal(1L);
        
        Venta ventaEsperada = new Venta();
        ventaEsperada.setIdVenta(1L);
        ventaEsperada.setEmpleado(empleado);
        ventaEsperada.setSucursal(sucursal);

        // Configuramos los mocks
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));
        when(ventaRepository.save(org.mockito.ArgumentMatchers.any(Venta.class))).thenReturn(ventaEsperada);

        // Ejecutamos el método y verificamos
        Long idVenta = servicio.nuevaVenta(1L, 1L);
        assertEquals(1L, idVenta);

        // Prueba 2: Intentar crear una venta con empleado inexistente
        when(empleadoRepository.findById(999L)).thenReturn(Optional.empty());
        
        assertThrows(IllegalArgumentException.class, () -> {
            servicio.nuevaVenta(999L, 1L);
        });
    }

    /**
     * Prueba para el método agregarProductoAVenta()
     */
    @Test
    void testAgregarProductoAVenta() {
        // Prueba 1: Agregar producto con inventario suficiente
        
        // Creamos los objetos necesarios
        Venta venta = new Venta();
        venta.setIdVenta(1L);
        
        Producto producto = new Producto();
        producto.setCodigo("123");
        
        Sucursal sucursal = new Sucursal();
        venta.setSucursal(sucursal);
        
        Inventario inventario = new Inventario();
        inventario.setStock(10);

        // Configuramos los mocks
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));
        when(productoRepository.findByCodigo("123")).thenReturn(Optional.of(producto));
        when(inventarioRepository.findBySucursalAndProducto(sucursal, producto))
            .thenReturn(Optional.of(inventario));

        // Ejecutamos y verificamos
        int cantidadAgregada = servicio.agregarProductoAVenta(1L, "123", 5);
        assertEquals(5, cantidadAgregada);

        // Prueba 2: Intentar agregar producto con inventario insuficiente
        assertThrows(IllegalArgumentException.class, () -> {
            servicio.agregarProductoAVenta(1L, "123", 20);
        });

        // Prueba 3: Intentar agregar producto a una venta inexistente
        when(ventaRepository.findById(999L)).thenReturn(Optional.empty());
        
        assertThrows(IllegalArgumentException.class, () -> {
            servicio.agregarProductoAVenta(999L, "123", 5);
        });
    }
}