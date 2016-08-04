package presentacion.ventanas.fleteros;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilCalendarModel;

@SuppressWarnings("serial")
public class VentanaAsignacion extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	
	public JTable table;
	public JTable tableResultado;

	private JScrollPane spDatosTabla;
	private JScrollPane spDatosTablaResultado;

	private JButton okButton;
	private JButton cancelButton;

	private JLabel lblEnvosAsignadosA;
	private JDatePickerImpl fechaEnvio;

	private JLabel lblFecha;
	private SpringLayout springLayout;

	public VentanaAsignacion(JFrame padre) {
		
		super(padre, true);
		setResizable(false);
		setTitle("Asignar Envíos");
		setBounds(100, 100, 789, 446);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

//		JLabel lblFletero = new JLabel("Fletero");
//		lblFletero.setBounds(10, 22, 64, 14);
//		contentPanel.add(lblFletero);

		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false) ;
		table.setBounds(10, 47, 287, 252);

		spDatosTabla = new JScrollPane(table);
		spDatosTabla.setBounds(10, 47, 486, 281);
		contentPanel.add(spDatosTabla);

		tableResultado = new JTable();
		tableResultado.getTableHeader().setReorderingAllowed(false) ;
		tableResultado.setBounds(356, 47, 295, 252);

		spDatosTablaResultado = new JScrollPane(tableResultado);
		spDatosTablaResultado.setBounds(506, 47, 267, 281);
		contentPanel.add(spDatosTablaResultado);

		UtilCalendarModel model = new UtilCalendarModel();
		Properties properties = new Properties();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);

		fechaEnvio = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		fechaEnvio.setBounds(605, 340, 168, 23);
		contentPanel.add(fechaEnvio);
		springLayout = (SpringLayout) fechaEnvio.getLayout();
		fechaEnvio.setEnabled(false);

//		lblEnvosAsignadosA = new JLabel();
//		lblEnvosAsignadosA.setBounds(467, 22, 257, 14);
//		contentPanel.add(lblEnvosAsignadosA);

		lblFecha = new JLabel("Fecha");
		lblFecha.setBounds(546, 349, 49, 14);
		contentPanel.add(lblFecha);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public JTable getTableResultado() {
		return tableResultado;
	}

	public void setTableResultado(JTable tableResultado) {
		this.tableResultado = tableResultado;
	}

	public JLabel getLblEnvosAsignadosA() {
		return lblEnvosAsignadosA;
	}

	public void setLblEnvosAsignadosA(String lblEnvosAsignadosA) {
		this.lblEnvosAsignadosA.setText(lblEnvosAsignadosA);
	}

	public JDatePickerImpl getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(JDatePickerImpl fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	private class DateLabelFormatter extends AbstractFormatter {

		private String datePattern = "dd/MM/yyyy";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}
			return "";
		}
	}

	public JLabel getLblFecha() {
		return lblFecha;
	}

	public void setLblFecha(JLabel lblFecha) {
		this.lblFecha = lblFecha;
	}

}
