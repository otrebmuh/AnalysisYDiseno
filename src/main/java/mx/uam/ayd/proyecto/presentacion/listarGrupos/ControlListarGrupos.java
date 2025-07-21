package mx.uam.ayd.proyecto.presentacion.listarGrupos;

import java.util.List;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.uam.ayd.proyecto.negocio.ServicioGrupo;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;

@Component
public class ControlListarGrupos {
    
    private static final Logger log = LoggerFactory.getLogger(ControlListarGrupos.class);
    
    private final ServicioGrupo servicioGrupo;
    private final VentanaListarGrupos ventana;

    @Autowired
    public ControlListarGrupos(ServicioGrupo servicioGrupo, VentanaListarGrupos ventana) {
        this.servicioGrupo = servicioGrupo;
        this.ventana = ventana;
    }
    
    /**
     * Método que se ejecuta después de la construcción del bean
     * y realiza la conexión bidireccional entre el control y la ventana
     */
    @PostConstruct
    public void init() {
        ventana.setControlListarGrupos(this);
    }

    /**
     * Inicia el caso de uso
     */
    public void inicia() {
        actualizaDatos();
    }
    
    /**
     * Actualiza los datos en la ventana
     */
    public void actualizaDatos() {
        // Siempre obtenemos datos frescos del repositorio
        List<Grupo> grupos = servicioGrupo.recuperaGrupos();
        
        for(Grupo grupo : grupos) {
            log.info("grupo: " + grupo + " con " + grupo.getUsuarios().size() + " usuarios");
        }
        
        ventana.muestra(grupos);
    }
} 