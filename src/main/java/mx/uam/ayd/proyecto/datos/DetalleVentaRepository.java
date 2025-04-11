package mx.uam.ayd.proyecto.datos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.DetalleVenta;

public interface DetalleVentaRepository extends CrudRepository<DetalleVenta, Long> {
    List<DetalleVenta> findByVenta_IdVenta(Long ventaId);
    //DetalleVenta findTopByOrderByIdDesc();
    @Query("SELECT MAX(e.id) FROM DetalleVenta e")
    Long findUltimoId();


}