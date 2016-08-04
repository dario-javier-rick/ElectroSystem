package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import dto.ProveedorDTO;
import modelo.ImporterCSV;
import modelo.Modelo;
import presentacion.ventanas.piezas.VentanaSeleccionarArchivo;

public class ControladorSeleccionarArchivo implements ActionListener {

	private Modelo modelo;
	private VentanaSeleccionarArchivo ventana;
	private File selectedFile;

	public ControladorSeleccionarArchivo(VentanaSeleccionarArchivo ventana, Modelo modelo) {

		this.ventana = ventana;
		this.modelo = modelo;

		iniciar();
		this.ventana.setTitle("Importar Precios");
		this.ventana.setVisible(true);

	}

	public void iniciar() {
		this.ventana.setLocationRelativeTo(null);

		this.ventana.getCancelButton().addActionListener(this);
		this.ventana.getBtnProcesar().addActionListener(this);
		this.ventana.getBtnSeleccionarArchivo().addActionListener(this);

		llenarComboProveedores();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.ventana.getCancelButton()) {
			this.ventana.dispose();

		} else if (e.getSource() == this.ventana.getBtnProcesar()) {

			Object item = this.ventana.getCombo().getSelectedItem();
			if (item != null) {
				if (selectedFile != null) {
					try {
						ImporterCSV importer = new ImporterCSV(this.ventana, modelo,
								((ProveedorDTO) item).getIdProveedor(), selectedFile.getAbsolutePath());
						importer.importPrices();
						JOptionPane.showMessageDialog(this.ventana,
								"Se ha completado la importacion de las piezas del archivo seleccionado.", "Importado!",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(this.ventana,
								"No se ha podido importar el archivo. " + e1.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else
					JOptionPane.showMessageDialog(this.ventana,
							"Debe seleccionar un archivo valido para poder importar los precios del archivo", "Error",
							JOptionPane.ERROR_MESSAGE);
			} else
				JOptionPane.showMessageDialog(this.ventana,
						"Debe seleccionar un proveedor para poder importar los precios del archivo", "Error",
						JOptionPane.ERROR_MESSAGE);
			selectedFile = null;

		} else if (e.getSource() == this.ventana.getBtnSeleccionarArchivo()) {
			
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV", "csv");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(ventana);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				selectedFile = chooser.getSelectedFile();
			}
		}
	}

	private void llenarComboProveedores() {
		this.ventana.getCombo().removeAllItems();

		List<ProveedorDTO> proveedores;
		try {
			proveedores = modelo.obtenerProveedores();
			for (ProveedorDTO proveedorDTO : proveedores) {
				this.ventana.getCombo().addItem(proveedorDTO);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ventana, "No se han podido cargar los proveedores", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
