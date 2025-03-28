package mx.uam.ayd.proyecto.datos;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.Inventario;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;

public interface InventarioRepository extends CrudRepository<Inventario, Long>{
   Optional<Inventario> findBySucursalAndProducto(Sucursal sucursal, Producto producto); 
}