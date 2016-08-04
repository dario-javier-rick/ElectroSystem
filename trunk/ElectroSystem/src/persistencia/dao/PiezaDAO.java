package persistencia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.MarcaDTO;
import dto.PiezaDTO;
import dto.PrecioPiezaDTO;
import persistencia.conexion.Conexion;

public class PiezaDAO {

	private final String sp_ReadAll = "call traerPiezas(?,?)";
	private final String sp_Insert = "call insertarPieza(?,?,?,?,?,?,?,?)";
	private final String sp_Delete = "call eliminarPieza(?,?,?,?)";
	private final String sp_Modify = "call modificarPieza(?,?,?,?,?,?,?,?)";
	private final String sp_StateChange = "call cambiarEstadoDePieza(?,?,?,?,?)";
	private final String sp_preciosProveedor = "call getPreciosProveedor(?,?,?,?)";
	private final String subirPiezaAlStock = "call altaPiezaStockFisico(?,?,?,?)";
	private final String contarDisponibles = "select contarDisponibles(?) as 'Cantidad'"; 
	
	private final String reestablecerPiezaDeOt = "UPDATE prod_piezas_stock SET idProdEstado = 1 WHERE idProdStock = ?";

	private final Conexion conexion;

	public PiezaDAO() throws Exception {
		conexion = Conexion.getConexion();
	}

	public ArrayList<PiezaDTO> readAll() throws Exception {

		ArrayList<PiezaDTO> piezas = new ArrayList<PiezaDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_ReadAll);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {
					PiezaDTO pieza = new PiezaDTO(resultSet.getInt("idProdPieza"),
							new MarcaDTO(resultSet.getInt("idProdMarca"), resultSet.getString("Nombre")),
							resultSet.getString("idUnico"), resultSet.getString("Descripcion"),
							resultSet.getFloat("Precio_venta"), resultSet.getInt("bajo_stock"),
							resultSet.getInt("cantidad"));

					piezas.add(pieza);
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

	public void insert(PiezaDTO pieza) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Insert);

			cs.setInt(1, pieza.getIdProdPieza());
			cs.setInt(2, pieza.getMarca().getIdMarca());
			cs.setString(3, pieza.getIdUnico());
			cs.setString(4, pieza.getDescripcion());
			cs.setDouble(5, pieza.getPrecio_venta());
			cs.setInt(6, pieza.getBajo_stock());
			cs.registerOutParameter(7, java.sql.Types.INTEGER);
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);

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

	public void delete(PiezaDTO pieza, int idPersona) throws Exception {
		try {

			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Delete);

			cs.setInt(1, pieza.getIdProdPieza());
			cs.setInt(2, idPersona);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);

			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode != 0)
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}

	}

	public void update(PiezaDTO pieza) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Modify);

			cs.setInt(1, pieza.getIdProdPieza());
			cs.setInt(2, pieza.getMarca().getIdMarca());
			cs.setString(3, pieza.getIdUnico());
			cs.setString(4, pieza.getDescripcion());
			cs.setDouble(5, pieza.getPrecio_venta());
			cs.setInt(6, pieza.getBajo_stock());
			cs.registerOutParameter(7, java.sql.Types.INTEGER);
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);

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

	public ArrayList<PrecioPiezaDTO> obtenerPrecioCompraItems(int idProveedor, int idMarca) throws Exception {

		ArrayList<PrecioPiezaDTO> piezas = new ArrayList<PrecioPiezaDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_preciosProveedor);

			cs.setInt(1, idProveedor);
			cs.setInt(2, idMarca);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {

					PiezaDTO pieza = new PiezaDTO(resultSet.getInt("idProdPieza"), null, resultSet.getString("idUnico"),
							resultSet.getString("Descripcion"), resultSet.getFloat("Precio_venta"),
							resultSet.getInt("bajo_stock"));
					float precio = resultSet.getFloat("precio_compra");
					piezas.add(new PrecioPiezaDTO(pieza, (precio == 0) ? null : precio, null));
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

	public void altaStock(PiezaDTO pieza, int cantidad) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(subirPiezaAlStock);

			cs.setInt(1, pieza.getIdProdPieza());
			cs.setInt(2, cantidad);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);

			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode != 0) {
				throw (new Exception(descErr));
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
	}

	public void bajaStock(PiezaDTO pieza, int cantidad, int estado) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_StateChange);
			cs.setInt(1, pieza.getIdProdPieza());
			cs.setInt(2, 1); // De disponible
			cs.setInt(3, estado); // a perdida.
			cs.registerOutParameter(4, java.sql.Types.INTEGER);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			while (cantidad != 0) {

				cs.executeQuery();
				int retCode = cs.getInt("retCode");
				String descErr = cs.getString("descErr");

				if (retCode == 0) {

				} else
					throw (new Exception(descErr));

				cantidad--;
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
	}

	public int dameStock(PiezaDTO pieza) throws Exception {
		try {		
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(contarDisponibles);
			cs.setInt(1, pieza.getIdProdPieza());
			ResultSet resultSet = cs.executeQuery();
			resultSet.last();
			
			return resultSet.getInt("Cantidad");
		} catch (Exception e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
	}

	public void reestablecerPiezaDeOT(PiezaDTO pieza) throws SQLException {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(reestablecerPiezaDeOt);
			statement.setInt(1, pieza.getIdProdPieza());
			statement.executeUpdate();

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
	}
}
