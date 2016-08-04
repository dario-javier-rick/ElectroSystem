package presentacion.ventanas.ot;

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

public class VentanaBuscadorOT extends JDialog {

	private static final long serialVersionUID = 9118768838770410703L;

	private JPanel panel;
	private JComboBox<String> cbBuscarPor;
	private JTextField txtDatos;
	private JLabel lblResultados;
	private JTable table;
	private JScrollPane spDatosTabla;
	private JButton btnBuscarOT;
	private JButton btnListo;
	private JLabel lblSeleccionarCliente;
	private int idOTasociada;
	private JButton btnSalir;

	public VentanaBuscadorOT(JFrame padre) {
		super(padre, true);
		setModal(false);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setTitle("Buscar Orden de Trabajo");
		setBounds(100, 100, 540, 334);
		setResizable(false);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 522, 261);
		this.getContentPane().add(panel);
		panel.setLayout(null);

		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false) ;
		table.setBounds(10, 82, 414, 166);

		spDatosTabla = new JScrollPane(table);
		spDatosTabla.setBounds(10, 81, 502, 170);
		panel.add(spDatosTabla);
		spDatosTabla.setVisible(false);

		JLabel lblBsquedaPor = new JLabel("Búsqueda por");
		lblBsquedaPor.setBounds(10, 15, 89, 14);
		panel.add(lblBsquedaPor);

		cbBuscarPor = new JComboBox<String>();
		cbBuscarPor.setBounds(94, 8, 162, 28);
		cbBuscarPor.addItem("ID");
		panel.add(cbBuscarPor);

		txtDatos = new JTextField();
		txtDatos.setBounds(266, 8, 147, 28);
		panel.add(txtDatos);
		txtDatos.setColumns(10);

		lblResultados = new JLabel("Resultado:");
		lblResultados.setBounds(10, 56, 89, 14);
		panel.add(lblResultados);

		btnBuscarOT = new JButton("Buscar");
		btnBuscarOT.setBounds(423, 8, 89, 29);
		panel.add(btnBuscarOT);

		lblSeleccionarCliente = new JLabel("(seleccione una orden de trabajo)");
		lblSeleccionarCliente.setForeground(Color.RED);
		lblSeleccionarCliente.setBounds(83, 56, 341, 14);
		panel.add(lblSeleccionarCliente);
		lblSeleccionarCliente.setVisible(false);

		btnListo = new JButton("Ver");
		btnListo.setBounds(369, 272, 69, 23);
		getContentPane().add(btnListo);

		btnSalir = new JButton("Salir");
		btnSalir.setBounds(448, 272, 74, 23);
		getContentPane().add(btnSalir);

		this.setVisible(true);
	}

	public int getIdOTasociada() {
		return idOTasociada;
	}

	public void setIdOTasociada(int idOTasociada) {
		this.idOTasociada = idOTasociada;
	}

	public void mostrarTabla() {
		this.spDatosTabla.setVisible(true);
	}

	public void mostrarLblSeleccionar() {
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

	public JButton getBtnListo() {
		return btnListo;
	}

	public void setBtnListo(JButton btnListo) {
		this.btnListo = btnListo;
	}

	public JComboBox<String> getCbBuscarPor() {
		return cbBuscarPor;
	}

	public void setCbBuscarPor(JComboBox<String> cbBuscarPor) {
		this.cbBuscarPor = cbBuscarPor;
	}

	public JButton getBtnBuscarOT() {
		return btnBuscarOT;
	}

	public void setBtnBuscarOT(JButton btnBuscarOT) {
		this.btnBuscarOT = btnBuscarOT;
	}

	public JButton getBtnSalir() {
		return btnSalir;
	}

	public void setBtnSalir(JButton btnSalir) {
		this.btnSalir = btnSalir;
	}

}
