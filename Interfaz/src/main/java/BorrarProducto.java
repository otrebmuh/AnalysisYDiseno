import javax.swing.*;
import java.awt.*;

public class BorrarProducto extends JDialog {
    
    public BorrarProducto(JFrame parent) {
        super(parent, "Eliminar Producto", true); // Ventana modal
        setSize(400, 200);
        setLocationRelativeTo(parent);
        initUI();
    }

    private void initUI() {
        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Mensaje de advertencia
        JLabel mensaje = new JLabel(
            "<html><center>¿Está seguro que desea eliminar este producto?<br>Esta acción no se puede deshacer.</center></html>", 
            SwingConstants.CENTER
        );
        mensaje.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(mensaje, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        
        JButton btnSi = new JButton("Sí");
        btnSi.addActionListener(e -> {
            // Aquí iría la lógica para borrar el producto
            dispose(); // Cierra la ventana
        });
        
        JButton btnNo = new JButton("No");
        btnNo.addActionListener(e -> dispose());
        
        buttonPanel.add(btnSi);
        buttonPanel.add(btnNo);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }
}