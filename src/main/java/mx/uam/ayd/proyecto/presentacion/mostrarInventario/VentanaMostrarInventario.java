package mx.uam.ayd.proyecto.presentacion.mostrarInventario;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.modelo.Inventario;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

@Component
public class VentanaMostrarInventario extends javax.swing.JFrame {

    private ControladorMostrarInventario controlador;

    public VentanaMostrarInventario() {
        // Configurar la ventana
        setTitle("Inventario de la Sucursal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear componentes
        tablaInventario = new JTable();
        scrollPane = new JScrollPane(tablaInventario);
        
        JPanel botones = new JPanel();
        // Botón para solicitar reabastecimiento
        botonSolicitarReabastecimiento = new JButton("Solicitar Reabastecimiento");
        botones.add(botonSolicitarReabastecimiento);
        
        // Configurar layout
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    public void muestra(ControladorMostrarInventario controlador) {
        this.controlador = controlador;
        
        // Configurar el action listener del botón
        botonSolicitarReabastecimiento.addActionListener(e -> {
            controlador.solicitarReabastecimiento();
        });
        
        setVisible(true);
    }

    public void actualizarTabla(List<Inventario> inventario) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Producto");
        modelo.addColumn("Codigo");
        modelo.addColumn("Stock");
        
        for (Inventario item : inventario) {
            Producto producto = item.getProducto();
            modelo.addRow(new Object[]{
                producto.getNombre(),
                producto.getCodigo(),
                item.getStock()
            });
        }
        
        tablaInventario.setModel(modelo);
    }
    
    // Componentes de la interfaz
    private JTable tablaInventario;
    private JScrollPane scrollPane;
    private JButton botonSolicitarReabastecimiento;
}