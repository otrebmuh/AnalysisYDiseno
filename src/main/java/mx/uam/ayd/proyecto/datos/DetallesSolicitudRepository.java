package mx.uam.ayd.proyecto.datos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.DetallesSolicitud;
import mx.uam.ayd.proyecto.negocio.modelo.SolicitudReabastecimiento;

public interface DetallesSolicitudRepository extends CrudRepository<DetallesSolicitud, Long> {
    
    /**
     * Encuentra todos los detalles para una solicitud de reabastecimiento específica
     * 
     * @param solicitudReabastecimiento La solicitud de reabastecimiento
     * @return Lista de detalles de la solicitud
     */
    List<DetallesSolicitud> findBySolicitudReabastecimiento(SolicitudReabastecimiento solicitudReabastecimiento);
}
