package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import dto.ZonaDTO;
import modelo.Modelo;
import presentacion.ventanas.cliente.VentanaZonas;

public class ControladorZonas implements ActionListener, ItemListener {

	private Modelo modelo;
	private VentanaZonas ventanaZonas;
	private ZonaDTO elegida;

	public ControladorZonas(VentanaZonas ventana, Modelo modelo) {

		iniciar(ventana, modelo);

		this.ventanaZonas.setTitle("Editar Zonas");
		this.ventanaZonas.setVisible(true);
	}

	private void iniciar(VentanaZonas ventana, Modelo modelo) {
		this.setVentanaZonas(ventana);
		this.ventanaZonas.setLocationRelativeTo(null);
		this.setModelo(modelo);

		this.ventanaZonas.getOkButton().addActionListener(this);
		this.ventanaZonas.getCancelButton().addActionListener(this);
		this.ventanaZonas.getComboZonas().addItemListener(this);

		try {
			this.ventanaZonas.setZonas(this.modelo.obtenerZonas());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public VentanaZonas getVentanaZonas() {
		return ventanaZonas;
	}

	public void setVentanaZonas(VentanaZonas ventana) {
		this.ventanaZonas = ventana;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.ventanaZonas.getCancelButton()) {
			this.ventanaZonas.dispose();

		} else if (e.getSource() == this.ventanaZonas.getOkButton()) {

			this.elegida = this.ventanaZonas.getZona();

			this.elegida.setNombre(this.ventanaZonas.getTxtDescripcion().getText());
			this.elegida.setPrecio(Double.parseDouble(this.ventanaZonas.getTxtPrecio().getText()));

			try {
				this.modelo.actualizarZona(this.elegida);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			this.ventanaZonas.dispose();
		}

	}

	public ZonaDTO getElegida() {
		return elegida;
	}

	public void setElegida(ZonaDTO elegida) {
		this.elegida = elegida;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		if (e.getSource() == this.ventanaZonas.getComboZonas()) {

			ZonaDTO seleccionada = (ZonaDTO) this.ventanaZonas.getComboZonas().getSelectedItem();

			cargarDatos(seleccionada);
		}
	}

	public void cargarDatos(ZonaDTO z) {
		try {
			for (int i = 0; i < this.modelo.obtenerZonas().size(); i++) {
				if (this.modelo.obtenerZonas().get(i).getId() == z.getId()) {
					this.ventanaZonas.getTxtDescripcion().setText(z.getNombre());
					this.ventanaZonas.getTxtPrecio().setText("" + z.getPrecio());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
