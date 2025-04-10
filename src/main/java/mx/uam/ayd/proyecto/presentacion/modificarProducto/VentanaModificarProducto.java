package mx.uam.ayd.proyecto.presentacion.modificarProducto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import mx.uam.ayd.proyecto.negocio.modelo.*;

public class VentanaModificarProducto extends JDialog {

    private JTextField txtCodigo, txtNombre, txtDescripcion, txtContenido, txtPrecio;
    private JCheckBox chkReceta;
    private JComboBox<Laboratorio> cmbLaboratorio;
    private JComboBox<Ingrediente> cmbIngrediente;
    private JComboBox<CategoriaProducto> cmbCategoria;
    private JButton btnModificar;

    public VentanaModificarProducto(JFrame parent, List<Laboratorio> laboratorios,
                                  List<Ingrediente> ingredientes, List<CategoriaProducto> categorias) {
        super(parent, "Modificar Producto", true);
        setSize(500, 600);
        setLocationRelativeTo(parent);
        initUI(laboratorios, ingredientes, categorias);
    }

    private void initUI(List<Laboratorio> laboratorios, List<Ingrediente> ingredientes, 
                       List<CategoriaProducto> categorias) {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Modificar Producto", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));

        txtCodigo = addFormField(formPanel, "Código:");
        txtNombre = addFormField(formPanel, "Nombre:");
        txtDescripcion = addFormField(formPanel, "Descripción:");
        txtContenido = addFormField(formPanel, "Contenido:");
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

        btnModificar = new JButton("Modificar Producto");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnModificar);

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

    public void agregarListenerModificar(ActionListener listener) {
        btnModificar.addActionListener(listener);
    }

    // Métodos para obtener los valores
    public String getCodigo() { return txtCodigo.getText(); }
    public String getNombre() { return txtNombre.getText(); }
    public String getDescripcion() { return txtDescripcion.getText(); }
    public String getContenido() { return txtContenido.getText(); }
    public double getPrecio() { return Double.parseDouble(txtPrecio.getText()); }
    public boolean getReceta() { return chkReceta.isSelected(); }
    public Laboratorio getLaboratorio() { return (Laboratorio) cmbLaboratorio.getSelectedItem(); }
    public Ingrediente getIngrediente() { return (Ingrediente) cmbIngrediente.getSelectedItem(); }
    public CategoriaProducto getCategoria() { return (CategoriaProducto) cmbCategoria.getSelectedItem(); }

    // Métodos para establecer valores (útil al cargar un producto existente)
    public void setCodigo(String codigo) { txtCodigo.setText(codigo); }
    public void setNombre(String nombre) { txtNombre.setText(nombre); }
    public void setDescripcion(String descripcion) { txtDescripcion.setText(descripcion); }
    public void setContenido(String contenido) { txtContenido.setText(contenido); }
    public void setPrecio(double precio) { txtPrecio.setText(String.valueOf(precio)); }
    public void setReceta(boolean receta) { chkReceta.setSelected(receta); }
    public void setLaboratorio(Laboratorio laboratorio) { cmbLaboratorio.setSelectedItem(laboratorio); }
    public void setIngrediente(Ingrediente ingrediente) { cmbIngrediente.setSelectedItem(ingrediente); }
    public void setCategoria(CategoriaProducto categoria) { cmbCategoria.setSelectedItem(categoria); }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}