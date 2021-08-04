package componentes_swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JComboBox;

public class ListaHorarios extends JComboBox<String>{

	public ListaHorarios() {
		super();
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		setBackground(Color.WHITE);
		
		addItem("");
		
		for(int i = 0; i<10;i++) {
			addItem("0"+i+":00");
			addItem("0"+i+":30");
		}
		for(int i = 10; i<24;i++) {
			addItem(i+":00");
			addItem(i+":30");
		}
		
		setPreferredSize(new Dimension(getPreferredSize().width + 20, getPreferredSize().height));
	}
	
	public ListaHorarios(String seleccionado) {
		super();
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		
		addItem("");
		
		for(int i = 0; i<10;i++) {
			addItem("0"+i+":00");
			addItem("0"+i+":30");
		}
		for(int i = 10; i<24;i++) {
			addItem(i+":00");
			addItem(i+":30");
		}
		
		setPreferredSize(new Dimension(getPreferredSize().width + 20, getPreferredSize().height));
		setSelectedItem(seleccionado);
	}
	
}
