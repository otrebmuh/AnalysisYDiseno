package mx.uam.ayd.proyecto.presentacion.gestionInventario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.stereotype.Component;

@Component
public class VentanaGestionInventario extends JFrame {
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JButton btnCargarCSV;
    private JButton btnAgregarInventario;
    private JScrollPane scrollPane;

    private ControladorGestionInventario controladorGestionInventario;

    public VentanaGestionInventario() {
        setTitle("Gestión de Inventario");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear modelo de tabla
        String[] columnas = {"Producto", "Cantidad"};
        modeloTabla = new DefaultTableModel(columnas, 0);

        // Crear tabla
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setAutoCreateRowSorter(true);
        scrollPane = new JScrollPane(tablaProductos);

        // Crear botón para cargar un archivo csv
        btnCargarCSV = new JButton("Cargar desde CSV");
        btnCargarCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorGestionInventario.agregarInventarioDesdeCSV();
            }
        });

        // Crear botón para cargar un archivo csv
        btnAgregarInventario = new JButton("Agregar Inventario");
        btnAgregarInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorGestionInventario.agregarInventario();
            }
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnCargarCSV);
        panelBotones.add(btnAgregarInventario);

        // Configurar layout
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public void agregarProducto(String producto, String cantidad) {
        Object[] fila = {producto, cantidad};
        modeloTabla.addRow(fila);
        tablaProductos.updateUI();
    }

    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    public void muestra(ControladorGestionInventario controladorGestionInventario) {
        this.controladorGestionInventario = controladorGestionInventario;
        setVisible(true);
    }

}