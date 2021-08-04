package componentes_swing.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import clase_app.App;
import clases.Estacion;
import componentes_swing.*;
import componentes_swing.modelos_tablas.ModeloTablaProxMantenimiento;
import postgre_db.PostgreDB;

public class ProximoMantenimiento extends JPanel {

	//PanelProximoMantenimiento
	private JPanel panelMantenimiento;
	private EtiquetaJ estacion;
	//PanelEstaciones
	private JPanel panelEstaciones;
	private TablaJ tabla;
	private ModeloTablaProxMantenimiento modelo;
	private JScrollPane scroll;
	//Volver
	private BotonJ volver;
	
	public ProximoMantenimiento() {
		super();
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		panelMantenimiento();
		panelEstaciones();
		
		volver = new BotonJ("Volver atrás");
		
		volver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.infoEstaciones();
			}			
		});
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.gridy=2;
		cons.gridx=0;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(0,20,10,20);
		add(volver,cons);
		
	}
	
	private void panelMantenimiento() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Próximo mantenimiento");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
	
		panelMantenimiento = new JPanel();
		panelMantenimiento.setLayout(new GridBagLayout());
		panelMantenimiento.setBackground(Color.WHITE);
		panelMantenimiento.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(20,20,10,20);
		cons.anchor = GridBagConstraints.NORTH;
		add(panelMantenimiento,cons);
		
		estacion = new EtiquetaJ();
		estacion.setFont(new Font("Microsoft Tai Le",Font.BOLD,18));
		estacion.setForeground(Color.RED);
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(20,10,20,10);
		panelMantenimiento.add(estacion,cons);
	}
	
	private void panelEstaciones() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Últimos mantenimientos");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
	
		panelEstaciones = new JPanel();
		panelEstaciones.setLayout(new GridBagLayout());
		panelEstaciones.setBackground(Color.WHITE);
		panelEstaciones.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(10,20,20,20);
		cons.anchor = GridBagConstraints.CENTER;
		add(panelEstaciones,cons);
		
		Map<Estacion, LocalDateTime> ultimosMantenimientos = PostgreDB.recuperarUltimosMantenimientos();
		
		PriorityQueue<Estacion> monticulo = new PriorityQueue<Estacion>(10,(e1,e2) -> ultimosMantenimientos.get(e1).compareTo(ultimosMantenimientos.get(e2)));
		
		for(Estacion e : ultimosMantenimientos.keySet()) monticulo.offer(e);
		
		modelo = new ModeloTablaProxMantenimiento();
		tabla = new TablaJ(modelo);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(10);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(300);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(250);
		scroll = new JScrollPane(tabla);
		
		cons.gridy = 0;
		panelEstaciones.add(scroll,cons);
		
		Estacion masUrgente = monticulo.peek();
		if(ultimosMantenimientos.get(masUrgente).equals(LocalDateTime.MIN)) estacion.setText(masUrgente.getNombre()+"   -   último mantenimiento: Nunca");
		else estacion.setText(masUrgente.getNombre()+" - último mantenimiento: "+ultimosMantenimientos.get(masUrgente).format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		while(!monticulo.isEmpty()) {
			Vector<Object> fila = new Vector<Object>();
			Estacion prox = monticulo.poll();
			
			fila.add(prox.getId());
			fila.add(prox.getNombre());
			if(ultimosMantenimientos.get(prox).equals(LocalDateTime.MIN)) fila.add("Nunca");
			else fila.add(ultimosMantenimientos.get(prox).format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
			data.add(fila);
		}
		
		modelo.setData(data);
		modelo.fireTableDataChanged();
	}
	
}
