package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.negocio.modelo.Inventario;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;
import mx.uam.ayd.proyecto.datos.InventarioRepository;
import mx.uam.ayd.proyecto.datos.SucursalRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.datos.ProductoRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicioActualizarInventario {

    private final InventarioRepository inventarioRepository;
    private final SucursalRepository sucursalRepository;
    private final ProductoRepository productoRepository;

    /**
     * Actualiza el inventario a partir de un archivo CSV.
     * @param file Archivo CSV.
     * @return Lista de inventario actualizado.
     */
    public List<Inventario> actualizarInventario(MultipartFile file) {
        List<Inventario> inventariosActualizados = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            boolean header = true;

            while ((line = reader.readLine()) != null) {
                // Saltar la cabecera
                if (header) {
                    header = false;
                    continue;
                }

                String[] data = line.split(",");

                if (data.length != 4) {
                    System.out.println("Formato incorrecto en línea: " + line);
                    continue;
                }

                Long idSucursal = Long.parseLong(data[0].trim());
                Long idProducto = Long.parseLong(data[1].trim());
                Integer cantidad = Integer.parseInt(data[2].trim());
                Double precioCompra = Double.parseDouble(data[3].trim());

                // Buscar sucursal
                Optional<Sucursal> sucursalOpt = sucursalRepository.findById(idSucursal);
                if (!sucursalOpt.isPresent()) { // Se usa isEmpty a partir de java 11
                    // Manejar el caso en que la sucursal no se encuentra
                    System.out.println("Sucursal no encontrada: " + idSucursal);
                    continue;
                }

                Sucursal sucursal = sucursalOpt.get();

                //Buscar producto por ID
                Optional<Producto> productoOpt = productoRepository.findById(idProducto);
                if (!productoOpt.isPresent()) {
                    throw new IllegalArgumentException("Producto no encontrado con ID: " + idProducto);
                }

                // Buscar el inventario usando la sucursal y el producto
                Producto producto = productoOpt.get();
                Optional<Inventario> inventarioOpt = inventarioRepository.findBySucursalAndProducto(sucursal, producto);

                if (!inventarioOpt.isPresent()) {
                    throw new IllegalArgumentException("Inventario no encontrado para la sucursal y el producto especificados.");
                }

                Inventario inventario;

                if (inventarioOpt.isPresent()) {
                    // Actualizar stock y precio
                    inventario = inventarioOpt.get();
                    inventario.setStock(inventario.getStock() + cantidad);
                } else {
                    // Crear nuevo inventario si no existe
                    inventario = new Inventario();
                    inventario.setSucursal(sucursal);
                    inventario.setStock(cantidad);
                }

                inventarioRepository.save(inventario);
                inventariosActualizados.add(inventario);
            }

        } catch (Exception e) {
            System.err.println("Error al procesar el archivo CSV: " + e.getMessage());
        }

        return inventariosActualizados;
    }
}
//Comentario para intentar hacer push

