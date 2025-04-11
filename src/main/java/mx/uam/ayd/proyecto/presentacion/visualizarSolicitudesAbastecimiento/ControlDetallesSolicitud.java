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
    
    private ControlVisualizarSolicitudesAbastecimiento control;
    /**
     * Método que inicia la ventana de detalles de una solicitud
     * 
     * @param solicitud la solicitud cuyos detalles se mostrarán
     */
    public void inicia(ControlVisualizarSolicitudesAbastecimiento control, SolicitudReabastecimiento solicitud) {
        this.control = control;
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
        control.actualizarSolicitudes();
        termina();
    }
    
    /**
     * Método para terminar el caso de uso
     */
    public void termina() {
        ventana.setVisible(false);
    }
    
    /**
     * Método para editar la cantidad de un producto en la solicitud
     * 
     * @param row índice de la fila en la tabla
     * @param cantidadEnAlmacen cantidad disponible en almacén
     */
    public void editarCantidad(int row, Integer cantidadEnAlmacen) {
        // Obtener la cantidad actual
        Integer cantidadActual = solicitudActual.getDetalles().get(row).getCantidad();
        
        // Mostrar el input dialog
        Integer nuevaCantidad = mostrarInputDialog(cantidadActual, cantidadEnAlmacen);
        
        if (nuevaCantidad != null) {
            // Actualizar el modelo
            solicitudActual.getDetalles().get(row).setCantidad(nuevaCantidad);
            servicioSolicitud.guardarSolicitud(solicitudActual);
            
            // Actualizar la vista
            ventana.actualizarTabla(solicitudActual.getDetalles(), 
                servicioSolicitud.obtenerCantidadesEnAlmacen(solicitudActual));
        }
    }
    
    private Integer mostrarInputDialog(Integer cantidadActual, Integer cantidadEnAlmacen) {
        String input = JOptionPane.showInputDialog(ventana, 
            "Cantidad en Almacén: " + cantidadEnAlmacen + "\n" +
            "Ingrese la nueva cantidad:", 
            "Editar Cantidad", 
            JOptionPane.QUESTION_MESSAGE);
            
        if (input != null) {
            try {
                int nuevaCantidad = Integer.parseInt(input);
                return nuevaCantidad;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(ventana, 
                    "Por favor ingrese un número válido", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
}
