package presentacion.ventanas.ot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dto.ElectrodomesticoDTO;
import dto.EstadoDTO;
import dto.MarcaDTO;

public class VentanaAdminOT extends JDialog {

	private static final long serialVersionUID = -1370947097460141486L;

	private final JPanel contentPanel = new JPanel();
	private JTextField txtDomicilio;
	private JTextField txtFecha;
	private JTextField txtCliente;
	private JButton btnCrear;
	private JButton btnCancelar;
	private JTextField txtUsuario;
	private ButtonGroup group;
	private JRadioButton rbEnvio;
	private JRadioButton rbRetiro;

	private JButton btnAgregarElectrodomstico;
	private JComboBox<String> comboNombre;
	private JComboBox<MarcaDTO> comboMarca;
	private JComboBox<ElectrodomesticoDTO> comboModelo;
	private JButton btnBuscar;
	private JLabel lblDomicilio;
	private JButton btnAgregar;
	private JTextField txtElectrodomestico;
	private JLabel lblUsuarioLogueado;
	private JComboBox<EstadoDTO> cbEstado;
	private JLabel lblEstado;
	private JTextArea txtDetalle;
	private JButton btnEnviarPorMail;
	private JButton btnCambiarDomicilio;
	private JLabel lblCostoDeEnvio;
	private JLabel lblCaducado;
	private JButton btnGenerarTicket;
	private JButton btnImprimirOrden;
	private JLabel lblRestantes;
	private JTextField txtTelefono;
	private JLabel lblEmail;
	private JTextField txtMail;
	private JLabel lblCostoDeEnvo;
	private JTextField txtCostoEnvio;

	/**
	 * @wbp.parser.constructor
	 */
	public VentanaAdminOT(JDialog padre) {
		super(padre, true);
		go();
	}

	public VentanaAdminOT(JFrame padre) {
		super(padre, true);
		go();
	}

	private void go() {
		setTitle("Nueva Orden de Trabajo");
		setBounds(100, 100, 700, 646);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblDetalleDelTrabajo = new JLabel("Detalle de la falla");
		lblDetalleDelTrabajo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDetalleDelTrabajo.setBounds(10, 273, 263, 14);
		contentPanel.add(lblDetalleDelTrabajo);

		JLabel lblElectrodomstico = new JLabel("Electrodoméstico");
		lblElectrodomstico.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblElectrodomstico.setBounds(10, 193, 117, 14);
		contentPanel.add(lblElectrodomstico);

		comboNombre = new JComboBox<String>();
		comboNombre.setBounds(242, 186, 304, 28);
		contentPanel.add(comboNombre);

		comboMarca = new JComboBox<MarcaDTO>();
		comboMarca.setBounds(123, 186, 109, 28);
		contentPanel.add(comboMarca);

		comboModelo = new JComboBox<ElectrodomesticoDTO>();
		comboModelo.setBounds(556, 186, 128, 28);
		contentPanel.add(comboModelo);

		JLabel lblCliente = new JLabel("Datos del cliente");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCliente.setBounds(10, 78, 109, 14);
		contentPanel.add(lblCliente);

		lblDomicilio = new JLabel("Domicilio:");
		lblDomicilio.setEnabled(false);
		lblDomicilio.setBounds(64, 482, 63, 14);
		contentPanel.add(lblDomicilio);

		txtDomicilio = new JTextField();
		txtDomicilio.setEditable(false);
		txtDomicilio.setVisible(true);
		txtDomicilio.setBounds(138, 475, 429, 28);
		contentPanel.add(txtDomicilio);
		txtDomicilio.setColumns(10);

		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(10, 18, 46, 14);
		contentPanel.add(lblFecha);

		txtFecha = new JTextField();
		txtFecha.setEditable(false);
		txtFecha.setBounds(63, 11, 71, 28);
		contentPanel.add(txtFecha);
		txtFecha.setColumns(10);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha = new Date();
		txtFecha.setText(sdf.format(fecha));

		txtCliente = new JTextField();
		txtCliente.setEditable(false);
		txtCliente.setBounds(123, 110, 124, 28);
		contentPanel.add(txtCliente);
		txtCliente.setColumns(10);

		btnBuscar = new JButton("Buscar cliente");
		btnBuscar.setBounds(122, 71, 125, 28);
		contentPanel.add(btnBuscar);

		btnCrear = new JButton("Aceptar");
		btnCrear.setBounds(496, 578, 89, 28);
		contentPanel.add(btnCrear);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(595, 578, 89, 28);
		contentPanel.add(btnCancelar);

		lblUsuarioLogueado = new JLabel("Usuario logueado:", SwingConstants.RIGHT);
		lblUsuarioLogueado.setBounds(144, 18, 162, 14);
		contentPanel.add(lblUsuarioLogueado);

		txtUsuario = new JTextField();
		txtUsuario.setEditable(false);
		txtUsuario.setBounds(316, 11, 145, 28);
		contentPanel.add(txtUsuario);
		txtUsuario.setColumns(10);

		rbEnvio = new JRadioButton("Env\u00EDo a domicilio");
		rbEnvio.setBounds(249, 439, 117, 23);
		rbEnvio.setEnabled(true);
		contentPanel.add(rbEnvio);

		rbRetiro = new JRadioButton("Retiro en local");
		rbRetiro.setBounds(138, 439, 109, 23);
		rbRetiro.setEnabled(true);
		contentPanel.add(rbRetiro);

		group = new ButtonGroup();
		group.add(rbEnvio);
		group.add(rbRetiro);

		btnAgregarElectrodomstico = new JButton("Agregar electrodom\u00E9stico");
		btnAgregarElectrodomstico.setBounds(123, 225, 187, 28);
		contentPanel.add(btnAgregarElectrodomstico);

		btnAgregar = new JButton("Agregar cliente");
		btnAgregar.setBounds(257, 71, 128, 28);
		contentPanel.add(btnAgregar);

		txtElectrodomestico = new JTextField();
		txtElectrodomestico.setEditable(false);
		txtElectrodomestico.setBounds(123, 189, 561, 23);
		contentPanel.add(txtElectrodomestico);
		txtElectrodomestico.setColumns(10);

		cbEstado = new JComboBox<EstadoDTO>();
		cbEstado.setBounds(539, 11, 145, 28);
		contentPanel.add(cbEstado);
		cbEstado.setVisible(false);

		lblEstado = new JLabel("Estado", SwingConstants.RIGHT);
		lblEstado.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEstado.setBounds(471, 18, 53, 14);
		contentPanel.add(lblEstado);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 298, 672, 101);
		contentPanel.add(scrollPane);

		txtDetalle = new JTextArea();
		scrollPane.setViewportView(txtDetalle);
		txtDetalle.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtDetalle.getText().length() > 239) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				lblRestantes.setText("Caracteres restantes: "
						+ (((240 - txtDetalle.getText().length()) >= 0) ? 240 - txtDetalle.getText().length() : 0)
						+ "  ");
			}
		});

		btnEnviarPorMail = new JButton("Enviar por mail");
		btnEnviarPorMail.setIcon(null);
		btnEnviarPorMail.setBounds(10, 578, 151, 28);
		contentPanel.add(btnEnviarPorMail);

		btnCambiarDomicilio = new JButton("Cambiar domicilio de entrega");
		btnCambiarDomicilio.setBounds(139, 514, 227, 28);
		contentPanel.add(btnCambiarDomicilio);

		lblCostoDeEnvio = new JLabel("");
		lblCostoDeEnvio.setBounds(254, 338, 180, 14);
		contentPanel.add(lblCostoDeEnvio);
		lblCostoDeEnvio.setVisible(true);

		lblCaducado = new JLabel(
				"<HTML>El vencimiento del presupuesto ha caducado.<br>Por favor reingrese la orden para ser<br>nuevamente presupuestada.</HTML>");
		lblCaducado.setForeground(Color.RED);
		lblCaducado.setBounds(457, 508, 227, 59);
		lblCaducado.setVisible(false);
		contentPanel.add(lblCaducado);

		btnGenerarTicket = new JButton("Generar ticket");
		btnGenerarTicket.setBounds(332, 578, 151, 28);
		btnGenerarTicket.setVisible(false);
		contentPanel.add(btnGenerarTicket);

		btnImprimirOrden = new JButton("Imprimir Orden");
		btnImprimirOrden.setBounds(171, 578, 151, 28);
		btnImprimirOrden.setVisible(false);
		contentPanel.add(btnImprimirOrden);

		lblRestantes = new JLabel("");
		lblRestantes.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRestantes.setFont(new Font("Tahoma", Font.ITALIC, 9));
		lblRestantes.setBounds(514, 273, 168, 14);
		contentPanel.add(lblRestantes);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 421, 674, 2);
		contentPanel.add(separator_1);

		JLabel lblOpcionesDeEnvo = new JLabel("Opciones de envío");
		lblOpcionesDeEnvo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblOpcionesDeEnvo.setBounds(10, 443, 117, 14);
		contentPanel.add(lblOpcionesDeEnvo);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 50, 674, 2);
		contentPanel.add(separator_2);

		JLabel lblApellidoYNombre = new JLabel("Apellido y Nombre:");
		lblApellidoYNombre.setBounds(10, 117, 109, 14);
		contentPanel.add(lblApellidoYNombre);

		JLabel lblTelfono = new JLabel("Teléfono:");
		lblTelfono.setBounds(257, 117, 78, 14);
		contentPanel.add(lblTelfono);

		txtTelefono = new JTextField();
		txtTelefono.setEditable(false);
		txtTelefono.setBounds(316, 110, 109, 28);
		contentPanel.add(txtTelefono);
		txtTelefono.setColumns(10);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(10, 161, 674, 2);
		contentPanel.add(separator_3);

		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(435, 117, 78, 14);
		contentPanel.add(lblEmail);

		txtMail = new JTextField();
		txtMail.setEditable(false);
		txtMail.setBounds(471, 110, 213, 28);
		contentPanel.add(txtMail);
		txtMail.setColumns(10);

		lblCostoDeEnvo = new JLabel("Costo de envío: $", SwingConstants.RIGHT);
		lblCostoDeEnvo.setEnabled(false);
		lblCostoDeEnvo.setBounds(372, 443, 111, 14);
		contentPanel.add(lblCostoDeEnvo);

		txtCostoEnvio = new JTextField();
		txtCostoEnvio.setEnabled(false);
		txtCostoEnvio.setEditable(false);
		txtCostoEnvio.setBounds(496, 436, 71, 28);
		contentPanel.add(txtCostoEnvio);
		txtCostoEnvio.setColumns(10);
		this.btnEnviarPorMail.setVisible(false);

		lblEstado.setVisible(false);
		this.txtElectrodomestico.setVisible(false);

	}

	public JLabel getLblCostoDeEnvo() {
		return lblCostoDeEnvo;
	}

	public void setLblCostoDeEnvo(JLabel lblCostoDeEnvo) {
		this.lblCostoDeEnvo = lblCostoDeEnvo;
	}

	public JTextField getTxtCostoEnvio() {
		return txtCostoEnvio;
	}

	public void setTxtCostoEnvio(JTextField txtCostoEnvio) {
		this.txtCostoEnvio = txtCostoEnvio;
	}

	public JButton getBtnImprimirOrden() {
		return btnImprimirOrden;
	}

	public JButton getBtnGenerarTicket() {
		return btnGenerarTicket;
	}

	public JLabel getLblCaducado() {
		return lblCaducado;
	}

	public JLabel getLblCostoDeEnvio() {
		return lblCostoDeEnvio;
	}

	public JComboBox<EstadoDTO> getCbEstado() {
		return cbEstado;
	}

	public JLabel getLblEstado() {
		return lblEstado;
	}

	public JTextField getTxtElectrodomestico() {
		return txtElectrodomestico;
	}

	public void setTxtElectrodomestico(String txtElectrodomestico) {
		this.txtElectrodomestico.setText(txtElectrodomestico);
	}

	public JButton getBtnAgregar() {
		return btnAgregar;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(JButton btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	public JComboBox<String> getComboNombre() {
		return comboNombre;
	}

	public JComboBox<MarcaDTO> getComboMarca() {
		return comboMarca;
	}

	public JComboBox<ElectrodomesticoDTO> getComboModelo() {
		return comboModelo;
	}

	public JButton getBtnAgregarElectrodomestico() {
		return btnAgregarElectrodomstico;
	}

	public JRadioButton getButtonEnvio() {
		return rbEnvio;
	}

	public void setButtonEnvio(boolean b) {
		this.rbEnvio.setSelected(b);
		;
	}

	public JRadioButton getButtonRetiro() {
		return rbRetiro;
	}

	public void setButtonRetiro(boolean b) {
		this.rbRetiro.setSelected(b);
	}

	public boolean isDelivery() {
		if (this.rbEnvio.isSelected())
			return true;
		return false;
	}

	public JTextField getTxtUsuario() {
		return txtUsuario;
	}

	public void setTxtUsuario(String txtUsuario) {
		this.txtUsuario.setText(txtUsuario);
	}

	public String getTxtDetalle() {
		return txtDetalle.getText();
	}

	public JTextArea getDetalle() {
		return txtDetalle;
	}

	public void setTxtDetalle(String t) {
		this.txtDetalle.setText(t);
	}

	public JTextField getTxtDomicilio() {
		return txtDomicilio;
	}

	public void setTxtDomicilio(String domicilio) {
		this.txtDomicilio.setText(domicilio);
	}

	public JTextField getTxtFecha() {
		return txtFecha;
	}

	public void setTxtFecha(JTextField txtFecha) {
		this.txtFecha = txtFecha;
	}

	public JTextField getTxtCliente() {
		return txtCliente;
	}

	public void setTxtCliente(String txtCliente) {
		this.txtCliente.setText(txtCliente);
	}

	public void mostrarOpcionesEnvio() {
		this.txtDomicilio.setEnabled(true);
		this.lblDomicilio.setEnabled(true);
		this.txtCostoEnvio.setEnabled(true);
		this.lblCostoDeEnvo.setEnabled(true);
	}

	public void esconderOpcionesEnvio() {
		this.txtDomicilio.setEnabled(false);
		this.txtDomicilio.setEditable(false);
		this.lblDomicilio.setEnabled(false);
		this.lblCostoDeEnvo.setEnabled(false);
		this.txtCostoEnvio.setEnabled(false);
	}

	public JButton getBtnCrear() {
		return btnCrear;
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnCrear = btnAceptar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public JRadioButton getRbEnvio() {
		return rbEnvio;
	}

	public JRadioButton getRbRetiro() {
		return rbRetiro;
	}

	public JLabel getLblDomicilio() {
		return lblDomicilio;
	}

	public void setLblDomicilio(JLabel lblDomicilio) {
		this.lblDomicilio = lblDomicilio;
	}

	public JLabel getLblUsuarioLogueado() {
		return lblUsuarioLogueado;
	}

	public void setLblUsuarioLogueado(String lblUsuarioLogueado) {
		this.lblUsuarioLogueado.setText(lblUsuarioLogueado);
	}

	public void esconderBotones() {
		getBtnBuscar().setVisible(false);
		getBtnAgregar().setVisible(false);
		getBtnAgregarElectrodomestico().setVisible(false);
		getComboMarca().setVisible(false);
		getComboModelo().setVisible(false);
		getComboNombre().setVisible(false);
		setLblUsuarioLogueado("Usuario que dió de alta: ");
		getDetalle().setEditable(false);
		getTxtElectrodomestico().setEditable(false);
		getTxtElectrodomestico().setVisible(true);
	}

	public JButton getBtnEnviarPorMail() {
		return btnEnviarPorMail;
	}

	public void setBtnEnviarPorMail(JButton btnEnviarPorMail) {
		this.btnEnviarPorMail = btnEnviarPorMail;
	}

	public JButton getBtnCambiarDomicilio() {
		return btnCambiarDomicilio;
	}

	public void setBtnCambiarDomicilio(JButton btnCambiarDomicilio) {
		this.btnCambiarDomicilio = btnCambiarDomicilio;
	}

	public JTextField getTxtTelefono() {
		return txtTelefono;
	}

	public void setTxtTelefono(String txtTelefono) {
		this.txtTelefono.setText(txtTelefono);
	}

	public JTextField getTxtMail() {
		return txtMail;
	}

	public void setTxtMail(String txtMail) {
		this.txtMail.setText(txtMail);
	}

}
