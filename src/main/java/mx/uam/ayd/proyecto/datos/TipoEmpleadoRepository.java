package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;
import mx.uam.ayd.proyecto.negocio.modelo.TipoEmpleado;

public interface TipoEmpleadoRepository extends CrudRepository<TipoEmpleado, Long> {
    
    // Método personalizado para buscar por nombre
    TipoEmpleado findByNombre(String nombre);
}