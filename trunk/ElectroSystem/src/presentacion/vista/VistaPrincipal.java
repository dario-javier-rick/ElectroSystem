package presentacion.vista;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilCalendarModel;

import JCDesktopPane.JCDesktopPane;
import dto.EstadoDTO;
import dto.FleteroDTO;
import dto.MarcaDTO;
import dto.ProveedorDTO;

public class VistaPrincipal extends JFrame {

	private static final long serialVersionUID = -7524460981496689307L;

	private static final String ENTREGADA = "Entregada";
	private static final String DESPACHADA = "Despachada";
	private static final String IRREPARABLE = "Irreparable";
	private static final String REPARADA = "Reparada";
	private static final String EN_ESPERA_DE_PIEZAS = "En espera de piezas";
	private static final String EN_REPARACIÓN = "En reparación";
	private static final String DESAPROBADA = "Desaprobada";
	private static final String APROBADA = "Aprobada";
	private static final String PRESUPUESTADA = "Presupuestada";
	private static final String INGRESADA = "Ingresada";
	private static final String SELECCIONAR = "seleccionar";

	JCDesktopPane jCDesktopPane1;
	private JPanel tabClientes;
	private JTabbedPane tabbedPane;
	private JComboBox<MarcaDTO> cbMarcas;
	private JScrollPane spEds;
	private JPanel tabUsuarios;
	private JButton btnAgregarCliente;
	private JButton btnNuevaOt;
	private JButton btnNuevaSc;
	private JButton btnAadirPiezas;
	private JButton btnAgregarUsuario;
	private JButton btnNuevoProveedor;
	private JTable tablePiezas;
	private JTable tableClientes;

	private JTable tableOT_UT;
	private JComboBox<EstadoDTO> comboEstadosUT;
	private JTable tableOT_UA;
	private JComboBox<EstadoDTO> comboEstadosUA;

	private JTable tableScs;
	private JTable tableEds;
	private JTable tableUsuarios;
	private JTable tableProveedores;
	private JPanel tabFleteros;
	private JButton btnNuevoFletero;
	private JTable tableFleteros;
	private JScrollPane spFleteros;
	private JMenuBar menuBar;
	private JButton btnEnviarMailOT;
	private JButton btnEnviarMailSC;

	private JMenu mnHerramientas;
	private JMenuItem btnCrearBackup;
	private JMenuItem btnCargarBackup;
	private JPanel tabOT_UT;
	private JPanel tabOT_UA;
	private JPanel panelScs;
	private JPanel panelProveedores;
	private JPanel tabPiezas;
	private JPanel tabEds;
	private JButton btnAgregarEd;
	private JMenuItem mntmDatos;
	private JMenuItem mntmSalir;
	private JMenu menuLog;
	private JMenuItem mntmActualizar;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel tabImportarPrecios;
	private JComboBox<ProveedorDTO> comboBoxProvedores;

	private JButton btnProcesar;
	private JButton fileChooser;

	private JMenu mnEdicin;
	private JMenuItem mntmEditarZonas;

	private JPanel panel_4;
	private JPanel tabContabilidad;
	private JScrollPane spContabilidad;
	private JTable tableContabilidad;
	private JPanel panel_5;

	private JDatePickerImpl fechaInicio;
	private JLabel lblDesde;
	private JLabel lblHasta;

	private JDatePickerImpl fechaFin;
	private JButton btnVerContabilidad;
	private JPanel tabReportes;
	private JRadioButton rdSemana;
	private JRadioButton rdMes;
	private JRadioButton rdAnio;
	private JRadioButton rdPersonalizado;
	private ButtonGroup group;
	private JDatePickerImpl dpiSemana;
	private JDatePickerImpl dpiMes;
	private JDatePickerImpl dpiAnio;
	private JDatePickerImpl dpiPersonalizado1;
	private JDatePickerImpl dpiPersonalizado2;
	private JTextField txtReporteDesde;
	private JTextField txtReporteHasta;
	private JLabel lblDesdePersonalizado;
	private JLabel lblHastaPersonalizado;
	private JButton btnGenerarReporte;
	private JButton btnReporteContable;
	private JComboBox<String> comboReportes;

	private JPanel tabAlertas;
	private JScrollPane spAlertas;
	private JPanel panel_6;
	private JTable tableAlertas;

	private JPanel panel_7;
	private JPanel tabEnvios;
	private JButton btnAsignarEnvio;
	private JTable tableEnvios;
	private JScrollPane spEnvios;
	private JComboBox<FleteroDTO> comboFleteros;
	private JButton btnVerEntregados;
	private JButton btnEnvios;
	private JButton btnBack;
	private JMenuItem mntmEditarLocalidades;
	private JButton btnBuscarOT;
	private JPanel panel_8;
	private JButton btnBuscarCliente;
	private JMenuItem mntmImportarPrecios;

	/** Creates new form principal */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public VistaPrincipal() {
		super();
		setIconImage(Toolkit.getDefaultToolkit().getImage(VistaPrincipal.class.getResource("/icons/service.png")));
		setBackground(SystemColor.menu);

		getContentPane().setLayout(new CardLayout(0, 0));

		setTabbedPane(new JTabbedPane(JTabbedPane.TOP));
		getContentPane().add(getTabbedPane(), "name_20870881958318");

		tabClientes = new JPanel();
		tabClientes.setBackground(SystemColor.menu);
		tabClientes.setToolTipText("");
		getTabbedPane().addTab("Clientes", null, tabClientes, null);
		GridBagLayout gbl_tabClientes = new GridBagLayout();
		gbl_tabClientes.columnWidths = new int[] { 0, 0, 0 };
		gbl_tabClientes.rowHeights = new int[] { 5, 23, 367, 0 };
		gbl_tabClientes.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_tabClientes.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		tabClientes.setLayout(gbl_tabClientes);

		panel_8 = new JPanel();
		GridBagConstraints gbc_panel_8 = new GridBagConstraints();
		gbc_panel_8.insets = new Insets(0, 0, 5, 0);
		gbc_panel_8.fill = GridBagConstraints.BOTH;
		gbc_panel_8.gridx = 1;
		gbc_panel_8.gridy = 1;
		tabClientes.add(panel_8, gbc_panel_8);
		GridBagLayout gbl_panel_8 = new GridBagLayout();
		gbl_panel_8.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_8.rowHeights = new int[] { 0, 0 };
		gbl_panel_8.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_8.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_8.setLayout(gbl_panel_8);

		btnAgregarCliente = new JButton();
		GridBagConstraints gbc_btnAgregarCliente = new GridBagConstraints();
		gbc_btnAgregarCliente.insets = new Insets(0, 0, 0, 5);
		gbc_btnAgregarCliente.gridx = 0;
		gbc_btnAgregarCliente.gridy = 0;
		panel_8.add(btnAgregarCliente, gbc_btnAgregarCliente);
		btnAgregarCliente.setToolTipText("Nuevo");
		btnAgregarCliente.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/add.png")));
		btnAgregarCliente.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAgregarCliente.setHorizontalTextPosition(SwingConstants.CENTER);

		btnBuscarCliente = new JButton("");
		btnBuscarCliente.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/find.png")));
		btnBuscarCliente.setToolTipText("Buscar Cliente");
		GridBagConstraints gbc_btnBuscarCliente = new GridBagConstraints();
		gbc_btnBuscarCliente.insets = new Insets(0, 0, 0, 5);
		gbc_btnBuscarCliente.gridx = 1;
		gbc_btnBuscarCliente.gridy = 0;
		panel_8.add(btnBuscarCliente, gbc_btnBuscarCliente);

		JScrollPane spDatosCliente = new JScrollPane();
		GridBagConstraints gbc_spDatosCliente = new GridBagConstraints();
		gbc_spDatosCliente.fill = GridBagConstraints.BOTH;
		gbc_spDatosCliente.gridx = 1;
		gbc_spDatosCliente.gridy = 2;
		tabClientes.add(spDatosCliente, gbc_spDatosCliente);

		tableClientes = new JTable();
		tableClientes.getTableHeader().setReorderingAllowed(false) ;
		spDatosCliente.setColumnHeaderView(tableClientes);
		spDatosCliente.setViewportView(tableClientes);

		tabOT_UT = new JPanel();
		getTabbedPane().addTab("Ordenes de trabajo - UT", null, tabOT_UT, null);
		GridBagLayout gbl_tabOT_UT = new GridBagLayout();
		gbl_tabOT_UT.columnWidths = new int[] { 5, 830, 0 };
		gbl_tabOT_UT.rowHeights = new int[] { 5, 32, 367, 0 };
		gbl_tabOT_UT.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_tabOT_UT.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		tabOT_UT.setLayout(gbl_tabOT_UT);

		tabOT_UA = new JPanel();
		getTabbedPane().addTab("Ordenes de trabajo - UA", null, tabOT_UA, null);
		GridBagLayout gbl_tabOT_UA = new GridBagLayout();
		gbl_tabOT_UA.columnWidths = new int[] { 5, 830, 0 };
		gbl_tabOT_UA.rowHeights = new int[] { 5, 32, 367, 0 };
		gbl_tabOT_UA.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_tabOT_UA.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		tabOT_UA.setLayout(gbl_tabOT_UA);

		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 1;
		tabOT_UA.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 46, 118, 0, 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 23, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblEstado = new JLabel("Estado:");
		GridBagConstraints gbc_lblEstado = new GridBagConstraints();
		gbc_lblEstado.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblEstado.insets = new Insets(0, 0, 0, 5);
		gbc_lblEstado.gridx = 0;
		gbc_lblEstado.gridy = 0;
		panel_1.add(lblEstado, gbc_lblEstado);

		comboEstadosUA = new JComboBox<EstadoDTO>();
		GridBagConstraints gbc_comboEstadosUA = new GridBagConstraints();
		gbc_comboEstadosUA.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboEstadosUA.insets = new Insets(0, 0, 0, 5);
		gbc_comboEstadosUA.gridx = 1;
		gbc_comboEstadosUA.gridy = 0;
		panel_1.add(comboEstadosUA, gbc_comboEstadosUA);

		comboEstadosUA.addItem(new EstadoDTO(0, SELECCIONAR));
		comboEstadosUA.addItem(new EstadoDTO(1, INGRESADA));
		comboEstadosUA.addItem(new EstadoDTO(2, PRESUPUESTADA));
		comboEstadosUA.addItem(new EstadoDTO(3, APROBADA));
		comboEstadosUA.addItem(new EstadoDTO(4, DESAPROBADA));
		comboEstadosUA.addItem(new EstadoDTO(5, EN_REPARACIÓN));
		comboEstadosUA.addItem(new EstadoDTO(6, EN_ESPERA_DE_PIEZAS));
		comboEstadosUA.addItem(new EstadoDTO(7, REPARADA));
		comboEstadosUA.addItem(new EstadoDTO(8, IRREPARABLE));
		comboEstadosUA.addItem(new EstadoDTO(9, DESPACHADA));
		comboEstadosUA.addItem(new EstadoDTO(10, ENTREGADA));

		btnNuevaOt = new JButton("");
		btnNuevaOt.setToolTipText("Nueva");
		btnNuevaOt.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/add.png")));
		btnNuevaOt.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnNuevaOt.setHorizontalTextPosition(SwingConstants.CENTER);

		GridBagConstraints gbc_btnNuevaOt = new GridBagConstraints();
		gbc_btnNuevaOt.insets = new Insets(0, 0, 0, 5);
		gbc_btnNuevaOt.anchor = GridBagConstraints.NORTH;
		gbc_btnNuevaOt.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNuevaOt.gridx = 2;
		gbc_btnNuevaOt.gridy = 0;
		panel_1.add(btnNuevaOt, gbc_btnNuevaOt);

		btnEnviarMailOT = new JButton("");
		btnEnviarMailOT.setToolTipText("Enviar");
		btnEnviarMailOT.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/mail.png")));
		btnEnviarMailOT.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnEnviarMailOT.setHorizontalTextPosition(SwingConstants.CENTER);

		GridBagConstraints gbc_btnEnviarMailOT = new GridBagConstraints();
		gbc_btnEnviarMailOT.insets = new Insets(0, 0, 0, 5);
		gbc_btnEnviarMailOT.anchor = GridBagConstraints.NORTH;
		gbc_btnEnviarMailOT.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEnviarMailOT.gridx = 3;
		gbc_btnEnviarMailOT.gridy = 0;
		panel_1.add(btnEnviarMailOT, gbc_btnEnviarMailOT);

		btnBuscarOT = new JButton("");
		btnBuscarOT.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/find.png")));
		GridBagConstraints gbc_btnFindOT = new GridBagConstraints();
		gbc_btnFindOT.insets = new Insets(0, 0, 0, 5);
		gbc_btnFindOT.gridx = 4;
		gbc_btnFindOT.gridy = 0;
		panel_1.add(btnBuscarOT, gbc_btnFindOT);

		JScrollPane spDatosOT_UA = new JScrollPane();
		GridBagConstraints gbc_spDatosOT_UA = new GridBagConstraints();
		gbc_spDatosOT_UA.fill = GridBagConstraints.BOTH;
		gbc_spDatosOT_UA.gridx = 1;
		gbc_spDatosOT_UA.gridy = 2;
		tabOT_UA.add(spDatosOT_UA, gbc_spDatosOT_UA);

		tableOT_UA = new JTable();
		tableOT_UA.getTableHeader().setReorderingAllowed(false) ;
		spDatosOT_UA.setViewportView(tableOT_UA);

		panelScs = new JPanel();
		getTabbedPane().addTab("Solicitudes de compra", null, panelScs, null);
		GridBagLayout gbl_panelScs = new GridBagLayout();
		gbl_panelScs.columnWidths = new int[] { 5, 830, 0 };
		gbl_panelScs.rowHeights = new int[] { 5, 34, 367, 0 };
		gbl_panelScs.columnWeights = new double[] { 0.0, 0.1, Double.MIN_VALUE };
		gbl_panelScs.rowWeights = new double[] { 0.0, 0.0, 0.1, Double.MIN_VALUE };
		panelScs.setLayout(gbl_panelScs);

		panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.anchor = GridBagConstraints.WEST;
		gbc_panel_2.fill = GridBagConstraints.VERTICAL;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 1;
		panelScs.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 23, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		btnNuevaSc = new JButton("");
		btnNuevaSc.setToolTipText("Nueva");
		btnNuevaSc.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/add.png")));
		btnNuevaSc.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnNuevaSc.setHorizontalTextPosition(SwingConstants.CENTER);
		GridBagConstraints gbc_btnNuevaSc = new GridBagConstraints();
		gbc_btnNuevaSc.anchor = GridBagConstraints.NORTH;
		gbc_btnNuevaSc.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNuevaSc.insets = new Insets(0, 0, 0, 5);
		gbc_btnNuevaSc.gridx = 0;
		gbc_btnNuevaSc.gridy = 0;
		panel_2.add(btnNuevaSc, gbc_btnNuevaSc);

		btnEnviarMailSC = new JButton("");
		btnEnviarMailSC.setToolTipText("Enviar");
		btnEnviarMailSC.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/mail.png")));

		btnEnviarMailSC.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnEnviarMailSC.setHorizontalTextPosition(SwingConstants.CENTER);

		GridBagConstraints gbc_btnEnviarMailSC = new GridBagConstraints();
		gbc_btnEnviarMailSC.anchor = GridBagConstraints.NORTH;
		gbc_btnEnviarMailSC.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEnviarMailSC.gridx = 1;
		gbc_btnEnviarMailSC.gridy = 0;
		panel_2.add(btnEnviarMailSC, gbc_btnEnviarMailSC);

		JScrollPane spDatosScs = new JScrollPane();
		GridBagConstraints gbc_spDatosScs = new GridBagConstraints();
		gbc_spDatosScs.fill = GridBagConstraints.BOTH;
		gbc_spDatosScs.gridx = 1;
		gbc_spDatosScs.gridy = 2;
		panelScs.add(spDatosScs, gbc_spDatosScs);

		tableScs = new JTable();
		tableScs.getTableHeader().setReorderingAllowed(false) ;
		spDatosScs.setViewportView(tableScs);

		panelProveedores = new JPanel();
		getTabbedPane().addTab("Proveedores", null, panelProveedores, null);
		GridBagLayout gbl_panelProveedores = new GridBagLayout();
		gbl_panelProveedores.columnWidths = new int[] { 5, 830, 0 };
		gbl_panelProveedores.rowHeights = new int[] { 5, 23, 367, 0 };
		gbl_panelProveedores.columnWeights = new double[] { 0.0, 0.1, Double.MIN_VALUE };
		gbl_panelProveedores.rowWeights = new double[] { 0.0, 0.0, 0.1, Double.MIN_VALUE };
		panelProveedores.setLayout(gbl_panelProveedores);

		btnNuevoProveedor = new JButton("");
		btnNuevoProveedor.setToolTipText("Nuevo");
		btnNuevoProveedor.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/add.png")));
		btnNuevoProveedor.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnNuevoProveedor.setHorizontalTextPosition(SwingConstants.CENTER);
		GridBagConstraints gbc_btnNuevoProveedor = new GridBagConstraints();
		gbc_btnNuevoProveedor.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNuevoProveedor.insets = new Insets(0, 0, 5, 0);
		gbc_btnNuevoProveedor.gridx = 1;
		gbc_btnNuevoProveedor.gridy = 1;
		panelProveedores.add(btnNuevoProveedor, gbc_btnNuevoProveedor);

		JScrollPane spProveedores = new JScrollPane();
		GridBagConstraints gbc_spProveedores = new GridBagConstraints();
		gbc_spProveedores.fill = GridBagConstraints.BOTH;
		gbc_spProveedores.gridx = 1;
		gbc_spProveedores.gridy = 2;
		panelProveedores.add(spProveedores, gbc_spProveedores);

		tableProveedores = new JTable();
		tableProveedores.getTableHeader().setReorderingAllowed(false) ;
		spProveedores.setViewportView(tableProveedores);

		tabPiezas = new JPanel();
		getTabbedPane().addTab("Piezas", null, tabPiezas, null);
		GridBagLayout gbl_tabPiezas = new GridBagLayout();
		gbl_tabPiezas.columnWidths = new int[] { 5, 830, 0 };
		gbl_tabPiezas.rowHeights = new int[] { 5, 37, 370, 0 };
		gbl_tabPiezas.columnWeights = new double[] { 0.0, 0.1, Double.MIN_VALUE };
		gbl_tabPiezas.rowWeights = new double[] { 0.0, 0.0, 0.1, Double.MIN_VALUE };
		tabPiezas.setLayout(gbl_tabPiezas);

		panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.anchor = GridBagConstraints.WEST;
		gbc_panel_3.fill = GridBagConstraints.VERTICAL;
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 1;
		tabPiezas.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 133, 0, 0 };
		gbl_panel_3.rowHeights = new int[] { 23, 0 };
		gbl_panel_3.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		cbMarcas = new JComboBox<MarcaDTO>();
		GridBagConstraints gbc_cbMarcas = new GridBagConstraints();
		gbc_cbMarcas.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbMarcas.insets = new Insets(0, 0, 0, 5);
		gbc_cbMarcas.gridx = 0;
		gbc_cbMarcas.gridy = 0;
		panel_3.add(cbMarcas, gbc_cbMarcas);

		btnAadirPiezas = new JButton("");
		btnAadirPiezas.setToolTipText("Nueva");
		btnAadirPiezas.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/add.png")));
		btnAadirPiezas.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAadirPiezas.setHorizontalTextPosition(SwingConstants.CENTER);
		GridBagConstraints gbc_btnAadirPiezas = new GridBagConstraints();
		gbc_btnAadirPiezas.anchor = GridBagConstraints.NORTH;
		gbc_btnAadirPiezas.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAadirPiezas.gridx = 1;
		gbc_btnAadirPiezas.gridy = 0;
		panel_3.add(btnAadirPiezas, gbc_btnAadirPiezas);

		JScrollPane spDatosPiezas = new JScrollPane();
		GridBagConstraints gbc_spDatosPiezas = new GridBagConstraints();
		gbc_spDatosPiezas.fill = GridBagConstraints.BOTH;
		gbc_spDatosPiezas.gridx = 1;
		gbc_spDatosPiezas.gridy = 2;
		tabPiezas.add(spDatosPiezas, gbc_spDatosPiezas);

		tablePiezas = new JTable();
		tablePiezas.getTableHeader().setReorderingAllowed(false) ;
		spDatosPiezas.setViewportView(tablePiezas);

		tabEds = new JPanel();
		getTabbedPane().addTab("Electrodomesticos", null, tabEds, null);
		GridBagLayout gbl_tabEds = new GridBagLayout();
		gbl_tabEds.columnWidths = new int[] { 5, 830, 0 };
		gbl_tabEds.rowHeights = new int[] { 5, 23, 367, 0 };
		gbl_tabEds.columnWeights = new double[] { 0.0, 0.1, Double.MIN_VALUE };
		gbl_tabEds.rowWeights = new double[] { 0.0, 0.0, 0.1, Double.MIN_VALUE };
		tabEds.setLayout(gbl_tabEds);

		panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.anchor = GridBagConstraints.WEST;
		gbc_panel_4.fill = GridBagConstraints.VERTICAL;
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.gridx = 1;
		gbc_panel_4.gridy = 1;
		tabEds.add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 23, 0 };
		gbl_panel_4.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		btnAgregarEd = new JButton("");
		btnAgregarEd.setToolTipText("Nuevo");
		btnAgregarEd.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/add.png")));

		btnAgregarEd.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAgregarEd.setHorizontalTextPosition(SwingConstants.CENTER);

		GridBagConstraints gbc_btnAgregarEd = new GridBagConstraints();
		gbc_btnAgregarEd.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnAgregarEd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAgregarEd.gridx = 0;
		gbc_btnAgregarEd.gridy = 0;
		panel_4.add(btnAgregarEd, gbc_btnAgregarEd);

		spEds = new JScrollPane();
		GridBagConstraints gbc_spEds = new GridBagConstraints();
		gbc_spEds.fill = GridBagConstraints.BOTH;
		gbc_spEds.gridx = 1;
		gbc_spEds.gridy = 2;
		tabEds.add(spEds, gbc_spEds);

		tableEds = new JTable();
		tableEds.getTableHeader().setReorderingAllowed(false) ;
		spEds.setViewportView(tableEds);

		tabUsuarios = new JPanel();
		getTabbedPane().addTab("Usuarios", null, tabUsuarios, null);
		GridBagLayout gbl_tabUsuarios = new GridBagLayout();
		gbl_tabUsuarios.columnWidths = new int[] { 5, 830, 0 };
		gbl_tabUsuarios.rowHeights = new int[] { 5, 23, 367, 0 };
		gbl_tabUsuarios.columnWeights = new double[] { 0.0, 0.1, Double.MIN_VALUE };
		gbl_tabUsuarios.rowWeights = new double[] { 0.0, 0.0, 0.1, Double.MIN_VALUE };
		tabUsuarios.setLayout(gbl_tabUsuarios);

		btnAgregarUsuario = new JButton("");
		btnAgregarUsuario.setToolTipText("Nuevo");
		btnAgregarUsuario.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/add.png")));

		btnAgregarUsuario.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAgregarUsuario.setHorizontalTextPosition(SwingConstants.CENTER);

		GridBagConstraints gbc_btnAgregarUsuario = new GridBagConstraints();
		gbc_btnAgregarUsuario.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnAgregarUsuario.insets = new Insets(0, 0, 5, 0);
		gbc_btnAgregarUsuario.gridx = 1;
		gbc_btnAgregarUsuario.gridy = 1;
		tabUsuarios.add(btnAgregarUsuario, gbc_btnAgregarUsuario);

		JScrollPane spUsuarios = new JScrollPane();
		GridBagConstraints gbc_spUsuarios = new GridBagConstraints();
		gbc_spUsuarios.fill = GridBagConstraints.BOTH;
		gbc_spUsuarios.gridx = 1;
		gbc_spUsuarios.gridy = 2;
		tabUsuarios.add(spUsuarios, gbc_spUsuarios);

		tableUsuarios = new JTable();
		tableUsuarios.getTableHeader().setReorderingAllowed(false) ;
		spUsuarios.setViewportView(tableUsuarios);

		tabFleteros = new JPanel();
		tabbedPane.addTab("Fleteros", null, tabFleteros, null);
		GridBagLayout gbl_tabFleteros = new GridBagLayout();
		gbl_tabFleteros.columnWidths = new int[] { 5, 830, 0 };
		gbl_tabFleteros.rowHeights = new int[] { 5, 23, 367, 0 };
		gbl_tabFleteros.columnWeights = new double[] { 0.0, 0.1, Double.MIN_VALUE };
		gbl_tabFleteros.rowWeights = new double[] { 0.0, 0.0, 0.1, Double.MIN_VALUE };
		tabFleteros.setLayout(gbl_tabFleteros);

		btnNuevoFletero = new JButton("");
		btnNuevoFletero.setToolTipText("Nuevo");
		btnNuevoFletero.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/add.png")));

		btnNuevoFletero.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnNuevoFletero.setHorizontalTextPosition(SwingConstants.CENTER);

		GridBagConstraints gbc_btnNuevoFletero = new GridBagConstraints();
		gbc_btnNuevoFletero.gridwidth = 2;
		gbc_btnNuevoFletero.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNuevoFletero.insets = new Insets(0, 0, 5, 0);
		gbc_btnNuevoFletero.gridx = 1;
		gbc_btnNuevoFletero.gridy = 1;
		tabFleteros.add(btnNuevoFletero, gbc_btnNuevoFletero);

		spFleteros = new JScrollPane();
		GridBagConstraints gbc_spFleteros = new GridBagConstraints();
		gbc_spFleteros.fill = GridBagConstraints.BOTH;
		gbc_spFleteros.gridx = 1;
		gbc_spFleteros.gridy = 2;
		tabFleteros.add(spFleteros, gbc_spFleteros);

		tableFleteros = new JTable();
		tableFleteros.getTableHeader().setReorderingAllowed(false) ;
		spFleteros.setViewportView(tableFleteros);

		menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.menu);
		setJMenuBar(menuBar);

		mnEdicin = new JMenu("Edición");
		mnEdicin.setVisible(false);
		menuBar.add(mnEdicin);

		mntmEditarZonas = new JMenuItem("Editar Zonas");
		mntmEditarZonas.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/edit.png")));
		mnEdicin.add(mntmEditarZonas);

		mntmEditarLocalidades = new JMenuItem("Editar Localidades");
		mntmEditarLocalidades.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/edit.png")));
		mnEdicin.add(mntmEditarLocalidades);

		mnHerramientas = new JMenu("Herramientas");
		mnHerramientas.setVisible(false);
		menuBar.add(mnHerramientas);
		menuBar.add(Box.createHorizontalGlue());

		btnCrearBackup = new JMenuItem("Crear backup");
		btnCrearBackup.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/download.png")));
		mnHerramientas.add(btnCrearBackup);

		btnCargarBackup = new JMenuItem("Cargar backup");
		btnCargarBackup.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/upload.png")));
		mnHerramientas.add(btnCargarBackup);

		mntmImportarPrecios = new JMenuItem("Importar precios");
		mntmImportarPrecios.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/csv.png")));
		mnHerramientas.add(mntmImportarPrecios);

		mntmActualizar = new JMenuItem("Actualizar");
		mntmActualizar.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/refresh.png")));
		mnHerramientas.add(mntmActualizar);

		menuLog = new JMenu("New menu");
		menuLog.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/usr.png")));
		menuBar.add(menuLog);

		mntmDatos = new JMenuItem("Mis datos");
		mntmDatos.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/datos.png")));
		menuLog.add(mntmDatos);

		mntmSalir = new JMenuItem("Salir");
		mntmSalir.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/exit.png")));
		menuLog.add(mntmSalir);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.WEST;
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		tabOT_UT.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 53, 118, 0 };
		gbl_panel.rowHeights = new int[] { 20, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblEstadoUT = new JLabel("Estado:");
		GridBagConstraints gbc_lblEstadoUT = new GridBagConstraints();
		gbc_lblEstadoUT.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblEstadoUT.insets = new Insets(0, 0, 0, 5);
		gbc_lblEstadoUT.gridx = 0;
		gbc_lblEstadoUT.gridy = 0;
		panel.add(lblEstadoUT, gbc_lblEstadoUT);

		comboEstadosUT = new JComboBox<EstadoDTO>();
		GridBagConstraints gbc_comboEstadosUT = new GridBagConstraints();
		gbc_comboEstadosUT.fill = GridBagConstraints.BOTH;
		gbc_comboEstadosUT.gridx = 1;
		gbc_comboEstadosUT.gridy = 0;
		panel.add(comboEstadosUT, gbc_comboEstadosUT);
		comboEstadosUT.addItem(new EstadoDTO(1, INGRESADA));
		comboEstadosUT.addItem(new EstadoDTO(3, APROBADA));
		comboEstadosUT.addItem(new EstadoDTO(5, EN_REPARACIÓN));

		JScrollPane spDatosOT_UT = new JScrollPane();
		GridBagConstraints gbc_spDatosOT_UT = new GridBagConstraints();
		gbc_spDatosOT_UT.fill = GridBagConstraints.BOTH;
		gbc_spDatosOT_UT.gridx = 1;
		gbc_spDatosOT_UT.gridy = 2;
		tabOT_UT.add(spDatosOT_UT, gbc_spDatosOT_UT);

		tableOT_UT = new JTable();
		tableOT_UT.getTableHeader().setReorderingAllowed(false) ;
		spDatosOT_UT.setViewportView(tableOT_UT);

		tabContabilidad = new JPanel();
		tabContabilidad.setToolTipText("");
		tabbedPane.addTab("Contabilidad", null, tabContabilidad, null);
		GridBagLayout gbl_tabContabilidad = new GridBagLayout();
		gbl_tabContabilidad.columnWidths = new int[] { 5, 830, 0 };
		gbl_tabContabilidad.rowHeights = new int[] { 5, 23, 367, 0 };
		gbl_tabContabilidad.columnWeights = new double[] { 0.0, 0.1, Double.MIN_VALUE };
		gbl_tabContabilidad.rowWeights = new double[] { 0.0, 0.0, 0.1, Double.MIN_VALUE };
		tabContabilidad.setLayout(gbl_tabContabilidad);

		panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.insets = new Insets(0, 0, 5, 0);
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 1;
		gbc_panel_5.gridy = 1;
		tabContabilidad.add(panel_5, gbc_panel_5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_5.rowHeights = new int[] { 0, 0 };
		gbl_panel_5.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_5.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_5.setLayout(gbl_panel_5);

		lblDesde = new JLabel("Desde: ");
		GridBagConstraints gbc_lblDesde = new GridBagConstraints();
		gbc_lblDesde.insets = new Insets(0, 0, 0, 5);
		gbc_lblDesde.gridx = 0;
		gbc_lblDesde.gridy = 0;
		panel_5.add(lblDesde, gbc_lblDesde);

		UtilCalendarModel model = new UtilCalendarModel();
		Properties properties = new Properties();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
		fechaInicio = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		SpringLayout springLayout = (SpringLayout) fechaInicio.getLayout();
		springLayout.putConstraint(SpringLayout.SOUTH, fechaInicio.getJFormattedTextField(), 0, SpringLayout.SOUTH,
				fechaInicio);
		fechaInicio.setEnabled(false);
		fechaInicio.setBounds(125, 404, 119, 32);

		GridBagConstraints gbc_btnBuscarOT = new GridBagConstraints();
		gbc_btnBuscarOT.insets = new Insets(0, 0, 0, 5);
		gbc_btnBuscarOT.gridx = 1;
		gbc_btnBuscarOT.gridy = 0;
		panel_5.add(fechaInicio, gbc_btnBuscarOT);

		lblHasta = new JLabel("Hasta:");
		GridBagConstraints gbc_lblHasta = new GridBagConstraints();
		gbc_lblHasta.insets = new Insets(0, 0, 0, 5);
		gbc_lblHasta.gridx = 2;
		gbc_lblHasta.gridy = 0;
		panel_5.add(lblHasta, gbc_lblHasta);

		UtilCalendarModel model2 = new UtilCalendarModel();
		Properties properties2 = new Properties();
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, properties2);
		fechaFin = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		SpringLayout springLayout2 = (SpringLayout) fechaFin.getLayout();
		springLayout2.putConstraint(SpringLayout.SOUTH, fechaFin.getJFormattedTextField(), 0, SpringLayout.SOUTH,
				fechaFin);
		fechaFin.setEnabled(false);
		fechaFin.setBounds(125, 404, 119, 32);
		GridBagConstraints gbc_btnNewButton2 = new GridBagConstraints();
		gbc_btnNewButton2.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton2.gridx = 3;
		gbc_btnNewButton2.gridy = 0;
		panel_5.add(fechaFin, gbc_btnNewButton2);

		btnVerContabilidad = new JButton("Ver");
		GridBagConstraints gbc_btnVerContabilidad = new GridBagConstraints();
		gbc_btnVerContabilidad.insets = new Insets(0, 0, 0, 5);
		gbc_btnVerContabilidad.gridx = 4;
		gbc_btnVerContabilidad.gridy = 0;
		panel_5.add(btnVerContabilidad, gbc_btnVerContabilidad);

		btnReporteContable = new JButton("");
		GridBagConstraints gbc_btnReporteContable = new GridBagConstraints();
		gbc_btnReporteContable.gridx = 5;
		gbc_btnReporteContable.gridy = 0;
		panel_5.add(btnReporteContable, gbc_btnReporteContable);
		btnReporteContable.setToolTipText("Reporte Contable");
		btnReporteContable.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/report.png")));

		btnReporteContable.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnReporteContable.setHorizontalTextPosition(SwingConstants.CENTER);

		spContabilidad = new JScrollPane();
		GridBagConstraints gbc_spContabilidad = new GridBagConstraints();
		gbc_spContabilidad.fill = GridBagConstraints.BOTH;
		gbc_spContabilidad.gridx = 1;
		gbc_spContabilidad.gridy = 2;
		tabContabilidad.add(spContabilidad, gbc_spContabilidad);

		tableContabilidad = new JTable();
		tableContabilidad.getTableHeader().setReorderingAllowed(false) ;
		spContabilidad.setViewportView(tableContabilidad);

		tabAlertas = new JPanel();
		tabbedPane.addTab("Alertas", null, tabAlertas, null);
		GridBagLayout gbl_tabAlertas = new GridBagLayout();
		gbl_tabAlertas.columnWidths = new int[] { 0, 0, 0 };
		gbl_tabAlertas.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_tabAlertas.columnWeights = new double[] { 0.0, 0.1, Double.MIN_VALUE };
		gbl_tabAlertas.rowWeights = new double[] { 0.0, 0.0, 0.1, Double.MIN_VALUE };
		tabAlertas.setLayout(gbl_tabAlertas);

		panel_6 = new JPanel();
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.insets = new Insets(0, 0, 5, 0);
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.gridx = 1;
		gbc_panel_6.gridy = 1;
		tabAlertas.add(panel_6, gbc_panel_6);
		GridBagLayout gbl_panel_6 = new GridBagLayout();
		gbl_panel_6.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel_6.rowHeights = new int[] { 0, 0 };
		gbl_panel_6.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_6.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_6.setLayout(gbl_panel_6);

		spAlertas = new JScrollPane();
		GridBagConstraints gbc_spAlertas = new GridBagConstraints();
		gbc_spAlertas.fill = GridBagConstraints.BOTH;
		gbc_spAlertas.gridx = 1;
		gbc_spAlertas.gridy = 2;
		tabAlertas.add(spAlertas, gbc_spAlertas);

		tableAlertas = new JTable();
		tableAlertas.getTableHeader().setReorderingAllowed(false) ;
		spAlertas.setViewportView(tableAlertas);

		tabReportes = new JPanel();
		tabReportes.setToolTipText("");
		tabbedPane.addTab("Reportes", null, tabReportes, null);
		tabReportes.setLayout(null);

		rdSemana = new JRadioButton("Por semana");
		rdSemana.setBounds(46, 111, 109, 23);
		tabReportes.add(rdSemana);

		UtilCalendarModel model3 = new UtilCalendarModel();
		Properties properties3 = new Properties();
		JDatePanelImpl datePanel3 = new JDatePanelImpl(model3, properties3);
		dpiSemana = new JDatePickerImpl(datePanel3, new DateLabelFormatter());
		dpiSemana.getJFormattedTextField().setVisible(false);
		dpiSemana.setVisible(false);
		SpringLayout springLayout3 = (SpringLayout) dpiSemana.getLayout();
		springLayout3.putConstraint(SpringLayout.SOUTH, dpiSemana.getJFormattedTextField(), 0, SpringLayout.SOUTH,
				dpiSemana);
		dpiSemana.setEnabled(false);
		dpiSemana.setBounds(179, 111, 101, 32);
		tabReportes.add(dpiSemana);

		rdMes = new JRadioButton("Por mes");
		rdMes.setBounds(46, 154, 109, 23);
		tabReportes.add(rdMes);

		UtilCalendarModel model4 = new UtilCalendarModel();
		Properties properties4 = new Properties();
		JDatePanelImpl datePanel4 = new JDatePanelImpl(model4, properties4);
		dpiMes = new JDatePickerImpl(datePanel4, new DateLabelFormatter());
		dpiMes.setVisible(false);
		dpiMes.getJFormattedTextField().setVisible(false);
		SpringLayout springLayout4 = (SpringLayout) dpiMes.getLayout();
		springLayout4.putConstraint(SpringLayout.SOUTH, dpiMes.getJFormattedTextField(), 0, SpringLayout.SOUTH, dpiMes);
		dpiMes.setEnabled(false);
		dpiMes.setBounds(179, 154, 101, 32);
		tabReportes.add(dpiMes);

		rdAnio = new JRadioButton("Por año");
		rdAnio.setBounds(46, 197, 109, 23);
		tabReportes.add(rdAnio);

		UtilCalendarModel model5 = new UtilCalendarModel();
		Properties properties5 = new Properties();
		JDatePanelImpl datePanel5 = new JDatePanelImpl(model5, properties5);
		dpiAnio = new JDatePickerImpl(datePanel5, new DateLabelFormatter());
		dpiAnio.setVisible(false);
		dpiAnio.getJFormattedTextField().setVisible(false);
		SpringLayout springLayout5 = (SpringLayout) dpiAnio.getLayout();
		springLayout5.putConstraint(SpringLayout.SOUTH, dpiAnio.getJFormattedTextField(), 0, SpringLayout.SOUTH,
				dpiAnio);
		dpiAnio.setEnabled(false);
		dpiAnio.setBounds(179, 197, 101, 32);
		tabReportes.add(dpiAnio);

		rdPersonalizado = new JRadioButton("Personalizado");
		rdPersonalizado.setBounds(46, 240, 109, 23);
		tabReportes.add(rdPersonalizado);

		UtilCalendarModel model6 = new UtilCalendarModel();
		Properties properties6 = new Properties();
		JDatePanelImpl datePanel6 = new JDatePanelImpl(model6, properties6);
		dpiPersonalizado1 = new JDatePickerImpl(datePanel6, new DateLabelFormatter());
		SpringLayout springLayout6 = (SpringLayout) dpiPersonalizado1.getLayout();
		springLayout6.putConstraint(SpringLayout.SOUTH, dpiPersonalizado1.getJFormattedTextField(), 0,
				SpringLayout.SOUTH, dpiPersonalizado1);
		dpiPersonalizado1.setEnabled(false);
		dpiPersonalizado1.setVisible(false);
		dpiPersonalizado1.getJFormattedTextField().setVisible(false);
		dpiPersonalizado1.setBounds(221, 240, 101, 32);
		tabReportes.add(dpiPersonalizado1);

		UtilCalendarModel model7 = new UtilCalendarModel();
		Properties properties7 = new Properties();
		JDatePanelImpl datePanel7 = new JDatePanelImpl(model7, properties7);
		dpiPersonalizado2 = new JDatePickerImpl(datePanel7, new DateLabelFormatter());
		SpringLayout springLayout7 = (SpringLayout) dpiPersonalizado2.getLayout();
		springLayout7.putConstraint(SpringLayout.SOUTH, dpiPersonalizado2.getJFormattedTextField(), 0,
				SpringLayout.SOUTH, dpiPersonalizado2);
		dpiPersonalizado2.setEnabled(false);
		dpiPersonalizado2.setVisible(false);
		dpiPersonalizado2.getJFormattedTextField().setVisible(false);
		dpiPersonalizado2.setBounds(382, 240, 101, 32);
		tabReportes.add(dpiPersonalizado2);

		group = new ButtonGroup();
		group.add(rdSemana);
		group.add(rdMes);
		group.add(rdAnio);
		group.add(rdPersonalizado);

		btnGenerarReporte = new JButton("Generar reporte");
		btnGenerarReporte.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnGenerarReporte.setBounds(649, 301, 168, 42);
		tabReportes.add(btnGenerarReporte);

		JLabel lblNewLabel_1 = new JLabel("Desde: ");
		lblNewLabel_1.setBounds(180, 329, 46, 14);
		tabReportes.add(lblNewLabel_1);

		txtReporteDesde = new JTextField();
		txtReporteDesde.setEditable(false);
		txtReporteDesde.setBounds(236, 326, 86, 20);
		tabReportes.add(txtReporteDesde);
		txtReporteDesde.setColumns(10);

		JLabel lblHasta_1 = new JLabel("Hasta: ");
		lblHasta_1.setBounds(346, 329, 46, 14);
		tabReportes.add(lblHasta_1);

		txtReporteHasta = new JTextField();
		txtReporteHasta.setEditable(false);
		txtReporteHasta.setColumns(10);
		txtReporteHasta.setBounds(394, 326, 86, 20);
		tabReportes.add(txtReporteHasta);

		comboReportes = new JComboBox<String>();
		comboReportes.setModel(new DefaultComboBoxModel(new String[] { "(seleccionar)", "Electrodomésticos más usados",
				"Piezas más insumidas", "Reporte Contable", "Proveedores Incumplidores" }));
		comboReportes.setBounds(46, 41, 180, 20);
		tabReportes.add(comboReportes);

		lblDesdePersonalizado = new JLabel("Desde: ");
		lblDesdePersonalizado.setBounds(179, 244, 46, 14);
		lblDesdePersonalizado.setVisible(false);
		tabReportes.add(lblDesdePersonalizado);

		lblHastaPersonalizado = new JLabel("Hasta: ");
		lblHastaPersonalizado.setBounds(346, 244, 46, 14);
		lblHastaPersonalizado.setVisible(false);
		tabReportes.add(lblHastaPersonalizado);

		tabEnvios = new JPanel();
		tabbedPane.addTab("Envíos", null, tabEnvios, null);
		GridBagLayout gbl_tabEnvios = new GridBagLayout();
		gbl_tabEnvios.columnWidths = new int[] { 0, 0, 0 };
		gbl_tabEnvios.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_tabEnvios.columnWeights = new double[] { 0.0, 0.1, Double.MIN_VALUE };
		gbl_tabEnvios.rowWeights = new double[] { 0.0, 0.0, 0.1, Double.MIN_VALUE };
		tabEnvios.setLayout(gbl_tabEnvios);

		panel_7 = new JPanel();
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.insets = new Insets(0, 0, 5, 0);
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.gridx = 1;
		gbc_panel_7.gridy = 1;

		tabEnvios.add(panel_7, gbc_panel_7);
		GridBagLayout gbl_panel_7 = new GridBagLayout();
		gbl_panel_7.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_7.rowHeights = new int[] { 0, 0 };
		gbl_panel_7.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_7.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_7.setLayout(gbl_panel_7);

		comboFleteros = new JComboBox<FleteroDTO>();
		GridBagConstraints gbc_comboFleteros = new GridBagConstraints();
		gbc_comboFleteros.gridwidth = 5;
		gbc_comboFleteros.insets = new Insets(0, 0, 0, 5);
		gbc_comboFleteros.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFleteros.gridx = 0;
		gbc_comboFleteros.gridy = 0;
		panel_7.add(comboFleteros, gbc_comboFleteros);

		btnAsignarEnvio = new JButton("");
		btnAsignarEnvio.setToolTipText("Nuevo Envío");
		btnAsignarEnvio.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/asignar.png")));

		btnAsignarEnvio.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAsignarEnvio.setHorizontalTextPosition(SwingConstants.CENTER);

		GridBagConstraints gbc_btnEnvios = new GridBagConstraints();
		gbc_btnEnvios.insets = new Insets(0, 0, 0, 5);
		gbc_btnEnvios.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnEnvios.gridx = 5;
		gbc_btnEnvios.gridy = 0;
		panel_7.add(btnAsignarEnvio, gbc_btnEnvios);

		btnBack = new JButton("");
		btnBack.setToolTipText("Envíos Asignados");
		btnBack.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/asignados.png")));

		btnBack.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnBack.setHorizontalTextPosition(SwingConstants.CENTER);

		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 0, 5);
		gbc_btnBack.gridx = 6;
		gbc_btnBack.gridy = 0;
		panel_7.add(btnBack, gbc_btnBack);

		btnVerEntregados = new JButton("");
		btnVerEntregados.setToolTipText("Envíos Entregados");
		btnVerEntregados.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/entregados.png")));

		btnVerEntregados.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnVerEntregados.setHorizontalTextPosition(SwingConstants.CENTER);

		GridBagConstraints gbc_btnVerEnvios = new GridBagConstraints();
		gbc_btnVerEnvios.insets = new Insets(0, 0, 0, 5);
		gbc_btnVerEnvios.gridx = 7;
		gbc_btnVerEnvios.gridy = 0;
		panel_7.add(btnVerEntregados, gbc_btnVerEnvios);

		btnEnvios = new JButton("");
		btnEnvios.setToolTipText("Envíos del Día");
		btnEnvios.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/icons/report.png")));

		btnEnvios.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnEnvios.setHorizontalTextPosition(SwingConstants.CENTER);

		GridBagConstraints gbc_btnReporteEnvosDel = new GridBagConstraints();
		gbc_btnReporteEnvosDel.insets = new Insets(0, 0, 0, 5);
		gbc_btnReporteEnvosDel.gridx = 8;
		gbc_btnReporteEnvosDel.gridy = 0;
		panel_7.add(btnEnvios, gbc_btnReporteEnvosDel);

		spEnvios = new JScrollPane();
		GridBagConstraints gbc_spEnvios = new GridBagConstraints();
		gbc_spEnvios.fill = GridBagConstraints.BOTH;
		gbc_spEnvios.gridx = 1;
		gbc_spEnvios.gridy = 2;
		tabEnvios.add(spEnvios, gbc_spEnvios);

		tableEnvios = new JTable();
		tableEnvios.getTableHeader().setReorderingAllowed(false) ;
		spEnvios.setViewportView(tableEnvios);

		initialize();
	}

	public JMenu getMnHerramientas() {
		return mnHerramientas;
	}

	public JMenu getMnEdicin() {
		return mnEdicin;
	}

	public JPanel getTabAlertas() {
		return tabAlertas;
	}

	public JScrollPane getSpAlertas() {
		return spAlertas;
	}

	public JTable getTableAlertas() {
		return tableAlertas;
	}

	public JButton getBtnGenerarReporte() {
		return btnGenerarReporte;
	}

	public JLabel getLblDesdePersonalizado() {
		return lblDesdePersonalizado;
	}

	public JLabel getLblHastaPersonalizado() {
		return lblHastaPersonalizado;
	}

	public JTextField getTxtReporteDesde() {
		return txtReporteDesde;
	}

	public JTextField getTxtReporteHasta() {
		return txtReporteHasta;
	}

	public JPanel getTabReportes() {
		return tabReportes;
	}

	public JRadioButton getRdSemana() {
		return rdSemana;
	}

	public JRadioButton getRdMes() {
		return rdMes;
	}

	public JRadioButton getRdAnio() {
		return rdAnio;
	}

	public JRadioButton getRdPersonalizado() {
		return rdPersonalizado;
	}

	public JDatePickerImpl getDpiSemana() {
		return dpiSemana;
	}

	public JDatePickerImpl getDpiMes() {
		return dpiMes;
	}

	public JDatePickerImpl getDpiAnio() {
		return dpiAnio;
	}

	public JDatePickerImpl getDpiPersonalizado1() {
		return dpiPersonalizado1;
	}

	public JDatePickerImpl getDpiPersonalizado2() {
		return dpiPersonalizado2;
	}

	public JTable getTableContabilidad() {
		return tableContabilidad;
	}

	public JDatePickerImpl getFechaInicio() {
		return fechaInicio;
	}

	public JDatePickerImpl getFechaFin() {
		return fechaFin;
	}

	public JButton getBtnVerContabilidad() {
		return btnVerContabilidad;
	}

	public JScrollPane getSpContabilidad() {
		return spContabilidad;
	}

	private class DateLabelFormatter extends AbstractFormatter {

		private static final long serialVersionUID = 7596593350901299254L;
		private String datePattern = "dd/MM/yyyy";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}
			return "";
		}
	}

	public JMenuItem getMntmActualizar() {
		return mntmActualizar;
	}

	public JButton getBtnEnviarMailOT() {
		return btnEnviarMailOT;
	}

	public JButton getBtnEnviarMailSC() {
		return btnEnviarMailSC;
	}

	public JMenuItem getMntmDatos() {
		return mntmDatos;
	}

	public JMenuItem getMntmSalir() {
		return mntmSalir;
	}

	public JMenu getMenuLog() {
		return menuLog;
	}

	public JButton getBtnAgregarEd() {
		return btnAgregarEd;
	}

	public JPanel getTabUsuarios() {
		return tabUsuarios;
	}

	public JPanel getTabOts() {
		return tabOT_UT;
	}

	public JPanel getPanelScs() {
		return panelScs;
	}

	public JPanel getPanelProveedores() {
		return panelProveedores;
	}

	public JPanel getTabPiezas() {
		return tabPiezas;
	}

	public JPanel getTabEds() {
		return tabEds;
	}

	public JMenuItem getBtnCrearBackup() {
		return btnCrearBackup;
	}

	public void setBtnCrearBackup(JMenuItem btnCrearBackup) {
		this.btnCrearBackup = btnCrearBackup;
	}

	public JMenuItem getBtnCargarBackup() {
		return btnCargarBackup;
	}

	public void setBtnCargarBackup(JMenuItem btnCargarBackup) {
		this.btnCargarBackup = btnCargarBackup;
	}

	public JButton getBtnAgregarUsuario() {
		return btnAgregarUsuario;
	}

	public JTable getTableUsuarios() {
		return tableUsuarios;
	}

	public JTable getTablePiezas() {
		return tablePiezas;
	}

	public JComboBox<MarcaDTO> getCbMarcas() {
		return cbMarcas;
	}

	public JTable getTableEds() {
		return tableEds;
	}

	public JButton getBtnAadirPiezas() {
		return btnAadirPiezas;
	}

	public JTable getTableScs() {
		return tableScs;
	}

	public JButton getBtnNuevaSc() {
		return btnNuevaSc;
	}

	public JButton getBtnNuevaOt() {
		return btnNuevaOt;
	}

	public JPanel getTabContabilidad() {
		return tabContabilidad;
	}

	public JButton getBtnAgregarCliente() {
		return btnAgregarCliente;
	}

	public JTable getTableClientes() {
		return tableClientes;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 913, 487);
		jCDesktopPane1 = new JCDesktopPane();

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

	}

	public JCDesktopPane getjCDesktopPane1() {
		return jCDesktopPane1;
	}

	public JPanel getTabClientes() {
		return tabClientes;
	}

	public JTable getTableProveedores() {
		return tableProveedores;
	}

	public JButton getBtnNuevoProveedor() {
		return btnNuevoProveedor;
	}

	private void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public JPanel getTabFleteros() {
		return tabFleteros;
	}

	public void setTabFleteros(JPanel tabFleteros) {
		this.tabFleteros = tabFleteros;
	}

	public JButton getBtnNuevoFletero() {
		return btnNuevoFletero;
	}

	public void setBtnNuevoFletero(JButton btnNuevoFletero) {
		this.btnNuevoFletero = btnNuevoFletero;
	}

	public JTable getTableFleteros() {
		return tableFleteros;
	}

	public void setTableFleteros(JTable tableFleteros) {
		this.tableFleteros = tableFleteros;
	}

	public JComboBox<EstadoDTO> getComboEstadosUT() {
		return comboEstadosUT;
	}

	public void setComboEstadosUT(JComboBox<EstadoDTO> comboEstadosUT) {
		this.comboEstadosUT = comboEstadosUT;
	}

	public JComboBox<EstadoDTO> getComboEstadosUA() {
		return comboEstadosUA;
	}

	public void setComboEstadosUA(JComboBox<EstadoDTO> comboEstadosUA) {
		this.comboEstadosUA = comboEstadosUA;
	}

	public void setBtnNuevaOt(JButton btnNuevaOt) {
		this.btnNuevaOt = btnNuevaOt;
	}

	public void setBtnNuevaSc(JButton btnNuevaSc) {
		this.btnNuevaSc = btnNuevaSc;
	}

	public void setBtnEnviarMailOT(JButton btnEnviarMailOT) {
		this.btnEnviarMailOT = btnEnviarMailOT;
	}

	public void setBtnEnviarMailSC(JButton btnEnviarMailSC) {
		this.btnEnviarMailSC = btnEnviarMailSC;
	}

	public JTable getTableOT_UT() {
		return tableOT_UT;
	}

	public void setTableOT_UT(JTable tableOT_UT) {
		this.tableOT_UT = tableOT_UT;
	}

	public JTable getTableOT_UA() {
		return tableOT_UA;
	}

	public void setTableOT_UA(JTable tableOT_UA) {
		this.tableOT_UA = tableOT_UA;
	}

	public JPanel getTabOT_UT() {
		return tabOT_UT;
	}

	public void setTabOT_UT(JPanel tabOT_UT) {
		this.tabOT_UT = tabOT_UT;
	}

	public JPanel getTabOT_UA() {
		return tabOT_UA;
	}

	public void setTabOT_UA(JPanel tabOT_UA) {
		this.tabOT_UA = tabOT_UA;
	}

	public JPanel getTabImportarPrecios() {
		return tabImportarPrecios;
	}

	public JComboBox<ProveedorDTO> getComboBoxProvedores() {
		return comboBoxProvedores;
	}

	public JButton getBtnProcesar() {
		return btnProcesar;
	}

	public JButton getFileChooser() {
		return fileChooser;
	}

	public JMenuItem getMntmEditarZonas() {
		return mntmEditarZonas;
	}

	public void setMntmEditarZonas(JMenuItem mntmEditarZonas) {
		this.mntmEditarZonas = mntmEditarZonas;
	}

	public JButton getBtnReporteContable() {
		return btnReporteContable;
	}

	public void setBtnReporteContable(JButton btnReporteContable) {
		this.btnReporteContable = btnReporteContable;
	}

	public JComboBox<String> getComboReportes() {
		return comboReportes;
	}

	public void setComboReportes(JComboBox<String> comboReportes) {
		this.comboReportes = comboReportes;
	}

	public ButtonGroup getGroup() {
		return group;
	}

	public void setGroup(ButtonGroup group) {
		this.group = group;
	}

	public JTable getTableEnvios() {
		return tableEnvios;
	}

	public void setTableEnvios(JTable tableEnvios) {
		this.tableEnvios = tableEnvios;
	}

	public JComboBox<FleteroDTO> getComboFleteros() {
		return comboFleteros;
	}

	public void setComboFleteros(JComboBox<FleteroDTO> comboFleteros) {
		this.comboFleteros = comboFleteros;
	}

	public JButton getBtnAsignarEnvio() {
		return btnAsignarEnvio;
	}

	public void setBtnAsignarEnvio(JButton btnAsignarEnvio) {
		this.btnAsignarEnvio = btnAsignarEnvio;
	}

	public JButton getBtnVerEntregados() {
		return btnVerEntregados;
	}

	public void setBtnVerEntregados(JButton btnVerEnvios) {
		this.btnVerEntregados = btnVerEnvios;
	}

	public JButton getBtnEnvios() {
		return btnEnvios;
	}

	public void setBtnEnvios(JButton btnEnvios) {
		this.btnEnvios = btnEnvios;
	}

	public JButton getBtnBack() {
		return btnBack;
	}

	public void setBtnBack(JButton btnBack) {
		this.btnBack = btnBack;
	}

	public JPanel getTabEnvios() {
		return tabEnvios;
	}

	public void setTabEnvios(JPanel tabEnvios) {
		this.tabEnvios = tabEnvios;
	}

	public JMenuItem getMntmEditarLocalidades() {
		return mntmEditarLocalidades;
	}

	public void setMntmEditarLocalidades(JMenuItem mntmEditarLocalidades) {
		this.mntmEditarLocalidades = mntmEditarLocalidades;
	}

	public JButton getBtnBuscarOT() {
		return btnBuscarOT;
	}

	public void setBtnBuscarOT(JButton btnBuscarOT) {
		this.btnBuscarOT = btnBuscarOT;
	}

	public JButton getBtnBuscarCliente() {
		return btnBuscarCliente;
	}

	public void setBtnBuscarCliente(JButton btnBuscarCliente) {
		this.btnBuscarCliente = btnBuscarCliente;
	}

	public JMenuItem getMntmImportarPrecios() {
		return mntmImportarPrecios;
	}

	public void setMntmImportarPrecios(JMenuItem mntmImportarPrecios) {
		this.mntmImportarPrecios = mntmImportarPrecios;
	}

}
