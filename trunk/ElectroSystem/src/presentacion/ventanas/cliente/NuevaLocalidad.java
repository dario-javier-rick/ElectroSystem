package presentacion.ventanas.cliente;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dto.LocalidadDTO;
import dto.ZonaDTO;
import helpers.Validador;

public class NuevaLocalidad extends JDialog {

	private static final long serialVersionUID = 2437204042595523802L;

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCP;
	private JTextField txtNombre;
	private JComboBox<ZonaDTO> comboZonas;
	private JButton okButton;
	private JButton cancelButton;
	private JTextField tfProvincia;
	private JLabel lblProvincia;
	private JComboBox<LocalidadDTO> comboLocalidades;

	public NuevaLocalidad(JDialog padre) {
		super(padre, true);
		setTitle("Nueva Localidad");
		setBounds(100, 100, 298, 287);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblCdigoPostal = new JLabel("Código Postal:");
		lblCdigoPostal.setBounds(27, 57, 86, 14);
		contentPanel.add(lblCdigoPostal);

		txtCP = new JTextField();
		txtCP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.esNumero(e.getKeyChar()) || txtCP.getText().length() > 3)
					e.consume();
			}
		});
		txtCP.setBounds(123, 50, 133, 28);
		contentPanel.add(txtCP);
		txtCP.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(27, 96, 86, 14);
		contentPanel.add(lblNombre);

		JLabel lblZona = new JLabel("Zona:");
		lblZona.setBounds(27, 177, 86, 14);
		contentPanel.add(lblZona);

		txtNombre = new JTextField();
		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacioNumero(e.getKeyChar()) || txtNombre.getText().length() > 29)
					e.consume();
			}
		});
		txtNombre.setBounds(123, 89, 133, 28);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);

		comboZonas = new JComboBox<ZonaDTO>();
		comboZonas.setBounds(123, 170, 133, 28);
		contentPanel.add(comboZonas);

		tfProvincia = new JTextField();
		tfProvincia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacioNumero(e.getKeyChar()) || tfProvincia.getText().length() > 29)
					e.consume();
			}
		});
		tfProvincia.setBounds(123, 131, 133, 28);
		contentPanel.add(tfProvincia);
		tfProvincia.setColumns(10);

		lblProvincia = new JLabel("Provincia:");
		lblProvincia.setBounds(27, 138, 86, 14);
		contentPanel.add(lblProvincia);

		comboLocalidades = new JComboBox<LocalidadDTO>();
		comboLocalidades.setBounds(27, 11, 229, 28);
		contentPanel.add(comboLocalidades);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Aceptar");
				buttonPane.add(okButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				buttonPane.add(cancelButton);
			}
		}
	}

	/**
	 * @wbp.parser.constructor
	 */
	public NuevaLocalidad(JFrame padre) {
		super(padre, true);
		setResizable(false);
		setTitle("Editar Localidad");
		setBounds(100, 100, 298, 287);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblCdigoPostal = new JLabel("Código Postal:");
		lblCdigoPostal.setBounds(27, 57, 86, 14);
		contentPanel.add(lblCdigoPostal);

		txtCP = new JTextField();
		txtCP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.esNumero(e.getKeyChar()) || txtCP.getText().length() > 3)
					e.consume();
			}
		});
		txtCP.setBounds(123, 50, 133, 28);
		contentPanel.add(txtCP);
		txtCP.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(27, 96, 86, 14);
		contentPanel.add(lblNombre);

		JLabel lblZona = new JLabel("Zona:");
		lblZona.setBounds(27, 177, 86, 14);
		contentPanel.add(lblZona);

		txtNombre = new JTextField();
		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacioNumero(e.getKeyChar()) || txtNombre.getText().length() > 29)
					e.consume();
			}
		});
		txtNombre.setBounds(123, 89, 133, 28);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);

		comboZonas = new JComboBox<ZonaDTO>();
		comboZonas.setBounds(123, 170, 133, 28);
		contentPanel.add(comboZonas);

		tfProvincia = new JTextField();
		tfProvincia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacioNumero(e.getKeyChar()) || tfProvincia.getText().length() > 29)
					e.consume();
			}
		});
		tfProvincia.setBounds(123, 131, 133, 28);
		contentPanel.add(tfProvincia);
		tfProvincia.setColumns(10);

		lblProvincia = new JLabel("Provincia:");
		lblProvincia.setBounds(27, 138, 86, 14);
		contentPanel.add(lblProvincia);

		comboLocalidades = new JComboBox<LocalidadDTO>();
		comboLocalidades.setBounds(27, 11, 229, 28);
		contentPanel.add(comboLocalidades);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Aceptar");
				buttonPane.add(okButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				buttonPane.add(cancelButton);
			}
		}
	}

	public JTextField getTfProvincia() {
		return tfProvincia;
	}

	public int getTxtCP() {
		if (txtCP.getText().isEmpty())
			return 0;
		return Integer.parseInt(this.txtCP.getText().toString());
	}

	public void setTxtCP(String txtCP) {
		this.txtCP.setText(txtCP);
	}

	public String getTxtNombre() {
		return txtNombre.getText().toString();
	}

	public void setTxtNombre(String txtNombre) {
		this.txtNombre.setText(txtNombre);
	}

	public JComboBox<ZonaDTO> getComboZonas() {
		return comboZonas;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public void setTfProvincia(String tfProvincia) {
		this.tfProvincia.setText(tfProvincia);
	}

	public JComboBox<LocalidadDTO> getComboLocalidades() {
		return comboLocalidades;
	}

	public void setComboLocalidades(JComboBox<LocalidadDTO> comboLocalidades) {
		this.comboLocalidades = comboLocalidades;
	}

	public void bloquearCampos() {
		this.txtCP.setEditable(false);
	}

	public void actualizarCampos(LocalidadDTO localidad) {
		this.setTxtCP(String.valueOf(localidad.getCodigoPostal()));
		this.setTxtNombre(localidad.getNombre());
		this.setTfProvincia(localidad.getProvincia());
		this.comboZonas.getModel().setSelectedItem((ZonaDTO)localidad.getZonaDeEnvio());
	}

}
