package presentacion.ventanas.ot;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import dto.LocalidadDTO;
import helpers.Validador;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VentanaNuevoDomicilioDeEntrega extends JDialog {

	private static final long serialVersionUID = -1755472475375925057L;

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JComboBox<LocalidadDTO> comboLocalidades;
	private JButton btnNuevaLocalidad;
	private JButton okButton;
	private JButton cancelButton;

	public VentanaNuevoDomicilioDeEntrega(JDialog padre) {
		super (padre, true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setTitle("Nuevo domicilio de entrega");
		setBounds(100, 100, 419, 195);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblDomicilioDeEntrega = new JLabel("Domicilio:");
		lblDomicilioDeEntrega.setBounds(21, 31, 84, 14);
		contentPanel.add(lblDomicilioDeEntrega);
		
		JLabel label = new JLabel("Localidad:");
		label.setBounds(21, 71, 73, 14);
		contentPanel.add(label);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacioNumero(e.getKeyChar()) || textField.getText().length() > 19)
					e.consume();
			}
		});
		textField.setColumns(10);
		textField.setBounds(104, 24, 287, 28);
		contentPanel.add(textField);
		
		comboLocalidades = new JComboBox<LocalidadDTO>();
		comboLocalidades.setBounds(104, 64, 149, 28);
		contentPanel.add(comboLocalidades);
		
		btnNuevaLocalidad = new JButton("Nueva Localidad");
		btnNuevaLocalidad.setBounds(263, 63, 128, 28);
		contentPanel.add(btnNuevaLocalidad);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Aceptar");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public JButton getBtnNuevaLocalidad() {
		return btnNuevaLocalidad;
	}

	public JTextField getTextField() {
		return textField;
	}

	public JComboBox<LocalidadDTO> getComboLocalidades() {
		return comboLocalidades;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}
}
