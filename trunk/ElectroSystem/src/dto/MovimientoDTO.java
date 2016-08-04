package dto;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class MovimientoDTO {

	private List<OrdenDTO> ingresos;
	private List<SolicitudCompraDTO> egresos;

	private double totalIngresos;
	private double totalEgresos;
	private double saldo;
	private Calendar ini, fin;

	public MovimientoDTO(Calendar ini, Calendar fin) {
		this.ingresos = new LinkedList<OrdenDTO>();
		this.egresos = new LinkedList<SolicitudCompraDTO>();
		this.totalEgresos = 0;
		this.totalIngresos = 0;
		this.ini = ini;
		this.fin = fin;
	}

	public MovimientoDTO(List<OrdenDTO> ingresos, List<SolicitudCompraDTO> egresos) {
		this.ingresos = ingresos;
		this.egresos = egresos;
	}

	public List<OrdenDTO> getIngresos() {
		return ingresos;
	}

	public void setIngresos(List<OrdenDTO> ingresos) {
		this.ingresos = ingresos;
	}

	public List<SolicitudCompraDTO> getEgresos() {
		return egresos;
	}

	public void setEgresos(List<SolicitudCompraDTO> egresos) {
		this.egresos = egresos;
	}

	public void agregarOT(OrdenDTO ot) {
		this.ingresos.add(ot);
	}

	public void agregarSC(SolicitudCompraDTO sc) {
		this.egresos.add(sc);
	}

	public JRDataSource getingresosDS() {
		return new JRBeanCollectionDataSource(ingresos);
	}

	public JRDataSource getegresosDS() {
		return new JRBeanCollectionDataSource(egresos);
	}

	public double getTotalIngresos() {
		for (int i = 0; i < ingresos.size(); i++) {
			this.totalIngresos += ingresos.get(i).getTotal();
		}
		return this.totalIngresos;
	}

	public double getTotalEgresos() {
		for (int i = 0; i < egresos.size(); i++) {
			this.totalEgresos += egresos.get(i).getTotal();
		}
		return this.totalEgresos;
	}

	public double getSaldo() {
		saldo = totalIngresos - totalEgresos;
		return saldo;
	}

	public Calendar getIni() {
		return ini;
	}

	public void setIni(Calendar ini) {
		this.ini = ini;
	}

	public Calendar getFin() {
		return fin;
	}

	public void setFin(Calendar fin) {
		this.fin = fin;
	}
	
	
}
