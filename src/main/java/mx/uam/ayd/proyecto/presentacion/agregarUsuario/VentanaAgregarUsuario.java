package mx.uam.ayd.proyecto.presentacion.agregarUsuario;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.springframework.stereotype.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

import mx.uam.ayd.proyecto.negocio.modelo.Grupo;

@SuppressWarnings("serial")
@Component
public class VentanaAgregarUsuario extends JFrame {

	private JPanel contentPane;
	private ControlAgregarUsuario control;
	private JTextField textFieldNombre;
	private JTextField textFieldApellido;
	private JComboBox <String> comboBoxGrupo;

	/**
	 * Launch the application.
	 *
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAgregarUsuario frame = new VentanaAgregarUsuario();
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
	public VentanaAgregarUsuario() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 292, 266);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(28, 39, 80, 16);
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(28, 84, 61, 16);
		contentPane.add(lblApellido);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(92, 34, 130, 26);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		textFieldApellido = new JTextField();
		textFieldApellido.setBounds(92, 79, 130, 26);
		contentPane.add(textFieldApellido);
		textFieldApellido.setColumns(10);
		
		JButton btnAgregar = new JButton("Agregar");
		
		btnAgregar.setBounds(28, 189, 117, 29);
		contentPane.add(btnAgregar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.termina();
			}
		});
		btnCancelar.setBounds(157, 189, 117, 29);
		contentPane.add(btnCancelar);
		
		JLabel lblGrupo = new JLabel("Grupo");
		lblGrupo.setBounds(28, 127, 61, 16);
		contentPane.add(lblGrupo);


		
		comboBoxGrupo = new JComboBox<>();
		comboBoxGrupo.setBounds(92, 123, 130, 27);
		
		contentPane.add(comboBoxGrupo);
		
		// Listeners
		
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textFieldNombre.getText().equals("") || textFieldApellido.getText().equals("")) {
					muestraDialogoConMensaje("El nombre y el apellido no deben estar vacios");
				} else {
					control.agregaUsuario(textFieldNombre.getText(), textFieldApellido.getText(), (String) comboBoxGrupo.getSelectedItem());
				}
			}
		});
	
		
	}
	
	public void muestra(ControlAgregarUsuario control, List <Grupo> grupos) {
		
		this.control = control;
		
		textFieldNombre.setText("");

		textFieldApellido.setText("");

		DefaultComboBoxModel <String> comboBoxModel = new DefaultComboBoxModel <>();
		
		
		for(Grupo grupo:grupos) {
			comboBoxModel.addElement(grupo.getNombre());
		}
		
		comboBoxGrupo.setModel(comboBoxModel);
		
		setVisible(true);

	}
	
	public void muestraDialogoConMensaje(String mensaje ) {
		JOptionPane.showMessageDialog(this , mensaje);
	}
}
