package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;

public interface SucursalRepository extends CrudRepository<Sucursal, Long>{
    
}