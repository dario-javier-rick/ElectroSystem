package persistencia.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import dto.AlertaDTO;
import dto.MarcaDTO;
import dto.PiezaDTO;
import persistencia.conexion.Conexion;

public class AlertaDAO {

	private final String sp_ReadAll = "call traerAlertas(?,?)";
	private final String sp_obtenerUltimaSCPendiente = "call obtenerUltimaSCPendiente(?,?,?,?)";

	private final Conexion conexion;

	public AlertaDAO() throws Exception {
		conexion = Conexion.getConexion();
	}

	public List<AlertaDTO> readAll() throws Exception {

		ArrayList<AlertaDTO> alertas = new ArrayList<AlertaDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_ReadAll, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {
					alertas.add(new AlertaDTO(resultSet.getInt("idAlerta"), getCalendar(resultSet.getDate("fecha_Alerta")), resultSet.getString("tipoAlerta"),
							new PiezaDTO(resultSet.getInt("idProdPieza"), new MarcaDTO(resultSet.getInt("IdProdMarca"), resultSet.getString("Nombre")), resultSet.getString("idUnico"), resultSet.getString("Descripcion"), (float) resultSet.getDouble("Precio_venta"), resultSet.getInt("bajo_stock"), resultSet.getInt("cantidad")), getCalendar(resultSet.getDate("Fecha_Baja"))));
				}
			} else
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return alertas;
	}

	public void obtenerUltimaSCPendiente(AlertaDTO alerta) throws Exception {

		// Voy a la base y buscar la ultima SC donde fue pedida la pieza
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_obtenerUltimaSCPendiente);

			cs.setInt(1, alerta.getPieza().getIdProdPieza());

			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);

			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			int idSolcCompra = cs.getInt("idSolcCompra");

			if (retCode == 0) {
				if (idSolcCompra != 0) {
					SolicitudCompraDAO daoSC = new SolicitudCompraDAO();
					alerta.setPedidaEn(daoSC.traerSolicitudXid(idSolcCompra));
				}
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
