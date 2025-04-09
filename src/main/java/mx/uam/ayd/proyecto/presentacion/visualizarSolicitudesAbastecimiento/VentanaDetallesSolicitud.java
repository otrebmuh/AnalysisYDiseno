package mx.uam.ayd.proyecto.presentacion.visualizarSolicitudesAbastecimiento;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.modelo.DetallesSolicitud;
import mx.uam.ayd.proyecto.negocio.modelo.SolicitudReabastecimiento;

/**
 * Ventana para visualizar los detalles de una solicitud de abastecimiento
 * 
 * @author Cascade
 */
@Component
public class VentanaDetallesSolicitud extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private ControlDetallesSolicitud control;
    private JTable tablaDetalles;
    private DefaultTableModel modelo;
    
    /**
     * Constructor
     */
    public VentanaDetallesSolicitud() {
        // Configuración de la ventana
        setTitle("Detalle de solicitud de abastecimiento");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Panel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Crear el modelo de tabla
        modelo = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable
            }
        };
        
        // Definir columnas
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad en Almacén");
        modelo.addColumn("Cantidad Solicitada");
        
        // Crear la tabla
        tablaDetalles = new JTable(modelo);
        
        JScrollPane scrollPane = new JScrollPane(tablaDetalles);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Información de la solicitud
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        JLabel lblTitulo = new JLabel("Solicitudes de abastecimiento");
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(18.0f));
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
        panelInfo.add(lblTitulo);
        panelInfo.add(Box.createVerticalStrut(10));
        
        panel.add(panelInfo, BorderLayout.NORTH);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        
        JButton btnAtender = new JButton("Marcar como Atendida");
        btnAtender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog(
                    VentanaDetallesSolicitud.this, 
                    "¿Está seguro de marcar esta solicitud como atendida?", 
                    "Confirmar", 
                    JOptionPane.YES_NO_OPTION);
                
                if (confirmacion == JOptionPane.YES_OPTION) {
                    control.marcarComoAtendida();
                }
            }
        });
        panelBotones.add(btnAtender);
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.termina();
            }
        });
        panelBotones.add(btnCerrar);
        
        panel.add(panelBotones, BorderLayout.SOUTH);
        add(panel);
    }
    
    /**
     * Método para mostrar la ventana y actualizarla con los detalles de la solicitud
     * 
     * @param control el controlador
     * @param solicitud la solicitud cuyos detalles se mostrarán
     * @param cantidadesEnAlmacen lista de cantidades en almacén para cada producto
     */
    public void muestra(ControlDetallesSolicitud control, SolicitudReabastecimiento solicitud, List<Integer> cantidadesEnAlmacen) {
        this.control = control;
        actualizarTabla(solicitud, cantidadesEnAlmacen);
        
        // Actualizar título con información de la solicitud
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        String fechaFormateada = solicitud.getFecha() != null ? dateFormat.format(solicitud.getFecha()) : "Sin fecha";
        String sucursal = solicitud.getSucursal() != null ? solicitud.getSucursal().getNombre() : "Sin sucursal";
        
        setTitle("Detalle de solicitud - " + sucursal + " (" + fechaFormateada + ")");
        
        setVisible(true);
    }
    
    /**
     * Método para actualizar la tabla con los detalles de la solicitud
     * 
     * @param solicitud la solicitud cuyos detalles se mostrarán
     * @param cantidadesEnAlmacen lista de cantidades en almacén para cada producto
     */
    private void actualizarTabla(SolicitudReabastecimiento solicitud, List<Integer> cantidadesEnAlmacen) {
        // Limpiar la tabla
        modelo.setRowCount(0);
        
        // Llenar con datos
        List<DetallesSolicitud> detalles = solicitud.getDetalles();
        for (int i = 0; i < detalles.size(); i++) {
            DetallesSolicitud detalle = detalles.get(i);
            String nombreProducto = detalle.getProducto().getNombre();
            Integer cantidadSolicitada = detalle.getCantidad();
            
            // Obtener la cantidad en almacén correspondiente
            Integer cantidadEnAlmacen = (i < cantidadesEnAlmacen.size()) ? cantidadesEnAlmacen.get(i) : 0;
            
            // Agregar fila con los datos
            modelo.addRow(new Object[]{nombreProducto, cantidadEnAlmacen, cantidadSolicitada});
        }
    }
}
