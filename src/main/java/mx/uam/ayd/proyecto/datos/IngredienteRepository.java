package mx.uam.ayd.proyecto.datos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.uam.ayd.proyecto.negocio.modelo.Ingrediente;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    
    /**
     * Busca un ingrediente por su nombre
     * @param nombre El nombre del ingrediente
     * @return Optional con el ingrediente si existe
     */
    Optional<Ingrediente> findByNombre(String nombre);

    /**
     * Obtiene todos los productos asociados a un ingrediente
     * @param ingrediente El ingrediente a consultar
     * @return Lista de productos
     */
    @Query("SELECT p FROM Producto p JOIN p.ingrediente i WHERE i = :ingrediente")
    List<Producto> findProductosByIngrediente(@Param("ingrediente") Ingrediente ingrediente);

    /**
     * Obtiene el número total de productos que usan un ingrediente
     * @param ingrediente El ingrediente a consultar
     * @return Cantidad de productos
     */
    @Query("SELECT COUNT(p) FROM Producto p JOIN p.ingrediente i WHERE i = :ingrediente")
    Long countProductosByIngrediente(@Param("ingrediente") Ingrediente ingrediente);

    /**
     * Busca ingredientes por nombre parcial
     * @param nombre El nombre parcial a buscar
     * @return Lista de ingredientes que coinciden
     */
    List<Ingrediente> findByNombreContainingIgnoreCase(String nombre);
}
