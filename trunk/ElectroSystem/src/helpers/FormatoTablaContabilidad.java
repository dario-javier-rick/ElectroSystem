package helpers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class FormatoTablaContabilidad extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
	      if (table.getValueAt(row, 5).equals("Egreso"))
	      {
	         this.setOpaque(true);
	         this.setBackground(new Color (255, 127, 80));
	         this.setForeground(Color.BLACK);
	      } else if (table.getValueAt(row, 5).equals("Ingreso")) {
	    	  this.setOpaque(true);
		         this.setBackground(new Color(125, 251, 152));
		         this.setForeground(Color.BLACK);
	      }

	      return this;
	}

}
