package presentacion.ventanas.cliente;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class VentanaBuscadorCliente extends JDialog {

	private static final long serialVersionUID = 9118768838770410703L;

	private JPanel panel;
	private JComboBox<String> cbBuscarPor;
	private JTextField txtDatos;
	private JLabel lblResultados;
	private JTable table;
	private JScrollPane spDatosTabla;
	private JButton btnBuscarCliente;
	private JButton btnListo;
	private JLabel lblSeleccionarCliente;
	private int idClienteAsociado;

	public VentanaBuscadorCliente(JFrame padre) {
		super(padre, true);
		setModal(false);
		setTitle("Buscar cliente");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		setBounds(100, 100, 514, 340);
		setResizable(false);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 498, 261);
		this.getContentPane().add(panel);
		panel.setLayout(null);

		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false) ;
		table.setBounds(10, 82, 414, 166);

		spDatosTabla = new JScrollPane(table);
		spDatosTabla.setBounds(10, 81, 478, 170);
		panel.add(spDatosTabla);
		spDatosTabla.setVisible(false);

		JLabel lblBsquedaPor = new JLabel("Búsqueda por");
		lblBsquedaPor.setBounds(10, 15, 89, 14);
		panel.add(lblBsquedaPor);

		cbBuscarPor = new JComboBox<String>();
		cbBuscarPor.setBounds(94, 8, 138, 28);
		cbBuscarPor.addItem("Apellido");
		cbBuscarPor.addItem("Nombre");
		cbBuscarPor.addItem("ID");
		panel.add(cbBuscarPor);

		txtDatos = new JTextField();
		txtDatos.setBounds(242, 8, 147, 28);
		panel.add(txtDatos);
		txtDatos.setColumns(10);

		lblResultados = new JLabel("Resultados:");
		lblResultados.setBounds(10, 56, 89, 14);
		panel.add(lblResultados);

		btnBuscarCliente = new JButton("Buscar");
		btnBuscarCliente.setBounds(399, 7, 89, 28);
		panel.add(btnBuscarCliente);

		lblSeleccionarCliente = new JLabel("(seleccionar un cliente)");
		lblSeleccionarCliente.setForeground(Color.RED);
		lblSeleccionarCliente.setBounds(83, 56, 147, 14);
		panel.add(lblSeleccionarCliente);
		lblSeleccionarCliente.setVisible(false);

		btnListo = new JButton("Listo");
		btnListo.setBounds(409, 272, 89, 23);
		getContentPane().add(btnListo);

		setVisible(true);
	}

	/**
	 * @wbp.parser.constructor
	 */
	public VentanaBuscadorCliente(JDialog padre) {
		super(padre, true);
		setModal(true);
		
		setTitle("Buscar cliente");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 514, 340);
		setResizable(false);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 498, 269);
		this.getContentPane().add(panel);
		panel.setLayout(null);

		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false) ;
		table.setBounds(10, 82, 414, 166);

		spDatosTabla = new JScrollPane(table);
		spDatosTabla.setBounds(10, 81, 478, 177);
		panel.add(spDatosTabla);
		spDatosTabla.setVisible(false);

		JLabel lblBsquedaPor = new JLabel("Búsqueda por");
		lblBsquedaPor.setBounds(10, 15, 89, 14);
		panel.add(lblBsquedaPor);

		cbBuscarPor = new JComboBox<String>();
		cbBuscarPor.setBounds(94, 8, 147, 28);
		cbBuscarPor.addItem("Apellido");
		cbBuscarPor.addItem("Nombre");
		cbBuscarPor.addItem("ID");
		panel.add(cbBuscarPor);

		txtDatos = new JTextField();
		txtDatos.setBounds(251, 8, 138, 28);
		panel.add(txtDatos);
		txtDatos.setColumns(10);

		lblResultados = new JLabel("Resultados:");
		lblResultados.setBounds(10, 56, 89, 14);
		panel.add(lblResultados);

		btnBuscarCliente = new JButton("Buscar");
		btnBuscarCliente.setBounds(399, 7, 89, 28);
		panel.add(btnBuscarCliente);

		lblSeleccionarCliente = new JLabel("(seleccionar un cliente)");
		lblSeleccionarCliente.setForeground(Color.RED);
		lblSeleccionarCliente.setBounds(83, 56, 147, 14);
		panel.add(lblSeleccionarCliente);
		lblSeleccionarCliente.setVisible(false);

		btnListo = new JButton("Listo");
		btnListo.setBounds(409, 280, 89, 23);
		getContentPane().add(btnListo);
	}
	
	public int getClienteAsociado() {
		return idClienteAsociado;
	}

	public void setIDClienteAsociado(int idClienteAsociado) {
		this.idClienteAsociado = idClienteAsociado;;
	}

	public void mostrarTabla() {
		this.spDatosTabla.setVisible(true);
	}
	
	public void mostrarLblSeleccionar () {
		this.lblSeleccionarCliente.setVisible(true);
	}

	public JLabel getLblSeleccionarCliente() {
		return lblSeleccionarCliente;
	}

	public JTextField getTxtDatos() {
		return txtDatos;
	}

	public void setTxtDatos(JTextField txtDatos) {
		this.txtDatos = txtDatos;
	}

	public JComboBox<String> getComboBox() {
		return cbBuscarPor;
	}

	public void setComboBox(JComboBox<String> comboBox) {
		this.cbBuscarPor = comboBox;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JScrollPane getSpDatosTabla() {
		return spDatosTabla;
	}

	public void setSpDatosTabla(JScrollPane spDatosTabla) {
		this.spDatosTabla = spDatosTabla;
	}

	public JButton getBtnBuscarCliente() {
		return btnBuscarCliente;
	}

	public void setBtnBuscarCliente(JButton btnBuscar) {
		this.btnBuscarCliente = btnBuscar;
	}
	
	public JButton getBtnListo() {
		return btnListo;
	}

	public void setBtnListo(JButton btnListo) {
		this.btnListo = btnListo;
	}
}
