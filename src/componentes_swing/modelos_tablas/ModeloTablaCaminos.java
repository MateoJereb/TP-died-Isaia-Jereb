package componentes_swing.modelos_tablas;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ModeloTablaCaminos extends AbstractTableModel{

	String[] columnNames = {"Camino","Distancia (Km)","Duraci�n (min)","Costo ($)"};
	Vector<Vector<Object>> data;
	
	public ModeloTablaCaminos() {
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
		return false;
	}
	
	 public void setValueAt(Object value, int row, int col) {
	        data.get(row).set(col, value);
	        fireTableCellUpdated(row, col);
	 }

}
