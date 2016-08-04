package persistencia.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dto.ClienteDTO;
import dto.ElectrodomesticoDTO;
import dto.EstadoDTO;
import dto.LocalidadDTO;
import dto.MarcaDTO;
import dto.OrdenDTO;
import dto.PiezaDTO;
import dto.UsuarioDTO;
import dto.ZonaDTO;
import persistencia.conexion.Conexion;

public class OrdenDAO {

	/*------------------------------------------------------------------------------*/
	private final String readall = "SELECT * " 
			+ "FROM ot_ordenes_trabajo ot, " 
			+ "cli_clientes cli, pers_personal per, elec_electrodomesticos ed, " 
			+ "prod_marcas ma, ot_estados es, pers_roles rol " 
			+ "WHERE ot.idCliente = cli.idCliente " 
			+ "AND ot.idElectro = ed.idElectro " 
			+ "AND ot.idUsuarioAlta = per.IdPersonal " 
			+ "AND ot.estado_orden = es.idEstadoPosible "
			+ "AND ed.idProdMarca = ma.idProdMarca "
			+ "AND per.IdRol = rol.IdRol";

	private final String readPorFecha = "SELECT * " 
			+ "FROM ot_ordenes_trabajo ot, " 
			+ "cli_clientes cli, pers_personal per, elec_electrodomesticos ed, " 
			+ "prod_marcas ma, ot_estados es, pers_roles rol " 
			+ "WHERE ot.idCliente = cli.idCliente " 
			+ "AND ot.idElectro = ed.idElectro " 
			+ "AND ot.idUsuarioAlta = per.IdPersonal " 
			+ "AND ot.estado_orden = es.idEstadoPosible "
			+ "AND ed.idProdMarca = ma.idProdMarca "
			+ "AND per.IdRol = rol.IdRol "
			+ "AND ot.fechaReparado BETWEEN ? AND ? " 
			+ "ORDER BY ot.fechaReparado";

	private final String presupuestar = "UPDATE ot_ordenes_trabajo ot " + "SET ot.idTecnicoAsoc = ?, " + "ot.vencPresup = ?, " + "ot.manoDeObra = ?, " + "ot.costoDeEnvio = ?, " + "ot.estado_orden = 2 " + "WHERE ot.idOT = ?";

	private final String insertPresupuestadas = "INSERT INTO ot_piezas_presupuestadas " + "(idOT, idProdPieza, costoPresupuestada) " + "VALUES (?, ?, ?)";

	private final String readLocalidadDeCliente = "SELECT * FROM cli_clientes cli, localidades l, ot_zonas z " + "WHERE cli.Codigo_postal = l.codigoPostal " + "AND l.zonaDeEnvio = z.idZonaPosible AND " + "cli.IdCliente = ?";
	
	private final String readDatosEntrega = "SELECT * "
			+ "FROM ot_ordenes_trabajo ot, localidades l, ot_zonas oz "
			+ "WHERE l.codigoPostal = ot.codigoPostal "
			+ "AND l.zonaDeEnvio = oz.idZonaPosible "
			+ "AND ot.IdOT = ?";

	private final String readTecnico = "SELECT * FROM pers_personal, pers_roles r " + "WHERE IdPersonal = ?";

	private final String readforid = "SELECT * " 
			+ "FROM ot_ordenes_trabajo ot, " 
			+ "cli_clientes cli, pers_personal per, elec_electrodomesticos ed, " 
			+ "prod_marcas ma, ot_estados es, pers_roles rol " 
			+ "WHERE ot.idCliente = cli.idCliente " 
			+ "AND ot.idElectro = ed.idElectro " 
			+ "AND ot.idUsuarioAlta = per.IdPersonal " 
			+ "AND ot.estado_orden = es.idEstadoPosible "
			+ "AND ed.idProdMarca = ma.idProdMarca "
			+ "AND per.IdRol = rol.IdRol "
			+ "AND ot.idOT = ?";


	private final String readUsadas = "SELECT * FROM ot_piezas_usadas otpu, " + "prod_piezas_stock pps, prod_piezas pp, prod_marcas ma " + "WHERE otpu.idProdStock = pps.idProdStock " + "AND pps.idProdPieza = pp.idProdPieza " + "AND pp.idProdMarca = ma.IdProdMarca " + "AND otpu.idOT = ?";

	private final String readPresupuestadas = "SELECT * FROM ot_piezas_presupuestadas otpp, " + "prod_piezas pp, prod_marcas ma " + "WHERE otpp.idProdPieza = pp.idProdPieza " + "AND pp.idProdMarca = ma.IdProdMarca " + "AND otpp.idOT = ?";

	private final String deletePresupuestadas = "DELETE FROM ot_piezas_presupuestadas " + "WHERE idOT = ?";

	private final String deleteUsadas = "DELETE FROM ot_piezas_usadas " + "WHERE idProdStock = ?";

	private final String uptateEnvio = "UPDATE ot_ordenes_trabajo	" + "SET esDelivery = ? " + "WHERE idOT = ?";
	private final String updateDomicilioEnvio = "UPDATE ot_ordenes_trabajo SET domicilio = ?, codigoPostal = ?, esDelivery = ?, costoDeEnvio = ? WHERE idOT = ?";

	
	private final String uptateCostoEnvio = "UPDATE ot_ordenes_trabajo	" + "SET costoDeEnvio = ? " + "WHERE idOT = ?";

	private final String sp_insertPiezaUsada = "call agregarPiezasUsadasOT(?,?,?,?)";

	
	/*------------------------------------------------------------------------------*/

	private final String sp_obtenerItems = "call traerItemsOT(?, ?, ?, ?, ?)";
	private final String sp_cambiarEstado = "call cambiarEstadoOT(?,?,?,?)";
	private final String sp_agregarItems = "call agregarItemOt(?,?,?,?,?)";
	private final String sp_ReadEstados = "call traerOTsConEstado (?,?,?)";

	private final String sp_Insertar = "call crearOT(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private final Conexion conexion;

	/*------------------------------------------------------------------------------*/

	public OrdenDAO() throws Exception {
		conexion = Conexion.getConexion();
	}

	public void insert(OrdenDTO orden) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Insertar);

			cs.setInt(1, orden.getCliente().getIdCliente());
			cs.setInt(2, orden.getElectrodomestico().getIdElectro());
			cs.setString(3, orden.getDescripcion());
			cs.setInt(4, orden.getUsuarioAlta().getIdPersonal());
			cs.setBoolean(5, orden.isEsDelivery());
			cs.setBigDecimal(6, new BigDecimal(orden.getCostoDeEnvio()));
			cs.setString(7, orden.getDomicilioEntrega());
			cs.setInt(8, orden.getLocalidadEntrega().getCodigoPostal());
			cs.registerOutParameter(9, java.sql.Types.INTEGER);
			cs.registerOutParameter(10, java.sql.Types.INTEGER);
			cs.registerOutParameter(11, java.sql.Types.VARCHAR);

			cs.executeUpdate();				

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0)
				orden.setIdOT(cs.getInt("retIdOT"));

			else
				throw (new Exception(descErr));

			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
	}

	public void presupuestar(OrdenDTO orden) {
		//TODO: Verificar problema transaccional
		borrarPresupuestadas(orden);
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(presupuestar);
			statement.setInt(1, orden.getTecnicoAsoc().getIdPersonal());
			statement.setDate(2, new Date(orden.getVencPresup().getTimeInMillis()));
			statement.setBigDecimal(3, new BigDecimal(orden.getManoDeObra()));
			statement.setBigDecimal(4, new BigDecimal(orden.getCostoDeEnvio()));
			statement.setInt(5, orden.getIdOT());
			statement.executeUpdate();

			insertarPresupuestadas(orden);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
	}

	private void borrarPresupuestadas(OrdenDTO orden) {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(deletePresupuestadas);
			statement.setInt(1, orden.getIdOT());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
	}

	private void insertarPresupuestadas(OrdenDTO orden) {
		PreparedStatement statement;
		try {
			for (PiezaDTO p : orden.getPiezasPresupuestadas()) {
				statement = conexion.getSQLConexion().prepareStatement(insertPresupuestadas);
				statement.setInt(1, orden.getIdOT());
				statement.setInt(2, p.getIdProdPieza());
				statement.setBigDecimal(3, new BigDecimal(p.getPrecio_venta()));
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
	}

	public void cambiarEstadoOT(OrdenDTO orden, int idEstado) throws Exception {
		try {
			
			// Ojo si descomentan esto porque no cambia a estado entregada.
//			System.out.println("Orden: " + orden.getIdOT());
//			System.out.println("idEstado viejo : " + orden.getEstado().getId());
//			System.out.println("idEstado nuevo : " + idEstado);
			
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_cambiarEstado);

			cs.setInt(1, orden.getIdOT());
			cs.setInt(2, idEstado);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);

			cs.executeUpdate();				

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode != 0)
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
	}

	public List<OrdenDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<OrdenDTO> ordenes = new ArrayList<OrdenDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Calendar vencimientoPresupuestoCalendar = getCalendar(resultSet.getDate("vencPresup"));
				Calendar fechaReparadoCalendar = getCalendar(resultSet.getDate("fechaReparado"));
				Calendar expiraGarantiaCalendar = getCalendar(resultSet.getDate("expiraGarantia"));

				ClienteDTO cliente = new ClienteDTO(
						resultSet.getInt("cli.idCliente"), 
						resultSet.getString("cli.Nombre"), 
						resultSet.getString("cli.Apellido"), 
						null,
						null, // Por si no tiene zona.
						resultSet.getString("cli.Telefono"), 
						resultSet.getString("cli.Email"));

				if (resultSet.getString("Codigo_postal") != null) {
					cliente.setDireccion(resultSet.getString("cli.Direccion"));
					cliente.setLocalidad(dameLocalidad(cliente));
				}

				MarcaDTO marca = new MarcaDTO(resultSet.getInt("ma.idProdMarca"), resultSet.getString("ma.Nombre"));

				ElectrodomesticoDTO electrodomestico = new ElectrodomesticoDTO(resultSet.getInt("ed.IdElectro"), marca, resultSet.getString("ed.Modelo"), resultSet.getString("ed.Descripcion"));

				UsuarioDTO usuarioAlta = new UsuarioDTO(resultSet.getInt("per.IdPersonal"), resultSet.getInt("per.IdRol"), resultSet.getString("rol.Descripcion"), resultSet.getString("per.Nombre"), resultSet.getString("per.Apellido"), resultSet.getString("per.Usuario"), resultSet.getString("per.Pass"));

				OrdenDTO orden = new OrdenDTO(resultSet.getInt("idOT"), cliente, electrodomestico, resultSet.getString("descripcion"), usuarioAlta, null, // tecnico
						resultSet.getBoolean("esDelivery"), vencimientoPresupuestoCalendar, fechaReparadoCalendar, expiraGarantiaCalendar, null, // otAsociada
						new EstadoDTO(resultSet.getInt("es.idEstadoPosible"), resultSet.getString("es.estado")), null, // piezasPresupuestadas
						null, // piezasUsadas
						resultSet.getDouble("manoDeObra"), resultSet.getDouble("costoDeEnvio"),
						null,
						null);
				if (resultSet.getString("idTecnicoAsoc") != null)
					orden.setTecnicoAsoc(dameTecnico(resultSet.getInt("idTecnicoAsoc")));
				if (resultSet.getString("idOtAsociada") != null)
					orden.setOtAsociada(dameOt(resultSet.getInt("idOtAsociada")));
				orden.setPiezasPresupuestadas(damePresupuestadas(orden));
				orden.setPiezasUsadas(dameUsadas(orden));
				
				if (resultSet.getString("ot.domicilio") != null) {
					orden.setDomicilioEntrega(resultSet.getString("ot.domicilio"));
					orden.setLocalidadEntrega(dameLocalidadOrden(orden));
				}
				
				ordenes.add(orden);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
		return ordenes;
	}

	private List<PiezaDTO> dameUsadas(OrdenDTO orden) {
		PreparedStatement statement;
		ResultSet resultSet;
		List<PiezaDTO> piezas = new ArrayList<PiezaDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readUsadas);
			statement.setInt(1, orden.getIdOT());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				PiezaDTO pieza = new PiezaDTO(resultSet.getInt("otpu.idProdStock"), resultSet.getInt("pp.idProdPieza"), new MarcaDTO(resultSet.getInt("ma.IdProdMarca"), resultSet.getString("ma.nombre")), resultSet.getString("pp.idUnico"), resultSet.getString("pp.Descripcion"), resultSet.getFloat("Precio_venta"), resultSet.getInt("bajo_stock"));
				piezas.add(pieza);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
		return piezas;
	}

	private List<PiezaDTO> damePresupuestadas(OrdenDTO orden) {
		PreparedStatement statement;
		ResultSet resultSet;
		List<PiezaDTO> piezas = new ArrayList<PiezaDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readPresupuestadas);
			statement.setInt(1, orden.getIdOT());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				PiezaDTO pieza = new PiezaDTO(resultSet.getInt("otpp.idProdPieza"), new MarcaDTO(resultSet.getInt("ma.IdProdMarca"), resultSet.getString("ma.nombre")), resultSet.getString("pp.idUnico"), resultSet.getString("pp.Descripcion"), resultSet.getFloat("Precio_venta"), resultSet.getInt("bajo_stock"));
				piezas.add(pieza);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
		return piezas;
	}

	private LocalidadDTO dameLocalidad(ClienteDTO cliente) {
		PreparedStatement statement;
		ResultSet resultSet;
		LocalidadDTO localidad = null;
		try {
			statement = conexion.getSQLConexion().prepareStatement(readLocalidadDeCliente);
			statement.setInt(1, cliente.getIdCliente());
			resultSet = statement.executeQuery();
			if (resultSet.next())
				localidad = new LocalidadDTO(resultSet.getInt("l.codigoPostal"), resultSet.getString("l.nombre"), new ZonaDTO(resultSet.getInt("z.idZonaPosible"), resultSet.getString("z.nombre"), resultSet.getInt("z.Precio")), resultSet.getString("l.provincia"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
		return localidad;
	}
	
	private LocalidadDTO dameLocalidadOrden(OrdenDTO orden) {
		PreparedStatement statement;
		ResultSet resultSet;
		LocalidadDTO localidad = null;
		try {
			statement = conexion.getSQLConexion().prepareStatement(readDatosEntrega);
			statement.setInt(1, orden.getIdOT());
			resultSet = statement.executeQuery();
			if (resultSet.next())
				localidad = new LocalidadDTO(resultSet.getInt("l.codigoPostal"), resultSet.getString("l.nombre"), new ZonaDTO(resultSet.getInt("oz.idZonaPosible"), resultSet.getString("oz.nombre"), resultSet.getInt("oz.Precio")), resultSet.getString("l.provincia"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
		return localidad;
	}

	public OrdenDTO dameOt(int i) {
		PreparedStatement statement;
		ResultSet resultSet;
		OrdenDTO orden = null;
		try {
			statement = conexion.getSQLConexion().prepareStatement(readforid);
			statement.setInt(1, i);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {

				Calendar vencimientoPresupuestoCalendar = getCalendar(resultSet.getDate("vencPresup"));
				Calendar fechaReparadoCalendar = getCalendar(resultSet.getDate("fechaReparado"));
				Calendar expiraGarantiaCalendar = getCalendar(resultSet.getDate("expiraGarantia"));

				ClienteDTO cliente = new ClienteDTO(resultSet.getInt("cli.idCliente"), resultSet.getString("cli.Nombre"), 
						resultSet.getString("cli.Apellido"), null, 
						null, // Por si no tiene zona.
						resultSet.getString("cli.Telefono"), resultSet.getString("cli.Email"));

				if (resultSet.getString("cli.Codigo_postal") != null) {
					cliente.setDireccion(resultSet.getString("cli.Direccion"));
					cliente.setLocalidad(dameLocalidad(cliente));
				}

				MarcaDTO marca = new MarcaDTO(resultSet.getInt("ma.idProdMarca"), resultSet.getString("ma.Nombre"));

				ElectrodomesticoDTO electrodomestico = new ElectrodomesticoDTO(resultSet.getInt("ed.IdElectro"), marca, resultSet.getString("ed.Modelo"), resultSet.getString("ed.Descripcion"));

				UsuarioDTO usuarioAlta = new UsuarioDTO(resultSet.getInt("per.IdPersonal"), resultSet.getInt("per.IdRol"), resultSet.getString("rol.Descripcion"), resultSet.getString("per.Nombre"), resultSet.getString("per.Apellido"), resultSet.getString("per.Usuario"), resultSet.getString("per.Pass"));

				orden = new OrdenDTO(resultSet.getInt("idOT"), cliente, electrodomestico, resultSet.getString("descripcion"), usuarioAlta, null, // tecnico
						resultSet.getBoolean("esDelivery"), vencimientoPresupuestoCalendar, fechaReparadoCalendar, expiraGarantiaCalendar, null, // otAsociada
						new EstadoDTO(resultSet.getInt("es.idEstadoPosible"), resultSet.getString("es.estado")), null, // piezasPresupuestadas
						null, // piezasUsadas
						resultSet.getDouble("manoDeObra"), resultSet.getDouble("costoDeEnvio"),
						null,
						null);
				if (resultSet.getString("idTecnicoAsoc") != null)
					orden.setTecnicoAsoc(dameTecnico(resultSet.getInt("idTecnicoAsoc")));
				if (resultSet.getString("idOtAsociada") != null)
					orden.setOtAsociada(dameOt(resultSet.getInt("idOtAsociada")));
				orden.setPiezasPresupuestadas(damePresupuestadas(orden));
				
				if (resultSet.getString("ot.domicilio") != null) {
					orden.setDomicilioEntrega(resultSet.getString("ot.domicilio"));
					orden.setLocalidadEntrega(dameLocalidadOrden(orden));
				}
				
				orden.setPiezasUsadas(dameUsadas(orden));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
		return orden;
	}

	private UsuarioDTO dameTecnico(int i) {
		PreparedStatement statement;
		ResultSet resultSet;
		UsuarioDTO tecnico = null;
		try {
			statement = conexion.getSQLConexion().prepareStatement(readTecnico);
			statement.setInt(1, i);
			resultSet = statement.executeQuery();
			if (resultSet.next())
				tecnico = new UsuarioDTO(resultSet.getInt("IdPersonal"), resultSet.getInt("r.IdRol"), resultSet.getString("r.Descripcion"), resultSet.getString("Nombre"), resultSet.getString("Apellido"), resultSet.getString("Usuario"), resultSet.getString("pass"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
		return tecnico;
	}

	public void updateOpcionesEnvio(OrdenDTO orden) {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(uptateEnvio);
			statement.setBoolean(1, orden.isEsDelivery());
			statement.setInt(2, orden.getIdOT());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
		if (orden.isEsDelivery())
			updateCostoEnvio(orden.getIdOT(), new BigDecimal(orden.getLocalidadEntrega().getZonaDeEnvio().getPrecio()));
		else
			updateCostoEnvio(orden.getIdOT(), new BigDecimal(0.0));
	}

	private void updateCostoEnvio(int id, BigDecimal costo) {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(uptateCostoEnvio);
			statement.setBigDecimal(1, costo);
			statement.setInt(2, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
	}


	private Calendar getCalendar(Date sqlDate) throws SQLException {
		if (sqlDate == null)
			return null;

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(sqlDate.getTime());
		return calendar;
	}


	public void insertUsada(OrdenDTO orden, PiezaDTO pieza) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_insertPiezaUsada);

			cs.setInt(1, orden.getIdOT());
			cs.setInt(2, pieza.getIdProdPieza());

			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);

			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {

			} else
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally 
		{
			conexion.cerrarConexion();
		}
	}

	public void deleteUsada(OrdenDTO orden, PiezaDTO pieza) {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(deleteUsadas);
			statement.setInt(1, pieza.getIdProdStock());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
	}

	public List<OrdenDTO> readPorFechas(Calendar inicio, Calendar fin) {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<OrdenDTO> ordenes = new ArrayList<OrdenDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readPorFecha);
			statement.setDate(1, new Date(inicio.getTimeInMillis()));
			statement.setDate(2, new Date(fin.getTimeInMillis()));
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Calendar vencimientoPresupuestoCalendar = getCalendar(resultSet.getDate("vencPresup"));
				Calendar fechaReparadoCalendar = getCalendar(resultSet.getDate("fechaReparado"));
				Calendar expiraGarantiaCalendar = getCalendar(resultSet.getDate("expiraGarantia"));

				ClienteDTO cliente = new ClienteDTO(resultSet.getInt("cli.idCliente"), resultSet.getString("cli.Nombre"), 
						resultSet.getString("cli.Apellido"), resultSet.getString("cli.Direccion"), 
						null, // Por si no tiene zona.
						resultSet.getString("cli.Telefono"), resultSet.getString("cli.Email"));

				if (resultSet.getString("cli.Codigo_postal") != null) {
					cliente.setDireccion(resultSet.getString("cli.Direccion"));
					cliente.setLocalidad(dameLocalidad(cliente)); 
				}

				MarcaDTO marca = new MarcaDTO(resultSet.getInt("ma.idProdMarca"), resultSet.getString("ma.Nombre"));

				ElectrodomesticoDTO electrodomestico = new ElectrodomesticoDTO(resultSet.getInt("ed.IdElectro"), marca, resultSet.getString("ed.Modelo"), resultSet.getString("ed.Descripcion"));

				UsuarioDTO usuarioAlta = new UsuarioDTO(resultSet.getInt("per.IdPersonal"), resultSet.getInt("per.IdRol"), resultSet.getString("rol.Descripcion"), resultSet.getString("per.Nombre"), resultSet.getString("per.Apellido"), resultSet.getString("per.Usuario"), resultSet.getString("per.Pass"));

				OrdenDTO orden = new OrdenDTO(resultSet.getInt("idOT"), cliente, electrodomestico, resultSet.getString("descripcion"), usuarioAlta, null, // tecnico
						resultSet.getBoolean("esDelivery"), vencimientoPresupuestoCalendar, fechaReparadoCalendar, expiraGarantiaCalendar, null, // otAsociada
						new EstadoDTO(resultSet.getInt("es.idEstadoPosible"), resultSet.getString("es.estado")), null, // piezasPresupuestadas
						null, // piezasUsadas
						resultSet.getDouble("manoDeObra"), resultSet.getDouble("costoDeEnvio"),
						null,
						null);
				if (resultSet.getString("idTecnicoAsoc") != null)
					orden.setTecnicoAsoc(dameTecnico(resultSet.getInt("idTecnicoAsoc")));
				if (resultSet.getString("idOtAsociada") != null)
					orden.setOtAsociada(dameOt(resultSet.getInt("idOtAsociada")));
				orden.setPiezasPresupuestadas(damePresupuestadas(orden));
				orden.setPiezasUsadas(dameUsadas(orden));
				
				if (resultSet.getString("ot.domicilio") != null) {
					orden.setDomicilioEntrega(resultSet.getString("ot.domicilio"));
					orden.setLocalidadEntrega(dameLocalidadOrden(orden));
				}
				
				ordenes.add(orden);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
		return ordenes;
	}

	public void actualizarEntrega(OrdenDTO orden) {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(updateDomicilioEnvio);
			statement.setString(1, orden.getDomicilioEntrega());
			statement.setInt(2, orden.getLocalidadEntrega().getCodigoPostal());
			statement.setBoolean(3, orden.isEsDelivery());
			if (orden.isEsDelivery())
				statement.setBigDecimal(4, new BigDecimal(orden.getLocalidadEntrega().getZonaDeEnvio().getPrecio()));
			else
				statement.setBigDecimal(4, new BigDecimal(0.0));
			statement.setInt(5, orden.getIdOT());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}		
	}
}