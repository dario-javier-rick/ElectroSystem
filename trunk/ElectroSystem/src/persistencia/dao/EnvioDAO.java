package persistencia.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dto.ClienteDTO;
import dto.DetallesEnvioDTO;
import dto.ElectrodomesticoDTO;
import dto.EnvioDTO;
import dto.EstadoDTO;
import dto.FleteroDTO;
import dto.LocalidadDTO;
import dto.MarcaDTO;
import dto.OrdenDTO;
import dto.VehiculoDTO;
import dto.ZonaDTO;
import persistencia.conexion.Conexion;

public class EnvioDAO {

	private static final String insertEnvio = "insert into env_envios (idFletero, fechaEnvio) values (?, ?)";
	private static final String insertDetalle = "insert into env_envios_detalle (idEnvio, idOT, entregado) values (?, ?, ?)";

	private static final String readAll = "select * "
			+ "from env_envios e, env_fleteros f, env_vehiculos v, env_estados_vehiculos estado "
			+ "where e.idFletero = f.idFletero "
			+ "and f.IdVehiculo = v.Patente "
			+ "and estado.idEstado = v.Estado";	
	
	private static final String readAllDetalles = "select * "
			+ "from ot_ordenes_trabajo ot, env_envios_detalle det, cli_clientes cli, elec_electrodomesticos elec, "
			+ "localidades l, prod_marcas marca, ot_zonas zona "
			+ "where ot.idOT = det.idOt "
			+ "and cli.idCliente = ot.idCliente "
			+ "and elec.idElectro = ot.idElectro "
			+ "and l.codigoPostal = cli.codigo_postal "
			+ "and marca.idProdMarca = elec.idProdMarca "
			+ "and zona.idZonaPosible = l.zonaDeEnvio "
			+ "and det.idEnvio = ? ";
	
	private static final String deleteOT = "delete from env_envios_detalle where idOT = ?";
	private static final String deleteEnvio = "delete from env_envios where idEnvio = ?";
		
	private static final String readByFletero = "select * "
			+ "from ot_ordenes_trabajo ot, env_fleteros f, env_envios e, env_envios_detalle det, "
			+ "env_vehiculos v, env_estados_vehiculos estado "
			+ "where ot.idOT = det.idOT "
			+ "and e.idFletero = f.idFletero "
			+ "and f.IdVehiculo = v.Patente "
			+ "and estado.idEstado = v.Estado "
			+ "and f.idFletero = ?";
	
	private static final String updateEntregado = "update env_envios_detalle set entregado = 1 where idOT = ? and idEnvio = ?";
	
	private static final String readAllEntregados = "select * "
			+ "from ot_ordenes_trabajo ot, env_envios_detalle det, cli_clientes cli, elec_electrodomesticos elec, "
			+ "localidades l, prod_marcas marca, ot_zonas zona "
			+ "where ot.idOT = det.idOt "
			+ "and cli.idCliente = ot.idCliente "
			+ "and elec.idElectro = ot.idElectro "
			+ "and l.codigoPostal = cli.codigo_postal "
			+ "and marca.idProdMarca = elec.idProdMarca "
			+ "and zona.idZonaPosible = l.zonaDeEnvio "
			+ "and det.entregado = 1 "
			;
	
	private final Conexion conexion;

	public EnvioDAO() throws Exception {
		conexion = Conexion.getConexion();
		}
	
	public void insertEnvio(EnvioDTO envio) {

		PreparedStatement statement;

		try {
			statement = conexion.getSQLConexion().prepareStatement(insertEnvio);
			
			Date fechaEnvio = new Date(envio.getFechaEnvio().getTimeInMillis());

			statement.setInt(1, envio.getFletero().getIdFletero());
			statement.setDate(2, fechaEnvio);

			statement.executeUpdate();
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertDetalle(DetallesEnvioDTO detalles, EnvioDTO envio) {

		PreparedStatement statement;

		try {
			statement = conexion.getSQLConexion().prepareStatement(insertDetalle);

			statement.setInt(1, envio.getId());
			statement.setInt(2, detalles.getOt().getIdOT());
			statement.setBoolean(3, detalles.isEntregado());

			statement.executeUpdate();
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<EnvioDTO> readAll() {

		PreparedStatement statement;
		ResultSet resultSet;

		List<EnvioDTO> envios = new ArrayList<EnvioDTO>();

		try {
			statement = conexion.getSQLConexion().prepareStatement(readAll);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
												
				Calendar fechaVencimientoVTV = getCalendar(resultSet.getDate("v.FechaVencimientoVTV"));
				Calendar fechaVencimientoOblea = getCalendar(resultSet.getDate("v.FechaVencimientoOblea"));
				Calendar vencimientoRegistro = getCalendar(resultSet.getDate("f.fechaVencimientoRegistro"));
				Calendar fechaEnvio = getCalendar(resultSet.getDate("e.fechaEnvio"));

				EstadoDTO estado = new EstadoDTO(resultSet.getInt("estado.idEstado"), 
						resultSet.getString("estado.nombreEstado"));
				
				VehiculoDTO vehiculo = new VehiculoDTO(
						resultSet.getString("v.Patente"), 
						resultSet.getString("v.Marca"), 
						resultSet.getString("v.Modelo"), 
						resultSet.getInt("v.CapacidadCarga"),
						fechaVencimientoVTV, 
						resultSet.getBoolean("v.Oblea"), 
						fechaVencimientoOblea, 
						estado);
								
				FleteroDTO fletero = new FleteroDTO(
						resultSet.getInt("f.idFletero"), 
						resultSet.getString("f.Nombre"), 
						resultSet.getString("f.Apellido"),
						resultSet.getString("f.Celular"), 
						resultSet.getString("f.Celular"), 
						vencimientoRegistro, 
						vehiculo);
								
				envios.add(new EnvioDTO(resultSet.getInt("e.idEnvio"), 
						fletero,
						fechaEnvio,
						null));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally

		{
			conexion.cerrarConexion();
		}
		return envios;
	}

	public List<DetallesEnvioDTO> readAllDetalles (EnvioDTO envio) {

		PreparedStatement statement;
		ResultSet resultSet;
 
		List<DetallesEnvioDTO> detalles = new ArrayList<DetallesEnvioDTO>();

		try {
			statement = conexion.getSQLConexion().prepareStatement(readAllDetalles);
			statement.setInt(1, envio.getId());
			
			resultSet = statement.executeQuery();
			

			while (resultSet.next()) {
				
				ZonaDTO zonaDeEnvio = new ZonaDTO(resultSet.getInt("zona.idZonaPosible"), 
						resultSet.getString("zona.nombre"), resultSet.getDouble("zona.precio"));
				
				LocalidadDTO localidad = new LocalidadDTO(
						resultSet.getInt("l.codigoPostal"), 
						resultSet.getString("l.nombre"), zonaDeEnvio, 
						resultSet.getString("l.provincia"));
				
				ClienteDTO cliente = new ClienteDTO(resultSet.getInt("cli.idCliente"), 
						resultSet.getString("cli.nombre"), 
						resultSet.getString("cli.apellido"), 
						resultSet.getString("cli.direccion"), 
						localidad, 
						resultSet.getString("cli.telefono"), 
						resultSet.getString("cli.email"));
				
						MarcaDTO marca = new MarcaDTO(
								resultSet.getInt("marca.idProdMarca"), 
								resultSet.getString("marca.Nombre"));
						
				ElectrodomesticoDTO electrodomestico = new ElectrodomesticoDTO(
						resultSet.getInt("ot.idElectro"), 
						marca, 
						resultSet.getString("elec.modelo"), 
						resultSet.getString("elec.descripcion"));
				
				detalles.add(new DetallesEnvioDTO(
						new OrdenDTO(resultSet.getInt("ot.idOT"), 
								cliente, 
								electrodomestico, 
								resultSet.getString("ot.descripcion"), 
								null,
								resultSet.getBoolean("ot.esDelivery")), 
						resultSet.getBoolean("det.entregado")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally

		{
			conexion.cerrarConexion();
		}
		return detalles;
	}
	
	public void quitarOTenvio(OrdenDTO orden) {
		// elimina a una ot de un envio

		PreparedStatement stm;

		try {
			stm = conexion.getSQLConexion().prepareStatement(deleteOT);

			stm.setInt(1, orden.getIdOT());

			stm.executeUpdate();
			stm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteEnvio(EnvioDTO envio) {

		PreparedStatement stm;

		try {
			stm = conexion.getSQLConexion().prepareStatement(deleteEnvio);

			stm.setInt(1, envio.getId());

			stm.executeUpdate();
			stm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<EnvioDTO> readByFletero(FleteroDTO fletero) {

		PreparedStatement statement;
		ResultSet resultSet;

		List<EnvioDTO> envios = new ArrayList<EnvioDTO>();

		try {
			statement = conexion.getSQLConexion().prepareStatement(readByFletero);

			statement.setInt(1, fletero.getIdFletero());

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				
				Calendar fechaVencimientoVTV = getCalendar(resultSet.getDate("v.FechaVencimientoVTV"));
				Calendar fechaVencimientoOblea = getCalendar(resultSet.getDate("v.FechaVencimientoOblea"));
				Calendar vencimientoRegistro = getCalendar(resultSet.getDate("f.fechaVencimientoRegistro"));
				Calendar fechaEnvio = getCalendar(resultSet.getDate("e.fechaEnvio"));
				
				EstadoDTO estado = new EstadoDTO(resultSet.getInt("estado.idEstado"), 
						resultSet.getString("estado.nombreEstado"));
				
				VehiculoDTO vehiculo = new VehiculoDTO(
						resultSet.getString("v.Patente"), 
						resultSet.getString("v.Marca"), 
						resultSet.getString("v.Modelo"), 
						resultSet.getInt("v.CapacidadCarga"),
						fechaVencimientoVTV, 
						resultSet.getBoolean("v.Oblea"), 
						fechaVencimientoOblea, 
						estado);
								
				FleteroDTO f = new FleteroDTO(
						resultSet.getInt("f.idFletero"), 
						resultSet.getString("f.Nombre"), 
						resultSet.getString("f.Apellido"),
						resultSet.getString("f.Celular"), 
						resultSet.getString("f.Celular"), 
						vencimientoRegistro, 
						vehiculo);
								
				envios.add(new EnvioDTO(resultSet.getInt("e.idEnvio"), 
						f, fechaEnvio, null));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally

		{
			conexion.cerrarConexion();
		}
		return envios;
	}
	
	public void updateEntregado (OrdenDTO orden, EnvioDTO envio) {
		
		PreparedStatement statement;

		try {
			statement = conexion.getSQLConexion().prepareStatement(updateEntregado);
			
			statement.setInt(1, orden.getIdOT());
			statement.setInt(2, envio.getId());

			statement.executeUpdate();
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<DetallesEnvioDTO> readAllEntregados () {

		PreparedStatement statement;
		ResultSet resultSet;
 
		List<DetallesEnvioDTO> detalles = new ArrayList<DetallesEnvioDTO>();

		try {
			statement = conexion.getSQLConexion().prepareStatement(readAllEntregados);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				
				ZonaDTO zonaDeEnvio = new ZonaDTO(resultSet.getInt("zona.idZonaPosible"), 
						resultSet.getString("zona.nombre"), resultSet.getDouble("zona.precio"));
				
				LocalidadDTO localidad = new LocalidadDTO(
						resultSet.getInt("l.codigoPostal"), 
						resultSet.getString("l.nombre"), zonaDeEnvio,
						resultSet.getString("l.provincia"));
				
				ClienteDTO cliente = new ClienteDTO(resultSet.getInt("cli.idCliente"), 
						resultSet.getString("cli.nombre"), 
						resultSet.getString("cli.apellido"), 
						resultSet.getString("cli.direccion"), 
						localidad, 
						resultSet.getString("cli.telefono"), 
						resultSet.getString("cli.email"));
				
						MarcaDTO marca = new MarcaDTO(
								resultSet.getInt("marca.idProdMarca"), 
								resultSet.getString("marca.Nombre"));
						
				ElectrodomesticoDTO electrodomestico = new ElectrodomesticoDTO(
						resultSet.getInt("ot.idElectro"), 
						marca, 
						resultSet.getString("elec.modelo"), 
						resultSet.getString("elec.descripcion"));
				
				detalles.add(new DetallesEnvioDTO(
						new OrdenDTO(resultSet.getInt("ot.idOT"), 
								cliente, 
								electrodomestico, 
								resultSet.getString("ot.descripcion"), 
								null,
								resultSet.getBoolean("ot.esDelivery")), 
						resultSet.getBoolean("det.entregado")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally

		{
			conexion.cerrarConexion();
		}
		return detalles;
	}
	
	private Calendar getCalendar(Date sqlDate) throws SQLException {
		if (sqlDate == null)
			return null;

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(sqlDate.getTime());
		return calendar;
	} 

}
