package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.Ingrediente;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long>{
    
}
