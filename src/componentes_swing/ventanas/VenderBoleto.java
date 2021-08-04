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
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import clase_app.App;
import clase_grafo.*;
import clases.*;
import componentes_swing.*;

public class VenderBoleto extends JPanel {

	private Vector<Estacion> estaciones;
	private Vector<Ruta> rutas;
	private Vector<Vector<Ruta>> listaCaminos;
	private Map<Vector<Ruta>,Double> distancias;
	private Map<Vector<Ruta>,Integer> duraciones;
	private Map<Vector<Ruta>,Double> costos;
	private Vector<Ruta> caminoRapido;
	private Vector<Ruta> caminoCorto;
	private Vector<Ruta> caminoBarato;
	//PanelEstaciones
	private JPanel panelEstaciones;
	private EtiquetaJ eOrigen;
	private ListaEstaciones lOrigen;
	private EtiquetaJ eDestino;
	private ListaEstaciones lDestino;
	private BotonJ buscar;
	//PanelMasRapido
	private JPanel panelMasRapido;
	private JPanel rapidoPanelTexto;
	private EtiquetaJ rapidoDistancia;
	private EtiquetaJ rapidoDuracion;
	private EtiquetaJ rapidoCosto;
	private PanelDibujoCamino rapidoDibujo;
	//PanelMenorDistancia
	private JPanel panelMenorDistancia;
	private JPanel cortoPanelTexto;
	private EtiquetaJ cortoDistancia;
	private EtiquetaJ cortoDuracion;
	private EtiquetaJ cortoCosto;
	private PanelDibujoCamino cortoDibujo;
	//PanelMasBarato
	private JPanel panelMasBarato;
	private JPanel baratoPanelTexto;
	private EtiquetaJ baratoDistancia;
	private EtiquetaJ baratoDuracion;
	private EtiquetaJ baratoCosto;
	private PanelDibujoCamino baratoDibujo;
	//PanelBotones
	private JPanel panelBotones;
	private EtiquetaJ noSeleccionados;
	private BotonJ masRapido;
	private BotonJ menorDistancia;
	private BotonJ masBarato;
	private BotonJ otro;
	private BotonJ volver;
	
	public VenderBoleto() {
		super();
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		SistemaTransporte sistema = SistemaTransporte.getInstancia();
		sistema.recuperarEstaciones();
		sistema.recuperarRutas();
		estaciones = sistema.getEstaciones().stream().filter(e -> e.operativa()).collect(Collectors.toCollection(Vector::new));
		rutas = sistema.getRutas().stream().filter(r -> r.getLinea().activa() && r.activa() && r.getOrigen().operativa() && r.getDestino().operativa()).collect(Collectors.toCollection(Vector::new));
		
		distancias = new LinkedHashMap<Vector<Ruta>,Double>();
		duraciones = new LinkedHashMap<Vector<Ruta>,Integer>();
		costos = new LinkedHashMap<Vector<Ruta>,Double>();
		
		listaCaminos = new Vector<Vector<Ruta>>();
		caminoRapido = new Vector<Ruta>();
		caminoCorto = new Vector<Ruta>();
		caminoBarato = new Vector<Ruta>();
		
		panelEstaciones();
		panelMasRapido();
		panelMenorDistancia();
		panelMasBarato();
		panelBotones();
	}
	
	private void panelEstaciones() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Origen y destino");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
	
		panelEstaciones = new JPanel();
		panelEstaciones.setLayout(new GridBagLayout());
		panelEstaciones.setBackground(Color.WHITE);
		panelEstaciones.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(10,20,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		add(panelEstaciones,cons);
		
		eOrigen = new EtiquetaJ("Origen");
		lOrigen = new ListaEstaciones(true);
		eDestino = new EtiquetaJ("Destino");
		lDestino = new ListaEstaciones(true);
		buscar = new BotonJ("Buscar caminos");
		
		cons.weighty = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelEstaciones.add(eOrigen,cons);
		
		cons.gridx = 1;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelEstaciones.add(lOrigen,cons);
		
		cons.gridx = 2;
		cons.weighty = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,0,10,5);
		panelEstaciones.add(eDestino,cons);
		
		cons.gridx = 3;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelEstaciones.add(lDestino,cons);
		
		cons.gridx = 4;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,10,10,20);
		panelEstaciones.add(buscar,cons);
		
		lOrigen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					lOrigen.setBackground(UIManager.getColor("ComboBox.background"));
					Vector<Estacion> aux = new Vector<Estacion>();				
					Estacion origSeleccionada = (Estacion) lOrigen.getSelectedItem();
					Estacion destSeleccionada = (Estacion)lDestino.getSelectedItem();
					LocalTime a = null, c = null;
					aux.add(new Estacion(-1,"",a,c,true));
					aux.addAll(estaciones);
					lDestino.removeAllItems();
					aux.remove(origSeleccionada);
					
					for(Estacion e1 : aux) {
						lDestino.addItem(e1);
					}
				
					lDestino.setSelectedItem(destSeleccionada);
				}
		});
		
		lDestino.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					lDestino.setBackground(UIManager.getColor("ComboBox.background"));
					Vector<Estacion> aux = new Vector<Estacion>();
					Estacion origSeleccionada = (Estacion) lOrigen.getSelectedItem();
					Estacion destSeleccionada = (Estacion)lDestino.getSelectedItem();
					LocalTime a = null, c = null;
					aux.add(new Estacion(-1,"",a,c,true));
					aux.addAll(estaciones);
					lOrigen.removeAllItems();
					aux.remove(destSeleccionada);
					
					for(Estacion e1 : aux) {
						lOrigen.addItem(e1);
					}
				
					lOrigen.setSelectedItem(origSeleccionada);
			}
		});
		
		buscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarCaminos();
				actualizarPaneles();
			}			
		});
		
	}

	private void panelMasRapido() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Camino más rápido");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
	
		panelMasRapido = new JPanel();
		panelMasRapido.setLayout(new GridBagLayout());
		panelMasRapido.setBackground(Color.WHITE);
		panelMasRapido.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(10,20,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		add(panelMasRapido,cons);
		
		rapidoPanelTexto = new JPanel(new GridBagLayout());
		rapidoPanelTexto.setBackground(Color.WHITE);
		rapidoDistancia = new EtiquetaJ("Distancia: 0.0 km");
		rapidoDuracion = new EtiquetaJ("Duración: 0 min");
		rapidoCosto = new EtiquetaJ("Costo: $0.0");
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(0,0,5,0);
		panelMasRapido.add(rapidoPanelTexto,cons);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(0,10,5,0);
		cons.anchor = GridBagConstraints.WEST;
		rapidoPanelTexto.add(rapidoDistancia,cons);
		
		cons.gridy = 1;
		cons.insets = new Insets(5,10,5,0);
		rapidoPanelTexto.add(rapidoDuracion,cons);
		
		cons.gridy = 2;
		cons.insets = new Insets(5,10,0,0);
		rapidoPanelTexto.add(rapidoCosto,cons);
		
		rapidoDibujo = new PanelDibujoCamino(new Vector<Ruta>());
		
		cons.gridx = 1;
		cons.gridy = 0;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.gridheight = 1;
		cons.fill = GridBagConstraints.BOTH;
		cons.anchor = GridBagConstraints.CENTER;
		cons.insets = new Insets(5,5,5,5);
		panelMasRapido.add(rapidoDibujo,cons);
	}
	
	private void panelMenorDistancia() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Camino de menor distancia");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
	
		panelMenorDistancia = new JPanel();
		panelMenorDistancia.setLayout(new GridBagLayout());
		panelMenorDistancia.setBackground(Color.WHITE);
		panelMenorDistancia.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 2;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(10,20,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		add(panelMenorDistancia,cons);
		
		cortoPanelTexto = new JPanel(new GridBagLayout());
		cortoPanelTexto.setBackground(Color.WHITE);
		cortoDistancia = new EtiquetaJ("Distancia: 0.0 km");
		cortoDuracion = new EtiquetaJ("Duración: 0 min");
		cortoCosto = new EtiquetaJ("Costo: $0.0");
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(0,0,5,0);
		panelMenorDistancia.add(cortoPanelTexto,cons);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(5,10,15,0);
		cons.anchor = GridBagConstraints.WEST;
		cortoPanelTexto.add(cortoDistancia,cons);
		
		cons.gridy = 1;
		cons.insets = new Insets(0,10,10,0);
		cortoPanelTexto.add(cortoDuracion,cons);
		
		cons.gridy = 2;
		cons.insets = new Insets(0,10,5,0);
		cortoPanelTexto.add(cortoCosto,cons);
		
		cortoDibujo = new PanelDibujoCamino(new Vector<Ruta>());
		
		cons.gridx = 1;
		cons.gridy = 0;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.gridheight = 1;
		cons.fill = GridBagConstraints.BOTH;
		cons.anchor = GridBagConstraints.CENTER;
		cons.insets = new Insets(5,5,5,5);
		panelMenorDistancia.add(cortoDibujo,cons);

		
	}
	
	private void panelMasBarato() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Camino más barato");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
	
		panelMasBarato = new JPanel();
		panelMasBarato.setLayout(new GridBagLayout());
		panelMasBarato.setBackground(Color.WHITE);
		panelMasBarato.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 3;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(10,20,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		add(panelMasBarato,cons);
		
		baratoPanelTexto = new JPanel(new GridBagLayout());
		baratoPanelTexto.setBackground(Color.WHITE);
		baratoDistancia = new EtiquetaJ("Distancia: 0.0 km");
		baratoDuracion = new EtiquetaJ("Duración: 0 min");
		baratoCosto = new EtiquetaJ("Costo: $0.0");
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(0,0,5,0);
		panelMasBarato.add(baratoPanelTexto,cons);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(0,10,5,0);
		cons.anchor = GridBagConstraints.WEST;
		baratoPanelTexto.add(baratoDistancia,cons);
		
		cons.gridy = 1;
		cons.insets = new Insets(5,10,5,0);
		baratoPanelTexto.add(baratoDuracion,cons);
		
		cons.gridy = 2;
		cons.insets = new Insets(5,10,0,0);
		baratoPanelTexto.add(baratoCosto,cons);
		
		baratoDibujo = new PanelDibujoCamino(new Vector<Ruta>());
		
		cons.gridx = 1;
		cons.gridy = 0;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.gridheight = 1;
		cons.fill = GridBagConstraints.BOTH;
		cons.anchor = GridBagConstraints.CENTER;
		cons.insets = new Insets(5,5,5,5);
		
		panelMasBarato.add(baratoDibujo,cons);
	}
	
	private void panelBotones(){
		GridBagConstraints cons = new GridBagConstraints();
		panelBotones = new JPanel();
		panelBotones = new JPanel();
		panelBotones.setLayout(new GridBagLayout());
		panelBotones.setBackground(UIManager.getColor("CheckBox.focus"));
		
		cons.gridx = 0;
		cons.gridy = 4;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(0,20,0,20);
		cons.anchor = GridBagConstraints.EAST;
		add(panelBotones,cons);
		
		noSeleccionados = new EtiquetaJ("Debe seleccionar un origen y un destino");
		masRapido = new BotonJ("Más rápido");
		menorDistancia = new BotonJ("Menor distancia");
		masBarato = new BotonJ("Más barato");
		otro = new BotonJ("Otro camino");
		volver = new BotonJ("Volver atrás");
		
		masRapido.setEnabled(false);
		menorDistancia.setEnabled(false);
		masBarato.setEnabled(false);
		otro.setEnabled(false);
		
		cons.gridy=0;
		cons.gridx = 0;
		cons.insets = new Insets(0,10,5,10);
		noSeleccionados.setForeground(Color.RED);
		noSeleccionados.setVisible(false);
		panelBotones.add(noSeleccionados,cons);
		
		cons.gridx = 1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelBotones.add(masRapido,cons);
		
		cons.gridx = 2;
		panelBotones.add(menorDistancia,cons);
		
		cons.gridx = 3;
		panelBotones.add(masBarato,cons);
		
		cons.gridx = 4;
		panelBotones.add(otro,cons);
		
		cons.gridx = 5;
		cons.insets = new Insets(0,10,5,0);
		panelBotones.add(volver,cons);
		
		masRapido.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.finalizarVenta(caminoRapido);
			}
		});
		
		menorDistancia.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.finalizarVenta(caminoCorto);
			}
		});
		
		masBarato.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.finalizarVenta(caminoBarato);
			}
		});
		
		otro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Vector<Vector<Ruta>> otrosCaminos = new Vector<Vector<Ruta>>(); 
				otrosCaminos.addAll(listaCaminos.stream().filter(c -> c!=caminoRapido && c!=caminoCorto && c!=caminoBarato).collect(Collectors.toCollection(Vector::new)));
				
				if(otrosCaminos.size() > 0) {
					App.seleccionarOtroCamino(otrosCaminos);
				}
				else {
					JDialog ventana = new JDialog(App.getVentana(),"Vender Boleto");
					JPanel noHayCamino = new JPanel(new GridBagLayout());
					noHayCamino.setBackground(UIManager.getColor("CheckBox.focus"));
					ventana.setContentPane(noHayCamino);
					
					GridBagConstraints cons2 = new GridBagConstraints();
					EtiquetaJ texto = new EtiquetaJ("No existen otros caminos de '"+((Estacion)lOrigen.getSelectedItem()).getNombre()+"' a '"+((Estacion)lDestino.getSelectedItem()).getNombre()+"'");
					BotonJ aceptar = new BotonJ("Aceptar");
					
					cons2.gridx = 0;
					cons2.gridy = 0;
					cons2.gridwidth = 1;
					cons2.gridheight = 1;
					cons2.weightx = 0.1;
					cons2.fill = GridBagConstraints.HORIZONTAL;
					cons2.insets = new Insets(10,10,10,10);
					noHayCamino.add(texto,cons2);
					
					cons2.gridy = 1;
					cons2.fill = GridBagConstraints.NONE;
					noHayCamino.add(aceptar,cons2);
					
					App.getVentana().setEnabled(false);
					ventana.pack();
					ventana.setLocationRelativeTo(null);
					ventana.setVisible(true);
					
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
	
	private void actualizarPaneles() {
		DecimalFormat df = new DecimalFormat("###.00");
		
		if(listaCaminos.size() > 0) {
			for(Vector<Ruta> camino : listaCaminos) {
				Double distancia = camino.stream().map(r -> r.getDistancia()).reduce(0.0, (d1,d2) -> d1+d2);
				Integer duracion = camino.stream().map(r -> r.getDuracion()).reduce(0, (d1,d2) -> d1+d2);
				Double costo = camino.stream().map(r -> r.getCosto()).reduce(0.0, (c1,c2) -> c1+c2);
				
				if(distancias.containsKey(camino)) {
					distancias.replace(camino, distancia);
				}
				else {
					distancias.put(camino, distancia);
				}
				
				if(duraciones.containsKey(camino)) {
					duraciones.replace(camino, duracion);
				}
				else {
					duraciones.put(camino, duracion);
				}
				
				if(costos.containsKey(camino)) {
					costos.replace(camino, costo);
				}
				else {
					costos.put(camino, costo);
				}
			}
			
			caminoRapido = listaCaminos.stream().min((c1,c2) -> duraciones.get(c1).compareTo(duraciones.get(c2))).get();
			caminoCorto = listaCaminos.stream().min((c1,c2) -> distancias.get(c1).compareTo(distancias.get(c2))).get();
			caminoBarato = listaCaminos.stream().min((c1,c2) -> costos.get(c1).compareTo(costos.get(c2))).get();
			
			rapidoDistancia.setText("Distancia: "+df.format(distancias.get(caminoRapido))+" km");
			rapidoDuracion.setText("Duración: "+duraciones.get(caminoRapido)+" min");
			rapidoCosto.setText("Costo: $"+df.format(costos.get(caminoRapido)));
			
			cortoDistancia.setText("Distancia: "+df.format(distancias.get(caminoCorto))+" km");
			cortoDuracion.setText("Duración: "+duraciones.get(caminoCorto)+" min");
			cortoCosto.setText("Costo: $"+df.format(costos.get(caminoCorto)));
			
			baratoDistancia.setText("Distancia: "+df.format(distancias.get(caminoBarato))+" km");
			baratoDuracion.setText("Duración: "+duraciones.get(caminoBarato)+" min");
			baratoCosto.setText("Costo: $"+df.format(costos.get(caminoBarato)));
			
			rapidoDibujo.actualizarCamino(caminoRapido);
			cortoDibujo.actualizarCamino(caminoCorto);
			baratoDibujo.actualizarCamino(caminoBarato);
			
			masRapido.setEnabled(true);
			menorDistancia.setEnabled(true);
			masBarato.setEnabled(true);
			otro.setEnabled(true);
		}
		else {
			resetearCaminos();
			
			if(((Estacion) lOrigen.getSelectedItem()).getId() != -1 && ((Estacion) lDestino.getSelectedItem()).getId() != -1) {
				JDialog ventana = new JDialog(App.getVentana(),"Vender Boleto");
				JPanel noHayCamino = new JPanel(new GridBagLayout());
				noHayCamino.setBackground(UIManager.getColor("CheckBox.focus"));
				ventana.setContentPane(noHayCamino);
				
				GridBagConstraints cons2 = new GridBagConstraints();
				EtiquetaJ texto = new EtiquetaJ("No existe ningún camino de '"+((Estacion)lOrigen.getSelectedItem()).getNombre()+"' a '"+((Estacion)lDestino.getSelectedItem()).getNombre()+"'");
				BotonJ aceptar = new BotonJ("Aceptar");
				
				cons2.gridx = 0;
				cons2.gridy = 0;
				cons2.gridwidth = 1;
				cons2.gridheight = 1;
				cons2.weightx = 0.1;
				cons2.fill = GridBagConstraints.HORIZONTAL;
				cons2.insets = new Insets(10,10,10,10);
				noHayCamino.add(texto,cons2);
				
				cons2.gridy = 1;
				cons2.fill = GridBagConstraints.NONE;
				noHayCamino.add(aceptar,cons2);
				
				App.getVentana().setEnabled(false);
				ventana.pack();
				ventana.setLocationRelativeTo(null);
				ventana.setVisible(true);
				
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
			}
			else {
				noSeleccionados.setVisible(true);
			}
		}
	}	
		
	private void buscarCaminos() {
		SistemaTransporte sistema = SistemaTransporte.getInstancia();
		Graph<Integer> grafo = new Graph<Integer>();		
		
		estaciones.stream().forEach(e -> grafo.addNodo(e.getId()));
		for(Ruta r : rutas) {
			grafo.conectar(r.getOrigen().getId(), r.getDestino().getId(), r.getId(), 0.0);
		}
	
		Estacion origen = (Estacion) lOrigen.getSelectedItem();
		Estacion destino = (Estacion) lDestino.getSelectedItem();
		
		if(origen.getId() == -1 || destino.getId() == -1) {
			noSeleccionados.setVisible(true);
			resetearCaminos();
			if(origen.getId() == -1) lOrigen.setBackground(new Color(255,200,200));
			if(destino.getId() == -1) lDestino.setBackground(new Color(255,200,200));
		}
		else {
			noSeleccionados.setVisible(false);
			Vector<Vector<Edge<Integer>>> todosLosCaminos = grafo.caminosRutas(origen.getId(), destino.getId()); //IDs origen y destino
			Vector<Vector<Ruta>> todosLosCaminosRuta = new Vector<Vector<Ruta>>();
		
			for(Vector<Edge<Integer>> camino : todosLosCaminos) {
				Vector<Ruta> caminoRuta;
				caminoRuta = camino.stream().map(edge -> sistema.getRuta(edge.getId()).get()).collect(Collectors.toCollection(Vector::new));
				todosLosCaminosRuta.add(caminoRuta);
			}
			
			listaCaminos = todosLosCaminosRuta.stream().filter(c -> !repiteEstacion(c)).collect(Collectors.toCollection(Vector::new));
		}
	}
	
	private Boolean repiteEstacion(Vector<Ruta> camino) {
		Vector<Estacion> recorridas = new Vector<Estacion>();
		
		for(Ruta r : camino) {
			if(recorridas.contains(r.getOrigen())) return true;
			else recorridas.add(r.getOrigen());
		}
		
		return false;
	}

	private void resetearCaminos() {
		caminoRapido = new Vector<Ruta>();;
		caminoCorto = new Vector<Ruta>();;
		caminoBarato = new Vector<Ruta>();
		listaCaminos = new Vector<Vector<Ruta>>();
		
		rapidoDistancia.setText("Distancia: 0.0 km");
		rapidoDuracion.setText("Duración: 0 min");
		rapidoCosto.setText("Costo: $0.0");
		
		cortoDistancia.setText("Distancia: 0.0 km");
		cortoDuracion.setText("Duración: 0 min");
		cortoCosto.setText("Costo: $0.0");
		
		baratoDistancia.setText("Distancia: 0.0 km");
		baratoDuracion.setText("Duración: 0 min");
		baratoCosto.setText("Costo: $0.0");
		
		rapidoDibujo.actualizarCamino(new Vector<Ruta>());
		cortoDibujo.actualizarCamino(new Vector<Ruta>());
		baratoDibujo.actualizarCamino(new Vector<Ruta>());
		
		masRapido.setEnabled(false);
		menorDistancia.setEnabled(false);
		masBarato.setEnabled(false);
		otro.setEnabled(false);
	}
}
