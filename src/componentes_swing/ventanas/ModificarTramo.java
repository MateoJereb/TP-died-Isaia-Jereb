package componentes_swing.ventanas;

import java.awt.Color;
import java.awt.Font;
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

import clases.Estacion;
import componentes_swing.BotonJ;
import componentes_swing.CampoJ;
import componentes_swing.CheckBoxJ;
import componentes_swing.EtiquetaJ;
import postgre_db.PostgreDB;

public class ModificarTramo extends JPanel {

	public ModificarTramo(VerTrayecto panelAnterior, Integer id, String origen, String destino, Integer idLinea, Double distancia, Integer duracion, Integer capacidad, Double costo) {
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
		cons.insets = new Insets(10,20,10,20);
		add(panelDatos,cons);
		
		EtiquetaJ estacionesTramo = new EtiquetaJ("Tramo desde '"+origen+"' a '"+destino+"'");
		EtiquetaJ eDistancia = new EtiquetaJ("Distancia (Km)");
		CampoJ cDistancia = new CampoJ(String.valueOf(distancia));
		EtiquetaJ eDuracion = new EtiquetaJ("Duracion (min)");
		CampoJ cDuracion = new CampoJ(String.valueOf(duracion));
		EtiquetaJ eCapacidad = new EtiquetaJ("Capacidad");
		CampoJ cCapacidad = new CampoJ(String.valueOf(capacidad));
		EtiquetaJ eCosto = new EtiquetaJ("Costo ($)");
		CampoJ cCosto = new CampoJ(String.valueOf(costo));
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 4;
		cons.insets = new Insets(0,10,10,10);
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.CENTER;
		estacionesTramo.setFont(new Font("Microsoft Tai Le",Font.BOLD,14));
		panelDatos.add(estacionesTramo,cons);
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelDatos.add(eDistancia,cons);
		
		cons.gridx = 1;
		cons.insets = new Insets(10,5,10,10);
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		panelDatos.add(cDistancia,cons);
		
		cons.gridx = 2;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,10,10,5);
		panelDatos.add(eDuracion,cons);
		
		cons.gridx = 3;
		cons.insets = new Insets(10,5,10,20);
		cons.weightx = 0.085;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		panelDatos.add(cDuracion,cons);
		
		cons.gridx = 0;
		cons.gridy = 2;
		cons.gridwidth = 1;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelDatos.add(eCapacidad,cons);
		
		cons.gridx = 1;
		cons.insets = new Insets(10,5,10,10);
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		panelDatos.add(cCapacidad,cons);
		
		cons.gridx = 2;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,10,10,5);
		panelDatos.add(eCosto,cons);
		
		cons.gridx = 3;
		cons.insets = new Insets(10,5,10,20);
		cons.weightx = 0.085;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		panelDatos.add(cCosto,cons);
		
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
		EtiquetaJ entradaInvalida = new EtiquetaJ();
		entradaInvalida.setVisible(false);
		entradaInvalida.setForeground(Color.RED);
		
		cons.gridy=0;
		cons.gridx = 0;
		cons.insets = new Insets(0,10,0,0);
		panelBotones.add(entradaInvalida,cons);
		
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridx = 1;
		cons.insets = new Insets(0,10,0,10);
		panelBotones.add(aceptar,cons);
		
		cons.gridx=2;
		panelBotones.add(cancelar,cons);
		
		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(camposCompletos()) {
					if(numericosValidos()) {
						Double dis = Double.parseDouble(cDistancia.getText());
						Integer dur = Integer.parseInt(cDuracion.getText());
						Integer cap = Integer.parseInt(cCapacidad.getText());
						Double cos = Double.parseDouble(cCosto.getText());
						
						PostgreDB.modificarRuta(id, dis, dur, cap, cos);
						panelAnterior.actualizarTabla(idLinea);
					}
					else {
						entradaInvalida.setText("Campos numéricos inválidos");
						entradaInvalida.setVisible(true);
					}
				}
				else {
					entradaInvalida.setText("Debe completar todos los campos");
					entradaInvalida.setVisible(true);
				}
			}
		});
	}
	
	public Boolean camposCompletos() {
		JPanel panelDatos = (JPanel)this.getComponent(0);
		
		CampoJ cDistancia = (CampoJ) panelDatos.getComponent(2);
		CampoJ cDuracion = (CampoJ) panelDatos.getComponent(4);
		CampoJ cCapacidad = (CampoJ) panelDatos.getComponent(6);
		CampoJ cCosto = (CampoJ) panelDatos.getComponent(8);
	
		String distancia = cDistancia.getText();
		String duracion = cDuracion.getText();
		String capacidad = cCapacidad.getText();
		String costo = cCosto.getText();
		
		return distancia.length()>0 && duracion.length()>0 && capacidad.length()>0 && costo.length()>0;
	}
	
	public Boolean numericosValidos() {
		JPanel panelDatos = (JPanel)this.getComponent(0);
		
		Boolean salida = true;
		
		CampoJ cDistancia = (CampoJ) panelDatos.getComponent(2);
		CampoJ cDuracion = (CampoJ) panelDatos.getComponent(4);
		CampoJ cCapacidad = (CampoJ) panelDatos.getComponent(6);
		CampoJ cCosto = (CampoJ) panelDatos.getComponent(8);
		
		String distancia = cDistancia.getText();
		String duracion = cDuracion.getText();
		String capacidad = cCapacidad.getText();
		String costo = cCosto.getText();
		
		try {
			Double.parseDouble(distancia);
		}
		catch(NumberFormatException e) {
			salida = false;
			cDistancia.setBackground(new Color(255,200,200));
		}
		
		try {
			Integer.parseInt(duracion);
		}
		catch(NumberFormatException e) {
			salida = false;
			cDuracion.setBackground(new Color(255,200,200));
		}
		
		try {
			Integer.parseInt(capacidad);
		}
		catch(NumberFormatException e) {
			salida = false;
			cCapacidad.setBackground(new Color(255,200,200));
		}
		
		try {
			Double.parseDouble(costo);
		}
		catch(NumberFormatException e) {
			salida = false;
			cCosto.setBackground(new Color(255,200,200));
		}
		
		return salida;
	}
}
