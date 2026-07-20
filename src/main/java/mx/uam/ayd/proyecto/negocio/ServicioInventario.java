package mx.uam.ayd.proyecto.negocio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.InventarioRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Inventario;

@Service
public class ServicioInventario {

    private final InventarioRepository inventarioRepository;

    @Autowired
    public ServicioInventario(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    /**
     * Recupera todos los registros del inventario.
     *
     * @return Lista de inventarios.
     */
    public List<Inventario> recuperaInventario() {

        List<Inventario> inventarios = new ArrayList<>();

        for (Inventario inventario : inventarioRepository.findAll()) {
            inventarios.add(inventario);
        }

        return inventarios;
    }


/**
 * Recupera únicamente los productos con bajo stock.
 *
 * @return Lista de inventarios con bajo stock.
 */
public List<Inventario> obtenerProductosBajoStock() {

    List<Inventario> productosBajoStock = new ArrayList<>();

    for (Inventario inventario : inventarioRepository.findAll()) {

        if (inventario.getExistenciaActual() <= inventario.getStockMinimo()) {

            productosBajoStock.add(inventario);

        }

    }

    return productosBajoStock;
   }
}
