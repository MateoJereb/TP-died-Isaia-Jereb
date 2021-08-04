package componentes_swing.ventanas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import clase_app.App;
import clases.*;
import componentes_swing.*;
import postgre_db.PostgreDB;

public class FinalizarVenta extends JPanel {

	private Vector<Ruta> camino;
	private Integer nroS;
	private Estacion origenS;
	private Estacion destinoS;
	private String mailS;
	private String nombreS;
	private LocalDateTime fechaS;
	private Double costoS;
	private String caminoS;
	//PanelDatos
	private JPanel panelDatos;
	private EtiquetaJ eNro;
	private EtiquetaJ eMail;
	private EtiquetaJ eNombre;
	private EtiquetaJ eFecha;
	private EtiquetaJ eOrigen;
	private EtiquetaJ eDestino;
	private EtiquetaJ eCamino;
	private EtiquetaJ vNro;
	private CampoJ cMail;
	private CampoJ cNombre;
	private EtiquetaJ vFecha;
	private EtiquetaJ vOrigen;
	private EtiquetaJ vDestino;
	private EtiquetaJ vCamino;
	private EtiquetaJ costo;
	//PanelBotones
	private JPanel panelBotones;
	private EtiquetaJ camposIncompletos;
	private BotonJ vender;
	private BotonJ volver;
	
	
	public FinalizarVenta(Vector<Ruta> camino) {
		super();
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		this.camino = camino;
		
		panelDatos();
		panelBotones();
		
	}
	
	private void panelDatos() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Boleto");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		
		panelDatos = new JPanel();
		panelDatos.setLayout(new GridBagLayout());
		panelDatos.setBackground(Color.WHITE);
		panelDatos.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.VERTICAL;
		cons.insets = new Insets(10,20,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		add(panelDatos,cons);
		
		eNro = new EtiquetaJ("Nro. boleto:");
		eMail = new EtiquetaJ("Email cliente:");
		eNombre = new EtiquetaJ("Nombre cliente:");
		eFecha = new EtiquetaJ("Fecha:");
		eOrigen = new EtiquetaJ("Estación origen:");
		eDestino = new EtiquetaJ("Estación destino:");
		eCamino = new EtiquetaJ("Camino:");
		
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		eNro.setFont(new Font("Microsoft Tai Le",Font.BOLD,12));
		panelDatos.add(eNro,cons);
		
		cons.gridy = 1;
		eMail.setFont(new Font("Microsoft Tai Le",Font.BOLD,12));
		panelDatos.add(eMail,cons);
		
		cons.gridy = 2;
		eNombre.setFont(new Font("Microsoft Tai Le",Font.BOLD,12));
		panelDatos.add(eNombre,cons);
		
		cons.gridy = 3;
		eFecha.setFont(new Font("Microsoft Tai Le",Font.BOLD,12));
		panelDatos.add(eFecha,cons);
		
		cons.gridy = 4;
		eOrigen.setFont(new Font("Microsoft Tai Le",Font.BOLD,12));
		panelDatos.add(eOrigen,cons);
		
		cons.gridy = 5;
		eDestino.setFont(new Font("Microsoft Tai Le",Font.BOLD,12));
		panelDatos.add(eDestino,cons);
		
		cons.gridy = 6;
		eCamino.setFont(new Font("Microsoft Tai Le",Font.BOLD,12));
		panelDatos.add(eCamino,cons);
		
		vNro = new EtiquetaJ();
		cMail = new CampoJ();
		cNombre = new CampoJ();
		vFecha = new EtiquetaJ();
		vOrigen = new EtiquetaJ();
		vDestino = new EtiquetaJ();
		vCamino = new EtiquetaJ();
		costo = new EtiquetaJ();
		
		Vector<Boleto> boletos = PostgreDB.recuperarBoletos();
		if(boletos.size() > 0 ) nroS = boletos.stream().map(b -> b.getNro()).max((n1,n2) -> n1.compareTo(n2)).get()+1;
		else nroS = 1;
		
		origenS = camino.get(0).getOrigen();
		destinoS = camino.get(camino.size()-1).getDestino();
		
		caminoS="";
		costoS = 0.0;
		for(Ruta r : camino) {
			caminoS += r.getOrigen().getNombre()+" - ";
			costoS += r.getCosto();
		}
		caminoS += destinoS.getNombre();
		
		fechaS = LocalDateTime.now();
		
		vNro.setText(String.valueOf(nroS));
		vFecha.setText(fechaS.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		vOrigen.setText(origenS.getNombre());
		vDestino.setText(destinoS.getNombre());
		vCamino.setText(caminoS);
		costo.setText("TOTAL: $"+String.valueOf(costoS));
		costo.setFont(new Font("Microsoft Tai Le",Font.BOLD,15));
		costo.setForeground(new Color(0,150,0));
		
		cons.gridx = 1;
		cons.gridy = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.WEST;
		cons.insets = new Insets(10,5,10,20);
		panelDatos.add(vNro,cons);
		
		cons.gridy = 1;
		cMail.setPreferredSize(new Dimension(200,(int)cMail.getPreferredSize().getHeight()));
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelDatos.add(cMail,cons);
		
		cons.gridy = 2;
		cNombre.setPreferredSize(cMail.getPreferredSize());
		panelDatos.add(cNombre,cons);
		
		cons.gridy = 3;
		cons.fill = GridBagConstraints.NONE;
		panelDatos.add(vFecha,cons);
		
		cons.gridy = 4;
		panelDatos.add(vOrigen,cons);
		
		cons.gridy = 5;
		panelDatos.add(vDestino,cons);
		
		cons.gridy = 6;
		panelDatos.add(vCamino,cons);
		
		cons.gridy = 7;
		cons.anchor = GridBagConstraints.EAST;		
		panelDatos.add(costo,cons);
	}
	
	private void panelBotones() {
		GridBagConstraints cons = new GridBagConstraints();
		panelBotones = new JPanel();
		panelBotones.setLayout(new GridBagLayout());
		panelBotones.setBackground(UIManager.getColor("CheckBox.focus"));
		
		cons.gridx = 0;
		cons.gridy = 2;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(0,20,0,20);
		cons.anchor = GridBagConstraints.EAST;
		add(panelBotones,cons);
		
		camposIncompletos = new EtiquetaJ("Debe completar todos los campos");
		vender = new BotonJ("Vender boleto");
		volver = new BotonJ("Volver atrás");
		
		cons.gridy = 0;
		cons.insets = new Insets(0,10,5,10);
		camposIncompletos.setForeground(Color.RED);
		camposIncompletos.setVisible(false);
		panelBotones.add(camposIncompletos,cons);
		
		cons.gridx = 1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelBotones.add(vender);
		
		cons.gridx = 2;
		cons.insets = new Insets(0,10,0,0);
		panelBotones.add(volver,cons);
		
		vender.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cMail.getText().length() > 0 && cNombre.getText().length() > 0) {
					camposIncompletos.setVisible(false);
					mailS = cMail.getText();
					nombreS = cNombre.getText();
					PostgreDB.cargarBoleto(nroS, origenS.getId(), destinoS.getId(), mailS, nombreS, fechaS, costoS, caminoS);
					
					JDialog ventana = new JDialog(App.getVentana(),"Vender Boleto");
					JPanel vendido = new JPanel(new GridBagLayout());
					vendido.setBackground(UIManager.getColor("CheckBox.focus"));
					ventana.setContentPane(vendido);
					
					GridBagConstraints cons2 = new GridBagConstraints();
					EtiquetaJ texto = new EtiquetaJ("Boleto #"+nroS+" vendido");
					BotonJ aceptar = new BotonJ("Aceptar");
					
					cons2.gridx = 0;
					cons2.gridy = 0;
					cons2.gridwidth = 1;
					cons2.gridheight = 1;
					cons2.weightx = 0.1;
					cons2.fill = GridBagConstraints.HORIZONTAL;
					cons2.insets = new Insets(10,10,10,10);
					texto.setForeground(new Color(0,150,0));
					vendido.add(texto,cons2);
					
					cons2.gridy = 1;
					cons2.fill = GridBagConstraints.NONE;
					vendido.add(aceptar,cons2);
					
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
							App.venderBoleto();
						}						
					});
					
					ventana.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
							App.venderBoleto();
						}
					});
					
				}
				else {
					camposIncompletos.setVisible(true);
				}
			}			
		});
		
	}
	
	public BotonJ getVolver() {
		return volver;
	}
	
}
