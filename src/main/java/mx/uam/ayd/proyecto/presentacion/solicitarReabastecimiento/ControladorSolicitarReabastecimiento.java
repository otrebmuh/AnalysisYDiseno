package mx.uam.ayd.proyecto.presentacion.solicitarReabastecimiento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioProducto;
import mx.uam.ayd.proyecto.negocio.ServicioSolicitudReabastecimiento;
import mx.uam.ayd.proyecto.negocio.modelo.DetallesSolicitud;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.negocio.modelo.SolicitudReabastecimiento;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;

@Component
public class ControladorSolicitarReabastecimiento {
    @Autowired
    private ServicioProducto servicioProducto;
    
    @Autowired
    private VentanaSolicitarReabastecimiento ventana;

    @Autowired
    private ServicioSolicitudReabastecimiento servicioSolicitud;

    private List<DetallesSolicitud> detallesSolicitud;
    private SolicitudReabastecimiento solicitud;
    
    public void inicia(Sucursal sucursal) {
        // Obtener productos que necesitan reabastecimiento
        detallesSolicitud = new ArrayList<>();
        solicitud = new SolicitudReabastecimiento();
        solicitud.setSucursal(sucursal);
        solicitud.setFecha(new Date());
        ventana.muestra(this,detallesSolicitud);
    }

    public void solicitarReabastecimiento() {
        // Aquí puedes implementar la logica para solicitar reabastecimiento
        // Puedes acceder a los detalles de la solicitud y a la sucursal actual
        if (detallesSolicitud.isEmpty()) {
            ventana.muestraMensaje("Debe agregar al menos un producto");
            return;
        }
        for (DetallesSolicitud detalle : detallesSolicitud) {
            solicitud.agregarDetalle(detalle);
        }
        servicioSolicitud.guardarSolicitud(solicitud);
        ventana.muestraMensaje("Solicitud enviada");
        ventana.dispose();

    }   

    public void cancelar() {
        // Aquí puedes implementar la logica para cancelar la solicitud
        ventana.dispose();
    }

    public void agregarProducto(String codigo) {
        Producto producto = servicioProducto.obtenerPorCodigo(codigo);
        if (producto == null) {
            ventana.muestraMensaje("Producto no encontrado");
            return;
        }
        int cantidad = ventana.obtenerCantidad();
        if (cantidad <= 0) {
            ventana.muestraMensaje("Cantidad debe ser mayor a 0");
            return;
        }
        DetallesSolicitud detalle= new DetallesSolicitud();
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setSolicitudReabastecimiento(solicitud);
        detallesSolicitud.add(detalle);
        ventana.actualizarTabla(detallesSolicitud);
        ventana.muestraMensaje("Producto agregado");
    }


    
}