package mx.uam.ayd.proyecto.negocio.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

/**
 * Entidad de negocio Grupo
 * 
 * @author humbertocervantes
 *
 */
@Entity
@Data
public class Grupo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idGrupo;

	private String nombre;
	
	@OneToMany(targetEntity = Usuario.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idGrupo")
	private final List <Usuario> usuarios = new ArrayList <> ();
	
	/**
	 * 
	 * Permite agregar un usuario al grupo
	 * Nota: un mismo usuario no puede estar dos veces en el grupo
	 * 
	 * @param usuario el usuario que deseo agregar al grupo
	 * @return true si el usuario se agregó correctamente, false si no
	 * @throws IllegalArgumentException si el usuario es nulo
	 */
	public boolean addUsuario(Usuario usuario) {

		if(usuario == null) {
			throw new IllegalArgumentException("El usuario no puede ser null");
		}
		
		if(usuarios.contains(usuario)) {
			// Checo si el usuario está en el grupo por que no se puede agregar un usuario dos veces
			return false;
		}
		
		return usuarios.add(usuario);
				
	}
}
