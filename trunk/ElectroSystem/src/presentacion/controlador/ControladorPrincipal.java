package presentacion.controlador;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import dto.AlertaDTO;
import dto.ClienteDTO;
import dto.DetallesEnvioDTO;
import dto.ElectrodomesticoDTO;
import dto.EnvioDTO;
import dto.FleteroDTO;
import dto.MarcaDTO;
import dto.MovimientoDTO;
import dto.OrdenDTO;
import dto.PiezaDTO;
import dto.PrecioPiezaDTO;
import dto.ProveedorDTO;
import dto.SolicitudCompraDTO;
import dto.UsuarioDTO;
import helpers.FormatoTablaContabilidad;
import helpers.Validador;
import modelo.Modelo;
import modelo.Reporte;
import presentacion.controlador.button.column.ButtonColumn;
import presentacion.controlador.fleteros.ControladorAsignacion;
import presentacion.controlador.fleteros.ControladorEnvio;
import presentacion.controlador.fleteros.ControladorVentanaNuevoFletero;
import presentacion.controlador.presupuesto.ControladorTecnicoOT;
import presentacion.ventanas.Proveedores.VentanaAltaModifProveedor;
import presentacion.ventanas.cliente.NuevaLocalidad;
import presentacion.ventanas.cliente.VentanaAltaModifCliente;
import presentacion.ventanas.cliente.VentanaBuscadorCliente;
import presentacion.ventanas.cliente.VentanaZonas;
import presentacion.ventanas.ed.VentanaAltaModifElectrod;
import presentacion.ventanas.fleteros.VentanaAsignacion;
import presentacion.ventanas.fleteros.VentanaEnvio;
import presentacion.ventanas.fleteros.VentanaNuevoFletero;
import presentacion.ventanas.logueo.VentanaLogueo;
import presentacion.ventanas.mail.VentanaMail;
import presentacion.ventanas.ot.VentanaAdminOT;
import presentacion.ventanas.ot.VentanaBuscadorOT;
import presentacion.ventanas.ot.VentanaTecnicoOT;
import presentacion.ventanas.piezas.VentanaAltaModifPieza;
import presentacion.ventanas.piezas.VentanaSeleccionarArchivo;
import presentacion.ventanas.sc.VentanaNuevaSC;
import presentacion.ventanas.usuario.VentanaAltaModifUsuario;
import presentacion.ventanas.worker.ProgressWork;
import presentacion.vista.VistaPrincipal;

public class ControladorPrincipal implements ActionListener, ItemListener, ChangeListener, PropertyChangeListener {

	private static final String BUTTON_NAME_DETALLES = "DETALLES";
	private static final String BUTTON_NAME_VER_SOLICITUD = "VER SOLICITUD";
	private static final String BUTTON_NAME_SOLICITAR = "SOLICITAR";
	private static final String BUTTON_NAME_HOJA_DE_RUTA = "HOJA DE RUTA";
	private static final String BUTTON_NAME_BLANQUEAR = "BLANQUEAR";
	private static final String BUTTON_NAME_CONTINUAR = "CONTINUAR";
	private static final String BUTTON_NAME_REPARAR = "REPARAR";
	private static final String BUTTON_NAME_PRESUPUESTAR = "PRESUPUESTAR";
	private static final String BUTTON_NAME_BORRAR = "BORRAR";
	private static final String BUTTON_NAME_EDITAR = "EDITAR";
	private static final String BUTTON_NAME_VER = "VER";

	private static final String EN_REPARACIÓN = "En reparación";
	private static final String APROBADA = "Aprobada";
	private static final String INGRESADA = "Ingresada";

	private VistaPrincipal vistaPrincipal;
	private Modelo modelo;

	private List<ClienteDTO> clientes_en_tabla;
	private List<ElectrodomesticoDTO> electrodomestico_en_tabla;
	private List<OrdenDTO> ordenes_en_tabla_UA;
	private List<OrdenDTO> ordenes_en_tabla_UT;
	private List<PiezaDTO> piezas_en_tabla;
	private List<SolicitudCompraDTO> solicitudes_en_tabla;
	private List<MarcaDTO> marcas;
	private List<UsuarioDTO> usuarios_comunes_en_tabla;
	private List<ProveedorDTO> proveedores_en_tabla;
	private List<FleteroDTO> fleteros_en_tabla;
	private List<AlertaDTO> alertas_en_tabla;

	private List<OrdenDTO> ordenes_a_enviar;
	private List<EnvioDTO> envios_asignados;

	private final UsuarioDTO usuarioLogueado;

	private VentanaAltaModifCliente ventanaCliente;
	private VentanaAltaModifPieza ventanaPieza;
	private VentanaAdminOT ventanaOT;
	private VentanaAltaModifElectrod ventanaElectrodomestico;
	private VentanaTecnicoOT ventanaPresupuesto;
	private OrdenDTO ordenApresupuestar;
	private OrdenDTO ordenAcargar;
	private VentanaAltaModifUsuario ventanaUsuario;
	private VentanaAltaModifProveedor ventanaProveedor;
	private VentanaNuevoFletero ventanaFletero;
	private VentanaMail ventanaMail;

	// private File selectedFile;
	private MovimientoDTO movimientosContables;

	public ControladorPrincipal(VistaPrincipal vistaPrincipal, Modelo modelo, UsuarioDTO usuarioLogueado) {
		this.vistaPrincipal = vistaPrincipal;
		this.modelo = modelo;
		this.usuarioLogueado = usuarioLogueado;
	}

	void iniciar() {
		this.vistaPrincipal.setTitle("Electro R (S.R.L.)");
		this.vistaPrincipal.setLocationRelativeTo(null); // centrado en pantalla
		this.vistaPrincipal.getCbMarcas().addItemListener(this);
		this.vistaPrincipal.getBtnAgregarCliente().addActionListener(this);
		this.vistaPrincipal.getBtnNuevaOt().addActionListener(this);
		this.vistaPrincipal.getBtnNuevaSc().addActionListener(this);
		this.vistaPrincipal.getBtnAadirPiezas().addActionListener(this);
		this.vistaPrincipal.getBtnAgregarUsuario().addActionListener(this);
		this.vistaPrincipal.getBtnNuevoProveedor().addActionListener(this);
		this.vistaPrincipal.getBtnNuevoFletero().addActionListener(this);
		this.vistaPrincipal.getBtnAgregarEd().addActionListener(this);
		this.vistaPrincipal.getTabbedPane().addChangeListener(this);
		this.vistaPrincipal.getBtnEnviarMailOT().addActionListener(this);
		this.vistaPrincipal.getBtnEnviarMailSC().addActionListener(this);
		this.vistaPrincipal.getBtnAsignarEnvio().addActionListener(this);
		this.vistaPrincipal.getBtnVerContabilidad().addActionListener(this);
		this.vistaPrincipal.getBtnVerEntregados().addActionListener(this);

		this.vistaPrincipal.getBtnCrearBackup().addActionListener(this);
		this.vistaPrincipal.getBtnCargarBackup().addActionListener(this);

		this.vistaPrincipal.getComboEstadosUA().addItemListener(this);
		this.vistaPrincipal.getComboEstadosUT().addItemListener(this);
		this.vistaPrincipal.getComboFleteros().addItemListener(this);

		this.vistaPrincipal.getMntmDatos().addActionListener(this);
		this.vistaPrincipal.getMntmSalir().addActionListener(this);
		this.vistaPrincipal.getMntmActualizar().addActionListener(this);
		this.vistaPrincipal.getMntmEditarZonas().addActionListener(this);
		this.vistaPrincipal.getMntmEditarLocalidades().addActionListener(this);
		this.vistaPrincipal.getMntmImportarPrecios().addActionListener(this);

		// this.vistaPrincipal.getBtnProcesar().addActionListener(this);
		// this.vistaPrincipal.getFileChooser().addActionListener(this);

		this.vistaPrincipal.getDpiSemana().getJFormattedTextField().addPropertyChangeListener(this);
		this.vistaPrincipal.getDpiMes().getJFormattedTextField().addPropertyChangeListener(this);
		this.vistaPrincipal.getDpiAnio().getJFormattedTextField().addPropertyChangeListener(this);
		this.vistaPrincipal.getDpiPersonalizado1().getJFormattedTextField().addPropertyChangeListener(this);
		this.vistaPrincipal.getDpiPersonalizado2().getJFormattedTextField().addPropertyChangeListener(this);
		this.vistaPrincipal.getRdSemana().addActionListener(this);
		this.vistaPrincipal.getRdMes().addActionListener(this);
		this.vistaPrincipal.getRdAnio().addActionListener(this);
		this.vistaPrincipal.getRdPersonalizado().addActionListener(this);
		this.vistaPrincipal.getBtnGenerarReporte().addActionListener(this);
		this.vistaPrincipal.getBtnReporteContable().addActionListener(this);

		this.vistaPrincipal.getBtnVerEntregados().addActionListener(this);
		this.vistaPrincipal.getBtnBack().addActionListener(this);
		this.vistaPrincipal.getBtnEnvios().addActionListener(this);

		this.vistaPrincipal.getBtnBuscarOT().addActionListener(this);
		this.vistaPrincipal.getBtnBuscarCliente().addActionListener(this);

		try {
			this.marcas = this.modelo.obtenerMarcas();
			this.electrodomestico_en_tabla = this.modelo.obtenerElectrodomesticos();

			for (int i = 0; i < this.modelo.obtenerFleteros().size(); i++) {
				this.vistaPrincipal.getComboFleteros().addItem(this.modelo.obtenerFleteros().get(i));
			}

		} catch (Exception e) {
			// TODO ATRAPAR ERROR
			e.printStackTrace();
		}

		this.vistaPrincipal.getMenuLog().setText("Bienvenido/a " + usuarioLogueado);
		JTabbedPane tabbedPane = this.vistaPrincipal.getTabbedPane();
		switch (usuarioLogueado.getRol().getDescripcion()) {
		case "Administrativo":
			tabbedPane.remove(this.vistaPrincipal.getTabUsuarios());
			tabbedPane.remove(this.vistaPrincipal.getTabUsuarios());
			tabbedPane.remove(this.vistaPrincipal.getPanelProveedores());
			tabbedPane.remove(this.vistaPrincipal.getTabFleteros());
			tabbedPane.remove(this.vistaPrincipal.getTabOT_UT());
			tabbedPane.remove(this.vistaPrincipal.getTabContabilidad());
			try {
				this.alertas_en_tabla = this.modelo.obtenerAlertas();
				if (!this.alertas_en_tabla.isEmpty())
					JOptionPane.showMessageDialog(null, "Hay piezas faltantes o con bajo stock, por favor revise las alertas y realice los pedidos correspondientes a la brevedad.", "¡¡¡Revisar alertas!!!", JOptionPane.WARNING_MESSAGE, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			llenarTablaClientes();
			break;
		case "Tecnico":
			tabbedPane.remove(this.vistaPrincipal.getTabClientes());
			tabbedPane.remove(this.vistaPrincipal.getPanelScs());
			tabbedPane.remove(this.vistaPrincipal.getTabPiezas());
			tabbedPane.remove(this.vistaPrincipal.getTabEds());
			tabbedPane.remove(this.vistaPrincipal.getTabUsuarios());
			tabbedPane.remove(this.vistaPrincipal.getPanelProveedores());
			tabbedPane.remove(this.vistaPrincipal.getTabFleteros());
			tabbedPane.remove(this.vistaPrincipal.getTabOT_UA());
			tabbedPane.remove(this.vistaPrincipal.getTabOT_UA());
			tabbedPane.remove(this.vistaPrincipal.getTabImportarPrecios());
			tabbedPane.remove(this.vistaPrincipal.getTabContabilidad());
			tabbedPane.remove(this.vistaPrincipal.getTabAlertas());
			tabbedPane.remove(this.vistaPrincipal.getTabReportes());
			tabbedPane.remove(this.vistaPrincipal.getTabEnvios());
			this.vistaPrincipal.getBtnEnviarMailOT().setVisible(false);
			llenarTablaOT_UT(vistaPrincipal.getComboEstadosUT().getSelectedItem().toString());
			break;
		case "Superusuario":
			this.vistaPrincipal.getMnEdicin().setVisible(true);
			this.vistaPrincipal.getMnHerramientas().setVisible(true);
			llenarTablaClientes();
			break;
		}

		this.vistaPrincipal.setVisible(true);
	}

	@SuppressWarnings("serial")
	private void llenarTablaClientes() {
		JTable tableClientes = this.vistaPrincipal.getTableClientes();
		try {
			String[] columns = { "Nro. de cliente", "Nombre", "Telefono", "Email", "", "", "" };
			DefaultTableModel dtm = new DefaultTableModel(null, columns) {
				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 4 || column == 5 || column == 6)
						return true;
					else
						return false;
				}
			};
			dtm.setColumnIdentifiers(columns);
			clientes_en_tabla = modelo.obtenerClientes();
			for (ClienteDTO c : clientes_en_tabla) {
				Object[] fila = { c.getIdCliente(), c, c.getTelefono(), c.geteMail(), BUTTON_NAME_VER, BUTTON_NAME_EDITAR, BUTTON_NAME_BORRAR };
				dtm.addRow(fila);
			}
			tableClientes.setModel(dtm);
			new ButtonColumn(tableClientes, borrarCliente(), 6);
			new ButtonColumn(tableClientes, editarCliente(), 5);
			new ButtonColumn(tableClientes, verCliente(), 4);

			tableClientes.getColumnModel().getColumn(3).setWidth(500);

			tableClientes.getColumnModel().getColumn(4).setMinWidth(150);
			tableClientes.getColumnModel().getColumn(4).setMaxWidth(150);
			tableClientes.getColumnModel().getColumn(5).setMinWidth(150);
			tableClientes.getColumnModel().getColumn(5).setMaxWidth(150);
			tableClientes.getColumnModel().getColumn(6).setMinWidth(150);
			tableClientes.getColumnModel().getColumn(6).setMaxWidth(150);

		} catch (Exception e) {
			// TODO ATRAPAR ERROR
			e.printStackTrace();
		}
		// tableClientes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}

	private void llenarTablaOT_UA(String estado) {
		try {

			String[] columns = { "Nro. de orden", "Cliente", "Producto", "Descripcion", "Estado", "" };

			DefaultTableModel dtm = new DefaultTableModel(null, columns) {

				private static final long serialVersionUID = 3054660273548654771L;

				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 5)
						return true;
					else
						return false;
				}
			};
			dtm.setColumnIdentifiers(columns);

			ordenes_en_tabla_UA = filtrarSegun(estado);

			for (OrdenDTO orden : ordenes_en_tabla_UA) {

				ClienteDTO cliente = orden.getCliente();
				ElectrodomesticoDTO electrodomestico = orden.getElectrodomestico();

				Object[] fila = { orden, Validador.contactenarStrings(cliente.getNombre(), cliente.getApellido()), Validador.contactenarStrings(electrodomestico.getMarca().getNombre(), electrodomestico.getModelo(), electrodomestico.getDescripcion()), orden.getDescripcion(), orden.getEstado().getNombre(), BUTTON_NAME_VER };

				dtm.addRow(fila);
			}
			this.vistaPrincipal.getTableOT_UA().setModel(dtm);
			new ButtonColumn(this.vistaPrincipal.getTableOT_UA(), verOT_UA(), 5);

			this.vistaPrincipal.getTableOT_UA().getColumnModel().getColumn(5).setMinWidth(150);
			this.vistaPrincipal.getTableOT_UA().getColumnModel().getColumn(5).setMaxWidth(150);

		} catch (Exception e) {
			// TODO ATRAPAR ERROR
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	private void llenarTablaOT_UT(String estado) {
		try {

			String[] columns = { "Nro. de orden", "Cliente", "Producto", "Descripcion", "A cargo de", "" };

			DefaultTableModel dtm = new DefaultTableModel(null, columns) {
				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 5)
						return true;
					else
						return false;
				}
			};

			dtm.setColumnIdentifiers(columns);

			this.ordenes_en_tabla_UT = new LinkedList<OrdenDTO>();
			List<OrdenDTO> obtenerOrdenes = modelo.obtenerOrdenes();
			for (int i = 0; i < obtenerOrdenes.size(); i++) {
				OrdenDTO actual = obtenerOrdenes.get(i);
				if (actual.getEstado().getNombre().equals(INGRESADA) || actual.getEstado().getNombre().equals(APROBADA) || actual.getEstado().getNombre().equals(EN_REPARACIÓN))
					if (actual.getEstado().getNombre().equals(estado))
						ordenes_en_tabla_UT.add(actual);
			}

			for (OrdenDTO orden : ordenes_en_tabla_UT) {

				String buttonName = null;
				switch (orden.getEstado().getNombre()) {
				case INGRESADA:
					buttonName = BUTTON_NAME_PRESUPUESTAR;
					break;
				case APROBADA:
					buttonName = BUTTON_NAME_REPARAR;
					break;
				case EN_REPARACIÓN:
					buttonName = BUTTON_NAME_CONTINUAR;
					break;
				default:
					break;
				}

				ClienteDTO cliente = orden.getCliente();
				ElectrodomesticoDTO electrodomestico = orden.getElectrodomestico();

				Object[] fila = { orden, Validador.contactenarStrings(cliente.getNombre(), cliente.getApellido()), Validador.contactenarStrings(electrodomestico.getMarca().getNombre(), electrodomestico.getModelo(), electrodomestico.getDescripcion()), orden.getDescripcion(), orden.getTecnicoAsoc() != null ? orden.getTecnicoAsoc() : "Sin tecnico", buttonName };

				dtm.addRow(fila);
			}

			this.vistaPrincipal.getTableOT_UT().setModel(dtm);

			Action actionPresupuestar = presupuestarOT();
			Action actionReparar = reparar();
			Action action = null;

			for (OrdenDTO orden : ordenes_en_tabla_UT) {

				switch (orden.getEstado().getNombre()) {
				case INGRESADA:
					action = actionPresupuestar;
					break;
				case APROBADA:
					action = actionReparar;
					break;
				case EN_REPARACIÓN:
					action = actionReparar;
					break;

				default:
					break;
				}
			}

			new ButtonColumn(this.vistaPrincipal.getTableOT_UT(), action, 5);

			this.vistaPrincipal.getTableOT_UT().getColumnModel().getColumn(5).setMinWidth(150);
			this.vistaPrincipal.getTableOT_UT().getColumnModel().getColumn(5).setMaxWidth(150);

		} catch (Exception exc) {
			// TODO ATRAPAR ERROR
			exc.printStackTrace();
		}

	}

	@SuppressWarnings("serial")
	private void llenarTablaScs() {
		try {
			String[] columns = { "Nro. de solicitud", "Proveedor", "Estado", "", "" };
			DefaultTableModel dtm = new DefaultTableModel(null, columns) {
				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 3 || column == 4)
						return true;
					else
						return false;
				}
			};
			dtm.setColumnIdentifiers(columns);
			solicitudes_en_tabla = modelo.obtenerSolicitudes();
			for (SolicitudCompraDTO sc : solicitudes_en_tabla) {
				Object[] fila = { sc.getId(), sc.getProveedor().getNombre(), sc.getEstado().getNombre(), BUTTON_NAME_VER, BUTTON_NAME_EDITAR };
				dtm.addRow(fila);
			}
			this.vistaPrincipal.getTableScs().setModel(dtm);

			new ButtonColumn(this.vistaPrincipal.getTableScs(), verSC(), 3);
			new ButtonColumn(this.vistaPrincipal.getTableScs(), editarSC(), 4);

			this.vistaPrincipal.getTableScs().getColumnModel().getColumn(3).setMinWidth(150);
			this.vistaPrincipal.getTableScs().getColumnModel().getColumn(3).setMaxWidth(150);
			this.vistaPrincipal.getTableScs().getColumnModel().getColumn(4).setMinWidth(150);
			this.vistaPrincipal.getTableScs().getColumnModel().getColumn(4).setMaxWidth(150);

		} catch (Exception e) {
			// TODO ATRAPAR ERROR
			e.printStackTrace();
		}
	}

	private void llenarTablaProveedores() {
		try {
			String[] columns = { "CUIT", "Nombre", "Contacto", "Teléfono", "", "", "" };
			DefaultTableModel dtm = new DefaultTableModel(null, columns) {
				private static final long serialVersionUID = 6446529126548721007L;

				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 4 || column == 5 || column == 6)
						return true;
					else
						return false;
				}
			};
			dtm.setColumnIdentifiers(columns);
			proveedores_en_tabla = modelo.obtenerProveedores();
			for (ProveedorDTO p : proveedores_en_tabla) {
				Object[] fila = { p.getCuit(), p, p.getContacto(), p.getTelefono(), BUTTON_NAME_VER, BUTTON_NAME_EDITAR, BUTTON_NAME_BORRAR };
				dtm.addRow(fila);
			}
			JTable tableProveedores = this.vistaPrincipal.getTableProveedores();
			tableProveedores.setModel(dtm);
			new ButtonColumn(tableProveedores, verProveedor(), 4);
			new ButtonColumn(tableProveedores, editarProveedor(), 5);
			new ButtonColumn(tableProveedores, borrarProveedor(), 6);

			tableProveedores.getColumnModel().getColumn(4).setMinWidth(150);
			tableProveedores.getColumnModel().getColumn(4).setMaxWidth(150);
			tableProveedores.getColumnModel().getColumn(5).setMinWidth(150);
			tableProveedores.getColumnModel().getColumn(5).setMaxWidth(150);
			tableProveedores.getColumnModel().getColumn(6).setMinWidth(150);
			tableProveedores.getColumnModel().getColumn(6).setMaxWidth(150);

		} catch (Exception e) {
			// TODO ATRAPAR ERROR
			e.printStackTrace();
		}
	}

	private void cargarCbMarcas() {
		try {
			this.vistaPrincipal.getCbMarcas().removeAllItems();
			marcas = this.modelo.obtenerMarcas();
			for (MarcaDTO m : marcas)
				this.vistaPrincipal.getCbMarcas().addItem(m);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@SuppressWarnings("serial")
	private void llenarTablaPiezas() {
		try {
			String[] columns = { "Nro. de pieza", "Marca", "Descripcion", "Precio Venta", "Stock", "", "", "", "" };
			DefaultTableModel dtm = new DefaultTableModel(null, columns) {
				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 5 || column == 6 || column == 7 || column == 8)
						return true;
					else
						return false;
				}
			};
			dtm.setColumnIdentifiers(columns);
			piezas_en_tabla = modelo.obtenerItems();
			for (PiezaDTO pieza : piezas_en_tabla) {
				if (pieza.getMarca().equals((MarcaDTO) this.vistaPrincipal.getCbMarcas().getSelectedItem())) {
					Object[] fila = { pieza.getIdUnico(), pieza.getMarca(), pieza, pieza.getPrecio_venta(), pieza.getStock(), BUTTON_NAME_VER, BUTTON_NAME_EDITAR, "MODIFICAR STOCK", BUTTON_NAME_BORRAR };
					dtm.addRow(fila);
				}
			}
			JTable tablePiezas = this.vistaPrincipal.getTablePiezas();
			tablePiezas.setModel(dtm);
			new ButtonColumn(tablePiezas, verPieza(), 5);
			new ButtonColumn(tablePiezas, editarPieza(), 6);
			new ButtonColumn(tablePiezas, modificarStock(), 7);
			new ButtonColumn(tablePiezas, borrarPieza(), 8);

			tablePiezas.getColumnModel().getColumn(5).setMinWidth(150);
			tablePiezas.getColumnModel().getColumn(5).setMaxWidth(150);
			tablePiezas.getColumnModel().getColumn(6).setMinWidth(150);
			tablePiezas.getColumnModel().getColumn(6).setMaxWidth(150);
			tablePiezas.getColumnModel().getColumn(7).setMinWidth(150);
			tablePiezas.getColumnModel().getColumn(7).setMaxWidth(150);
			tablePiezas.getColumnModel().getColumn(8).setMinWidth(150);
			tablePiezas.getColumnModel().getColumn(8).setMaxWidth(150);

		} catch (Exception e) {
			// TODO ATRAPAR ERROR
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	private void llenarTablaElectrodomesticos() {
		try {
			String[] columns = { "Producto", "Marca", "Modelo", "", "" };
			DefaultTableModel dtm = new DefaultTableModel(null, columns) {
				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 3 || column == 4)
						return true;
					else
						return false;
				}
			};
			dtm.setColumnIdentifiers(columns);
			electrodomestico_en_tabla = modelo.obtenerElectrodomesticos();
			for (ElectrodomesticoDTO electrodomestico : electrodomestico_en_tabla) {
				Object[] fila = { electrodomestico.getDescripcion(), electrodomestico.getMarca().getNombre(), electrodomestico, BUTTON_NAME_EDITAR, BUTTON_NAME_BORRAR };
				dtm.addRow(fila);
			}
			JTable tableEds = this.vistaPrincipal.getTableEds();
			tableEds.setModel(dtm);
			new ButtonColumn(tableEds, borrarElectrodomestico(), 4);
			new ButtonColumn(tableEds, editarElectrodomestico(), 3);

			tableEds.getColumnModel().getColumn(3).setMinWidth(150);
			tableEds.getColumnModel().getColumn(3).setMaxWidth(150);
			tableEds.getColumnModel().getColumn(4).setMinWidth(150);
			tableEds.getColumnModel().getColumn(4).setMaxWidth(150);

		} catch (Exception e) {
			// TODO ATRAPAR ERROR
			e.printStackTrace();
		}
	}

	private void llenarTablaUsuarios() {
		try {
			String[] columns = { "Nombre de empleado", "Nombre de usuario", "Tipo", "", "" };
			DefaultTableModel dtm = new DefaultTableModel(null, columns) {
				private static final long serialVersionUID = 4428870321225072154L;

				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 3 || column == 4)
						return true;
					else
						return false;
				}
			};
			dtm.setColumnIdentifiers(columns);
			usuarios_comunes_en_tabla = modelo.obtenerUsuariosComunes();
			for (UsuarioDTO usuario : usuarios_comunes_en_tabla) {
				Object[] fila = { usuario, usuario.getUsuario(), usuario.getRol(), BUTTON_NAME_BORRAR, BUTTON_NAME_BLANQUEAR };
				dtm.addRow(fila);
			}
			JTable tableUsuarios = this.vistaPrincipal.getTableUsuarios();
			tableUsuarios.setModel(dtm);
			new ButtonColumn(tableUsuarios, borrarUsuario(), 3);
			new ButtonColumn(tableUsuarios, blanquearUsuario(), 4);

			tableUsuarios.getColumnModel().getColumn(3).setMinWidth(150);
			tableUsuarios.getColumnModel().getColumn(3).setMaxWidth(150);
			tableUsuarios.getColumnModel().getColumn(4).setMinWidth(150);
			tableUsuarios.getColumnModel().getColumn(4).setMaxWidth(150);

		} catch (Exception e) {
			// TODO ATRAPAR ERROR
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	private void llenarTablaFleteros() {

		try {
			String[] columns = { "Nombre y Apellido", "Celular", "Registro Conducir", "Vehículo", "", "", "", "" };

			DefaultTableModel dtm = new DefaultTableModel(null, columns) {

				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 4 || column == 5 || column == 6 || column == 7)
						return true;
					else
						return false;
				}
			};
			dtm.setColumnIdentifiers(columns);

			fleteros_en_tabla = modelo.obtenerFleteros();

			for (FleteroDTO fletero : fleteros_en_tabla) {
				Object[] fila = { fletero, fletero.getCelular(), fletero.getRegistroConducir(), fletero.getVehiculo().getPatente(), BUTTON_NAME_VER, BUTTON_NAME_EDITAR, BUTTON_NAME_BORRAR, BUTTON_NAME_HOJA_DE_RUTA };
				dtm.addRow(fila);
			}

			JTable tableFleteros = this.vistaPrincipal.getTableFleteros();
			tableFleteros.setModel(dtm);

			new ButtonColumn(tableFleteros, verFletero(), 4);
			new ButtonColumn(tableFleteros, editarFletero(), 5);
			new ButtonColumn(tableFleteros, borrarFletero(), 6);
			new ButtonColumn(tableFleteros, verHojaRuta(), 7);

			tableFleteros.getColumnModel().getColumn(4).setMinWidth(150);
			tableFleteros.getColumnModel().getColumn(4).setMaxWidth(150);
			tableFleteros.getColumnModel().getColumn(5).setMinWidth(150);
			tableFleteros.getColumnModel().getColumn(5).setMaxWidth(150);
			tableFleteros.getColumnModel().getColumn(6).setMinWidth(150);
			tableFleteros.getColumnModel().getColumn(6).setMaxWidth(150);
			tableFleteros.getColumnModel().getColumn(7).setMinWidth(150);
			tableFleteros.getColumnModel().getColumn(7).setMaxWidth(150);

		} catch (Exception e) {
			// TODO ATRAPAR ERROR
			e.printStackTrace();
		}
	}

	private void llenarTablaAlertas() {
		try {
			alertas_en_tabla = modelo.obtenerAlertas();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(vistaPrincipal, "Ocurrió un error al traer las alertas." + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

			e1.printStackTrace();
		}

		if (alertas_en_tabla.isEmpty()) {
			JOptionPane.showMessageDialog(this.vistaPrincipal, "¡ ¡ ¡ Felicitaciones, no hay ninguna pieza en alerta ! ! !", "SIN ALERTAS", JOptionPane.INFORMATION_MESSAGE, null);
		}
		try {
			String[] columns = { "Fecha de creación", "Número de pieza", "Nombre", "Stock mínimo", "Stock actual", "Pedida en solicitud", "Tipo de alerta", "" };

			DefaultTableModel dtm = new DefaultTableModel(null, columns) {

				private static final long serialVersionUID = -6274845895096406073L;

				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 7)
						return true;
					else
						return false;
				}
			};
			dtm.setColumnIdentifiers(columns);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			for (AlertaDTO alerta : alertas_en_tabla) {

				String buttonName = null;
				if (alerta.getPedidaEn() == null)
					buttonName = BUTTON_NAME_SOLICITAR;
				else
					buttonName = BUTTON_NAME_VER_SOLICITUD;

				PiezaDTO pieza = alerta.getPieza();
				Object[] fila = { sdf.format(alerta.getFechaCreada().getTime()), pieza.getIdProdPieza(), "(" + pieza.getMarca().getNombre() + ") " + pieza.getDescripcion(), pieza.getBajo_stock(), pieza.getStock(), alerta.getPedidaEn()==null?"No pedida":alerta.getPedidaEn(), alerta.getTipoAlerta(), buttonName };
				dtm.addRow(fila);
			}

			JTable tableAlertas = this.vistaPrincipal.getTableAlertas();
			tableAlertas.setModel(dtm);

			new ButtonColumn(tableAlertas, accionAlerta(), 7);

			tableAlertas.getColumnModel().getColumn(7).setMinWidth(150);
			tableAlertas.getColumnModel().getColumn(7).setMaxWidth(150);

		} catch (Exception e) {
			// TODO ATRAPAR ERROR
			e.printStackTrace();
		}
	}

	private Action borrarFletero() {
		Action borrarFletero = new AbstractAction() {

			private static final long serialVersionUID = -1791065090765145181L;

			@Override
			public void actionPerformed(ActionEvent a) {

				JTable table = (JTable) a.getSource();
				int modelRow = Integer.valueOf(a.getActionCommand());

				try {
					int rta = JOptionPane.showConfirmDialog(vistaPrincipal, "¿Está seguro que desea eliminar al empleado?", null, JOptionPane.YES_NO_OPTION);

					if (rta == JOptionPane.YES_OPTION) {
						TableModel model = table.getModel();
						FleteroDTO fletero = (FleteroDTO) model.getValueAt(table.getSelectedRow(), 0);
						modelo.borrarFletero(fletero.getIdFletero(), usuarioLogueado.getIdPersonal());
						((DefaultTableModel) model).removeRow(modelRow);
					}

					llenarTablaFleteros();

				} catch (Exception exc) {
					// TODO ATRAPAR ERROR
					exc.printStackTrace();
				}
			}

		};
		return borrarFletero;

	}

	private Action editarFletero() {
		Action editarFletero = new AbstractAction() {

			private static final long serialVersionUID = -1791065090765145181L;

			@Override
			public void actionPerformed(ActionEvent a) {

				JTable table = (JTable) a.getSource();
				ventanaFletero = new VentanaNuevoFletero(vistaPrincipal);

				try {
					FleteroDTO fletero = (FleteroDTO) table.getModel().getValueAt(table.getSelectedRow(), 0);
					new ControladorVentanaNuevoFletero(ventanaFletero, modelo, fletero, true, usuarioLogueado);

					llenarTablaFleteros();

				} catch (Exception exc) {
					exc.printStackTrace();
				}

			}
		};

		return editarFletero;

	}

	public Action verFletero() {
		Action verFletero = new AbstractAction() {

			private static final long serialVersionUID = 5147297273785951828L;

			@Override
			public void actionPerformed(ActionEvent a) {

				JTable table = (JTable) a.getSource();
				ventanaFletero = new VentanaNuevoFletero(vistaPrincipal);

				try {
					FleteroDTO fletero = (FleteroDTO) table.getModel().getValueAt(table.getSelectedRow(), 0);
					new ControladorVentanaNuevoFletero(ventanaFletero, modelo, fletero, false, usuarioLogueado);

					llenarTablaFleteros();

				} catch (Exception exc) {
					exc.printStackTrace();
				}

			}
		};

		return verFletero;
	}

	private Action borrarCliente() {
		Action borrarCliente = new AbstractAction() {

			private static final long serialVersionUID = -5021431911307537308L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				try {
					int respuesta = JOptionPane.showConfirmDialog(vistaPrincipal, "¿Está seguro que desea eliminar el cliente?", null, JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_OPTION) {
						modelo.borrarCliente((ClienteDTO) table.getModel().getValueAt(table.getSelectedRow(), 1), usuarioLogueado.getIdPersonal());
						((DefaultTableModel) table.getModel()).removeRow(modelRow);
						clientes_en_tabla = null;
						// Por si se vuelve a cargar la tabla de clientes.
					}
				} catch (Exception e1) {
					// TODO ATRAPAR ERROR
					e1.printStackTrace();
				}
			}
		};
		return borrarCliente;
	}

	@SuppressWarnings("serial")
	private void llenarTablaEnvios(FleteroDTO fletero) {
		try {

			String[] columns = { "Nº", "Fecha Envío", "" };
			DefaultTableModel dtm = new DefaultTableModel(null, columns) {
				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 2)
						return true;
					else
						return false;
				}
			};
			dtm.setColumnIdentifiers(columns);

			envios_asignados = new LinkedList<EnvioDTO>();

			for (EnvioDTO e : modelo.obtenerEnviosPor(fletero)) {
				if (!envios_asignados.contains(e))
					envios_asignados.add(e);
			}

			for (int i = 0; i < envios_asignados.size(); i++) {
				EnvioDTO envio = envios_asignados.get(i);
				envio.setDetalles(this.modelo.obtenerDetalles(envio));
			}

			for (EnvioDTO e : envios_asignados) {
				Object[] fila = { e, calendar2str(e.getFechaEnvio()), "VER" };
				dtm.addRow(fila);
			}

			JTable tableEnvios = this.vistaPrincipal.getTableEnvios();
			tableEnvios.setModel(dtm);

			new ButtonColumn(tableEnvios, verEnvio(), 2);

			tableEnvios.getColumnModel().getColumn(2).setMinWidth(200);
			tableEnvios.getColumnModel().getColumn(2).setMaxWidth(200);

		} catch (Exception e) {
			// TODO ATRAPAR ERROR
			e.printStackTrace();
		}
	}

	public Action verEnvio() {
		Action ver = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent a) {

				JTable table = (JTable) a.getSource();
				EnvioDTO envio = (EnvioDTO) table.getModel().getValueAt(table.getSelectedRow(), 0);

				VentanaEnvio ventanaEnvio = new VentanaEnvio(vistaPrincipal);
				ControladorEnvio controladorEnvio = new ControladorEnvio(ventanaEnvio, modelo, envio);

				llenarTablaEnvios((FleteroDTO) vistaPrincipal.getComboFleteros().getSelectedItem());
			}
		};
		return ver;
	}

	public Action verHojaRuta() {
		Action verHoja = new AbstractAction() {

			private static final long serialVersionUID = -1107233758997912440L;

			@Override
			public void actionPerformed(ActionEvent a) {

				int row = Integer.valueOf(a.getActionCommand());

				Reporte reporte = new Reporte();
				try {
					reporte.ReporteHojaRuta((FleteroDTO) vistaPrincipal.getTableFleteros().getModel().getValueAt(row, 0));
					reporte.mostrar();
				} catch (Exception e1) {
					// TODO ATRAPAR ERROR
					e1.printStackTrace();
				}
			}
		};

		return verHoja;
	}

	public Action editarCliente() {
		Action editarCliente = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				ventanaCliente = new VentanaAltaModifCliente(vistaPrincipal);
				try {
					new ControladorCliente(ventanaCliente, modelo, (ClienteDTO) table.getModel().getValueAt(table.getSelectedRow(), 1), true, false, usuarioLogueado);
					clientes_en_tabla = null;
					llenarTablaClientes();
					ordenes_en_tabla_UT = null;
					llenarTablaOT_UT(vistaPrincipal.getComboEstadosUT().getSelectedItem().toString());
				} catch (Exception e2) {
					// TODO ATRAPAR ERROR
					e2.printStackTrace();
				}
			}
		};
		return editarCliente;
	}

	public Action verCliente() {
		Action verCliente = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				ventanaCliente = new VentanaAltaModifCliente(vistaPrincipal);
				try {
					new ControladorCliente(ventanaCliente, modelo, (ClienteDTO) table.getModel().getValueAt(table.getSelectedRow(), 1), false, false, usuarioLogueado);
					marcas = modelo.obtenerMarcas();
					llenarTablaElectrodomesticos();
					// Pudo haber creado una OT con ED y marcas nuevos.
					llenarTablaOT_UT(vistaPrincipal.getComboEstadosUT().getSelectedItem().toString());
				} catch (Exception e2) {
					// TODO ATRAPAR ERROR
					e2.printStackTrace();
				}
			}
		};
		return verCliente;
	}

	private Action presupuestarOT() {
		Action presupuestar = new AbstractAction() {

			private static final long serialVersionUID = -2772361624419816539L;

			@Override
			public void actionPerformed(ActionEvent e) {

				ventanaPresupuesto = new VentanaTecnicoOT(vistaPrincipal);

				ordenApresupuestar = (OrdenDTO) vistaPrincipal.getTableOT_UT().getValueAt(vistaPrincipal.getTableOT_UT().getSelectedRow(), 0);
				new ControladorTecnicoOT(ventanaPresupuesto, modelo, ordenApresupuestar, usuarioLogueado);
				llenarTablaOT_UT(vistaPrincipal.getComboEstadosUT().getSelectedItem().toString());
			}
		};
		return presupuestar;
	}

	private Action reparar() {
		Action reparar = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaPresupuesto = new VentanaTecnicoOT(vistaPrincipal);

				ordenApresupuestar = (OrdenDTO) vistaPrincipal.getTableOT_UT().getValueAt(vistaPrincipal.getTableOT_UT().getSelectedRow(), 0);
				new ControladorTecnicoOT(ventanaPresupuesto, modelo, ordenApresupuestar, usuarioLogueado);

				llenarTablaOT_UT(vistaPrincipal.getComboEstadosUT().getSelectedItem().toString());
			}
		};
		return reparar;
	}

	@SuppressWarnings("serial")
	private Action verSC() {
		Action ver = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JTable table = (JTable) e.getSource();
				VentanaNuevaSC ventanaSC = new VentanaNuevaSC(vistaPrincipal);
				SolicitudCompraDTO sc = obtenerScSeleccionada((int) table.getModel().getValueAt(table.getSelectedRow(), 0));

				if (sc != null)
					new ControladorSC(ventanaSC, modelo, sc, false, usuarioLogueado);
				llenarTablaScs();
			}

			private SolicitudCompraDTO obtenerScSeleccionada(int id) {
				for (SolicitudCompraDTO s : solicitudes_en_tabla)
					if (s.getId() == id)
						return s;
				return null;
			}
		};
		return ver;
	}

	private Action editarSC() {
		Action editarSC = new AbstractAction() {

			private static final long serialVersionUID = -1791065090765145181L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				VentanaNuevaSC ventanaSC = new VentanaNuevaSC(vistaPrincipal);
				SolicitudCompraDTO sc = obtenerScSeleccionada((int) table.getModel().getValueAt(table.getSelectedRow(), 0));
				if (sc != null)
					new ControladorSC(ventanaSC, modelo, sc, true, usuarioLogueado);
				llenarTablaScs();
			}

			private SolicitudCompraDTO obtenerScSeleccionada(int id) {
				for (SolicitudCompraDTO s : solicitudes_en_tabla)
					if (s.getId() == id)
						return s;
				return null;
			}
		};
		return editarSC;
	}

	private Action accionAlerta() {
		Action accionAlerta = new AbstractAction() {

			private static final long serialVersionUID = -1791065090765145181L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				VentanaNuevaSC ventanaSC = new VentanaNuevaSC(vistaPrincipal);

				if (((String) table.getModel().getValueAt(table.getSelectedRow(), 7)).equals(BUTTON_NAME_SOLICITAR))
					new ControladorSC(ventanaSC, modelo, usuarioLogueado);
				else if (((String) table.getModel().getValueAt(table.getSelectedRow(), 7)).equals(BUTTON_NAME_VER_SOLICITUD))
					new ControladorSC(ventanaSC, modelo, (SolicitudCompraDTO) table.getModel().getValueAt(table.getSelectedRow(), 5), true, usuarioLogueado);
				llenarTablaAlertas();
			}

		};
		return accionAlerta;
	}

	private Action borrarProveedor() {
		Action borrarProveedor = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				try {
					int respuesta = JOptionPane.showConfirmDialog(vistaPrincipal, "¿Está seguro que desea eliminar este proveedor?", null, JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_OPTION) {
						TableModel model = table.getModel();
						modelo.borrarProveedor((ProveedorDTO) model.getValueAt(table.getSelectedRow(), 1), usuarioLogueado);
						((DefaultTableModel) model).removeRow(modelRow);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		return borrarProveedor;
	}

	private Action editarProveedor() {
		Action editarProveedor = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				ventanaProveedor = new VentanaAltaModifProveedor(vistaPrincipal);
				try {
					new ControladorProveedor(ventanaProveedor, modelo, marcas, (ProveedorDTO) table.getModel().getValueAt(table.getSelectedRow(), 1), proveedores_en_tabla, true);
					llenarTablaProveedores();
					marcas = modelo.obtenerMarcas();
				} catch (Exception e2) {
					// TODO ATRAPAR ERROR
					e2.printStackTrace();
				}
			}
		};
		return editarProveedor;
	}

	private Action verProveedor() {
		Action verProveedor = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				ventanaProveedor = new VentanaAltaModifProveedor(vistaPrincipal);
				try {
					new ControladorProveedor(ventanaProveedor, modelo, marcas, (ProveedorDTO) table.getModel().getValueAt(table.getSelectedRow(), 1), proveedores_en_tabla, false);
				} catch (Exception e2) {
					// TODO ATRAPAR ERROR
					e2.printStackTrace();
				}
			}
		};
		return verProveedor;
	}

	private Action verPieza() {
		Action verPieza = new AbstractAction() {

			private static final long serialVersionUID = 7168413639461853L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				ventanaPieza = new VentanaAltaModifPieza(vistaPrincipal);
				try {
					new ControladorPieza(ventanaPieza, modelo, (PiezaDTO) table.getModel().getValueAt(table.getSelectedRow(), 2), ControladorPieza.tipo_vista.VER);
				} catch (Exception e2) {
					// TODO Mensaje no se puede ver la pieza.
					e2.printStackTrace();
				}
				llenarTablaPiezas();
			}
		};
		return verPieza;
	}

	private Action editarPieza() {
		Action editarPieza = new AbstractAction() {

			private static final long serialVersionUID = 6169817258155359124L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				ventanaPieza = new VentanaAltaModifPieza(vistaPrincipal);
				try {
					new ControladorPieza(ventanaPieza, modelo, (PiezaDTO) table.getModel().getValueAt(table.getSelectedRow(), 2), ControladorPieza.tipo_vista.EDITAR);
				} catch (Exception e2) {
					// TODO Mensaje no se puede ver la pieza.
					e2.printStackTrace();
				}
				llenarTablaPiezas();
			}
		};
		return editarPieza;
	}

	private Action borrarPieza() {
		Action borrarPieza = new AbstractAction() {

			private static final long serialVersionUID = 5734405772071836193L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				ventanaPieza = new VentanaAltaModifPieza(vistaPrincipal);
				try {
					int respuesta = JOptionPane.showConfirmDialog(vistaPrincipal, "¿Está seguro que desea eliminar la pieza?", null, JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_OPTION) {
						modelo.eliminarPieza((PiezaDTO) table.getModel().getValueAt(table.getSelectedRow(), 2), usuarioLogueado.getIdPersonal());
						((DefaultTableModel) table.getModel()).removeRow(modelRow);
						// Por si se vuelve a cargar la tabla de piezas.
					}
				} catch (Exception e1) {
					// TODO ATRAPAR ERROR
					e1.printStackTrace();
				}
				llenarTablaPiezas();
			}
		};
		return borrarPieza;
	}

	private Action modificarStock() {
		Action modificarPieza = new AbstractAction() {

			private static final long serialVersionUID = 8324732515266641438L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				ventanaPieza = new VentanaAltaModifPieza(vistaPrincipal);
				try {
					new ControladorPieza(ventanaPieza, modelo, (PiezaDTO) table.getModel().getValueAt(table.getSelectedRow(), 2), ControladorPieza.tipo_vista.REAJUSTE_STOCK);
				} catch (Exception e2) {
					// TODO Mensaje no se puede ver la pieza.
					e2.printStackTrace();
				}
				llenarTablaPiezas();
			}
		};
		return modificarPieza;
	}

	private Action borrarElectrodomestico() {
		Action borrarElectrodomestico = new AbstractAction() {

			private static final long serialVersionUID = 1723778348788093381L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				try {
					int respuesta = JOptionPane.showConfirmDialog(vistaPrincipal, "¿Está seguro que desea eliminar el electrodoméstico?", null, JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_OPTION) {
						modelo.borrarElectrodomestico((ElectrodomesticoDTO) table.getModel().getValueAt(table.getSelectedRow(), 2), usuarioLogueado.getIdPersonal());
						((DefaultTableModel) table.getModel()).removeRow(modelRow);
						llenarTablaElectrodomesticos();
					}
				} catch (Exception e1) {
					// TODO ATRAPAR ERROR
					e1.printStackTrace();
				}
			}
		};
		return borrarElectrodomestico;
	}

	public Action editarElectrodomestico() {
		Action editarElectrodomestico = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				try {
					ventanaElectrodomestico = new VentanaAltaModifElectrod(vistaPrincipal);
					new ControladorElectrodomestico(ventanaElectrodomestico, modelo, (ElectrodomesticoDTO) table.getModel().getValueAt(table.getSelectedRow(), 2));
					llenarTablaElectrodomesticos();
					marcas = modelo.obtenerMarcas();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		};
		return editarElectrodomestico;
	}

	private Action verOT_UA() {

		Action verOT = new AbstractAction() {
			private static final long serialVersionUID = 7386169802184763591L;

			@Override
			public void actionPerformed(ActionEvent e) {

				ventanaOT = new VentanaAdminOT(vistaPrincipal);

				ordenAcargar = (OrdenDTO) vistaPrincipal.getTableOT_UA().getValueAt(vistaPrincipal.getTableOT_UA().getSelectedRow(), 0);
				new ControladorAdminOT(ventanaOT, modelo, ordenAcargar, usuarioLogueado, false);

				llenarTablaOT_UA(vistaPrincipal.getComboEstadosUA().getSelectedItem().toString());
			}
		};
		return verOT;

	}

	private Action borrarUsuario() {
		Action borrarUsuario = new AbstractAction() {

			private static final long serialVersionUID = -6922096709923256228L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				try {
					int respuesta = JOptionPane.showConfirmDialog(vistaPrincipal, "¿Está seguro que desea eliminar al usuario?", null, JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_OPTION) {
						modelo.borrarUsuario((UsuarioDTO) table.getModel().getValueAt(table.getSelectedRow(), 0), usuarioLogueado);// Usuario
						((DefaultTableModel) table.getModel()).removeRow(modelRow);
						usuarios_comunes_en_tabla = null;
						llenarTablaUsuarios();
					}
				} catch (Exception e1) {
					// TODO ATRAPAR ERROR
					e1.printStackTrace();
				}
			}
		};
		return borrarUsuario;
	}

	private Action blanquearUsuario() {
		Action blanquearUsuario = new AbstractAction() {

			private static final long serialVersionUID = 7263285411499949529L;

			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				try {
					int respuesta = JOptionPane.showConfirmDialog(vistaPrincipal, "¿Está seguro que desea reestablecer la contraseña?", null, JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_OPTION) {
						((UsuarioDTO) table.getModel().getValueAt(table.getSelectedRow(), 0)).setContrasenia("qwerasdf");
						modelo.actualizarUsuario(((UsuarioDTO) table.getModel().getValueAt(table.getSelectedRow(), 0)));
						JOptionPane.showMessageDialog(vistaPrincipal, "La contraseña se ha reestablecido correctamente a \"qwerasdf\".", "Contraseña reestablecida", JOptionPane.INFORMATION_MESSAGE, null);
						llenarTablaUsuarios();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		return blanquearUsuario;
	}

	private Action editarUsuario() {
		Action editarUsuario = new AbstractAction() {

			private static final long serialVersionUID = -8113347557377032502L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				try {
					ventanaUsuario = new VentanaAltaModifUsuario(vistaPrincipal);
					new ControladorUsuario(ventanaUsuario, modelo, usuarios_comunes_en_tabla, (UsuarioDTO) table.getModel().getValueAt(table.getSelectedRow(), 1));
					llenarTablaUsuarios();
					// Problemas con el editar cuando chequea si ya existe.
				} catch (Exception e2) {
					// TODO ATRAPAR ERROR
					e2.printStackTrace();
				}
			}
		};
		return editarUsuario;
	}

	public void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 50; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + 1, width);
			}
			columnModel.getColumn(column).setPreferredWidth(width);
		}
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.vistaPrincipal.getBtnAgregarCliente()) {
			this.ventanaCliente = new VentanaAltaModifCliente(this.vistaPrincipal);
			ControladorCliente controladorCliente = new ControladorCliente(ventanaCliente, modelo);
			llenarTablaClientes();

		} else if (e.getSource() == this.vistaPrincipal.getBtnNuevaOt()) {
			try {

				this.ventanaOT = new VentanaAdminOT(this.vistaPrincipal);
				ControladorAdminOT controladorOT = new ControladorAdminOT(ventanaOT, modelo, usuarioLogueado);
				marcas = modelo.obtenerMarcas();
				llenarTablaOT_UA(vistaPrincipal.getComboEstadosUA().getSelectedItem().toString());

			} catch (Exception e2) {
				// TODO: handle exception
			}
		} else if (e.getSource() == this.vistaPrincipal.getBtnNuevaSc()) {
			VentanaNuevaSC ventanaSC = new VentanaNuevaSC(this.vistaPrincipal);
			ControladorSC controladorSC = new ControladorSC(ventanaSC, modelo, usuarioLogueado);
			llenarTablaScs();

		} else if (e.getSource() == this.vistaPrincipal.getBtnAadirPiezas()) {
			this.ventanaPieza = new VentanaAltaModifPieza(this.vistaPrincipal);
			try {
				ControladorPieza controladorPieza = new ControladorPieza(this.ventanaPieza, this.modelo);
				llenarTablaPiezas();

			} catch (Exception e1) {
				// TODO ATRAPAR ERROR
				e1.printStackTrace();
			}
		} else if (e.getSource() == this.vistaPrincipal.getBtnAgregarUsuario()) {
			this.ventanaUsuario = new VentanaAltaModifUsuario(this.vistaPrincipal);
			ControladorUsuario controladorUsuario = new ControladorUsuario(this.ventanaUsuario, this.modelo, this.usuarios_comunes_en_tabla);
			llenarTablaUsuarios();

		} else if (e.getSource() == this.vistaPrincipal.getBtnNuevoProveedor()) {
			try {

				this.ventanaProveedor = new VentanaAltaModifProveedor(this.vistaPrincipal);
				ControladorProveedor controladorProveedor = new ControladorProveedor(this.ventanaProveedor, this.modelo, this.marcas, this.proveedores_en_tabla);
				llenarTablaProveedores();
				marcas = modelo.obtenerMarcas();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		} else if (e.getSource() == this.vistaPrincipal.getBtnNuevoFletero()) {

			this.ventanaFletero = new VentanaNuevoFletero(this.vistaPrincipal);
			ControladorVentanaNuevoFletero controladorFletero = new ControladorVentanaNuevoFletero(this.ventanaFletero, this.modelo, usuarioLogueado);
			llenarTablaFleteros();

		} else if (e.getSource() == this.vistaPrincipal.getBtnCrearBackup()) {

			try {

				JFileChooser f = new JFileChooser();
				f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				f.showSaveDialog(null);
				// TODO HAY QUE PONER CONFIGURACION EN UN PROPERTIES
				String bat = "mysqldump -ugrupo1 -pgrupo1 20161_service_g1 > " + f.getSelectedFile().toString() + "\\backup.sql";

				final File file = new File("backup.bat");
				file.createNewFile();
				PrintWriter writer = new PrintWriter(file, "UTF-8");
				writer.println(bat);
				writer.close();

				Process p = Runtime.getRuntime().exec("cmd /c backup.bat");
				p.waitFor();
				file.delete();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "No se creó el backup. " + ex.getMessage());
			}

		} else if (e.getSource() == this.vistaPrincipal.getBtnCargarBackup()) {


			JFileChooser f = new JFileChooser();
			f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			f.showOpenDialog(null);

			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					String bat = "mysql -ugrupo1 -pgrupo1 20161_service_g1 < " + f.getSelectedFile().toString();

					try {
						final File file = new File("backup.bat");
						file.createNewFile();
						PrintWriter writer = new PrintWriter(file, "UTF-8");
						writer.println(bat);
						writer.close();

						Process p = Runtime.getRuntime().exec("cmd /c backup.bat");
						p.waitFor();
						file.delete();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "No se cargó el backup correctamente. " + ex.getMessage());
					}

				}
			});
			try {
				generateThread(thread);
			} catch (InterruptedException e1) {
				JOptionPane.showMessageDialog(null, "No se cargó el backup correctamente. " + e1.getMessage());
			}

			actualizarTabla();

		} else if (e.getSource() == this.vistaPrincipal.getBtnAgregarEd()) {
			try {
				this.ventanaElectrodomestico = new VentanaAltaModifElectrod(this.vistaPrincipal);
				ControladorElectrodomestico controladorEd = new ControladorElectrodomestico(ventanaElectrodomestico, modelo);
				marcas = modelo.obtenerMarcas();
				llenarTablaElectrodomesticos();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		} else if (e.getSource() == this.vistaPrincipal.getMntmDatos()) {
			ventanaUsuario = new VentanaAltaModifUsuario(vistaPrincipal);
			new ControladorUsuario(ventanaUsuario, modelo, usuarios_comunes_en_tabla, usuarioLogueado);
			llenarTablaUsuarios();

		} else if (e.getSource() == this.vistaPrincipal.getMntmSalir()) {
			VentanaLogueo ventanaLogueo = new VentanaLogueo();
			ControladorLogueo controladorLogueo = new ControladorLogueo(ventanaLogueo, modelo);
			controladorLogueo.iniciar();
			this.vistaPrincipal.dispose();

		} else if (e.getSource() == this.vistaPrincipal.getBtnEnviarMailOT()) {
			VentanaMail ventanaMail = new VentanaMail(vistaPrincipal);
			ControladorMail controladorMail = new ControladorMail(ventanaMail, this.modelo);

		} else if (e.getSource() == this.vistaPrincipal.getBtnEnviarMailSC()) {
			ventanaMail = new VentanaMail(vistaPrincipal);
			ControladorMail controladorMail = new ControladorMail(ventanaMail, this.modelo);

		} else if (e.getSource() == this.vistaPrincipal.getMntmActualizar())
			actualizarTabla();
		else if (e.getSource() == this.vistaPrincipal.getBtnAsignarEnvio()) {

			ordenes_a_enviar = new LinkedList<OrdenDTO>();
			ordenes_a_enviar = obtenerReparadasDeliveryPendientes(7);

			if (ordenes_a_enviar.isEmpty())
				JOptionPane.showMessageDialog(null, "No hay ordenes reparadas para ser enviadas.");
			else {
				FleteroDTO fleteroElegido = (FleteroDTO) this.vistaPrincipal.getComboFleteros().getSelectedItem();

				VentanaAsignacion ventanaAsignacion = new VentanaAsignacion(this.vistaPrincipal);
				new ControladorAsignacion(ventanaAsignacion, modelo, fleteroElegido, ordenes_a_enviar);

				llenarTablaEnvios((FleteroDTO) this.vistaPrincipal.getComboFleteros().getSelectedItem());
			}

		} else if (e.getSource() == this.vistaPrincipal.getMntmEditarZonas()) {

			VentanaZonas ventanaZonas = new VentanaZonas(this.vistaPrincipal);
			ControladorZonas controladorZonas = new ControladorZonas(ventanaZonas, modelo);

		} else if (e.getSource() == this.vistaPrincipal.getBtnVerContabilidad()) {
			if (this.vistaPrincipal.getFechaInicio().getModel().getValue() != null && this.vistaPrincipal.getFechaFin().getModel().getValue() != null) {
				String[] columns = { "Solicitud/Orden", "Nro. solicitud/orden", "Cliente/Proveedor", "Importe", "Fecha", "Ingreso/Egreso", "" };
				DefaultTableModel dtm = new DefaultTableModel(null, columns) {
					private static final long serialVersionUID = -6605937517476172425L;

					@Override
					public boolean isCellEditable(int row, int column) {
						if (column == 6)
							return true;
						else
							return false;
					}
				};
				dtm.setColumnIdentifiers(columns);

				try {
					movimientosContables = modelo.obtenerMovimientos((Calendar) this.vistaPrincipal.getFechaInicio().getModel().getValue(), (Calendar) this.vistaPrincipal.getFechaFin().getModel().getValue());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				for (OrdenDTO o : movimientosContables.getIngresos()) {
					double importe = 0.0;
					for (PiezaDTO p : o.getPiezasPresupuestadas()) {
						importe += p.getPrecio_venta();
					}
					importe += o.getManoDeObra() + o.getCostoDeEnvio();
					Object[] fila = { "Orden", o, o.getCliente().toString(), importe, o.getFechaReparado().getTime(), "Ingreso", BUTTON_NAME_DETALLES };
					dtm.addRow(fila);
				}

				for (SolicitudCompraDTO sc : movimientosContables.getEgresos()) {
					double importe = 0.0;
					for (PrecioPiezaDTO p : sc.getPiezas()) {
						importe += (p.getPrecio() * p.getCantidad());
					}
					Object[] fila = { "Solicitud", sc, sc.getProveedor(), importe, sc.getFechaProcesada().getTime(), "Egreso", BUTTON_NAME_DETALLES };
					dtm.addRow(fila);
				}

				this.vistaPrincipal.getTableContabilidad().setModel(dtm);
				new ButtonColumn(this.vistaPrincipal.getTableContabilidad(), verDetallesContabilidad(), 6);
				this.vistaPrincipal.getTableContabilidad().setDefaultRenderer(Object.class, new FormatoTablaContabilidad());
			} else {
				JOptionPane.showMessageDialog(null, "Debe seleccionar ambas fechas para poder visualizar el reporte contable.", "¡Atención!", JOptionPane.WARNING_MESSAGE);
			}
		} else if (e.getSource() == this.vistaPrincipal.getRdSemana()) {
			limpiarTextBox();
			deshabilitarDPI();
			this.vistaPrincipal.getDpiSemana().setVisible(true);
			this.vistaPrincipal.getDpiSemana().getJFormattedTextField().setVisible(true);
		} else if (e.getSource() == this.vistaPrincipal.getRdMes()) {
			limpiarTextBox();
			deshabilitarDPI();
			this.vistaPrincipal.getDpiMes().setVisible(true);
			this.vistaPrincipal.getDpiMes().getJFormattedTextField().setVisible(true);
		} else if (e.getSource() == this.vistaPrincipal.getRdAnio()) {
			limpiarTextBox();
			deshabilitarDPI();
			this.vistaPrincipal.getDpiAnio().setVisible(true);
			this.vistaPrincipal.getDpiAnio().getJFormattedTextField().setVisible(true);
		} else if (e.getSource() == this.vistaPrincipal.getRdPersonalizado()) {
			limpiarTextBox();
			deshabilitarDPI();
			this.vistaPrincipal.getDpiPersonalizado1().setVisible(true);
			this.vistaPrincipal.getDpiPersonalizado1().getJFormattedTextField().setVisible(true);
			this.vistaPrincipal.getDpiPersonalizado2().setVisible(true);
			this.vistaPrincipal.getDpiPersonalizado2().getJFormattedTextField().setVisible(true);
			this.vistaPrincipal.getLblDesdePersonalizado().setVisible(true);
			this.vistaPrincipal.getLblHastaPersonalizado().setVisible(true);

		} else if (e.getSource() == this.vistaPrincipal.getBtnGenerarReporte()) {

			String reporteElegido = this.vistaPrincipal.getComboReportes().getSelectedItem().toString();

			if (this.vistaPrincipal.getTxtReporteDesde() != null && this.vistaPrincipal.getTxtReporteHasta() != null) {
				if (this.vistaPrincipal.getGroup().getSelection() != null) {

					Reporte reporte = new Reporte();
					Calendar ini = null;
					Calendar fin = null;
					try {
						ini = string2calendar(this.vistaPrincipal.getTxtReporteDesde().getText().toString());
						fin = string2calendar(this.vistaPrincipal.getTxtReporteHasta().getText().toString());
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					switch (reporteElegido) {

					case "(seleccionar)":
						JOptionPane.showMessageDialog(null, "Debe seleccionar un reporte.");
						break;

					case "Electrodomésticos más usados":
						try {
							reporte.ReporteRankElectrodomesticos(ini, fin);
							reporte.mostrar();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						break;

					case "Piezas más insumidas":
						try {
							reporte.ReporteRankPiezas(ini, fin);
							reporte.mostrar();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						break;

					case "Reporte Contable":
						try {
							movimientosContables = modelo.obtenerMovimientos(ini, fin);
							reporte.ReporteContable(movimientosContables);
							reporte.mostrar();

						} catch (Exception e2) {
							e2.printStackTrace();
						}
						break;

					case "Proveedores Incumplidores":
						try {
							reporte.RankProveedoresIncumplidores(ini, fin);
							reporte.mostrar();

						} catch (Exception e2) {
							e2.printStackTrace();
						}
						break;

					default:
						break;
					}

				} else
					JOptionPane.showMessageDialog(null, "Debe indicar el filtro de fechas que va a utilizar.");
			} else
				JOptionPane.showMessageDialog(null, "Debe seleccionar un rango de fechas.");

		} else if (e.getSource() == this.vistaPrincipal.getBtnReporteContable()) {

			if (this.vistaPrincipal.getFechaInicio().getModel().getValue() != null && this.vistaPrincipal.getFechaFin().getModel().getValue() != null) {
				try {
					movimientosContables = modelo.obtenerMovimientos((Calendar) this.vistaPrincipal.getFechaInicio().getModel().getValue(), (Calendar) this.vistaPrincipal.getFechaFin().getModel().getValue());

				} catch (Exception e2) {
					// TODO: handle exception
				}
				Reporte reporte = new Reporte();
				try {
					reporte.ReporteContable(movimientosContables);
					reporte.mostrar();
				} catch (Exception e1) {
					// TODO ATRAPAR ERROR
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Debe seleccionar un rango de fechas.");
			}

		} else if (e.getSource() == this.vistaPrincipal.getBtnVerEntregados()) {
			llenarTablaEntregadas();

		} else if (e.getSource() == this.vistaPrincipal.getBtnEnvios()) {

			Reporte reporte = new Reporte();
			try {
				reporte.ReporteEnvios();
				reporte.mostrar();
			} catch (Exception e1) {
				// TODO ATRAPAR ERROR
				e1.printStackTrace();
			}

		} else if (e.getSource() == this.vistaPrincipal.getBtnBack()) {
			llenarTablaEnvios((FleteroDTO) this.vistaPrincipal.getComboFleteros().getSelectedItem());
		}

		else if (e.getSource() == this.vistaPrincipal.getMntmEditarLocalidades()) {
			NuevaLocalidad ventanaLocalidad = new NuevaLocalidad(this.vistaPrincipal);
			ControladorLocalidad controladorLocalidad = new ControladorLocalidad(ventanaLocalidad, modelo, true);

		} else if (e.getSource() == this.vistaPrincipal.getBtnBuscarOT()) {
			VentanaBuscadorOT buscadorOT = new VentanaBuscadorOT(this.vistaPrincipal);
			ControladorBuscadorOT controladorBuscadorOT = new ControladorBuscadorOT(buscadorOT, this.modelo, this.usuarioLogueado, this.vistaPrincipal);

		} else if (e.getSource() == this.vistaPrincipal.getBtnBuscarCliente()) {
			VentanaBuscadorCliente buscadorCliente = new VentanaBuscadorCliente(this.vistaPrincipal);
			ControladorBuscadorCliente controladorBuscador = new ControladorBuscadorCliente(buscadorCliente, this.modelo, this.vistaPrincipal, this.usuarioLogueado);

		} else if (e.getSource() == this.vistaPrincipal.getMntmImportarPrecios()) {
			VentanaSeleccionarArchivo seleccionarArchivo = new VentanaSeleccionarArchivo(this.vistaPrincipal);
			ControladorSeleccionarArchivo controladorSeleccionar = new ControladorSeleccionarArchivo(seleccionarArchivo, modelo);
		}

	}

	public void llenarTablaEntregadas() {

		String[] columns = { "Nº OT", "Cliente", "Electrodoméstico", "Fecha Entregado" };
		DefaultTableModel dtm = new DefaultTableModel(null, columns);
		dtm.setColumnIdentifiers(columns);

		List<OrdenDTO> entregadas = new LinkedList<OrdenDTO>();
		try {
			for (EnvioDTO e : this.modelo.obtenerEnvios()) {
				e.setDetalles(this.modelo.obtenerDetalles(e));

				for (DetallesEnvioDTO d : e.getDetalles()) {
					if (d.isEntregado() && !entregadas.contains(d.getOt())) {
						entregadas.add(d.getOt());
						Object[] fila = { d.getOt().getIdOT(), d.getOt().getCliente(), d.getOt().getElectrodomestico(), calendar2str(e.getFechaEnvio()) };
						dtm.addRow(fila);
					}
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		this.vistaPrincipal.getTableEnvios().setModel(dtm);
	}

	private void generateThread(Thread mail) throws InterruptedException {
		ProgressWork work = new ProgressWork(ventanaMail, "Por favor aguarde mientras se esta cargando el backup.", mail,"/recursos/db.gif");
		work.mostrar();
	}

	public Calendar string2calendar(String txt) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date = sdf.parse(txt);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar;
	}

	public String calendar2str(Calendar cal) {

		Date date = cal.getTime();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = df.format(date);
		return fecha;
	}

	private void limpiarTextBox() {
		this.vistaPrincipal.getTxtReporteDesde().setText("");
		this.vistaPrincipal.getTxtReporteHasta().setText("");
	}

	private void deshabilitarDPI() {
		this.vistaPrincipal.getDpiSemana().setVisible(false);
		this.vistaPrincipal.getDpiSemana().getJFormattedTextField().setVisible(false);
		this.vistaPrincipal.getDpiSemana().getModel().setValue(null);
		this.vistaPrincipal.getDpiMes().setVisible(false);
		this.vistaPrincipal.getDpiMes().getJFormattedTextField().setVisible(false);
		this.vistaPrincipal.getDpiMes().getModel().setValue(null);
		this.vistaPrincipal.getDpiAnio().setVisible(false);
		this.vistaPrincipal.getDpiAnio().getJFormattedTextField().setVisible(false);
		this.vistaPrincipal.getDpiAnio().getModel().setValue(null);
		this.vistaPrincipal.getDpiPersonalizado1().setVisible(false);
		this.vistaPrincipal.getDpiPersonalizado1().getJFormattedTextField().setVisible(false);
		this.vistaPrincipal.getDpiPersonalizado1().getModel().setValue(null);
		this.vistaPrincipal.getDpiPersonalizado2().setVisible(false);
		this.vistaPrincipal.getDpiPersonalizado2().getJFormattedTextField().setVisible(false);
		this.vistaPrincipal.getDpiPersonalizado2().getModel().setValue(null);
		this.vistaPrincipal.getLblDesdePersonalizado().setVisible(false);
		this.vistaPrincipal.getLblHastaPersonalizado().setVisible(false);
	}

	private Action verDetallesContabilidad() {
		Action detallesContabilidad = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				Object movimiento = table.getModel().getValueAt(table.getSelectedRow(), 1);
				if (movimiento.getClass().getName().equals("dto.OrdenDTO")) {
					Reporte reporte = new Reporte();
					try {
						reporte.ReporteOtGeneral((OrdenDTO) movimiento);
						reporte.mostrar();
					} catch (Exception e1) {
						// TODO ATRAPAR ERROR
						e1.printStackTrace();
					}
				} else if (movimiento.getClass().getName().equals("dto.SolicitudCompraDTO")) {
					Reporte reporte = new Reporte();
					try {
						reporte.ReporteScGeneral((SolicitudCompraDTO) movimiento);
						reporte.mostrar();
					} catch (Exception e1) {
						// TODO ATRAPAR ERROR
						e1.printStackTrace();
					}
				}
			}
		};
		return detallesContabilidad;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		if (e.getSource() == this.vistaPrincipal.getCbMarcas() && this.vistaPrincipal.getCbMarcas().getSelectedItem() != null)
			llenarTablaPiezas();

		else if (e.getSource() == this.vistaPrincipal.getComboEstadosUA()) {
			llenarTablaOT_UA(this.vistaPrincipal.getComboEstadosUA().getSelectedItem().toString());
		}

		else if (e.getSource() == this.vistaPrincipal.getComboEstadosUT()) {
			llenarTablaOT_UT(this.vistaPrincipal.getComboEstadosUT().getSelectedItem().toString());
		}

		else if (e.getSource() == this.vistaPrincipal.getComboFleteros()) {
			llenarTablaEnvios((FleteroDTO) this.vistaPrincipal.getComboFleteros().getSelectedItem());
		}

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		actualizarTabla();
	}

	private void actualizarTabla() {
		if (this.vistaPrincipal.getTabClientes().isShowing())
			llenarTablaClientes();
		if (this.vistaPrincipal.getTabOT_UA().isShowing())
			llenarTablaOT_UA(this.vistaPrincipal.getComboEstadosUA().getSelectedItem().toString());
		if (this.vistaPrincipal.getTabOT_UT().isShowing())
			llenarTablaOT_UT(this.vistaPrincipal.getComboEstadosUT().getSelectedItem().toString());
		if (this.vistaPrincipal.getPanelScs().isShowing())
			llenarTablaScs();
		if (this.vistaPrincipal.getPanelProveedores().isShowing())
			llenarTablaProveedores();
		if (this.vistaPrincipal.getTabPiezas().isShowing()) {
			cargarCbMarcas();
			llenarTablaPiezas();
		}
		if (this.vistaPrincipal.getTabEds().isShowing())
			llenarTablaElectrodomesticos();
		if (this.vistaPrincipal.getTabUsuarios().isShowing())
			llenarTablaUsuarios();
		if (this.vistaPrincipal.getTabFleteros().isShowing())
			llenarTablaFleteros();
		// if (this.vistaPrincipal.getTabImportarPrecios().isShowing())
		// llenarComboProveedores();
		if (this.vistaPrincipal.getTabEnvios().isShowing())
			llenarTablaEnvios((FleteroDTO) this.vistaPrincipal.getComboFleteros().getSelectedItem());
		if (this.vistaPrincipal.getTabAlertas().isShowing())
			llenarTablaAlertas();
	}

	public List<OrdenDTO> filtrarSegun(String s) {
		List<OrdenDTO> result = new LinkedList<OrdenDTO>();

		List<OrdenDTO> obtenerOrdenes = null;
		try {
			obtenerOrdenes = this.modelo.obtenerOrdenes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (s.equals("seleccionar"))
			result = obtenerOrdenes;
		else {
			for (int i = 0; i < obtenerOrdenes.size(); i++) {
				if (obtenerOrdenes.get(i).getEstado().getNombre().equals(s)) {
					result.add(obtenerOrdenes.get(i));
				}
			}
		}
		return result;
	}

	public List<OrdenDTO> obtenerReparadasDeliveryPendientes(int estado) {
		// trae de la db
		List<EnvioDTO> envios = new LinkedList<EnvioDTO>();
		List<OrdenDTO> resultado = new LinkedList<OrdenDTO>();
		try {
			envios = this.modelo.obtenerEnvios();
			for (int i = 0; i < envios.size(); i++) {
				EnvioDTO envio = envios.get(i);
				envio.setDetalles(this.modelo.obtenerDetalles(envio));
			}

			// para mostrar en tabla
			List<OrdenDTO> ordenes_en_envios = new LinkedList<OrdenDTO>();
			for (int i = 0; i < envios.size(); i++) {
				for (int j = 0; j < envios.get(i).getDetalles().size(); j++) {
					ordenes_en_envios.add(envios.get(i).getDetalles().get(j).getOt());
				}
			}

			// controla reparadas con delivery
			for (int i = 0; i < this.modelo.obtenerOrdenes().size(); i++) {
				OrdenDTO orden = this.modelo.obtenerOrdenes().get(i);
				if (!ordenes_en_envios.contains(orden) && orden.isEsDelivery() && orden.getEstado().getId() == estado)
					resultado.add(orden);
			}

			// agrego las pendientes (no entregadas)
			List<OrdenDTO> pendientes = new LinkedList<OrdenDTO>();
			for (int i = 0; i < envios.size(); i++) {
				for (int j = 0; j < envios.get(i).getDetalles().size(); j++) {
					if (!envios.get(i).getDetalles().get(j).isEntregado())
						pendientes.add(envios.get(i).getDetalles().get(j).getOt());
				}
			}

			// controla que no haya sido entregada por otro fletero
			for (int i = 0; i < this.modelo.obtenerOrdenes().size(); i++) {
				for (int j = 0; j < pendientes.size(); j++) {
					if (pendientes.get(j).getIdOT() == modelo.obtenerOrdenes().get(i).getIdOT() && modelo.obtenerOrdenes().get(i).getEstado().getId() != 10)
						resultado.add(pendientes.get(j));
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return resultado;
	}

	private void llenarComboProveedores() {
		this.vistaPrincipal.getComboBoxProvedores().removeAllItems();

		List<ProveedorDTO> proveedores;
		try {
			proveedores = modelo.obtenerProveedores();
			for (ProveedorDTO proveedorDTO : proveedores) {
				this.vistaPrincipal.getComboBoxProvedores().addItem(proveedorDTO);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(vistaPrincipal, "No se han podido cargar los proveedores", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getSource() == this.vistaPrincipal.getDpiSemana().getJFormattedTextField() && this.vistaPrincipal.getRdSemana().isSelected() && this.vistaPrincipal.getDpiSemana().getModel().getValue() != null) {
			GregorianCalendar gregDate = (GregorianCalendar) this.vistaPrincipal.getDpiSemana().getModel().getValue();
			this.vistaPrincipal.getTxtReporteDesde().setText(this.getPrimerDiaSemana(gregDate));
			this.vistaPrincipal.getTxtReporteHasta().setText(this.getUltimoDiaSemana(gregDate));
		} else if (e.getSource() == this.vistaPrincipal.getDpiMes().getJFormattedTextField() && this.vistaPrincipal.getRdMes().isSelected() && this.vistaPrincipal.getDpiMes().getModel().getValue() != null) {
			GregorianCalendar gregDate = (GregorianCalendar) this.vistaPrincipal.getDpiMes().getModel().getValue();
			this.vistaPrincipal.getTxtReporteDesde().setText(this.getPrimerDiaMes(gregDate));
			this.vistaPrincipal.getTxtReporteHasta().setText(this.getUltimoDiaMes(gregDate));
		} else if (e.getSource() == this.vistaPrincipal.getDpiAnio().getJFormattedTextField() && this.vistaPrincipal.getRdAnio().isSelected() && this.vistaPrincipal.getDpiAnio().getModel().getValue() != null) {
			GregorianCalendar gregDate = (GregorianCalendar) this.vistaPrincipal.getDpiAnio().getModel().getValue();
			this.vistaPrincipal.getTxtReporteDesde().setText(this.getPrimerDiaAnio(gregDate));
			this.vistaPrincipal.getTxtReporteHasta().setText(this.getUltimoDiaAnio(gregDate));
		} else if (e.getSource() == this.vistaPrincipal.getDpiPersonalizado1().getJFormattedTextField() && this.vistaPrincipal.getRdPersonalizado().isSelected() && this.vistaPrincipal.getDpiPersonalizado1().getModel().getValue() != null) {
			GregorianCalendar gregDate1 = (GregorianCalendar) this.vistaPrincipal.getDpiPersonalizado1().getModel().getValue();
			Calendar cal1 = gregDate1;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			this.vistaPrincipal.getTxtReporteDesde().setText(sdf.format(cal1.getTime()));
		} else if (e.getSource() == this.vistaPrincipal.getDpiPersonalizado2().getJFormattedTextField() && this.vistaPrincipal.getRdPersonalizado().isSelected() && this.vistaPrincipal.getDpiPersonalizado2().getModel().getValue() != null) {
			GregorianCalendar gregDate1 = (GregorianCalendar) this.vistaPrincipal.getDpiPersonalizado2().getModel().getValue();
			Calendar cal1 = gregDate1;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			this.vistaPrincipal.getTxtReporteHasta().setText(sdf.format(cal1.getTime()));
		}

	}

	private String getPrimerDiaSemana(GregorianCalendar fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		// get today and clear time of day
		Calendar cal = fecha;
		// get start of the week
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		return sdf.format(cal.getTime());
	}

	private String getUltimoDiaSemana(GregorianCalendar fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = fecha;
		// get end of the week
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return sdf.format(cal.getTime());
	}

	private String getPrimerDiaMes(GregorianCalendar fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = fecha;
		// get start of the month
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return sdf.format(cal.getTime());
	}

	private String getUltimoDiaMes(GregorianCalendar fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = fecha;
		// get end of the month
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return sdf.format(cal.getTime());
	}

	private String getPrimerDiaAnio(GregorianCalendar fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = fecha;
		// get start of the year
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return sdf.format(cal.getTime());
	}

	private String getUltimoDiaAnio(GregorianCalendar fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = fecha;
		// get end of the year
		cal.add(Calendar.YEAR, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return sdf.format(cal.getTime());
	}
}