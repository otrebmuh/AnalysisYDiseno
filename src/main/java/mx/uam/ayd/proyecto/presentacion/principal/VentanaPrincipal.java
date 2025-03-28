package mx.uam.ayd.proyecto.presentacion.principal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.springframework.stereotype.Component;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
@Component
public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	
	private ControlPrincipal control;

	/**
	 * Launch the application.
	 *
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMiAplicacin = new JLabel("Mi Aplicación");
		lblMiAplicacin.setBounds(5, 5, 440, 16);
		contentPane.add(lblMiAplicacin);
		
		JButton btnAgregarUsuario = new JButton("Agregar usuario");
		btnAgregarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.agregarUsuario();
			}
		});
		btnAgregarUsuario.setBounds(15, 33, 178, 29);
		contentPane.add(btnAgregarUsuario);
		
		JButton btnListarUsuarios = new JButton("Listar usuarios");
		btnListarUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.listarUsuarios();
			}
		});
		btnListarUsuarios.setBounds(15, 88, 178, 29);
		contentPane.add(btnListarUsuarios);

		JButton btnRealizarVenta = new JButton("Realizar Venta");
		btnRealizarVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.realizarVenta();
			}
		});
		btnRealizarVenta.setBounds(15, 143, 178, 29);
		contentPane.add(btnRealizarVenta);
	}
	
	public void muestra(ControlPrincipal control) {
		
		this.control = control;
		
		setVisible(true);
	}
}
