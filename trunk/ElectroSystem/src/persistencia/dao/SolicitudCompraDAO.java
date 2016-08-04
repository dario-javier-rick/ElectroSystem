package persistencia.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dto.EstadoDTO;
import dto.MarcaDTO;
import dto.PiezaDTO;
import dto.PrecioPiezaDTO;
import dto.ProveedorDTO;
import dto.SolicitudCompraDTO;
import persistencia.conexion.Conexion;

public class SolicitudCompraDAO {

	private final String sp_Insertar = "call insertarSolicitudCompra(?,?,?,?,?,?)";
	private final String sp_update = "call eliminarPiezasSolicitud(?,?,?)";
	private final String sp_ReadAll = "call traerSolicitudes(?,?)";
	private final String sp_traerSolicitudXid = "call traerSolicitudes(?,?)";
	private final String sp_insertarPiezasSolicitud = "call insertarPiezasSolicitud(?,?,?,?,?)";
	private final String sp_getPiezaProveedor = "call getPiezaProveedor(?,?,?)";
	private final String sp_ReadProcesadasPorFecha = "call traerProcesadasPorFecha(?,?,?,?,?)";

	private final String sp_cambiarEstado = "call cambiarEstadoSC(?,?,?,?)";
	private final String sp_altaPiezas = "call altaPiezaStockFisico(?,?,?,?)";
	
	private final Conexion conexion;

	public SolicitudCompraDAO() throws Exception {
		conexion = Conexion.getConexion();
	}
	public void insert(SolicitudCompraDTO solicitudCompra, int idUsuario) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Insertar);

			cs.setInt(1, 1); // Estado ingresada hardcodeado.
			cs.setInt(2, idUsuario);
			cs.setInt(3, solicitudCompra.getProveedor().getIdProveedor());
			cs.registerOutParameter(4, java.sql.Types.INTEGER);
			cs.registerOutParameter(5, java.sql.Types.INTEGER);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				solicitudCompra.setId(cs.getInt("retIdSC"));

				// Agrego las piezas a la SC
				for (PrecioPiezaDTO piezas : solicitudCompra.getPiezas()) {
					PiezaDTO pieza = piezas.getPieza();
					insertarPiezasSolicitud(solicitudCompra.getId(), pieza.getIdProdPieza(), piezas.getCantidad());
				}
			} else
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
	}

	private void insertarPiezasSolicitud(int idSolcCompra, int idProdPieza, int cantidad) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_insertarPiezasSolicitud);

			cs.setInt(1, idSolcCompra);
			cs.setInt(2, idProdPieza);
			cs.setInt(3, cantidad);
			cs.registerOutParameter(4, java.sql.Types.INTEGER);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode != 0)
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
	}

	public void update(SolicitudCompraDTO solicitudCompra) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_update);
			// borro las piezas
			cs.setInt(1, solicitudCompra.getId());
			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {

				// Agrego las piezas a la SC
				for (PrecioPiezaDTO piezas : solicitudCompra.getPiezas()) {
					PiezaDTO pieza = piezas.getPieza();
					insertarPiezasSolicitud(solicitudCompra.getId(), pieza.getIdProdPieza(), piezas.getCantidad());
				}
			} else
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
	}

	public List<SolicitudCompraDTO> readAll() throws Exception {

		ArrayList<SolicitudCompraDTO> solicitudes = new ArrayList<SolicitudCompraDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_ReadAll, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {
					solicitudes.add(new SolicitudCompraDTO(resultSet.getInt("idSolcCompra"), new ProveedorDTO(resultSet.getInt("IdProveedor"), resultSet.getString("Nombre"), resultSet.getString("Contacto"), resultSet.getString("Cuit"), resultSet.getString("Telefono"), resultSet.getString("Mail"), null), new EstadoDTO(resultSet.getInt("IdSolcEstado"), resultSet.getString("Descripcion")),
							traerPiezasSolicitud(resultSet.getInt("idSolcCompra"))));
				}
			} else
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return solicitudes;
	}

	public ArrayList<PrecioPiezaDTO> traerPiezasSolicitud(int idSolcCompra) throws Exception {

		ArrayList<PrecioPiezaDTO> piezas = new ArrayList<PrecioPiezaDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_getPiezaProveedor, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			cs.setInt(1, idSolcCompra);
			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {

					PiezaDTO pieza = new PiezaDTO(resultSet.getInt("idProdPieza"), new MarcaDTO(resultSet.getInt("idProdMarca"), resultSet.getString("nombreMarca")), resultSet.getString("idUnico"), resultSet.getString("Descripcion"), resultSet.getFloat("Precio_venta"), resultSet.getInt("bajo_stock"));
					float precio = resultSet.getFloat("precio_compra");
					piezas.add(new PrecioPiezaDTO(pieza, (precio == 0) ? null : precio, resultSet.getInt("cantidad")));
				}
			} else
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return piezas;
	}

	public void procesarSolicitud(SolicitudCompraDTO solicitudCompra) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_altaPiezas);

			List<PrecioPiezaDTO> piezas = solicitudCompra.getPiezas();
			for (PrecioPiezaDTO itemDTO : piezas) {

				cs.setInt(1, itemDTO.getPieza().getIdProdPieza());
				cs.setInt(2, itemDTO.getCantidad());
				cs.registerOutParameter(3, java.sql.Types.INTEGER);
				cs.registerOutParameter(4, java.sql.Types.VARCHAR);
				cs.executeUpdate();

				int retCode = cs.getInt("retCode");
				String descErr = cs.getString("descErr");

				if (retCode != 0)
					throw (new Exception(descErr));
			}
			cambiarEstado(solicitudCompra, 3);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			conexion.cerrarConexion();
		}

	}

	public void cambiarEstado(SolicitudCompraDTO solicitudCompra, int idEstado) throws SQLException, Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_cambiarEstado);
			cs.setInt(1, solicitudCompra.getId());
			cs.setInt(2, idEstado);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode != 0)
				throw (new Exception(descErr));
		} catch (

		SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
	}

	public List<SolicitudCompraDTO> readProcesadasPorFecha(Calendar inicio, Calendar fin) throws Exception {

		ArrayList<SolicitudCompraDTO> solicitudes = new ArrayList<SolicitudCompraDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_ReadProcesadasPorFecha);

			cs.setInt(1, 3);
			cs.setDate(2, new Date(inicio.getTimeInMillis()));
			cs.setDate(3, new Date(fin.getTimeInMillis()));

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {
					Calendar fechaProcesada = getCalendar(resultSet.getDate("Fecha_Procesada"));

					solicitudes.add(new SolicitudCompraDTO(resultSet.getInt("idSolcCompra"), new ProveedorDTO(resultSet.getInt("IdProveedor"), resultSet.getString("Nombre"), resultSet.getString("Contacto"), resultSet.getString("Cuit"), resultSet.getString("Telefono"), resultSet.getString("Mail"), null), fechaProcesada,
							new EstadoDTO(resultSet.getInt("IdSolcEstado"), resultSet.getString("Descripcion")), traerPiezasSolicitud(resultSet.getInt("idSolcCompra"))));
				}
			} else
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return solicitudes;
	}

	public SolicitudCompraDTO traerSolicitudXid(int idSolcCompra) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_traerSolicitudXid);

			cs.setInt(1, idSolcCompra);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				resultSet.last();// Query devuelve un unico resultado en el cursor implicito. Me voy al final del resultset

				Calendar fechaProcesada = getCalendar(resultSet.getDate("Fecha_Procesada"));

				return (new SolicitudCompraDTO(resultSet.getInt("idSolcCompra"), new ProveedorDTO(resultSet.getInt("IdProveedor"), resultSet.getString("Nombre"), resultSet.getString("Contacto"), resultSet.getString("Cuit"), resultSet.getString("Telefono"), resultSet.getString("Mail"), null), fechaProcesada, new EstadoDTO(resultSet.getInt("IdSolcEstado"), resultSet.getString("Descripcion")),
						traerPiezasSolicitud(resultSet.getInt("idSolcCompra"))));

			}

			else
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally // Se ejecuta siempre
		{
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

}