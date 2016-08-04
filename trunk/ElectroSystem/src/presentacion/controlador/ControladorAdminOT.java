package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import dto.ClienteDTO;
import dto.ElectrodomesticoDTO;
import dto.EstadoDTO;
import dto.LocalidadDTO;
import dto.MarcaDTO;
import dto.OrdenDTO;
import dto.UsuarioDTO;
import dto.ZonaDTO;
import modelo.Modelo;
import modelo.Reporte;
import presentacion.ventanas.cliente.NuevaLocalidad;
import presentacion.ventanas.cliente.VentanaAltaModifCliente;
import presentacion.ventanas.cliente.VentanaBuscadorCliente;
import presentacion.ventanas.ed.VentanaAltaModifElectrod;
import presentacion.ventanas.mail.VentanaMail;
import presentacion.ventanas.ot.VentanaAdminOT;
import presentacion.ventanas.ot.VentanaNuevoDomicilioDeEntrega;

public class ControladorAdminOT implements ActionListener, ItemListener {

	private static final String PRESUPUESTADA = "Presupuestada";
	private static final String ENTREGADA = "Entregada";
	private static final String DESPACHADA = "Despachada";
	private static final String REPARADA = "Reparada";
	private static final String IRREPARABLE = "Irreparable";
	private static final String EN_ESPERA_DE_PIEZAS = "En espera de piezas";
	private static final String DESAPROBADA = "Desaprobada";
	private static final String APROBADA = "Aprobada";
	private static final String INGRESADA = "Ingresada";

	private Modelo modelo;
	private VentanaAdminOT ventana;
	private VentanaAltaModifElectrod ventanaElectrod;
	private VentanaBuscadorCliente ventanaBuscador;
	private VentanaNuevoDomicilioDeEntrega ventanaDomicilio;
	private List<ElectrodomesticoDTO> electrodomesticos;
	private List<MarcaDTO> marcas;
	private ClienteDTO clienteAsociado;
	private VentanaAltaModifCliente ventanaCliente;
	private VentanaMail ventanaMail;
	private OrdenDTO orden;
	private UsuarioDTO usuario;
	private LocalidadDTO localidadEntrega;
	private String domicilioEntrega;

	public ControladorAdminOT(VentanaAdminOT ventana, Modelo modelo, UsuarioDTO usuarioLogueado) {
		this.ventana = ventana;
		this.ventana.setLocationRelativeTo(null);
		this.modelo = modelo;
		this.usuario = usuarioLogueado;
		try {
			this.marcas = modelo.obtenerMarcas();
			this.electrodomesticos = modelo.obtenerElectrodomesticos();
		} catch (Exception e) {
			e.printStackTrace();
		}
		iniciarAlta();
	}

	public ControladorAdminOT(VentanaAdminOT ventana, Modelo modelo, ClienteDTO cliente, UsuarioDTO usuarioLogueado) {
		this.setVentana(ventana);
		this.ventana.setLocationRelativeTo(null);
		this.setModelo(modelo);
		this.usuario = usuarioLogueado;
		this.clienteAsociado = cliente;
		try {
			this.electrodomesticos = modelo.obtenerElectrodomesticos();
		} catch (Exception e) {
			e.printStackTrace();
		}
		iniciarAltaConCliente();
	}

	public ControladorAdminOT(VentanaAdminOT ventana, Modelo modelo, OrdenDTO orden, UsuarioDTO usuarioLogueado, boolean busqueda) {

		if (!busqueda) {
			this.ventana = ventana;
			this.ventana.setLocationRelativeTo(null);
			this.modelo = modelo;
			this.usuario = usuarioLogueado;
			this.clienteAsociado = orden.getCliente();
			this.orden = orden;
			this.domicilioEntrega = orden.getDomicilioEntrega();
			this.localidadEntrega = orden.getLocalidadEntrega();
			iniciarVer();

		} else {
			this.ventana = ventana;
			this.ventana.setLocationRelativeTo(null);
			this.modelo = modelo;
			this.usuario = usuarioLogueado;
			this.clienteAsociado = orden.getCliente();
			this.orden = orden;
			this.domicilioEntrega = orden.getDomicilioEntrega();
			this.localidadEntrega = orden.getLocalidadEntrega();
			iniciarReadOnly();
		}

	}

	private void iniciarAlta() {
		this.ventana.getBtnCrear().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnAgregar().addActionListener(this);
		this.ventana.getBtnBuscar().addActionListener(this);
		this.ventana.getBtnAgregarElectrodomestico().addActionListener(this);
		this.ventana.getButtonEnvio().addActionListener(this);
		this.ventana.getButtonRetiro().addActionListener(this);
		this.ventana.getBtnCambiarDomicilio().addActionListener(this);
		this.ventana.getComboMarca().addItemListener(this);
		this.ventana.getComboNombre().addItemListener(this);
		this.ventana.getComboModelo().addItemListener(this);
		this.ventana.getComboNombre().setEnabled(false);
		this.ventana.getComboModelo().setEnabled(false);
		this.ventana.getTxtUsuario().setText(usuario.toString());
		this.ventana.getButtonRetiro().setSelected(true);
		this.ventana.getButtonEnvio().setEnabled(false);
		cargarComboBoxMarcas();
		this.ventana.setVisible(true);
	}

	private void iniciarAltaConCliente() {
		this.ventana.getBtnCrear().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnAgregarElectrodomestico().addActionListener(this);
		this.ventana.getBtnCambiarDomicilio().addActionListener(this);
		this.ventana.getButtonEnvio().addActionListener(this);
		this.ventana.getButtonRetiro().addActionListener(this);
		this.ventana.getComboMarca().addItemListener(this);
		this.ventana.getComboNombre().addItemListener(this);
		this.ventana.getComboModelo().addItemListener(this);
		this.ventana.getComboNombre().setEnabled(false);
		this.ventana.getComboModelo().setEnabled(false);
		this.ventana.getTxtUsuario().setText(usuario.toString());
		this.ventana.getButtonRetiro().setSelected(true);
		cargarComboBoxMarcas();
		this.ventana.getBtnBuscar().setEnabled(false);
		this.ventana.getBtnAgregar().setEnabled(false);
		this.ventana.setTxtCliente(this.clienteAsociado.getApellido() + ", " + this.clienteAsociado.getNombre());
		habilitarEnvio();
		this.ventana.setVisible(true);
	}

	private void iniciarVer() {
		this.ventana.setTitle("Orden número " + orden.getIdOT());
		this.ventana.getBtnCrear().setText("Aceptar");
		this.ventana.getBtnCrear().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnEnviarPorMail().addActionListener(this);
		this.ventana.getBtnAgregarElectrodomestico().setVisible(false);
		this.ventana.getBtnCambiarDomicilio().addActionListener(this);
		this.ventana.getButtonEnvio().addActionListener(this);
		this.ventana.getButtonRetiro().addActionListener(this);
		this.ventana.getBtnImprimirOrden().addActionListener(this);
		this.ventana.getBtnImprimirOrden().setVisible(true);
		this.ventana.getTxtUsuario().setText(usuario.toString());
		this.ventana.getBtnEnviarPorMail().setVisible(true);
		this.ventana.getBtnGenerarTicket().addActionListener(this);
		this.ventana.getBtnGenerarTicket().setVisible(true);
		this.ventana.esconderBotones();
		if (orden.isEsDelivery())
			this.ventana.getLblCostoDeEnvio().setText("Costo de envío: $ " + this.orden.getLocalidadEntrega().getZonaDeEnvio().getPrecio());
		cargarCampos(orden);
		cargarEstados();
		cargarOpcionesDeEnvio();
		this.ventana.setVisible(true);
	}

	public void iniciarReadOnly() {
		this.ventana.setTitle("Orden número " + orden.getIdOT());
		this.ventana.getBtnCancelar().setText("OK");
		this.ventana.getBtnCancelar().addActionListener(this);

		this.ventana.getBtnCrear().setVisible(false);

		this.ventana.getBtnEnviarPorMail().setVisible(false);
		this.ventana.getBtnAgregarElectrodomestico().setVisible(false);
		this.ventana.getBtnCambiarDomicilio().setVisible(false);
		this.ventana.getButtonEnvio().setVisible(false);
		this.ventana.getButtonRetiro().setVisible(false);

		this.ventana.getBtnImprimirOrden().addActionListener(this);
		this.ventana.getBtnImprimirOrden().setVisible(true);

		this.ventana.getTxtUsuario().setText(usuario.toString());
		this.ventana.getBtnEnviarPorMail().setVisible(false);

		this.ventana.getBtnGenerarTicket().addActionListener(this);
		this.ventana.getBtnGenerarTicket().setVisible(true);

		this.ventana.esconderBotones();
		if (orden.isEsDelivery())
			this.ventana.getLblCostoDeEnvio().setText("Costo de envío: $ " + this.orden.getLocalidadEntrega().getZonaDeEnvio().getPrecio());
		cargarCampos(orden);
		cargarEstados();
		cargarOpcionesDeEnvio();
		this.ventana.setVisible(true);
	}

	public void cargarCampos(OrdenDTO orden) {
		this.ventana.setTxtElectrodomestico(orden.getElectrodomestico().getDescripcion() + " - " + orden.getElectrodomestico().getMarca() + " - " + orden.getElectrodomestico().getModelo());
		this.ventana.setTxtDetalle(orden.getDescripcion());
		this.ventana.setTxtUsuario(orden.getUsuarioAlta().getApellido() + ", " + orden.getUsuarioAlta().getNombre());
		this.ventana.setTxtCliente(orden.getCliente().toString());
		this.ventana.setTxtDetalle(orden.getDescripcion());
		
		if (orden.getCliente().getTelefono() != null)
			this.ventana.setTxtTelefono(orden.getCliente().getTelefono());
		if (orden.getCliente().geteMail() != null)
			this.ventana.setTxtMail(orden.getCliente().geteMail());		

		if (orden.isEsDelivery()) {
			this.ventana.getButtonEnvio().setSelected(true);
			this.ventana.getLblDomicilio().setEnabled(true);
			this.ventana.getTxtDomicilio().setEnabled(true);
			this.ventana.getTxtDomicilio().setText(this.domicilioEntrega + " - " + this.localidadEntrega + ", "	+ this.localidadEntrega.getProvincia());
			this.ventana.getTxtCostoEnvio().setText(clienteAsociado.getLocalidad().getZonaDeEnvio().getPrecio() + "");
		} else {
			this.ventana.getButtonRetiro().setSelected(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.ventana.getBtnAgregarElectrodomestico()) {
			this.ventanaElectrod = new VentanaAltaModifElectrod(this.ventana);
			ControladorElectrodomestico controlElectrod = new ControladorElectrodomestico(this.ventanaElectrod, this.modelo);
			if (controlElectrod.getElectrodomestico() != null) {
				try {
					actualizarDatosMarcaElectrodomestico();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "No se han podido cargar las marcas o los electrodomesticos.", "¡Atención!", JOptionPane.ERROR_MESSAGE);
				}
				try {
					seleccionarElectrodomesticoAgregado(controlElectrod.getElectrodomestico());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "No se han podido cargar las marcas.", "¡Atención!", JOptionPane.ERROR_MESSAGE);
				}
			}

		} else if (e.getSource() == this.ventana.getBtnBuscar()) {

			this.ventanaBuscador = new VentanaBuscadorCliente(this.ventana);
			ControladorBuscadorCliente controladorBuscador = new ControladorBuscadorCliente(this.ventanaBuscador, this.modelo);


			if (controladorBuscador.getClienteSeleccionado() != null) {
				this.clienteAsociado = controladorBuscador.getClienteSeleccionado();
				this.ventana.setTxtCliente(this.clienteAsociado.getApellido() + ", " + this.clienteAsociado.getNombre());
				if (clienteAsociado.getTelefono() != null)
					this.ventana.setTxtTelefono(this.clienteAsociado.getTelefono());
				if (clienteAsociado.geteMail() != null)
					this.ventana.setTxtMail(this.clienteAsociado.geteMail());

				habilitarEnvio();
				this.ventana.getBtnBuscar().setEnabled(false);
				this.ventana.getBtnAgregar().setEnabled(false);
			}

		} else if (e.getSource() == this.ventana.getButtonEnvio()) {
			
			this.ventana.mostrarOpcionesEnvio();
			if (this.localidadEntrega == null) {
				if (clienteAsociado.getLocalidad() != null) {
					this.ventana.setTxtDomicilio(
							this.clienteAsociado.getDireccion() + " - " + this.clienteAsociado.getLocalidad() + ", "
									+ this.clienteAsociado.getLocalidad().getProvincia());
					this.ventana.getTxtCostoEnvio().setText(clienteAsociado.getLocalidad().getZonaDeEnvio().getPrecio() + "");

				} else {
					llamarVentanaNuevoDomicilio();
				}
			} else {
				this.ventana.setTxtDomicilio(this.domicilioEntrega + " - " + this.localidadEntrega + ", " + this.localidadEntrega.getProvincia());
				this.ventana.getLblCostoDeEnvio().setText("Costo de envío: $ " + this.localidadEntrega.getZonaDeEnvio().getPrecio());
			}
			if (orden != null) {
				// this.orden.setEsDelivery(true);
				this.orden.setCostoDeEnvio(this.orden.getLocalidadEntrega().getZonaDeEnvio().getPrecio());
				cargarEstados();
			}

		} else if (e.getSource() == this.ventana.getButtonRetiro()) {
			this.ventana.esconderOpcionesEnvio();
			this.ventana.getLblCostoDeEnvio().setText("");
			if (orden != null) {
				// this.orden.setEsDelivery(false);
				this.orden.setCostoDeEnvio(0.0);
				cargarEstados();
			}

		} else if (e.getSource() == this.ventana.getBtnCrear()) {
			if (this.orden == null)
				try {
					if (validarCampos()) {
						crearOT();
						JOptionPane.showMessageDialog(this.ventana, "Orden generada correctamente.", "Nueva orden", JOptionPane.INFORMATION_MESSAGE);
						prepararBotonReporte();
					} else {
						JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(null, "Ocurrió un error " + exc.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
					this.ventana.dispose();
				}
			else {
				try {

					// if (orden.getEstado().getId() != 1) {//TODO: Hay que controlar estados, pero no este.
					if (orden.isEsDelivery() != ventana.isDelivery())
						orden.setEsDelivery(ventana.isDelivery());
					modelo.actualizarEnvioOT(orden);
					// }
					cambiarEstado();
				} catch (Exception ex) {
					ex.printStackTrace(); // TODO: Lanzar error a la vista y que
											// muestre un cartel bonito
				}
				this.ventana.dispose();
			}

		} else if (e.getSource() == this.ventana.getBtnCancelar()) {
			this.ventana.dispose();

		} else if (e.getSource() == this.ventana.getBtnAgregar()) {
			ventanaCliente = new VentanaAltaModifCliente(ventana);
			try {
				ControladorCliente controladorCliente = new ControladorCliente(ventanaCliente, modelo);
				if (controladorCliente.getCliente() != null) {
					this.clienteAsociado = controladorCliente.getCliente();
					this.ventana.setTxtCliente(this.clienteAsociado.getApellido() + ", " + this.clienteAsociado.getNombre());
					habilitarEnvio();
					this.ventana.getBtnBuscar().setEnabled(false);
					this.ventana.getBtnAgregar().setEnabled(false);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		} else if (e.getSource() == this.ventana.getBtnEnviarPorMail()) {
			Reporte reporte = new Reporte();
			try {
				reporte.ReporteOtGeneral(orden);
				reporte.adjunto();
				String ruta = "C:/ProgramData/LabSW/20161/ServiceG1/reporte.pdf";
				ventanaMail = new VentanaMail(this.ventana);
				new ControladorMail(ventanaMail, modelo, orden, ruta);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		} else if (e.getSource() == this.ventana.getBtnCambiarDomicilio()) {

			if (this.clienteAsociado != null) {

				llamarVentanaNuevoDomicilio();

			} else {
				JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente para cambiar el domicilio.");
			}

		} else if (e.getSource() == this.ventana.getBtnGenerarTicket()) {
			Reporte reporte = new Reporte();
			try {
				reporte.ReporteOtPresupuestada(orden);
				reporte.mostrarDesdeDialog(ventana, "Ticket para el cliente y para el técnico");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		} else if (e.getSource() == this.ventana.getBtnImprimirOrden()) {
			Reporte reporte = new Reporte();
			try {
				reporte.ReporteOtGeneral(orden);
				reporte.mostrarDesdeDialog(ventana, "Orden de trabajo");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		} else if (ventanaDomicilio != null && e.getSource() == this.ventanaDomicilio.getBtnNuevaLocalidad()) {

			NuevaLocalidad ventanaLocalidad = new NuevaLocalidad(this.ventana);
			ControladorLocalidad controladorLocalidad = new ControladorLocalidad(ventanaLocalidad, this.modelo, false);

			if (controladorLocalidad.getElegida() != null && !controladorLocalidad.getElegida().getNombre().equals("(seleccionar)")) {
				this.ventanaDomicilio.getComboLocalidades().addItem(controladorLocalidad.getElegida());
				this.ventanaDomicilio.getComboLocalidades().setSelectedItem(controladorLocalidad.getElegida());
			}

		} else if (ventanaDomicilio != null && e.getSource() == this.ventanaDomicilio.getOkButton()) {
			if (!this.ventanaDomicilio.getComboLocalidades().getSelectedItem().toString().equals("(seleccionar)") && !this.ventanaDomicilio.getTextField().getText().isEmpty()) {
				this.domicilioEntrega = this.ventanaDomicilio.getTextField().getText();
				this.localidadEntrega = (LocalidadDTO) this.ventanaDomicilio.getComboLocalidades().getSelectedItem();
				if (this.orden != null) {
					this.orden.setDomicilioEntrega(domicilioEntrega);
					this.orden.setLocalidadEntrega(localidadEntrega);
					try {
						modelo.actualizarDomicilioEntrega(this.orden);
						JOptionPane.showMessageDialog(null, "Se ha modificado el domicilio de entrega correctamente.", "Modificación de domicilio de entrega correcta", JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "No se pudo actualizar el domicilio de entrega.", "Error", JOptionPane.WARNING_MESSAGE);
					}
				}
				this.ventana.setTxtDomicilio(this.domicilioEntrega + " - " + this.localidadEntrega + ", " + this.localidadEntrega.getProvincia());
				if (this.ventana.getRbEnvio().isSelected())
					this.ventana.getLblCostoDeEnvio().setText("Costo de envío: $ " + this.localidadEntrega.getZonaDeEnvio().getPrecio());
				this.ventanaDomicilio.dispose();
				this.ventanaDomicilio = null;
			} else {
				JOptionPane.showMessageDialog(null, "Por favor verifique todos los campos", "Faltan datos", JOptionPane.WARNING_MESSAGE);
			}

		} else if (ventanaDomicilio != null && e.getSource() == this.ventanaDomicilio.getCancelButton()) {
			this.ventanaDomicilio.dispose();
			this.ventanaDomicilio = null;
			if (clienteAsociado != null && clienteAsociado.getLocalidad() == null)
				this.ventana.getRbRetiro().setSelected(true);
		}
	}

	private void llamarVentanaNuevoDomicilio() {
		try {

			ventanaDomicilio = new VentanaNuevoDomicilioDeEntrega(this.ventana);
			this.ventanaDomicilio.getComboLocalidades().addItem(new LocalidadDTO(0, "(seleccionar)", new ZonaDTO(0, null, 0), null));
			this.ventanaDomicilio.getBtnNuevaLocalidad().addActionListener(this);
			this.ventanaDomicilio.getOkButton().addActionListener(this);
			this.ventanaDomicilio.getCancelButton().addActionListener(this);
			for (int i = 0; i < this.modelo.obtenerLocalidades().size(); i++) {
				this.ventanaDomicilio.getComboLocalidades().addItem(this.modelo.obtenerLocalidades().get(i));
			}
			this.ventanaDomicilio.setVisible(true);
		} catch (Exception e) {
			// TODO: handle exception
		}
		this.ventanaDomicilio.setVisible(true);
	}

	private void prepararBotonReporte() {
		this.ventana.getBtnCancelar().setText("Cerrar");
		this.ventana.getBtnCrear().setVisible(false);
		this.ventana.getBtnGenerarTicket().setVisible(true);// TODO
		this.ventana.getBtnAgregarElectrodomestico().setVisible(false);
		this.ventana.getBtnBuscar().setEnabled(false);
		this.ventana.getBtnAgregar().setEnabled(false);
		this.ventana.setTxtElectrodomestico(orden.getElectrodomestico().getDescripcion() + " - " + orden.getElectrodomestico().getMarca() + " - " + orden.getElectrodomestico().getModelo());
		this.ventana.esconderBotones();
		this.ventana.getBtnGenerarTicket().addActionListener(this);
	}

	public OrdenDTO getOrden() {
		return orden;
	}

	private void cargarEstados() {
		this.ventana.getCbEstado().setVisible(true);
		this.ventana.getLblEstado().setVisible(true);
		this.ventana.getCbEstado().removeAllItems();
		this.ventana.getCbEstado().addItem(this.orden.getEstado());
		if (this.orden.getEstado().getNombre().equals(PRESUPUESTADA)) {
			if (orden.getVencPresup().compareTo(Calendar.getInstance()) != 1) {
				// TODO: Se paso de la fecha de presupuesto, se debe
				// represupuestar.
				this.ventana.getLblCaducado().setVisible(true);
				// TODO: Avisar al cliente, si se envia el mail pasar a
				// ingresada y cerrar la ventana.
				this.ventana.getCbEstado().addItem(new EstadoDTO(1, INGRESADA));
			} else {
				this.ventana.getCbEstado().addItem(new EstadoDTO(3, APROBADA));
				this.ventana.getCbEstado().addItem(new EstadoDTO(4, DESAPROBADA));
			}

		} else if (this.orden.getEstado().getNombre().equals(EN_ESPERA_DE_PIEZAS)) {
			this.ventana.getCbEstado().addItem(new EstadoDTO(8, IRREPARABLE));

		} else if (this.orden.getEstado().getNombre().equals(REPARADA)) {
			if (!this.ventana.isDelivery())
				this.ventana.getCbEstado().addItem(new EstadoDTO(10, ENTREGADA));
		}
	}

	private void cambiarEstado() throws Exception {
		try {
			if (!this.orden.getEstado().equals((EstadoDTO) ventana.getCbEstado().getSelectedItem())) {
				modelo.actualizarEstado(orden, ((EstadoDTO) ventana.getCbEstado().getSelectedItem()).getId());
			}
		} catch (Exception ex) {
			throw ex;
		}
	}

	private boolean validarCampos() {
		try {
			if (this.ventana.getTxtCliente().getText().isEmpty() || this.ventana.getTxtDetalle().isEmpty() || this.ventana.getComboModelo().getSelectedIndex() == -1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	private void cargarOpcionesDeEnvio() {
		int estado = orden.getEstado().getId();
		if (estado == 4 || estado == 8 || estado == 9 || estado == 10) {
			ventana.getBtnCambiarDomicilio().setVisible(false);
			ventana.getButtonEnvio().setVisible(false);
			ventana.getButtonRetiro().setVisible(false);
			ventana.esconderOpcionesEnvio();
		}
	}

	private void habilitarEnvio() {
		this.ventana.getRbEnvio().setEnabled(true);
		if (this.clienteAsociado.getDireccion() != null)
			this.ventana.getRbRetiro().setEnabled(true);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == this.ventana.getComboMarca() && this.ventana.getComboMarca().getSelectedItem() != null) {
			this.ventana.getComboNombre().setEnabled(true);
			this.ventana.getComboModelo().setEnabled(false);
			cargarComboBoxNombre();

		} else if (e.getSource() == this.ventana.getComboNombre()) {
			this.ventana.getComboModelo().setEnabled(true);
			cargarComboBoxModelo();
		}
	}

	private void actualizarDatosMarcaElectrodomestico() throws Exception {
		try {
			this.marcas = this.modelo.obtenerMarcas();
			this.electrodomesticos = this.modelo.obtenerElectrodomesticos();
		} catch (Exception e) {
			throw e;
		}
	}

	private void cargarComboBoxMarcas() {
		this.ventana.getComboMarca().removeAllItems();
		try {
			this.marcas = modelo.obtenerMarcas();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (MarcaDTO m : marcas) {
			this.ventana.getComboMarca().addItem(m);
		}
	}

	private void cargarComboBoxNombre() {
		this.ventana.getComboNombre().removeAllItems();
		if (electrodomesticos != null) {
			for (ElectrodomesticoDTO e : electrodomesticos) {
				if (e.getMarca().equals(((MarcaDTO) this.ventana.getComboMarca().getSelectedItem()))) {
					this.ventana.getComboNombre().addItem(e.getDescripcion());
				}
			}
		}
	}

	private void cargarComboBoxModelo() {
		this.ventana.getComboModelo().removeAllItems();
		if (electrodomesticos != null && this.ventana.getComboNombre().getSelectedItem() != null) {
			for (ElectrodomesticoDTO e : electrodomesticos) {
				if (e.getMarca().equals(((MarcaDTO) this.ventana.getComboMarca().getSelectedItem())) && e.getDescripcion().equals(this.ventana.getComboNombre().getSelectedItem()))
					this.ventana.getComboModelo().addItem(e);
			}
		}
	}

	private void seleccionarElectrodomesticoAgregado(ElectrodomesticoDTO ed) {
		cargarComboBoxMarcas();
		this.ventana.getComboMarca().getModel().setSelectedItem(ed.getMarca());
		this.ventana.getComboNombre().getModel().setSelectedItem(ed.getDescripcion());
		this.ventana.getComboModelo().getModel().setSelectedItem(ed);
	}

	private void crearOT() {
		OrdenDTO nuevaOrden = new OrdenDTO(0, clienteAsociado, ((ElectrodomesticoDTO) this.ventana.getComboModelo().getSelectedItem()), this.ventana.getTxtDetalle(), this.usuario, this.ventana.isDelivery());
		System.out.println(clienteAsociado);

		if (this.localidadEntrega == null) {
			if (nuevaOrden.getCliente().getLocalidad() != null) {
				nuevaOrden.setDomicilioEntrega(nuevaOrden.getCliente().getDireccion());
				nuevaOrden.setLocalidadEntrega(nuevaOrden.getCliente().getLocalidad());
			} else {
				nuevaOrden.setDomicilioEntrega("");
				nuevaOrden.setLocalidadEntrega(new LocalidadDTO(0, null, null, null));
			}
		} else {
			nuevaOrden.setDomicilioEntrega(this.domicilioEntrega);
			nuevaOrden.setLocalidadEntrega(this.localidadEntrega);
		}
		if (nuevaOrden.isEsDelivery())
			nuevaOrden.setCostoDeEnvio(nuevaOrden.getLocalidadEntrega().getZonaDeEnvio().getPrecio());
		try {
			this.modelo.agregarOrden(nuevaOrden);
			this.orden = nuevaOrden;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<ElectrodomesticoDTO> getElectrodomesticos() {
		return electrodomesticos;
	}

	public void setElectrodomesticos(List<ElectrodomesticoDTO> electrodomesticos) {
		this.electrodomesticos = electrodomesticos;
	}

	private void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	private void setVentana(VentanaAdminOT ventana) {
		this.ventana = ventana;
	}

	public VentanaAdminOT getVentana() {
		return ventana;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public ClienteDTO getClienteAsociado() {
		return clienteAsociado;
	}

	public void setClienteAsociado(ClienteDTO clienteAsociado) {
		this.clienteAsociado = clienteAsociado;
	}
}
