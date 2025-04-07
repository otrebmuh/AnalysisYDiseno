package mx.uam.ayd.proyecto;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.datos.UsuarioRepository;
import mx.uam.ayd.proyecto.presentacion.agregarUsuario.VentanaAgregarUsuario;
import mx.uam.ayd.proyecto.presentacion.listarGrupos.VentanaListarGrupos;
import mx.uam.ayd.proyecto.presentacion.listarUsuarios.VentanaListarUsuarios;
import mx.uam.ayd.proyecto.presentacion.principal.VentanaPrincipal;

@TestConfiguration
@Profile("test")
@EnableTransactionManagement
public class TestConfig {

    @Bean
    @Primary
    public VentanaPrincipal ventanaPrincipal() {
        return new VentanaPrincipal();
    }

    @Bean
    @Primary
    public VentanaAgregarUsuario ventanaAgregarUsuario() {
        return new VentanaAgregarUsuario();
    }

    @Bean
    @Primary
    public VentanaListarUsuarios ventanaListarUsuarios() {
        return new VentanaListarUsuarios();
    }

    @Bean
    @Primary
    public VentanaListarGrupos ventanaListarGrupos() {
        return new VentanaListarGrupos();
    }
} 