package componentes_swing.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import clase_app.App;
import componentes_swing.BotonJ;

public class MenuPrincipal extends JPanel {

	public MenuPrincipal() {
		super();
		
		GridBagConstraints cons = new GridBagConstraints();
		
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		TitledBorder borde  = BorderFactory.createTitledBorder("Sistema SGTM");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,19));
		borde.setTitleJustification(TitledBorder.CENTER);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridBagLayout());
		panelBotones.setBackground(Color.WHITE);
		panelBotones.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(20,20,20,20);
		add(panelBotones,cons);
		
		BotonJ venderBoletos = new BotonJ("Vender Boleto");
		BotonJ infoEstaciones = new BotonJ("Información Estaciones");
		BotonJ gestionarEstaciones = new BotonJ("Gestionar Estaciones");
		BotonJ gestionarTrayectos = new BotonJ("Gestionar Trayectos");
		BotonJ gestionarLineas = new BotonJ("Gestionar Lineas");
		
		cons.weighty = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(20,100,20,100);
		
		cons.gridy = 0;
		panelBotones.add(venderBoletos,cons);
		cons.gridy = 1;
		panelBotones.add(infoEstaciones,cons);
		cons.gridy = 2;
		panelBotones.add(gestionarEstaciones,cons);
		cons.gridy = 3;
		panelBotones.add(gestionarLineas,cons);
		cons.gridy = 4;
		panelBotones.add(gestionarTrayectos,cons);
		
		venderBoletos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.venderBoleto();
			}
		});
		
		infoEstaciones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.infoEstaciones();
			}
		});
		
		gestionarEstaciones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.gestionarEstaciones();				
			}
		});
		
		gestionarLineas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.gestionarLineas();				
			}
		});
		
		gestionarTrayectos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.gestionarTrayectos();				
			}
		});
	}
	
}
