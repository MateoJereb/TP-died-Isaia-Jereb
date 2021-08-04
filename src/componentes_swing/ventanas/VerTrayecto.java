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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;

import clase_app.App;
import clases.*;
import componentes_swing.*;
import componentes_swing.modelos_tablas.ModeloTablaRutas;
import componentes_swing.modelos_tablas.ModeloTablaTrayectos;
import postgre_db.PostgreDB;

public class VerTrayecto extends JPanel{

	private TablaJ tabla;
	private ModeloTablaRutas modelo;
	private Vector<Ruta> rutas;
	private Boolean activaCambiado;
	private Boolean trayectoEliminado;
	private EtiquetaJ camino;
	
	public static final int NUEVO_TRAYECTO = 1;
	public static final int AGREGAR_PRINCIPIO = 2;
	public static final int AGREGAR_FINAL = 3;
	
	public VerTrayecto(Integer idLinea) {
		super();			
		setBorder(new EmptyBorder(10, 20, 10, 20));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		camino = new EtiquetaJ();
		camino.setFont(new Font("Microsoft Tai Le",Font.ITALIC,15));
		rutas = PostgreDB.recuperarRutas(Optional.of(idLinea), Optional.ofNullable(null), Optional.ofNullable(null));
		
		panelCamino();
		panelResultados(idLinea);
		panelBotones(idLinea);
	}
	
	public void panelCamino() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Camino");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		
		JPanel panelCamino = new JPanel();
		panelCamino.setLayout(new GridBagLayout());
		panelCamino.setBackground(Color.WHITE);
		panelCamino.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(20,20,10,20);
		cons.anchor = GridBagConstraints.NORTH;
		
		add(panelCamino,cons);
		
		actualizarCamino();
		cons.insets = new Insets(10,10,10,10);
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.CENTER;
		panelCamino.add(camino,cons);
	}
	
	public void panelResultados(Integer idLinea) {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Tramos");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
	
		JPanel panelResultados = new JPanel();
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
		
		modelo = new ModeloTablaRutas();
		tabla = new TablaJ(modelo);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(200);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(100);
		tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
		JScrollPane scroll = new JScrollPane(tabla);
		
		actualizarTabla(idLinea);
		
		cons.gridy = 0;
		panelResultados.add(scroll,cons);
		
		tabla.getDefaultEditor(Boolean.class).addCellEditorListener(new CellEditorListener() {
			@Override
			public void editingStopped(ChangeEvent e) {
				activaCambiado = false;
				Integer fila = tabla.getSelectedRow();
				Integer columna = tabla.getSelectedColumn();
				Boolean valor = (Boolean) modelo.getValueAt(fila,columna);
				Integer idRuta = (Integer) modelo.getValueAt(fila, 0);
				String nomRuta = "'"+modelo.getValueAt(fila, 1)+"' a '"+modelo.getValueAt(fila, 2)+"'";
				
				JDialog ventana = new JDialog(App.getVentana(),"Cambiar estado ruta");
				JPanel confirmacion = new JPanel(new GridBagLayout());
				confirmacion.setBackground(UIManager.getColor("CheckBox.focus"));
				ventana.setContentPane(confirmacion);
				GridBagConstraints cons2 = new GridBagConstraints();
				EtiquetaJ texto = new EtiquetaJ();
				BotonJ si = new BotonJ("Sí");
				BotonJ no = new BotonJ("No");
				
				cons2.gridx = 0;
				cons2.gridy = 0;
				cons2.gridwidth = 2;
				cons2.gridheight = 1;
				cons2.weightx = 0.1;
				cons2.fill = GridBagConstraints.NONE;
				cons2.insets = new Insets(10,10,10,10);
				
				confirmacion.add(texto,cons2);
				cons2.gridy = 1;
				cons2.gridwidth = 1;
				cons2.fill = GridBagConstraints.NONE;
				cons2.anchor = GridBagConstraints.EAST;
				si.setPreferredSize(no.getPreferredSize());
				confirmacion.add(si,cons2);
				cons2.gridx = 1;
				cons2.anchor = GridBagConstraints.WEST;
				confirmacion.add(no,cons2);
				
				if(!valor) texto.setText("¿Desea desactivar la ruta que va de "+nomRuta+"?");
				else texto.setText("¿Desea activar la ruta que va de "+nomRuta+"?");
				
				App.getVentana().setEnabled(false);
				ventana.pack();
				ventana.setLocationRelativeTo(null);
				ventana.setVisible(true);
				
				ventana.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						if(!activaCambiado) modelo.setValueAt(!valor, fila, columna);
						App.getVentana().setEnabled(true);
						App.getVentana().setVisible(true);
					}	
				});
				
				si.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						PostgreDB.cambiarEstadoRuta(idRuta, valor);
						
						JPanel realizado = new JPanel(new GridBagLayout());
						realizado.setBackground(UIManager.getColor("CheckBox.focus"));
						GridBagConstraints cons2 = new GridBagConstraints();
						EtiquetaJ mensaje = new EtiquetaJ();
						BotonJ aceptar = new BotonJ("Aceptar");
						mensaje.setForeground(new Color(0,150,0));
						
						if(!valor) mensaje.setText("La ruta que va de "+nomRuta+" está ahora inactiva.");
						else mensaje.setText("La ruta que va de "+nomRuta+" está ahora activa.");
						
						cons2.gridx = 0;
						cons2.gridy = 0;
						cons2.fill = GridBagConstraints.NONE;
						cons2.anchor = GridBagConstraints.CENTER;
						cons2.insets = new Insets(10,10,10,10);
						
						realizado.add(mensaje,cons2);
						
						cons2.gridy = 1;
						realizado.add(aceptar,cons2);
						
						ventana.setContentPane(realizado);

						ventana.revalidate();
						ventana.repaint();
						ventana.pack();
						ventana.setLocationRelativeTo(null);
						
						aceptar.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								ventana.dispose();
								App.getVentana().setEnabled(true);
								App.getVentana().setVisible(true);
							}
						});
					}
				});
				
				no.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						modelo.setValueAt(!valor, fila, columna);
						ventana.dispose();
						App.getVentana().setEnabled(true);
						App.getVentana().setVisible(true);
					}
				});
			}

			@Override
			public void editingCanceled(ChangeEvent e) { }
		});
	}
	
	public void panelBotones(Integer idLinea) {
		GridBagConstraints cons = new GridBagConstraints();
		JPanel panelBotones = new JPanel();
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
		
		BotonJ altaTramo = new BotonJ("Agregar tramo");
		BotonJ modificarTramo = new BotonJ("Modificar tramo"); 
		BotonJ bajaTrayecto = new BotonJ("Eliminar trayecto");
		BotonJ volver = new BotonJ("Volver atrás");
		
		cons.gridy=0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(0,10,20,10);
		panelBotones.add(altaTramo,cons);
		
		cons.gridx=1;
		panelBotones.add(modificarTramo,cons);
		
		cons.gridx=2;
		panelBotones.add(bajaTrayecto,cons);
		
		cons.gridx = 3;
		cons.insets = new Insets(0,10,20,20);
		panelBotones.add(volver,cons);
		
		volver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.gestionarTrayectos();
			}
		});
		
		altaTramo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Vector<Ruta> todasLasRutas = PostgreDB.recuperarRutas(Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null));
				Integer maxID;
				if(todasLasRutas.isEmpty()) maxID = 0;
				else maxID = todasLasRutas.stream().map(l -> l.getId()).max((id1,id2) -> id1.compareTo(id2)).get();
				
				if(rutas.isEmpty()) {
					App.altaTramo(Optional.ofNullable(null), idLinea, NUEVO_TRAYECTO, maxID+1);
				}
				else {
					JDialog ventana = new JDialog(App.getVentana(),"Alta tramo");
					JPanel panel = new JPanel(new GridBagLayout());
					panel.setBackground(UIManager.getColor("CheckBox.focus"));
					ventana.setContentPane(panel);
					GridBagConstraints cons2 = new GridBagConstraints();
					EtiquetaJ texto = new EtiquetaJ("¿En qué parte del trayecto desea agregar el tramo?");
					BotonJ alPrincipio = new BotonJ("Al principio");
					BotonJ alFinal = new BotonJ("Al final");
					BotonJ cancelar = new BotonJ("Cancelar");
					
					cons2.gridx = 0;
					cons2.gridy = 0;
					cons2.gridwidth = 3;
					cons2.gridheight = 1;
					cons2.weightx = 0.1;
					cons2.fill = GridBagConstraints.NONE;
					cons2.insets = new Insets(10,10,10,10);
					
					panel.add(texto,cons2);
					cons2.gridy = 1;
					cons2.gridwidth = 1;
					cons2.fill = GridBagConstraints.NONE;
					cons2.anchor = GridBagConstraints.EAST;
					cons2.insets = new Insets(10,10,10,5);
					panel.add(alPrincipio,cons2);
					
					cons2.gridx = 1;
					cons2.anchor = GridBagConstraints.CENTER;
					cons2.insets = new Insets(10,5,10,5);
					alFinal.setPreferredSize(alPrincipio.getPreferredSize());
					panel.add(alFinal,cons2);
					
					cons2.gridx=2;
					cons2.anchor = GridBagConstraints.WEST;
					cons2.insets = new Insets(10,5,10,10);
					cancelar.setPreferredSize(alPrincipio.getPreferredSize());
					panel.add(cancelar,cons2);
					
					ventana.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
						}	
					});
					
					alPrincipio.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							App.altaTramo(Optional.of(buscarPrimera(rutas).getOrigen()), idLinea, AGREGAR_PRINCIPIO, maxID+1);
							ventana.dispose();
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
						}
					});
					
					alFinal.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							App.altaTramo(Optional.of(buscarUltima(rutas).getDestino()), idLinea, AGREGAR_FINAL, maxID+1);
							ventana.dispose();
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
						}
					});
					
					cancelar.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							ventana.dispose();
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
						}
					});
					
					
					App.getVentana().setEnabled(false);
					ventana.pack();
					ventana.setLocationRelativeTo(null);
					ventana.setVisible(true);
					
					
				}
			}
		});
		
		modificarTramo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer fila = tabla.getSelectedRow();
				
				if(fila != -1) {
					Integer idS = (Integer) modelo.getValueAt(fila, 0);
					String origenS = (String) modelo.getValueAt(fila, 1);
					String destinoS = (String) modelo.getValueAt(fila, 2);
					Double distanciaS = (Double) modelo.getValueAt(fila, 3);
					Integer duracionS = (Integer) modelo.getValueAt(fila, 4);
					Integer capacidadS = (Integer) modelo.getValueAt(fila, 5);
					Double costoS = (Double) modelo.getValueAt(fila, 6);
					
					App.modificarTramo(idS, origenS, destinoS, idLinea, distanciaS, duracionS, capacidadS, costoS);
				}
				else {
					JDialog ventana = new JDialog(App.getVentana(),"Modificar Tramo");
					JPanel panelModificacion = new JPanel(new GridBagLayout());
					panelModificacion.setBackground(UIManager.getColor("CheckBox.focus"));
					ventana.setContentPane(panelModificacion);
					GridBagConstraints cons2 = new GridBagConstraints();
					EtiquetaJ texto = new EtiquetaJ("Seleccione un tramo antes");
					BotonJ aceptar = new BotonJ("Aceptar");
					
					cons2.gridx = 0;
					cons2.gridy = 0;
					cons2.gridwidth = 1;
					cons2.gridheight = 1;
					cons2.weightx = 0.1;
					cons2.fill = GridBagConstraints.HORIZONTAL;
					cons2.insets = new Insets(10,10,10,10);
					panelModificacion.add(texto,cons2);
					
					cons2.gridy = 1;
					cons2.fill = GridBagConstraints.NONE;
					panelModificacion.add(aceptar,cons2);
					
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
					
					App.getVentana().setEnabled(false);
					ventana.pack();
					ventana.setLocationRelativeTo(null);
					ventana.setVisible(true);
				}
			}
		});
		
		bajaTrayecto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				trayectoEliminado = false;
				JDialog ventana = new JDialog(App.getVentana(),"Eliminar Trayecto");
				JPanel panelBaja = new JPanel(new GridBagLayout());
				panelBaja.setBackground(UIManager.getColor("CheckBox.focus"));
				ventana.setContentPane(panelBaja);
				GridBagConstraints cons2 = new GridBagConstraints();
				
				EtiquetaJ texto = new EtiquetaJ("¿Desea eliminar el trayecto?");
				EtiquetaJ texto2 = new EtiquetaJ("Se eliminarán  TODAS las rutas");
				BotonJ si = new BotonJ("Sí");
				BotonJ no = new BotonJ("No");
				
				cons2.gridx = 0;
				cons2.gridy = 0;
				cons2.gridwidth = 2;
				cons2.gridheight = 1;
				cons2.weightx = 0.1;
				cons2.fill = GridBagConstraints.NONE;
				cons2.insets = new Insets(10,10,5,10);
				panelBaja.add(texto,cons2);
				
				cons2.gridy = 1;
				cons2.insets = new Insets(0,10,10,10);
				panelBaja.add(texto2,cons2);
				
				cons2.gridy = 2;
				cons2.gridwidth = 1;
				cons2.fill = GridBagConstraints.NONE;
				cons2.anchor = GridBagConstraints.EAST;
				panelBaja.add(si,cons2);
				
				cons2.gridx = 1;
				cons2.anchor = GridBagConstraints.WEST;
				panelBaja.add(no,cons2);
				
				App.getVentana().setEnabled(false);
				ventana.pack();
				ventana.setLocationRelativeTo(null);
				ventana.setVisible(true);
				
				si.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						PostgreDB.eliminarTrayecto(idLinea);
						trayectoEliminado = true;
						JPanel completado = new JPanel(new GridBagLayout());
						completado.setBackground(UIManager.getColor("CheckBox.focus"));
						
						EtiquetaJ mensaje = new EtiquetaJ("El trayecto fue eliminado");
						mensaje.setForeground(new Color(0,150,0));
						BotonJ aceptar = new BotonJ("Aceptar");
						
						cons2.gridy = 0;
						cons2.anchor = GridBagConstraints.CENTER;
						cons2.insets = new Insets(10,10,10,10);
						completado.add(mensaje,cons2);
						
						cons2.gridy = 1;
						completado.add(aceptar,cons2);
						
						ventana.setContentPane(completado);
						ventana.revalidate();
						ventana.repaint();
						ventana.pack();
						actualizarTabla(idLinea);
						actualizarCamino();
						
						aceptar.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								ventana.dispose();
								App.getVentana().setEnabled(true);
								App.getVentana().setVisible(true);
							}
						});	
					}
				});
				
				no.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ventana.dispose();
						App.getVentana().setEnabled(true);
						App.getVentana().setVisible(true);
					}						
				});
				
				ventana.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						if(trayectoEliminado) {
							actualizarTabla(idLinea);
							actualizarCamino();
						}
						App.getVentana().setEnabled(true);
						App.getVentana().setVisible(true);
					}
				});
			}
		});
	}
	
	public void actualizarTabla(Integer idLinea) {
		rutas = PostgreDB.recuperarRutas(Optional.of(idLinea), Optional.ofNullable(null), Optional.ofNullable(null));
		Vector<Vector<Object>> data =  new Vector<Vector<Object>>();
		
		for(Ruta r : rutas) {
			Vector<Object> fila = new Vector<Object>();
			fila.add(r.getId());
			fila.add(r.getOrigen().getNombre());
			fila.add(r.getDestino().getNombre());
			fila.add(r.getDistancia());
			fila.add(r.getDuracion());
			fila.add(r.getCapacidad());
			fila.add(r.getCosto());
			fila.add(r.activa());
			data.add(fila);
		}
		
		modelo.setData(data);
		modelo.fireTableDataChanged();
	}

	public void actualizarCamino() {
		
		if(rutas.isEmpty()) {
			camino.setText(" No hay ningún trayecto definido... ");
			camino.setForeground(Color.LIGHT_GRAY);
		}
		else {
			String caminoString=" ";
			Vector<Ruta> rutasAux = new Vector<Ruta>();
			rutasAux.addAll(rutas);
			
			while(rutasAux.size() > 1) {
				Ruta prox = buscarPrimera(rutasAux);
				caminoString+=(prox.getOrigen().getNombre()+"  ->  ");
				rutasAux.remove(prox);
			}
			caminoString+=rutasAux.get(0).getOrigen().getNombre()+"  ->  "+rutasAux.get(0).getDestino().getNombre()+" ";
			camino.setText(caminoString);
			camino.setForeground(Color.BLACK);
		}
		
		camino.revalidate();
		camino.repaint();
	}
	
	private Ruta buscarPrimera(Vector<Ruta> rutas) {
		Ruta rtdo = null;
		Map<Estacion,Integer> tieneEntrada = new LinkedHashMap<Estacion,Integer>();
		
		for(Ruta r : rutas) {
			if(!tieneEntrada.containsKey(r.getOrigen())) {
				tieneEntrada.put(r.getOrigen(), 0);
			}
			
			if(tieneEntrada.containsKey(r.getDestino())) {
				tieneEntrada.replace(r.getDestino(),1);
			}
			else {
				tieneEntrada.put(r.getDestino(),1);
			}
		}
		
		Estacion origen = null;
		Set<Estacion> estacionesDistintas = tieneEntrada.keySet();
		for(Estacion e : estacionesDistintas) {
			if(tieneEntrada.get(e) == 0) {
				origen = e;
				break;
			}
		}
		
		for(Ruta r : rutas) {
			if(r.getOrigen().equals(origen)) {
				rtdo = r;
				break;
			}
		}
		
		return rtdo;
	}
	
	private Ruta buscarUltima(Vector<Ruta> rutas) {
		Ruta rtdo = null;
		Map<Estacion,Integer> tieneEntrada = new LinkedHashMap<Estacion,Integer>();
		
		for(Ruta r : rutas) {
			if(!tieneEntrada.containsKey(r.getDestino())) {
				tieneEntrada.put(r.getDestino(), 0);
			}
			
			if(tieneEntrada.containsKey(r.getOrigen())) {
				tieneEntrada.replace(r.getOrigen(),1);
			}
			else {
				tieneEntrada.put(r.getOrigen(),1);
			}
		}
		
		Estacion destino = null;
		Set<Estacion> estacionesDistintas = tieneEntrada.keySet();
		for(Estacion e : estacionesDistintas) {
			if(tieneEntrada.get(e) == 0) {
				destino = e;
				break;
			}
		}
		
		for(Ruta r : rutas) {
			if(r.getDestino().equals(destino)) {
				rtdo = r;
				break;
			}
		}
		
		return rtdo;
	}
}
