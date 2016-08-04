package dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HojaDeRutaDTO {

	private String apellidoCliente, nombreCliente, electrodomestico, idot, descripcionOT, direccion, apellidoFletero,
			nombreFletero, fechaEntrega, costo, localidad, cp, provincia, telefono;

	public HojaDeRutaDTO(String apellidoCliente, String nombreCliente, String electrodomestico, String idot,
			String descripcionOT, String direccion, String apellidoFletero, String nombreFletero, String fechaEntrega,
			String costo, String localidad, String cp, String provincia, String telefono) {
		this.apellidoCliente = apellidoCliente;
		this.nombreCliente = nombreCliente;
		this.electrodomestico = electrodomestico;
		this.idot = idot;
		this.descripcionOT = descripcionOT;
		this.direccion = direccion;
		this.apellidoFletero = apellidoFletero;
		this.nombreFletero = nombreFletero;
		this.fechaEntrega = fechaEntrega;
		this.costo = costo;
		this.localidad = localidad;
		this.cp = cp;
		this.provincia = provincia;
		this.telefono = telefono;
	}

	public String getApellidoCliente() {
		return apellidoCliente;
	}

	public void setApellidoCliente(String apellidoCliente) {
		this.apellidoCliente = apellidoCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getElectrodomestico() {
		return electrodomestico;
	}

	public void setElectrodomestico(String electrodomestico) {
		this.electrodomestico = electrodomestico;
	}

	public String getIdot() {
		return idot;
	}

	public void setIdot(String idot) {
		this.idot = idot;
	}

	public String getDescripcionOT() {
		return descripcionOT;
	}

	public void setDescripcionOT(String descripcionOT) {
		this.descripcionOT = descripcionOT;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getApellidoFletero() {
		return apellidoFletero;
	}

	public void setApellidoFletero(String apellidoFletero) {
		this.apellidoFletero = apellidoFletero;
	}

	public String getNombreFletero() {
		return nombreFletero;
	}

	public void setNombreFletero(String nombreFletero) {
		this.nombreFletero = nombreFletero;
	}

	public String getFechaEntrega() throws ParseException {
		return cambiarFormatoFecha(fechaEntrega);
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getCosto() {
		return costo;
	}

	public void setCosto(String costo) {
		this.costo = costo;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String cambiarFormatoFecha (String fecha) throws ParseException {
		
		final String oldFormat = "yyyy-MM-dd";
		final String newFormat = "dd/MM/yyyy";

		String oldDate = fecha;
		String newDate;

		SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
		Date d = sdf.parse(oldDate);
		sdf.applyPattern(newFormat);
		newDate = sdf.format(d);
		
		return newDate;
	}

}
