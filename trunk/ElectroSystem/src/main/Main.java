package main;

import javax.swing.JOptionPane;

import modelo.Modelo;
import presentacion.controlador.ControladorLogueo;
import presentacion.ventanas.logueo.VentanaLogueo;

public class Main {

	private Modelo modelo;
	private VentanaLogueo ventanaLogueo;
	private ControladorLogueo controladorLogueo;

	public Main() {

		try {
			this.modelo = Modelo.getInstance();
			this.ventanaLogueo = new VentanaLogueo();
			this.controladorLogueo = new ControladorLogueo(ventanaLogueo, modelo);
			controladorLogueo.iniciar();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocurrió un error: " + e.getMessage());
		}

	}

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main();
			}
		});
	}

	// public static void main(String[] args) {
	//
	// Modelo modelo = new Modelo();
	// VentanaLogueo ventanaLogueo = new VentanaLogueo();
	// ControladorLogueo controladorLogueo = new
	// ControladorLogueo(ventanaLogueo, modelo);
	// controladorLogueo.iniciar();
	// }

}