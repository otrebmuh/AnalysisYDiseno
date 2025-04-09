package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.DetallesSolicitud;

public interface DetallesSolicitudRepository extends CrudRepository<DetallesSolicitud, Long> {
}
