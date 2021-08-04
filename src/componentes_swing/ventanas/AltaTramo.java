package componentes_swing.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxEditor;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import clases.*;
import componentes_swing.*;
import postgre_db.PostgreDB;

public class AltaTramo extends JPanel{
	
	private ListaEstaciones lOrigen = null;
	private ListaEstaciones lDestino = null;
	private Vector<Estacion> listaEstaciones;
	
	public AltaTramo(VerTrayecto panelAnterior, Optional<Estacion> estacion, Integer idLinea, Integer lugar, Integer proxID) {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		listaEstaciones = new Vector<Estacion>();
		
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
		
		EtiquetaJ eOrigen = new EtiquetaJ("Estación Origen");
		EtiquetaJ eDestino = new EtiquetaJ("Estación Destino");
		EtiquetaJ eDistancia = new EtiquetaJ("Distancia (Km)");
		CampoJ cDistancia = new CampoJ("0.0");
		EtiquetaJ eDuracion = new EtiquetaJ("Duracion (min)");
		CampoJ cDuracion = new CampoJ("0");
		EtiquetaJ eCapacidad = new EtiquetaJ("Capacidad");
		CampoJ cCapacidad = new CampoJ("0");
		EtiquetaJ eCosto = new EtiquetaJ("Costo ($)");
		CampoJ cCosto = new CampoJ("0.0");
		CheckBoxJ cActiva = new CheckBoxJ("Activa");
		
		switch(lugar) {
		case VerTrayecto.NUEVO_TRAYECTO:
			lOrigen = new ListaEstaciones(idLinea);
			lDestino = new ListaEstaciones(idLinea);
			
			for(int i=0; i<lOrigen.getItemCount();i++) {
				listaEstaciones.add(lOrigen.getItemAt(i));
			}
			
			break;
		case VerTrayecto.AGREGAR_PRINCIPIO:
			lOrigen = new ListaEstaciones(idLinea);
			lDestino = new ListaEstaciones(idLinea,estacion.get());
			
			for(int i=0; i<lOrigen.getItemCount();i++) {
				listaEstaciones.add(lOrigen.getItemAt(i));
			}
			
			break;
		case VerTrayecto.AGREGAR_FINAL:
			lOrigen = new ListaEstaciones(idLinea,estacion.get());
			lDestino = new ListaEstaciones(idLinea);
			
			for(int i=0; i<lDestino.getItemCount();i++) {
				listaEstaciones.add(lDestino.getItemAt(i));
			}
			
			break;
		}
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelDatos.add(eOrigen,cons);
		
		cons.gridx = 1;
		cons.gridwidth = 3;
		cons.insets = new Insets(10,5,10,20);
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		panelDatos.add(lOrigen,cons);
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelDatos.add(eDestino,cons);
		
		cons.gridx = 1;
		cons.gridwidth = 3;
		cons.insets = new Insets(10,5,10,20);
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		panelDatos.add(lDestino,cons);
		
		cons.gridx = 0;
		cons.gridy = 2;
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
		cons.gridy = 3;
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
		
		cons.gridx = 0;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		cons.gridy = 4;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(0,20,10,20);
		cActiva.setSelected(true);
		panelDatos.add(cActiva,cons);
		
		lOrigen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(lugar == VerTrayecto.NUEVO_TRAYECTO) {
					Vector<Estacion> aux = new Vector<Estacion>();				
					Estacion origSeleccionada = (Estacion) lOrigen.getSelectedItem();
					Estacion destSeleccionada = (Estacion)lDestino.getSelectedItem();
					aux.addAll(listaEstaciones);
					lDestino.removeAllItems();
					aux.remove(origSeleccionada);
					
					for(Estacion e1 : aux) {
						lDestino.addItem(e1);
					}
				
					lDestino.setSelectedItem(destSeleccionada);
				}
			}
		});
		
		lDestino.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(lugar == VerTrayecto.NUEVO_TRAYECTO) {
					Vector<Estacion> aux = new Vector<Estacion>();
					Estacion origSeleccionada = (Estacion) lOrigen.getSelectedItem();
					Estacion destSeleccionada = (Estacion)lDestino.getSelectedItem();
					aux.addAll(listaEstaciones);
					lOrigen.removeAllItems();
					aux.remove(destSeleccionada);
					
					for(Estacion e1 : aux) {
						lOrigen.addItem(e1);
					}
				
					lOrigen.setSelectedItem(origSeleccionada);
				}
			}
		});
		
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
		cons.insets = new Insets(0,10,20,0);
		panelBotones.add(entradaInvalida,cons);
		
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
					if(numericosValidos()) {
						
						Integer ori = ((Estacion) lOrigen.getSelectedItem()).getId();
						Integer des = ((Estacion) lDestino.getSelectedItem()).getId();
						Double dis = Double.parseDouble(cDistancia.getText());
						Integer dur = Integer.parseInt(cDuracion.getText());
						Integer cap = Integer.parseInt(cCapacidad.getText());
						Double cos = Double.parseDouble(cCosto.getText());
						Boolean act = cActiva.isSelected();
						
						PostgreDB.cargarRuta(proxID, ori, des, idLinea, dis, dur, cap, act, cos);
						panelAnterior.actualizarTabla(idLinea);
						panelAnterior.actualizarCamino();
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
		
		CampoJ cDistancia = (CampoJ) panelDatos.getComponent(5);
		CampoJ cDuracion = (CampoJ) panelDatos.getComponent(7);
		CampoJ cCapacidad = (CampoJ) panelDatos.getComponent(9);
		CampoJ cCosto = (CampoJ) panelDatos.getComponent(11);
		
		Integer origen = ((Estacion) lOrigen.getSelectedItem()).getId();
		Integer destino = ((Estacion) lDestino.getSelectedItem()).getId();
		String distancia = cDistancia.getText();
		String duracion = cDuracion.getText();
		String capacidad = cCapacidad.getText();
		String costo = cCosto.getText();
		
		return origen!=-1 && destino!=-1 && distancia.length()>0 && duracion.length()>0 && capacidad.length()>0 && costo.length()>0;
	}
	
	public Boolean numericosValidos() {
		JPanel panelDatos = (JPanel)this.getComponent(0);
		
		Boolean salida = true;
		
		CampoJ cDistancia = (CampoJ) panelDatos.getComponent(5);
		CampoJ cDuracion = (CampoJ) panelDatos.getComponent(7);
		CampoJ cCapacidad = (CampoJ) panelDatos.getComponent(9);
		CampoJ cCosto = (CampoJ) panelDatos.getComponent(11);
		
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
