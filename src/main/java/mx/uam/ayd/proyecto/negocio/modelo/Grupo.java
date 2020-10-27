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
	
	public boolean addUsuario(Usuario usuario) {
		return usuarios.add(usuario);
	}
}
