package persistencia.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ElectrodomesticoDTO;
import dto.MarcaDTO;
import persistencia.conexion.Conexion;

public class ElectrodomesticoDAO {

	private final String sp_Insertar = "call insertarElectrodomestico(?,?,?,?,?,?)";
	private final String sp_Eliminar = "call eliminarElectrodomestico(?,?,?,?)";
	private final String sp_ReadAll = "call traerElectrodomesticos(?,?)";
	private final String sp_Modificar = "call modificarElectrodomestico(?,?,?,?,?)";
	private final Conexion conexion;

	public ElectrodomesticoDAO() throws Exception {
		conexion = Conexion.getConexion();
	}

	public void insert(ElectrodomesticoDTO electrodomestico) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Insertar);

			cs.setInt(1, electrodomestico.getMarca().getIdMarca());
			cs.setString(2, electrodomestico.getModelo());
			cs.setString(3, electrodomestico.getDescripcion());
			cs.registerOutParameter(4, java.sql.Types.INTEGER);
			cs.registerOutParameter(5, java.sql.Types.INTEGER);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);

			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0)
				electrodomestico.setIdElectro(cs.getInt("retIdElectro"));

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

	public void delete(ElectrodomesticoDTO electrodomestico, int IdPersonal) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Eliminar);

			cs.setInt(1, electrodomestico.getIdElectro());
			cs.setInt(2, IdPersonal);

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

	public List<ElectrodomesticoDTO> readAll() throws Exception {

		ArrayList<ElectrodomesticoDTO> electrodomesticos = new ArrayList<ElectrodomesticoDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_ReadAll, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {
					electrodomesticos.add(new ElectrodomesticoDTO(resultSet.getInt("idElectro"), new MarcaDTO(resultSet.getInt("idProdMarca"), resultSet.getString("Nombre")), resultSet.getString("Modelo"), resultSet.getString("descripcion")));
				}
			} else
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return electrodomesticos;
	}

	public void update(ElectrodomesticoDTO electrodomestico) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Modificar);

			cs.setInt(1, electrodomestico.getIdElectro());
			cs.setString(2, electrodomestico.getModelo());
			cs.setString(3, electrodomestico.getDescripcion());
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

}
