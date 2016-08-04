package presentacion.controlador.presupuesto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dto.EstadoDTO;
import dto.MarcaDTO;
import dto.OrdenDTO;
import dto.PiezaDTO;
import dto.UsuarioDTO;
import modelo.Modelo;
import presentacion.controlador.button.column.ButtonColumn;
import presentacion.ventanas.ot.VentanaTecnicoOT;

public class ControladorTecnicoOT implements ActionListener, ItemListener {

	private static final String BUTTON_NAME_USAR = "USAR";
	private static final String BUTTON_NAME_SIN_STOCK = "SIN STOCK";
	private static final String BUTTON_NAME_QUITAR = "QUITAR";

	private static final String ESTADO_OT_EN_ESPERA_DE_PIEZAS = "En espera de piezas";
	private static final String ESTADO_OT_EN_REPARACION = "En reparación";
	private static final String ESTADO_OT_REPARADA = "Reparada";
	private static final String ESTADO_OT_IRREPARABLE = "Irreparable";
	private static final String ESTADO_OT_APROBADA = "Aprobada";

	private Modelo modelo;
	private VentanaTecnicoOT ventana;
	private OrdenDTO orden;
	private UsuarioDTO usuario;
	private List<PiezaDTO> presupuestadas;
	private List<PiezaDTO> usadas;
	private float total;
	private DefaultTableModel tabla_de_piezas;

	public ControladorTecnicoOT(VentanaTecnicoOT ventana, Modelo modelo, OrdenDTO orden, UsuarioDTO usuarioLogueado) {
		this.setVentana(ventana);
		this.ventana.setLocationRelativeTo(null);
		
		this.setModelo(modelo);
		this.orden = orden;
		this.usuario = usuarioLogueado;
		this.presupuestadas = orden.getPiezasPresupuestadas();
		this.usadas = orden.getPiezasUsadas();
		if (orden.getEstado().getId() == 1) // TODO Ingresada hardcodeado
			iniciarPresupuestar();
		else
			iniciarReparar();
	}

	private void iniciarPresupuestar() {
		this.ventana.setTitle("Presupuestando orden de trabajo número: " + orden.getIdOT());
		this.ventana.getBtnCrear().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnAgregarPieza().addActionListener(this);
		this.ventana.getComboMarca().addItemListener(this);
		cargarComboBoxMarcas();
		llenarTablaPresupuesto();
		cargarOt();
		this.ventana.setVisible(true);
	}

	private void iniciarReparar() {
		this.ventana.setTitle("Reparando orden de trabajo número: " + orden.getIdOT());
		this.ventana.getBtnCrear().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnAgregarPieza().addActionListener(this);
		this.ventana.getComboMarca().addItemListener(this);
		this.ventana.getCbEstado().addActionListener(this);
		this.ventana.getBtnAgregarPieza().setText("Agregar pieza");
		cambiarVista();
		cargarComboBoxMarcas();
		cargarOt();
		cargarEstados();
		cargarTablaDeUso();
		this.ventana.setVisible(true);
	}

	private void cambiarVista() {
		this.ventana.getLblManoDeObra().setVisible(false);
		this.ventana.getTfManoDeObra().setVisible(false);
		this.ventana.getDateFechaVencimiento().setVisible(false);
		this.ventana.getLblFechaExpiraGaranta().setVisible(false);
		this.ventana.getLblTotalPresupuesto().setVisible(false);
		this.ventana.getLblEstado().setVisible(true);
		this.ventana.getCbEstado().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.ventana.getBtnAgregarPieza()) {
			this.presupuestadas.add((PiezaDTO) this.ventana.getComboPieza().getSelectedItem());
			if (orden.getEstado().getId() == 1) {
				llenarTablaPresupuesto();
				this.ventana.setLblTotalPresupuesto("Total piezas: $" + total);
			} else {
				AgregarTablaReparar();
			}
		}

		else if (e.getSource() == this.ventana.getBtnCancelar()) {
			ventana.dispose();
		}

		else if (e.getSource() == this.ventana.getBtnCrear()) {
			if (orden.getEstado().getId() == 1) {
				if (this.ventana.getDateFechaVencimiento().getModel().getValue() != null) {
					if (!this.ventana.getTfManoDeObra().getText().isEmpty()) {
						if (presupuestadas == null || presupuestadas.isEmpty()) {
							int respuesta = JOptionPane.showConfirmDialog(ventana, "No se han seleccionado piezas. ¿Desea continuar?", null, JOptionPane.YES_NO_OPTION);
							if (respuesta == JOptionPane.YES_OPTION) {
								Calendar presup = (Calendar) ventana.getDateFechaVencimiento().getModel().getValue();
								Calendar today = Calendar.getInstance();
								if (!presup.after(today)) {
									JOptionPane.showMessageDialog(null, "Por favor, ingrese una fecha superior al dia de hoy. ", "Error", JOptionPane.WARNING_MESSAGE);
									return;
								}
								crearPresupuesto();
								JOptionPane.showMessageDialog(null, "La orden " + orden.getIdOT() + " se ha presupuestado correctamente", "Orden presupuestada", JOptionPane.INFORMATION_MESSAGE);
								this.ventana.dispose();
							}
						} else {
							Calendar presup = (Calendar) ventana.getDateFechaVencimiento().getModel().getValue();
							Calendar today = Calendar.getInstance();
							if (!presup.after(today)) {
								JOptionPane.showMessageDialog(null, "Por favor, ingrese una fecha superior al dia de hoy. ", "Error", JOptionPane.WARNING_MESSAGE);
								return;
							}
							crearPresupuesto();
							JOptionPane.showMessageDialog(null, "La orden " + orden.getIdOT() + " se ha presupuestado correctamente", "Orden presupuestada", JOptionPane.INFORMATION_MESSAGE);
							this.ventana.dispose();
						}
					} else {
						int respuesta = JOptionPane.showConfirmDialog(ventana, "No se ha agregado costo por mano de obra. ¿Desea continuar?", null, JOptionPane.YES_NO_OPTION);
						if (respuesta == JOptionPane.YES_OPTION) {
							Calendar presup = (Calendar) ventana.getDateFechaVencimiento().getModel().getValue();
							Calendar today = Calendar.getInstance();
							if (!presup.after(today)) {
								JOptionPane.showMessageDialog(null, "Por favor, ingrese una fecha superior al dia de hoy. ", "Error", JOptionPane.WARNING_MESSAGE);
								return;
							}
							crearPresupuesto();
							JOptionPane.showMessageDialog(null, "La orden " + orden.getIdOT() + " se ha presupuestado correctamente", "Orden presupuestada", JOptionPane.INFORMATION_MESSAGE);
							this.ventana.dispose();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Por favor, ingrese una fecha ", "Error", JOptionPane.WARNING_MESSAGE);
				}
			} else {
				try {
					cambiarEstado();
				} catch (Exception ex) {
					ex.printStackTrace(); // TODO: Lanzar error a la vista y que
					// muestre un cartel bonito
				}
				this.ventana.dispose();
			}
		}
	}

	private void crearPresupuesto() {
		Calendar presup = (Calendar) ventana.getDateFechaVencimiento().getModel().getValue();
		this.orden.setVencPresup(presup);
		this.orden.setTecnicoAsoc(usuario);
		this.orden.setPiezasPresupuestadas(presupuestadas);
		if (!this.ventana.getTfManoDeObra().getText().isEmpty())
			this.orden.setManoDeObra(Double.parseDouble(this.ventana.getTfManoDeObra().getText()));
		else
			this.orden.setManoDeObra(0.0);
		if (orden.isEsDelivery())
			this.orden.setCostoDeEnvio(orden.getLocalidadEntrega().getZonaDeEnvio().getPrecio());
		else
			this.orden.setCostoDeEnvio(0.0);
		try {
			modelo.presupuestarOT(orden);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void llenarTablaPresupuesto() {
		total = 0;
		String[] columnas = { "Descripción", "Precio Venta", "" };
		DefaultTableModel tabla_de_presupuestadas = new DefaultTableModel(null, columnas) {

			private static final long serialVersionUID = -6822691945339120617L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 2)
					return true;
				else
					return false;
			}
		};
		tabla_de_presupuestadas.setColumnIdentifiers(columnas);
		if (orden.getEstado().getId() == 1 && this.presupuestadas != null && this.presupuestadas.size() > 0)
			// TODO Ingresada hardcodeado
			for (PiezaDTO p : this.presupuestadas) {
			Object[] fila = { p.getDescripcion(), p.getPrecio_venta(), BUTTON_NAME_QUITAR };
			tabla_de_presupuestadas.addRow(fila);
			total += p.getPrecio_venta();
			}
		this.ventana.getTable().setModel(tabla_de_presupuestadas);
		new ButtonColumn(this.ventana.getTable(), quitarDelPresupuesto(), 2);
	}

	private void cargarTablaDeUso() {
		this.presupuestadas = orden.getPiezasPresupuestadas();
		this.usadas = orden.getPiezasUsadas();
		String[] columnas = { "Presupuestada", "Numero de pieza", "Descripción", "", "" };
		tabla_de_piezas = new DefaultTableModel(null, columnas) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 3 || column == 4)
					return true;
				else
					return false;
			}
		};
		tabla_de_piezas.setColumnIdentifiers(columnas);

		List<PiezaDTO> agregar_usadas = new ArrayList<PiezaDTO>();
		for (PiezaDTO p : this.usadas) {
			agregar_usadas.add(p);
		}

		for (PiezaDTO pp : this.presupuestadas) {
			boolean agregada = false;
			PiezaDTO pieza = null;
			for (PiezaDTO pu : agregar_usadas) {
				if (pp.getIdProdPieza() == pu.getIdProdPieza() && agregada == false) {
					Object[] fila = { "Si", pu, pu.getDescripcion(), BUTTON_NAME_QUITAR, BUTTON_NAME_SIN_STOCK };
					tabla_de_piezas.addRow(fila);
					agregada = true;
					pieza = pu;
					break;
				}
			}
			if (pieza != null)
				agregar_usadas.remove(pieza);
			if (!agregada) {
				Object[] fila = { "Si", pp, pp.getDescripcion(), BUTTON_NAME_USAR, BUTTON_NAME_SIN_STOCK };
				tabla_de_piezas.addRow(fila);
			}
		}
		if (!agregar_usadas.isEmpty()) {
			for (PiezaDTO pu : agregar_usadas) {
				Object[] fila = { "No", pu, pu.getDescripcion(), BUTTON_NAME_QUITAR, BUTTON_NAME_SIN_STOCK };
				tabla_de_piezas.addRow(fila);
			}
		}
		this.ventana.getTable().setModel(tabla_de_piezas);
		new ButtonColumn(this.ventana.getTable(), usar(), 3);
		new ButtonColumn(this.ventana.getTable(), reportar(), 4);
	}

	private void AgregarTablaReparar() {
		PiezaDTO p = (PiezaDTO) this.ventana.getComboPieza().getSelectedItem();
		Object[] fila = { "No", p, p.getDescripcion(), BUTTON_NAME_USAR, BUTTON_NAME_SIN_STOCK };
		tabla_de_piezas.addRow(fila);
		this.ventana.getTable().setModel(tabla_de_piezas);
	}

	private Action quitarDelPresupuesto() {
		return new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				total -= presupuestadas.get(modelRow).getPrecio_venta();
				ventana.setLblTotalPresupuesto("Total piezas: $" + total);
				presupuestadas.remove(modelRow);
				((DefaultTableModel) table.getModel()).removeRow(modelRow);
			}
		};
	}

	private Action usar() {
		return new AbstractAction() {

			private static final long serialVersionUID = -2016978315345988092L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				PiezaDTO pieza = (PiezaDTO) table.getModel().getValueAt(modelRow, 1);
				if (table.getModel().getValueAt(modelRow, 3).equals(BUTTON_NAME_USAR)) {
					boolean revisarStock = false;
					try {
						revisarStock = modelo.revisarStock(pieza);
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if (revisarStock) {
						try {
							modelo.agregarUsada(orden, pieza);
							orden = modelo.obtenerOrdenPorId(orden.getIdOT());
							cargarTablaDeUso();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else {
						try {
							// modelo.crearAlerta(pieza);
							ventana.getCbEstado().removeAllItems();
							ventana.getCbEstado().addItem(new EstadoDTO(6, ESTADO_OT_EN_ESPERA_DE_PIEZAS));
							cambiarEstado();
							// TODO: Se puede seguir reparando por mas que no tenga una pieza.
							table.getModel().setValueAt(BUTTON_NAME_SIN_STOCK, modelRow, 3);
							JOptionPane.showMessageDialog(ventana, "La pieza " + pieza + " se encuentra sin stock. Puede seguir usando piezas pero luego de que cierre esta ventana la orden quedará en estado de espera de piezas hasta que reingrese en el stock.", "¡Atención!", JOptionPane.INFORMATION_MESSAGE, null);
						} catch (Exception ex) {
							ex.printStackTrace();
							// TODO: Lanzar error a la vista y que muestre un cartel bonito
						}
					}
				} else if (table.getModel().getValueAt(modelRow, 3).equals(BUTTON_NAME_QUITAR)) {
					try {
						modelo.quitarUsada(orden, pieza);
						orden = modelo.obtenerOrdenPorId(orden.getIdOT());
						cargarTablaDeUso();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		};
	}

	private Action reportar() {
		return new AbstractAction() {

			private static final long serialVersionUID = -8907703577066499812L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				PiezaDTO pieza = (PiezaDTO) table.getModel().getValueAt(modelRow, 1);
				try {
					if (table.getModel().getValueAt(modelRow, 3).equals(BUTTON_NAME_SIN_STOCK)) {
						JOptionPane.showMessageDialog(ventana, "La pieza esta sin stock, no se puede reportar.", "¡Atención!", JOptionPane.ERROR_MESSAGE, null);
					} else if (table.getModel().getValueAt(modelRow, 3).equals(BUTTON_NAME_QUITAR)) {
						JOptionPane.showMessageDialog(ventana, "Por favor, quite primeramente la pieza y luego repórtela.", "¡Atención!", JOptionPane.ERROR_MESSAGE, null);
					} else {
						int seleccion = JOptionPane.showOptionDialog(ventana, "¿La pieza está rota o perdida?", "Baja de stock", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "ROTA", "PERDIDA" }, "ROTA");

						if (seleccion != -1) {// TODO 3 Rota, 4 Perdida
							if (modelo.revisarStock(pieza)) {
								modelo.bajaStockPieza(pieza, 1, seleccion + 3);
							}
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
	}

	public void cargarOt() {
		this.ventana.getTxtUsuario().setText(this.usuario.toString());
		this.ventana.setLblidOT(this.orden.getIdOT());
		this.ventana.setTxtCliente(orden.getCliente().getApellido() + ", " + orden.getCliente().getNombre());
		this.ventana.setTxtDetalle(this.orden.getDescripcion());
		this.ventana.setTxtElectro(orden.getElectrodomestico().getMarca() + " - " + orden.getElectrodomestico().getModelo());
	}

	private void cargarComboBoxMarcas() {
		// this.ventana.agregarMarcas(modelo.obtenerMarcas());
		this.ventana.getComboMarca().addItem(this.orden.getElectrodomestico().getMarca());
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		if (e.getSource() == this.ventana.getComboMarca() && this.ventana.getComboMarca().getSelectedItem() != null) {
			MarcaDTO marca = (MarcaDTO) this.ventana.getComboMarca().getSelectedItem();
			try {
				// TODO: preguntar a Lucas si hace falta traer tooooodas las
				// piezas.
				List<PiezaDTO> piezas = modelo.obtenerItems();
				List<PiezaDTO> piezasMarca = new ArrayList<PiezaDTO>();
				for (PiezaDTO piezaDTO : piezas) {
					if (piezaDTO.getMarca().equals(marca))
						piezasMarca.add(piezaDTO);
				}
				this.ventana.agregarPiezas(piezasMarca);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void cargarEstados() {
		this.ventana.getCbEstado().addItem(this.orden.getEstado());

		if (this.orden.getEstado().getNombre().equals(ESTADO_OT_APROBADA)) {
			this.ventana.getCbEstado().removeItem(this.orden.getEstado());
			this.ventana.getCbEstado().addItem(new EstadoDTO(5, ESTADO_OT_EN_REPARACION));
			this.ventana.getCbEstado().addItem(new EstadoDTO(7, ESTADO_OT_REPARADA));
			this.ventana.getCbEstado().addItem(new EstadoDTO(8, ESTADO_OT_IRREPARABLE));

		} else if (this.orden.getEstado().getNombre().equals(ESTADO_OT_EN_REPARACION)) {
			this.ventana.getCbEstado().addItem(new EstadoDTO(7, ESTADO_OT_REPARADA));
			this.ventana.getCbEstado().addItem(new EstadoDTO(8, ESTADO_OT_IRREPARABLE));
		}
	}

	private void cambiarEstado() throws Exception {
		if (!this.orden.getEstado().equals((EstadoDTO) ventana.getCbEstado().getSelectedItem())) {
			modelo.actualizarEstado(orden, ((EstadoDTO) ventana.getCbEstado().getSelectedItem()).getId());
		}
	}

	private void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	private void setVentana(VentanaTecnicoOT ventana) {
		this.ventana = ventana;
	}

	public VentanaTecnicoOT getVentana() {
		return ventana;
	}

	public Modelo getModelo() {
		return modelo;
	}
}
