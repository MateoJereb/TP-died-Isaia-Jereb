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
import java.util.Optional;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
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
import componentes_swing.modelos_tablas.ModeloTablaLineas;
import postgre_db.PostgreDB;

public class GestionarLineas extends JPanel {

	private TablaJ tabla;
	private ModeloTablaLineas modelo;
	private Boolean activaCambiado;
	private Boolean lineaEliminada;
	
	public GestionarLineas() {
		super();			
		setBorder(new EmptyBorder(10, 20, 10, 20));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		panelBusqueda();
		panelResultados();
		panelBotones();
	}
	
	public void panelBusqueda() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Buscar lineas");
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
		ListaColores lColor = new ListaColores();
		EtiquetaJ eColor = new EtiquetaJ("Color");
		CheckBoxJ cActiva = new CheckBoxJ("Activa");
		BotonJ buscar = new BotonJ("Buscar");
		
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
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		panelBusqueda.add(cNombre,cons);
		
		cons.gridx=4;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(10,20,10,5);
		cons.anchor = GridBagConstraints.WEST;
		cActiva.setSelected(true);
		panelBusqueda.add(cActiva,cons);
		
		cons.gridy = 1;
		cons.gridx = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelBusqueda.add(eColor,cons);
		
		cons.gridx=1;
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		cons.insets = new Insets(10,5,10,20);
		panelBusqueda.add(lColor,cons);
		
		cons.gridx=4;
		cons.weightx = 0.02;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(10,20,20,20);
		panelBusqueda.add(buscar,cons);
		
		buscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Optional<Integer> id;
				Optional<String> nombre;
				Optional<Color> color;
				Optional<Boolean> activa;
				
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
				
				if(((String)lColor.getSelectedItem()).length() == 0) {
					color = Optional.ofNullable(null);
				}
				else {
					color = Optional.of(new Color(Integer.parseInt((String) lColor.getSelectedItem())));
				}
				
				if(cActiva.isSelected()) {
					activa = Optional.of(true);
				}
				else {
					activa = Optional.of(false);
				}
				
				actualizarTabla(id,nombre,color,activa);
			}
		});
	}
	
	public void panelResultados() {
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
		
		modelo = new ModeloTablaLineas();
		tabla = new TablaJ(modelo);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(10);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(200);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(50);
		JScrollPane scroll = new JScrollPane(tabla);
		
		actualizarTabla(Optional.ofNullable(null),Optional.ofNullable(null),Optional.ofNullable(null),Optional.of(true));
		
		cons.gridy = 0;
		panelResultados.add(scroll,cons);
		
		tabla.getDefaultEditor(Boolean.class).addCellEditorListener(new CellEditorListener() {
			@Override
			public void editingStopped(ChangeEvent e) {
				activaCambiado = false;
				
				Integer fila = tabla.getSelectedRow();
				Integer columna = tabla.getSelectedColumn();
				Boolean valor = (Boolean) modelo.getValueAt(fila,columna);
				Integer idLinea = (Integer) modelo.getValueAt(tabla.getSelectedRow(), 0);
				String nomLinea = (String) modelo.getValueAt(tabla.getSelectedRow(), 1);
				
				JDialog ventana = new JDialog(App.getVentana(),"Cambiar estado linea");
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
				
				if(!valor) texto.setText("¿Desea desactivar la linea '"+nomLinea+"'?");
				else texto.setText("¿Desea activar la linea '"+nomLinea+"'?");
				
				ventana.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						if(!activaCambiado) modelo.setValueAt(!valor, fila, columna);
						else actualizarTabla();
						
						App.getVentana().setEnabled(true);
						App.getVentana().setVisible(true);
					}	
				});
				
				si.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						PostgreDB.cambiarEstadoLinea(idLinea, valor);
						
						JPanel realizado = new JPanel(new GridBagLayout());
						realizado.setBackground(UIManager.getColor("CheckBox.focus"));
						GridBagConstraints cons2 = new GridBagConstraints();
						EtiquetaJ mensaje = new EtiquetaJ();
						BotonJ aceptar = new BotonJ("Aceptar");
						mensaje.setForeground(new Color(0,150,0));
						
						if(!valor) mensaje.setText("La linea '"+nomLinea+"' está ahora inactiva.");
						else mensaje.setText("La linea '"+nomLinea+"' está ahora activa.");
						
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
			public void editingCanceled(ChangeEvent e) { }
		});
		
	}
	
	public void panelBotones() {
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
		
		BotonJ altaLinea = new BotonJ(" Añadir ");
		BotonJ modificarLinea = new BotonJ("Modificar"); 
		BotonJ bajaLinea = new BotonJ("Eliminar");
		BotonJ volver = new BotonJ("Volver atrás");
		
		cons.gridy=0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(0,10,20,10);
		panelBotones.add(altaLinea,cons);
		
		cons.gridx=1;
		panelBotones.add(modificarLinea,cons);
		
		cons.gridx=2;
		panelBotones.add(bajaLinea,cons);
		
		cons.gridx = 3;
		cons.insets = new Insets(0,10,20,20);
		panelBotones.add(volver,cons);
		
		volver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.menuPrincipal();
			}
		});
		
		altaLinea.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Vector<Linea> lineas = PostgreDB.recuperarLineas(Optional.ofNullable(null),Optional.ofNullable(null),Optional.ofNullable(null),Optional.ofNullable(null));
				Integer maxID;
				if(lineas.size() > 0) maxID = lineas.stream().map(l -> l.getId()).max((id1,id2) -> id1.compareTo(id2)).get();
				else maxID = 0;
				App.altaLinea(maxID+1);
			}
		});
		
		modificarLinea.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer fila = tabla.getSelectedRow();
				
				if(fila != -1) {
					Integer idS = (Integer) modelo.getValueAt(fila, 0);
					String nombreS = (String) modelo.getValueAt(fila, 1);
					Color colorS = (Color) modelo.getValueAt(fila, 2);
										
					App.modificarLinea(idS, nombreS, colorS);
				}
				else {
					JDialog ventana = new JDialog(App.getVentana(),"Modificar Linea");
					JPanel panelModificacion = new JPanel(new GridBagLayout());
					panelModificacion.setBackground(UIManager.getColor("CheckBox.focus"));
					ventana.setContentPane(panelModificacion);
					GridBagConstraints cons2 = new GridBagConstraints();
					EtiquetaJ texto = new EtiquetaJ("Seleccione una linea antes");
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
		
		bajaLinea.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lineaEliminada = false;
				Integer fila = tabla.getSelectedRow();
				JDialog ventana = new JDialog(App.getVentana(),"Baja Linea");
				JPanel panelBaja = new JPanel(new GridBagLayout());
				panelBaja.setBackground(UIManager.getColor("CheckBox.focus"));
				ventana.setContentPane(panelBaja);
				GridBagConstraints cons2 = new GridBagConstraints();
				
				if(fila != -1) {
					Integer idLinea = (Integer) modelo.getValueAt(fila, 0);
					String nombreLinea = (String) modelo.getValueAt(fila, 1); 
					
					EtiquetaJ texto = new EtiquetaJ("¿Desea eliminar la linea '"+nombreLinea+"'?");
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
							PostgreDB.eliminarLinea(idLinea);
							lineaEliminada = true;
							JPanel completado = new JPanel(new GridBagLayout());
							completado.setBackground(UIManager.getColor("CheckBox.focus"));
							
							EtiquetaJ mensaje = new EtiquetaJ("La linea '"+nombreLinea+"' fue eliminada");
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
					EtiquetaJ texto = new EtiquetaJ("Seleccione una linea antes");
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
						if(lineaEliminada) actualizarTabla();
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
	
	private void actualizarTabla(Optional<Integer> id, Optional<String> nombre, Optional<Color> color, Optional<Boolean> activa) {
		Vector<Linea> lineas = PostgreDB.recuperarLineas(id, nombre, color, activa);
		Vector<Vector<Object>> data =  new Vector<Vector<Object>>();
		
		for(Linea l : lineas) {
			Vector<Object> fila = new Vector<Object>();
			fila.add(l.getId());
			fila.add(l.getNombre());
			fila.add(l.getColor());
			fila.add(l.activa());
			data.add(fila);
		}
		
				
		modelo.setData(data);
		modelo.fireTableDataChanged();
	}
	
	public void actualizarTabla() {
		JPanel panelBusqueda = (JPanel) this.getComponent(0);
		
		CampoJ cId = (CampoJ) panelBusqueda.getComponent(1);
		CampoJ cNombre = (CampoJ) panelBusqueda.getComponent(3);
		ListaColores lColor = (ListaColores) panelBusqueda.getComponent(6);
		CheckBoxJ cActiva = (CheckBoxJ) panelBusqueda.getComponent(4);
		
		Optional<Integer> id;
		Optional<String> nombre;
		Optional<Color> color;
		Optional<Boolean> activa;
		
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
		
		if(((String)lColor.getSelectedItem()).length() == 0) {
			color = Optional.ofNullable(null);
		}
		else {
			color = Optional.of(new Color(Integer.parseInt((String) lColor.getSelectedItem())));
		}
		
		if(cActiva.isSelected()) {
			activa = Optional.of(true);
		}
		else {
			activa = Optional.of(false);
		}
		
		actualizarTabla(id,nombre,color,activa);
	}
}
