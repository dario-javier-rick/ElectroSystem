package modelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dto.AlertaDTO;
import dto.ClienteDTO;
import dto.DetallesEnvioDTO;
import dto.ElectrodomesticoDTO;
import dto.EnvioDTO;
import dto.FleteroDTO;
import dto.HojaDeRutaDTO;
import dto.LocalidadDTO;
import dto.MarcaDTO;
import dto.MovimientoDTO;
import dto.OrdenDTO;
import dto.PiezaDTO;
import dto.PrecioPiezaDTO;
import dto.ProveedorDTO;
import dto.RankElectrodomesticosDTO;
import dto.RankIncumplidoresDTO;
//import dto.RankIncumplidoresDTO;
import dto.RankPiezasDTO;
import dto.SolicitudCompraDTO;
import dto.UsuarioDTO;
import dto.VehiculoDTO;
import dto.ZonaDTO;
import persistencia.dao.AlertaDAO;
import persistencia.dao.ClienteDAO;
import persistencia.dao.ElectrodomesticoDAO;
import persistencia.dao.EnvioDAO;
import persistencia.dao.FleteroDAO;
import persistencia.dao.LocalidadDAO;
import persistencia.dao.MarcaDAO;
import persistencia.dao.OrdenDAO;
import persistencia.dao.PiezaDAO;
import persistencia.dao.ProveedorDAO;
import persistencia.dao.ReporteDAO;
import persistencia.dao.SolicitudCompraDAO;
import persistencia.dao.UsuarioDAO;
import persistencia.dao.VehiculoDAO;
import persistencia.dao.ZonaDAO;

public class Modelo {
	private ClienteDAO cliente;
	private OrdenDAO ordenTrabajo;
	private ElectrodomesticoDAO electrodomestico;
	private ProveedorDAO proveedor;
	private MarcaDAO marca;
	private PiezaDAO pieza;
	private SolicitudCompraDAO solicitudCompra;
	private UsuarioDAO usuario;
	private FleteroDAO fletero;
	private VehiculoDAO vehiculo;
	private LocalidadDAO localidad;
	private ZonaDAO zona;
	private EnvioDAO envio;
	private ReporteDAO reportes;
	private AlertaDAO alerta;

	private static Modelo instance;

	private Modelo() throws Exception {
		cliente = new ClienteDAO();
		ordenTrabajo = new OrdenDAO();
		electrodomestico = new ElectrodomesticoDAO();
		marca = new MarcaDAO();
		proveedor = new ProveedorDAO();
		pieza = new PiezaDAO();
		solicitudCompra = new SolicitudCompraDAO();
		usuario = new UsuarioDAO();
		fletero = new FleteroDAO();
		vehiculo = new VehiculoDAO();
		localidad = new LocalidadDAO();
		zona = new ZonaDAO();
		envio = new EnvioDAO();
		reportes = new ReporteDAO();
		alerta = new AlertaDAO();
	}

	public static Modelo getInstance() throws Exception {
		if (instance == null)
			instance = new Modelo();
		return instance;
	}

	public void agregarCliente(ClienteDTO nuevoCliente) throws Exception {
		cliente.insert(nuevoCliente);
	}

	public void borrarCliente(ClienteDTO cliente, int idPersona) throws Exception {
		this.cliente.delete(cliente, idPersona);
	}

	public List<ClienteDTO> obtenerClientes() throws Exception {
		return cliente.readAll();
	}

	public void actualizarCliente(ClienteDTO cliente) throws Exception {
		this.cliente.update(cliente);
	}

	public void agregarOrden(OrdenDTO nuevaOrden) throws Exception {
		ordenTrabajo.insert(nuevaOrden);
	}

	public void presupuestarOT(OrdenDTO orden) throws Exception {
		ordenTrabajo.presupuestar(orden);
	}

	public List<OrdenDTO> obtenerOrdenes() throws Exception {
		return this.ordenTrabajo.readAll();
	}

	public List<ElectrodomesticoDTO> obtenerElectrodomesticos() throws Exception {
		return electrodomestico.readAll();
	}

	public List<MarcaDTO> obtenerMarcas() throws Exception {
		return marca.readAll();
	}

	public void agregarElectrodomestico(ElectrodomesticoDTO electrodomestico) throws Exception {
		this.electrodomestico.insert(electrodomestico);
	}

	public void actualizarElectrodomestico(ElectrodomesticoDTO electrodomestico) throws Exception {
		this.electrodomestico.update(electrodomestico);
	}

	public void agregarMarca(MarcaDTO nuevaMarca) throws Exception {
		marca.insert(nuevaMarca);
	}

	public void actualizarMarca(MarcaDTO marca_a_actualizar) throws Exception {
		marca.update(marca_a_actualizar);
	}

	public List<PiezaDTO> obtenerItems() throws Exception {
		return pieza.readAll();
	}

	public void borrarElectrodomestico(ElectrodomesticoDTO electrodomestico, int idPersonal) throws Exception {
		this.electrodomestico.delete(electrodomestico, idPersonal);

	}

	public void agregarSolicitud(SolicitudCompraDTO sc, int idUsuario) throws Exception {
		solicitudCompra.insert(sc, idUsuario);
	}

	public void procesarSolicitud(SolicitudCompraDTO sc) throws Exception {
		solicitudCompra.procesarSolicitud(sc);
	}

	public void actualizarSolicitud(SolicitudCompraDTO sc) throws Exception {
		solicitudCompra.update(sc);
	}

	public void cambiarEstado(SolicitudCompraDTO sc, int idEstado) throws Exception {
		solicitudCompra.cambiarEstado(sc, idEstado);
	}

	public List<SolicitudCompraDTO> obtenerSolicitudes() throws Exception {
		List<SolicitudCompraDTO> solicitudes = solicitudCompra.readAll();
		for (SolicitudCompraDTO s : solicitudes) {
			s.getProveedor().setMarcas(this.proveedor.traerMarcasProvistas(s.getProveedor().getIdProveedor()));
		}
		return solicitudes;
	}

	public List<MarcaDTO> obtenerMarcasProveedor(int idProveedor) throws Exception {
		return this.proveedor.traerMarcasProvistas(idProveedor);

	}

	public void updatePrecioPiezaProveedor(int idProveedor, List<PrecioPiezaDTO> piezas) throws Exception {
		this.proveedor.updatePrecioPiezaProveedor(idProveedor, piezas);
	}

	public void agregarPieza(PiezaDTO pieza) throws Exception {
		this.pieza.insert(pieza);
	}

	public void eliminarPieza(PiezaDTO pieza, int idPersona) throws Exception {
		this.pieza.delete(pieza, idPersona);
	}

	public void modificarPieza(PiezaDTO pieza) throws Exception {
		this.pieza.update(pieza);
	}

	public List<UsuarioDTO> obtenerUsuarios() throws Exception {
		return usuario.readAll();
	}

	public List<UsuarioDTO> obtenerUsuariosComunes() throws Exception {
		return usuario.readComunes();
	}

	public void agregarUsuario(UsuarioDTO usuario) throws Exception {
		this.usuario.insert(usuario);
	}

	public void borrarUsuario(UsuarioDTO usuario_a_eliminar, UsuarioDTO usuarioLogueado) throws Exception {
		this.usuario.delete(usuario_a_eliminar, usuarioLogueado);
	}

	public UsuarioDTO obtenerUsuario(String Usuario) throws Exception {
		return (this.usuario.obtenerUsuario(Usuario));
	}

	public void agregarFletero(FleteroDTO fletero) throws Exception {
		this.fletero.insert(fletero);
	}

	public List<FleteroDTO> obtenerFleteros() throws Exception {
		return fletero.readAll();
	}

	public List<VehiculoDTO> obtenerVehiculos() throws Exception {
		return this.vehiculo.readAll();
	}

	public void agregarVehiculo(VehiculoDTO vehiculo) throws Exception {
		this.vehiculo.insert(vehiculo);
	}

	public void agregarProveedor(ProveedorDTO proveedor_nuevo) throws Exception {
		this.proveedor.insert(proveedor_nuevo);
	}

	public void insertarMarcaProveedor(int idProveedor, int idMarca) throws Exception {
		this.proveedor.insertarMarcaProveedor(idProveedor, idMarca);
	}

	public void actualizarProveedor(ProveedorDTO proveedor_a_actualizar, List<MarcaDTO> marcas_anterior) throws Exception {
		this.proveedor.update(proveedor_a_actualizar, marcas_a_borrar(marcas_anterior, proveedor_a_actualizar.getMarcas()), marcas_a_agregar(marcas_anterior, proveedor_a_actualizar.getMarcas()));
	}

	private List<MarcaDTO> marcas_a_borrar(List<MarcaDTO> anteriores, List<MarcaDTO> nuevas) throws Exception {
		List<MarcaDTO> marcas_a_quitar = new ArrayList<MarcaDTO>();
		for (MarcaDTO anterior : anteriores) {
			boolean quitar = true;
			for (MarcaDTO nueva : nuevas) {
				if (nueva.equals(anterior))
					quitar = false;
			}
			if (quitar == true)
				marcas_a_quitar.add(anterior);
		}
		return marcas_a_quitar;
	}

	private List<MarcaDTO> marcas_a_agregar(List<MarcaDTO> anteriores, List<MarcaDTO> nuevas) throws Exception {
		List<MarcaDTO> marcas_a_agregar = new ArrayList<MarcaDTO>();
		for (MarcaDTO nueva : nuevas) {
			boolean agregar = true;
			for (MarcaDTO anterior : anteriores) {
				if (nueva.equals(anterior))
					agregar = false;
			}
			if (agregar == true)
				marcas_a_agregar.add(nueva);
		}
		return marcas_a_agregar;
	}

	public void borrarProveedor(ProveedorDTO proveedor_a_eliminar, UsuarioDTO usuarioLogueado) throws Exception {
		this.proveedor.delete(proveedor_a_eliminar, usuarioLogueado);
	}

	public List<ProveedorDTO> obtenerProveedores() throws Exception {
		return proveedor.readAll2();
	}

	public void actualizarUsuario(UsuarioDTO usuario_a_editar) throws Exception {
		this.usuario.update(usuario_a_editar);
	}

	public void borrarFletero(int fletero, int idPersonal) throws Exception {
		this.fletero.delete(fletero, idPersonal);
	}

	public void actualizarFletero(FleteroDTO fletero_a_editar) throws Exception {
		this.fletero.update(fletero_a_editar);
	}

	public boolean verificarClave(UsuarioDTO usuario, String clave) throws Exception {
		return this.usuario.verificarClave(usuario, clave);
	}

	public void guardarClaveDesbl(String clave) throws Exception {
		this.usuario.guardarClaveDesbl(clave);
	}

	public String obtenerMail() throws Exception {
		return this.usuario.readMail();
	}

	public boolean verificarClaveReest(String clave) throws Exception {
		return this.usuario.verificarClaveReest(clave);
	}

	public void reestablecerSuperUsuario() throws Exception {
		this.usuario.reestablecerSuperusuario();
	}

	public void actualizarMailSuperusuario(String mail) throws Exception {
		usuario.actulizarMail(mail);
	}

	public void actualizarEstado(OrdenDTO orden, int idEstado) throws Exception {
		this.ordenTrabajo.cambiarEstadoOT(orden, idEstado);
	}

	public void altaStockPieza(PiezaDTO pieza, int cantidad) throws Exception {
		this.pieza.altaStock(pieza, cantidad);
	}

	public void eliminarVehiculo(VehiculoDTO vehiculo, UsuarioDTO usuario) throws Exception {
		this.vehiculo.delete(vehiculo, usuario);
	}

	public void actualizarVehiculo(VehiculoDTO vehiculo) throws Exception {
		this.vehiculo.update(vehiculo);
	}

	public List<PrecioPiezaDTO> obtenerPrecioCompraItems(int idProveedor, int idMarca) throws Exception {
		return pieza.obtenerPrecioCompraItems(idProveedor, idMarca);
	}

	public void bajaStockPieza(PiezaDTO pieza, int cantidad, int estado) throws Exception {
		this.pieza.bajaStock(pieza, cantidad, estado);
	}

	public void agregarLocalidad(LocalidadDTO localidad) throws Exception {
		this.localidad.insert(localidad);
	}

	public List<LocalidadDTO> obtenerLocalidades() throws Exception {
		return this.localidad.readAll();
	}

	public List<ZonaDTO> obtenerZonas() throws Exception {
		return this.zona.readAll();
	}

	public void actualizarZona(ZonaDTO zona) throws Exception {
		this.zona.update(zona);
	}

	public void actualizarEnvioOT(OrdenDTO orden) throws Exception {
		this.ordenTrabajo.updateOpcionesEnvio(orden);
	}

	public boolean revisarStock(PiezaDTO pieza) throws Exception {
		return (this.pieza.dameStock(pieza) > 0);
	}

	public void agregarUsada(OrdenDTO orden, PiezaDTO pieza) throws Exception {
		this.ordenTrabajo.insertUsada(orden, pieza);
	}

	public void quitarUsada(OrdenDTO orden, PiezaDTO pieza) throws Exception {
		this.ordenTrabajo.deleteUsada(orden, pieza);
		this.pieza.reestablecerPiezaDeOT(pieza);
	}

	public OrdenDTO obtenerOrdenPorId(int id) throws Exception {
		return this.ordenTrabajo.dameOt(id);
	}

	public List<RankElectrodomesticosDTO> getRankElectrodomesticos(int porcentaje, Calendar ini, Calendar fin) throws Exception {
		return this.reportes.getRankElectrodomesticos(porcentaje, ini, fin);
	}

	public List<RankPiezasDTO> getRankPiezas(int porcentaje, Calendar ini, Calendar fin) throws Exception {
		return this.reportes.getRankPiezas(porcentaje, ini, fin);
	}

	public MovimientoDTO obtenerMovimientos(Calendar inicio, Calendar fin) throws Exception {
		// TODO ordenar
		MovimientoDTO movimientos = new MovimientoDTO(inicio, fin);
		List<OrdenDTO> ordenes = this.ordenTrabajo.readPorFechas(inicio, fin);
		List<SolicitudCompraDTO> solicitudes = this.solicitudCompra.readProcesadasPorFecha(inicio, fin);
		movimientos.setIngresos(ordenes);
		movimientos.setEgresos(solicitudes);

		return movimientos;
	}

	public void agregarEnvio(EnvioDTO envio) throws Exception {
		this.envio.insertEnvio(envio);
	}

	public void agregarDetalle(DetallesEnvioDTO detalles, EnvioDTO envio) throws Exception {
		// seria como agregar una ot al envio
		this.envio.insertDetalle(detalles, envio);
	}

	public List<EnvioDTO> obtenerEnvios() throws Exception {
		return this.envio.readAll();
	}

	public void quitarOTdeEnvio(OrdenDTO orden) throws Exception {
		this.envio.quitarOTenvio(orden);
	}

	public List<EnvioDTO> obtenerEnviosPor(FleteroDTO fletero) throws Exception {
		return this.envio.readByFletero(fletero);
	}

	public List<DetallesEnvioDTO> obtenerDetalles(EnvioDTO envio) throws Exception {
		return this.envio.readAllDetalles(envio);
	}

	public List<HojaDeRutaDTO> obtenerHojaRuta(FleteroDTO fletero) throws Exception {
		return reportes.getHojaDeRuta(fletero);
	}

	public List<AlertaDTO> obtenerAlertas() throws Exception {
		List<AlertaDTO> alertas = this.alerta.readAll();

		for (AlertaDTO alerta : alertas) {
			ObtenerUltimaSCpendiente(alerta);
		}

		return alertas;
	}

	private void ObtenerUltimaSCpendiente(AlertaDTO alerta) throws Exception {
		this.alerta.obtenerUltimaSCPendiente(alerta);
	}

	public void actualizarEntregado(OrdenDTO orden, EnvioDTO envio_a_cargar) throws Exception {
		this.envio.updateEntregado(orden, envio_a_cargar);
	}

	public List<DetallesEnvioDTO> readAllEntregadas() {
		return this.envio.readAllEntregados();
	}

	public void agregarProveedorIncumplidor(ProveedorDTO proveedor) throws Exception {
		this.proveedor.insertIncumplidor(proveedor);
	}

	public List<RankIncumplidoresDTO> getRankIncumplidores(Calendar ini, Calendar fin) throws Exception {
		return this.reportes.getRankIncumplidores(ini, fin);
	}

	public void actualizarDomicilioEntrega(OrdenDTO orden) throws Exception {
		this.ordenTrabajo.actualizarEntrega(orden);
	}

	public ProveedorDTO obtenerProveedorMasBarato(PrecioPiezaDTO pieza) throws Exception {
		return this.proveedor.obtenerProveedorMasBarato(pieza);
	}

	public void actualizarLocalidad(LocalidadDTO localidad) throws Exception {
		this.localidad.update(localidad);
	}
}
