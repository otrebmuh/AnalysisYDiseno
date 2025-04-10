package mx.uam.ayd.proyecto.datos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

/**
 * 
 * Repositorio para usuarios
 * 
 * @author humbertocervantes
 *
 */
public interface UsuarioRepository extends CrudRepository <Usuario, Long> {
	
	public Usuario findByNombreAndApellido(String nombre, String apellido);

	public Optional<Usuario> findByNombre(String nombre);
	
	public List <Usuario> findByEdadBetween(int edad1, int edad2);
	

}
