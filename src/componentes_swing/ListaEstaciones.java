package componentes_swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Vector;

import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import clases.Estacion;
import clases.Ruta;
import postgre_db.PostgreDB;

public class ListaEstaciones extends JComboBox<Estacion> {
	
	public ListaEstaciones(Boolean soloOperativas) {
		super();
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		
		LocalTime a = null, c = null;
		addItem(new Estacion(-1,"",a,c,false));
		
		Vector<Estacion> estaciones = null;
		
		if(soloOperativas) {
			estaciones = PostgreDB.recuperarEstaciones(Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null), Optional.of(true));
		}
		else {
			estaciones = PostgreDB.recuperarEstaciones(Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null));
		}
		
		for(Estacion e : estaciones) {
			addItem(e);
		}
		
		setRenderer(new MyCellRenderer(this));
	}
	
	public ListaEstaciones(Integer idLinea) {
		super();
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		
		LocalTime a = null, c = null;
		addItem(new Estacion(-1,"",a,c,false));
		
		Vector<Estacion> estaciones = PostgreDB.recuperarEstaciones(Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null));
		Vector<Ruta> rutas = PostgreDB.recuperarRutas(Optional.of(idLinea), Optional.ofNullable(null), Optional.ofNullable(null));
		
		Vector<Estacion> estacionesTrayecto = new Vector<Estacion>();
		rutas.stream().forEach(r -> {estacionesTrayecto.add(r.getOrigen()); estacionesTrayecto.add(r.getDestino());});
		
		estaciones.removeAll(estacionesTrayecto);
		
		for(Estacion e : estaciones) {
			addItem(e);
		}
		
		setRenderer(new MyCellRenderer(this));
	}
	
	public ListaEstaciones(Integer idLinea, Estacion seleccionada) {
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		setEnabled(false);
		
		LocalTime a = null, c = null;
		addItem(new Estacion(-1,"",a,c,false));
		
		addItem(seleccionada);
		UIManager.put("ComboBox.disabledForeground", Color.GRAY);
		setRenderer(new MyCellRenderer(this));
		setSelectedItem(seleccionada);
	}
	
	class MyCellRenderer extends JLabel implements ListCellRenderer{
		
		private JComboBox lista;
		
		public MyCellRenderer(JComboBox lista) {    
            this.lista = lista;
        } 
		
		@Override
		 public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
			Estacion estacion= (Estacion) value;
			
			if(estacion.getNombre().length()>0) {
				setText(estacion.getNombre() + " (ID "+estacion.getId()+")");
				lista.setForeground(Color.BLACK);
				setForeground(Color.BLACK);
				
				
			}
			else {
				setText("Seleccionar...");
				lista.setForeground(Color.GRAY);
				setForeground(Color.GRAY);
			}		
			
			return this;
		}
	}
	
}
