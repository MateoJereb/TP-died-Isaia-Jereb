package componentes_swing.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import clase_app.App;
import clase_grafo.SistemaTransporte;
import clases.*;
import componentes_swing.*;
import componentes_swing.modelos_tablas.ModeloTablaPageRank;
import postgre_db.PostgreDB;

public class PageRank extends JPanel {

	//PanelResultados
	private JPanel panelResultados;
	private TablaJ tabla;
	private ModeloTablaPageRank modelo;
	//Volver	
	private BotonJ volver;
	//Calculo PageRank
	private static final double d = 0.5; //Factor de amortiguacion
	private static final double eCorte = 0.00000001; // Valor de corte
	
	public PageRank() {
		super();
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		panelResultados();
		
		volver = new BotonJ("Volver atrás");
		
		volver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.infoEstaciones();
			}			
		});
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.gridy=1;
		cons.gridx=0;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(0,20,10,20);
		add(volver,cons);
		
	}
	
	private void panelResultados() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Page Rank");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
	
		panelResultados = new JPanel();
		panelResultados.setLayout(new GridBagLayout());
		panelResultados.setBackground(Color.WHITE);
		panelResultados.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(10,20,20,20);
		cons.anchor = GridBagConstraints.CENTER;
		
		add(panelResultados,cons);
		
		modelo = new ModeloTablaPageRank();
		tabla = new TablaJ(modelo);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(10);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(300);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(250);
		
		JScrollPane scroll = new JScrollPane(tabla);
		cons.gridy = 0;
		panelResultados.add(scroll,cons);
		
		Map<Estacion,Double> listaPR = calcularPR();
		Set<Estacion> estaciones = listaPR.keySet();
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		for(Estacion e :  estaciones) {
			Vector<Object> fila = new Vector<Object>();
			fila.add(e.getId());
			fila.add(e.getNombre());
			fila.add(listaPR.get(e));
			data.add(fila);
		}
		
		data.sort((e1,e2) -> ((Double) e2.get(2)).compareTo((Double) e1.get(2)));
		
		modelo.setData(data);
		modelo.fireTableDataChanged();
	}
	
	private Map<Estacion,Double> calcularPR() {
		SistemaTransporte grafo = SistemaTransporte.getInstancia();
		grafo.recuperarEstaciones();
		grafo.recuperarRutas();
		
		Vector<Estacion> estaciones = grafo.getEstaciones();
		Vector<Ruta> rutas = grafo.getRutas();
		
		Map<Estacion,Vector<Estacion>> adyacentesEntrantes = new LinkedHashMap<Estacion,Vector<Estacion>>();
		Map<Estacion,Double> pageRank = new LinkedHashMap<Estacion,Double>();
		Map<Estacion,Integer> gradoSalida = new LinkedHashMap<Estacion,Integer>();
		
		estaciones.stream().forEach(e -> {
			adyacentesEntrantes.put(e, grafo.getAdyacentesEntrantes(e));
			pageRank.put(e, 1.0); //Inicializar en 1 todos los PR
			gradoSalida.put(e,grafo.gradoSalida(e));
		});
		
		Map<Estacion,Double> nuevoPR = prIterativo(pageRank,adyacentesEntrantes,gradoSalida);
		
		while(!cortarIteracion(nuevoPR,pageRank)) {
			
			for(Estacion e : estaciones) {
				pageRank.replace(e, nuevoPR.get(e));
			}
			
			 nuevoPR = prIterativo(pageRank,adyacentesEntrantes,gradoSalida);
		}
		
		for(Estacion e : estaciones) {
			pageRank.replace(e, nuevoPR.get(e)); //Ultimo cambio de valores
		}
		
		return pageRank;
	}
	
	private Map<Estacion,Double> prIterativo(Map<Estacion,Double> prViejo, Map<Estacion,Vector<Estacion>> adyacentesEntrantes, Map<Estacion,Integer> gradoSalida){
		Map<Estacion,Double> nuevoPR = new LinkedHashMap<Estacion,Double>();
		Set<Estacion> estaciones = prViejo.keySet();
		
		estaciones.stream().forEach(e -> {
			nuevoPR.put(e, 1-d);
			
			adyacentesEntrantes.get(e).stream().forEach(eEntrante -> {
				Double sumaActual = nuevoPR.get(e);
				Double prEntrante = prViejo.get(eEntrante);
				Integer gradoSalidaEntrante = gradoSalida.get(eEntrante);
				
				nuevoPR.replace(e, sumaActual+d*prEntrante/gradoSalidaEntrante);
				
			});
			
		});
		
		return nuevoPR;
	}
	
	private Boolean cortarIteracion(Map<Estacion,Double> nuevo, Map<Estacion,Double> viejo) {
		Set<Estacion> estaciones = nuevo.keySet();
		
		for(Estacion e : estaciones) {
			if(Math.abs(nuevo.get(e)-viejo.get(e)) > eCorte) {
				return false;
			}
		}
		
		return true;
	}
}
