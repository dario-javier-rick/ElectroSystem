package dto;

public class RankElectrodomesticosDTO {
	private String marca;
	private String modelo;
	private String descripcion;
	private Double porcentaje;
	private int cantidad;

	public RankElectrodomesticosDTO(String marca, String modelo, int cantidad, String descripcion, Double porcentaje) {
		super();
		this.marca = marca;
		this.modelo = modelo;
		this.cantidad = cantidad;
		this.descripcion = descripcion;
		this.porcentaje = porcentaje;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

}
