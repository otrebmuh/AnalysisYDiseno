package mx.uam.ayd.proyecto.negocio;

import mx.uam.ayd.proyecto.datos.InventarioRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Inventario;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServicioInventario {
    
    @Autowired
    private InventarioRepository inventarioRepository;
    
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
}