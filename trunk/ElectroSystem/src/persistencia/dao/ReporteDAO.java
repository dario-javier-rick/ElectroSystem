package persistencia.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import dto.FleteroDTO;
import dto.HojaDeRutaDTO;
import dto.RankElectrodomesticosDTO;
import dto.RankIncumplidoresDTO;
//import dto.RankIncumplidoresDTO;
import dto.RankPiezasDTO;
import persistencia.conexion.Conexion;

public class ReporteDAO {

	private final String sp_RankElectrodomesticos = "call RepElectrodomesticos(?,?,?,?,?)";
	private final String sp_RankIncumplidores = "call RepProveedoresIncumplidores(?,?,?,?)";
	private final String sp_RankPiezas = "call RepPiezas(?,?,?,?,?)";
	private final String sp_HojaDeRutaPorFletero = "call ReporteHojaDeRutaPorFletero (?, ?, ?)";

	private final Conexion conexion;

	public ReporteDAO() throws Exception {
		conexion = Conexion.getConexion();
	}
	
	public List<RankElectrodomesticosDTO> getRankElectrodomesticos(int porcentaje, Calendar ini, Calendar fin) throws Exception {

		ArrayList<RankElectrodomesticosDTO> rank = new ArrayList<RankElectrodomesticosDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_RankElectrodomesticos, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			cs.setInt(1, porcentaje);

			Date date = new Date(ini.getTimeInMillis());
			cs.setDate(2, date);
			Date date2 = new Date(fin.getTimeInMillis());
			cs.setDate(3, date2);
			cs.registerOutParameter(4, java.sql.Types.INTEGER);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {
					rank.add(new RankElectrodomesticosDTO(resultSet.getString("Marca"), resultSet.getString("Modelo"), resultSet.getInt("Cantidad"), resultSet.getString("Descripcion"), resultSet.getDouble("Porcentaje")));
				}
			} else
				throw new Exception(descErr);

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return rank;
	}
	
	public List<RankIncumplidoresDTO> getRankIncumplidores(Calendar ini, Calendar fin) throws Exception {

		ArrayList<RankIncumplidoresDTO> rank = new ArrayList<RankIncumplidoresDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_RankIncumplidores, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			Date date = new Date(ini.getTimeInMillis());
			cs.setDate(1, date);
			Date date2 = new Date(fin.getTimeInMillis());
			cs.setDate(2, date2);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {
					rank.add(new RankIncumplidoresDTO(resultSet.getString("Nombre"), resultSet.getString("Cuit"), resultSet.getInt("Cantidad")));
				}
			} else
				throw new Exception(descErr);

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return rank;
	}

	public List<RankPiezasDTO> getRankPiezas(int porcentaje, Calendar fechaDesde, Calendar fechaHasta) throws Exception {

		ArrayList<RankPiezasDTO> rank = new ArrayList<RankPiezasDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_RankPiezas, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			cs.setInt(1, porcentaje);
			Date fechaDesdeDate = new Date(fechaDesde.getTimeInMillis());
			cs.setDate(2, fechaDesdeDate);
			Date fechaHastaDate = new Date(fechaHasta.getTimeInMillis());
			cs.setDate(3, fechaHastaDate);
			cs.registerOutParameter(4, java.sql.Types.INTEGER);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {
					rank.add(new RankPiezasDTO(resultSet.getString("Pieza"), resultSet.getString("Marca"), resultSet.getString("Descripcion"), resultSet.getInt("Cantidad"), resultSet.getDouble("Porcentaje")));
				}
			} else
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return rank;
	}

	public List<HojaDeRutaDTO> getHojaDeRuta(FleteroDTO f) throws Exception {

		LinkedList<HojaDeRutaDTO> hojas = new LinkedList<HojaDeRutaDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_HojaDeRutaPorFletero);

			cs.setInt(1, f.getIdFletero());
			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {

					hojas.add(new HojaDeRutaDTO(
							resultSet.getString("ApellidoCliente"), 
							resultSet.getString("NombreCliente"), 
							resultSet.getString("Electrodomestico"), 
							resultSet.getString("IdOT"), 
							resultSet.getString("DescripcionOT"), 
							resultSet.getString("Domicilio"), 
							resultSet.getString("ApellidoFletero"), 
							resultSet.getString("NombreFletero"), 
							resultSet.getString("FechaEntrega"),
							resultSet.getString("Costo"),
							resultSet.getString("Localidad"),
							resultSet.getString("CP"),
							resultSet.getString("Provincia"),
							resultSet.getString("Telefono")
							));

				}
			} else
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;

		} finally {
			conexion.cerrarConexion();

		}
		return hojas;
	}
}
