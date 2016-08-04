package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dto.OrdenDTO;
import dto.UsuarioDTO;
import helpers.Validador;
import modelo.Modelo;
import presentacion.ventanas.ot.VentanaAdminOT;
import presentacion.ventanas.ot.VentanaBuscadorOT;
import presentacion.vista.VistaPrincipal;

public class ControladorBuscadorOT implements ActionListener {

	private VentanaBuscadorOT ventana;
	private Modelo modelo;

	private List<OrdenDTO> ordenes;
	private UsuarioDTO usuarioLogueado;
	private VistaPrincipal ventanaPrincipal;

	public ControladorBuscadorOT(VentanaBuscadorOT ventana, Modelo modelo, UsuarioDTO usrLogueado,
			VistaPrincipal vistaPrincipal) {
		this.ventana = ventana;
		this.ventana.setLocationRelativeTo(null);

		this.setModelo(modelo);
		this.usuarioLogueado = usrLogueado;
		this.ventanaPrincipal = vistaPrincipal;
		iniciar();
	}

	private void iniciar() {

		this.ventana.getBtnBuscarOT().addActionListener(this);
		this.ventana.getBtnListo().addActionListener(this);
		this.ventana.getBtnSalir().addActionListener(this);
		this.ventana.mostrarTabla();

		try {
			this.ordenes = this.modelo.obtenerOrdenes();
			llenarTabla(ordenes);
		} catch (Exception e) {
			this.ordenes = new ArrayList<OrdenDTO>();
			e.printStackTrace();
		}
		this.ventana.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.ventana.getBtnBuscarOT()) {
			this.ventana.getLblSeleccionarCliente().setVisible(false);
			String selected = this.ventana.getComboBox().getSelectedItem().toString();
			String datos = this.ventana.getTxtDatos().getText().toString();

			if (selected.equals("ID")) {

				if (soloNumeros(datos))
					llenarTabla(filtrarIdSegun(Integer.parseInt(datos.isEmpty() ? "0" : datos)));
				else
					JOptionPane.showMessageDialog(null,
							"Para realizar una búsqueda por ID, por favor, ingrese solo números.");
			}
		}

		else if (e.getSource() == this.ventana.getBtnListo()) {
			if (this.ventana.getTable().getSelectedRowCount() == 0) {
				this.ventana.mostrarLblSeleccionar();
			} else {
				VentanaAdminOT ventanaOT = new VentanaAdminOT(ventanaPrincipal);
				OrdenDTO ordenAcargar = (OrdenDTO) this.ventana.getTable().getModel()
						.getValueAt(this.ventana.getTable().getSelectedRow(), 0);
				new ControladorAdminOT(ventanaOT, modelo, ordenAcargar, this.usuarioLogueado, true);
			}

		} else if (e.getSource() == this.ventana.getBtnSalir()) {
			this.ventana.dispose();
		}
	}

	@SuppressWarnings("serial")
	public void llenarTabla(List<OrdenDTO> ordenes) {

		String[] columns = { "ID", "Electrodoméstico" };

		DefaultTableModel dtm = new DefaultTableModel(null, columns) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		dtm.setColumnIdentifiers(columns);

		JTable table = this.ventana.getTable();

		for (OrdenDTO o : ordenes) {
			Object[] fila = { o, o.getElectrodomestico() };
			dtm.addRow(fila);
		}

		table.setModel(dtm);
	}

	public List<OrdenDTO> filtrarIdSegun(int idot) {
		List<OrdenDTO> resultado = new ArrayList<OrdenDTO>();
		for (int i = 0; i < ordenes.size(); i++) {
			if (ordenes.get(i).getIdOT() == idot) {
				resultado.add(ordenes.get(i));
			}
		}
		if (resultado.size() == 0) {
			JOptionPane.showMessageDialog(ventana, "No existe ninguna orden de trabajo con el número ingresado.", "",
					0);
		}
		return resultado;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public boolean soloNumeros(String s) {

		for (int i = 0; i < s.length(); i++) {
			if (!Validador.esNumero(s.charAt(i)))
				return false;
		}
		return true;
	}

}
