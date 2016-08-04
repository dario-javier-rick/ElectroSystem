package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.mail.MessagingException;
import javax.swing.JOptionPane;

import dto.UsuarioDTO;
import helpers.Validador;
import modelo.Mail;
import modelo.Modelo;
import presentacion.ventanas.logueo.VentanaLogueo;
import presentacion.vista.VistaPrincipal;

public class ControladorLogueo implements ActionListener {

	private VentanaLogueo ventana;
	private Modelo modelo;
	private UsuarioDTO usuarioLogueado;

	public ControladorLogueo(VentanaLogueo ventana, Modelo modelo) {
		this.ventana = ventana;
		this.modelo = modelo;
		this.usuarioLogueado = null;
	}

	public void iniciar() {

		this.ventana.setLocationRelativeTo(null);

		this.ventana.getBtnAceptar().addActionListener(this);
		this.ventana.getMntmreestablecer().addActionListener(this);
		this.ventana.getMntmIngresarClave().addActionListener(this);

		this.ventana.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {

			if (e.getSource() == this.ventana.getBtnAceptar()) {
				try {
					UsuarioDTO usuarioPedido = modelo.obtenerUsuario(this.ventana.getTfUsuario().getText());
					if (usuarioPedido.getUsuario() != null) {
						if (passwordOk(usuarioPedido, this.ventana.getPfPassword().getPassword())) {
							VistaPrincipal vistaPrincipal = new VistaPrincipal();
							ControladorPrincipal controlador = new ControladorPrincipal(vistaPrincipal, modelo, usuarioPedido);
							controlador.iniciar();
							this.ventana.dispose();
						} else {
							JOptionPane.showMessageDialog(null, "La contraseña ingresada es incorrecta.", "contraseña incorrecta", JOptionPane.WARNING_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "El usuario ingresado no existe.", "Usuario incorrecto", JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Ocurrió un error: " + ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
				}

			} else if (e.getSource() == this.ventana.getMntmreestablecer()) {
				int respuesta = JOptionPane.showConfirmDialog(null, "Se enviará la clave de desbloqueo a la casilla de mail del superusuario, si ud. no es superusuario por favor desista. ¿Desea continuar?", "¿Olvido su clave?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
				if (respuesta == JOptionPane.YES_OPTION) {
					String clave = Validador.getCadenaAlfanumAleatoria(20);
					modelo.guardarClaveDesbl(clave);
					enviarMail(clave);
				}

			} else if (e.getSource() == this.ventana.getMntmIngresarClave()) {
				String clave = JOptionPane.showInputDialog(null, "Ingrese la clave de reestablecimiento que se ha enviado a su mail.", "Reestablecer superusuario", JOptionPane.INFORMATION_MESSAGE);
				if (clave != null) {
					if (modelo.verificarClaveReest(clave)) {
						modelo.reestablecerSuperUsuario();
						JOptionPane.showMessageDialog(null, "Clave de desbloqueo correcta, se han reestablecido el superusuario con los valores" + "enviados al mail.", "Clave válida", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Clave de desbloqueo errónea.", "Clave errónea", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}
	}

	private void enviarMail(String clave) {
		String body = "Ingresando la siguiente clave se reestableceran los datos de login a usuario: admin, pass: admin. Recuerde " + "modificarlos luego de acceder\n" + "Clave de reestablecimiento: " + clave;
		try {
			Mail mail = new Mail(modelo.obtenerMail(), "Reestablecimiento de clave de ServiceG1", body);
			JOptionPane.showMessageDialog(this.ventana, "Clave de desbloqueo enviada.", "Envío de clave", JOptionPane.INFORMATION_MESSAGE);
		} catch (MessagingException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.ventana, "No se pudo enviar la clave de desbloqueo.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	private boolean passwordOk(UsuarioDTO usuario, char[] password) {
		String clave = new String(password);
		try {
			return modelo.verificarClave(usuario, clave);
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}
		return false;
	}

	public UsuarioDTO getUsuarioLogueado() {
		return usuarioLogueado;
	}

}
