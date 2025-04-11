package mx.uam.ayd.proyecto.negocio;

import mx.uam.ayd.proyecto.datos.InventarioRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import mx.uam.ayd.proyecto.datos.ProductoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Inventario;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;


import org.springframework.stereotype.Component;

@Component
public class ServicioInventario {
    
    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductoRepository productoRepository;
    
    public void actualizarInventario(Sucursal sucursal,Producto producto, int cantidad) {
        try {
            Optional<Inventario> inventarioOpt = inventarioRepository.findBySucursalAndProducto(sucursal, producto);
            Inventario inventario;
            
            if (inventarioOpt.isPresent()) {
                inventario = inventarioOpt.get();
            } else {
                inventario = new Inventario();
                inventario.setSucursal(sucursal);
                inventario.setProducto(producto);
            }

            inventario.agregarStock(cantidad);
            inventarioRepository.save(inventario);
            
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el inventario: " + e.getMessage());
        }
    }

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

    public Inventario obtenerPorSucursalYProducto(Sucursal sucursal, Producto producto) {
        Optional<Inventario> inventarioOpt = inventarioRepository.findBySucursalAndProducto(sucursal, producto);
        return inventarioOpt.orElse(null);
    }

    public void agregarStock(Sucursal sucursal, Producto producto, int cantidad) {
        Optional<Inventario> inventarioOpt = inventarioRepository.findBySucursalAndProducto(sucursal, producto);
        Inventario inventario = inventarioOpt.orElse(null);
        if (inventario == null) {
            inventario = new Inventario();
            inventario.setSucursal(sucursal);
            inventario.setProducto(producto);
        }
        inventario.agregarStock(cantidad);
        inventarioRepository.save(inventario);
    }

    public void disminuirStock(Sucursal sucursal, Producto producto, int cantidad) {
        Optional<Inventario> inventarioOpt = inventarioRepository.findBySucursalAndProducto(sucursal, producto);
        Inventario inventario = inventarioOpt.orElse(null);
        if (inventario == null) {
            inventario = new Inventario();
            inventario.setSucursal(sucursal);
            inventario.setProducto(producto);
        }
        inventario.disminuirStock(cantidad);
        inventarioRepository.save(inventario);
    }
}