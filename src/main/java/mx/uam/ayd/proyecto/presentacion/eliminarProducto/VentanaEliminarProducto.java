package mx.uam.ayd.proyecto.presentacion.eliminarProducto;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import mx.uam.ayd.proyecto.negocio.modelo.Producto;

@Component
public class VentanaEliminarProducto extends JFrame {

    private JComboBox<String> comboProductos;
    private JButton btnEliminar;
    private JButton btnCancelar;

    public VentanaEliminarProducto() {
        setTitle("Eliminar Producto");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Selecciona un producto para eliminar:", SwingConstants.CENTER);
        add(lblTitulo, BorderLayout.NORTH);

        comboProductos = new JComboBox<>();
        add(comboProductos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnEliminar = new JButton("Eliminar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    public void llenaProductos(List<Producto> productos) {
        comboProductos.removeAllItems();
        for (Producto p : productos) {
            comboProductos.addItem(p.getNombre());
        }
    }

    public String getProductoSeleccionado() {
        return (String) comboProductos.getSelectedItem();
    }

    public void agregarListenerEliminar(ActionListener listener) {
        btnEliminar.addActionListener(listener);
    }

    public void agregarListenerCancelar(ActionListener listener) {
        btnCancelar.addActionListener(listener);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public boolean confirmarEliminacion(String nombreProducto) {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas eliminar el producto \"" + nombreProducto + "\"?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
        return opcion == JOptionPane.YES_OPTION;
    }
}