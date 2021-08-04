package componentes_swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import clases.Estacion;
import clases.Ruta;

public class TablaJ extends JTable{

	public TablaJ(TableModel modelo) {
		super(modelo);
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		setRowHeight(22);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();	
		centerRenderer.setHorizontalAlignment(EtiquetaJ.CENTER);
		setDefaultRenderer(String.class, centerRenderer);
		setDefaultRenderer(Double.class, centerRenderer);
		setDefaultRenderer(Integer.class, centerRenderer);
		setDefaultRenderer(Color.class, new EditorCeldasColores());
		setDefaultRenderer(Vector.class, new EditorCeldasCaminos());
		
		setAutoResizeMode(AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		setRowSelectionAllowed(true);
		setColumnSelectionAllowed(false);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
	}
	
	class EditorCeldasColores extends JLabel implements TableCellRenderer{
		
		public EditorCeldasColores() {
			setOpaque(true);
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			
			if(value.getClass() == (Color.class)) {
				Color c = (Color) value;
				setBackground(c);
				setForeground(c);
			}
			
			return this;
		}
		
	}
	
	class EditorCeldasCaminos extends JLabel implements TableCellRenderer{
		
		public EditorCeldasCaminos() {
			setOpaque(true);
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			
			if(value.getClass() == Vector.class) {
				Vector<Ruta> camino = (Vector<Ruta>) value;
				String texto="  ";
				for(int i = 0; i<camino.size();i++) {
					texto+=camino.get(i).getOrigen().getNombre()+", ";
				}
				texto+=camino.get(camino.size()-1).getDestino().getNombre();
				setText(texto);
				setFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
				setForeground(UIManager.getColor("Table.foreground"));
				if(isSelected) {
					setBackground(UIManager.getColor("Table.selectionBackground"));
					
				}
				else {
					setBackground(UIManager.getColor("Table.background"));
				}
				
			}
			
			return this;
		}
		
	}
	
}
