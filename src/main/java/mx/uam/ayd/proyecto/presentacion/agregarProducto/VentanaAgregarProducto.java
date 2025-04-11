package mx.uam.ayd.proyecto.presentacion.agregarProducto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import mx.uam.ayd.proyecto.negocio.modelo.*;

public class VentanaAgregarProducto extends JDialog {

    private final ControlAgregarProducto controlador;

    private JTextField txtCodigo, txtNombre, txtDescripcion, txtContenido, txtPrecio;
    private JCheckBox chkReceta;
    private JComboBox<Laboratorio> cmbLaboratorio;
    private JComboBox<Ingrediente> cmbIngrediente;
    private JComboBox<CategoriaProducto> cmbCategoria;

    public VentanaAgregarProducto(ControlAgregarProducto controlador, List<Laboratorio> laboratorios,
                                  List<Ingrediente> ingredientes, List<CategoriaProducto> categorias) {
        super((JFrame) null, "Agregar Producto", true);
        this.controlador = controlador;

        setSize(500, 600); // más alto para incluir el campo nuevo
        setLocationRelativeTo(null);
        initUI(laboratorios, ingredientes, categorias);
    }

    private void initUI(List<Laboratorio> laboratorios, List<Ingrediente> ingredientes, List<CategoriaProducto> categorias) {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Agregar Producto Nuevo", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10)); // antes era 8 filas

        txtCodigo = addFormField(formPanel, "Código:");
        txtNombre = addFormField(formPanel, "Nombre:");
        txtDescripcion = addFormField(formPanel, "Descripción:");
        txtContenido = addFormField(formPanel, "Contenido:");

        // 🔹 Campo nuevo: Precio
        txtPrecio = addFormField(formPanel, "Precio:");

        chkReceta = new JCheckBox("¿Requiere Receta?");
        formPanel.add(new JLabel("Receta:"));
        formPanel.add(chkReceta);

        cmbIngrediente = new JComboBox<>(ingredientes.toArray(new Ingrediente[0]));
        formPanel.add(new JLabel("Ingrediente Activo:"));
        formPanel.add(cmbIngrediente);

        cmbLaboratorio = new JComboBox<>(laboratorios.toArray(new Laboratorio[0]));
        formPanel.add(new JLabel("Laboratorio:"));
        formPanel.add(cmbLaboratorio);

        cmbCategoria = new JComboBox<>(categorias.toArray(new CategoriaProducto[0]));
        formPanel.add(new JLabel("Categoría:"));
        formPanel.add(cmbCategoria);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAgregar = new JButton("Agregar Producto");
        btnAgregar.addActionListener(this::agregarProducto);
        buttonPanel.add(btnAgregar);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 20));
        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel);
    }

    private JTextField addFormField(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField();
        panel.add(label);
        panel.add(textField);
        return textField;
    }

    private void agregarProducto(ActionEvent e) {
        try {
            double precio = Double.parseDouble(txtPrecio.getText());
            controlador.agregarProducto( // Cambia ControlAgregarProducto por agregarProducto
                txtCodigo.getText(),
                txtNombre.getText(),
                txtDescripcion.getText(),
                txtContenido.getText(),
                chkReceta.isSelected(),
                (Laboratorio) cmbLaboratorio.getSelectedItem(),
                (Ingrediente) cmbIngrediente.getSelectedItem(),
                (CategoriaProducto) cmbCategoria.getSelectedItem(),
                precio
            );
        } catch (NumberFormatException ex) {
            mostrarError("Por favor, ingresa un valor numérico válido para el precio.");
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
