package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.mail.MessagingException;
import javax.swing.JOptionPane;

import dto.OrdenDTO;
import dto.SolicitudCompraDTO;
import modelo.Mail;
import modelo.Modelo;
import presentacion.ventanas.mail.VentanaMail;
import presentacion.ventanas.worker.ProgressWork;

public class ControladorMail implements ActionListener {
	private VentanaMail ventanaMail;
	private String ruta;
	private boolean fueEnviado;

	public ControladorMail(VentanaMail ventana, Modelo modelo) {
		iniciar(ventana, modelo);
	}

	public ControladorMail(VentanaMail ventana, Modelo modelo, OrdenDTO orden) {

		iniciar(ventana, modelo);
		this.ventanaMail.setTxtEnviarA(orden.getCliente().geteMail());
		this.ventanaMail.setTxtAsunto("Presupuesto - Reparación de " + orden.getElectrodomestico().getDescripcion());

		String presupuesto = "Se adjunta el presupuesto del electrodoméstico.";
		presupuesto += "\nEsperamos su respuesta.\n\nSaludos,\nElectro R";

		this.ventanaMail.setMensaje(presupuesto);

	}

	public ControladorMail(VentanaMail ventana, Modelo modelo, OrdenDTO orden, String ruta) {
		this.ruta = ruta;
		iniciar(ventana, modelo);
		this.ventanaMail.setTxtEnviarA(orden.getCliente().geteMail());
		this.ventanaMail.setTxtAsunto("Presupuesto - Reparación de " + orden.getElectrodomestico().getDescripcion());

		String presupuesto = "Se adjunta el presupuesto del electrodoméstico.";

		presupuesto += "\nEsperamos su respuesta.\n\nSaludos,\nElectro R";

		this.ventanaMail.setMensaje(presupuesto);
	}

	public ControladorMail(VentanaMail ventana, Modelo modelo, SolicitudCompraDTO solicitud, String ruta) {
		this.ruta = ruta;
		iniciar(ventana, modelo, solicitud, ruta);

	}

	private void iniciar(VentanaMail ventana, Modelo modelo, SolicitudCompraDTO solicitud, String ruta) {

		this.ventanaMail = ventana;
		this.ventanaMail.setLocationRelativeTo(null);

		this.ventanaMail.setTxtEnviarA(solicitud.getProveedor().getMail());
		this.ventanaMail.setTxtAsunto("Solicitud de compra");

		if (solicitud.getProveedor().getContacto() != null)
			this.ventanaMail.setMensaje("Estimado " + solicitud.getProveedor().getContacto() + ",\n\nSolicitamos el envío de las piezas incluidas en la solicitud de compra adjunta." + "\n\nEsperamos su respuesta.\nElectro R");
		else
			this.ventanaMail.setMensaje("Estimado " + solicitud.getProveedor().getNombre() + ",\n\nSolicitamos el envío de las piezas incluidas en la solicitud de compra adjunta." + "\n\nEsperamos su respuesta.\nElectro R");

		this.ventanaMail.getBtnVolver().addActionListener(this);
		this.ventanaMail.getBtnEnviar().addActionListener(this);
		this.ventanaMail.setVisible(true);
	}

	private void iniciar(VentanaMail ventana, Modelo modelo) {

		this.ventanaMail = ventana;
		this.ventanaMail.setLocationRelativeTo(null);

		this.ventanaMail.getBtnVolver().addActionListener(this);
		this.ventanaMail.getBtnEnviar().addActionListener(this);
		this.ventanaMail.setVisible(true);
		this.fueEnviado = false;
	}

	private boolean camposValidos() {
		boolean ret = true;
		ret = ret && this.ventanaMail.getTxtEnviarA().getText().length() > 0;
		ret = ret && this.ventanaMail.getTxtAsunto().getText().length() > 0;
		ret = ret && this.ventanaMail.getMensaje().getText().length() > 0;

		return ret;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.ventanaMail.getBtnEnviar()) {
			if (ruta == null) {
				if (camposValidos()) {
					try {

						Mail mail = new Mail(ventanaMail.getTxtEnviarA().getText(), ventanaMail.getTxtAsunto().getText(), ventanaMail.getMensaje().getText());
						generateThread(mail);
						JOptionPane.showMessageDialog(this.ventanaMail, "Mail enviado correctamente");
						this.fueEnviado = true;
						this.ventanaMail.dispose();
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(this.ventanaMail, "Se produjo un error al enviar el mail.");
					}
				} else {
					JOptionPane.showMessageDialog(this.ventanaMail, "Debe completar todos los campos");
				}
			} else {
				if (camposValidos()) {

					try {
						Mail mail = new Mail(ventanaMail.getTxtEnviarA().getText(), ventanaMail.getTxtAsunto().getText(), ventanaMail.getMensaje().getText(), ruta);
						generateThread(mail);
						JOptionPane.showMessageDialog(this.ventanaMail, "Mail enviado correctamente");
						this.fueEnviado = true;
						this.ventanaMail.dispose();
					} catch (MessagingException | InterruptedException e1) {
						JOptionPane.showMessageDialog(this.ventanaMail, "Se produjo un error al enviar el mail.");
					}

				} else
					JOptionPane.showMessageDialog(this.ventanaMail, "Debe completar todos los campos");
			}
		} else if (e.getSource() == this.ventanaMail.getBtnVolver()) {
			this.ventanaMail.dispose();
		}
	}

	private void generateThread(Thread mail) throws InterruptedException {
		ProgressWork work = new ProgressWork(ventanaMail, "Por favor aguarde mientras se esta enviando el mail.", mail,"/recursos/mail.gif");
		work.mostrar();
	}

	public boolean fueEnviado() {
		return fueEnviado;
	}

	public void fueEnviado(boolean fueEnviado) {
		this.fueEnviado = fueEnviado;
	}

}
