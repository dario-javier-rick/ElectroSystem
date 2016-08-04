package dto;

public class PiezaPresupuestoDTO {

	private PiezaDTO pieza;
	private int cantidad;

	public PiezaPresupuestoDTO(PiezaDTO pieza, int cantidad) {
		this.pieza = pieza;
		this.cantidad = cantidad;
	}

	public PiezaDTO getPieza() {
		return pieza;
	}

	public void setPieza(PiezaDTO pieza) {
		this.pieza = pieza;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
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
		PiezaPresupuestoDTO other = (PiezaPresupuestoDTO) obj;
		if (pieza == null) {
			if (other.pieza != null)
				return false;
		} else if (!pieza.equals(other.pieza))
			return false;
		return true;
	}

}
