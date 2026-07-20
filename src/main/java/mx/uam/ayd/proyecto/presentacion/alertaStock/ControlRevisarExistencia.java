package mx.uam.ayd.proyecto.presentacion.alertaStock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioInventario;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

/**
 * @author kevin dydier
 */
@Component
public class ControlRevisarExistencia {

	private final ServicioInventario servicioInventario;
	////////////////////////////////////////////////////////////////////// referencias a las vistas
	private VentanaRevisionExistencia ventanaRevision;
	private VentanaDetalleProducto ventanaDetalle;

	///////////////////////////////////////////////////////////////////////////// constructori para inyeccin de dependencia s
	@Autowired
	public ControlRevisarExistencia(ServicioInventario servicioInventario, VentanaRevisionExistencia ventanaRevision, VentanaDetalleProducto ventanaDetalle) {
		this.servicioInventario = servicioInventario;
		this.ventanaRevision = ventanaRevision;
		this.ventanaDetalle = ventanaDetalle;
	}

//////////////////////////////////////////////////////////////////////////////////////Metodos del controlador 
	public void inicia() {
		this.ventanaRevision.muestra(this);
		this.consultarAlertas();
	}

////////////////////////////////////////////////////////////////////////////////////////////// obtiene los productos con bajo stock 
	public void consultarAlertas() {
		List<Producto> productosAlerta = servicioInventario.consultarAlertas();
		
		if (productosAlerta.isEmpty()) {
			ventanaRevision.mostrarMensajeSinAlertas();
		} else {
			// Envía la lista a la vista para el resaltar en rojo los productos con bajo stock
			ventanaRevision.mostrarAlertas(productosAlerta);
		}
	}

	/**
	 * Despliega el detalle del stock actual y el límite mínimo configurado.
	 * Se ejecuta cuando el usuario hace clic sobre una advertencia en rojo
	 *
	 * @param idProducto Identificador técnico para recuperar el detalle.
	 */
	public void consultarDetalleProducto(long idProducto) {
		// Recupera el producto y su stock mínimo desde el negocio
		Producto producto = servicioInventario.obtenerDetalleProducto(idProducto);
        
        // En un diseño real, el stock mínimo se recupera del objeto Inventario asociado
        // aquí simplificamos el paso de datos al diálogo de detalle
		ventanaDetalle.muestra(producto, 10); // Ejemplo: 10 como stock mínimo
	}
}
