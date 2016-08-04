package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dto.ClienteDTO;
import dto.UsuarioDTO;
import helpers.Validador;
import modelo.Modelo;
import presentacion.ventanas.cliente.VentanaAltaModifCliente;
import presentacion.ventanas.cliente.VentanaBuscadorCliente;
import presentacion.vista.VistaPrincipal;

public class ControladorBuscadorCliente implements ActionListener {

	private VistaPrincipal vistaPrincipal;
	private VentanaBuscadorCliente ventana;
	private Modelo modelo;
	private UsuarioDTO logueado;

	private ClienteDTO clienteSeleccionado;
	private List<ClienteDTO> clientes;
	
	public ControladorBuscadorCliente(VentanaBuscadorCliente ventana, Modelo modelo) {
		// dialog
		this.ventana = ventana;
		this.ventana.setLocationRelativeTo(null);
		this.setModelo(modelo);
		iniciar();
	}

	public ControladorBuscadorCliente(VentanaBuscadorCliente ventana, Modelo modelo, VistaPrincipal vistaPrincipal,	UsuarioDTO logueado) {
		// frame
		this.ventana = ventana;
		this.ventana.setLocationRelativeTo(null);
		this.setModelo(modelo);
		this.logueado = logueado;
		this.vistaPrincipal = vistaPrincipal;
		iniciar();
	}

	private void iniciar() {
		this.ventana.getBtnBuscarCliente().addActionListener(this);
		this.ventana.getBtnListo().addActionListener(this);
		this.ventana.mostrarTabla();

		try {
			this.clientes = modelo.obtenerClientes();
			llenarTabla(clientes);
		} catch (Exception e) {
			this.clientes = new ArrayList<ClienteDTO>();
			e.printStackTrace();
		}
		this.ventana.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.ventana.getBtnBuscarCliente()) {
			this.ventana.getLblSeleccionarCliente().setVisible(false);
			String selected = this.ventana.getComboBox().getSelectedItem().toString();
			String datos = this.ventana.getTxtDatos().getText().toString();

			if (selected.equals("Apellido")) {
				llenarTabla(filtrarApellidoSegun(datos));
			} else if (selected.equals("Nombre")) {
				llenarTabla(filtrarNombreSegun(datos));
			} else {
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
				this.clienteSeleccionado = (ClienteDTO) this.ventana.getTable()
						.getValueAt(this.ventana.getTable().getSelectedRow(), 1);
				this.ventana.dispose();

				if (this.ventana.getParent().getClass().getName().equals("presentacion.vista.VistaPrincipal")) {
					
					VentanaAltaModifCliente ventanaCliente = new VentanaAltaModifCliente(this.vistaPrincipal);
					ControladorCliente controladorCliente = new ControladorCliente(ventanaCliente, modelo,
							this.getClienteSeleccionado(), false, false, logueado);

				}

			}
		}
	}

	@SuppressWarnings("serial")
	public void llenarTabla(List<ClienteDTO> clientes) {

		String[] columns = { "ID", "Cliente" };

		DefaultTableModel dtm = new DefaultTableModel(null, columns) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		dtm.setColumnIdentifiers(columns);

		JTable table = this.ventana.getTable();

		for (ClienteDTO c : clientes) {
			Object[] fila = { c.getIdCliente(), c };
			dtm.addRow(fila);
		}
		table.setModel(dtm);
	}

	public List<ClienteDTO> filtrarIdSegun(int idCliente) {
		List<ClienteDTO> resultado = new ArrayList<ClienteDTO>();
		for (int i = 0; i < clientes.size(); i++) {
			if (clientes.get(i).getIdCliente() == idCliente) {
				resultado.add(clientes.get(i));
			}
		}
		if (resultado.size() == 0) {
			JOptionPane.showMessageDialog(ventana, "No existe nigún cliente con ese ID.", "", 0);
		}
		return resultado;
	}

	public List<ClienteDTO> filtrarApellidoSegun(String apellido) {
		List<ClienteDTO> resultado = new ArrayList<ClienteDTO>();
		for (int i = 0; i < clientes.size(); i++) {
			if (clientes.get(i).getApellido().toLowerCase().contains(apellido.toLowerCase())) {
				resultado.add(clientes.get(i));
			}
		}
		if (resultado.size() == 0) {
			JOptionPane.showMessageDialog(ventana, "No existe nigún cliente con el apellido ingresado.", "", 0);
		}
		return resultado;
	}

	public List<ClienteDTO> filtrarNombreSegun(String nombre) {
		List<ClienteDTO> resultado = new ArrayList<ClienteDTO>();
		for (int i = 0; i < clientes.size(); i++) {
			if (clientes.get(i).getNombre().toLowerCase().contains(nombre.toLowerCase())) {
				resultado.add(clientes.get(i));
			}
		}
		if (resultado.size() == 0) {
			JOptionPane.showMessageDialog(ventana, "No existe nigún cliente con el nombre ingresado.", "", 0);
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

	public ClienteDTO getClienteSeleccionado() {
		return clienteSeleccionado;
	}

	public void setClienteSeleccionado(ClienteDTO clienteSeleccionado) {
		this.clienteSeleccionado = clienteSeleccionado;
	}

}
