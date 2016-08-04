package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JOptionPane;

import dto.LocalidadDTO;
import dto.ZonaDTO;
import modelo.Modelo;
import presentacion.ventanas.cliente.NuevaLocalidad;

public class ControladorLocalidad implements ActionListener, ItemListener {

	private Modelo modelo;
	private NuevaLocalidad ventanaLocalidad;
	private LocalidadDTO elegida;
	private boolean edit;

	public ControladorLocalidad(NuevaLocalidad ventana, Modelo modelo, boolean edit) {
		this.edit = edit;

		if (!edit) {
			iniciar(ventana, modelo);
			this.ventanaLocalidad.setTitle("Nueva Localidad");
			this.ventanaLocalidad.getComboLocalidades().setVisible(false);
			this.ventanaLocalidad.setVisible(true);

		} else {
			editar(ventana, modelo);
			this.ventanaLocalidad.setTitle("Editar Localidad");
			this.ventanaLocalidad.setVisible(true);
		}

	}

	private void iniciar(NuevaLocalidad ventana, Modelo modelo) {
		this.setVentanaLocalidad(ventana);
		this.ventanaLocalidad.setLocationRelativeTo(null);

		this.setModelo(modelo);

		try {
			this.ventanaLocalidad.getCancelButton().addActionListener(this);
			this.ventanaLocalidad.getOkButton().addActionListener(this);
			for (int i = 0; i < this.modelo.obtenerZonas().size(); i++) {
				this.ventanaLocalidad.getComboZonas().addItem(this.modelo.obtenerZonas().get(i));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void editar(NuevaLocalidad ventana, Modelo modelo) {

		this.setVentanaLocalidad(ventana);
		this.ventanaLocalidad.setLocationRelativeTo(null);

		this.setModelo(modelo);
		try {
			this.ventanaLocalidad.getCancelButton().addActionListener(this);
			this.ventanaLocalidad.getOkButton().addActionListener(this);
			this.ventanaLocalidad.getComboLocalidades().addItemListener(this);

			for (int i = 0; i < this.modelo.obtenerZonas().size(); i++) {
				this.ventanaLocalidad.getComboZonas().addItem(this.modelo.obtenerZonas().get(i));
			}

			for (int i = 0; i < this.modelo.obtenerLocalidades().size(); i++) {
				this.ventanaLocalidad.getComboLocalidades().addItem(this.modelo.obtenerLocalidades().get(i));
			}

			this.ventanaLocalidad.actualizarCampos((LocalidadDTO) this.ventanaLocalidad.getComboLocalidades().getSelectedItem());
			this.ventanaLocalidad.bloquearCampos();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public NuevaLocalidad getVentanaLocalidad() {
		return ventanaLocalidad;
	}

	public void setVentanaLocalidad(NuevaLocalidad ventanaLocalidad) {
		this.ventanaLocalidad = ventanaLocalidad;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.ventanaLocalidad.getCancelButton()) {

			this.ventanaLocalidad.dispose();
		}

		else if (e.getSource() == this.ventanaLocalidad.getOkButton()) {

			try {

				if (camposCompletos()) {
					LocalidadDTO localidad = new LocalidadDTO(this.ventanaLocalidad.getTxtCP(), this.ventanaLocalidad.getTxtNombre(), (ZonaDTO) this.ventanaLocalidad.getComboZonas().getSelectedItem(), this.ventanaLocalidad.getTfProvincia().getText());

					if (!edit) {
						this.modelo.agregarLocalidad(localidad);
						this.elegida = localidad;
						this.ventanaLocalidad.dispose();

					} else {
						this.modelo.actualizarLocalidad(localidad);
						JOptionPane.showMessageDialog(null, "Se guardaron los cambios correctamente.");
						this.ventanaLocalidad.dispose();
					}

				} else {
					JOptionPane.showMessageDialog(this.ventanaLocalidad, "Por favor, complete todos los campos.", "Completar campos", JOptionPane.ERROR_MESSAGE, null);
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	private boolean camposCompletos() {
		return this.ventanaLocalidad.getTxtCP() > 999 && !this.ventanaLocalidad.getTxtNombre().isEmpty() && !this.ventanaLocalidad.getTfProvincia().getText().isEmpty();
	}

	public LocalidadDTO getElegida() {
		return elegida;
	}

	public void setElegida(LocalidadDTO elegida) {
		this.elegida = elegida;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == this.ventanaLocalidad.getComboLocalidades()) {
			this.ventanaLocalidad.actualizarCampos((LocalidadDTO) this.ventanaLocalidad.getComboLocalidades().getSelectedItem());
		}
	}

}
