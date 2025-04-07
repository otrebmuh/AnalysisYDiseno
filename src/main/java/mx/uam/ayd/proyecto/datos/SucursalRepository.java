package mx.uam.ayd.proyecto.datos;

import java.util.Optional;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;

public interface SucursalRepository extends CrudRepository<Sucursal, Long>{
   public Optional<Sucursal> findByIdSucursal(Long id); 
}