package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.Producto;

/**
 * Repositorio para Producto
 * 
 * @author Yamelin Larios Nepomuseno
 *
 */
public interface ProductoRepository extends CrudRepository <Producto, Long> {
    
    /**
     * Encuentra un producto a partir de su idProducto
     * 
     * @param idProducto
     * @return
     */
    public Producto findByIdProducto(long idProducto);

}