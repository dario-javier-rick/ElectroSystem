package presentacion.ventanas.fleteros;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilCalendarModel;

import helpers.Validador;

public class VentanaNuevoFletero extends JDialog {

	private static final long serialVersionUID = -5952523350246162583L;

	private JLabel lblVlidoHasta;
	private JTextField txtRegistro;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtCelular;
	private JTextField txtPatente;

	private JButton btnAceptar;
	private JButton btnCancelar;
	private JButton btnActualizar;
	private JButton btnNuevoVechculo;
	private JButton btnBuscarVehculo;

	private JDatePickerImpl vencimientoRegistro;
	private SpringLayout springLayout;

	/**
	 * Create the dialog.
	 */
	public VentanaNuevoFletero(JFrame padre) {
		
		super(padre, true);
		setBounds(100, 100, 370, 488);
		setResizable(false);
		getContentPane().setLayout(null);

		JLabel lblRegistroDeConducir = new JLabel("Registro de Conducir Nº ");
		lblRegistroDeConducir.setBounds(21, 173, 137, 14);
		getContentPane().add(lblRegistroDeConducir);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(21, 58, 64, 14);
		getContentPane().add(lblNombre);

		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(21, 97, 64, 14);
		getContentPane().add(lblApellido);

		JLabel lblCelular = new JLabel("Celular:");
		lblCelular.setBounds(21, 136, 64, 14);
		getContentPane().add(lblCelular);

		txtRegistro = new JTextField();
		txtRegistro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterTelefonoValido(e.getKeyChar())|| txtRegistro.getText().length()>19)
					e.consume();
			}
		});
		txtRegistro.setBounds(153, 166, 181, 28);
		getContentPane().add(txtRegistro);
		txtRegistro.setColumns(10);

		txtNombre = new JTextField();
		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacio(e.getKeyChar()) || txtNombre.getText().length()>19) {
					e.consume();
				}
			}
		});
		txtNombre.setBounds(86, 51, 248, 28);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);

		txtApellido = new JTextField();
		txtApellido.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacio(e.getKeyChar()) || txtApellido.getText().length()>19) {
					e.consume();
				}
			}
		});
		txtApellido.setBounds(86, 90, 248, 28);
		getContentPane().add(txtApellido);
		txtApellido.setColumns(10);

		txtCelular = new JTextField();
		txtCelular.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterTelefonoValido(e.getKeyChar())|| txtCelular.getText().length()>19)
					e.consume();
			}
		});
		txtCelular.setBounds(86, 129, 248, 28);
		getContentPane().add(txtCelular);
		txtCelular.setColumns(10);

		btnNuevoVechculo = new JButton("Nuevo");
		btnNuevoVechculo.setBounds(171, 346, 75, 23);
		getContentPane().add(btnNuevoVechculo);

		JLabel lblVehculo = new JLabel("Patente:");
		lblVehculo.setBounds(21, 314, 46, 14);
		getContentPane().add(lblVehculo);

		btnBuscarVehculo = new JButton("Buscar");
		btnBuscarVehculo.setBounds(86, 346, 75, 23);
		getContentPane().add(btnBuscarVehculo);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 36, 342, 4);
		getContentPane().add(separator);

		JLabel lblDatosPersonales = new JLabel("Datos Personales");
		lblDatosPersonales.setBounds(10, 11, 124, 14);
		getContentPane().add(lblDatosPersonales);

		JLabel lblDatosDelVehculo = new JLabel("Datos del Vehículo");
		lblDatosDelVehculo.setBounds(10, 265, 124, 14);
		getContentPane().add(lblDatosDelVehculo);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 290, 342, 2);
		getContentPane().add(separator_1);

		txtPatente = new JTextField();
		txtPatente.setEditable(false);
		txtPatente.setBounds(86, 307, 248, 28);
		getContentPane().add(txtPatente);
		txtPatente.setColumns(10);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(268, 425, 84, 23);
		getContentPane().add(btnCancelar);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(177, 425, 84, 23);
		getContentPane().add(btnAceptar);

		lblVlidoHasta = new JLabel("válido hasta");
		lblVlidoHasta.setBounds(72, 211, 75, 14);
		getContentPane().add(lblVlidoHasta);

		UtilCalendarModel model = new UtilCalendarModel();
		Properties properties = new Properties();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);

		vencimientoRegistro = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		springLayout = (SpringLayout) vencimientoRegistro.getLayout();
		vencimientoRegistro.setEnabled(false);
		vencimientoRegistro.setBounds(153, 205, 181, 32);
		getContentPane().add(vencimientoRegistro);

		btnActualizar = new JButton("Actualizar");
		btnActualizar.setBounds(10, 425, 101, 23);
		getContentPane().add(btnActualizar);
		this.btnActualizar.setVisible(false);
	}

	private class DateLabelFormatter extends AbstractFormatter {

		private static final long serialVersionUID = 617345725955120858L;
		
		private String datePattern = "dd/MM/yyyy";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}
			return "";
		}

	}

	public JDatePickerImpl getVencimientoRegistro() {
		return vencimientoRegistro;
	}

	public void setVencimientoRegistro(JDatePickerImpl vencimientoRegistro) {
		this.vencimientoRegistro = vencimientoRegistro;
	}

	public String getTxtRegistro() {
		return txtRegistro.getText();
	}

	public void setTxtRegistro(String txtRegistro) {
		this.txtRegistro.setText(txtRegistro);
	}

	public String getTxtNombre() {
		return txtNombre.getText();
	}

	public void setTxtNombre(String txtNombre) {
		this.txtNombre.setText(txtNombre);
	}

	public String getTxtApellido() {
		return txtApellido.getText();
	}

	public void setTxtApellido(String txtApellido) {
		this.txtApellido.setText(txtApellido);
	}

	public String getTxtCelular() {
		return txtCelular.getText();
	}

	public void setTxtCelular(String txtCelular) {
		this.txtCelular.setText(txtCelular);
	}

	public JTextField getTxtPatente() {
		return txtPatente;
	}

	public void setTxtPatente(String txtPatente) {
		this.txtPatente.setText(txtPatente);
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public JButton getBtnNuevoVechculo() {
		return btnNuevoVechculo;
	}

	public void setBtnNuevoVechculo(JButton btnNuevoVechculo) {
		this.btnNuevoVechculo = btnNuevoVechculo;
	}

	public JButton getBtnBuscarVehculo() {
		return btnBuscarVehculo;
	}

	public void setBtnBuscarVehculo(JButton btnBuscarVehculo) {
		this.btnBuscarVehculo = btnBuscarVehculo;
	}

	public JButton getBtnActualizar() {
		return btnActualizar;
	}

	public void setBtnActualizar(JButton btnActualizar) {
		this.btnActualizar = btnActualizar;
	}

	public void mostrarActualizar() {
		this.btnActualizar.setVisible(true);
		this.btnAceptar.setVisible(false);
	}
	
	public void bloquearCampos() {
		this.txtNombre.setEditable(false);
		this.txtApellido.setEditable(false);
		this.txtCelular.setEditable(false);
		this.txtRegistro.setEditable(false);
		this.txtPatente.setEditable(false);
		this.vencimientoRegistro.setTextEditable(false);
		this.btnCancelar.setText("OK");
		this.getBtnAceptar().setVisible(false);
		this.btnActualizar.setVisible(false);
		this.btnBuscarVehculo.setVisible(false);
		this.btnNuevoVechculo.setVisible(false);
		
		// TODO que traiga la fecha de vecnimiento del registro
		this.vencimientoRegistro.setVisible(false);
		this.lblVlidoHasta.setVisible(false);
		
	}
}
