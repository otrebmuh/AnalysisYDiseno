package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.DVenta;

public interface DVentaRepository extends CrudRepository<DVenta, Long> {
    public DVenta findByDVenta(long id);
    
}
