package mx.uam.ayd.proyecto.presentacion.visualizarSolicitudesAbastecimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioSolicitudReabastecimiento;
import mx.uam.ayd.proyecto.negocio.modelo.SolicitudReabastecimiento;

/**
 * Controlador para la visualización de detalles de una solicitud de abastecimiento
 * 
 * @author Cascade
 */
@Component
public class ControlDetallesSolicitud {
    
    @Autowired
    private ServicioSolicitudReabastecimiento servicioSolicitud;
    
    @Autowired
    private VentanaDetallesSolicitud ventana;
    
    private SolicitudReabastecimiento solicitudActual;
    
    /**
     * Método que inicia la ventana de detalles de una solicitud
     * 
     * @param solicitud la solicitud cuyos detalles se mostrarán
     */
    public void inicia(SolicitudReabastecimiento solicitud) {
        this.solicitudActual = solicitud;
        
        // Obtener las cantidades en almacén
        List<Integer> cantidadesEnAlmacen = servicioSolicitud.obtenerCantidadesEnAlmacen(solicitud);
        
        // Mostrar la ventana con los detalles
        ventana.muestra(this, solicitud, cantidadesEnAlmacen);
    }
    
    /**
     * Método para marcar la solicitud actual como atendida
     */
    public void marcarComoAtendida() {
        servicioSolicitud.marcaSolicitudComoAtendida(solicitudActual);
        termina();
    }
    
    /**
     * Método para terminar el caso de uso
     */
    public void termina() {
        ventana.setVisible(false);
    }
}
