package componentes_swing.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import componentes_swing.*;
import postgre_db.PostgreDB;

public class AltaLinea extends JPanel {

	public AltaLinea(GestionarLineas panelGestionar, Integer proxID) {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		//PanelDatos
		TitledBorder borde  = BorderFactory.createTitledBorder("Datos");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		
		JPanel panelDatos = new JPanel();
		panelDatos.setLayout(new GridBagLayout());
		panelDatos.setBackground(Color.WHITE);
		panelDatos.setBorder(borde);
		GridBagConstraints cons = new GridBagConstraints();
			
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(20,20,10,20);
		add(panelDatos,cons);
		
		CampoJ cNombre = new CampoJ();
		EtiquetaJ eNombre = new EtiquetaJ("Nombre");
		CheckBoxJ cActiva = new CheckBoxJ("Activa");
		
		JPanel panelColor = new JPanel();
		TitledBorder borde2  = BorderFactory.createTitledBorder("Color");
		borde2.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		borde2.setTitleJustification(TitledBorder.LEFT);
		borde2.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		
		panelColor.setLayout(new GridBagLayout());
		panelColor.setBackground(Color.WHITE);
		panelColor.setBorder(borde2);
		
		CampoJ cColorR = new CampoJ("0");
		EtiquetaJ eColorR = new EtiquetaJ("R");
		CampoJ cColorG = new CampoJ("0");
		EtiquetaJ eColorG = new EtiquetaJ("G");
		CampoJ cColorB = new CampoJ("0");
		EtiquetaJ eColorB = new EtiquetaJ("B");
		PanelColor vistaColor = new PanelColor();
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelDatos.add(eNombre,cons);
		
		cons.gridx = 1;
		cons.insets = new Insets(10,5,10,20);
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		panelDatos.add(cNombre,cons);
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 2;
		cons.insets = new Insets(10,10,10,10);
		panelDatos.add(panelColor,cons);
		
		cons.gridy = 2;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(0,20,20,20);
		cActiva.setSelected(true);
		panelDatos.add(cActiva,cons);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelColor.add(eColorR,cons);
		
		cons.gridx=1;
		cons.insets = new Insets(10,5,10,20);
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		panelColor.add(cColorR,cons);
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelColor.add(eColorG,cons);
		
		cons.gridx=1;
		cons.insets = new Insets(10,5,10,20);
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		panelColor.add(cColorG,cons);
		
		cons.gridx = 0;
		cons.gridy = 2;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelColor.add(eColorB,cons);
		
		cons.gridx=1;
		cons.insets = new Insets(10,5,10,20);
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		panelColor.add(cColorB,cons);
		
		cons.gridx = 2;
		cons.gridy = 0;
		cons.gridheight = 3;
		cons.weighty = 0.1;
		cons.weightx = 0;
		cons.anchor = GridBagConstraints.CENTER;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(10,10,10,10);
		panelColor.add(vistaColor,cons);
		
		DocumentListener cambioColorListener = new DocumentListener() {
			private void cambiar() {
				Integer r = 0;
				Integer g = 0;
				Integer b = 0;
				
				if(cColorR.getText().length() > 0) {
					try{
						r = Integer.parseInt(cColorR.getText());
						if(r < 0) r = 0;
						if(r > 255) r = 255;
					}
					catch(NumberFormatException e) { }	
				}
				if(cColorG.getText().length() > 0) {
					try{
						g = Integer.parseInt(cColorG.getText());
						if(g < 0) g = 0;
						if(g > 255) g = 255;
					}
					catch(NumberFormatException e) { }	
				}
				if(cColorB.getText().length() > 0) {
					try{
						b = Integer.parseInt(cColorB.getText());
						if(b < 0) b = 0;
						if(b > 255) b = 255;
					}
					catch(NumberFormatException e) { }	
				}
				
				vistaColor.actualizarColor(r,g,b);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				cambiar();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				cambiar();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				cambiar();
			}
		};
		
		cColorR.getDocument().addDocumentListener(cambioColorListener);
		cColorG.getDocument().addDocumentListener(cambioColorListener);
		cColorB.getDocument().addDocumentListener(cambioColorListener);
		
		//PanelBotones
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridBagLayout());
		panelBotones.setBackground(UIManager.getColor("CheckBox.focus"));
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(0,20,0,20);
		cons.anchor = GridBagConstraints.EAST;
		add(panelBotones,cons);
		
		BotonJ aceptar = new BotonJ("Aceptar");
		BotonJ cancelar = new BotonJ("Cancelar");
		EtiquetaJ camposIncompletos = new EtiquetaJ("Debe completar todos los campos");
		camposIncompletos.setVisible(false);
		camposIncompletos.setForeground(Color.RED);
		
		cons.gridy=0;
		cons.gridx = 0;
		cons.insets = new Insets(0,10,20,0);
		panelBotones.add(camposIncompletos,cons);
		
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridx = 1;
		cons.insets = new Insets(0,10,20,10);
		panelBotones.add(aceptar,cons);
		
		cons.gridx=2;
		panelBotones.add(cancelar,cons);
		
		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					if(camposCompletos()) {
						String nom = cNombre.getText();
						Color col = vistaColor.getColorSeleccionado();
						Boolean act = cActiva.isSelected();
						
						PostgreDB.cargarLinea(proxID, nom, col, act);
						panelGestionar.actualizarTabla();
					}
					else {
						camposIncompletos.setVisible(true);
					}
			}			
		});
	}
	
	public Boolean camposCompletos() {
		JPanel panelDatos = (JPanel)this.getComponent(0);
		
		Integer cNombre = ((CampoJ) panelDatos.getComponent(1)).getText().length();
		Integer cColorR = ((CampoJ) ((JPanel) panelDatos.getComponent(2)).getComponent(1)).getText().length();
		Integer cColorG = ((CampoJ) ((JPanel) panelDatos.getComponent(2)).getComponent(3)).getText().length();
		Integer cColorB = ((CampoJ) ((JPanel) panelDatos.getComponent(2)).getComponent(5)).getText().length();
		
		return (cNombre > 0 && cColorR > 0 && cColorG > 0 && cColorB > 0);
	}
}
