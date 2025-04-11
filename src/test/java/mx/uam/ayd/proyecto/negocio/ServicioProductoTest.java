package mx.uam.ayd.proyecto.negocio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mx.uam.ayd.proyecto.datos.ProductoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.CategoriaProducto;
import mx.uam.ayd.proyecto.negocio.modelo.Ingrediente;
import mx.uam.ayd.proyecto.negocio.modelo.Laboratorio;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

@ExtendWith(MockitoExtension.class)
class ServicioProductoTest {

    // Mock del repositorio que será inyectado en el servicio
    @Mock
    private ProductoRepository productoRepository;
    
    // Servicio que vamos a probar, con los mocks inyectados
    @InjectMocks
    private ServicioProducto servicio;
    
    /**
     * Prueba para el método getAll() que recupera todos los productos
     */
    @Test
    void testGetAll() {
        // Prueba 1: Corroborar que regresa una lista vacía si no hay productos
        
        List<Producto> productos = servicio.getAll();
        assertTrue(productos.isEmpty());
        
        // Prueba 2: Corroborar que regresa una lista con productos cuando existen
        
        // Creamos una lista con productos de prueba
        List<Producto> listaProductos = new LinkedList<>();
        
        // Creamos categoría, laboratorio e ingrediente necesarios para los productos
        CategoriaProducto categoria = new CategoriaProducto();
        categoria.setNombre("Analgésicos");
        
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setNombre("Bayer");
        
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre("Paracetamol");
        
        // Producto 1
        Producto producto1 = new Producto();
        producto1.setCodigo("1204101");
        producto1.setNombre("Tempra 500mg");
        producto1.setDescripcion("Tabletas de paracetamol");
        producto1.setContenido("10 tabletas");
        producto1.setPrecio(50.00);
        producto1.setCategoria(categoria);
        producto1.setLaboratorio(laboratorio);
        producto1.setIngrediente(ingrediente);
        
        // Producto 2
        Producto producto2 = new Producto();
        producto2.setCodigo("0012345");
        producto2.setNombre("Amoxicilina 500mg");
        producto2.setDescripcion("Cápsulas de amoxicilina");
        producto2.setContenido("12 cápsulas");
        producto2.setPrecio(120.00);
        producto2.setCategoria(categoria);
        producto2.setLaboratorio(laboratorio);
        producto2.setIngrediente(ingrediente);
        
        listaProductos.add(producto1);
        listaProductos.add(producto2);
        
        // Configuramos el mock para que devuelva nuestra lista de productos
        when(productoRepository.findAll()).thenReturn(listaProductos);
        
        productos = servicio.getAll();
        
        // Verificamos que la lista tenga 2 productos
        assertEquals(2, productos.size());
        // Verificamos que los productos sean los esperados
        assertEquals("Tempra 500mg", productos.get(0).getNombre());
        assertEquals("Amoxicilina 500mg", productos.get(1).getNombre());
    }
    
    /**
     * Prueba para el método obtenerPorId()
     */
    @Test
    void testObtenerPorId() {
        // Prueba 1: Corroborar que regresa null cuando el producto no existe
        
        Long idInexistente = 999L;
        when(productoRepository.findById(idInexistente)).thenReturn(Optional.empty());
        
        Producto resultado = servicio.obtenerPorId(idInexistente);
        assertNull(resultado);
        
        // Prueba 2: Corroborar que regresa el producto cuando existe
        
        Long idExistente = 1L;
        
        CategoriaProducto categoria = new CategoriaProducto();
        categoria.setNombre("Analgésicos");
        
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setNombre("Bayer");
        
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre("Paracetamol");
        
        Producto productoExistente = new Producto();
        productoExistente.setIdProducto(idExistente);
        productoExistente.setCodigo("1204101");
        productoExistente.setNombre("Tempra 500mg");
        productoExistente.setDescripcion("Tabletas de paracetamol");
        productoExistente.setContenido("10 tabletas");
        productoExistente.setPrecio(50.00);
        productoExistente.setCategoria(categoria);
        productoExistente.setLaboratorio(laboratorio);
        productoExistente.setIngrediente(ingrediente);
        
        when(productoRepository.findById(idExistente)).thenReturn(Optional.of(productoExistente));
        
        resultado = servicio.obtenerPorId(idExistente);
        
        // Verificamos que el producto devuelto es el esperado
        assertNotNull(resultado);
        assertEquals(idExistente, resultado.getIdProducto());
        assertEquals("Tempra 500mg", resultado.getNombre());
    }
    
    /**
     * Prueba para el método obtenerPorNombre()
     */
    @Test
    void testObtenerPorNombre() {
        // Prueba 1: Corroborar que regresa null cuando el producto no existe
        
        String nombreInexistente = "Producto Inexistente";
        when(productoRepository.findByNombre(nombreInexistente)).thenReturn(Optional.empty());
        
        Producto resultado = servicio.obtenerPorNombre(nombreInexistente);
        assertNull(resultado);
        
        // Prueba 2: Corroborar que regresa el producto cuando existe
        
        String nombreExistente = "Tempra 500mg";
        
        CategoriaProducto categoria = new CategoriaProducto();
        categoria.setNombre("Analgésicos");
        
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setNombre("Bayer");
        
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre("Paracetamol");
        
        Producto productoExistente = new Producto();
        productoExistente.setIdProducto(1L);
        productoExistente.setCodigo("1204101");
        productoExistente.setNombre(nombreExistente);
        productoExistente.setDescripcion("Tabletas de paracetamol");
        productoExistente.setContenido("10 tabletas");
        productoExistente.setPrecio(50.00);
        productoExistente.setCategoria(categoria);
        productoExistente.setLaboratorio(laboratorio);
        productoExistente.setIngrediente(ingrediente);
        
        when(productoRepository.findByNombre(nombreExistente)).thenReturn(Optional.of(productoExistente));
        
        resultado = servicio.obtenerPorNombre(nombreExistente);
        
        // Verificamos que el producto devuelto es el esperado
        assertNotNull(resultado);
        assertEquals(nombreExistente, resultado.getNombre());
        assertEquals("1204101", resultado.getCodigo());
    }
    
    /**
     * Prueba para el método obtenerPorCodigo()
     */
    @Test
    void testObtenerPorCodigo() {
        // Prueba 1: Corroborar que regresa null cuando el producto no existe
        
        String codigoInexistente = "9999999";
        when(productoRepository.findByCodigo(codigoInexistente)).thenReturn(Optional.empty());
        
        Producto resultado = servicio.obtenerPorCodigo(codigoInexistente);
        assertNull(resultado);
        
        // Prueba 2: Corroborar que regresa el producto cuando existe
        
        String codigoExistente = "1204101";
        
        CategoriaProducto categoria = new CategoriaProducto();
        categoria.setNombre("Analgésicos");
        
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setNombre("Bayer");
        
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre("Paracetamol");
        
        Producto productoExistente = new Producto();
        productoExistente.setIdProducto(1L);
        productoExistente.setCodigo(codigoExistente);
        productoExistente.setNombre("Tempra 500mg");
        productoExistente.setDescripcion("Tabletas de paracetamol");
        productoExistente.setContenido("10 tabletas");
        productoExistente.setPrecio(50.00);
        productoExistente.setCategoria(categoria);
        productoExistente.setLaboratorio(laboratorio);
        productoExistente.setIngrediente(ingrediente);
        
        when(productoRepository.findByCodigo(codigoExistente)).thenReturn(Optional.of(productoExistente));
        
        resultado = servicio.obtenerPorCodigo(codigoExistente);
        
        // Verificamos que el producto devuelto es el esperado
        assertNotNull(resultado);
        assertEquals(codigoExistente, resultado.getCodigo());
        assertEquals("Tempra 500mg", resultado.getNombre());
    }
    
    /**
     * Prueba para el método crear()
     */
    @Test
    void testCrear() {
        // Creamos un producto para guardar
        CategoriaProducto categoria = new CategoriaProducto();
        categoria.setNombre("Analgésicos");
        
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setNombre("Bayer");
        
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre("Paracetamol");
        
        Producto nuevoProducto = new Producto();
        nuevoProducto.setCodigo("1204101");
        nuevoProducto.setNombre("Tempra 500mg");
        nuevoProducto.setDescripcion("Tabletas de paracetamol");
        nuevoProducto.setContenido("10 tabletas");
        nuevoProducto.setPrecio(50.00);
        nuevoProducto.setCategoria(categoria);
        nuevoProducto.setLaboratorio(laboratorio);
        nuevoProducto.setIngrediente(ingrediente);
        
        // Configuramos el mock para que devuelva el producto guardado
        when(productoRepository.save(nuevoProducto)).thenReturn(nuevoProducto);
        
        Producto resultado = servicio.crear(nuevoProducto);
        
        // Verificamos que el producto devuelto es el mismo que se intentó guardar
        assertNotNull(resultado);
        assertEquals("Tempra 500mg", resultado.getNombre());
        assertEquals("1204101", resultado.getCodigo());
    }
    
    /**
     * Prueba para el método actualizar()
     */
    @Test
    void testActualizar() {
        // Prueba 1: Corroborar que regresa null cuando el producto no existe
        
        Long idInexistente = 999L;
        Producto productoInexistente = new Producto();
        productoInexistente.setIdProducto(idInexistente);
        
        when(productoRepository.existsById(idInexistente)).thenReturn(false);
        
        Producto resultado = servicio.actualizar(productoInexistente);
        assertNull(resultado);
        
        // Prueba 2: Corroborar que actualiza y regresa el producto cuando existe
        
        Long idExistente = 1L;
        
        CategoriaProducto categoria = new CategoriaProducto();
        categoria.setNombre("Analgésicos");
        
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setNombre("Bayer");
        
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre("Paracetamol");
        
        Producto productoExistente = new Producto();
        productoExistente.setIdProducto(idExistente);
        productoExistente.setCodigo("1204101");
        productoExistente.setNombre("Tempra 500mg");
        productoExistente.setDescripcion("Tabletas de paracetamol");
        productoExistente.setContenido("10 tabletas");
        productoExistente.setPrecio(50.00);
        productoExistente.setCategoria(categoria);
        productoExistente.setLaboratorio(laboratorio);
        productoExistente.setIngrediente(ingrediente);
        
        // Producto actualizado
        Producto productoActualizado = new Producto();
        productoActualizado.setIdProducto(idExistente);
        productoActualizado.setCodigo("1204101");
        productoActualizado.setNombre("Tempra Forte 500mg");
        productoActualizado.setDescripcion("Tabletas de paracetamol");
        productoActualizado.setContenido("10 tabletas");
        productoActualizado.setPrecio(55.00); // Precio actualizado
        productoActualizado.setCategoria(categoria);
        productoActualizado.setLaboratorio(laboratorio);
        productoActualizado.setIngrediente(ingrediente);
        
        when(productoRepository.existsById(idExistente)).thenReturn(true);
        when(productoRepository.save(productoActualizado)).thenReturn(productoActualizado);
        
        resultado = servicio.actualizar(productoActualizado);
        
        // Verificamos que el producto devuelto es el actualizado
        assertNotNull(resultado);
        assertEquals(idExistente, resultado.getIdProducto());
        assertEquals("Tempra Forte 500mg", resultado.getNombre());
        assertEquals(55.00, resultado.getPrecio());
    }
    
    /**
     * Prueba para el método eliminar()
     */
    @Test
    void testEliminar() {
        // Prueba 1: Corroborar que regresa false cuando el producto no existe
        
        Long idInexistente = 999L;
        when(productoRepository.existsById(idInexistente)).thenReturn(false);
        
        boolean resultado = servicio.eliminar(idInexistente);
        assertFalse(resultado);
        
        // Prueba 2: Corroborar que regresa true cuando el producto existe y se elimina
        
        Long idExistente = 1L;
        when(productoRepository.existsById(idExistente)).thenReturn(true);
        
        resultado = servicio.eliminar(idExistente);
        assertTrue(resultado);
    }
    
    /**
     * Prueba para el método validarProducto()
     */
    @Test
    void testValidarProducto() {
        // Prueba 1: Producto nulo
        assertFalse(servicio.validarProducto(null));
        
        // Prueba 2: Producto con campos obligatorios vacíos/nulos
        Producto productoInvalido = new Producto();
        assertFalse(servicio.validarProducto(productoInvalido));
        
        // Prueba 3: Producto con precio inválido (0 o negativo)
        CategoriaProducto categoria = new CategoriaProducto();
        categoria.setNombre("Analgésicos");
        
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setNombre("Bayer");
        
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre("Paracetamol");
        
        Producto productoPrecioInvalido = new Producto();
        productoPrecioInvalido.setCodigo("1204101");
        productoPrecioInvalido.setNombre("Tempra 500mg");
        productoPrecioInvalido.setDescripcion("Tabletas de paracetamol");
        productoPrecioInvalido.setContenido("10 tabletas");
        productoPrecioInvalido.setPrecio(0.00); // Precio inválido
        productoPrecioInvalido.setCategoria(categoria);
        productoPrecioInvalido.setLaboratorio(laboratorio);
        productoPrecioInvalido.setIngrediente(ingrediente);
        
        assertFalse(servicio.validarProducto(productoPrecioInvalido));
        
        // Prueba 4: Producto válido
        Producto productoValido = new Producto();
        productoValido.setCodigo("1204101");
        productoValido.setNombre("Tempra 500mg");
        productoValido.setDescripcion("Tabletas de paracetamol");
        productoValido.setContenido("10 tabletas");
        productoValido.setPrecio(50.00);
        productoValido.setCategoria(categoria);
        productoValido.setLaboratorio(laboratorio);
        productoValido.setIngrediente(ingrediente);
        
        assertTrue(servicio.validarProducto(productoValido));
    }
}