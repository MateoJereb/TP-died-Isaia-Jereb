package componentes_swing.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import clase_app.App;
import clase_grafo.*;
import clases.*;
import componentes_swing.*;
import componentes_swing.modelos_tablas.ModeloTablaFlujoMaximo;
import componentes_swing.modelos_tablas.ModeloTablaPageRank;

public class FlujoMaximo extends JPanel {

	private Vector<Estacion> estaciones;
	private Vector<Ruta> rutas;
	private BotonJ volver;
	//PanelSelecion
	private JPanel panelSeleccion;
	private EtiquetaJ eEstacion;
	private ListaEstaciones lEstacion;
	private BotonJ calcularFlujo;
	//PanelResultados
	private JPanel panelResultados;
	private TablaJ tabla;
	private ModeloTablaFlujoMaximo modelo;
	
	public FlujoMaximo() {
		super();
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		SistemaTransporte sistema = SistemaTransporte.getInstancia();
		sistema.recuperarEstaciones();
		sistema.recuperarRutas();
		estaciones = sistema.getEstaciones();
		rutas = sistema.getRutas();
		
		panelSeleccion();
		panelResultados();
		
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
	
	private void panelSeleccion() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Estación origen");
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
		cons.weighty = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(20,20,10,20);
		cons.anchor = GridBagConstraints.NORTH;
		add(panelSeleccion,cons);
		
		eEstacion = new EtiquetaJ("Estación origen");
		lEstacion = new ListaEstaciones(false);
		calcularFlujo = new BotonJ("Calcular flujo");
		
		cons.anchor = GridBagConstraints.CENTER;
		cons.weighty = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelSeleccion.add(eEstacion,cons);
		
		cons.gridx = 1;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelSeleccion.add(lEstacion,cons);
		
		cons.gridx = 2;
		cons.gridy = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,0,10,20);
		panelSeleccion.add(calcularFlujo,cons);
		
		calcularFlujo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarTabla();
			}			
		});
		
	}
	
	private void panelResultados() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Estaciones destino");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
	
		panelResultados = new JPanel();
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
		
		modelo = new ModeloTablaFlujoMaximo();
		tabla = new TablaJ(modelo);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(10);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(300);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(250);
		
		JScrollPane scroll = new JScrollPane(tabla);
		cons.gridy = 0;
		panelResultados.add(scroll,cons);
	}
	
	private void actualizarTabla() {
		SistemaTransporte sistema = SistemaTransporte.getInstancia();
		Estacion origen = (Estacion) lEstacion.getSelectedItem();
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		//Se quitan las rutas que ingresan a la estacion de inicio, ya que debe ser fuente
		Vector<Ruta> subrutas = rutas.stream().filter(r -> !r.getDestino().equals(origen)).collect(Collectors.toCollection(Vector::new));
		if(origen.getId() != -1) {
			for(Estacion e : estaciones) {
				if(!origen.equals(e)) {
					Integer flujo = calcularFlujo(origen, e, subrutas, estaciones);
					Vector<Object> fila = new Vector<Object>();
					fila.add(e.getId());
					fila.add(e.getNombre());
					fila.add(flujo);
					data.add(fila);
				}
			}
		}
		
		modelo.setData(data);
		modelo.fireTableDataChanged();
	}
	
	private Integer calcularFlujo(Estacion origen, Estacion destino, Vector<Ruta> rutasAntes, Vector<Estacion> estaciones) {
		
		Integer rtdo = 0;
		
		//Se quitan las rutas que salen de la estacion destino, ya que debe ser sumidero
		Vector<Ruta> rutas = rutasAntes.stream().filter(r -> !r.getOrigen().equals(destino)).collect(Collectors.toCollection(Vector::new));
		
		Map<Ruta,Integer> capRestante = new LinkedHashMap<Ruta,Integer>();
		for(Ruta r : rutas) capRestante.put(r, r.getCapacidad());
		
		Vector<Ruta> rutasCamino = buscarCamino(origen,destino,rutas,estaciones,capRestante);
		
		while(!rutasCamino.isEmpty()) {
			Integer minCapacidad = rutasCamino.stream().map(r -> capRestante.get(r)).min((c1,c2) -> c1.compareTo(c2)).get();
			
			for(Ruta r : rutasCamino) capRestante.replace(r, capRestante.get(r)-minCapacidad);
			
			rtdo+=minCapacidad;
	
			for(Ruta r : capRestante.keySet()) {
				if(capRestante.get(r) == 0) rutas.remove(r);
			}
	
			rutasCamino = buscarCamino(origen,destino,rutas,estaciones,capRestante);
		}
		
		return rtdo;
	}
	
	private Vector<Ruta> buscarCamino(Estacion origen, Estacion destino, Vector<Ruta> rutas, Vector<Estacion> estaciones, Map<Ruta,Integer> capacidad){
		Vector<Ruta> salida = new Vector<Ruta>();
		
		Graph<Integer> grafo = new Graph<Integer>();
		
		estaciones.stream().forEach(e -> grafo.addNodo(e.getId()));
		for(Ruta r : rutas) {
			Double cap = Double.parseDouble(String.valueOf(capacidad.get(r)));
			grafo.conectar(r.getOrigen().getId(), r.getDestino().getId(), r.getId(), cap);
		}
		
		//Para encontrar el camino, reutilizamos el algoritmo de Dijkstra utilizado para encontrar el camino mas corto/rapido/barato, pero repitiendolo hasta que no encuentre ningun camino
		Vector<Edge<Integer>> camino = grafo.caminoDijkstra(origen.getId(),destino.getId());
		
		SistemaTransporte sistema = SistemaTransporte.getInstancia();
		camino.stream().forEach(x -> salida.add(sistema.getRuta(x.getId()).get()));
		
		return salida;
	}
}
