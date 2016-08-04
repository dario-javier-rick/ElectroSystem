package presentacion.ventanas.sc;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import dto.MarcaDTO;
import dto.PrecioPiezaDTO;
import dto.ProveedorDTO;
import java.awt.Font;
import javax.swing.JSeparator;

public class VentanaNuevaSC extends JDialog {

	private static final long serialVersionUID = -1394811355246704888L;

	private JLabel lblProveedor;
	private JTextField txtFecha;
	private JTextField txtUsuario;
	private JTable table;
	private JScrollPane spDatosTabla;
	private JButton btnSolicitar;
	private JButton btnCancelar;
	private JTextField lblPrecioTotal;
	private JComboBox<ProveedorDTO> cboProveedores;
	private JSpinner tfCantidad;
	private JComboBox<PrecioPiezaDTO> cbPiezas;
	private JComboBox<MarcaDTO> cbMarca;
	private JButton btnAgregarPiezas;
	private JLabel lblPieza;
	private JLabel lblCantidadTotal;

	private JButton btnEnviar;
	private JTextField txtProveedor;
	private JButton btnImprimir;

	private JButton btnModificar;
	private JSeparator separator;
	private JSeparator separator_1;

	public VentanaNuevaSC(JFrame padre) {
		super(padre, true);
		setTitle("Nueva Solicitud de Compra");
		setBounds(100, 100, 700, 544);
		setResizable(false);
		getContentPane().setLayout(null);

		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(10, 15, 46, 14);
		getContentPane().add(lblFecha);

		setTxtFecha(new JTextField());
		getTxtFecha().setEditable(false);
		getTxtFecha().setBounds(66, 8, 71, 28);
		getContentPane().add(getTxtFecha());
		getTxtFecha().setColumns(10);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha = new Date();
		txtFecha.setText(sdf.format(fecha));

		JLabel lblUsuarioLogueado = new JLabel("Usuario logueado:");
		lblUsuarioLogueado.setBounds(449, 18, 121, 14);
		getContentPane().add(lblUsuarioLogueado);

		setTxtUsuario(new JTextField());
		getTxtUsuario().setEditable(false);
		getTxtUsuario().setBounds(568, 11, 117, 28);
		getContentPane().add(getTxtUsuario());
		getTxtUsuario().setColumns(10);

		setLblProveedor(new JLabel("Proveedor"));
		getLblProveedor().setBounds(10, 74, 63, 14);
		getContentPane().add(getLblProveedor());

		JTable table2 = new JTable();
		table2.getTableHeader().setReorderingAllowed(false) ;
		setTable(table2);

		setSpDatosTabla(new JScrollPane());
		getSpDatosTabla().setBounds(10, 205, 675, 171);
		getContentPane().add(getSpDatosTabla());
		getSpDatosTabla().setViewportView(getTable());

		setBtnSolicitar(new JButton("Solicitar"));
		getBtnSolicitar().setBounds(497, 476, 89, 28);
		getContentPane().add(getBtnSolicitar());

		setBtnCancelar(new JButton("Cancelar"));
		getBtnCancelar().setBounds(596, 476, 89, 28);
		getContentPane().add(getBtnCancelar());

		JLabel lblItems = new JLabel("Piezas a solicitar");
		lblItems.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblItems.setBounds(10, 180, 160, 14);
		getContentPane().add(lblItems);

		JLabel lblTotal = new JLabel("Total: $ ", SwingConstants.RIGHT);
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotal.setBounds(555, 387, 57, 14);
		getContentPane().add(lblTotal);

		setLblPrecioTotal(new JTextField("", SwingConstants.LEFT));
		getLblPrecioTotal().setBounds(614, 387, 71, 14);
		getContentPane().add(getLblPrecioTotal());

		setCboProveedores(new JComboBox<ProveedorDTO>());
		getCboProveedores().setBounds(86, 67, 175, 28);
		getContentPane().add(getCboProveedores());

		lblPieza = new JLabel("Pieza:");
		lblPieza.setBounds(12, 124, 40, 14);
		getContentPane().add(lblPieza);

		cbMarca = new JComboBox<MarcaDTO>();
		cbMarca.setBounds(56, 117, 152, 28);
		getContentPane().add(cbMarca);

		cbPiezas = new JComboBox<PrecioPiezaDTO>();
		cbPiezas.setBounds(218, 117, 142, 28);
		getContentPane().add(cbPiezas);

		SpinnerModel sm = new SpinnerNumberModel(1, 1, 100, 1);
		tfCantidad = new JSpinner(sm);
		tfCantidad.setBounds(370, 117, 89, 28);
		getContentPane().add(tfCantidad);

		btnAgregarPiezas = new JButton("Agregar pieza");
		btnAgregarPiezas.setBounds(510, 117, 175, 28);
		getContentPane().add(btnAgregarPiezas);

		JLabel lblCantItems = new JLabel("");
		lblCantItems.setBounds(55, 117, 63, 14);
		getContentPane().add(lblCantItems);

		btnEnviar = new JButton("Enviar por mail");
		btnEnviar.setBounds(10, 476, 142, 28);
		getContentPane().add(btnEnviar);

		txtProveedor = new JTextField();
		txtProveedor.setBounds(86, 67, 175, 28);
		getContentPane().add(txtProveedor);
		txtProveedor.setColumns(10);
		txtProveedor.setVisible(false);
		txtProveedor.setEditable(false);

		JLabel lblCantidadTotalDe = new JLabel("Cantidad de piezas: ", SwingConstants.RIGHT);
		lblCantidadTotalDe.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCantidadTotalDe.setBounds(362, 387, 136, 14);
		getContentPane().add(lblCantidadTotalDe);

		lblCantidadTotal = new JLabel("", SwingConstants.LEFT);
		lblCantidadTotal.setBounds(499, 387, 46, 14);
		getContentPane().add(lblCantidadTotal);

		btnImprimir = new JButton("Imprimir");
		btnImprimir.setBounds(161, 476, 142, 28);
		getContentPane().add(btnImprimir);

		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(596, 437, 89, 28);
		btnModificar.setVisible(false);
		getContentPane().add(btnModificar);

		separator = new JSeparator();
		separator.setBounds(10, 106, 674, 2);
		getContentPane().add(separator);

		separator_1 = new JSeparator();
		separator_1.setBounds(11, 54, 674, 2);
		getContentPane().add(separator_1);

	}

	public JButton getBtnImprimir() {
		return btnImprimir;
	}

	public JButton getBtnAgregarPiezas() {
		return btnAgregarPiezas;
	}

	public JSpinner getTfCantidad() {
		return tfCantidad;
	}

	public int getCantidad() {
		return (int) tfCantidad.getValue();
	}

	public JComboBox<PrecioPiezaDTO> getCbPiezas() {
		return cbPiezas;
	}

	public JComboBox<MarcaDTO> getCbMarca() {
		return cbMarca;
	}

	public JComboBox<ProveedorDTO> getCboProveedores() {
		return cboProveedores;
	}

	void setCboProveedores(JComboBox<ProveedorDTO> cboProveedores) {
		this.cboProveedores = cboProveedores;
	}

	public JLabel getLblProveedor() {
		return lblProveedor;
	}

	private void setLblProveedor(JLabel lblProveedor) {
		this.lblProveedor = lblProveedor;
		lblProveedor.setFont(new Font("Tahoma", Font.BOLD, 11));
	}

	public JTextField getTxtFecha() {
		return txtFecha;
	}

	private void setTxtFecha(JTextField txtFecha) {
		this.txtFecha = txtFecha;
	}

	public JTextField getTxtUsuario() {
		return txtUsuario;
	}

	private void setTxtUsuario(JTextField txtUsuario) {
		this.txtUsuario = txtUsuario;
	}

	public JTable getTable() {
		return table;
	}

	private void setTable(JTable table) {
		this.table = table;
	}

	public JScrollPane getSpDatosTabla() {
		return spDatosTabla;
	}

	private void setSpDatosTabla(JScrollPane spDatosTabla) {
		this.spDatosTabla = spDatosTabla;
	}

	public JButton getBtnSolicitar() {
		return btnSolicitar;
	}

	private void setBtnSolicitar(JButton btnSolicitar) {
		this.btnSolicitar = btnSolicitar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	private void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public JTextField getLblPrecioTotal() {
		return lblPrecioTotal;
	}

	private void setLblPrecioTotal(JTextField jTextField) {
		this.lblPrecioTotal = jTextField;
		lblPrecioTotal.setBorder(null);
		lblPrecioTotal.setEditable(false);
		lblPrecioTotal.setBackground(UIManager.getColor("Label.background"));
	}

	public void setLblPrecioTotal(String lbPrecioTotal) {
		this.lblPrecioTotal.setText(lbPrecioTotal);
	}

	public void setLblPrecioTotal(float f) {
		this.lblPrecioTotal.setText(f + "");
	}

	public JButton getBtnEnviar() {
		return btnEnviar;
	}

	public JTextField getTxtProveedor() {
		return txtProveedor;
	}

	public void setTxtProveedor(String txtProveedor) {
		this.txtProveedor.setText(txtProveedor);
	}

	public JLabel getLblPieza() {
		return lblPieza;
	}

	public void setLabel(JLabel label) {
		this.lblPieza = label;
	}

	public String getLblCantidadTotal() {
		return lblCantidadTotal.toString();
	}

	public void setLblCantidadTotal(int lblCantidadTotal) {
		this.lblCantidadTotal.setText(String.valueOf(lblCantidadTotal));
	}

	public JButton getBtnModificar() {
		return btnModificar;
	}

}
