package dto;

public class PrecioPiezaDTO {

	private PiezaDTO pieza;
	private Float precio;
	private Integer cantidad;

	public PrecioPiezaDTO(PiezaDTO pieza, Float precio, Integer cantidad) {
		super();
		this.pieza = pieza;
		this.precio = precio;
		this.cantidad = cantidad;
	}

	public PiezaDTO getPieza() {
		return pieza;
	}

	public void setPieza(PiezaDTO pieza) {
		this.pieza = pieza;
	}

	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrecioPiezaDTO other = (PrecioPiezaDTO) obj;
		if (pieza == null) {
			if (other.pieza != null)
				return false;
		} else if (!pieza.equals(other.pieza))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return pieza.toString();
	}

}
