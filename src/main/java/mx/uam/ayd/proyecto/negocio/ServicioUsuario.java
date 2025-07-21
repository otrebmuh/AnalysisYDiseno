package mx.uam.ayd.proyecto.negocio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.datos.UsuarioRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

@Service
public class ServicioUsuario {
	
	// Define a static logger field
	private static final Logger log = LoggerFactory.getLogger(ServicioUsuario.class);
	
	private final UsuarioRepository usuarioRepository;
	private final GrupoRepository grupoRepository;
	
	@Autowired
	public ServicioUsuario(UsuarioRepository usuarioRepository, GrupoRepository grupoRepository) {
		this.usuarioRepository = usuarioRepository;
		this.grupoRepository = grupoRepository;
	}
	
	/**
	 * 
	 * Permite agregar un usuario
	 * 
	 * @param nombre nombre del usuario
	 * @param apellido apellido del usuario
	 * @param grupo nombre grupo al que debe pertencer
	 * @return el usuario que se agregó
	 * @throws IllegalArgumentException si el usuario ya existe, no existe el grupo,
	 *         o si alguno de los parámetros es nulo o vacío
	 * 
	 */
	public Usuario agregaUsuario(String nombre, String apellido, String nombreGrupo) {
		
		// Validar que ningún parámetro sea nulo o vacío
		if(nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
		}
		
		if(apellido == null || apellido.trim().isEmpty()) {
			throw new IllegalArgumentException("El apellido no puede ser nulo o vacío");
		}
		
		if(nombreGrupo == null || nombreGrupo.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre del grupo no puede ser nulo o vacío");
		}
		
		// Regla de negocio: No se permite agregar dos usuarios con el mismo nombre y apellido
		
		Usuario usuario = usuarioRepository.findByNombreAndApellido(nombre, apellido);
		
		if(usuario != null) {
			throw new IllegalArgumentException("Ese usuario ya existe");
		}
		
		Grupo grupo = grupoRepository.findByNombre(nombreGrupo);
		
		if(grupo == null) {
			throw new IllegalArgumentException("No se encontró el grupo");
		}
		
		// Se validaron correctamente las reglas de negocio
		
		log.info("Agregando usuario nombre: "+nombre+" apellido:"+apellido);

		// Crea el usuario
		
		usuario = new Usuario();
		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		
		// Conecta al grupo con el usuario
		
		grupo.addUsuario(usuario);
		
		grupoRepository.save(grupo); // Esto es el update
		
		return usuario;
		
	}

	/**
	 * Recupera todos los usuarios existentes
	 * 
	 * @return Una lista con los usuarios (o lista vacía)
	 */
	public List <Usuario> recuperaUsuarios() {

		
		System.out.println("usuarioRepository = "+usuarioRepository);
		
		List <Usuario> usuarios = new ArrayList<>();
		
		for(Usuario usuario:usuarioRepository.findAll()) {
			usuarios.add(usuario);
		}
				
		return usuarios;
	}

}
