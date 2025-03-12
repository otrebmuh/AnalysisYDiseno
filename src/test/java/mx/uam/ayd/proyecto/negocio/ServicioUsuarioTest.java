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

		// Caso de prueba 1: No hay usuarios guardados, me regresa lista vacía
		
		List <Usuario> usuarios = servicioUsuario.recuperaUsuarios();
		
		assertEquals(0,usuarios.size());
		
		
		// Caso de prueba 2: Si hay usuarios guardados, me regresa lista con usuarios

		ArrayList <Usuario> lista = new ArrayList <> ();

		// Tengo que crear un Iterable <Usuario> para que el método 
		// usuarioRepository.findAll() no me regrese una lista vacía
		// cuando lo invoco
		Usuario usuario1 = new Usuario();
		usuario1.setNombre("Juan");
		usuario1.setApellido("Perez");

		Usuario usuario2 = new Usuario();
		usuario2.setNombre("María");
		usuario2.setApellido("Ramírez");
		
		lista.add(usuario1);
		lista.add(usuario2);

		Iterable <Usuario> listaIterable = lista;
		
		// Al usar when, lo que hacemos es que definimos un comportamiento
		// para la invoación del método.
		// A partir de este punto, la invocación a usuarioRepository.findAll() ya
		// no me regresa una lista vacía, si no que me regresa una listaLigada
		// vista como Iterable que tiene dos elementos
		when(usuarioRepository.findAll()).thenReturn(listaIterable);
		
		usuarios = servicioUsuario.recuperaUsuarios();
		
		assertEquals(2,usuarios.size());
		
		
	}


}
