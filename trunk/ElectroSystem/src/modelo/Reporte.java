package modelo;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import dto.EnvioDTO;
import dto.FleteroDTO;
import dto.MovimientoDTO;
import dto.OrdenDTO;
import dto.RankElectrodomesticosDTO;
import dto.RankIncumplidoresDTO;
import dto.RankPiezasDTO;
import dto.SolicitudCompraDTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Reporte {
	private JasperReport reporte;
	private JasperViewer reporteViewer;
	private JasperPrint reporteLleno;
	private Modelo modelo;

	public void ReporteRankElectrodomesticos(Calendar ini, Calendar fin) throws Exception {
		modelo = Modelo.getInstance();
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		parametersMap.put("fecha", cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR));
		List<RankElectrodomesticosDTO> rankElectrodomesticos = modelo.getRankElectrodomesticos(10, ini, fin);
		parametersMap.put("electrodomesticos", new JRBeanCollectionDataSource(rankElectrodomesticos));
		List<RankElectrodomesticosDTO> rankElectrodomesticosGraphic = modelo.getRankElectrodomesticos(20, ini, fin);
		parametersMap.put("electrodomesticosGraphic", new JRBeanCollectionDataSource(rankElectrodomesticosGraphic));

		if (rankElectrodomesticos.isEmpty() && rankElectrodomesticosGraphic.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No se ha encontrado ningun electrodomestico utilizado alguna orden de trabajo que haya sido reparado  en el plazo deseado.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String reportSource = "/Rank_Electrodomesticos.jasper";
		InputStream is = null;
		try {
			is = getClass().getResourceAsStream(reportSource);
			this.reporte = (JasperReport) JRLoader.loadObject(is);
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, new JRBeanCollectionDataSource(rankElectrodomesticos));
			// TODO LA LISTA "rankElectrodomesticos" NO LA USA PERO ES NECESARIO PASARLA POR ERROR, es utilizada la que se pasa en el map
			// WARNING: The supplied java.sql.Connection object is null.
		} catch (JRException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			try {
				this.reporte = (JasperReport) JRLoader.loadObjectFromFile("reports//Rank_Electrodomesticos.jasper");
				this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, new JRBeanCollectionDataSource(rankElectrodomesticos));
			} catch (JRException ex2) {
				ex2.printStackTrace();
			}
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}
	}

	public void ReporteRankPiezas(Calendar ini, Calendar fin) throws Exception {
		modelo = Modelo.getInstance();
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		parametersMap.put("fecha", cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR));
		List<RankPiezasDTO> rankPiezas = modelo.getRankPiezas(10, ini, fin);
		parametersMap.put("piezas", new JRBeanCollectionDataSource(rankPiezas));
		List<RankPiezasDTO> rankPiezasGraphic = modelo.getRankPiezas(20, ini, fin);
		parametersMap.put("piezasGraphic", new JRBeanCollectionDataSource(rankPiezasGraphic));

		if (rankPiezas.isEmpty() && rankPiezasGraphic.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No se han encontrado piezas utilizadas en alguna orden de trabajo reparada en el plazo deseado.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String reportSource = "/Rank_Piezas.jasper";
		InputStream is = null;
		try {
			is = getClass().getResourceAsStream(reportSource);
			this.reporte = (JasperReport) JRLoader.loadObject(is);
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, new JRBeanCollectionDataSource(rankPiezas));
		} catch (JRException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			try {
				this.reporte = (JasperReport) JRLoader.loadObjectFromFile("reports//Rank_Piezas.jasper");
				this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, new JRBeanCollectionDataSource(rankPiezas));
			} catch (JRException ex2) {
				ex2.printStackTrace();
			}
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}
	}

	public void ReporteOtPresupuestada(OrdenDTO orden) {
		Collection<OrdenDTO> collection = new ArrayList<OrdenDTO>();
		collection.add(orden);
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		try {
			InputStream fuente = getClass().getResourceAsStream("/OtPresupuestada.jasper");
			this.reporte = (JasperReport) JRLoader.loadObject(fuente);
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, new JRBeanCollectionDataSource(collection));
		} catch (JRException ex) {
			ex.printStackTrace();
		}
	}

	public void ReporteOtGeneral(OrdenDTO orden) {
		Collection<OrdenDTO> collection = new ArrayList<OrdenDTO>();
		collection.add(orden);
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		try {
			InputStream fuente = getClass().getResourceAsStream("/OtGeneral.jasper");
			this.reporte = (JasperReport) JRLoader.loadObject(fuente);
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, new JRBeanCollectionDataSource(collection));
		} catch (JRException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	public void RankProveedoresIncumplidores(Calendar ini, Calendar fin) throws Exception {
		modelo = Modelo.getInstance();
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		Date desde = ini.getTime();
		Date hasta = fin.getTime();
		parametersMap.put("fechaDesde", new SimpleDateFormat("dd/MM/yyyy").format(desde));
		parametersMap.put("fechaHasta", new SimpleDateFormat("dd/MM/yyyy").format(hasta));
		List<RankIncumplidoresDTO> rankIncumplidores = modelo.getRankIncumplidores(ini, fin);

		if (rankIncumplidores.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No se han encontrado proveedores incumplidaores en el plazo seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String reportSource = "/Rank_Incumplidores.jasper";
		InputStream is = null;
		try {
			is = getClass().getResourceAsStream(reportSource);
			this.reporte = (JasperReport) JRLoader.loadObject(is);
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, new JRBeanCollectionDataSource(rankIncumplidores));
		} catch (JRException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			try {
				this.reporte = (JasperReport) JRLoader.loadObjectFromFile("reports//Rank_Incumplidores.jasper");
				this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, new JRBeanCollectionDataSource(rankIncumplidores));
			} catch (JRException ex2) {
				ex2.printStackTrace();
			}
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}
	}

	public void ReporteScGeneral(SolicitudCompraDTO solicitud) {
		Collection<SolicitudCompraDTO> collection = new ArrayList<SolicitudCompraDTO>();
		collection.add(solicitud);
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		try {
			InputStream fuente = getClass().getResourceAsStream("/ScGeneral.jasper");
			this.reporte = (JasperReport) JRLoader.loadObject(fuente);
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, new JRBeanCollectionDataSource(collection));
		} catch (JRException ex) {
			ex.printStackTrace();
		}
	}

	public void ReporteContable(MovimientoDTO movimientos) {

		Collection<Object> collection = new ArrayList<Object>();
		collection.add(movimientos);

		Map<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

		try {
			InputStream fuente = getClass().getResourceAsStream("/Contable.jasper");
			this.reporte = (JasperReport) JRLoader.loadObject(fuente);
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, new JRBeanCollectionDataSource(collection));

		} catch (JRException ex) {
			ex.printStackTrace();
		}
	}

	public void ReporteEnvios() throws Exception {

		modelo = Modelo.getInstance();
		List<EnvioDTO> envios = modelo.obtenerEnvios();
		for (int i = 0; i < envios.size(); i++) {
			if (calendar2str(envios.get(i).getFechaEnvio()).equals(calendar2str(Calendar.getInstance()))) {
				envios.get(i).setDetalles(modelo.obtenerDetalles(envios.get(i)));
			}
		}

		try {
			InputStream fuente = getClass().getResourceAsStream("/EnviosDelDia.jasper");
			this.reporte = (JasperReport) JRLoader.loadObject(fuente);
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, null, new JRBeanCollectionDataSource(envios));
		} catch (JRException ex) {
			ex.printStackTrace();
		}
	}

	public void ReporteHojaRuta(FleteroDTO fletero) throws Exception {

		modelo = Modelo.getInstance();
		try {
			InputStream fuente = getClass().getResourceAsStream("/HojaDeRuta.jasper");
			this.reporte = (JasperReport) JRLoader.loadObject(fuente);
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, null, new JRBeanCollectionDataSource(modelo.obtenerHojaRuta(fletero)));
		} catch (JRException ex) {
			ex.printStackTrace();
		}
	}

	public void mostrar() {
		this.reporteViewer = new JasperViewer(reporteLleno, false);
		this.reporteViewer.setVisible(true);
	}

	public void mostrarDesdeDialog(JDialog padre, String titulo) {
		this.reporteViewer = new JasperViewer(reporteLleno, false);
		JDialog dialog = new JDialog(padre);// the owner
		dialog.setContentPane(this.reporteViewer.getContentPane());
		dialog.setSize(this.reporteViewer.getSize());
		dialog.setTitle(titulo);
		dialog.setVisible(true);
	}

	public OutputStream adjunto() {
		OutputStream os = null;
		try {
			os = new FileOutputStream("C:/ProgramData/LabSW/20161/ServiceG1/reporte.pdf");
			// TODO: Esto puede estar en una ruta en el config.ini
			JasperExportManager.exportReportToPdfStream(reporteLleno, os);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return os;
		
	}

	public String calendar2str(Calendar cal) {

		Date date = cal.getTime();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = df.format(date);
		return fecha;
	}
}
