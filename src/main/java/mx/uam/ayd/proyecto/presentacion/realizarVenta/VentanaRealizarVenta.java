/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mx.uam.ayd.proyecto.presentacion.realizarVenta;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Lalo
 */
public class VentanaRealizarVenta extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtTotal;
    private ControlRealizarVenta control;

    /**
     * Creates new form VentanaRealizarVenta
     */
    public VentanaRealizarVenta() {
        setTitle("Realizar una venta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 500);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Header panel with date and cashier info
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(10, 10, 764, 50);
        headerPanel.setLayout(null);
        contentPane.add(headerPanel);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd / MMMM /yyyy    HH:mm 'hrs.'");
        JLabel lblDate = new JLabel(now.format(formatter));
        lblDate.setBounds(10, 10, 300, 20);
        headerPanel.add(lblDate);

        JLabel lblCajero = new JLabel("Cajero Responsable: Juan Perez");
        lblCajero.setBounds(400, 10, 300, 20);
        headerPanel.add(lblCajero);

        // Table
        String[] columnNames = {"Código", "Producto", "Dosis", "Presentación", "Precio Unitario", "Cantidad", "Subtotal"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 70, 764, 300);
        contentPane.add(scrollPane);

        // Total amount
        JLabel lblTotal = new JLabel("Importe total: $");
        lblTotal.setBounds(550, 380, 100, 25);
        contentPane.add(lblTotal);

        txtTotal = new JTextField();
        txtTotal.setBounds(650, 380, 124, 25);
        txtTotal.setEditable(false);
        txtTotal.setText("156");
        contentPane.add(txtTotal);

        // Buttons
        JButton btnConfirmar = new JButton("Confirmar Venta");
        btnConfirmar.setBounds(550, 420, 150, 25);
        contentPane.add(btnConfirmar);

        JButton btnCancelar = new JButton("Cancelar Venta");
        btnCancelar.setBounds(710, 420, 150, 25);
        contentPane.add(btnCancelar);

        // Add action listeners
        btnConfirmar.addActionListener(e -> confirmarVenta());
        btnCancelar.addActionListener(e -> dispose());

        // Add a default row
        model.addRow(new Object[]{"726511", "", "", "", "", "", ""});
    }

    public void setControl(ControlRealizarVenta control) {
        this.control = control;
    }

    private void confirmarVenta() {
        if (control != null) {
            // Implement confirmation logic here
            control.confirmarVenta();
        }
    }

    public void muestra(ControlRealizarVenta control) {
        this.control = control;
        setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaRealizarVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaRealizarVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaRealizarVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaRealizarVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaRealizarVenta().setVisible(true);
            }
        });
    }
}
