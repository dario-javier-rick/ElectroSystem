package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dto.MarcaDTO;
import dto.PiezaDTO;
import dto.PrecioPiezaDTO;
import dto.ProveedorDTO;
import dto.SolicitudCompraDTO;
import dto.UsuarioDTO;
import modelo.Modelo;
import modelo.Reporte;
import presentacion.controlador.button.column.ButtonColumn;
import presentacion.ventanas.mail.VentanaMail;
import presentacion.ventanas.sc.VentanaNuevaSC;

public class ControladorSC implements ActionListener, ItemListener {

	private VentanaNuevaSC ventana;
	private Modelo modelo;
	private List<ProveedorDTO> proveedores;
	private List<MarcaDTO> marcas;
	private List<PrecioPiezaDTO> piezasSolicitud;
	private ArrayList<PrecioPiezaDTO> piezasSolicitudLocal;
	private DefaultTableModel tabla_de_items;
	private SolicitudCompraDTO solicitud_a_editar;
	private float preciototal = 0;
	private int cantidadPiezas = 0;

	private VentanaMail ventanaMail;
	private UsuarioDTO usuario;

	private final ProveedorDTO proveedorGenerico = new ProveedorDTO(-1, SOLICITUD_GENERICA_PROVEEDOR, null, null, null, null, marcas);

	public ControladorSC(VentanaNuevaSC ventana, Modelo modelo, UsuarioDTO usuario) {
		this.setVentana(ventana);
		this.ventana.setLocationRelativeTo(null);
		this.setModelo(modelo);
		this.usuario = usuario;

		try {
			cargarCboProveedores();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, NO_SE_HAN_PODIDO_CARGAR_LOS_PROVEEDORES, ATENCION, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		this.piezasSolicitud = new ArrayList<PrecioPiezaDTO>();
		this.piezasSolicitudLocal = new ArrayList<PrecioPiezaDTO>();
		iniciarSolicitud();
	}

	public ControladorSC(VentanaNuevaSC ventana, Modelo modelo, SolicitudCompraDTO sc, boolean editar, UsuarioDTO usuario) {

		this.setVentana(ventana);
		this.ventana.setLocationRelativeTo(null);
		this.setModelo(modelo);
		this.usuario = usuario;

		this.marcas = sc.getProveedor().getMarcas();
		this.solicitud_a_editar = sc;
		this.piezasSolicitud = solicitud_a_editar.getPiezas();
		this.piezasSolicitudLocal = new ArrayList<PrecioPiezaDTO>(piezasSolicitud.size());
		for (PrecioPiezaDTO item : piezasSolicitud)
			piezasSolicitudLocal.add(item);
		String nombreEstado = solicitud_a_editar.getEstado().getNombre();
		this.ventana.getCboProveedores().addItem(this.solicitud_a_editar.getProveedor());

		this.ventana.getBtnCancelar().addActionListener(this);

		if (!editar) {
			verSolicitud();
		} else {
			this.ventana.getBtnAgregarPiezas().setText("Corregir pieza");
			if (nombreEstado.equals(ESTADO_INGRESADA)) {
				iniciarEdicionSolicitud();
			} else if (nombreEstado.equals(ESTADO_ENVIADA)) {
				iniciarProcesarSolicitud();
			}
		}

	}

	private void verSolicitud() {

		this.ventana.setTitle(SOLICITUD_NUMERO + this.solicitud_a_editar.getId());
		this.ventana.getTxtUsuario().setText(usuario.toString());

		this.ventana.getBtnAgregarPiezas().setVisible(false);
		this.ventana.getBtnCancelar().setText(BUTTON_NAME_OK);
		this.ventana.getBtnEnviar().setVisible(true);
		this.ventana.getBtnSolicitar().setVisible(false);

		this.ventana.getCboProveedores().setVisible(false);
		this.ventana.setTxtProveedor(solicitud_a_editar.getProveedor().getNombre());
		this.ventana.getTxtProveedor().setVisible(true);

		this.ventana.getLblPieza().setVisible(false);
		this.ventana.getCbMarca().setVisible(false);
		this.ventana.getCbPiezas().setVisible(false);
		this.ventana.getTfCantidad().setVisible(false);

		this.ventana.getBtnImprimir().addActionListener(this);
		this.ventana.getBtnEnviar().addActionListener(this);

		cargarItems();
		getCantidadPiezas(this.solicitud_a_editar);
		this.ventana.setVisible(true);
	}

	private void iniciarProcesarSolicitud() {

		this.ventana.setTitle("Procesar solicitud nro.: " + this.solicitud_a_editar.getId());
		this.ventana.getTxtUsuario().setText(usuario.toString());
		getCantidadPiezas(this.solicitud_a_editar);

		JButton btnSolicitar = this.ventana.getBtnSolicitar();
		btnSolicitar.setText(BUTTON_NAME_PROCESAR);
		preparedView(btnSolicitar);

	}

	private void preparedView(JButton btnSolicitar) {
		btnSolicitar.addActionListener(this);
		this.ventana.getBtnModificar().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnAgregarPiezas().addActionListener(this);
		this.ventana.getCbMarca().addItemListener(this);
		this.ventana.getCboProveedores().addItemListener(this);
		this.ventana.getBtnEnviar().addActionListener(this);
		this.ventana.getBtnImprimir().addActionListener(this);

		try {
			cargarCombosMarcas((ProveedorDTO) this.ventana.getCboProveedores().getSelectedItem());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, NO_SE_HAN_PODIDO_CARGAR_LAS_MARCAS, ATENCION, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		crearTabla();
		llenarPiezas();
		this.ventana.setVisible(true);
	}

	private void llenarPiezas() {

		for (PrecioPiezaDTO entry : piezasSolicitud) {

			PiezaDTO piezaItem = entry.getPieza();
			Object marcaItem = piezaItem.getMarca();

			int cantidadFloat = entry.getCantidad();
			float precioTotal = entry.getPrecio() * cantidadFloat;
			Object[] fila = { marcaItem, piezaItem, cantidadFloat, entry.getPrecio(), precioTotal, BUTTON_NAME_QUITAR };

			tabla_de_items.addRow(fila);
			preciototal += precioTotal;
		}
		this.ventana.setLblPrecioTotal(preciototal);

	}

	private void iniciarSolicitud() {

		this.ventana.setTitle("Nueva Solicitud de compra");
		this.ventana.getTxtUsuario().setText(usuario.toString());

		this.ventana.getBtnSolicitar().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnAgregarPiezas().addActionListener(this);
		this.ventana.getCbMarca().addItemListener(this);
		this.ventana.getCboProveedores().addItemListener(this);
		this.ventana.getBtnEnviar().setVisible(false);
		this.ventana.getBtnImprimir().setVisible(false);
		this.ventana.getBtnEnviar().addActionListener(this);

		try {
			cargarCboProveedores();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, NO_SE_HAN_PODIDO_CARGAR_LOS_PROVEEDORES, ATENCION, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		try {
			cargarCombosMarcas((ProveedorDTO) this.ventana.getCboProveedores().getSelectedItem());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, NO_SE_HAN_PODIDO_CARGAR_LAS_MARCAS, ATENCION, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		crearTabla();
		this.ventana.setVisible(true);
	}

	private void iniciarEdicionSolicitud() {
		this.ventana.setTitle("Editar solicitud nro.: " + this.solicitud_a_editar.getId());
		this.ventana.getTxtUsuario().setText(usuario.toString());
		getCantidadPiezas(this.solicitud_a_editar);

		JButton btnModificar = this.ventana.getBtnModificar();
		btnModificar.setVisible(true);

		JButton btnSolicitar = this.ventana.getBtnSolicitar();
		btnSolicitar.setText(BUTTON_NAME_PROCESAR);
		preparedView(btnSolicitar);
	}

	private void getCantidadPiezas(SolicitudCompraDTO s) {
		if (s.getId() == 0) {
			this.ventana.setLblCantidadTotal(cantidadPiezas);

		} else {
			for (int i = 0; i < s.getPiezas().size(); i++) {
				this.cantidadPiezas += solicitud_a_editar.getPiezas().get(i).getCantidad();
			}
		}

		this.ventana.setLblCantidadTotal(cantidadPiezas);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.ventana.getBtnCancelar()) {
			ventana.dispose();

		} else {
			JButton btnSolicitar = this.ventana.getBtnSolicitar();
			if (e.getSource() == btnSolicitar) {
				if (this.ventana.getCboProveedores() != null) {

					String text = btnSolicitar.getText();
					ProveedorDTO selectedItem = (ProveedorDTO) this.ventana.getCboProveedores().getSelectedItem();
					if (text.equals("Solicitar")) {

						if (this.tabla_de_items.getRowCount() != 0) {
							try {
								int count = impactarSC(selectedItem);
								if (selectedItem.getIdProveedor() == -1) {
									JOptionPane.showMessageDialog(null, String.format(SE_REALIZO_LA_SOLICITUD_DE_LA_ORDEN_DE_COMPRA2, count));
									ventana.dispose();
								} else {
									JOptionPane.showMessageDialog(null, SE_REALIZO_LA_SOLICITUD_DE_LA_ORDEN_DE_COMPRA);
									ventana.dispose();
								}
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(null, NO_SE_HA_PODIDO_CREAR_LA_SOLICITUD_DE_COMPRA, ATENCION, JOptionPane.ERROR_MESSAGE);
								ventana.dispose();
							}
						} else {
							JOptionPane.showMessageDialog(null, NO_SE_HAN_AGREGADO_PIEZAS_A_LA_SOLICITUD, ATENCION, JOptionPane.ERROR_MESSAGE);
						}

					} else if (text.equals(BUTTON_NAME_PROCESAR)) {

						try {
							solicitud_a_editar.setPiezas(piezasSolicitud);
							this.modelo.actualizarSolicitud(solicitud_a_editar);
							this.modelo.procesarSolicitud(solicitud_a_editar);
							if (!this.proveedorCumplidor()) {
								try {
									this.modelo.agregarProveedorIncumplidor(solicitud_a_editar.getProveedor());
									JOptionPane.showMessageDialog(null, SE_HA_CARGADO_UN_PROVEEDOR_INCUMPLIDOR, ATENCION, JOptionPane.INFORMATION_MESSAGE);
								} catch (Exception ex) {
									JOptionPane.showMessageDialog(null, NO_SE_PUDO_CARGAR_PROVEEDOR_INCUMPLIDOR + ex.getMessage(), ATENCION, JOptionPane.ERROR_MESSAGE);
								}
							}
							JOptionPane.showMessageDialog(null, SE_PROCESO_LA_SOLICITUD_DE_LA_ORDEN_DE_COMPRA_MESSAGE_ERROR);
							ventana.dispose();
						} catch (Exception e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, NO_SE_HA_PODIDO_PROCESAR_LA_SOLICITUD_DE_COMPRA, ATENCION, JOptionPane.ERROR_MESSAGE);
							ventana.dispose();
						}

					}
				} else {
					JOptionPane.showMessageDialog(null, NO_SE_HA_SELECCIONADO_NINGUN_PROVEEDOR_MESSAGE_ERROR, ATENCION, JOptionPane.ERROR_MESSAGE);
				}

			} else if (e.getSource() == this.ventana.getBtnModificar()) {
				if (this.ventana.getCboProveedores() != null) {

					ProveedorDTO selectedItem = (ProveedorDTO) this.ventana.getCboProveedores().getSelectedItem();
					if (this.tabla_de_items.getRowCount() != 0) {
						try {
							impactarSC(selectedItem);
							JOptionPane.showMessageDialog(null, SE_MODIFICO_LA_SOLICITUD_DE_LA_ORDEN_DE_COMPRA);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, NO_SE_HA_PODIDO_MODIFICAR_LA_SOLICITUD_DE_COMPRA, ATENCION, JOptionPane.ERROR_MESSAGE);
							ventana.dispose();
						}
					} else {
						JOptionPane.showMessageDialog(null, NO_SE_HAN_AGREGADO_PIEZAS_A_LA_SOLICITUD, ATENCION, JOptionPane.ERROR_MESSAGE);
					}
				}
			} else if (e.getSource() == this.ventana.getBtnAgregarPiezas()) {

				String cantidad = this.ventana.getTfCantidad().getValue().toString();
				int valorCantidad = Integer.parseInt(cantidad);

				if (valorCantidad > 0 && this.ventana.getCbMarca().getSelectedItem() != null && this.ventana.getCbPiezas().getSelectedItem() != null) {
					agregarItem();
				}

			} else if (e.getSource() == this.ventana.getBtnEnviar()) {
				Reporte reporte = new Reporte();
				try {
					reporte.ReporteScGeneral(this.solicitud_a_editar);
					reporte.adjunto();
					// TODO CAMBIAR RUTA ABSOLUTA A RUTA RELATIVA, estado a cambiar quemado
					String ruta = "C:/ProgramData/LabSW/20161/ServiceG1/reporte.pdf";
					this.ventanaMail = new VentanaMail(this.ventana);
					ControladorMail controladorMail = new ControladorMail(ventanaMail, this.modelo, solicitud_a_editar, ruta);

					if (controladorMail.fueEnviado() && this.solicitud_a_editar.getEstado().getId() == 1) {
						modelo.cambiarEstado(solicitud_a_editar, 2);
						JOptionPane.showMessageDialog(this.ventana, SE_HA_ENVIADO_LA_SOLICITUD_DE_COMPRA_SATISFACTORIAMENTE, EXITO, JOptionPane.INFORMATION_MESSAGE);
						ventana.dispose();
					} else {
						JOptionPane.showMessageDialog(this.ventana, NO_SE_HA_PODIDO_ENVIAR_LA_SOLICITUD_DE_COMPRA, ATENCION, JOptionPane.ERROR_MESSAGE);
						ventana.dispose();
					}

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, NO_SE_HA_PODIDO_ENVIAR_LA_SOLICITUD_DE_COMPRA, ATENCION, JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			} else if (e.getSource() == this.ventana.getBtnImprimir()) {
				Reporte reporte = new Reporte();
				reporte.ReporteScGeneral(this.solicitud_a_editar);
				reporte.mostrarDesdeDialog(this.ventana, SOLICITUD_NUMERO + this.solicitud_a_editar.getId());
			}
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
		JComboBox<MarcaDTO> cbMarca = this.ventana.getCbMarca();
		JComboBox<ProveedorDTO> cbProveedores = this.ventana.getCboProveedores();
		
		if (e.getSource() == this.ventana.getCboProveedores()) {
			this.ventana.getTable().removeAll();
			this.piezasSolicitud.removeAll(piezasSolicitud);
			this.cantidadPiezas = 0;
			
			this.preciototal = 0;
			crearTabla();
		}
		
		if (e.getSource() == cbMarca && cbMarca.getSelectedItem() != null) {
			ProveedorDTO proveedor = (ProveedorDTO) cbProveedores.getSelectedItem();
			MarcaDTO marca = (MarcaDTO) cbMarca.getSelectedItem();

			cargarComboPiezas(proveedor.getIdProveedor(), marca.getIdMarca());

		} else if (e.getSource() == cbProveedores && cbProveedores.getSelectedItem() != null) {
			cargarCombosMarcas((ProveedorDTO) cbProveedores.getSelectedItem());
		}
	}

	private int impactarSC(ProveedorDTO proveedor) throws Exception {
		int count = 0;

		if (proveedor.getIdProveedor() != -1) {
			SolicitudCompraDTO solicitudCompra = new SolicitudCompraDTO(0, (ProveedorDTO) this.ventana.getCboProveedores().getSelectedItem(), null, piezasSolicitud);
			if (solicitud_a_editar != null) {
				solicitud_a_editar.setPiezas(piezasSolicitud);
				this.modelo.actualizarSolicitud(solicitud_a_editar);
			} else
				this.modelo.agregarSolicitud(solicitudCompra, usuario.getIdPersonal());
		} else // Es SC Generica
		{
			if (solicitud_a_editar != null) {
				throw new Exception(NO_SE_PUEDEN_EDITAR_SOLICITUDES_GENERICAS);
			} else {

				Hashtable<ProveedorDTO, SolicitudCompraDTO> hashProveedorSC = new Hashtable<ProveedorDTO, SolicitudCompraDTO>();

				for (PrecioPiezaDTO pieza : piezasSolicitud) {

					ProveedorDTO prov = this.modelo.obtenerProveedorMasBarato(pieza);

					if (hashProveedorSC.containsKey(prov)) {
						hashProveedorSC.get(prov).setPieza(pieza);
					} else {
						List<PrecioPiezaDTO> listaPiezas = new ArrayList<PrecioPiezaDTO>();
						SolicitudCompraDTO solicitudCompra = new SolicitudCompraDTO(0, prov, null, listaPiezas);
						hashProveedorSC.put(prov, solicitudCompra);
						hashProveedorSC.get(prov).setPieza(pieza);
					}
				}

				for (ProveedorDTO key : hashProveedorSC.keySet()) {
					this.modelo.agregarSolicitud((SolicitudCompraDTO) hashProveedorSC.get(key), usuario.getIdPersonal());
					count++;
				}

			}
		}
		return count;
	}

	private void agregarItem() {

		PrecioPiezaDTO piezaItem = (PrecioPiezaDTO) this.ventana.getCbPiezas().getSelectedItem();
		int cantidadXU = 0;
		cantidadXU = this.ventana.getCantidad();
		PrecioPiezaDTO item = new PrecioPiezaDTO(piezaItem.getPieza(), null, cantidadXU);

		if (!piezasSolicitud.contains(item) || cantidadPiezas == 0)
			agregarPieza(piezaItem, item);
		else
			modificarPieza(piezaItem, item);
	}

	private void agregarPieza(PrecioPiezaDTO piezaItem, PrecioPiezaDTO item) {

		Object marcaItem = this.ventana.getCbMarca().getSelectedItem();
		float precioTotal = piezaItem.getPrecio() * item.getCantidad();
		Object[] fila = { marcaItem, piezaItem.getPieza(), item.getCantidad(), piezaItem.getPrecio(), precioTotal, BUTTON_NAME_QUITAR };
		tabla_de_items.addRow(fila);

		preciototal += precioTotal;
		this.ventana.setLblPrecioTotal(preciototal);
		this.ventana.getTfCantidad().setValue(1);

		piezasSolicitud.add(item);

		int cantidadPiezasNuevas = 0;
		cantidadPiezasNuevas = item.getCantidad();
		cantidadPiezas += cantidadPiezasNuevas;
		this.ventana.setLblCantidadTotal(cantidadPiezas);

	}

	private void modificarPieza(PrecioPiezaDTO piezaItem, PrecioPiezaDTO item) {

		int fila = 0;
		JTable table = this.ventana.getTable();
		for (int i = 0; i < table.getRowCount(); i++) {
			PiezaDTO pieza = (PiezaDTO) table.getValueAt(i, 1);
			if (pieza.getIdUnico().equals(item.getPieza().getIdUnico())) {
				fila = i;
			}
		}

		preciototal -= Float.parseFloat(ventana.getTable().getValueAt(fila, 4).toString());
		cantidadPiezas -= Integer.parseInt(ventana.getTable().getValueAt(fila, 2).toString());

		tabla_de_items.removeRow(fila);
		piezasSolicitud.remove(piezaItem);

		float precioTotal = piezaItem.getPrecio() * item.getCantidad();
		Object[] data = { this.ventana.getCbMarca().getSelectedItem(), piezaItem.getPieza(), item.getCantidad(), piezaItem.getPrecio(), precioTotal, BUTTON_NAME_QUITAR };
		tabla_de_items.addRow(data);
		piezasSolicitud.add(item);

		preciototal += precioTotal;
		this.ventana.setLblPrecioTotal(preciototal);
		cantidadPiezas += item.getCantidad();
		this.ventana.setLblCantidadTotal(cantidadPiezas);

		// clean spinner
		this.ventana.getTfCantidad().setValue(1);

	}

	private void cargarCboProveedores() throws Exception {
		JComboBox<ProveedorDTO> cboProveedores = this.ventana.getCboProveedores();
		cboProveedores.removeAllItems();

		this.proveedores = modelo.obtenerProveedores();
		for (ProveedorDTO p : proveedores) {
			cboProveedores.addItem(p);
		}
		cboProveedores.addItem(proveedorGenerico);

	}

//	private void cargarCombosMarcas() {
//		cargarCombosMarcas(null);
//	}

	private void cargarCombosMarcas(ProveedorDTO proveedor) {
		try {
			JComboBox<MarcaDTO> cbMarca = this.ventana.getCbMarca();
			cbMarca.removeAllItems();
			if (this.marcas == null || proveedor != null)
				if (proveedor != null && proveedor.getIdProveedor() != -1) {
					this.marcas = modelo.obtenerMarcasProveedor(proveedor.getIdProveedor());
				} else {
					this.marcas = modelo.obtenerMarcas();
					// Si es -1, es una solicitud genérica
				}
			for (MarcaDTO marca : marcas) {
				cbMarca.addItem(marca);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, NO_SE_HAN_PODIDO_CARGAR_LAS_MARCAS, ATENCION, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void cargarComboPiezas(int idProveedor, int idMarca) {
		try {
			JComboBox<PrecioPiezaDTO> cbPiezas = this.ventana.getCbPiezas();
			cbPiezas.removeAllItems();
			List<PrecioPiezaDTO> pieza = modelo.obtenerPrecioCompraItems(idProveedor, idMarca);
			for (PrecioPiezaDTO precioPieza : pieza) {
				cbPiezas.addItem(precioPieza);
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, NO_SE_HAN_PODIDO_CARGAR_LAS_PIEZAS, ATENCION, JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
	}

	private void crearTabla() {
		String[] columns = { MARCA_COLUMN, PRODUCTO_COLUMN, CANTIDAD_COLUMN, PRECIO_X_U_COLUMN, TOTAL_ITEM_COLUMN, "" };

		tabla_de_items = new DefaultTableModel(null, columns) {

			private static final long serialVersionUID = -3156823212983122206L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 5) {
					return true;
				}
				return false;
			}
		};

		tabla_de_items.setColumnIdentifiers(columns);
		this.ventana.getTable().setModel(tabla_de_items);

		new ButtonColumn(this.ventana.getTable(), quitarItem(), 5);

	}

	private void cargarItems() {

		String[] columns = { MARCA_COLUMN, PRODUCTO_COLUMN, CANTIDAD_COLUMN, PRECIO_X_U_COLUMN, TOTAL_ITEM_COLUMN };

		tabla_de_items = new DefaultTableModel(null, columns);

		tabla_de_items.setColumnIdentifiers(columns);
		this.ventana.getTable().setModel(tabla_de_items);

		for (PrecioPiezaDTO entry : piezasSolicitud) {

			PiezaDTO piezaItem = entry.getPieza();
			Object marcaItem = piezaItem.getMarca();

			int cantidadFloat = entry.getCantidad();
			float precioTotal = entry.getPrecio() * cantidadFloat;
			Object[] fila = { marcaItem, piezaItem, cantidadFloat, entry.getPrecio(), precioTotal };

			tabla_de_items.addRow(fila);
			preciototal += precioTotal;
		}
		this.ventana.setLblPrecioTotal(preciototal);
	}

	private Action quitarItem() {

		Action quitar = new AbstractAction() {

			private static final long serialVersionUID = -927641575309989379L;

			@Override
			public void actionPerformed(ActionEvent e) {

				int rowAEliminar = ventana.getTable().getSelectedRow();

				try {

					int respuesta = JOptionPane.showConfirmDialog(ventana, ESTA_SEGURO_QUE_DESEA_ELIMINAR_LA_PIEZA, null, JOptionPane.YES_NO_OPTION);

					if (respuesta == JOptionPane.YES_OPTION) {
						PiezaDTO piezaTable = (PiezaDTO) tabla_de_items.getValueAt(ventana.getTable().getSelectedRow(), 1);

						for (PrecioPiezaDTO PrecioPiezaDTO : piezasSolicitud) {
							if (PrecioPiezaDTO.getPieza().getIdProdPieza() == piezaTable.getIdProdPieza()) {
								piezasSolicitud.remove(PrecioPiezaDTO);
								break;
							}
						}

						String valorString = tabla_de_items.getValueAt(rowAEliminar, 4).toString();
						Float precioARestar = Float.valueOf(valorString);
						preciototal -= precioARestar;
						ventana.setLblPrecioTotal(preciototal);

						int cantidadRestar = 0;
						cantidadRestar = (int) tabla_de_items.getValueAt(ventana.getTable().getSelectedRow(), 2);
						cantidadPiezas -= cantidadRestar;
						ventana.setLblCantidadTotal(cantidadPiezas);

						tabla_de_items.removeRow(ventana.getTable().getSelectedRow());
						ventana.getTable().setModel(tabla_de_items);

					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		return quitar;
	}

	private void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	private void setVentana(VentanaNuevaSC ventana) {
		this.ventana = ventana;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public boolean proveedorCumplidor() {
		boolean contains = true;
		for (int i = 0; i < piezasSolicitudLocal.size(); i++) {
			if (!this.listaContienePieza(piezasSolicitudLocal.get(i))) {
				contains = false;
			}
		}
		return contains;
	}

	public boolean listaContienePieza(PrecioPiezaDTO pieza) {
		for (int i = 0; i < piezasSolicitud.size(); i++) {
			if (piezasSolicitud.get(i).getPieza().equals(pieza.getPieza()) && piezasSolicitud.get(i).getCantidad().equals(pieza.getCantidad())) {
				return true;
			}
		}
		return false;
	}

	// TODO-------------------------------------------CONSTANTES ------------------------------------------------------------------------------------------------------
	private static final String SOLICITUD_GENERICA_PROVEEDOR = "SOLICITUD GENERICA";

	private static final String SOLICITUD_NUMERO = "Solicitud de Compra Nº ";

	private static final String TOTAL_ITEM_COLUMN = "Total item";
	private static final String PRECIO_X_U_COLUMN = "Precio por u.";
	private static final String CANTIDAD_COLUMN = "Cantidad";
	private static final String PRODUCTO_COLUMN = "Producto";
	private static final String MARCA_COLUMN = "Marca";

	private static final String EXITO = "¡Exito!";
	private static final String ATENCION = "¡Atención!";

	private static final String ESTA_SEGURO_QUE_DESEA_ELIMINAR_LA_PIEZA = "¿Está seguro que desea eliminar la pieza?";

	private static final String SE_MODIFICO_LA_SOLICITUD_DE_LA_ORDEN_DE_COMPRA = "Se modificó la solicitud de la orden de compra";
	private static final String SE_REALIZO_LA_SOLICITUD_DE_LA_ORDEN_DE_COMPRA = "Se realizó la solicitud de la orden de compra";
	private static final String SE_REALIZO_LA_SOLICITUD_DE_LA_ORDEN_DE_COMPRA2 = "Se realizaron %s solicitudes de compras";
	private static final String SE_HA_ENVIADO_LA_SOLICITUD_DE_COMPRA_SATISFACTORIAMENTE = "Se ha enviado la solicitud de compra satisfactoriamente.";
	private static final String SE_PROCESO_LA_SOLICITUD_DE_LA_ORDEN_DE_COMPRA_MESSAGE_ERROR = "Se proceso la solicitud de la orden de compra";
	private static final String SE_HA_CARGADO_UN_PROVEEDOR_INCUMPLIDOR = "Se ha cargado un proveedor incumplidor.";

	private static final String NO_SE_HAN_PODIDO_CARGAR_LAS_PIEZAS = "No se han podido cargar las piezas.";
	private static final String NO_SE_PUEDEN_EDITAR_SOLICITUDES_GENERICAS = "No se pueden editar solicitudes genericas";
	private static final String NO_SE_HA_PODIDO_CREAR_LA_SOLICITUD_DE_COMPRA = "No se ha podido crear la solicitud de compra.";
	private static final String NO_SE_HA_PODIDO_MODIFICAR_LA_SOLICITUD_DE_COMPRA = "No se ha podido modificar la solicitud de compra.";
	private static final String NO_SE_HAN_AGREGADO_PIEZAS_A_LA_SOLICITUD = "No se han agregado piezas a la solicitud.";
	private static final String NO_SE_HAN_PODIDO_CARGAR_LAS_MARCAS = "No se han podido cargar las marcas.";
	private static final String NO_SE_HAN_PODIDO_CARGAR_LOS_PROVEEDORES = "No se han podido cargar los proveedores.";
	private static final String NO_SE_HA_SELECCIONADO_NINGUN_PROVEEDOR_MESSAGE_ERROR = "No se ha seleccionado ningun proveedor.";
	private static final String NO_SE_PUDO_CARGAR_PROVEEDOR_INCUMPLIDOR = "No se pudo cargar proveedor incumplidor. ";
	private static final String NO_SE_HA_PODIDO_PROCESAR_LA_SOLICITUD_DE_COMPRA = "No se ha podido procesar la solicitud de compra.";
	private static final String NO_SE_HA_PODIDO_ENVIAR_LA_SOLICITUD_DE_COMPRA = "No se ha podido enviar la solicitud de compra.";

	private static final String BUTTON_NAME_QUITAR = "QUITAR";
	private static final String BUTTON_NAME_OK = "OK";
	private static final String BUTTON_NAME_PROCESAR = "Procesar";

	private static final String ESTADO_ENVIADA = "Enviada";
	private static final String ESTADO_INGRESADA = "Ingresada";

}
