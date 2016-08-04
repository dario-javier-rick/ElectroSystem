package dto;

public class LocalidadDTO {

	private int codigoPostal;
	private String nombre;
	private ZonaDTO zonaDeEnvio;
	private String provincia;

	public LocalidadDTO(int codigoPostal, String nombre, ZonaDTO zonaDeEnvio, String provincia) {
		this.codigoPostal = codigoPostal;
		this.nombre = nombre;
		this.zonaDeEnvio = zonaDeEnvio;
		this.setProvincia(provincia);
	}

	public int getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ZonaDTO getZonaDeEnvio() {
		return zonaDeEnvio;
	}

	public void setZonaDeEnvio(ZonaDTO zonaDeEnvio) {
		this.zonaDeEnvio = zonaDeEnvio;
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

}
