package presentacion.controlador.fleteros;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dto.DetallesEnvioDTO;
import dto.EnvioDTO;
import modelo.Modelo;
import presentacion.ventanas.fleteros.VentanaEnvio;

public class ControladorEnvio implements ActionListener, MouseListener {

	private VentanaEnvio ventana;
	private Modelo modelo;
	private EnvioDTO envio_a_cargar;
	private DefaultTableModel dtm1;

	public ControladorEnvio(VentanaEnvio ventana, Modelo modelo, EnvioDTO envio) {

		this.ventana = ventana;
		this.ventana.setLocationRelativeTo(null);

		this.modelo = modelo;
		this.envio_a_cargar = envio;

		init();
	}

	public void init() {

		this.ventana.setLocationRelativeTo(null);
		this.ventana.setTitle("Envío Nº" + envio_a_cargar.getId());

		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnGuardarCambios().addActionListener(this);
		this.ventana.getTable().addMouseListener(this);

		llenarTabla();
		this.ventana.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.ventana.getBtnCancelar()) {
			this.ventana.dispose();
		}

		else if (e.getSource() == this.ventana.getBtnGuardarCambios()) {
			guardarCambios();
			this.ventana.dispose();
		}
	}

	public void llenarTabla() {

		String[] columns = { "OT Nº", "Cliente", "Domicilio", "Entregada", "No Entregada" };

		dtm1 = new DefaultTableModel(null, columns) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {

				if (column == 3 && (Boolean) getValueAt(row, 4))
					return true;
				else if (column == 4 && (Boolean) getValueAt(row, 3))
					return false;
				else
					return false;
			}

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int columnIndex) {
				if (columnIndex == 3 || columnIndex == 4)
					return Boolean.class;
				return super.getColumnClass(columnIndex);
			}

		};
		dtm1.setColumnIdentifiers(columns);

		JTable table = this.ventana.getTable();
		Object[] fila = new Object[0];

		for (DetallesEnvioDTO det : envio_a_cargar.getDetalles()) {

			if (det.isEntregado()) {
				Object[] filaEntregada = { det, det.getOt().getCliente(), det.getOt().getCliente().getDireccion(), det.isEntregado(), false };
				fila = filaEntregada;

			} else {
				Object[] filaNoEntregada = { det, det.getOt().getCliente(), det.getOt().getCliente().getDireccion(), false, true };
				fila = filaNoEntregada;
			}

			dtm1.addRow(fila);
		}
		table.setModel(dtm1);
	}

	public void guardarCambios() {
		try {
			for (int i = 0; i < this.ventana.getTable().getRowCount(); i++) {
				if ((Boolean) this.ventana.getTable().getModel().getValueAt(i, 4)) {
					DetallesEnvioDTO d = (DetallesEnvioDTO) this.ventana.getTable().getModel().getValueAt(i, 0);
					this.modelo.actualizarEntregado(d.getOt(), envio_a_cargar);
					this.modelo.actualizarEstado(d.getOt(), 10); // entregada
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getClickCount() == 1) {

			JTable target = (JTable) e.getSource();
			int row = target.getSelectedRow();
			int col = target.getSelectedColumn();

			// seleccionar solo uno
			if (col == 3 && target.getModel().getValueAt(row, 3) != null && (Boolean) target.getModel().getValueAt(row, 3))
				target.getModel().setValueAt(false, row, 4);
			else if (col == 4 && target.getModel().getValueAt(row, 4) != null && (Boolean) target.getModel().getValueAt(row, 4))
				target.getModel().setValueAt(false, row, 4);
			else if (col == 3 && target.getModel().getValueAt(row, 3) != null && !(Boolean) target.getModel().getValueAt(row, 3))
				target.getModel().setValueAt(true, row, 4);
			else if (col == 4 && target.getModel().getValueAt(row, 4) != null && !(Boolean) target.getModel().getValueAt(row, 4))
				target.getModel().setValueAt(true, row, 3);

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
