package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.Grupo;

/**
 * Repositorio para Grupos
 * 
 * @author humbertocervantes
 *
 */
public interface GrupoRepository extends CrudRepository <Grupo, Long> {
	
	public Grupo findByNombre(String nombre);
	

}
