package componentes_swing.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import clase_app.App;
import clases.*;
import componentes_swing.*;
import componentes_swing.modelos_tablas.ModeloTablaCaminos;

public class SeleccionarOtroCamino extends JPanel {

	private Vector<Vector<Ruta>> listaCaminos;
	private Map<Vector<Ruta>,Double> distancias;
	private Map<Vector<Ruta>,Integer> duraciones;
	private Map<Vector<Ruta>,Double> costos;
	//PanelSeleccion
	private JPanel panelSeleccion;
	private JScrollPane scroll;
	private TablaJ tabla;
	private ModeloTablaCaminos modelo;
	//PanelCamino
	private JPanel panelCamino;
	private JPanel panelTexto;
	private EtiquetaJ distancia;
	private EtiquetaJ duracion;
	private EtiquetaJ costo;
	private PanelDibujoCamino dibujo;
	//PanelBotones
	private JPanel panelBotones;
	private EtiquetaJ noSelecciono;
	private BotonJ aceptar;
	private BotonJ volver;
	
	public SeleccionarOtroCamino(Vector<Vector<Ruta>> listaCaminos) {
		super();
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		this.listaCaminos = listaCaminos;
		distancias = new LinkedHashMap<Vector<Ruta>,Double>();
		duraciones = new LinkedHashMap<Vector<Ruta>,Integer>();
		costos = new LinkedHashMap<Vector<Ruta>,Double>();
		
		panelSeleccion();
		panelCamino();
		panelBotones();
	}
	
	private void panelSeleccion() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Otros caminos");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
	
		panelSeleccion = new JPanel();
		panelSeleccion.setLayout(new GridBagLayout());
		panelSeleccion.setBackground(Color.WHITE);
		panelSeleccion.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(10,20,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		add(panelSeleccion,cons);
		
		modelo = new ModeloTablaCaminos();
		tabla = new TablaJ(modelo);
		scroll = new JScrollPane(tabla);
		
		tabla.getColumnModel().getColumn(0).setPreferredWidth(500);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(20);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(20);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(13);
		
		cons.gridy = 0;
		panelSeleccion.add(scroll,cons);
		
		DecimalFormat df = new DecimalFormat("###.00");
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		for(Vector<Ruta> c : listaCaminos) {
			Double distancia = c.stream().map(r -> r.getDistancia()).reduce(0.0, (d1,d2) -> d1+d2);
			Integer duracion = c.stream().map(r -> r.getDuracion()).reduce(0, (d1,d2) -> d1+d2);
			Double costo = c.stream().map(r -> r.getCosto()).reduce(0.0, (c1,c2) -> c1+c2);
			
			distancias.put(c, distancia);
			duraciones.put(c, duracion);
			costos.put(c, costo);
			
			Vector<Object> fila = new Vector<Object>();
			fila.add(c);
			fila.add(df.format(distancias.get(c)));
			fila.add(duraciones.get(c));
			fila.add(df.format(costos.get(c)));
			data.add(fila);
		}
		
		modelo.setData(data);
		modelo.fireTableDataChanged();
		
		tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				actualizarCamino();
			}
		});
	}
	
	private void panelCamino() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Recorrido");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
	
		panelCamino = new JPanel();
		panelCamino.setLayout(new GridBagLayout());
		panelCamino.setBackground(Color.WHITE);
		panelCamino.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(10,20,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		add(panelCamino,cons);
		
		panelTexto = new JPanel(new GridBagLayout());
		panelTexto.setBackground(Color.WHITE);
		distancia = new EtiquetaJ("Distancia: 0.0 km");
		duracion = new EtiquetaJ("Duración: 0 min");
		costo = new EtiquetaJ("Costo: $0.0");
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(0,0,5,0);
		panelCamino.add(panelTexto,cons);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(0,10,5,0);
		cons.anchor = GridBagConstraints.WEST;
		panelTexto.add(distancia,cons);
		
		cons.gridy = 1;
		cons.insets = new Insets(5,10,5,0);
		panelTexto.add(duracion,cons);
		
		cons.gridy = 2;
		cons.insets = new Insets(5,10,0,0);
		panelTexto.add(costo,cons);
		
		dibujo = new PanelDibujoCamino(new Vector<Ruta>());
		
		cons.gridx = 1;
		cons.gridy = 0;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.gridheight = 1;
		cons.fill = GridBagConstraints.BOTH;
		cons.anchor = GridBagConstraints.CENTER;
		cons.insets = new Insets(5,5,5,5);
		panelCamino.add(dibujo,cons);
	}
	
	private void panelBotones() {
		GridBagConstraints cons = new GridBagConstraints();
		panelBotones = new JPanel();
		panelBotones.setLayout(new GridBagLayout());
		panelBotones.setBackground(UIManager.getColor("CheckBox.focus"));
		
		cons.gridx = 0;
		cons.gridy = 3;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(0,20,0,20);
		cons.anchor = GridBagConstraints.EAST;
		add(panelBotones,cons);
		
		noSelecciono = new EtiquetaJ("Debe seleccionar un camino de la tabla");
		aceptar = new BotonJ("Aceptar");
		volver = new BotonJ("Volver atrás");
		
		cons.gridy=0;
		cons.gridx = 0;
		cons.insets = new Insets(0,10,5,10);
		noSelecciono.setForeground(Color.RED);
		noSelecciono.setVisible(false);
		panelBotones.add(noSelecciono,cons);
		
		cons.gridx = 1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelBotones.add(aceptar,cons);
		
		cons.gridx = 2;
		cons.insets = new Insets(0,10,0,0);
		panelBotones.add(volver,cons);
		
		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer filaSeleccionada = tabla.getSelectedRow();
				if(filaSeleccionada != -1) {
					noSelecciono.setVisible(false);
					Vector<Ruta> seleccionado = (Vector<Ruta>)modelo.getValueAt(filaSeleccionada, 0);
					App.finalizarVenta(seleccionado);
				}
				else {
					noSelecciono.setVisible(true);
				}
			}			
		});
	}
	
	public BotonJ getVolver() {
		return volver;
	}
	
	public void actualizarCamino() {
		DecimalFormat df = new DecimalFormat("###.00");
		Integer filaSeleccionada = tabla.getSelectedRow();
		
		if(filaSeleccionada != -1) {
			Vector<Ruta> seleccionado = (Vector<Ruta>) modelo.getValueAt(filaSeleccionada, 0);
			distancia.setText("Distancia: "+df.format(distancias.get(seleccionado))+" km");
			duracion.setText("Duración: "+duraciones.get(seleccionado)+" min");
			costo.setText("Costo: $"+df.format(costos.get(seleccionado)));
			
			dibujo.actualizarCamino(seleccionado);
			
		}
		else {
			distancia.setText("Distancia: 0.0 km");
			duracion.setText("Duración: 0 min");
			costo.setText("Costo: $0.0");
			
			dibujo.actualizarCamino(new Vector<Ruta>());
		}
	}
}
