package presentacion.ventanas.cliente;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dto.LocalidadDTO;
import helpers.Validador;

public class VentanaAltaModifCliente extends JDialog {

	private static final long serialVersionUID = -8955022527394413959L;

	private JPanel contentPane;
	private JTextField tfNombre;
	private JTextField tfTelefono;
	private JTextField tfMail;
	private JButton btnCancelar;
	private JButton btnAlta;
	private JLabel lblApellido;
	private JLabel lblDireccin;
	private JLabel lblLocalidad;
	private JLabel lblProvincia;
	private JTextField tfApellido;
	private JTextField tfDireccion;
	private JTextField tfProvincia;

	private JLabel lblAdvertenciaMail;
	private JButton btnCrearOt;
	private JButton btnNuevaLocalidad;
	private JComboBox<LocalidadDTO> comboLocalidades;
	private JTextField txtLocalidad;

	/**
	 * @wbp.parser.constructor
	 */
	public VentanaAltaModifCliente(JDialog padre) {
		super(padre, true);
		go();
	}

	public VentanaAltaModifCliente(JFrame padre) {
		super(padre, true);
		go();
	}

	private void go() {
		setBounds(100, 100, 488, 428);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(27, 29, 173, 14);
		contentPane.add(lblNombre);

		JLabel lblTelfono = new JLabel("Tel\u00E9fono:");
		lblTelfono.setBounds(27, 238, 173, 14);
		contentPane.add(lblTelfono);

		JLabel lblCorreoElectronico = new JLabel("Correo electr\u00F3nico:");
		lblCorreoElectronico.setBounds(27, 277, 173, 14);
		contentPane.add(lblCorreoElectronico);

		tfNombre = new JTextField();
		tfNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacio(e.getKeyChar()) || tfNombre.getText().length() > 19) {
					e.consume();
				}
			}
		});
		tfNombre.setBounds(156, 22, 292, 28);
		contentPane.add(tfNombre);
		tfNombre.setColumns(10);

		tfTelefono = new JTextField();
		tfTelefono.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterTelefonoValido(e.getKeyChar()) || tfTelefono.getText().length() > 19)
					e.consume();
			}
		});
		tfTelefono.setColumns(10);
		tfTelefono.setBounds(156, 231, 292, 28);
		contentPane.add(tfTelefono);

		tfMail = new JTextField();
		tfMail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterMailValido(e.getKeyChar()) || tfMail.getText().length() > 29)
					e.consume();
			}
		});
		tfMail.setColumns(10);
		tfMail.setBounds(156, 270, 292, 28);
		contentPane.add(tfMail);

		btnAlta = new JButton("Dar de alta");
		btnAlta.setBounds(183, 362, 155, 23);
		contentPane.add(btnAlta);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(348, 362, 100, 23);
		contentPane.add(btnCancelar);

		lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(27, 68, 173, 14);
		contentPane.add(lblApellido);

		lblDireccin = new JLabel("Direcci\u00F3n:");
		lblDireccin.setBounds(27, 107, 173, 14);
		contentPane.add(lblDireccin);

		lblLocalidad = new JLabel("Localidad:");
		lblLocalidad.setBounds(27, 146, 173, 14);
		contentPane.add(lblLocalidad);

		lblProvincia = new JLabel("Provincia:");
		lblProvincia.setBounds(27, 185, 173, 14);
		contentPane.add(lblProvincia);

		tfApellido = new JTextField();
		tfApellido.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacio(e.getKeyChar()) || tfNombre.getText().length() > 19) {
					e.consume();
				}
			}
		});
		tfApellido.setColumns(10);
		tfApellido.setBounds(156, 61, 292, 28);
		contentPane.add(tfApellido);

		tfDireccion = new JTextField();
		tfDireccion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacioNumero(e.getKeyChar()) || tfNombre.getText().length() > 19) {
					e.consume();
				}
			}
		});
		tfDireccion.setColumns(10);
		tfDireccion.setBounds(156, 100, 292, 28);
		contentPane.add(tfDireccion);

		tfProvincia = new JTextField();
		tfProvincia.setEditable(false);
		tfProvincia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacio(e.getKeyChar()) || tfNombre.getText().length() > 19) {
					e.consume();
				}
			}
		});
		tfProvincia.setColumns(10);
		tfProvincia.setBounds(156, 178, 292, 28);
		contentPane.add(tfProvincia);

		lblAdvertenciaMail = new JLabel("Formato de mail inválido.");
		lblAdvertenciaMail.setForeground(Color.RED);
		lblAdvertenciaMail.setBounds(210, 309, 155, 14);
		lblAdvertenciaMail.setVisible(false);
		contentPane.add(lblAdvertenciaMail);

		btnCrearOt = new JButton("Crear OT");
		btnCrearOt.setBounds(10, 362, 104, 23);
		btnCrearOt.setVisible(false);
		contentPane.add(btnCrearOt);

		comboLocalidades = new JComboBox<LocalidadDTO>();
		comboLocalidades.setBounds(156, 139, 154, 28);
		contentPane.add(comboLocalidades);

		btnNuevaLocalidad = new JButton("Nueva Localidad");
		btnNuevaLocalidad.setBounds(320, 138, 128, 29);
		contentPane.add(btnNuevaLocalidad);

		txtLocalidad = new JTextField();
		txtLocalidad.setEditable(false);
		txtLocalidad.setBounds(156, 139, 292, 28);
		contentPane.add(txtLocalidad);
		txtLocalidad.setColumns(10);
		this.txtLocalidad.setVisible(false);
	}

	public JLabel getLblAdvertenciaMail() {
		return lblAdvertenciaMail;
	}

	public JTextField getTfApellido() {
		return tfApellido;
	}

	public JButton getBtnCrearOt() {
		return btnCrearOt;
	}

	public JTextField getTfDireccion() {
		return tfDireccion;
	}

	// public JTextField getTfLocalidad() {
	// return tfLocalidad;
	// }

	public JTextField getTfProvincia() {
		return tfProvincia;
	}

	public JButton getBtnAlta() {
		return btnAlta;
	}

	public JTextField getTfNombre() {
		return tfNombre;
	}

	public JTextField getTfTelefono() {
		return tfTelefono;
	}

	public JTextField getTfMail() {
		return tfMail;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JButton getBtnNuevaLocalidad() {
		return btnNuevaLocalidad;
	}

	public void setBtnNuevaLocalidad(JButton btnNuevaLocalidad) {
		this.btnNuevaLocalidad = btnNuevaLocalidad;
	}

	public JComboBox<LocalidadDTO> getComboLocalidades() {
		return comboLocalidades;
	}

	public void setComboLocalidades(LocalidadDTO localidad) {
		this.comboLocalidades.getModel().setSelectedItem(localidad);
	}

	public JTextField getTxtLocalidad() {
		return txtLocalidad;
	}

	public void setTxtLocalidad(JTextField txtLocalidad) {
		this.txtLocalidad = txtLocalidad;
	}

	public JLabel getLblDireccin() {
		return lblDireccin;
	}

	public void setLblDireccin(String lblDireccin) {
		this.lblDireccin.setText(lblDireccin);
	}
	
	public void setTfProvincia(String tfProvincia) {
		this.tfProvincia.setText(tfProvincia);
	}
}
