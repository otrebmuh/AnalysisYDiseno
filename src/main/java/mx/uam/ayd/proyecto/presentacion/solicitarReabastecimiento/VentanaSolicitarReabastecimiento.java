package mx.uam.ayd.proyecto.presentacion.solicitarReabastecimiento;

import java.util.List;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.modelo.DetallesSolicitud;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

@Component
public class VentanaSolicitarReabastecimiento extends JFrame {
    private JTable tablaProductos;
    private JScrollPane scrollPane;
    private JButton botonSolicitar;
    private JButton botonCancelar;

    private ControladorSolicitarReabastecimiento controlador;
    
    public VentanaSolicitarReabastecimiento() {
        setTitle("Solicitud de Reabastecimiento");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear componentes
        tablaProductos = new JTable();
        scrollPane = new JScrollPane(tablaProductos);
        
        // Botones
        botonSolicitar = new JButton("Solicitar Reabastecimiento");
        botonSolicitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.solicitarReabastecimiento();
            }
        });
        botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.cancelar();
            }
        });
        
        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(botonSolicitar);
        panelBotones.add(botonCancelar);

        JPanel agregar = new JPanel();
        JLabel codigo = new JLabel("Codigo: ");
        JTextField txtCodigo = new JTextField();
        txtCodigo.setColumns(15);
        txtCodigo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.agregarProducto(txtCodigo.getText());
                txtCodigo.setText("");
            }
        });
        JButton botonAgregar = new JButton("Agregar");
        agregar.add(codigo);
        agregar.add(txtCodigo);
        agregar.add(botonAgregar);

        
        // Configurar layout
        setLayout(new BorderLayout());
        add(agregar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    public void muestra(ControladorSolicitarReabastecimiento controlador, List<DetallesSolicitud> productos) {
        this.controlador = controlador;
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Producto");
        modelo.addColumn("Codigo");
        modelo.addColumn("Cantidad");
        
        for (DetallesSolicitud item : productos) {
            Producto producto = item.getProducto();
            modelo.addRow(new Object[]{
                producto.getNombre(),
                producto.getCodigo(),
                0 // Cantidad a solicitar inicialmente
            });
        }
        
        tablaProductos.setModel(modelo);
        setVisible(true);
    }
    public void muestraMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public int obtenerCantidad() {
        String cantidadStr = JOptionPane.showInputDialog(
            this,
            "Ingrese la cantidad deseada para el producto: " ,
            "Cantidad",
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (cantidadStr == null || cantidadStr.trim().isEmpty()) {
            return 0; // Si el usuario cancela o no ingresa nada, retorna 0
        }
        
        try {
            return Integer.parseInt(cantidadStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                this,
                "Por favor ingrese un número válido",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return 0;
        }
    }

    public void actualizarTabla(List<DetallesSolicitud> productos) {
        DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
        modelo.setRowCount(0);
        
        for (DetallesSolicitud item : productos) {
            Producto producto = item.getProducto();
            modelo.addRow(new Object[]{
                producto.getNombre(),
                producto.getCodigo(),
                item.getCantidad()
            });
        }
        tablaProductos.setModel(modelo);
        tablaProductos.updateUI();
    }
}