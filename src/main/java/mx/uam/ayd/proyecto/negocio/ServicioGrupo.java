package mx.uam.ayd.proyecto.negocio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;

@Service
/**
 * Servicio relacionado con los grupos
 * 
 * @author humbertocervantes
 *
 */
public class ServicioGrupo {
	
	private final GrupoRepository grupoRepository;
	
	@Autowired
	public ServicioGrupo(GrupoRepository grupoRepository) {
		this.grupoRepository = grupoRepository;
	}
	
	/**
	 * 
	 * Recupera todos los grupos
	 * 
	 * @return una lista de grupos vacía o con los grupos existentes
	 */
	public List <Grupo> recuperaGrupos() {
		List <Grupo> grupos = new ArrayList<>();
		
		for(Grupo grupo:grupoRepository.findAll()) {
			grupos.add(grupo);
		}
				
		return grupos;
	}

	/**
	 * Crea un nuevo grupo
	 * 
	 * @param nombre el nombre del grupo a crear
	 * @return el grupo creado
	 * @throws IllegalArgumentException si el nombre es nulo o vacío, o si ya existe un grupo con ese nombre
	 */
	public Grupo creaGrupo(String nombre) {
		// Validar que el nombre no sea nulo o vacío
		if(nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre del grupo no puede ser nulo o vacío");
		}

		// Validar que no exista otro grupo con el mismo nombre
		Grupo grupoExistente = grupoRepository.findByNombre(nombre);
		if(grupoExistente != null) {
			throw new IllegalArgumentException("Ya existe un grupo con el nombre: " + nombre);
		}

		// Crear el nuevo grupo
		Grupo grupo = new Grupo();
		grupo.setNombre(nombre);

		// Guardar el grupo en la base de datos
		return grupoRepository.save(grupo);
	}

}
