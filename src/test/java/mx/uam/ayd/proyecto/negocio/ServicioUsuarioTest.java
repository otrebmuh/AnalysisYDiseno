package mx.uam.ayd.proyecto.negocio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.datos.UsuarioRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;

@ExtendWith(MockitoExtension.class)
class ServicioUsuarioTest {
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Mock
	private GrupoRepository grupoRepository;
	
	@InjectMocks
	private ServicioUsuario servicioUsuario;

	@Test
	void testRecuperaUsuarios() {
		// Caso 1: No hay usuarios guardados, regresa lista vacía
		List<Usuario> usuarios = servicioUsuario.recuperaUsuarios();
		assertEquals(0, usuarios.size());

		// Caso 2: Hay usuarios guardados, regresa lista con usuarios
		ArrayList<Usuario> lista = new ArrayList<>();

		Usuario usuario1 = new Usuario();
		usuario1.setNombre("Juan");
		usuario1.setApellido("Perez");

		Usuario usuario2 = new Usuario();
		usuario2.setNombre("María");
		usuario2.setApellido("Ramírez");
		
		lista.add(usuario1);
		lista.add(usuario2);

		when(usuarioRepository.findAll()).thenReturn(lista);
		
		usuarios = servicioUsuario.recuperaUsuarios();
		assertEquals(2, usuarios.size());
	}

	@Test
	void testAgregaUsuario() {
		// Caso 1: Agregar usuario exitosamente
		String nombre = "Juan";
		String apellido = "Pérez";
		String nombreGrupo = "Administradores";

		// El usuario no existe previamente
		when(usuarioRepository.findByNombreAndApellido(nombre, apellido)).thenReturn(null);

		// El grupo existe
		Grupo grupo = new Grupo();
		grupo.setNombre(nombreGrupo);
		when(grupoRepository.findByNombre(nombreGrupo)).thenReturn(grupo);

		// Configurar el comportamiento del save
		Usuario nuevoUsuario = new Usuario();
		nuevoUsuario.setNombre(nombre);
		nuevoUsuario.setApellido(apellido);
		when(grupoRepository.save(org.mockito.ArgumentMatchers.any(Grupo.class))).thenReturn(grupo);

		Usuario resultado = servicioUsuario.agregaUsuario(nombre, apellido, nombreGrupo);
		assertNotNull(resultado);
		assertEquals(nombre, resultado.getNombre());
		assertEquals(apellido, resultado.getApellido());

		// Caso 2: Intentar agregar usuario que ya existe
		Usuario usuarioExistente = new Usuario();
		usuarioExistente.setNombre("María");
		usuarioExistente.setApellido("Ramírez");
		when(usuarioRepository.findByNombreAndApellido("María", "Ramírez")).thenReturn(usuarioExistente);

		assertThrows(IllegalArgumentException.class, () -> {
			servicioUsuario.agregaUsuario("María", "Ramírez", nombreGrupo);
		});

		// Caso 3: Intentar agregar usuario a grupo que no existe
		when(usuarioRepository.findByNombreAndApellido("Pedro", "López")).thenReturn(null);
		when(grupoRepository.findByNombre("Grupo Inexistente")).thenReturn(null);

		assertThrows(IllegalArgumentException.class, () -> {
			servicioUsuario.agregaUsuario("Pedro", "López", "Grupo Inexistente");
		});

		// Caso 4: Intentar agregar usuario con datos nulos
		assertThrows(IllegalArgumentException.class, () -> {
			servicioUsuario.agregaUsuario(null, apellido, nombreGrupo);
		});

		assertThrows(IllegalArgumentException.class, () -> {
			servicioUsuario.agregaUsuario(nombre, null, nombreGrupo);
		});

		assertThrows(IllegalArgumentException.class, () -> {
			servicioUsuario.agregaUsuario(nombre, apellido, null);
		});
	}
}
