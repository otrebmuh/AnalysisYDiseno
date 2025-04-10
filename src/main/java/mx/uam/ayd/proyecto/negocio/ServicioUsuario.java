package mx.uam.ayd.proyecto.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.datos.UsuarioRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

@Slf4j
@Service
public class ServicioUsuario {
	
	@Autowired 
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	/**
	 * 
	 * Permite agregar un usuario
	 * 
	 * @param nombre
	 * @param apellido
	 * @param grupo
	 * @return
	 */
	public Usuario agregaUsuario(String nombre, String apellido, String nombreGrupo) {
		
		// Regla de negocio: No se permite agregar dos usuarios con el mismo nombre y apellido
		
		
		Usuario usuario = usuarioRepository.findByNombreAndApellido(nombre, apellido);
		
		if(usuario != null) {
			throw new IllegalArgumentException("Ese usuario ya existe");
		}
		
		Grupo grupo = grupoRepository.findByNombre(nombreGrupo);
		
		if(grupo == null) {
			throw new IllegalArgumentException("No se encontró el grupo");
		}
		
		log.info("Agregando usuario nombre: "+nombre+" apellido:"+apellido);
		
		usuario = new Usuario();
		usuario.setNombre(nombre);
		
		usuarioRepository.save(usuario);
		
		grupo.addUsuario(usuario);
		
		
		grupoRepository.save(grupo);
		
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

	public Usuario login(String nombre, String contrasena) {

		Optional<Usuario> usuario = usuarioRepository.findByNombre(nombre);

		if (usuario.isPresent()) {
			if (usuario.get().getPassword().equals(contrasena)) {
				return usuario.get();
			}
		}
		return null;
	}

}
