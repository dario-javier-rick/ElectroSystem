package dto;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class OrdenDTO {

	private int idOT;
	private ClienteDTO cliente;
	private ElectrodomesticoDTO electrodomestico;
	private String descripcion;
	private UsuarioDTO usuarioAlta;
	private UsuarioDTO tecnicoAsoc;
	private boolean esDelivery;
	private Calendar vencPresup;
	private Calendar fechaReparado;
	private Calendar expiraGarantia;
	private OrdenDTO otAsociada;
	private EstadoDTO estado;
	private List<PiezaDTO> piezasPresupuestadas;
	private List<PiezaDTO> piezasUsadas;
	private double manoDeObra;
	private double costoDeEnvio;
	private String domicilioEntrega;
	private LocalidadDTO localidadEntrega;

	// para el reporte
	private double total;
	private int cantidadPiezas;
	private double totalPiezas;
	private List<PiezaPresupuestoDTO> piezasReporte;

	public OrdenDTO(int idOT, ClienteDTO cliente, ElectrodomesticoDTO electrodomestico, String descripcion,
			UsuarioDTO usuarioAlta, UsuarioDTO tecnicoAsoc, boolean esDelivery, Calendar vencPresup,
			Calendar fechaReparado, Calendar expiraGarantia, OrdenDTO otAsociada, EstadoDTO estado,
			List<PiezaDTO> piezasPresupuestadas, List<PiezaDTO> piezasUsadas, double manoDeObra, 
			double costoDeEnvio, String domicilioEntrega, LocalidadDTO localidadEntrega) {

		this.idOT = idOT;
		this.cliente = cliente;
		this.electrodomestico = electrodomestico;
		this.descripcion = descripcion;
		this.usuarioAlta = usuarioAlta;
		this.tecnicoAsoc = tecnicoAsoc;
		this.esDelivery = esDelivery;
		this.vencPresup = vencPresup;
		this.fechaReparado = fechaReparado;
		this.expiraGarantia = expiraGarantia;
		this.otAsociada = otAsociada;
		this.estado = estado;
		this.piezasPresupuestadas = piezasPresupuestadas;
		this.piezasUsadas = piezasUsadas;
		this.manoDeObra = manoDeObra;
		this.costoDeEnvio = costoDeEnvio;
		this.domicilioEntrega = domicilioEntrega;
		this.localidadEntrega = localidadEntrega;
	}

	public OrdenDTO(int id, ClienteDTO clienteAsociado, ElectrodomesticoDTO electrodomestico, String detalle,
			UsuarioDTO usuario, boolean esDelivery) {
		this.idOT = id;
		this.cliente = clienteAsociado;
		this.electrodomestico = electrodomestico;
		this.descripcion = detalle;
		this.usuarioAlta = usuario;
		this.esDelivery = esDelivery;
//		if (esDelivery)
//			costoDeEnvio = clienteAsociado.getLocalidad().getZonaDeEnvio().getPrecio();
//		else
//			costoDeEnvio = 0.0;
	}

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	public UsuarioDTO getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(UsuarioDTO usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public UsuarioDTO getTecnicoAsoc() {
		return tecnicoAsoc;
	}

	public void setTecnicoAsoc(UsuarioDTO tecnicoAsoc) {
		this.tecnicoAsoc = tecnicoAsoc;
	}

	public boolean isEsDelivery() {
		return esDelivery;
	}

	public void setEsDelivery(boolean esDelivery) {
		this.esDelivery = esDelivery;
	}

	public OrdenDTO getOtAsociada() {
		return otAsociada;
	}

	public void setOtAsociada(OrdenDTO otAsociada) {
		this.otAsociada = otAsociada;
	}

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}

	public double getManoDeObra() {
		return manoDeObra;
	}

	public void setManoDeObra(double manoDeObra) {
		this.manoDeObra = manoDeObra;
	}

	public Calendar getExpiraGarantia() {
		return expiraGarantia;
	}

	public List<PiezaDTO> getPiezasPresupuestadas() {
		return piezasPresupuestadas;
	}

	public void setPiezasPresupuestadas(List<PiezaDTO> piezasPresupuestadas) {
		this.piezasPresupuestadas = piezasPresupuestadas;
	}

	public List<PiezaDTO> getPiezasUsadas() {
		return piezasUsadas;
	}

	public void setPiezasUsadas(List<PiezaDTO> piezasUsadas) {
		this.piezasUsadas = piezasUsadas;
	}

	public void setExpiraGarantia(Calendar expiraGarantia) {
		this.expiraGarantia = expiraGarantia;
	}

	public Calendar getVencPresup() {
		return vencPresup;
	}

	public void setVencPresup(Calendar vencPresup) {
		this.vencPresup = vencPresup;
	}

	public ElectrodomesticoDTO getElectrodomestico() {
		return electrodomestico;
	}

	public void setElectrodomestico(ElectrodomesticoDTO electrodomestico) {
		this.electrodomestico = electrodomestico;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIdOT() {
		return idOT;
	}

	public void setIdOT(int idOT) {
		this.idOT = idOT;
	}

	@Override
	public String toString() {
		return String.valueOf(idOT);
	}

	public double getCostoDeEnvio() {
		return costoDeEnvio;
	}

	public void setCostoDeEnvio(double costoDeEnvio) {
		this.costoDeEnvio = costoDeEnvio;
	}

	public Calendar getFechaReparado() {
		return fechaReparado;
	}

	public void setFechaReparado(Calendar fechaReparado) {
		this.fechaReparado = fechaReparado;
	}

	public JRDataSource getPresupuestadasDS() {
		return new JRBeanCollectionDataSource(piezasPresupuestadas);
	}

	public double getTotal() {
		this.total = 0;
		if (piezasPresupuestadas != null)
			for (PiezaDTO p : piezasPresupuestadas) {
				this.total += p.getPrecio_venta();
			}
		this.total += manoDeObra;
		this.total += costoDeEnvio;
		return total;
	}

	public int getCantidadPiezas() {
		this.cantidadPiezas = piezasPresupuestadas.size();
		return cantidadPiezas;
	}

	public void setCantidadPiezas(int cantidadPiezas) {
		this.cantidadPiezas = cantidadPiezas;
	}

	public double getTotalPiezas() {
		this.totalPiezas = 0;
		for (int i = 0; i < piezasPresupuestadas.size(); i++) {
			totalPiezas += piezasPresupuestadas.get(i).getPrecio_venta();
		}
		return totalPiezas;
	}

	public void setTotalPiezas(double totalPiezas) {
		this.totalPiezas = totalPiezas;
	}

	public List<PiezaPresupuestoDTO> getPiezasReporte() {
		List<PiezaPresupuestoDTO> result = new LinkedList<>();

		for (int i = 0; i < piezasPresupuestadas.size(); i++) {
			PiezaDTO pieza = piezasPresupuestadas.get(i);
			int cantidad = 0;
			for (int j = 0; j < piezasPresupuestadas.size(); j++) {
				if (piezasPresupuestadas.get(j).equals(pieza)) {
					cantidad++;
				}
			}
			PiezaPresupuestoDTO nueva = new PiezaPresupuestoDTO(pieza, cantidad);
			if (!result.contains(nueva))
				result.add(nueva);

		}

		piezasReporte = result;
		return piezasReporte;
	}

	public JRDataSource getPiezasConCantidadDS() {
		this.piezasReporte = getPiezasReporte();
		return new JRBeanCollectionDataSource(piezasReporte);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdenDTO other = (OrdenDTO) obj;
		if (idOT != other.idOT)
			return false;
		return true;
	}

	public String getDomicilioEntrega() {
		return domicilioEntrega;
	}

	public void setDomicilioEntrega(String domicilioEntrega) {
		this.domicilioEntrega = domicilioEntrega;
	}

	public LocalidadDTO getLocalidadEntrega() {
		return localidadEntrega;
	}

	public void setLocalidadEntrega(LocalidadDTO localidadEntrega) {
		this.localidadEntrega = localidadEntrega;
	}
}
