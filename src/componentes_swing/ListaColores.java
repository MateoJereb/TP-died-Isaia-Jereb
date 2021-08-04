package componentes_swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import postgre_db.PostgreDB;

public class ListaColores extends JComboBox<String>{
	
	public ListaColores() {
		super();
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		
		addItem("");
		for(Color c : PostgreDB.recuperarColores()) {
			addItem(String.valueOf(c.getRGB()));
		}
		
		setRenderer(new MyCellRenderer(this));
	}
	
	class MyCellRenderer extends JLabel implements ListCellRenderer{
		
		private JComboBox lista;
		
		public MyCellRenderer(JComboBox lista) {  
            setOpaque(true);  
            this.lista = lista;
        } 
		
		@Override
		 public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
			String color = (String) value;
			
			if(color.length()>0) {
				Color c = new Color(Integer.parseInt(color));
				lista.setBackground(c);
				lista.setForeground(c);
				setBackground(c);
				setForeground(c);
				setText(c.getRed()+"/"+c.getGreen()+"/"+c.getBlue());
			}
			else {
				setText("Seleccionar...");
				lista.setBackground(Color.WHITE);
				lista.setForeground(Color.GRAY);
				setBackground(Color.WHITE);
				setForeground(Color.GRAY);
			}		
			
			return this;
		}
	}
	
}
