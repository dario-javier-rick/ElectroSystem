package modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.csvreader.CsvReader;

import dto.MarcaDTO;
import dto.PiezaDTO;
import dto.PrecioPiezaDTO;
import presentacion.ventanas.piezas.VentanaSeleccionarArchivo;

public class ImporterCSV {

	private Modelo modelo;
	private List<MarcaDTO> marcasBD;
	private List<MarcaDTO> marcasBDProveedor;
	private List<PiezaDTO> piezasBD;
	private int idProveedor;
	private String path;
	private VentanaSeleccionarArchivo parent;

	private final static Integer OK = 1;
	private final static Integer CHANGE_DESCRIPTION = 2;
	private final static Integer CREATE_MARCA = 3;
	private final static Integer CREATE_PIEZA = 4;
	private final static Integer CHANGE_MARCA = 5;
	private final static Integer NO_ASIGNED_MARCA = 6;
	private static final Integer ERROR_MARCA_DESCRIPTION_DISTINCT = 7;
	private static final Integer ERROR_FORMAT = 8;

	public ImporterCSV(VentanaSeleccionarArchivo parent, Modelo modelo, int idProveedor, String path) {
		this.modelo = modelo;
		this.idProveedor = idProveedor;
		this.path = path;
		this.parent = parent;
	}

	public void importPrices() throws Exception {

		try {
			marcasBD = new ArrayList<MarcaDTO>();
			piezasBD = modelo.obtenerItems();
			List<MarcaDTO> allMarcas = modelo.obtenerMarcas();
			marcasBDProveedor = modelo.obtenerMarcasProveedor(idProveedor);
			for (MarcaDTO marcaDTO : allMarcas) {
				boolean exist = false;
				for (MarcaDTO marcaDTOProveedor : marcasBDProveedor) {
					if (marcaDTO.getIdMarca() == marcaDTOProveedor.getIdMarca()) {
						exist = true;
						break;
					}
				}
				if (!exist)
					marcasBD.add(marcaDTO);
			}

			List<PrecioPiezaDTO> piezasImport = new ArrayList<PrecioPiezaDTO>();
			List<ImportPrice> verificarPiezas = new ArrayList<ImportPrice>();

			loadCollections(piezasImport, verificarPiezas);

			for (ImportPrice importPrice : verificarPiezas) {

				Integer answer = importPrice.getAnswer();
				String datosPieza = null;
				PiezaDTO pieza = importPrice.getPieza();
				if (pieza != null)
					datosPieza = helpers.Validador.contactenarStrings(pieza.getIdUnico(), pieza.getDescripcion(), pieza.getMarca().getNombre());
				try {

					if (CHANGE_DESCRIPTION.equals(answer)) {
						changeDescription(piezasImport, importPrice, datosPieza);
					} else if (CREATE_MARCA.equals(answer)) {
						// CREAR MARCA Y ASIGNARLA AL PROVEEDOR
						createMarca(piezasImport, importPrice, datosPieza);
					} else if (CHANGE_MARCA.equals(answer)) {
						changeMarca(piezasImport, importPrice, datosPieza);
					} else if (NO_ASIGNED_MARCA.equals(answer)) {
						asignedMarca(piezasImport, importPrice, datosPieza);
					} else if (CREATE_PIEZA.equals(answer)) {

						String marca = importPrice.getMarca();

						MarcaDTO newPiezaMarca = null;

						// TODO VERIFICAR CASOS DE CREACION
						for (MarcaDTO marcaDTO : marcasBDProveedor) {
							if (marcaDTO.getNombre().equals(marca)) {
								newPiezaMarca = marcaDTO;
								break;
							}
						}

						if (newPiezaMarca == null) {
							for (MarcaDTO marcaDTO : marcasBD) {
								if (marcaDTO.getNombre().equals(marca)) {
									newPiezaMarca = marcaDTO;
									break;
								}
							}
						}
						createPieza(piezasImport, importPrice, datosPieza, newPiezaMarca);

					} else if (ERROR_MARCA_DESCRIPTION_DISTINCT.equals(answer)) {
						String datosPiezaImporter = helpers.Validador.contactenarStrings(importPrice.getNro_serie(), importPrice.getDescripcion(), importPrice.getMarca());
						JOptionPane.showMessageDialog(parent, "Ocurrió un error. El identificador de la pieza que desea importar no se corresponde con su marca y descripcion. \n Datos de la pieza: " + datosPiezaImporter, "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {
					continue;
				}
			}

			modelo.updatePrecioPiezaProveedor(idProveedor, piezasImport);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new Exception("No se ha encontrado el archivo especificado.");

		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Hubo problemas con el archivo");
		}

	}

	private void createPieza(List<PrecioPiezaDTO> piezasImport, ImportPrice importPrice, String datosPieza, MarcaDTO newPiezaMarca) throws Exception {

		MarcaDTO marca = null;
		if (newPiezaMarca == null) {
			marca = new MarcaDTO(0, importPrice.getMarca());
			modelo.agregarMarca(marca);
			modelo.insertarMarcaProveedor(idProveedor, marca.getIdMarca());
		} else {
			marca = newPiezaMarca;
		}

		PiezaDTO pieza = new PiezaDTO(0, marca, importPrice.getNro_serie(), importPrice.getDescripcion(), new Float(importPrice.getPrecio_compra()), 0);
		modelo.agregarPieza(pieza);

		piezasImport.add(new PrecioPiezaDTO(pieza, new Float(importPrice.getPrecio_compra()), null));
	}

	private void loadCollections(List<PrecioPiezaDTO> piezasImport, List<ImportPrice> verificarPiezas) throws FileNotFoundException, IOException, Exception {
		CsvReader piezasProveedores_import = new CsvReader(path);
		piezasProveedores_import.readHeaders();
		while (piezasProveedores_import.readRecord()) {
			String nro_serie = piezasProveedores_import.get("nro_serie");
			String descripcion = piezasProveedores_import.get("descripcion");
			String marca = piezasProveedores_import.get("marca");
			String precio_compra = piezasProveedores_import.get("precio");

			try {
				if (nro_serie.isEmpty() || marca.isEmpty() || descripcion.isEmpty() || (precio_compra.isEmpty()))
					verificarPiezas.add(new ImportPrice(nro_serie, descripcion, marca, precio_compra, null, ERROR_FORMAT));
				new Float(precio_compra);
			} catch (Exception e) {
				verificarPiezas.add(new ImportPrice(nro_serie, descripcion, marca, precio_compra, null, ERROR_FORMAT));
				throw new Exception("El archivo no esta bien formado. El precio no contiene un formato valido");// TODO
			}
			boolean existPieza = false;
			for (PiezaDTO piezaDTO : piezasBD) {
				Integer value = validateParameters(nro_serie, descripcion, marca, precio_compra, piezaDTO);
				if (value.equals(OK)) {
					piezasImport.add(new PrecioPiezaDTO(piezaDTO, new Float(precio_compra), null));
					existPieza = true;
					break;
				} else if (!value.equals(CREATE_PIEZA)) {
					verificarPiezas.add(new ImportPrice(nro_serie, descripcion, marca, precio_compra, piezaDTO, value));
					existPieza = true;
					break;
				}
				existPieza = false;
			}
			if (!existPieza)
				verificarPiezas.add(new ImportPrice(nro_serie, descripcion, marca, precio_compra, null, CREATE_PIEZA));

		}
		piezasProveedores_import.close();
	}

	private void asignedMarca(List<PrecioPiezaDTO> piezasImport, ImportPrice importPrice, String datosPieza) throws Exception {
		int respuesta = JOptionPane.showConfirmDialog(parent, "Desea asignar la marca al proveedor de la pieza: " + datosPieza + "\n" + "Por la siguiente marca: " + importPrice.getMarca(), null, JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			for (MarcaDTO marcaDTO : marcasBD) {
				if (marcaDTO.equals(importPrice.getMarca())) {
					modelo.insertarMarcaProveedor(idProveedor, marcaDTO.getIdMarca());
					asignarMarca(piezasImport, importPrice, datosPieza, marcaDTO);
					break;
				}
			}
		} else {
			// TODO NO ASIGNA
		}
	}

	private void changeDescription(List<PrecioPiezaDTO> piezasImport, ImportPrice importPrice, String datosPieza) throws Exception {
		int respuesta = JOptionPane.showConfirmDialog(parent, "Desea cambiar la descripcion de la pieza: " + datosPieza + "\n" + "Por la siguiente descripcion: " + importPrice.getDescripcion(), null, JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			PiezaDTO pieza = importPrice.getPieza();
			pieza.setDescripcion(importPrice.getDescripcion());
			piezasImport.add(new PrecioPiezaDTO(pieza, new Float(importPrice.getPrecio_compra()), null));
			modelo.modificarPieza(pieza);
		} else {
			// TODO NO IMPORTA PRECIO
		}
	}

	private void changeMarca(List<PrecioPiezaDTO> piezasImport, ImportPrice importPrice, String datosPieza) throws Exception {
		MarcaDTO marcaAsignar = null;
		for (MarcaDTO marca : marcasBDProveedor) {
			if (importPrice.getMarca().equals(marca.getNombre())) {
				marcaAsignar = marca;
				break;
			}
		}

		asignarMarca(piezasImport, importPrice, datosPieza, marcaAsignar);
	}

	private void createMarca(List<PrecioPiezaDTO> piezasImport, ImportPrice importPrice, String datosPieza) throws Exception {
		int respuesta = JOptionPane.showConfirmDialog(parent, "Se encontro una marca que no se esta registrada en el sistema. ¿Desea crear la marca y asociarla al proveedor?", null, JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			MarcaDTO marca = new MarcaDTO(0, importPrice.getMarca());
			modelo.agregarMarca(marca);
			modelo.insertarMarcaProveedor(idProveedor, marca.getIdMarca());

			asignarMarca(piezasImport, importPrice, datosPieza, marca);
		}
	}

	private void asignarMarca(List<PrecioPiezaDTO> piezasImport, ImportPrice importPrice, String datosPieza, MarcaDTO marca) throws Exception {
		int respuesta = JOptionPane.showConfirmDialog(parent, "Desea cambiar la marca de la pieza: " + datosPieza + "\n" + "Por la siguiente marca: " + importPrice.getMarca(), null, JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.OK_OPTION) {
			PiezaDTO pieza = importPrice.getPieza();
			pieza.setMarca(marca);
			piezasImport.add(new PrecioPiezaDTO(pieza, new Float(importPrice.getPrecio_compra()), null));
			modelo.modificarPieza(pieza);
		}
	}

	private Integer validateParameters(String nro_serie, String descripcion, String marca, String precio_compra, PiezaDTO pieza) {

		String nombreMarca = pieza.getMarca().getNombre();

		if (nro_serie.equals((pieza.getIdUnico()))) {
			if (descripcion.equals(pieza.getDescripcion())) {
				if (marca.equals(nombreMarca))
					return OK;
				else {
					for (MarcaDTO marcaDTO : marcasBDProveedor) {
						if (marcaDTO.getNombre().equals(marca))
							return CHANGE_MARCA;
					}
					for (MarcaDTO marcaDTO : marcasBD) {
						if (marcaDTO.getNombre().equals(marca))
							return NO_ASIGNED_MARCA;
					}
					// CREO MARCA,MODIFICO PIEZA ??CON PRECIO
					return CREATE_MARCA;
				}
			} else {
				if (marca.equals(nombreMarca))
					// PREGUNTO SI CAMBIA MODELO: SI ? MODIFICO PRECIO : CANCELAR
					return CHANGE_DESCRIPTION;
				else {
					// TODO PREGUNTAR CREAR PIEZA ??
					return ERROR_MARCA_DESCRIPTION_DISTINCT;
				}
			}
		}
		return CREATE_PIEZA;
	}

}
