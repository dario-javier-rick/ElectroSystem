package dto;

public class RankPiezasDTO {
	private String Pieza;
	private String Marca;
	private String Descripcion;
	private int Cantidad;
	private Double porcentaje;

	public RankPiezasDTO(String pieza, String marca, String descripcion, int cantidad, Double porcentaje) {
		super();
		this.Pieza = pieza;
		this.Marca = marca;
		this.Descripcion = descripcion;
		this.Cantidad = cantidad;
		this.porcentaje = porcentaje;
	}

	public String getPieza() {
		return Pieza;
	}

	public void setPieza(String pieza) {
		Pieza = pieza;
	}

	public String getMarca() {
		return Marca;
	}

	public void setMarca(String marca) {
		Marca = marca;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public int getCantidad() {
		return Cantidad;
	}

	public void setCantidad(int cantidad) {
		Cantidad = cantidad;
	}

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

}
