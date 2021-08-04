package componentes_swing.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;

import clase_app.App;
import clases.Linea;
import componentes_swing.BotonJ;
import componentes_swing.CampoJ;
import componentes_swing.CheckBoxJ;
import componentes_swing.EtiquetaJ;
import componentes_swing.ListaColores;
import componentes_swing.TablaJ;
import componentes_swing.modelos_tablas.ModeloTablaTrayectos;
import postgre_db.PostgreDB;

public class GestionarTrayectos extends JPanel{

	private TablaJ tabla;
	private ModeloTablaTrayectos modelo;
	
	public GestionarTrayectos() {
		super();			
		setBorder(new EmptyBorder(10, 20, 10, 20));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		panelBusqueda();
		panelResultados();
		panelBotones();
	}
	
	public void panelBusqueda() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Buscar lineas");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		
		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setLayout(new GridBagLayout());
		panelBusqueda.setBackground(Color.WHITE);
		panelBusqueda.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(20,20,10,20);
		cons.anchor = GridBagConstraints.NORTH;
		
		add(panelBusqueda,cons);
		
		CampoJ cId = new CampoJ();
		EtiquetaJ eId = new EtiquetaJ("ID");
		CampoJ cNombre = new CampoJ();
		EtiquetaJ eNombre = new EtiquetaJ("Nombre");
		ListaColores lColor = new ListaColores();
		EtiquetaJ eColor = new EtiquetaJ("Color");
		BotonJ buscar = new BotonJ("Buscar");
		
		cons.anchor = GridBagConstraints.CENTER;
		cons.weighty = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelBusqueda.add(eId,cons);
		
		cons.gridx = 1;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelBusqueda.add(cId,cons);
		
		cons.gridx = 2;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelBusqueda.add(eNombre,cons);
		
		cons.gridx = 3;
		cons.insets = new Insets(10,5,10,20);
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		panelBusqueda.add(cNombre,cons);
		
		cons.gridy = 1;
		cons.gridx = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelBusqueda.add(eColor,cons);
		
		cons.gridx=1;
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		cons.insets = new Insets(10,5,10,20);
		panelBusqueda.add(lColor,cons);
		
		cons.gridx=4;
		cons.weightx = 0.02;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(10,20,20,20);
		panelBusqueda.add(buscar,cons);
		
		buscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Optional<Integer> id;
				Optional<String> nombre;
				Optional<Color> color;
				
				if(cId.getText().length() == 0) {
					id = Optional.ofNullable(null);
				}
				else {
					id = Optional.of(Integer.parseInt(cId.getText()));
				}
				
				if(cNombre.getText().length() == 0) {
					nombre = Optional.ofNullable(null);
				}
				else {
					nombre = Optional.of(cNombre.getText());
				}
				
				if(((String)lColor.getSelectedItem()).length() == 0) {
					color = Optional.ofNullable(null);
				}
				else {
					color = Optional.of(new Color(Integer.parseInt((String) lColor.getSelectedItem())));
				}
				
				actualizarTabla(id,nombre,color);
			}
		});
	}
	
	public void panelResultados() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Resultados");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
	
		JPanel panelResultados = new JPanel();
		panelResultados.setLayout(new GridBagLayout());
		panelResultados.setBackground(Color.WHITE);
		panelResultados.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(10,20,20,20);
		cons.anchor = GridBagConstraints.CENTER;
		
		add(panelResultados,cons);
		
		modelo = new ModeloTablaTrayectos();
		tabla = new TablaJ(modelo);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(10);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(300);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(300);
		JScrollPane scroll = new JScrollPane(tabla);
		
		actualizarTabla(Optional.ofNullable(null),Optional.ofNullable(null),Optional.ofNullable(null));
		
		cons.gridy = 0;
		panelResultados.add(scroll,cons);
				
	}
	
	public void panelBotones() {
		GridBagConstraints cons = new GridBagConstraints();
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridBagLayout());
		panelBotones.setBackground(UIManager.getColor("CheckBox.focus"));
		
		cons.gridx = 0;
		cons.gridy = 2;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(0,20,0,20);
		cons.anchor = GridBagConstraints.EAST;
		add(panelBotones,cons);
		
		BotonJ verTrayecto = new BotonJ("Ver trayecto");
		BotonJ volver = new BotonJ("Volver atrás");
		
		cons.gridy=0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(0,10,20,10);
		panelBotones.add(verTrayecto,cons);
		
		cons.gridx=1;
		panelBotones.add(volver,cons);
		
		verTrayecto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer fila = tabla.getSelectedRow();
				
				if(fila != -1) {
					Integer id = (Integer) modelo.getValueAt(fila, 0);
					App.verTrayecto(id);
				}
				else {
					JDialog ventana = new JDialog(App.getVentana(),"Ver Trayecto");
					JPanel panelTrayecto = new JPanel(new GridBagLayout());
					panelTrayecto.setBackground(UIManager.getColor("CheckBox.focus"));
					ventana.setContentPane(panelTrayecto);
					GridBagConstraints cons2 = new GridBagConstraints();
					EtiquetaJ texto = new EtiquetaJ("Seleccione una linea antes");
					BotonJ aceptar = new BotonJ("Aceptar");
					
					cons2.gridx = 0;
					cons2.gridy = 0;
					cons2.gridwidth = 1;
					cons2.gridheight = 1;
					cons2.weightx = 0.1;
					cons2.fill = GridBagConstraints.HORIZONTAL;
					cons2.insets = new Insets(10,10,10,10);
					panelTrayecto.add(texto,cons2);
					
					cons2.gridy = 1;
					cons2.fill = GridBagConstraints.NONE;
					panelTrayecto.add(aceptar,cons2);
					
					aceptar.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							ventana.dispose();
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
						}						
					});
					
					ventana.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
						}
					});
					
					App.getVentana().setEnabled(false);
					ventana.pack();
					ventana.setLocationRelativeTo(null);
					ventana.setVisible(true);
				}
			}
		});
		
		volver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.menuPrincipal();
			}
		});
	}
	
	private void actualizarTabla(Optional<Integer> id, Optional<String> nombre, Optional<Color> color) {
		Vector<Linea> lineas = PostgreDB.recuperarLineas(id, nombre, color, Optional.ofNullable(null));
		Vector<Vector<Object>> data =  new Vector<Vector<Object>>();
		
		for(Linea l : lineas) {
			Vector<Object> fila = new Vector<Object>();
			fila.add(l.getId());
			fila.add(l.getNombre());
			fila.add(l.getColor());
			data.add(fila);
		}
				
		modelo.setData(data);
		modelo.fireTableDataChanged();
	}
}
