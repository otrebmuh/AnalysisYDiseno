package mx.uam.ayd.proyecto.presentacion.venta;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;


@Component
public class VistaVenta extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtTotal;
    private JTextField txtCodigo;
    private JTextField txtBusqueda;
    private ControlVenta control;
    private JLabel lblTotal;
    private JLabel lblCajero;

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

        lblCajero = new JLabel("Cajero Responsable: " );
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

        JLabel lblBusqueda = new JLabel("Cantidad (opcional):");
        lblBusqueda.setBounds(200, 10, 70, 25);
        searchPanel.add(lblBusqueda);

        txtBusqueda = new JTextField();
        txtBusqueda.setBounds(270, 10, 200, 25);
        searchPanel.add(txtBusqueda);

        JButton btnBuscar = new JButton("Agregar");
        btnBuscar.setBounds(480, 10, 100, 25);
        searchPanel.add(btnBuscar);

        // Table
        String[] columnNames = {"Código", "Producto", "Dosis", "Presentación", "Precio Unitario", "Cantidad", "Subtotal", "Eliminar", "ID detalle"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 7; // Only allow editing the Cantidad column
            }
        };
        table = new JTable(model);

        //Codigo para agregar botones de eliminar
        table.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Eliminar").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 140, 764, 230);
        contentPane.add(scrollPane);

        // Total amount
        lblTotal = new JLabel("Importe total: $");
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
        btnConfirmar.addActionListener(e -> confirmarVenta());
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
        String codigo = txtCodigo.getText().trim();
        String cant = txtBusqueda.getText().trim();
        //int cantidad = Integer.parseInt(cant);
        if (codigo.isEmpty() || cant.isEmpty()) {
            muestraError("Debe ingresar un código y una cantidad.");
            return;
       }

            try {
                int cantidad = Integer.parseInt(cant);
                control.agregarProductoAVenta(codigo, cantidad);
                txtCodigo.setText("");
                txtBusqueda.setText("");
                actualizarSubtotalYTotal(6);
             } catch (IllegalArgumentException e) {
                muestraError("Error al agregar producto: " + e.getMessage());
                return;
            }

    }

    public void confirmarVenta() {
        try{
            control.confirmarVenta();
            limpiarTabla();
        }catch(IllegalArgumentException e){
            muestraError("Error al confirmar venta: " + e.getMessage());
            return;
        }
    }



    private void agregarProductoPorCodigo() {
        String codigo = txtCodigo.getText().trim();
        if (!codigo.isEmpty()) {
            try {
                control.agregarProductoAVenta(codigo, 1);
                txtCodigo.setText("");
                actualizarSubtotalYTotal(6);
             } catch (IllegalArgumentException e) {
                muestraError("Error al agregar producto: " + e.getMessage());
                return;
            }
       }
    }

    private void actualizarSubtotalYTotal(int row) {
        // Implementation pending - will update subtotal for the row
        // and recalculate total
        double suma = 0.0;
        for(int fila = 0; fila<model.getRowCount(); fila++){
            Object valor = model.getValueAt(fila, row);
            if(valor != null){
                try{
                    suma += Double.parseDouble(valor.toString());

                }catch(NumberFormatException e){
                    muestraError("No se puede hacer el total");
                }
            }
        }

        String suma_text = String.valueOf(suma);
        txtTotal.setText(suma_text);


    }

    public void actualizaTabla(Object[] nuevaFila) {
        // Table
        model.addRow(nuevaFila);
    }

    public void limpiarTabla(){
        model.setRowCount(0);
        txtTotal.setText("0.00");
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

    public void setCajeroResponsable(String nombreCajero){
        lblCajero.setText("Cajero responsable: " + nombreCajero);
    }


    // Custom renderer for the "Eliminar" button
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Eliminar");
            return this;
        }
    }

    // Custom editor for the "Eliminar" button
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public java.awt.Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            label = "Eliminar";
            button.setText(label);
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                // Remove the row from the table
                int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas eliminar esta fila?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(row);
                    Long idDetalle = (Long) model.getValueAt(row, 8); // Assuming ID detalle is in column 8
                    control.eliminarDetalleVenta(idDetalle);
                    actualizarSubtotalYTotal(6); // Update totals after deletion
                }
            }
            clicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

}
