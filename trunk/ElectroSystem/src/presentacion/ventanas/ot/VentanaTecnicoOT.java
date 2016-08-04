package presentacion.ventanas.ot;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilCalendarModel;

import dto.EstadoDTO;
import dto.MarcaDTO;
import dto.PiezaDTO;
import helpers.Validador;
import javax.swing.JSeparator;

public class VentanaTecnicoOT extends JDialog {

	private static final long serialVersionUID = 2901241470693372810L;

	private final JPanel contentPanel = new JPanel();

	private JTextField txtDetalle;
	private JTextField txtUsuario;
	private JTextField lblidOT;
	private JTextField txtCliente;
	private JTextField txtElectro;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JButton btnAgregarPieza;
	private JDatePickerImpl dateFechaVencimiento;
	private JComboBox<PiezaDTO> comboPieza;
	private JComboBox<MarcaDTO> comboMarca;
	private JTable table;
	private JLabel lblTotalPresupuesto;
	private JLabel lblElectrodomstico_1;
	private JScrollPane scrollPane;
	private JLabel lblManoDeObra;
	private JTextField tfManoDeObra;
	private JLabel lblEstado;
	private JComboBox<EstadoDTO> cbEstado;
	private JLabel lblFechaExpiraGaranta;
	private JSeparator separator;
	private JSeparator separator_1;
	private JLabel lblPresupuesto;

	public void setLblidOT(int i) {
		this.lblidOT.setText(String.valueOf(i));
	}

	public VentanaTecnicoOT(JFrame padre) {
		super(padre, true);
		initialize();
	}

	private void initialize() {
		setTitle("Presupuesto - Orden Trabajo");
		setBounds(100, 100, 693, 646);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblDetalleDelTrabajo = new JLabel("Detalle del problema");
		lblDetalleDelTrabajo.setBounds(12, 143, 127, 14);
		contentPanel.add(lblDetalleDelTrabajo);

		txtDetalle = new JTextField();
		txtDetalle.setEditable(false);
		txtDetalle.setToolTipText("Ejemplo: reparar el botón de encendido");
		txtDetalle.setBounds(12, 168, 665, 40);
		contentPanel.add(txtDetalle);
		txtDetalle.setColumns(10);

		JLabel lblElectrodomstico = new JLabel("Seleccionar pieza:");
		lblElectrodomstico.setBounds(12, 270, 110, 14);
		contentPanel.add(lblElectrodomstico);

		comboPieza = new JComboBox<PiezaDTO>();
		comboPieza.setBounds(259, 263, 177, 28);
		contentPanel.add(comboPieza);

		comboMarca = new JComboBox<MarcaDTO>();
		comboMarca.setBounds(139, 263, 110, 28);
		contentPanel.add(comboMarca);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha = new Date();

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(489, 578, 89, 28);
		contentPanel.add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(588, 578, 89, 28);
		contentPanel.add(btnCancelar);

		JLabel lblUsuarioLogueado = new JLabel("Usuario logueado:", SwingConstants.RIGHT);
		lblUsuarioLogueado.setBounds(180, 20, 110, 14);
		contentPanel.add(lblUsuarioLogueado);

		txtUsuario = new JTextField();
		txtUsuario.setEditable(false);
		txtUsuario.setBounds(300, 13, 146, 28);
		contentPanel.add(txtUsuario);
		txtUsuario.setColumns(10);

		lblFechaExpiraGaranta = new JLabel("Fecha vencimiento:");
		lblFechaExpiraGaranta.setBounds(12, 441, 127, 23);
		contentPanel.add(lblFechaExpiraGaranta);
		UtilCalendarModel model = new UtilCalendarModel();
		Properties properties = new Properties();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
		dateFechaVencimiento = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		SpringLayout springLayout = (SpringLayout) dateFechaVencimiento.getLayout();
		springLayout.putConstraint(SpringLayout.SOUTH, dateFechaVencimiento.getJFormattedTextField(), 0, SpringLayout.SOUTH, dateFechaVencimiento);
		dateFechaVencimiento.setEnabled(false);
		dateFechaVencimiento.setBounds(119, 441, 119, 32);
		contentPanel.add(dateFechaVencimiento);

		btnAgregarPieza = new JButton("Agregar pieza al presupuesto");
		btnAgregarPieza.setBounds(446, 263, 231, 28);
		contentPanel.add(btnAgregarPieza);

		JLabel lblOT = new JLabel("Orden de trabajo Nº ");
		lblOT.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOT.setBounds(12, 21, 117, 14);
		contentPanel.add(lblOT);

		lblidOT = new JTextField("");
		lblidOT.setEditable(false);
		lblidOT.setHorizontalAlignment(SwingConstants.CENTER);
		lblidOT.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblidOT.setBounds(117, 13, 53, 28);
		contentPanel.add(lblidOT);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 302, 667, 128);
		contentPanel.add(scrollPane);

		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false) ;
		scrollPane.setViewportView(table);

		lblTotalPresupuesto = new JLabel("");
		lblTotalPresupuesto.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalPresupuesto.setBounds(524, 418, 153, 14);
		contentPanel.add(lblTotalPresupuesto);

		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCliente.setBounds(12, 74, 55, 16);
		contentPanel.add(lblCliente);

		txtCliente = new JTextField();
		txtCliente.setEditable(false);
		txtCliente.setBounds(119, 68, 217, 28);
		contentPanel.add(txtCliente);
		txtCliente.setColumns(10);

		lblElectrodomstico_1 = new JLabel("Electrodoméstico");
		lblElectrodomstico_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblElectrodomstico_1.setBounds(12, 113, 110, 16);
		contentPanel.add(lblElectrodomstico_1);

		txtElectro = new JTextField();
		txtElectro.setEditable(false);
		txtElectro.setBounds(119, 107, 558, 28);
		contentPanel.add(txtElectro);
		txtElectro.setColumns(10);

		lblManoDeObra = new JLabel("Mano de obra:");
		lblManoDeObra.setBounds(13, 491, 94, 14);
		contentPanel.add(lblManoDeObra);

		tfManoDeObra = new JTextField();
		tfManoDeObra.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.esNumero(e.getKeyChar()) && e.getKeyChar() != '.' || tfManoDeObra.getText().length() > 19 || !Validador.esCharDeDineroValido(tfManoDeObra.getText(), e.getKeyChar())) {
					e.consume();
				}
			}
		});
		tfManoDeObra.setBounds(99, 484, 71, 28);
		contentPanel.add(tfManoDeObra);
		tfManoDeObra.setColumns(10);

		cbEstado = new JComboBox<EstadoDTO>();
		cbEstado.setBounds(531, 13, 146, 28);
		cbEstado.setVisible(false);
		contentPanel.add(cbEstado);

		lblEstado = new JLabel("Estado", SwingConstants.RIGHT);
		lblEstado.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEstado.setBounds(456, 20, 65, 14);
		lblEstado.setVisible(false);
		contentPanel.add(lblEstado);
		
		separator = new JSeparator();
		separator.setBounds(12, 56, 665, 2);
		contentPanel.add(separator);
		
		separator_1 = new JSeparator();
		separator_1.setBounds(12, 229, 665, 2);
		contentPanel.add(separator_1);
		
		lblPresupuesto = new JLabel("Presupuesto");
		lblPresupuesto.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPresupuesto.setBounds(12, 242, 127, 14);
		contentPanel.add(lblPresupuesto);
		agregarDias();
		agregarMeses();
		agregarYears();

	}

	public JLabel getLblFechaExpiraGaranta() {
		return lblFechaExpiraGaranta;
	}

	public JLabel getLblManoDeObra() {
		return lblManoDeObra;
	}

	public JLabel getLblEstado() {
		return lblEstado;
	}

	public JComboBox<EstadoDTO> getCbEstado() {
		return cbEstado;
	}

	public JTextField getTfManoDeObra() {
		return tfManoDeObra;
	}

	public void agregarDias() {
	}

	public void agregarMeses() {

	}

	public void agregarYears() {
	}

	public JTextField getTxtElectro() {
		return txtElectro;
	}

	public void setTxtElectro(String s) {
		this.txtElectro.setText(s);
	}

	public JTextField getTxtCliente() {
		return txtCliente;
	}

	public void setTxtCliente(String txtCliente) {
		this.txtCliente.setText(txtCliente);
	}

	private class DateLabelFormatter extends AbstractFormatter {

		private static final long serialVersionUID = -6810515591056049112L;
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

	public JComboBox<PiezaDTO> getComboPieza() {
		return comboPieza;
	}

	public JComboBox<MarcaDTO> getComboMarca() {
		return comboMarca;
	}

	public JButton getBtnAgregarPieza() {
		return btnAgregarPieza;
	}

	public JTextField getTxtUsuario() {
		return txtUsuario;
	}

	public void setTxtUsuario(JTextField txtUsuario) {
		this.txtUsuario = txtUsuario;
	}

	public String getTxtDetalle() {
		return txtDetalle.getText();
	}

	public void setTxtDetalle(String txtDetalle) {
		this.txtDetalle.setText(txtDetalle);
	}

//	public JLabel getTxtCostoEnvio() {
//		return txtCostoEnvio;
//	}
//
//	public void setTxtCostoEnvio(String costo) {
//		this.txtCostoEnvio.setText(costo);
//	}
//
//	public JTextField getTxtFecha() {
//		return txtFecha;
//	}
//
//	public void setTxtFecha(JTextField txtFecha) {
//		this.txtFecha = txtFecha;
//	}

	public JButton getBtnCrear() {
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

	public void agregarMarcas(Collection<MarcaDTO> marcas) {
		this.comboMarca.removeAllItems();
		for (MarcaDTO marca : marcas) {
			this.comboMarca.addItem(marca);
		}
	}

	public void agregarPiezas(Collection<PiezaDTO> piezas) {
		this.comboPieza.removeAllItems();
		for (PiezaDTO pieza : piezas) {
			this.comboPieza.addItem(pieza);
		}
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JLabel getLblTotalPresupuesto() {
		return lblTotalPresupuesto;
	}

	public void setLblTotalPresupuesto(String i) {
		this.lblTotalPresupuesto.setText(i);
	}

//	public void setTxtCostoEnvio(JLabel txtCostoEnvio) {
//		this.txtCostoEnvio = txtCostoEnvio;
//	}

	public void setBtnCrear(JButton btnCrear) {
		this.btnAceptar = btnCrear;
	}

	public void setBtnAgregarPieza(JButton btnAgregarPieza) {
		this.btnAgregarPieza = btnAgregarPieza;
	}

	public void setComboPieza(JComboBox<PiezaDTO> comboPieza) {
		this.comboPieza = comboPieza;
	}

	public void setComboMarca(JComboBox<MarcaDTO> comboMarca) {
		this.comboMarca = comboMarca;
	}

	public JDatePickerImpl getDateFechaVencimiento() {
		return dateFechaVencimiento;
	}

	public JTextField getLblidOT() {
		return lblidOT;
	}

	public void setLblidOT(JTextField lblidOT) {
		this.lblidOT = lblidOT;
	}

	public void setDateFechaVencimiento(JDatePickerImpl dateFechaVencimiento) {
		this.dateFechaVencimiento = dateFechaVencimiento;
	}

	public void setLblTotalPresupuesto(JLabel lblTotalPresupuesto) {
		this.lblTotalPresupuesto = lblTotalPresupuesto;
	}

	public void setTxtCliente(JTextField txtCliente) {
		this.txtCliente = txtCliente;
	}

	public void setTxtElectro(JTextField txtElectro) {
		this.txtElectro = txtElectro;
	}
}
