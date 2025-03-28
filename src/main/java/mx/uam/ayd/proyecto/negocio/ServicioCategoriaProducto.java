package mx.uam.ayd.proyecto.negocio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.CategoriaProductoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.CategoriaProducto;

@Service
public class ServicioCategoriaProducto {
    
    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;
    
    /**
     * Obtiene todas las categorías de productos
     * @return Lista de categorías
     */
    public List<CategoriaProducto> obtenerTodas() {
        return categoriaProductoRepository.findAll();
    }
    
    /**
     * Obtiene una categoría por su ID
     * @param id ID de la categoría
     * @return Categoría si existe, null si no
     */
    public CategoriaProducto obtenerPorId(Long id) {
        Optional<CategoriaProducto> categoria = categoriaProductoRepository.findById(id);
        return categoria.orElse(null);
    }
    
    /**
     * Registra una nueva categoría
     * @param categoria Categoría a registrar
     * @return Categoría registrada
     */
    public CategoriaProducto registrar(CategoriaProducto categoria) {
        return categoriaProductoRepository.save(categoria);
    }
    
    /**
     * Actualiza una categoría existente
     * @param categoria Categoría a actualizar
     * @return Categoría actualizada
     */
    public CategoriaProducto actualizar(CategoriaProducto categoria) {
        if (!categoriaProductoRepository.existsById(categoria.getIdCategoria())) {
            return null;
        }
        return categoriaProductoRepository.save(categoria);
    }
    
    /**
     * Elimina una categoría
     * @param id ID de la categoría a eliminar
     * @return true si se eliminó, false si no existía
     */
    public boolean eliminar(Long id) {
        if (!categoriaProductoRepository.existsById(id)) {
            return false;
        }
        categoriaProductoRepository.deleteById(id);
        return true;
    }
    
    /**
     * Valida que los campos de la categoría sean correctos
     * @param categoria Categoría a validar
     * @return true si es válida, false si no
     */
    public boolean validarCategoria(CategoriaProducto categoria) {
        if (categoria == null) return false;
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) return false;
        if (categoria.getDescripcion() == null || categoria.getDescripcion().trim().isEmpty()) return false;
        return true;
    }
} 