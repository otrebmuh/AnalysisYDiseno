package mx.uam.ayd.proyecto.negocio.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Entidad de negocio Grupo
 * 
 * @author humbertocervantes
 *
 */
@Entity
public class Grupo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idGrupo;

	private String nombre;
	
	@OneToMany(targetEntity = Usuario.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "idGrupo")
	private final List <Usuario> usuarios = new ArrayList <> ();
	
	/**
	 * @return the idGrupo
	 */
	public long getIdGrupo() {
		return idGrupo;
	}

	/**
	 * @param idGrupo the idGrupo to set
	 */
	public void setIdGrupo(long idGrupo) {
		this.idGrupo = idGrupo;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the usuarios
	 */
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	/**
	 * 
	 * Permite agregar un usuario al grupo
	 * Nota: un mismo usuario no puede estar dos veces en el grupo
	 * 
	 * @param usuario el usuario que deseo agregar al grupo
	 * @return true si el usuario se agregó correctamente, false si ya estaba en el grupo
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
	
	/**
	 * 
	 * Permite quitar un usuario al grupo
	 * 
	 * @param usuario el usuario que deseo agregar al grupo
	 * @return true si el usuario se quitó correctamente, false si no estaba en el grupo
	 * @throws IllegalArgumentException si el usuario es nulo
	 */
	public boolean removeUsuario(Usuario usuario) {
		if(usuario == null) {
			throw new IllegalArgumentException("El usuario no puede ser null");
		}
		
		return usuarios.remove(usuario);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grupo other = (Grupo) obj;
		return idGrupo == other.idGrupo;
	}
	
	@Override
	public int hashCode() {
		return (int) (31 * idGrupo);
	}
	
	@Override
	public String toString() {
		return "Grupo [idGrupo=" + idGrupo + ", nombre=" + nombre + ", usuarios=" + usuarios + "]";
	}
}
