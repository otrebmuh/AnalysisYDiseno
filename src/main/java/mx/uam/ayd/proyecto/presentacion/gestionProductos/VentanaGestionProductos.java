package mx.uam.ayd.proyecto.presentacion.gestionProductos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mx.uam.ayd.proyecto.negocio.ServicioProducto;
import mx.uam.ayd.proyecto.presentacion.agregarProducto.ControlAgregarProducto;
import mx.uam.ayd.proyecto.presentacion.eliminarProducto.ControlEliminarProducto;
import mx.uam.ayd.proyecto.presentacion.modificarProducto.ControlModificarProducto;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

@SuppressWarnings("serial")
@Component
public class VentanaGestionProductos extends JFrame {
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;

    @Autowired
    private ControlAgregarProducto controlAgregarProducto;
    @Autowired
    private ControlModificarProducto controlModificarProducto;
    @Autowired
    private ControlEliminarProducto controlEliminarProducto;
    @Autowired
    private ServicioProducto servicioProducto;

    public VentanaGestionProductos() {
        initComponents();
    }

    private void initComponents() {
        jLabel1 = new JLabel();
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();
        btnAgregar = new JButton();
        btnModificar = new JButton();
        btnEliminar = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Productos");
        
        jLabel1.setText("Gestión de Productos");

        modeloTabla = new DefaultTableModel();
        modeloTabla.setColumnIdentifiers(new Object[]{
            "Código", "Producto", "Descripción", "Ingrediente Activo",
            "Laboratorio", "Contenido", "¿Requiere Receta?", "Precio"
        });
        jTable1.setModel(modeloTabla);
        jScrollPane1.setViewportView(jTable1);

        btnModificar.setText("Modificar Producto");
        btnModificar.addActionListener(evt -> modificarProducto());

        btnAgregar.setText("Agregar Nuevo Producto");
        btnAgregar.addActionListener(evt -> {
            controlAgregarProducto.inicia();
            actualizarTablaProductos();
        });

        btnEliminar.setText("Eliminar Producto");
        btnEliminar.addActionListener(evt -> controlEliminarProducto.inicia(this));


        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAgregar)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar))
                .addContainerGap())
        );

        pack();
    }

    private void modificarProducto() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow >= 0) {
            String codigo = (String) jTable1.getValueAt(selectedRow, 0);
            Producto productoSeleccionado = servicioProducto.obtenerPorCodigo(codigo);
            if (productoSeleccionado != null) {
                controlModificarProducto.inicia(productoSeleccionado);
                actualizarTablaProductos();
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un producto para modificar.");
        }
    }

    public void actualizarTablaProductos() {
        SwingUtilities.invokeLater(() -> {
            modeloTabla.setRowCount(0);
            List<Producto> productos = servicioProducto.getAll();
            for (Producto producto : productos) {
                modeloTabla.addRow(new Object[]{
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getIngrediente().getNombre(),
                    producto.getLaboratorio().getNombre(),
                    producto.getContenido(),
                    producto.getReceta() != null ? (producto.getReceta() ? "Sí" : "No") : "No",
                    producto.getPrecio() != null ? producto.getPrecio() : 0.0
                });
            }
        });
    }

    public void muestra() {
        actualizarTablaProductos();
        setVisible(true);
    }
}