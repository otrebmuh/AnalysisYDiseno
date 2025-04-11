package mx.uam.ayd.proyecto.negocio;

import mx.uam.ayd.proyecto.datos.SolicitudReabastecimientoRepository;
import mx.uam.ayd.proyecto.datos.DetallesSolicitudRepository;
import mx.uam.ayd.proyecto.negocio.modelo.SolicitudReabastecimiento;
import mx.uam.ayd.proyecto.negocio.modelo.DetallesSolicitud;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.negocio.modelo.Inventario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServicioSolicitudReabastecimientoTest {

    @Mock
    private SolicitudReabastecimientoRepository solicitudRepository;

    @Mock
    private DetallesSolicitudRepository detallesSolicitudRepository;

    @Mock
    private ServicioInventario servicioInventario;

    @Mock
    private ServicioSucursal servicioSucursal;

    @InjectMocks
    private ServicioSolicitudReabastecimiento servicioSolicitudReabastecimiento;

    private SolicitudReabastecimiento solicitud;
    private DetallesSolicitud detalle1;
    private DetallesSolicitud detalle2;
    private Sucursal sucursal;
    private Sucursal almacen;
    private Producto producto1;
    private Producto producto2;
    private Inventario inventario1;
    private Inventario inventario2;
    private final int CANTIDAD_DETALLE1 = 10;
    private final int CANTIDAD_DETALLE2 = 5;
    private final int STOCK_ALMACEN1 = 15;
    private final int STOCK_ALMACEN2 = 3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear objetos de prueba
        sucursal = new Sucursal();
        sucursal.setIdSucursal(1L);
        sucursal.setNombre("SucursalTest");

        almacen = new Sucursal();
        almacen.setIdSucursal(2L);
        almacen.setNombre("Almacén General");

        producto1 = new Producto();
        producto1.setIdProducto(1L);
        producto1.setNombre("ProductoTest1");

        producto2 = new Producto();
        producto2.setIdProducto(2L);
        producto2.setNombre("ProductoTest2");

        solicitud = new SolicitudReabastecimiento();
        solicitud.setIdSolicitudReabastecimiento(1L);
        solicitud.setSucursal(sucursal);
        solicitud.setFecha(new Date());
        solicitud.setAtendida(false);

        detalle1 = new DetallesSolicitud();
        detalle1.setIdDetallesSolicitud(1L);
        detalle1.setSolicitudReabastecimiento(solicitud);
        detalle1.setProducto(producto1);
        detalle1.setCantidad(CANTIDAD_DETALLE1);

        detalle2 = new DetallesSolicitud();
        detalle2.setIdDetallesSolicitud(2L);
        detalle2.setSolicitudReabastecimiento(solicitud);
        detalle2.setProducto(producto2);
        detalle2.setCantidad(CANTIDAD_DETALLE2);

        List<DetallesSolicitud> detalles = new ArrayList<>();
        detalles.add(detalle1);
        detalles.add(detalle2);
        solicitud.setDetalles(detalles);

        inventario1 = new Inventario();
        inventario1.setIdInventario(1L);
        inventario1.setSucursal(almacen);
        inventario1.setProducto(producto1);
        inventario1.setStock(STOCK_ALMACEN1);

        inventario2 = new Inventario();
        inventario2.setIdInventario(2L);
        inventario2.setSucursal(almacen);
        inventario2.setProducto(producto2);
        inventario2.setStock(STOCK_ALMACEN2);
    }

    @Test
    void testRecuperaSolicitudesNoAtendidas() {
        // Configurar el mock para devolver una lista de solicitudes
        List<SolicitudReabastecimiento> todasSolicitudes = new ArrayList<>();
        
        // Solicitud no atendida
        SolicitudReabastecimiento solicitudNoAtendida1 = new SolicitudReabastecimiento();
        solicitudNoAtendida1.setIdSolicitudReabastecimiento(1L);
        solicitudNoAtendida1.setAtendida(false);
        
        // Otra solicitud no atendida
        SolicitudReabastecimiento solicitudNoAtendida2 = new SolicitudReabastecimiento();
        solicitudNoAtendida2.setIdSolicitudReabastecimiento(2L);
        solicitudNoAtendida2.setAtendida(false);
        
        // Solicitud atendida
        SolicitudReabastecimiento solicitudAtendida = new SolicitudReabastecimiento();
        solicitudAtendida.setIdSolicitudReabastecimiento(3L);
        solicitudAtendida.setAtendida(true);
        
        // Solicitud con atendida=null
        SolicitudReabastecimiento solicitudNull = new SolicitudReabastecimiento();
        solicitudNull.setIdSolicitudReabastecimiento(4L);
        solicitudNull.setAtendida(null);
        
        todasSolicitudes.add(solicitudNoAtendida1);
        todasSolicitudes.add(solicitudNoAtendida2);
        todasSolicitudes.add(solicitudAtendida);
        todasSolicitudes.add(solicitudNull);
        
        when(solicitudRepository.findAll()).thenReturn(todasSolicitudes);
        
        // Ejecutar el método
        List<SolicitudReabastecimiento> resultado = servicioSolicitudReabastecimiento.recuperaSolicitudesNoAtendidas();
        
        // Verificar el resultado
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(solicitudNoAtendida1));
        assertTrue(resultado.contains(solicitudNoAtendida2));
        assertFalse(resultado.contains(solicitudAtendida));
        assertFalse(resultado.contains(solicitudNull));
    }

    @Test
    void testRecuperaSolicitudPorId_Existe() {
        // Configurar el mock para devolver una solicitud cuando existe
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));
        
        // Ejecutar el método
        SolicitudReabastecimiento resultado = servicioSolicitudReabastecimiento.recuperaSolicitudPorId(1L);
        
        // Verificar el resultado
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdSolicitudReabastecimiento());
    }

    @Test
    void testRecuperaSolicitudPorId_NoExiste() {
        // Configurar el mock para devolver vacío cuando no existe
        when(solicitudRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Ejecutar el método
        SolicitudReabastecimiento resultado = servicioSolicitudReabastecimiento.recuperaSolicitudPorId(999L);
        
        // Verificar el resultado
        assertNull(resultado);
    }

    @Test
    void testMarcaSolicitudComoAtendida() {
        // Configurar mocks
        when(servicioSucursal.obtenerPorNombre("Almacén General")).thenReturn(almacen);
        when(solicitudRepository.save(any(SolicitudReabastecimiento.class))).thenReturn(solicitud);
        
        List<DetallesSolicitud> detalles = new ArrayList<>();
        detalles.add(detalle1);
        detalles.add(detalle2);
        
        // Configurar el repositorio para devolver los detalles de la solicitud
        when(detallesSolicitudRepository.findAll()).thenReturn(detalles);
        
        // Ejecutar el método
        SolicitudReabastecimiento resultado = servicioSolicitudReabastecimiento.marcaSolicitudComoAtendida(solicitud);
        
        // Verificar el resultado
        assertNotNull(resultado);
        assertTrue(resultado.getAtendida());
        
        // Verificar que se llamaron los métodos internos correctamente
        verify(servicioInventario).agregarStock(sucursal, producto1, CANTIDAD_DETALLE1);
        verify(servicioInventario).disminuirStock(almacen, producto1, CANTIDAD_DETALLE1);
        verify(servicioInventario).agregarStock(sucursal, producto2, CANTIDAD_DETALLE2);
        verify(servicioInventario).disminuirStock(almacen, producto2, CANTIDAD_DETALLE2);
        verify(solicitudRepository).save(solicitud);
    }

    @Test
    void testObtenerCantidadesEnAlmacen() {
        // Configurar mocks
        when(servicioSucursal.obtenerPorNombre("Almacén General")).thenReturn(almacen);
        
        List<DetallesSolicitud> detalles = new ArrayList<>();
        detalles.add(detalle1);
        detalles.add(detalle2);
        
        // Configurar el repositorio para devolver los detalles de la solicitud
        when(detallesSolicitudRepository.findAll()).thenReturn(detalles);
        
        // Mockear inventario para producto1
        when(servicioInventario.obtenerPorSucursalYProducto(almacen, producto1)).thenReturn(inventario1);
        
        // Mockear inventario para producto2
        when(servicioInventario.obtenerPorSucursalYProducto(almacen, producto2)).thenReturn(inventario2);
        
        // Ejecutar el método
        List<Integer> resultado = servicioSolicitudReabastecimiento.obtenerCantidadesEnAlmacen(solicitud);
        
        // Verificar el resultado
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(STOCK_ALMACEN1, resultado.get(0));
        assertEquals(STOCK_ALMACEN2, resultado.get(1));
    }

    @Test
    void testObtenerCantidadesEnAlmacen_SinStock() {
        // Configurar mocks
        when(servicioSucursal.obtenerPorNombre("Almacén General")).thenReturn(almacen);
        
        List<DetallesSolicitud> detalles = new ArrayList<>();
        detalles.add(detalle1);
        
        // Configurar el repositorio para devolver los detalles de la solicitud
        when(detallesSolicitudRepository.findAll()).thenReturn(detalles);
        
        // Mockear que no hay inventario (retorna null)
        when(servicioInventario.obtenerPorSucursalYProducto(almacen, producto1)).thenReturn(null);
        
        // Ejecutar el método
        List<Integer> resultado = servicioSolicitudReabastecimiento.obtenerCantidadesEnAlmacen(solicitud);
        
        // Verificar el resultado
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(0, resultado.get(0)); // Se debe devolver 0 cuando no hay inventario
    }

    @Test
    void testObtenerDetallesSolicitud() {
        // Crear lista de todos los detalles que se podrían encontrar en el repositorio
        List<DetallesSolicitud> todosDetalles = new ArrayList<>();
        
        // Detalles de la solicitud con ID 1
        todosDetalles.add(detalle1);
        todosDetalles.add(detalle2);
        
        // Detalle de otra solicitud (ID 2)
        SolicitudReabastecimiento otraSolicitud = new SolicitudReabastecimiento();
        otraSolicitud.setIdSolicitudReabastecimiento(2L);
        
        DetallesSolicitud detalleOtraSolicitud = new DetallesSolicitud();
        detalleOtraSolicitud.setIdDetallesSolicitud(3L);
        detalleOtraSolicitud.setSolicitudReabastecimiento(otraSolicitud);
        detalleOtraSolicitud.setProducto(producto1);
        detalleOtraSolicitud.setCantidad(7);
        
        todosDetalles.add(detalleOtraSolicitud);
        
        // Configurar mock para devolver todos los detalles
        when(detallesSolicitudRepository.findAll()).thenReturn(todosDetalles);
        
        // Ejecutar el método
        List<DetallesSolicitud> resultado = servicioSolicitudReabastecimiento.obtenerDetallesSolicitud(solicitud);
        
        // Verificar el resultado
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(detalle1));
        assertTrue(resultado.contains(detalle2));
        assertFalse(resultado.contains(detalleOtraSolicitud));
    }

    @Test
    void testGuardarSolicitud_ConValoresValidos() {
        // Configurar mock
        when(solicitudRepository.save(any(SolicitudReabastecimiento.class))).thenReturn(solicitud);
        
        // Ejecutar el método
        SolicitudReabastecimiento resultado = servicioSolicitudReabastecimiento.guardarSolicitud(solicitud);
        
        // Verificar el resultado
        assertNotNull(resultado);
        assertFalse(resultado.getAtendida()); // Se debe inicializar como no atendida
        verify(solicitudRepository).save(solicitud);
    }

    @Test
    void testGuardarSolicitud_SinDetalles() {
        // Crear solicitud sin detalles
        SolicitudReabastecimiento solicitudSinDetalles = new SolicitudReabastecimiento();
        solicitudSinDetalles.setIdSolicitudReabastecimiento(3L);
        solicitudSinDetalles.setSucursal(sucursal);
        solicitudSinDetalles.setFecha(new Date());
        solicitudSinDetalles.setDetalles(new ArrayList<>()); // Lista vacía de detalles
        
        // Verificar que se lanza excepción al guardar
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            servicioSolicitudReabastecimiento.guardarSolicitud(solicitudSinDetalles);
        });
        
        assertEquals("La solicitud debe tener al menos un producto", exception.getMessage());
        // Verificar que nunca se llamó a save
        verify(solicitudRepository, never()).save(any(SolicitudReabastecimiento.class));
    }

    @Test
    void testGuardarSolicitud_SinSucursal() {
        // Crear solicitud sin sucursal
        SolicitudReabastecimiento solicitudSinSucursal = new SolicitudReabastecimiento();
        solicitudSinSucursal.setIdSolicitudReabastecimiento(3L);
        solicitudSinSucursal.setFecha(new Date());
        
        // Agregar detalle a la solicitud
        DetallesSolicitud detalle = new DetallesSolicitud();
        detalle.setProducto(producto1);
        detalle.setCantidad(5);
        List<DetallesSolicitud> detalles = new ArrayList<>();
        detalles.add(detalle);
        solicitudSinSucursal.setDetalles(detalles);
        
        // Verificar que se lanza excepción al guardar
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            servicioSolicitudReabastecimiento.guardarSolicitud(solicitudSinSucursal);
        });
        
        assertEquals("Debe especificar una sucursal para la solicitud", exception.getMessage());
        // Verificar que nunca se llamó a save
        verify(solicitudRepository, never()).save(any(SolicitudReabastecimiento.class));
    }

    @Test
    void testGuardarSolicitud_SinFecha() {
        // Crear solicitud sin fecha
        SolicitudReabastecimiento solicitudSinFecha = new SolicitudReabastecimiento();
        solicitudSinFecha.setIdSolicitudReabastecimiento(3L);
        solicitudSinFecha.setSucursal(sucursal);
        
        // Agregar detalle a la solicitud
        DetallesSolicitud detalle = new DetallesSolicitud();
        detalle.setProducto(producto1);
        detalle.setCantidad(5);
        List<DetallesSolicitud> detalles = new ArrayList<>();
        detalles.add(detalle);
        solicitudSinFecha.setDetalles(detalles);
        
        // Verificar que se lanza excepción al guardar
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            servicioSolicitudReabastecimiento.guardarSolicitud(solicitudSinFecha);
        });
        
        assertEquals("Debe especificar una fecha para la solicitud", exception.getMessage());
        // Verificar que nunca se llamó a save
        verify(solicitudRepository, never()).save(any(SolicitudReabastecimiento.class));
    }
}
