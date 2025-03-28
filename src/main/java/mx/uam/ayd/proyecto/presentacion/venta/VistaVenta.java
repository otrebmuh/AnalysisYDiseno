package mx.uam.ayd.proyecto.presentacion.venta;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VistaVenta extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtTotal;
    private JTextField txtCodigo;
    private JTextField txtBusqueda;
    private ControlVenta control;

    public VistaVenta() {
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

        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.setBounds(10, 70, 764, 60);
        searchPanel.setLayout(null);
        contentPane.add(searchPanel);

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(10, 10, 60, 25);
        searchPanel.add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(70, 10, 100, 25);
        searchPanel.add(txtCodigo);

        JLabel lblBusqueda = new JLabel("Búsqueda:");
        lblBusqueda.setBounds(200, 10, 70, 25);
        searchPanel.add(lblBusqueda);

        txtBusqueda = new JTextField();
        txtBusqueda.setBounds(270, 10, 200, 25);
        searchPanel.add(txtBusqueda);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(480, 10, 100, 25);
        searchPanel.add(btnBuscar);

        // Table
        String[] columnNames = {"Código", "Producto", "Dosis", "Presentación", "Precio Unitario", "Cantidad", "Subtotal", "Eliminar"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only allow editing the Cantidad column
            }
        };
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 140, 764, 230);
        contentPane.add(scrollPane);

        // Total amount
        JLabel lblTotal = new JLabel("Importe total: $");
        lblTotal.setBounds(550, 380, 100, 25);
        contentPane.add(lblTotal);

        txtTotal = new JTextField();
        txtTotal.setBounds(650, 380, 124, 25);
        txtTotal.setEditable(false);
        txtTotal.setText("0.00");
        contentPane.add(txtTotal);

        // Buttons
        JButton btnConfirmar = new JButton("Confirmar Venta");
        btnConfirmar.setBounds(550, 420, 150, 25);
        contentPane.add(btnConfirmar);

        JButton btnCancelar = new JButton("Cancelar Venta");
        btnCancelar.setBounds(710, 420, 150, 25);
        contentPane.add(btnCancelar);

        // Add action listeners
        btnConfirmar.addActionListener(e -> control.confirmarVenta());
        btnCancelar.addActionListener(e -> control.cancelarVenta());
        btnBuscar.addActionListener(e -> buscarProducto());
        txtCodigo.addActionListener(e -> agregarProductoPorCodigo());

        // Add table model listener for quantity changes
        table.getModel().addTableModelListener(e -> {
            if (e.getColumn() == 5) { // Cantidad column
                actualizarSubtotalYTotal(e.getFirstRow());
            }
        });
    }

    private void buscarProducto() {
        String criterio = txtBusqueda.getText().trim();
        if (!criterio.isEmpty()) {
            // Implementation pending - will show search results in a dialog
            // and call agregarProducto when one is selected
        }
    }

    private void agregarProductoPorCodigo() {
        String codigo = txtCodigo.getText().trim();
        if (!codigo.isEmpty()) {
            control.agregarProductoAVenta(codigo, 1);
            txtCodigo.setText("");
        }
    }

    private void actualizarSubtotalYTotal(int row) {
        // Implementation pending - will update subtotal for the row
        // and recalculate total
        actualizaTabla();
    }

    public void actualizaTabla() {
        // Implementation pending - will refresh table data from service
        // and update totals
    }

    public void muestraError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void muestraExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    public void muestra(ControlVenta control) {
        this.control = control;
        setVisible(true);
    }
}
