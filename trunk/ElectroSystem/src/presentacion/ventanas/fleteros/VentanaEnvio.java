package presentacion.ventanas.fleteros;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class VentanaEnvio extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnCancelar;
	private JButton btnGuardarCambios;
	private JTable table;
	private JScrollPane scrollPane;

	/**
	 * Create the dialog.
	 */
	public VentanaEnvio(JFrame padre) {
		super(padre, true);
		setResizable(false);
		setBounds(100, 100, 852, 436);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 826, 342);
		contentPanel.add(scrollPane);

		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false) ;
		scrollPane.setViewportView(table);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			btnGuardarCambios = new JButton("Guardar cambios");
			buttonPane.add(btnGuardarCambios);
			{
				btnCancelar = new JButton("Cancelar");
				buttonPane.add(btnCancelar);
			}
		}
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton okButton) {
		this.btnCancelar = okButton;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JButton getBtnGuardarCambios() {
		return btnGuardarCambios;
	}

	public void setBtnGuardarCambios(JButton btnGuardarCambios) {
		this.btnGuardarCambios = btnGuardarCambios;
	}

}
