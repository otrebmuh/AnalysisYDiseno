package mx.uam.ayd.proyecto.negocio;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.uam.ayd.proyecto.datos.BitacoraRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Bitacora;

/**
 * Servicio para gestionar la lógica de negocio de la Bitácora.
 *
 * Se encarga de registrar y persistir los eventos del sistema.
 *
 * @author Yamelin Larios Nepomuseno
 */
@Service
public class ServicioBitacora {

    @Autowired
    private BitacoraRepository bitacoraRepository;

    /**
     * Registra un cambio de precio de un producto en la bitácora para la HU-09.
     *
     * @param idProducto Identificador del producto modificado
     * @param precioAnterior Precio previo a la actualización
     * @param precioNuevo Nuevo precio asignado
     * @return El objeto Bitacora persistido.
     */
    @Transactional
    public Bitacora registrarCambioPrecio(long idProducto, double precioAnterior, double precioNuevo) {
        
        // Instanciar y llenar el objeto Bitacora
        Bitacora bitacora = new Bitacora();
        bitacora.setIdProducto((int) idProducto);
        bitacora.setPrecioAnterior(precioAnterior);
        bitacora.setPrecioNuevo(precioNuevo);
        bitacora.setFechaHora(LocalDateTime.now());

        // Persistir el registro en el repositorio
        return bitacoraRepository.save(bitacora);
    }
}