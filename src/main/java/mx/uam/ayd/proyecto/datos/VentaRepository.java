package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.Ingrediente;
import mx.uam.ayd.proyecto.negocio.modelo.Venta;

public interface VentaRepository extends CrudRepository<Venta, Long>{
    
}