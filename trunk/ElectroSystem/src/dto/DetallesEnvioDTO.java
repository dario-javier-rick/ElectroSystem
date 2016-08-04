package dto;

public class DetallesEnvioDTO {

	private OrdenDTO ot;
	private boolean entregado;

	public DetallesEnvioDTO(OrdenDTO ot, boolean entregado) {
		this.ot = ot;
		this.entregado = entregado;
	}

	public OrdenDTO getOt() {
		return ot;
	}

	public void setOt(OrdenDTO ot) {
		this.ot = ot;
	}

	public boolean isEntregado() {
		return entregado;
	}

	public void setEntregado(boolean entregado) {
		this.entregado = entregado;
	}

	@Override
	public String toString() {
		return this.ot.getIdOT() + "";
	}

}
