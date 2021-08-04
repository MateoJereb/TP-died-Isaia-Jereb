package componentes_swing.modelos_tablas;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ModeloTablaLineas extends AbstractTableModel{

	String[] columnNames = {"ID","Nombre","Color","Activa"};
	Vector<Vector<Object>> data;
	
	public ModeloTablaLineas() {
		data = new Vector<Vector<Object>>();
	}
	
	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex).get(columnIndex);
	}
	
	public String getColumnName(int c) {
		return columnNames[c];
	}
	
	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
	
	public void setData(Vector<Vector<Object>> data) {
		this.data = data;
	}
	
	public boolean isCellEditable(int row, int col) {
		if(col == 3) {
			return true;
		}
		return false;
	}
	
	 public void setValueAt(Object value, int row, int col) {
	        data.get(row).set(col, value);
	        fireTableCellUpdated(row, col);
	 }

}
