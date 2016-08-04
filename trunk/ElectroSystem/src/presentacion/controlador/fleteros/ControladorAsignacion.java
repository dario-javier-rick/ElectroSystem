package presentacion.controlador.fleteros;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dto.DetallesEnvioDTO;
import dto.EnvioDTO;
import dto.FleteroDTO;
import dto.OrdenDTO;
import modelo.Modelo;
import presentacion.controlador.button.column.ButtonColumn;
import presentacion.ventanas.fleteros.VentanaAsignacion;

public class ControladorAsignacion implements ActionListener {

	private VentanaAsignacion ventana;
	private Modelo modelo;

	private List<OrdenDTO> ordenes_en_tabla_disponibles;
	private List<OrdenDTO> ordenes_en_tabla_asignadas;

	private FleteroDTO fletero;
	private EnvioDTO nuevo_envio;

	private DefaultTableModel dtm1;
	private DefaultTableModel dtm2;

	public ControladorAsignacion(VentanaAsignacion ventana, Modelo modelo, FleteroDTO fletero, List<OrdenDTO> ordenes) {

		this.ventana = ventana;
		this.setModelo(modelo);
		this.fletero = fletero;

		this.ordenes_en_tabla_disponibles = ordenes;
		this.ordenes_en_tabla_asignadas = new LinkedList<OrdenDTO>();

		nuevo_envio = new EnvioDTO(0, fletero, null, new LinkedList<DetallesEnvioDTO>());

		iniciar();
	}

	private void iniciar() {
		this.ventana.setLocationRelativeTo(null);
//		this.ventana.setLblEnvosAsignadosA("Nuevos envíos asignados a " + fletero);

		llenarTablaOrigen();
		llenarTablaDestino();

		this.ventana.getOkButton().addActionListener(this);
		this.ventana.getCancelButton().addActionListener(this);

		this.ventana.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.ventana.getCancelButton()) {
			this.ventana.dispose();
		}

		else if (e.getSource() == this.ventana.getOkButton()) {

			if (camposCompletos() && fechaValida()) {
				asignarEnvios();
				this.ventana.dispose();
			}
		}
	}

	public boolean camposCompletos() {
		if (this.ventana.getFechaEnvio() == null || this.ordenes_en_tabla_asignadas.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Complete todos los campos.");
			return false;
		}
		return true;
	}

	public boolean fechaValida() {
		Calendar fechaElegida = (Calendar) ventana.getFechaEnvio().getModel().getValue();
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);

		if (fechaElegida.before(yesterday)) {
			JOptionPane.showMessageDialog(null, "La fecha indicada no es válida.");
			return false;

		} else
			return true;
	}

	public void asignarEnvios() {

		try {

			boolean existe = false;
			EnvioDTO envioDB = null;

			this.nuevo_envio.setFechaEnvio((Calendar) this.ventana.getFechaEnvio().getModel().getValue());

			for (int i = 0; i < this.modelo.obtenerEnvios().size(); i++) {
				EnvioDTO actual = this.modelo.obtenerEnvios().get(i);

				if (actual.getFletero().getIdFletero() == nuevo_envio.getFletero().getIdFletero() && calendar2str(actual.getFechaEnvio()).equals(calendar2str(nuevo_envio.getFechaEnvio()))) {
					existe = true;
					envioDB = actual;
				}
			}

			if (!existe) {
				nuevo_envio.setFechaEnvio((Calendar) this.ventana.getFechaEnvio().getModel().getValue());
				this.modelo.agregarEnvio(nuevo_envio);

				for (int i = 0; i < this.modelo.obtenerEnvios().size(); i++) {
					EnvioDTO actual = this.modelo.obtenerEnvios().get(i);
					if (actual.getFletero().getIdFletero() == nuevo_envio.getFletero().getIdFletero() && calendar2str(actual.getFechaEnvio()).equals(calendar2str(nuevo_envio.getFechaEnvio()))) {
						for (int j = 0; j < nuevo_envio.getDetalles().size(); j++) {
							this.modelo.agregarDetalle(nuevo_envio.getDetalles().get(j), actual);

							try {
								this.modelo.actualizarEstado(nuevo_envio.getDetalles().get(j).getOt(), 9);
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}
				}

			} else {
				for (int j = 0; j < nuevo_envio.getDetalles().size(); j++) {
					this.modelo.agregarDetalle(nuevo_envio.getDetalles().get(j), envioDB);

					try {
						this.modelo.actualizarEstado(nuevo_envio.getDetalles().get(j).getOt(), 9);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void llenarTablaOrigen() {

		String[] columns = { "OT", "Cliente", "Domicilio", "" };

		dtm1 = new DefaultTableModel(null, columns) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 3)
					return true;
				else
					return false;
			}
		};
		dtm1.setColumnIdentifiers(columns);

		JTable table = this.ventana.getTable();

		for (OrdenDTO o : this.ordenes_en_tabla_disponibles) {
			Object[] fila = { o, o.getCliente(), o.getCliente().getDireccion(), "ASIGNAR" };
			dtm1.addRow(fila);
		}

		table.setModel(dtm1);
		new ButtonColumn(this.ventana.getTable(), asignar(), 3);

		table.getColumnModel().getColumn(0).setMinWidth(40);
		table.getColumnModel().getColumn(0).setMaxWidth(40);

		table.getColumnModel().getColumn(3).setMinWidth(100);
		table.getColumnModel().getColumn(3).setMaxWidth(100);

	}

	private Action asignar() {
		Action asignar = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent a) {

				int row = Integer.valueOf(a.getActionCommand());

				OrdenDTO orden = (OrdenDTO) ventana.getTable().getValueAt(row, 0);
				DetallesEnvioDTO detalle = new DetallesEnvioDTO(orden, false);

				nuevo_envio.agregarDetalle(detalle);

				ordenes_en_tabla_disponibles.remove(orden);
				llenarTablaOrigen();
				ordenes_en_tabla_asignadas.add(orden);
				llenarTablaDestino();
			}

		};
		return asignar;
	}

	public void llenarTablaDestino() {

		String[] columns = { "OT", "" };

		dtm2 = new DefaultTableModel(null, columns) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 1)
					return true;
				else
					return false;
			}
		};
		dtm2.setColumnIdentifiers(columns);

		JTable table = this.ventana.getTableResultado();

		for (OrdenDTO o : this.ordenes_en_tabla_asignadas) {
			Object[] fila = { o, "QUITAR" };
			dtm2.addRow(fila);
		}

		table.setModel(dtm2);
		new ButtonColumn(this.ventana.getTableResultado(), quitar(), 1);
	}

	private Action quitar() {
		Action asignar = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent a) {

				int row = Integer.valueOf(a.getActionCommand());

				OrdenDTO orden = (OrdenDTO) ventana.getTableResultado().getValueAt(row, 0);
				DetallesEnvioDTO detalle = new DetallesEnvioDTO(orden, false);

				ordenes_en_tabla_asignadas.remove(orden);
				llenarTablaDestino();
				ordenes_en_tabla_disponibles.add(orden);
				llenarTablaOrigen();

				for (DetallesEnvioDTO det : nuevo_envio.getDetalles()) {
					if (det.getOt().getIdOT() == detalle.getOt().getIdOT())
						nuevo_envio.eliminarDetalle(det);
				}
			}
		};
		return asignar;
	}

	private void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public String calendar2str(Calendar cal) {

		Date date = cal.getTime();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = df.format(date);
		return fecha;
	}

}
