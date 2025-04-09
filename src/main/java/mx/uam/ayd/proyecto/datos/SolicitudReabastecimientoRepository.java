package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.SolicitudReabastecimiento;

public interface SolicitudReabastecimientoRepository extends CrudRepository<SolicitudReabastecimiento, Long> {
}
