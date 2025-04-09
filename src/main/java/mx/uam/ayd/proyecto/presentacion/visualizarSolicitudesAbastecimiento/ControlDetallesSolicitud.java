package mx.uam.ayd.proyecto.presentacion.visualizarSolicitudesAbastecimiento;

import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioSolicitudReabastecimiento;
import mx.uam.ayd.proyecto.negocio.modelo.DetallesSolicitud;
import mx.uam.ayd.proyecto.negocio.modelo.SolicitudReabastecimiento;

/**
 * Controlador para la visualización de detalles de una solicitud de abastecimiento
 * 
 * @author Eduardo Morgado
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
        try {
            this.solicitudActual = solicitud;
            
            // Obtener los detalles de la solicitud explícitamente
            List<DetallesSolicitud> detalles = servicioSolicitud.obtenerDetallesSolicitud(solicitud);
            
            // Obtener las cantidades en almacén
            List<Integer> cantidadesEnAlmacen = servicioSolicitud.obtenerCantidadesEnAlmacen(solicitud);
            
            // Mostrar la ventana con los detalles
            ventana.muestra(this, solicitud, detalles, cantidadesEnAlmacen);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los detalles: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
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
