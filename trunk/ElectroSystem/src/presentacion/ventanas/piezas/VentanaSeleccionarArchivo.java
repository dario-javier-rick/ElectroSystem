package presentacion.ventanas.piezas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dto.ProveedorDTO;

@SuppressWarnings("serial")
public class VentanaSeleccionarArchivo extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnProcesar;
	private JButton btnSeleccionarArchivo;
	private JButton cancelButton;
	private JComboBox<ProveedorDTO> combo;

	public VentanaSeleccionarArchivo(JFrame padre) {
		super(padre, true);
		setModal(true);
		setResizable(false);
		setTitle("Importar Precios");
		setBounds(100, 100, 312, 228);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Proveedor: ");
			lblNewLabel.setBounds(20, 22, 77, 14);
			contentPanel.add(lblNewLabel);
		}

		combo = new JComboBox<ProveedorDTO>();
		combo.setBounds(135, 15, 147, 28);
		contentPanel.add(combo);

		JLabel lblSeleccionarArchivo = new JLabel("Seleccionar archivo: ");
		lblSeleccionarArchivo.setBounds(20, 67, 114, 14);
		contentPanel.add(lblSeleccionarArchivo);

		btnSeleccionarArchivo = new JButton("");
		btnSeleccionarArchivo.setIcon(new ImageIcon(VentanaSeleccionarArchivo.class.getResource("/icons/csv.png")));
		btnSeleccionarArchivo.setBounds(135, 54, 147, 39);
		contentPanel.add(btnSeleccionarArchivo);

		btnProcesar = new JButton("Procesar");
		btnProcesar.setBounds(135, 104, 147, 28);
		contentPanel.add(btnProcesar);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				cancelButton = new JButton("OK");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public JButton getBtnProcesar() {
		return btnProcesar;
	}

	public void setBtnProcesar(JButton btnProcesar) {
		this.btnProcesar = btnProcesar;
	}

	public JButton getBtnSeleccionarArchivo() {
		return btnSeleccionarArchivo;
	}

	public void setBtnSeleccionarArchivo(JButton btnSeleccionarArchivo) {
		this.btnSeleccionarArchivo = btnSeleccionarArchivo;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public JComboBox<ProveedorDTO> getCombo() {
		return combo;
	}

	public void setCombo(JComboBox<ProveedorDTO> combo) {
		this.combo = combo;
	}

}
