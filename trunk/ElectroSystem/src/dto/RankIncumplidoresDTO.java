package dto;

public class RankIncumplidoresDTO {
	private String Nombre;
	private String CUIT;
	private int Cantidad;
	
	public RankIncumplidoresDTO(String nombre, String cUIT, int cantidad) {
		super();
		Nombre = nombre;
		CUIT = cUIT;
		Cantidad = cantidad;
	}

	public String getNombre() {
		return Nombre;
	}

	public String getCUIT() {
		return CUIT;
	}

	public int getCantidad() {
		return Cantidad;
	}
	
}
