package modelo;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail extends Thread {

	private String to;
	private String subject;
	private String body;
	private String rutaArchivo;
	private Transport transport;

	public Mail(String to, String subject, String body) throws AddressException, MessagingException {
		this.to = to;
		this.subject = subject;
		this.body = body;
		prepareMail();
	}

	public Mail(String to, String subject, String body, String rutaArchivo) throws AddressException, MessagingException {
		this.to = to;
		this.subject = subject;
		this.body = body;
		this.rutaArchivo = rutaArchivo;
		prepareMail();
	}

	private void prepareMail() throws AddressException, MessagingException {

		// Se setean las propiedades
		Properties mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");

		Session getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		MimeMessage generateMailMessage = new MimeMessage(getMailSession);
		// Se agrega un recipiente que contiene el receptor del mail
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(to));
		// Se carga el asunto del texto
		generateMailMessage.setSubject(subject);

		// Se carga el cuerpo del mail en formato html
		if (this.rutaArchivo == null) {
			generateMailMessage.setContent(body, "text/html");
		} else {
			BodyPart texto = new MimeBodyPart();
			texto.setText(body);

			BodyPart adjunto = new MimeBodyPart();
			adjunto.setDataHandler(new DataHandler(new FileDataSource(rutaArchivo)));
			adjunto.setFileName("reporte.pdf");

			MimeMultipart multiParte = new MimeMultipart();
			multiParte.addBodyPart(texto);
			multiParte.addBodyPart(adjunto);

			generateMailMessage.setContent(multiParte);
		}

		transport = getMailSession.getTransport("smtp");
		// Se conecta al mail emisor
		transport.connect("smtp.gmail.com", "electrorgrupo1", "grupo1234");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();

	}

	@Override
	public void run() {
		if (to != null && subject != null && body != null) {
			try {
				prepareMail();
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
}
