package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.CategoriaProducto;

public interface CategoriaProductoRepository extends CrudRepository<CategoriaProducto, Long> {
    public CategoriaProducto byNombre(String Nombre);
    
    
}
