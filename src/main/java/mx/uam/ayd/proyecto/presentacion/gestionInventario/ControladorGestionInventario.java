package mx.uam.ayd.proyecto.presentacion.gestionInventario;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import mx.uam.ayd.proyecto.negocio.ServicioInventario;
import mx.uam.ayd.proyecto.negocio.ServicioProducto;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;

import javax.swing.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class ControladorGestionInventario {
    @Autowired
    private VentanaGestionInventario ventana;
    private List<Pair<Producto,Integer>> productos;
    @Autowired
    private ServicioProducto servicioProducto;
    @Autowired
    private ServicioInventario servicioInventario;
    private Sucursal sucursal;
    
    public ControladorGestionInventario() {
        ventana = new VentanaGestionInventario();
        productos = new ArrayList<>();
    }

    private void mostrarProductos() {
        try {

            ventana.limpiarTabla();
            for (Pair<Producto,Integer> par : productos) {
                ventana.agregarProducto(
                    par.getFirst().getNombre(),
                    String.valueOf(par.getSecond())
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventana, "Error al cargar los productos: " + e.getMessage());
        }
    }

    public void agregarInventarioDesdeCSV() {
        Logger.getGlobal().info("Agregando inventario desde CSV");
        JFileChooser fileChooser = new JFileChooser();
        int seleccion = fileChooser.showOpenDialog(ventana);
        
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try (CSVReader reader = new CSVReader(new FileReader(archivo))) {
                String[] linea;
                productos.clear();
                while ((linea = reader.readNext()) != null) {
                    if (linea.length == 2) {
                        try {
                            String nombre = linea[0];
                            int cantidad = Integer.parseInt(linea[1]);
                            Producto p = servicioProducto.obtenerPorNombre(nombre);
                            if (p == null) {
                                throw new IllegalArgumentException("No existe el producto: " + nombre);
                            }
                            Pair<Producto, Integer> par = Pair.of(p,cantidad);
                            productos.add(par);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(ventana, "Error al procesar la línea: " + e.getMessage());
                        } catch (IllegalArgumentException e) {
                            JOptionPane.showMessageDialog(ventana, e.getMessage());
                        }
                    }
                }
                mostrarProductos();
                JOptionPane.showMessageDialog(ventana, "Archivo cargado exitosamente.");
            } catch (IOException | CsvValidationException e) {
                JOptionPane.showMessageDialog(ventana, "Error al leer el archivo CSV: " + e.getMessage());
            }
            mostrarProductos();
        }
    }

    public void agregarInventario() {
        Logger.getGlobal().info("Agregando inventario");
        if (productos == null ||productos.isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "No hay productos para agregar");
            return;
        }

        for (Pair<Producto, Integer> par : productos) {
            servicioInventario.actualizarInventario(sucursal, par.getFirst(), par.getSecond());
        }
        JOptionPane.showMessageDialog(ventana, "Inventario agregado exitosamente");
        productos.clear();
        mostrarProductos();
    }

    public void inicia(Sucursal sucursal) {
        this.sucursal = sucursal;
        ventana.muestra(this);
    }
}