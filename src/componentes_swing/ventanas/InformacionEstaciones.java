package componentes_swing.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import clase_app.App;
import componentes_swing.*;

public class InformacionEstaciones extends JPanel {

	//PanelBotones
	private JPanel panelBotones;
	private BotonJ flujoMaximo;
	private BotonJ pageRank;
	private BotonJ proxMantenimiento;
	//PanelInfo
	private JPanel panelInfo;
	PanelTextoJ descripcion;
	//Volver
	private BotonJ volver;
	
	public InformacionEstaciones() {
		super();
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		panelBotones();
		panelInfo();
		
		volver = new BotonJ("Volver atrás");
		
		volver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.menuPrincipal();
			}			
		});
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.gridy=1;
		cons.gridx=1;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(0,20,10,20);
		add(volver,cons);
	}
	
	private void panelBotones() {
		TitledBorder borde  = BorderFactory.createTitledBorder("Información Estaciones");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,17));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		
		panelBotones = new JPanel();
		panelBotones.setLayout(new GridBagLayout());
		panelBotones.setBackground(Color.WHITE);
		panelBotones.setBorder(borde);
		
		GridBagConstraints cons = new GridBagConstraints();
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(20,20,20,20);
		add(panelBotones,cons);
		
		flujoMaximo = new BotonJ("Flujo Máximo");
		pageRank = new BotonJ("Page Rank");
		proxMantenimiento = new BotonJ("Próximo Mantenimiento");
		
	
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(20,20,20,20);
		flujoMaximo.setPreferredSize(proxMantenimiento.getPreferredSize());
		panelBotones.add(flujoMaximo,cons);
		
		cons.gridy = 1;
		pageRank.setPreferredSize(proxMantenimiento.getPreferredSize());
		panelBotones.add(pageRank,cons);
		
		cons.gridy = 2;
		panelBotones.add(proxMantenimiento,cons);
		
		flujoMaximo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				descripcion.setText(textoFlujo);
				descripcion.setDisabledTextColor(Color.BLACK);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				descripcion.setText("Sitúe el mouse sobre un botón.");
				descripcion.setDisabledTextColor(Color.GRAY);
			}
		});
		
		pageRank.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				descripcion.setText(textoPR);
				descripcion.setDisabledTextColor(Color.BLACK);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				descripcion.setText("Sitúe el mouse sobre un botón.");
				descripcion.setDisabledTextColor(Color.GRAY);
			}
		});
		
		proxMantenimiento.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				descripcion.setText(textoMant);
				descripcion.setDisabledTextColor(Color.BLACK);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				descripcion.setText("Sitúe el mouse sobre un botón.");
				descripcion.setDisabledTextColor(Color.GRAY);
			}
		});
		
		flujoMaximo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.flujoMaximo();				
			}			
		});
		
		pageRank.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.pageRank();				
			}			
		});
		
		proxMantenimiento.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.proximoMantenimiento();		
			}			
		});
				
	}
	
	private void panelInfo() {
			
		TitledBorder borde  = BorderFactory.createTitledBorder("Descripción");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,17));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		
		panelInfo = new JPanel();
		panelInfo.setLayout(new GridBagLayout());
		panelInfo.setBackground(Color.WHITE);
		panelInfo.setBorder(borde);
		
		GridBagConstraints cons = new GridBagConstraints();
		
		cons.gridx = 1;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(20,20,20,20);
		add(panelInfo,cons);
		
		descripcion = new PanelTextoJ();
		descripcion.setText("Sitúe el mouse sobre un botón.");
		descripcion.setDisabledTextColor(Color.GRAY);
		descripcion.setEnabled(false);
		descripcion.setBorder(null);
		
		cons.gridx=0;
		panelInfo.add(descripcion,cons);
		
	}
	
	private	String textoFlujo = "El algoritmo de Flujo Máximo se utiliza para reducir embotellamientos entre ciertos"
			+ " puntos de partida y destino de una red. Existe un flujo que viaja desde un único lugar de origen (estacion fuente) hacia"
			+ " un único lugar de destino (estacion sumidero) a través de arcos que conectan nodos intermedios y que tienen cierta capacidad"
			+ " que no puede ser excedida.\n\n"
			+ ""
			+ "Se muestra la cantidad máxima de pasajeros que pueden arribar a cada una de las estaciones mediante"
			+ " cualquier camino, partiendo de una estación seleccionada.";
	
	private String textoPR = "El algoritmo Page Rank es utilizado para establecer la importancia de cada sitio web"
				+ " en función de la cantidad de sitios con un enlace a él y de la importancia relativa de los mismos.\n\n"
				+ ""
				+ "Se muestran todas las estaciones ordenadas según su importancia relativa calculada mediante el"
				+ " algoritmo de Page Rank.";
		
	
	private String textoMant = "Se consulta el último mantenimiento realizado a cada una de las estaciones y a partir"
			+ " de los datos obtenidos se sugiere realizar un mantenimiento sobre la que más tiempo lleve sin recibir uno.";
		
}
