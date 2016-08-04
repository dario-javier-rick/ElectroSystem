package presentacion.ventanas.mail;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VentanaMail extends JDialog {

	private static final long serialVersionUID = -9170875729309704256L;

	private JTextField txtEnviarA;
	private JTextField txtAsunto;
	private JEditorPane editorPane;
	private JButton btnEnviar;
	private JButton btnVolver;

	/**
	 * @wbp.parser.constructor
	 */
	public VentanaMail(JFrame padre) {
		super(padre, true);
		setBounds(100, 100, 459, 364);
		setTitle("Enviar Mail");
		go();
	}

	public VentanaMail(JDialog padre) {
		super(padre, true);
		// setModal(false); // Dario Rick
		setBounds(100, 100, 524, 371);
		setTitle("Enviar Mail");

		go();
	}

	private void go() {
		getContentPane().setLayout(null);
		setResizable(false);
		JLabel lblEnviarA = new JLabel("Enviar a:");
		lblEnviarA.setBounds(27, 45, 73, 14);
		getContentPane().add(lblEnviarA);

		JLabel lblAsunto = new JLabel("Asunto:");
		lblAsunto.setBounds(27, 88, 73, 14);
		getContentPane().add(lblAsunto);

		JLabel lblTexto = new JLabel("Mensaje:");
		lblTexto.setBounds(27, 130, 73, 14);
		getContentPane().add(lblTexto);

		txtEnviarA = new JTextField();
		txtEnviarA.setBounds(88, 38, 350, 28);
		getContentPane().add(txtEnviarA);
		txtEnviarA.setColumns(10);

		txtAsunto = new JTextField();
		txtAsunto.setColumns(10);
		txtAsunto.setBounds(88, 81, 350, 28);
		getContentPane().add(txtAsunto);

		editorPane = new JEditorPane();
		editorPane.setBounds(88, 127, 350, 139);
		getContentPane().add(editorPane);

		btnEnviar = new JButton("Enviar");
		btnEnviar.setBounds(250, 298, 89, 23);
		getContentPane().add(btnEnviar);

		btnVolver = new JButton("Volver");
		btnVolver.setBounds(349, 298, 89, 23);
		getContentPane().add(btnVolver);
	}

	public JTextField getTxtEnviarA() {
		return txtEnviarA;
	}

	public JTextField getTxtAsunto() {
		return txtAsunto;
	}

	public JEditorPane getMensaje() {
		return editorPane;
	}

	public JButton getBtnEnviar() {
		return btnEnviar;
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public void setTxtEnviarA(String txtEnviarA) {
		this.txtEnviarA.setText(txtEnviarA);
	}

	public void setTxtAsunto(String txtAsunto) {
		this.txtAsunto.setText(txtAsunto);
	}

	public void setMensaje(String editorPane) {
		this.editorPane.setText(editorPane);
	}

}
