package mx.uam.ayd.proyecto.presentacion.visualizarSolicitudesAbastecimiento;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.modelo.SolicitudReabastecimiento;

/**
 * Ventana para visualizar las solicitudes de abastecimiento
 * 
 * @author Eduardo Morgado
 */
@Component
public class VentanaVisualizarSolicitudesAbastecimiento extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private ControlVisualizarSolicitudesAbastecimiento control;
    private JTable tablaSolicitudes;
    private DefaultTableModel modelo;
    
    /**
     * Constructor
     */
    public VentanaVisualizarSolicitudesAbastecimiento() {
        // Configuración de la ventana
        setTitle("Solicitudes de abastecimiento");
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
        modelo.addColumn("Fecha");
        modelo.addColumn("Sucursal");
        modelo.addColumn("Acción");
        
        // Crear la tabla
        tablaSolicitudes = new JTable(modelo);
        tablaSolicitudes.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        
        JScrollPane scrollPane = new JScrollPane(tablaSolicitudes);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
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
     * Método para mostrar la ventana y actualizarla con las solicitudes
     * 
     * @param control el controlador
     * @param solicitudes la lista de solicitudes
     */
    public void muestra(ControlVisualizarSolicitudesAbastecimiento control, List<SolicitudReabastecimiento> solicitudes) {
        this.control = control;
        actualizarTabla(solicitudes);
        setVisible(true);
    }
    
    /**
     * Método para actualizar la tabla con la lista de solicitudes
     * 
     * @param solicitudes la lista de solicitudes
     */
    public void actualizarTabla(final List<SolicitudReabastecimiento> solicitudes) {
        // Limpiar la tabla y eliminar listeners previos
        modelo.setRowCount(0);
        for (java.awt.event.MouseListener listener : tablaSolicitudes.getMouseListeners()) {
            if (listener instanceof java.awt.event.MouseAdapter) {
                tablaSolicitudes.removeMouseListener(listener);
            }
        }
        
        // Formato para fechas
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        
        // Llenar con datos
        for (SolicitudReabastecimiento solicitud : solicitudes) {
            String fecha = solicitud.getFecha() != null ? dateFormat.format(solicitud.getFecha()) : "Sin fecha";
            String sucursal = solicitud.getSucursal() != null ? solicitud.getSucursal().getNombre() : "Sin sucursal";
            
            // Agregar fila con los datos
            modelo.addRow(new Object[]{fecha, sucursal, "Revisar"});
        }
        
        // Agregar nuevo MouseListener con referencia a las solicitudes actuales
        tablaSolicitudes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tablaSolicitudes.rowAtPoint(evt.getPoint());
                int columna = tablaSolicitudes.columnAtPoint(evt.getPoint());
                
                if (columna == 2) { // Si se hace clic en la columna "Acción"
                    if (fila >= 0 && fila < solicitudes.size()) {
                        control.revisarSolicitud(solicitudes.get(fila));
                    }
                }
            }
        });
    }
    
    /**
     * Clase para renderizar botones en la tabla
     */
    private class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        private static final long serialVersionUID = 1L;

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
}
