package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.Laboratorio;

public interface LaboratorioRepository extends CrudRepository<Laboratorio, Long>{
    
}
