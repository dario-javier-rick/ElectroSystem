package dto;

import java.util.Calendar;

public class AlertaDTO {
	private int idAlerta;
	private Calendar fechaCreada;
	private Calendar fechaBaja;
	private PiezaDTO pieza;
	private SolicitudCompraDTO pedidaEn;
	private String tipoAlerta;
	

	public AlertaDTO(int idAlerta, Calendar fechaCreada, String tipoAlerta, PiezaDTO pieza, 
			Calendar Fecha_Baja) {
		this.idAlerta = idAlerta;
		this.fechaCreada = fechaCreada;
		this.tipoAlerta = tipoAlerta;
		this.pieza = pieza;
		this.setFechaBaja(Fecha_Baja);
	}

	public int getIdAlerta() {
		return idAlerta;
	}

	public void setIdAlerta(int idAlerta) {
		this.idAlerta = idAlerta;
	}

	public Calendar getFechaCreada() {
		return fechaCreada;
	}

	public void setFechaCreada(Calendar fechaCreada) {
		this.fechaCreada = fechaCreada;
	}

	public PiezaDTO getPieza() {
		return pieza;
	}

	public void setPieza(PiezaDTO pieza) {
		this.pieza = pieza;
	}

	public SolicitudCompraDTO getPedidaEn() {
		return pedidaEn;
	}

	public void setPedidaEn(SolicitudCompraDTO pedidaEn) {
		this.pedidaEn = pedidaEn;
	}

	public String getTipoAlerta() {
		return tipoAlerta;
	}

	public void setTipoAlerta(String tipoAlerta) {
		this.tipoAlerta = tipoAlerta;
	}

	public Calendar getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Calendar fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
}
