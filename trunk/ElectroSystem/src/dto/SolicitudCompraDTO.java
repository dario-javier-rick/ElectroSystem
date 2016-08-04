package dto;

import java.util.Calendar;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class SolicitudCompraDTO {

	private int id;
	private ProveedorDTO proveedor;
	private Calendar fechaProcesada;
	private EstadoDTO estado;
	private List<PrecioPiezaDTO> piezas;
	private double total;		//Para el reporte

	public SolicitudCompraDTO(int id, ProveedorDTO proveedor, EstadoDTO estado, List<PrecioPiezaDTO> piezas) {
		super();
		this.id = id;
		this.proveedor = proveedor;
		this.estado = estado;
		this.piezas = piezas;
	}
	
	public SolicitudCompraDTO(int id, ProveedorDTO proveedor, Calendar fechaProcesada, EstadoDTO estado, List<PrecioPiezaDTO> piezas) {
		super();
		this.id = id;
		this.proveedor = proveedor;
		this.fechaProcesada = fechaProcesada;
		this.estado = estado;
		this.piezas = piezas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProveedorDTO getProveedor() {
		return proveedor;
	}

	public void setProveedor(ProveedorDTO proveedor) {
		this.proveedor = proveedor;
	}

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}

	public List<PrecioPiezaDTO> getPiezas() {
		return piezas;
	}

	public void setPiezas(List<PrecioPiezaDTO> piezas) {
		this.piezas = piezas;
	}
	
	public void setPieza(PrecioPiezaDTO pieza) {
		this.piezas.add(pieza);
	}
	
	@Override
	public String toString() {
		return String.valueOf(id);
	}

	public Calendar getFechaProcesada() {
		return fechaProcesada;
	}

	public void setFechaProcesada(Calendar fechaProcesada) {
		this.fechaProcesada = fechaProcesada;
	}
	
	public JRDataSource getPiezasDS() {
	    return new JRBeanCollectionDataSource(piezas);   
	}

	public double getTotal() {
		this.total = 0;
		if (piezas!=null)
			for (PrecioPiezaDTO p : piezas) {
				this.total += (p.getPrecio()*p.getCantidad());
			}
		return total;
	}


}
