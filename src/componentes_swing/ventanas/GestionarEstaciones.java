package componentes_swing.ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;

import clase_app.App;
import clases.*;
import componentes_swing.*;
import componentes_swing.modelos_tablas.ModeloTablaEstaciones;
import postgre_db.PostgreDB;

public class GestionarEstaciones extends JPanel {

	private TablaJ tabla;
	private ModeloTablaEstaciones modelo;
	private Boolean operativaCambiado;
	private Boolean estacionEliminada;
	
	public GestionarEstaciones() {
		super();			
		
		setBorder(new EmptyBorder(10, 20, 10, 20));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		panelBusqueda();
		panelResultados();
		panelBotones();
		
	}
	
	private void panelBusqueda() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Buscar estaciones");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		
		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setLayout(new GridBagLayout());
		panelBusqueda.setBackground(Color.WHITE);
		panelBusqueda.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(20,20,10,20);
		cons.anchor = GridBagConstraints.NORTH;
		
		add(panelBusqueda,cons);
			
		CampoJ cId = new CampoJ();
		EtiquetaJ eId = new EtiquetaJ("ID");
		CampoJ cNombre = new CampoJ();
		EtiquetaJ eNombre = new EtiquetaJ("Nombre");
		ListaHorarios lApertura = new ListaHorarios();
		EtiquetaJ eApertura = new EtiquetaJ("Horario apertura");
		ListaHorarios lCierre = new ListaHorarios();
		EtiquetaJ eCierre = new EtiquetaJ("Horario cierre");
		CheckBoxJ cOperativa = new CheckBoxJ("Operativa");
		BotonJ buscar = new BotonJ("Buscar");
		
		buscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Optional<Integer> id;
				Optional<String> nombre;
				Optional<LocalTime> apertura;
				Optional<LocalTime> cierre;
				Optional<Boolean> operativa;
				
				if(cId.getText().length() == 0) {
					id = Optional.ofNullable(null);
				}
				else {
					id = Optional.of(Integer.parseInt(cId.getText()));
				}
				
				if(cNombre.getText().length() == 0) {
					nombre = Optional.ofNullable(null);
				}
				else {
					nombre = Optional.of(cNombre.getText());
				}
				
				if(((String)lApertura.getSelectedItem()).length() == 0) {
					apertura = Optional.ofNullable(null);
				}
				else {
					apertura = Optional.of(LocalTime.parse(((String)lApertura.getSelectedItem()),DateTimeFormatter.ofPattern("HH:mm")));
				}
				
				if(((String)lCierre.getSelectedItem()).length() == 0) {
					cierre = Optional.ofNullable(null);
				}
				else {
					cierre = Optional.of(LocalTime.parse(((String)lCierre.getSelectedItem()),DateTimeFormatter.ofPattern("HH:mm")));
				}
				
				if(cOperativa.isSelected()) {
					operativa = Optional.of(true);
				}
				else {
					operativa = Optional.of(false);
				}
				
				actualizarTabla(id,nombre,apertura,cierre,operativa);
			}
		});
		
		cons.anchor = GridBagConstraints.CENTER;
		cons.weighty = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelBusqueda.add(eId,cons);
		
		cons.gridx = 1;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelBusqueda.add(cId,cons);
		
		cons.gridx = 2;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelBusqueda.add(eNombre,cons);
		
		cons.gridx = 3;
		cons.insets = new Insets(10,5,10,20);
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		panelBusqueda.add(cNombre,cons);
		
		cons.gridx = 4;
		cons.weightx = 0.02;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.WEST;
		cons.insets = new Insets(10,20,10,20);
		cOperativa.setSelected(true);
		panelBusqueda.add(cOperativa,cons);
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,20,5);
		panelBusqueda.add(eApertura,cons);
		
		cons.gridx=1;
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		cons.insets = new Insets(10,5,20,20);
		panelBusqueda.add(lApertura,cons);
		
		cons.gridx = 2;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,20,5);
		panelBusqueda.add(eCierre,cons);
		
		cons.gridx = 3;
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		cons.insets = new Insets(10,5,20,20);
		panelBusqueda.add(lCierre,cons);
		
		cons.gridx = 4;
		cons.weightx = 0.02;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(10,20,20,20);
		panelBusqueda.add(buscar,cons);
	}
	
	private void panelResultados() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Resultados");
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
		
		modelo = new ModeloTablaEstaciones();
		tabla = new TablaJ(modelo);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(10);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(30);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(30);
		tabla.getColumnModel().getColumn(4).setPreferredWidth(50);
		JScrollPane scroll = new JScrollPane(tabla);
		
		actualizarTabla(Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null), Optional.of(true));
		
		cons.gridy = 0;
		panelResultados.add(scroll,cons);
		
		tabla.getDefaultEditor(Boolean.class).addCellEditorListener(new CellEditorListener() {

			@Override
			public void editingStopped(ChangeEvent e) {
				operativaCambiado = false;
				
				Integer fila = tabla.getSelectedRow();
				Integer columna = tabla.getSelectedColumn();
				Boolean valor = (Boolean) modelo.getValueAt(fila,columna);
				Integer idEstacion = (Integer) modelo.getValueAt(tabla.getSelectedRow(), 0);
				String nomEstacion = (String) modelo.getValueAt(tabla.getSelectedRow(), 1); 
				
				JDialog ventana = new JDialog(App.getVentana(),"Mantenimiento");
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
				cons2.fill = GridBagConstraints.HORIZONTAL;
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
				
				if(!valor) texto.setText("¿Desea iniciar el mantenimiendo de la estación "+nomEstacion+"?");
				else texto.setText("¿Desea terminar el mantenimiendo de la estación "+nomEstacion+"?");
				
				ventana.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						if(!operativaCambiado) modelo.setValueAt(!valor, fila, columna);
						else actualizarTabla();
						
						App.getVentana().setEnabled(true);
						App.getVentana().setVisible(true);
					}	
				});
				
				si.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(!valor) {
							JPanel observaciones = new JPanel(new GridBagLayout());
							observaciones.setBackground(UIManager.getColor("CheckBox.focus"));
							EtiquetaJ eObservaciones = new EtiquetaJ("Observaciones sobre el mantenimiento");
							AreaTextoJ cObservaciones = new AreaTextoJ();
							JPanel botones = new JPanel(new GridBagLayout());
							botones.setBackground(UIManager.getColor("CheckBox.focus"));
							EtiquetaJ caracteres = new EtiquetaJ("Máximo 500 caracteres (actual "+cObservaciones.getText().length()+")");
							caracteres.setForeground(Color.RED);
							BotonJ aceptar = new BotonJ("Aceptar");
							BotonJ cancelar = new BotonJ("Cancelar");
							
							cons2.gridx=0;
							cons2.gridy=2;
							cons2.anchor = GridBagConstraints.EAST;
							observaciones.add(botones,cons2);
							
							cons2.gridy=0;
							cons2.gridwidth=3;
							cons2.fill = GridBagConstraints.NONE;
							cons2.anchor = GridBagConstraints.WEST;
							observaciones.add(eObservaciones,cons2);
							
							cons2.gridy = 1;
							cons2.fill = GridBagConstraints.HORIZONTAL;
							cons2.anchor = GridBagConstraints.CENTER;
							observaciones.add(cObservaciones,cons2);
							
							cons2.gridx=0;
							cons2.gridy = 0;
							cons2.gridwidth=1;
							cons2.fill = GridBagConstraints.NONE;
							botones.add(caracteres,cons2);
							
							cons2.gridx=1;
							cons2.anchor = GridBagConstraints.CENTER;
							botones.add(aceptar,cons2);
							
							cons2.gridx=2;
							cons2.anchor = GridBagConstraints.CENTER;
							botones.add(cancelar,cons2);
							
							ventana.setContentPane(observaciones);
							ventana.revalidate();
							ventana.repaint();	
							ventana.setSize(500,416);
							ventana.setLocationRelativeTo(null);
							caracteres.setVisible(false);
												
							aceptar.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									if(cObservaciones.getText().length() <= 500) {
										panelConfirmacionMantenimiento(ventana, valor, idEstacion, nomEstacion,cObservaciones.getText());
									}
									else {
										caracteres.setText("Máximo 500 caracteres (actual "+cObservaciones.getText().length()+")");
										caracteres.setVisible(true);
									}
								}
							});
							
							cancelar.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									modelo.setValueAt(!valor, fila, columna);
									ventana.dispose();
									App.getVentana().setEnabled(true);
									App.getVentana().setVisible(true);
								}
							});
						}
						else {
							panelConfirmacionMantenimiento(ventana, valor, idEstacion, nomEstacion, null);
						}
						
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
				
				App.getVentana().setEnabled(false);
				ventana.pack();
				ventana.setLocationRelativeTo(null);
				ventana.setVisible(true);
			}

			@Override
			public void editingCanceled(ChangeEvent e) {				
			}
			
		});
	}
	
	private void panelBotones() {
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
		
		BotonJ altaEstacion = new BotonJ(" Añadir ");
		BotonJ modificarEstacion = new BotonJ("Modificar"); 
		BotonJ bajaEstacion = new BotonJ("Eliminar");
		BotonJ volver = new BotonJ("Volver atrás");
		
		cons.gridy=0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(0,10,20,10);
		panelBotones.add(altaEstacion,cons);
		
		cons.gridx=1;
		panelBotones.add(modificarEstacion,cons);
		
		cons.gridx=2;
		panelBotones.add(bajaEstacion,cons);
		
		cons.gridx = 3;
		cons.insets = new Insets(0,10,20,20);
		panelBotones.add(volver,cons);
		
		volver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.menuPrincipal();
			}
		});
		
		altaEstacion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Vector<Estacion> estaciones = PostgreDB.recuperarEstaciones(Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null));
				Integer maxID; 
				if(estaciones.size() > 0) maxID = estaciones.stream().map(est -> est.getId()).max((id1,id2) -> id1.compareTo(id2)).get();
				else maxID = 0;
				App.altaEstacion(maxID+1);
			}
		});
		
		modificarEstacion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer fila = tabla.getSelectedRow();
				
				if(fila != -1) {
					Integer idS = (Integer) modelo.getValueAt(fila, 0);
					String nombreS = (String) modelo.getValueAt(fila, 1);
					String aperturaS = (String) modelo.getValueAt(fila, 2);
					String cierreS = (String) modelo.getValueAt(fila, 3);
					
					App.modificarEstacion(idS, nombreS, aperturaS, cierreS);
				}
				else {
					JDialog ventana = new JDialog(App.getVentana(),"Modificar Estación");
					JPanel panelModificacion = new JPanel(new GridBagLayout());
					panelModificacion.setBackground(UIManager.getColor("CheckBox.focus"));
					ventana.setContentPane(panelModificacion);
					GridBagConstraints cons2 = new GridBagConstraints();
					EtiquetaJ texto = new EtiquetaJ("Seleccione una estación antes");
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
		
		bajaEstacion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				estacionEliminada = false;
				Integer fila = tabla.getSelectedRow();
				JDialog ventana = new JDialog(App.getVentana(),"Baja Estación");
				JPanel panelBaja = new JPanel(new GridBagLayout());
				panelBaja.setBackground(UIManager.getColor("CheckBox.focus"));
				ventana.setContentPane(panelBaja);
				GridBagConstraints cons2 = new GridBagConstraints();
				
				if(fila != -1) {
					Integer idEstacion = (Integer) modelo.getValueAt(fila, 0);
					String nombreEstacion = (String) modelo.getValueAt(fila, 1); 
					
					EtiquetaJ texto = new EtiquetaJ("¿Desea eliminar la estación '"+nombreEstacion+"'?");
					BotonJ si = new BotonJ("Sí");
					BotonJ no = new BotonJ("No");
					
					cons2.gridx = 0;
					cons2.gridy = 0;
					cons2.gridwidth = 2;
					cons2.gridheight = 1;
					cons2.weightx = 0.1;
					cons2.fill = GridBagConstraints.HORIZONTAL;
					cons2.insets = new Insets(10,10,10,10);
					panelBaja.add(texto,cons2);
					
					cons2.gridy = 1;
					cons2.gridwidth = 1;
					cons2.fill = GridBagConstraints.NONE;
					cons2.anchor = GridBagConstraints.EAST;
					panelBaja.add(si,cons2);
					
					cons2.gridx = 1;
					cons2.anchor = GridBagConstraints.WEST;
					panelBaja.add(no,cons2);
					
					si.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							PostgreDB.eliminarEstacion(idEstacion);
							estacionEliminada = true;
							JPanel completado = new JPanel(new GridBagLayout());
							completado.setBackground(UIManager.getColor("CheckBox.focus"));
							
							EtiquetaJ mensaje = new EtiquetaJ("La estación '"+nombreEstacion+"' fue eliminada");
							mensaje.setForeground(new Color(0,150,0));
							BotonJ aceptar = new BotonJ("Aceptar");
							
							cons2.gridy = 0;
							cons2.anchor = GridBagConstraints.CENTER;
							completado.add(mensaje,cons2);
							
							cons2.gridy = 1;
							completado.add(aceptar,cons2);
							
							ventana.setContentPane(completado);
							ventana.revalidate();
							ventana.repaint();
							actualizarTabla();
							
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
				}
				else {
					EtiquetaJ texto = new EtiquetaJ("Seleccione una estación antes");
					BotonJ aceptar = new BotonJ("Aceptar");
					
					cons2.gridx = 0;
					cons2.gridy = 0;
					cons2.gridwidth = 1;
					cons2.gridheight = 1;
					cons2.weightx = 0.1;
					cons2.fill = GridBagConstraints.HORIZONTAL;
					cons2.insets = new Insets(10,10,10,10);
					panelBaja.add(texto,cons2);
					
					cons2.gridy = 1;
					cons2.fill = GridBagConstraints.NONE;
					panelBaja.add(aceptar,cons2);
					
					aceptar.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							ventana.dispose();
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
						}						
					});
				}
				
				ventana.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						if(estacionEliminada) actualizarTabla();
						App.getVentana().setEnabled(true);
						App.getVentana().setVisible(true);
					}
				});
				
				App.getVentana().setEnabled(false);
				ventana.pack();
				ventana.setLocationRelativeTo(null);
				ventana.setVisible(true);
			}
		});
	}
	
	private void panelConfirmacionMantenimiento(JDialog ventana, Boolean valor, Integer idEstacion, String nomEstacion, String observaciones) {
		operativaCambiado = true;
		
		if(!valor) {
			Vector<Mantenimiento> mantenimientos = PostgreDB.recuperarMantenimientos(Optional.ofNullable(null), Optional.ofNullable(null));
			Integer maxID;
			if(mantenimientos.size() > 0) maxID = mantenimientos.stream().map(est -> est.getId()).max((id1,id2) -> id1.compareTo(id2)).get();
			else maxID = 0;
			PostgreDB.cargarMantenimiento(maxID+1,idEstacion,observaciones);
		}
		else {
			PostgreDB.finalizarMantenimiento(idEstacion);
		}
		
		JPanel realizado = new JPanel(new GridBagLayout());
		realizado.setBackground(UIManager.getColor("CheckBox.focus"));
		GridBagConstraints cons2 = new GridBagConstraints();
		EtiquetaJ mensaje = new EtiquetaJ();
		BotonJ aceptar = new BotonJ("Aceptar");
		mensaje.setForeground(new Color(0,150,0));
		
		if(!valor) mensaje.setText("La estación '"+nomEstacion+"' está ahora en mantenimiento.");
		else mensaje.setText("El mantenimiento de la estación '"+nomEstacion+"' ha sido finalizado.");
		
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
				actualizarTabla();
				App.getVentana().setEnabled(true);
				App.getVentana().setVisible(true);
			}
		});
	}
	
	private void actualizarTabla(Optional<Integer> id, Optional<String> nombre, Optional<LocalTime> apertura, Optional<LocalTime> cierre, Optional<Boolean> operativa) {
		Vector<Estacion> estaciones = PostgreDB.recuperarEstaciones(id, nombre, apertura, cierre, operativa);	
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		for(Estacion e : estaciones) {
			Vector<Object> fila = new Vector<Object>();
			fila.add(e.getId());
			fila.add(e.getNombre());
			fila.add(e.getApertura().format(DateTimeFormatter.ofPattern("HH:mm")));
			fila.add(e.getCierre().format(DateTimeFormatter.ofPattern("HH:mm")));
			fila.add(e.operativa());
			data.add(fila);
		}
		
		modelo.setData(data);
		modelo.fireTableDataChanged();
	}
	
	public void actualizarTabla() {
		JPanel panelBusqueda = (JPanel) this.getComponent(0);
		
		CampoJ cId = (CampoJ) panelBusqueda.getComponent(1);
		CampoJ cNombre = (CampoJ) panelBusqueda.getComponent(3);
		ListaHorarios lApertura = (ListaHorarios) panelBusqueda.getComponent(6);
		ListaHorarios lCierre = (ListaHorarios) panelBusqueda.getComponent(8);
		CheckBoxJ cOperativa = (CheckBoxJ) panelBusqueda.getComponent(4);
		
		Optional<Integer> id;
		Optional<String> nombre;
		Optional<LocalTime> apertura;
		Optional<LocalTime> cierre;
		Optional<Boolean> operativa;
		
		if(cId.getText().length() == 0) {
			id = Optional.ofNullable(null);
		}
		else {
			id = Optional.of(Integer.parseInt(cId.getText()));
		}
		
		if(cNombre.getText().length() == 0) {
			nombre = Optional.ofNullable(null);
		}
		else {
			nombre = Optional.of(cNombre.getText());
		}
		
		if(((String)lApertura.getSelectedItem()).length() == 0) {
			apertura = Optional.ofNullable(null);
		}
		else {
			apertura = Optional.of(LocalTime.parse(((String)lApertura.getSelectedItem()),DateTimeFormatter.ofPattern("HH:mm")));
		}
		
		if(((String)lCierre.getSelectedItem()).length() == 0) {
			cierre = Optional.ofNullable(null);
		}
		else {
			cierre = Optional.of(LocalTime.parse(((String)lCierre.getSelectedItem()),DateTimeFormatter.ofPattern("HH:mm")));
		}
		
		if(cOperativa.isSelected()) {
			operativa = Optional.of(true);
		}
		else {
			operativa = Optional.of(false);
		}
		
		actualizarTabla(id,nombre,apertura,cierre,operativa);
		
	}
}
