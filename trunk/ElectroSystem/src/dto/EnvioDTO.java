package dto;

import java.util.Calendar;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class EnvioDTO {

	private int id;
	private FleteroDTO fletero;
	private Calendar fechaEnvio;
	private List<DetallesEnvioDTO> detalles;

	public EnvioDTO() {

	}

	public EnvioDTO(int id, FleteroDTO fletero, Calendar fechaEnvio, List<DetallesEnvioDTO> detalles) {
		this.id = id;
		this.fletero = fletero;
		this.fechaEnvio = fechaEnvio;
		this.detalles = detalles;
	}

	public FleteroDTO getFletero() {
		return fletero;
	}

	public void setFletero(FleteroDTO fletero) {
		this.fletero = fletero;
	}

	public Calendar getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Calendar fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public List<DetallesEnvioDTO> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetallesEnvioDTO> detalles) {
		this.detalles = detalles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void agregarDetalle(DetallesEnvioDTO detalle) {
		this.detalles.add(detalle);
	}

	public void eliminarDetalle(DetallesEnvioDTO detalle) {
		this.detalles.remove(detalle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnvioDTO other = (EnvioDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.id + "";
	}

	public JRDataSource getDetallesDS() {
		return new JRBeanCollectionDataSource(detalles);
	}
}