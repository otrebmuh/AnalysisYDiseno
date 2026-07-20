package mx.uam.ayd.proyecto.presentacion.revisionExistencia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioInventario;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.presentacion.revisionExistencia.VistaRevisionExistencia;

/**
 * Módulo Controlador para la HU-03: Recepción de Alertas de Inventario.
 * Se encarga de coordinar la navegación y la comunicación entre la 
 * lógica de negocio y la interfaz de usuario.
 * 
 * @author JAVITOS
 */
@Component
public class ControlRevisarExistencia {

	// Dependencia de la capa de negocio (Inyección de Dependencias)
	private final ServicioInventario servicioInventario;
	
	// Referencia a la vista asociada a esta historia de usuario
	private VistaRevisionExistencia vista;

	/**
	 * Constructor que externaliza la creación de módulos (IoC) [4, 5].
	 * 
	 * @param servicioInventario El servicio que maneja la lógica de alertas.
	 */
	@Autowired
	public ControlRevisarExistencia(ServicioInventario servicioInventario) {
		this.servicioInventario = servicioInventario;
	}

	/**
	 * Inicia el flujo de la historia de usuario.
	 * De acuerdo al diseño, el control pide a la vista que se muestre [3, 6].
	 * 
	 * @param vista Referencia a la vista que será gestionada por este control.
	 */
	public void inicia(VistaRevisionExistencia vista) {
		this.vista = vista;
		
		// 1. El controlador ordena a la vista mostrarse [6, 7]
		this.vista.muestra(this);
		
		// 2. Al iniciar, se cargan las alertas automáticamente para cumplir con RN-14
		this.consultarAlertas();
	}

	/**
	 * Coordina la obtención de productos con stock bajo o agotados.
	 * Satisface la RN-14 (Emisión de alerta) y RN-08 (Identificación por clave) [8-10].
	 */
	public void consultarAlertas() {
		// El controlador delega la lógica pesada al servicio (Slide 85) [3]
		List<Producto> productos = servicioInventario.consultarAlertas();
		
		if (productos.isEmpty()) {
			vista.mostrarMensajeSinAlertas();
		} else {
			// Pasa los datos a la vista para su visualización prioritaria en rojo [6, 8, 11]
			vista.mostrarAlertas(productos);
		}
	}

	/**
	 * Permite consultar el detalle de un producto específico al hacer clic 
	 * sobre una advertencia de la lista [6, 12].
	 *
	 * @param idProducto Identificador técnico del producto seleccionado.
	 */
	public void consultarDetalleProducto(long idProducto) {
		Producto producto = servicioInventario.obtenerDetalleProducto(idProducto);
		vista.mostrarDetalleProducto(producto);
	}
}