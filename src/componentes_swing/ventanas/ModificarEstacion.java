package componentes_swing.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import componentes_swing.BotonJ;
import componentes_swing.CampoJ;
import componentes_swing.EtiquetaJ;
import componentes_swing.ListaHorarios;
import postgre_db.PostgreDB;

public class ModificarEstacion extends JPanel {

	public ModificarEstacion(GestionarEstaciones panelGestionar, Integer id, String nombre, String apertura, String cierre) {
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
		
		CampoJ cNombre = new CampoJ(nombre);
		EtiquetaJ eNombre = new EtiquetaJ("Nombre");
		ListaHorarios lApertura = new ListaHorarios(apertura);
		EtiquetaJ eApertura = new EtiquetaJ("Horario apertura");
		ListaHorarios lCierre = new ListaHorarios(cierre);
		EtiquetaJ eCierre = new EtiquetaJ("Horario cierre");
		
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
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelDatos.add(eApertura,cons);
		
		cons.gridx = 1;
		cons.insets = new Insets(10,5,10,20);
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		panelDatos.add(lApertura,cons);
		
		cons.gridx = 0;
		cons.gridy = 2;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelDatos.add(eCierre,cons);
		
		cons.gridx = 1;
		cons.insets = new Insets(10,5,20,20);
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		panelDatos.add(lCierre,cons);
		
		//Panel botones
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
		cons.insets = new Insets(0,10,0,0);
		panelBotones.add(camposIncompletos,cons);
		
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
					String nom = cNombre.getText();
					LocalTime ape = LocalTime.parse(((String)lApertura.getSelectedItem()),DateTimeFormatter.ofPattern("HH:mm"));
					LocalTime cie = LocalTime.parse(((String)lCierre.getSelectedItem()),DateTimeFormatter.ofPattern("HH:mm"));
					
					PostgreDB.modificarEstacion(id, nom, ape, cie);
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
		
		CampoJ cNombre = (CampoJ) panelDatos.getComponent(1);
		ListaHorarios lApertura = (ListaHorarios) panelDatos.getComponent(3);
		ListaHorarios lCierre = (ListaHorarios) panelDatos.getComponent(5);
		
		return (cNombre.getText().length() > 0 && ((String)lApertura.getSelectedItem()).length() > 0 && ((String)lCierre.getSelectedItem()).length() > 0);
	}
	
}
