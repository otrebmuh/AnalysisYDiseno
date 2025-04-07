package mx.uam.ayd.proyecto.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.uam.ayd.proyecto.datos.InventarioRepository;
import mx.uam.ayd.proyecto.datos.ProductoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Inventario;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;

@Service
public class ServicioInventario {

    @Autowired
    private InventarioRepository inventarioRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    

    // Obtener inventario de una sucursal
    public List<Inventario> obtenerInventario(Sucursal sucursal) {
        Iterable<Producto> productos = productoRepository.findAll();
        ArrayList<Inventario> inventarios = new ArrayList<>();
        for (Producto producto : productos) {
            Optional<Inventario> inventario = inventarioRepository.findBySucursalAndProducto(sucursal, producto);
            if (inventario.isPresent()) {
                inventarios.add(inventario.get());
            }else{
                Inventario nuevoInventario = new Inventario();
                nuevoInventario.setSucursal(sucursal);
                nuevoInventario.setProducto(producto);
                nuevoInventario.setStock(0);
                inventarios.add(nuevoInventario);
            }
        }

        return inventarios;
    }
}