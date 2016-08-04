package presentacion.ventanas.usuario;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

import helpers.Validador;

import javax.swing.JPasswordField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.ImageIcon;

public class VentanaAltaModifUsuario extends JDialog {

	private static final long serialVersionUID = 9120280564711091384L;
	
	private JTextField tfNombre;
	private JTextField tfApellido;
	private JTextField tfUsuario;
	private JPasswordField pfPass;
	private JPasswordField pfRepitaPass;
	private JRadioButton rbAdm;
	private JRadioButton rbTec;
	private JButton btnCrear;
	private JButton btnCancelar;
	private JButton btnGenerarUsuario;
	private JLabel lblApellidoObligatorio;
	private JLabel lblNombreObligatorio;
	private JLabel lblPass;
	private JLabel lblRepita;
	private JLabel lblIngresePass;
	private JLabel lblRepitaPass;
	private JLabel lblRol;
	private JTextField tfMail;
	private JLabel lblMail;
	private JLabel lblFormatoDeMail;

	public VentanaAltaModifUsuario(JFrame padre) {
		super (padre, true);
		setBounds(100, 100, 530, 396);
		setResizable(false);
		getContentPane().setLayout(null);
		
		tfNombre = new JTextField();
		tfNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacio(e.getKeyChar()) || tfNombre.getText().length()>19) {
					e.consume();
				}
			}
		});
		tfNombre.setBounds(104, 11, 229, 28);
		getContentPane().add(tfNombre);
		tfNombre.setColumns(10);
		
		tfApellido = new JTextField();
		tfApellido.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacio(e.getKeyChar()) || tfNombre.getText().length()>19) {
					e.consume();
				}
			}
		});
		tfApellido.setBounds(104, 50, 229, 28);
		getContentPane().add(tfApellido);
		tfApellido.setColumns(10);
		
		tfUsuario = new JTextField();
		tfUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterUserPass(e.getKeyChar()) || tfNombre.getText().length()>19) {
					e.consume();
				}
			}
		});
		tfUsuario.setBounds(134, 102, 199, 28);
		getContentPane().add(tfUsuario);
		tfUsuario.setColumns(10);
		
		pfPass = new JPasswordField();
		pfPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterUserPass(e.getKeyChar()) || tfNombre.getText().length()>19) {
					e.consume();
				}
			}
		});
		pfPass.setBounds(134, 141, 199, 28);
		getContentPane().add(pfPass);
		
		pfRepitaPass = new JPasswordField();
		pfRepitaPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterUserPass(e.getKeyChar()) || tfNombre.getText().length()>19) {
					e.consume();
				}
			}
		});
		pfRepitaPass.setBounds(134, 181, 199, 28);
		getContentPane().add(pfRepitaPass);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(425, 328, 89, 28);
		getContentPane().add(btnCancelar);
		
		btnCrear = new JButton("Crear");
		btnCrear.setBounds(326, 328, 89, 28);
		getContentPane().add(btnCrear);
		
		rbAdm = new JRadioButton("Administrativo");
		rbAdm.setSelected(true);
		rbAdm.setBounds(134, 262, 109, 23);
		getContentPane().add(rbAdm);
		
		rbTec = new JRadioButton("Técnico");
		rbTec.setBounds(134, 288, 109, 23);
		getContentPane().add(rbTec);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rbAdm);
		group.add(rbTec);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(20, 18, 74, 14);
		getContentPane().add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(20, 57, 74, 14);
		getContentPane().add(lblApellido);
		
		JLabel lblNombreDeUsuario = new JLabel("");
		lblNombreDeUsuario.setIcon(new ImageIcon(VentanaAltaModifUsuario.class.getResource("/icons/usr.png")));
		lblNombreDeUsuario.setBounds(92, 102, 32, 28);
		getContentPane().add(lblNombreDeUsuario);
		
		lblRol = new JLabel("Rol:");
		lblRol.setBounds(82, 266, 46, 14);
		getContentPane().add(lblRol);
		
		lblIngresePass = new JLabel("");
		lblIngresePass.setIcon(new ImageIcon(VentanaAltaModifUsuario.class.getResource("/icons/pwd.png")));
		lblIngresePass.setBounds(92, 141, 32, 28);
		getContentPane().add(lblIngresePass);
		
		lblRepitaPass = new JLabel("Repita su contraseña:");
		lblRepitaPass.setBounds(20, 188, 125, 14);
		getContentPane().add(lblRepitaPass);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 89, 494, 2);
		getContentPane().add(separator);
		
		btnGenerarUsuario = new JButton("Generar usuario");
		btnGenerarUsuario.setBounds(359, 102, 138, 28);
		getContentPane().add(btnGenerarUsuario);
		
		lblApellidoObligatorio = new JLabel("Campos obligatorios.");
		lblApellidoObligatorio.setForeground(Color.RED);
		lblApellidoObligatorio.setBounds(359, 57, 145, 14);
		getContentPane().add(lblApellidoObligatorio);
		lblApellidoObligatorio.setVisible(false);
		
		lblNombreObligatorio = new JLabel("Campos obligatorios.");
		lblNombreObligatorio.setForeground(Color.RED);
		lblNombreObligatorio.setBounds(359, 18, 145, 14);
		getContentPane().add(lblNombreObligatorio);
		lblNombreObligatorio.setVisible(false);
		
		lblPass = new JLabel("Ingrese una contraseña.");
		lblPass.setForeground(Color.RED);
		lblPass.setBounds(359, 148, 145, 14);
		getContentPane().add(lblPass);
		lblPass.setVisible(false);
		
		lblRepita = new JLabel("La contraseña no coincide.");
		lblRepita.setForeground(Color.RED);
		lblRepita.setBounds(359, 184, 145, 14);
		getContentPane().add(lblRepita);
		
		tfMail = new JTextField();
		tfMail.setVisible(false);
		tfMail.setBounds(134, 221, 199, 28);
		getContentPane().add(tfMail);
		tfMail.setColumns(10);
		tfMail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterMailValido(e.getKeyChar()) || tfMail.getText().length() > 29)
					e.consume();
			}
		});
		
		lblFormatoDeMail = new JLabel("Formato de mail no válido.");
		lblFormatoDeMail.setVisible(false);
		lblFormatoDeMail.setForeground(Color.RED);
		lblFormatoDeMail.setBounds(359, 228, 145, 14);
		getContentPane().add(lblFormatoDeMail);
		
		lblMail = new JLabel("Mail:");
		lblMail.setVisible(false);
		lblMail.setBounds(82, 228, 42, 14);
		getContentPane().add(lblMail);
		lblRepita.setVisible(false);
	}

	public JTextField getTfMail() {
		return tfMail;
	}

	public JLabel getLblMail() {
		return lblMail;
	}

	public JLabel getLblFormatoDeMail() {
		return lblFormatoDeMail;
	}

	public JLabel getLblRol() {
		return lblRol;
	}

	public JLabel getLblIngresePass() {
		return lblIngresePass;
	}

	public JLabel getLblRepitaPass() {
		return lblRepitaPass;
	}

	public JLabel getLblApellidoObligatorio() {
		return lblApellidoObligatorio;
	}

	public JLabel getLblNombreObligatorio() {
		return lblNombreObligatorio;
	}

	public JLabel getLblPass() {
		return lblPass;
	}

	public JLabel getLblRepita() {
		return lblRepita;
	}

	public JButton getBtnGenerarUsuario() {
		return btnGenerarUsuario;
	}

	public JTextField getTfNombre() {
		return tfNombre;
	}

	public JTextField getTfApellido() {
		return tfApellido;
	}

	public JTextField getTfUsuario() {
		return tfUsuario;
	}

	public JPasswordField getPfPass() {
		return pfPass;
	}

	public JPasswordField getPfRepitaPass() {
		return pfRepitaPass;
	}

	public JRadioButton getRbAdm() {
		return rbAdm;
	}

	public JRadioButton getRbTec() {
		return rbTec;
	}

	public JButton getBtnCrear() {
		return btnCrear;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}
}
