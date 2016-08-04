package presentacion.controlador.fleteros;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dto.UsuarioDTO;
import dto.VehiculoDTO;
import modelo.Modelo;
import presentacion.controlador.button.column.ButtonColumn;
import presentacion.ventanas.fleteros.VentanaBuscadorVehiculos;
import presentacion.ventanas.fleteros.VentanaNuevoVehiculo;

public class ControladorBuscadorVehiculo implements ActionListener {

	private VentanaBuscadorVehiculos ventana;
	private Modelo modelo;
	private List<VehiculoDTO> vehiculos;

	private UsuarioDTO logueado;

	public ControladorBuscadorVehiculo(VentanaBuscadorVehiculos ventana, Modelo modelo, List<VehiculoDTO> vehiculos, UsuarioDTO usuario) {

		this.ventana = ventana;
		this.ventana.setLocationRelativeTo(null);

		this.setModelo(modelo);
		this.vehiculos = vehiculos;
		this.logueado = usuario;

		iniciar();
	}

	private void iniciar() {

		this.ventana.getBtnBuscar().addActionListener(this);
		this.ventana.getBtnListo().addActionListener(this);

		this.ventana.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.ventana.getBtnBuscar()) {

			String selected = this.ventana.getComboBox().getSelectedItem().toString();
			String datos = this.ventana.getTxtDatos().getText().toString();

			if (selected.equals("Patente")) {
				llenarTabla(filtrarPatenteSegun(datos));
			} else if (selected.equals("Marca")) {
				llenarTabla(filtrarMarcaSegun(datos));
			} else if (selected.equals("Modelo"))
				llenarTabla(filtrarModeloSegun(datos));
			else
				JOptionPane.showMessageDialog(null, "Seleccione un criterio para realizar la búsqueda.");

			this.ventana.mostrarTabla();
		}

		else if (e.getSource() == this.ventana.getBtnListo()) {

			if (this.ventana.getTable().getSelectedRowCount() == 0) {
				this.ventana.mostrarLblSeleccionar();

			} else {

				String patente = (String) this.ventana.getTable().getValueAt(this.ventana.getTable().getSelectedRow(), 0);
				traerDatos(patente);

				this.ventana.dispose();
			}
		}
	}

	public void traerDatos(String patente) {
		try {
			List<VehiculoDTO> obtenerVehiculos = this.modelo.obtenerVehiculos();
			for (int i = 0; i < obtenerVehiculos.size(); i++) {
				if (obtenerVehiculos.get(i).getPatente().equals(patente)) {
					this.ventana.setVehiculoElegido(obtenerVehiculos.get(i));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void llenarTabla(List<VehiculoDTO> vehiculos) {

		String[] columns = { "Patente", "Marca", "Modelo", "", "", "" };

		DefaultTableModel dtm = new DefaultTableModel(null, columns) {

			private static final long serialVersionUID = -71151758354627082L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 3 || column == 4 || column == 5)
					return true;
				else
					return false;
			}
		};
		dtm.setColumnIdentifiers(columns);

		JTable table = this.ventana.getTable();

		for (VehiculoDTO v : vehiculos) {
			Object[] fila = { v.getPatente(), v.getMarca(), v.getModelo(), "VER", "EDITAR", "BORRAR" };
			dtm.addRow(fila);
		}
		table.setModel(dtm);

		new ButtonColumn(this.ventana.getTable(), verVehiculo(), 3);
		new ButtonColumn(this.ventana.getTable(), editarVehiculo(), 4);
		new ButtonColumn(this.ventana.getTable(), borrarVehiculo(), 5);

	}

	private Action verVehiculo() {
		Action ver = new AbstractAction() {

			private static final long serialVersionUID = -2967408984009042143L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				try {
					String patente = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);

					VehiculoDTO vehiculo = (VehiculoDTO) buscarVehiculo(patente);

					VentanaNuevoVehiculo ventanaVehiculo = new VentanaNuevoVehiculo(ventana);
					new ControladorVentanaNuevoVehiculo(ventanaVehiculo, modelo, vehiculo, false);

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		};
		return ver;
	}

	private Action editarVehiculo() {
		Action editar = new AbstractAction() {

			private static final long serialVersionUID = 8707259785320962456L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				try {
					String patente = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);

					VehiculoDTO vehiculo = (VehiculoDTO) buscarVehiculo(patente);

					VentanaNuevoVehiculo ventanaVehiculo = new VentanaNuevoVehiculo(ventana);
					new ControladorVentanaNuevoVehiculo(ventanaVehiculo, modelo, vehiculo, true);
					// llenar tabla

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		};
		return editar;
	}

	private Action borrarVehiculo() {
		Action borrar = new AbstractAction() {

			private static final long serialVersionUID = -6111685473044885379L;

			@Override
			public void actionPerformed(ActionEvent a) {

				JTable table = (JTable) a.getSource();
				int modelRow = Integer.valueOf(a.getActionCommand());

				try {
					int rta = JOptionPane.showConfirmDialog(ventana, "¿Está seguro que desea eliminar el vehículo?", null, JOptionPane.YES_NO_OPTION);

					if (rta == JOptionPane.YES_OPTION) {
						String patente = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);

						modelo.eliminarVehiculo(buscarVehiculo(patente), logueado);
						((DefaultTableModel) table.getModel()).removeRow(modelRow);
					}

					// llenar tabla
				} catch (Exception exc) {

					exc.printStackTrace();
				}
			}
		};
		return borrar;
	}

	public VehiculoDTO buscarVehiculo(String patente) {
		VehiculoDTO resultado = null;
		for (int i = 0; i < vehiculos.size(); i++) {
			if (vehiculos.get(i).getPatente() == patente) {
				resultado = vehiculos.get(i);
			}
		}
		return resultado;
	}

	public List<VehiculoDTO> filtrarPatenteSegun(String patente) {
		List<VehiculoDTO> resultado = new ArrayList<VehiculoDTO>();
		for (int i = 0; i < vehiculos.size(); i++) {
			if (vehiculos.get(i).getPatente().toLowerCase().contains(patente.toLowerCase())) {
				resultado.add(vehiculos.get(i));
			}
		}
		if (resultado.size() == 0) {
			JOptionPane.showMessageDialog(ventana, "No existe nigún vehículo con la patente ingresada.", "", 0);
		}
		return resultado;
	}

	public List<VehiculoDTO> filtrarMarcaSegun(String marca) {
		List<VehiculoDTO> resultado = new ArrayList<VehiculoDTO>();
		for (int i = 0; i < vehiculos.size(); i++) {
			if (vehiculos.get(i).getMarca().toLowerCase().contains(marca.toLowerCase())) {
				resultado.add(vehiculos.get(i));
			}
		}
		if (resultado.size() == 0) {
			JOptionPane.showMessageDialog(ventana, "No existe nigún vehículo con la marca ingresada.", "", 0);
		}
		return resultado;
	}

	public List<VehiculoDTO> filtrarModeloSegun(String modelo) {
		List<VehiculoDTO> resultado = new ArrayList<VehiculoDTO>();
		for (int i = 0; i < vehiculos.size(); i++) {
			if (vehiculos.get(i).getModelo().toLowerCase().contains(modelo.toLowerCase())) {
				resultado.add(vehiculos.get(i));
			}
		}
		if (resultado.size() == 0) {
			JOptionPane.showMessageDialog(ventana, "No existe nigún vehículo con el modelo ingresado.", "", 0);
		}
		return resultado;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

}
