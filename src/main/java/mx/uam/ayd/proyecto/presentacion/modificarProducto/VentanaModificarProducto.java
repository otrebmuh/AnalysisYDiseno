package mx.uam.ayd.proyecto.presentacion.modificarProducto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaModificarProducto extends JDialog {

    private JTextField campoCodigo, campoNombre, campoDescripcion, campoIngrediente, campoLaboratorio, campoContenido, campoReceta, campoCategoria;
    private JButton btnModificar;

    public VentanaModificarProducto(JFrame parent) {
        super(parent, "Modificar Producto", true);
        setSize(500, 550);
        setLocationRelativeTo(parent);
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Modificar Producto", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titulo, BorderLayout.NORTH);

        JLabel instruccion = new JLabel("Por favor llene el siguiente formulario para editar el producto", SwingConstants.CENTER);
        mainPanel.add(instruccion, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        campoCodigo = agregarCampo(formPanel, "Código:");
        campoNombre = agregarCampo(formPanel, "Nombre:");
        campoDescripcion = agregarCampo(formPanel, "Descripción:");
        campoIngrediente = agregarCampo(formPanel, "Ingrediente Activo:");
        campoLaboratorio = agregarCampo(formPanel, "Laboratorio:");
        campoContenido = agregarCampo(formPanel, "Contenido:");
        campoReceta = agregarCampo(formPanel, "¿Requiere Receta?:");
        campoCategoria = agregarCampo(formPanel, "Categoría:");

        btnModificar = new JButton("Modificar Producto");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnModificar);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 20));
        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);
    }

    private JTextField agregarCampo(JPanel panel, String texto) {
        JLabel label = new JLabel(texto);
        JTextField campo = new JTextField();
        panel.add(label);
        panel.add(campo);
        return campo;
    }

    public void agregarListenerModificar(ActionListener listener) {
        btnModificar.addActionListener(listener);
    }

    // Getters para los campos
    public String getCodigo() { return campoCodigo.getText(); }
    public String getNombre() { return campoNombre.getText(); }
    public String getDescripcion() { return campoDescripcion.getText(); }
    public String getIngrediente() { return campoIngrediente.getText(); }
    public String getLaboratorio() { return campoLaboratorio.getText(); }
    public String getContenido() { return campoContenido.getText(); }
    public String getReceta() { return campoReceta.getText(); }
    public String getCategoria() { return campoCategoria.getText(); }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
