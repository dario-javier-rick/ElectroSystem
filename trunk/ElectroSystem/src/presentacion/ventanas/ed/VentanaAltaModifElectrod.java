package presentacion.ventanas.ed;

import java.awt.BorderLayout;
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

import dto.MarcaDTO;
import helpers.Validador;

public class VentanaAltaModifElectrod extends JDialog {

	private static final long serialVersionUID = -3786097914923014702L;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField tfModelo;
	private JTextField tfNombre;
	private JComboBox<MarcaDTO> comboMarca;
	private JButton btnAgregarMarca;
	private JButton btnAgregar;
	private JButton btnCancelar;

	
	public VentanaAltaModifElectrod(JDialog padre) {
		super(padre, true);
		go();
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public VentanaAltaModifElectrod(JFrame padre) {
		super(padre, true);
		go();
	}
	
	private void go() {
		setTitle("Agregar nuevo electrodoméstico");
		setBounds(100, 100, 495, 242);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		comboMarca = new JComboBox<MarcaDTO>();
		comboMarca.setBounds(110, 37, 195, 28);
		contentPanel.add(comboMarca);
		
		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setBounds(29, 44, 46, 14);
		contentPanel.add(lblMarca);
		
		btnAgregarMarca = new JButton("Agregar marca");
		btnAgregarMarca.setBounds(315, 37, 139, 28);
		contentPanel.add(btnAgregarMarca);
		
		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setBounds(29, 85, 71, 14);
		contentPanel.add(lblModelo);
		
		JLabel lblDescripcin = new JLabel("Nombre:");
		lblDescripcin.setBounds(29, 126, 71, 14);
		contentPanel.add(lblDescripcin);
		
		tfModelo = new JTextField();
		tfModelo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (tfModelo.getText().length()>20 || !Validador.caracterCodigoValido((e.getKeyChar())))
					e.consume();
			}
		});
		tfModelo.setBounds(110, 78, 145, 28);
		contentPanel.add(tfModelo);
		tfModelo.setColumns(10);
		
		tfNombre = new JTextField();
		tfNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (tfNombre.getText().length()>59)
					e.consume();
			}
		});
		tfNombre.setBounds(110, 119, 145, 28);
		contentPanel.add(tfNombre);
		tfNombre.setColumns(10);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(365, 176, 89, 23);
		contentPanel.add(btnCancelar);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(266, 176, 89, 23);
		contentPanel.add(btnAgregar);
	}

	public JTextField getTfModelo() {
		return tfModelo;
	}

	public JTextField getTfNombre() {
		return tfNombre;
	}

	public JComboBox<MarcaDTO> getComboMarca() {
		return comboMarca;
	}

	public JButton getBtnAgregarMarca() {
		return btnAgregarMarca;
	}

	public JButton getBtnAgregar() {
		return btnAgregar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}
}
