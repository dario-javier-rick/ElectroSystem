package persistencia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ClienteDTO;
import dto.LocalidadDTO;
import dto.ZonaDTO;
import persistencia.conexion.Conexion;

public class ClienteDAO {

	private final String sp_Insertar = "call insertarCliente(?,?,?,?,?,?,?,?,?)";
	private final String sp_Eliminar = "call eliminarCliente(?,?,?,?)";
	private final String sp_ReadAll = "call traerClientes(?,?)";
	private final String sp_Modificar = "call modificarCliente(?,?,?,?,?,?,?,?,?)";

	private final String readLocalidadDeCliente = "SELECT * FROM cli_clientes cli, localidades l, ot_zonas z " + "WHERE cli.Codigo_postal = l.codigoPostal " + "AND l.zonaDeEnvio = z.idZonaPosible AND " + "cli.IdCliente = ?";

	private final Conexion conexion;

	public ClienteDAO() throws Exception {
		conexion = Conexion.getConexion();
	}

	public void insert(ClienteDTO cliente) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Insertar);

			cs.setString(1, cliente.getNombre());
			cs.setString(2, cliente.getApellido());
			cs.setString(3, cliente.getDireccion());
			cs.setInt(4, cliente.getLocalidad().getCodigoPostal());
			cs.setString(5, cliente.getTelefono());
			cs.setString(6, cliente.geteMail());

			cs.registerOutParameter(7, java.sql.Types.INTEGER);
			cs.registerOutParameter(8, java.sql.Types.INTEGER);
			cs.registerOutParameter(9, java.sql.Types.VARCHAR);

			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0)
				cliente.setIdCliente(cs.getInt("retIdCliente"));

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

	public void delete(ClienteDTO cliente, int IdPersonal) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Eliminar);

			cs.setInt(1, cliente.getIdCliente());
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

	public List<ClienteDTO> readAll() throws Exception {

		ArrayList<ClienteDTO> clientes = new ArrayList<ClienteDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_ReadAll, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {
					LocalidadDTO localidad = null;
					ClienteDTO cliente = new ClienteDTO(resultSet.getInt("idCliente"), resultSet.getString("Nombre"), resultSet.getString("Apellido"), "", localidad, resultSet.getString("Telefono"), resultSet.getString("Email"));

					if (resultSet.getString("Codigo_postal") != null) {
						cliente.setDireccion(resultSet.getString("cli.Direccion"));
						cliente.setLocalidad(dameLocalidad(cliente));
					}
					clientes.add(cliente);
				}
			} else
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return clientes;
	}

	public void update(ClienteDTO cliente) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Modificar);

			cs.setInt(1, cliente.getIdCliente());
			cs.setString(2, cliente.getNombre());
			cs.setString(3, cliente.getApellido());
			if (cliente.getLocalidad() != null) {
				cs.setString(4, cliente.getDireccion());
				cs.setInt(5, cliente.getLocalidad().getCodigoPostal());
			}
			cs.setString(6, cliente.getTelefono());
			cs.setString(7, cliente.geteMail());
			cs.registerOutParameter(8, java.sql.Types.INTEGER);
			cs.registerOutParameter(9, java.sql.Types.VARCHAR);

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

}