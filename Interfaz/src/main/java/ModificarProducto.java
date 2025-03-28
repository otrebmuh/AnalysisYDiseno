import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ModificarProducto extends JDialog {
    
    public ModificarProducto(JFrame parent) {
        super(parent, "Modificar Producto", true);
        setSize(500, 550);
        setLocationRelativeTo(parent);
        initUI();
    }

    private void initUI() {
        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 1. Título superior
        JLabel titulo = new JLabel("Modificar Producto", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titulo, BorderLayout.NORTH);

        // 2. Instrucción
        JLabel instruccion = new JLabel("Por favor llene el siguiente formulario para editar el producto", SwingConstants.CENTER);
        mainPanel.add(instruccion, BorderLayout.CENTER);

        // 3. Panel de formulario con GridLayout
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        
        // Campos del formulario
        addFormField(formPanel, "Código:", new JTextField());
        addFormField(formPanel, "Nombre:", new JTextField());
        addFormField(formPanel, "Descripción:", new JTextField());
        addFormField(formPanel, "Ingrediente Activo:", new JTextField());
        addFormField(formPanel, "Laboratorio:", new JTextField());
        addFormField(formPanel, "Contenido:", new JTextField());
        addFormField(formPanel, "¿Requiere Receta?:", new JTextField());
        addFormField(formPanel, "Categoría:", new JTextField());

        // 4. Panel para el botón (centrado)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnModificar = new JButton("Modificar Producto");
        btnModificar.addActionListener(this::mostrarConfirmacion);
        buttonPanel.add(btnModificar);

        // Contenedor intermedio para el formulario y el botón
        JPanel contentPanel = new JPanel(new BorderLayout(10, 20));
        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.SOUTH);

        // Configurar el diálogo
        getContentPane().add(mainPanel);
    }

    private void addFormField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        panel.add(label);
        panel.add(textField);
    }

    private void mostrarConfirmacion(ActionEvent e) {
        // Mostrar diálogo de confirmación
        int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro que deseas modificar este producto?",
            "Confirmar Modificación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        // Si el usuario elige "Sí", cerrar la ventana
        if (respuesta == JOptionPane.YES_OPTION) {
            dispose();
        }
        // Si elige "No", no hacer nada (se queda en el formulario)
    }
}