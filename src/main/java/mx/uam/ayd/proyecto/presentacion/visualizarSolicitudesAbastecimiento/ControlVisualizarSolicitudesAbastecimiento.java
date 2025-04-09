package mx.uam.ayd.proyecto.presentacion.visualizarSolicitudesAbastecimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioSolicitudReabastecimiento;
import mx.uam.ayd.proyecto.negocio.modelo.SolicitudReabastecimiento;

/**
 * Controlador para la visualización de solicitudes de abastecimiento
 * 
 * @author Cascade
 */
@Component
public class ControlVisualizarSolicitudesAbastecimiento {
    
    @Autowired
    private ServicioSolicitudReabastecimiento servicioSolicitud;
    
    @Autowired
    private VentanaVisualizarSolicitudesAbastecimiento ventana;
    
    @Autowired
    private ControlDetallesSolicitud controlDetalles;
    
    /**
     * Método que inicia la historia de usuario
     */
    public void inicia() {
        // Recuperar las solicitudes no atendidas
        List<SolicitudReabastecimiento> solicitudes = servicioSolicitud.recuperaSolicitudesNoAtendidas();
        
        // Mostrar la ventana con las solicitudes
        ventana.muestra(this, solicitudes);
    }
    
    /**
     * Método para revisar una solicitud de abastecimiento específica
     * 
     * @param solicitud la solicitud a revisar
     */
    public void revisarSolicitud(SolicitudReabastecimiento solicitud) {
        // Iniciar el controlador de detalles de la solicitud
        controlDetalles.inicia(solicitud);
    }
    
    /**
     * Método para marcar una solicitud como atendida
     * 
     * @param solicitud la solicitud a marcar como atendida
     */
    public void marcarComoAtendida(SolicitudReabastecimiento solicitud) {
        servicioSolicitud.marcaSolicitudComoAtendida(solicitud);
        
        // Actualizar la lista de solicitudes
        List<SolicitudReabastecimiento> solicitudes = servicioSolicitud.recuperaSolicitudesNoAtendidas();
        ventana.actualizarTabla(solicitudes);
    }
    
    /**
     * Método para terminar el caso de uso
     */
    public void termina() {
        ventana.setVisible(false);
    }
}
