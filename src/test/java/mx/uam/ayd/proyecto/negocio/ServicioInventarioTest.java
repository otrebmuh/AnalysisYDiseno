package mx.uam.ayd.proyecto.negocio;

import mx.uam.ayd.proyecto.datos.InventarioRepository;
import mx.uam.ayd.proyecto.datos.ProductoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Inventario;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServicioInventarioTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ServicioInventario servicioInventario;

    private final int CANTIDAD = 10;
    private final int CANTIDAD_AGREGAR = 5;
    private final int CANTIDAD_DISMINUIR = 3;
    private Producto producto;
    private Inventario inventario;
    private Sucursal sucursal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear objetos de prueba
        producto = new Producto();
        producto.setIdProducto(1L);
        producto.setNombre("ProductoTest");

        sucursal = new Sucursal();
        sucursal.setIdSucursal(1L);
        sucursal.setNombre("SucursalTest");

        inventario = new Inventario();
        inventario.setIdInventario(1L);
        inventario.setSucursal(sucursal);
        inventario.setProducto(producto);
        inventario.setStock(CANTIDAD);
    }

    @Test
    void testActualizarInventarioNuevo() {
        // No existe inventario previo
        when(inventarioRepository.findBySucursalAndProducto(any(), any())).thenReturn(Optional.empty());

        // Ejecutar el método
        servicioInventario.actualizarInventario(sucursal, producto, CANTIDAD_AGREGAR);

        // Verificar que se creó un nuevo inventario
        verify(inventarioRepository).save(any(Inventario.class));
    }

    @Test
    void testActualizarInventarioExistente() {
        // Existe inventario previo
        when(inventarioRepository.findBySucursalAndProducto(any(), any())).thenReturn(Optional.of(inventario));

        // Ejecutar el método
        servicioInventario.actualizarInventario(sucursal, producto, CANTIDAD_AGREGAR);

        // Verificar que se actualizó el stock
        verify(inventarioRepository).save(inventario);
    }

    @Test
    void testObtenerInventario() {
        // Configurar productoRepository
        List<Producto> productos = new ArrayList<>();
        productos.add(producto);
        when(productoRepository.findAll()).thenReturn(productos);

        // Configurar inventarioRepository
        when(inventarioRepository.findBySucursalAndProducto(any(), any())).thenReturn(Optional.of(inventario));

        // Ejecutar el método
        List<Inventario> inventarios = servicioInventario.obtenerInventario(sucursal);

        // Verificar el resultado
        assertNotNull(inventarios);
        assertEquals(1, inventarios.size());
        assertEquals(CANTIDAD, inventarios.get(0).getStock());
    }

    @Test
    void testObtenerPorSucursalYProducto() {
        // Configurar inventarioRepository
        when(inventarioRepository.findBySucursalAndProducto(any(), any())).thenReturn(Optional.of(inventario));

        // Ejecutar el método
        Inventario resultado = servicioInventario.obtenerPorSucursalYProducto(sucursal, producto);

        // Verificar el resultado
        assertNotNull(resultado);
        assertEquals(CANTIDAD, resultado.getStock());
    }

    @Test
    void testAgregarStock() {
        // Configurar el mock para que devuelva el inventario existente
        when(inventarioRepository.findBySucursalAndProducto(any(), any())).thenReturn(Optional.of(inventario));

        // Ejecutar el método
        servicioInventario.agregarStock(sucursal, producto, CANTIDAD_AGREGAR);

        // Verificar que se actualizó el stock
        verify(inventarioRepository).save(inventario);
    }

    @Test
    void testDisminuirStock() {
        // Configurar el mock para que devuelva el inventario existente
        when(inventarioRepository.findBySucursalAndProducto(any(), any())).thenReturn(Optional.of(inventario));

        // Ejecutar el método
        servicioInventario.disminuirStock(sucursal, producto, CANTIDAD_DISMINUIR);

        // Verificar que se actualizó el stock
        verify(inventarioRepository).save(inventario);
    }
}