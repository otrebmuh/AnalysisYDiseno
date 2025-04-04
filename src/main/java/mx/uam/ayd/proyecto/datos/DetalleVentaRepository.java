package mx.uam.ayd.proyecto.datos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.DetalleVenta;

public interface DetalleVentaRepository extends CrudRepository<DetalleVenta, Long>{
    
    List<DetalleVenta> findByIdVenta(Long idVenta);
}