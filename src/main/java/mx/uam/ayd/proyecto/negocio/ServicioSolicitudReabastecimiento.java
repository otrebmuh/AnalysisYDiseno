package mx.uam.ayd.proyecto.negocio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.SolicitudReabastecimientoRepository;
import mx.uam.ayd.proyecto.datos.DetallesSolicitudRepository;
import mx.uam.ayd.proyecto.datos.InventarioRepository;
import mx.uam.ayd.proyecto.negocio.modelo.SolicitudReabastecimiento;
import mx.uam.ayd.proyecto.negocio.modelo.Inventario;
import mx.uam.ayd.proyecto.negocio.modelo.DetallesSolicitud;

/**
 * Servicio relacionado con las solicitudes de reabastecimiento
 * 
 * @author Cascade
 *
 */
@Service
public class ServicioSolicitudReabastecimiento {
    
    @Autowired
    private SolicitudReabastecimientoRepository solicitudRepository;
    
    @Autowired
    private DetallesSolicitudRepository detallesSolicitudRepository; // Se utiliza a través de cascada mediante solicitudRepository
    
    @Autowired
    private InventarioRepository inventarioRepository;
    
    /**
     * Recupera todas las solicitudes de reabastecimiento no atendidas
     * 
     * @return Lista de solicitudes no atendidas
     */
    public List<SolicitudReabastecimiento> recuperaSolicitudesNoAtendidas() {
        List<SolicitudReabastecimiento> solicitudes = new ArrayList<>();
        List<SolicitudReabastecimiento> todasSolicitudes = (List<SolicitudReabastecimiento>) solicitudRepository.findAll();
        
        for(SolicitudReabastecimiento solicitud : todasSolicitudes) {
            if(solicitud.getAtendida() != null && !solicitud.getAtendida()) {
                solicitudes.add(solicitud);
            }
        }
        
        return solicitudes;
    }
    
    /**
     * Recupera una solicitud por su ID
     * 
     * @param idSolicitud ID de la solicitud a recuperar
     * @return La solicitud con el ID especificado o null si no existe
     */
    public SolicitudReabastecimiento recuperaSolicitudPorId(Long idSolicitud) {
        return solicitudRepository.findById(idSolicitud).orElse(null);
    }
    
    /**
     * Marca una solicitud como atendida
     * 
     * @param solicitud La solicitud a marcar como atendida
     * @return La solicitud actualizada
     */
    public SolicitudReabastecimiento marcaSolicitudComoAtendida(SolicitudReabastecimiento solicitud) {
        solicitud.setAtendida(true);
        return solicitudRepository.save(solicitud);
    }
    
    /**
     * Obtiene el stock en almacén de cada producto en una solicitud
     * 
     * @param solicitud La solicitud cuyos productos se consultarán
     * @return Una lista de cantidades en almacén correspondiente a cada producto en la solicitud
     */
    public List<Integer> obtenerCantidadesEnAlmacen(SolicitudReabastecimiento solicitud) {
        List<Integer> cantidades = new ArrayList<>();
        
        for(DetallesSolicitud detalle : solicitud.getDetalles()) {
            // Buscar en inventario el stock del producto
            Integer cantidadEnAlmacen = 0;
            List<Inventario> inventarios = (List<Inventario>) inventarioRepository.findAll();
            
            for(Inventario inv : inventarios) {
                if(inv.getProducto().getIdProducto().equals(detalle.getProducto().getIdProducto())) {
                    cantidadEnAlmacen = inv.getStock();
                    break;
                }
            }
            
            cantidades.add(cantidadEnAlmacen);
        }
        
        return cantidades;
    }
}
