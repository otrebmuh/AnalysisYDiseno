package mx.uam.ayd.proyecto.presentacion.gestionProductos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioProducto;
import mx.uam.ayd.proyecto.presentacion.agregarProducto.ControlAgregarProducto;
import mx.uam.ayd.proyecto.presentacion.eliminarProducto.ControlEliminarProducto;
import mx.uam.ayd.proyecto.presentacion.modificarProducto.ControlModificarProducto;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.util.List;

@SuppressWarnings("serial")
@Component
public class VentanaGestionProductos extends JFrame {
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private DefaultTableModel modeloTabla;

    @Autowired
    private ControlAgregarProducto controlAgregarProducto;
    @Autowired
    private ControlModificarProducto controlModificarProducto;
    @Autowired
    private ControlEliminarProducto controlEliminarProducto;
    @Autowired
    private ServicioProducto servicioProducto;

    public VentanaGestionProductos() {
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Realizar Venta");
        jButton2.setText("Inventario de Farmacias");
        jButton3.setText("Gestión De Usuarios");
        jButton4.setText("Gestión De Promociones");
        jButton5.setText("Gestión de Inventario");
        jButton6.setText("Estadísticas");

        jButton7.setText("Gestión de Productos");
        jButton7.addActionListener(evt -> jButton7ActionPerformed(evt));

        jButton8.setText("Solicitudes de Abastecimiento");
        jLabel1.setText("Gestión de Productos");

        modeloTabla = new DefaultTableModel();
        modeloTabla.setColumnIdentifiers(new Object[]{
            "Código", "Producto", "Descripción", "Ingrediente Activo",
            "Laboratorio", "Contenido", "¿Requiere Receta?", "Precio"
        });
        jTable1.setModel(modeloTabla);
        jScrollPane1.setViewportView(jTable1);

        jButton9.setText("Modificar Producto");
        jButton9.addActionListener(evt -> {
            // Obtener el índice de la fila seleccionada
            int selectedRow = jTable1.getSelectedRow();
        
            if (selectedRow >= 0) {
                // Obtener el código del producto desde la tabla
                String codigo = (String) jTable1.getValueAt(selectedRow, 0);
        
                // Buscar el producto correspondiente en el servicio
                Producto productoSeleccionado = servicioProducto.obtenerPorCodigo(codigo);
        
                // Llamar al controlador con el producto seleccionado
                if (productoSeleccionado != null) {
                    controlModificarProducto.inicia(productoSeleccionado);
                } else {
                    JOptionPane.showMessageDialog(this, "Producto no encontrado.");
                }
        
                // Actualizar la tabla de productos (si es necesario)
                actualizarTablaProductos();
            } else {
                // Si no se seleccionó ningún producto, mostrar un mensaje de advertencia
                JOptionPane.showMessageDialog(this, "Debe seleccionar un producto para modificar.");
            }
        });
        
        
        jButton10.setText("Agregar Nuevo Producto");
        jButton10.addActionListener(evt -> {
            controlAgregarProducto.inicia();
            actualizarTablaProductos();
        });

        jButton11.setText("Eliminar Producto");
        jButton11.addActionListener(evt -> {
            controlEliminarProducto.inicia();
            actualizarTablaProductos();
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(462, 462, 462))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton8, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 942, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addGap(32, 32, 32)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton11)
                    .addComponent(jButton9)
                    .addComponent(jButton10))
                .addGap(17, 17, 17))
        );

        pack();
    }

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
        // Acción del botón de gestión de productos
    }

    private void actualizarTablaProductos() {
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
    }

    public void muestra() {
        actualizarTablaProductos();
        setVisible(true);
    }
}
