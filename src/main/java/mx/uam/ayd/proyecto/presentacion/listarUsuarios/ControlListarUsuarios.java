package mx.uam.ayd.proyecto.presentacion.listarUsuarios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.negocio.ServicioUsuario;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

@Slf4j
@Component
public class ControlListarUsuarios {
	@Autowired
	private ServicioUsuario servicioUsuario;

	public void inicia() {
		List <Usuario> usuarios = servicioUsuario.recuperaUsuarios();
		
		for(Usuario usuario:usuarios) {
			log.info("usuario: "+usuario);
		}
		
		
	}

}
