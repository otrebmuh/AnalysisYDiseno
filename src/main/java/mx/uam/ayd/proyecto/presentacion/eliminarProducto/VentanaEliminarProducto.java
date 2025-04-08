package mx.uam.ayd.proyecto.presentacion.eliminarProducto;

import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

/**
 * Ventana para seleccionar y eliminar un producto
 */
@Component
public class VentanaEliminarProducto extends JFrame {

    private JComboBox<String> comboProductos;
    private JButton botonEliminar;
    private JButton botonCancelar;
    private DefaultComboBoxModel<String> modeloCombo;

    public VentanaEliminarProducto() {
        setTitle("Eliminar Producto");
        setSize(400, 200);
        setLocationRelativeTo(null); // Centra la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        // Panel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = new JLabel("Selecciona un producto para eliminar", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titulo, BorderLayout.NORTH);

        // ComboBox
        modeloCombo = new DefaultComboBoxModel<>();
        comboProductos = new JComboBox<>(modeloCombo);
        panel.add(comboProductos, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        botonEliminar = new JButton("Eliminar");
        botonCancelar = new JButton("Cancelar");
        panelBotones.add(botonEliminar);
        panelBotones.add(botonCancelar);
        panel.add(panelBotones, BorderLayout.SOUTH);

        getContentPane().add(panel);
    }

    /**
     * Llena el ComboBox con la lista de productos
     */
    public void llenaProductos(List<Producto> productos) {
        modeloCombo.removeAllElements();
        for (Producto p : productos) {
            modeloCombo.addElement(p.getNombre());
        }
    }

    /**
     * Devuelve el nombre del producto seleccionado
     */
    public String getProductoSeleccionado() {
        return (String) comboProductos.getSelectedItem();
    }

    /**
     * Asigna la acción al botón de eliminar
     */
    public void agregarListenerEliminar(ActionListener listener) {
        botonEliminar.addActionListener(listener);
    }

    /**
     * Asigna la acción al botón de cancelar
     */
    public void agregarListenerCancelar(ActionListener listener) {
        botonCancelar.addActionListener(listener);
    }

    /**
     * Muestra un mensaje de confirmación
     */
    public boolean confirmarEliminacion(String nombre) {
        int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro que deseas eliminar el producto \"" + nombre + "\"?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION
        );
        return respuesta == JOptionPane.YES_OPTION;
    }

    /**
     * Muestra mensaje informativo
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
