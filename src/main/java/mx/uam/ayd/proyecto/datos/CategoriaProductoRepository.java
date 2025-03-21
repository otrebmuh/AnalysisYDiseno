package mx.uam.ayd.proyecto.datos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.CategoriaProducto;

public interface CategoriaProductoRepository extends CrudRepository<CategoriaProducto, Long> {
    public CategoriaProducto findByNombre(String Nombre);
    @SuppressWarnings("null")
    public List<CategoriaProducto> findAll();
}
