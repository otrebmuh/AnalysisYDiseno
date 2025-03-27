package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.Laboratorio;
import mx.uam.ayd.proyecto.negocio.modelo.DetalleVenta;

public interface DetalleVentaRepository extends CrudRepository<DetalleVenta, Long>{
    
}