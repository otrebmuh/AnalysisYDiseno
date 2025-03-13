package mx.uam.ayd.proyecto.negocio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.ProductoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

@Service
public class ServicioProducto {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    /**
     * Obtiene todos los productos registrados
     * @return Lista de productos
     */
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }
    
    /**
     * Obtiene un producto por su ID
     * @param id ID del producto
     * @return Producto si existe, null si no
     */
    public Producto obtenerPorId(Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.orElse(null);
    }
    
    /**
     * Registra un nuevo producto
     * @param producto Producto a registrar
     * @return Producto registrado
     */
    public Producto registrar(Producto producto) {
        return productoRepository.save(producto);
    }
    
    /**
     * Actualiza un producto existente
     * @param producto Producto a actualizar
     * @return Producto actualizado
     */
    public Producto actualizar(Producto producto) {
        if (!productoRepository.existsById(producto.getId())) {
            return null;
        }
        return productoRepository.save(producto);
    }
    
    /**
     * Elimina un producto
     * @param id ID del producto a eliminar
     * @return true si se eliminó, false si no existía
     */
    public boolean eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            return false;
        }
        productoRepository.deleteById(id);
        return true;
    }
    
    /**
     * Valida que los campos del producto sean correctos
     * @param producto Producto a validar
     * @return true si es válido, false si no
     */
    public boolean validarProducto(Producto producto) {
        if (producto == null) return false;
        if (producto.getCodigo() == null || producto.getCodigo().trim().isEmpty()) return false;
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) return false;
        if (producto.getDescripcion() == null || producto.getDescripcion().trim().isEmpty()) return false;
        if (producto.getIngredienteActivo() == null || producto.getIngredienteActivo().trim().isEmpty()) return false;
        if (producto.getLaboratorio() == null || producto.getLaboratorio().trim().isEmpty()) return false;
        if (producto.getContenido() == null || producto.getContenido().trim().isEmpty()) return false;
        if (producto.getCategoria() == null) return false;
        return true;
    }
} 