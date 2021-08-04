package clase_app;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Optional;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import clases.*;
import componentes_swing.*;
import componentes_swing.ventanas.*;


public class App {

	private static JFrame ventana = new JFrame();
	
	public static void main(String[] args) {
		menuPrincipal();
		ImageIcon icono = new ImageIcon("resources\\Logo.png");
		Image imagen = icono.getImage();
		ventana.setIconImage(imagen);
		ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		ventana.setSize(1000,600);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	public static JFrame getVentana() {
		return ventana;
	}
	
	public static void menuPrincipal() {
		MenuPrincipal panel = new MenuPrincipal();
		ventana.setContentPane(panel);
		ventana.setTitle("Sistema de Gestión Transporte Multimodal");
		ventana.revalidate();
		ventana.repaint();
	}
	
	public static void gestionarEstaciones() {
		GestionarEstaciones panel = new GestionarEstaciones();
		ventana.setContentPane(panel);
		ventana.setTitle("SGTM - Gestionar Estaciones");
		ventana.revalidate();
		ventana.repaint();
	}
	
	public static void altaEstacion(Integer proxID) {
		GestionarEstaciones panelGestionar = (GestionarEstaciones)ventana.getContentPane();
		JDialog ventanaAlta  = new JDialog(ventana,"Alta Estación");
		AltaEstacion panel = new AltaEstacion(panelGestionar, proxID);
		ventanaAlta.setContentPane(panel);
		ventanaAlta.setSize(500,320);
		ventanaAlta.setLocationRelativeTo(null);
		ventana.setEnabled(false);
		
		BotonJ aceptar = (BotonJ) ((JPanel) panel.getComponent(1)).getComponent(1);
		BotonJ cancelar = (BotonJ) ((JPanel) panel.getComponent(1)).getComponent(2);
		
		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(panel.camposCompletos()) {
					JPanel completado = new JPanel(new GridBagLayout());
					completado.setBackground(UIManager.getColor("CheckBox.focus"));
					
					EtiquetaJ mensaje = new EtiquetaJ("La estación '"+((CampoJ)((JPanel) panel.getComponent(0)).getComponent(1)).getText()+"' fue creada");
					mensaje.setForeground(new Color(0,150,0));
					BotonJ aceptar = new BotonJ("Aceptar");
					GridBagConstraints cons = new GridBagConstraints();
					
					cons.insets = new Insets(10,10,10,10);
					cons.gridy = 0;
					cons.anchor = GridBagConstraints.CENTER;
					completado.add(mensaje,cons);
					
					cons.gridy = 1;
					completado.add(aceptar,cons);
					
					ventanaAlta.setContentPane(completado);
					ventanaAlta.revalidate();
					ventanaAlta.repaint();
					ventanaAlta.pack();
					ventanaAlta.setLocationRelativeTo(null);
					
					aceptar.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							ventanaAlta.dispose();
							ventana.setEnabled(true);
							ventana.setVisible(true);
						}
					});
				}
			}
		});;
		
		cancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaAlta.dispose();
				ventana.setEnabled(true);
				ventana.setVisible(true);
			}
		});;
		
		ventanaAlta.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ventana.setEnabled(true);
				ventana.setVisible(true);
			}	
		});
		
		ventanaAlta.setVisible(true);
	}
	
	public static void modificarEstacion(Integer id, String nombre, String apertura, String cierre) {
		GestionarEstaciones panelGestionar = (GestionarEstaciones)ventana.getContentPane();
		JDialog ventanaModificacion = new JDialog(ventana,"Modificar Estación");
		ModificarEstacion panel = new ModificarEstacion(panelGestionar,id,nombre,apertura,cierre);
		ventanaModificacion.setContentPane(panel);
		ventanaModificacion.setSize(500,300);
		ventanaModificacion.setLocationRelativeTo(null);
		ventana.setEnabled(false);
		
		BotonJ aceptar = (BotonJ) ((JPanel) panel.getComponent(1)).getComponent(1);
		BotonJ cancelar = (BotonJ) ((JPanel) panel.getComponent(1)).getComponent(2);
		
		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(panel.camposCompletos()) {
					JPanel completado = new JPanel(new GridBagLayout());
					completado.setBackground(UIManager.getColor("CheckBox.focus"));
					
					EtiquetaJ mensaje = new EtiquetaJ("La estación '"+nombre+"' fue modificada");
					mensaje.setForeground(new Color(0,150,0));
					BotonJ aceptar = new BotonJ("Aceptar");
					GridBagConstraints cons = new GridBagConstraints();
					
					cons.insets = new Insets(10,10,10,10);
					cons.gridy = 0;
					cons.anchor = GridBagConstraints.CENTER;
					completado.add(mensaje,cons);
					
					cons.gridy = 1;
					completado.add(aceptar,cons);
					
					ventanaModificacion.setContentPane(completado);
					ventanaModificacion.revalidate();
					ventanaModificacion.repaint();
					ventanaModificacion.pack();
					ventanaModificacion.setLocationRelativeTo(null);
					
					aceptar.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							ventanaModificacion.dispose();
							ventana.setEnabled(true);
							ventana.setVisible(true);
						}
					});
				}
			}
		});;
		
		cancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaModificacion.dispose();
				ventana.setEnabled(true);
				ventana.setVisible(true);
			}
		});;
		
		ventanaModificacion.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ventana.setEnabled(true);
				ventana.setVisible(true);
			}	
		});
		
		ventanaModificacion.setVisible(true);		
	}
	
	public static void gestionarLineas() {
		GestionarLineas panel = new GestionarLineas();
		ventana.setContentPane(panel);
		ventana.setTitle("SGTM - Gestionar Lineas");
		ventana.revalidate();
		ventana.repaint();
	}
	
	public static void altaLinea(Integer proxID) {
		GestionarLineas panelGestionar = (GestionarLineas) ventana.getContentPane();
		JDialog ventanaAlta  = new JDialog(ventana,"Alta Linea");
		AltaLinea panel = new AltaLinea(panelGestionar,proxID);
		ventanaAlta.setContentPane(panel);
		ventanaAlta.setSize(500,390);
		ventanaAlta.setLocationRelativeTo(null);
		ventana.setEnabled(false);

		BotonJ aceptar = (BotonJ) ((JPanel) panel.getComponent(1)).getComponent(1);
		BotonJ cancelar = (BotonJ) ((JPanel) panel.getComponent(1)).getComponent(2);
		
		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(panel.camposCompletos()) {
					JPanel completado = new JPanel(new GridBagLayout());
					completado.setBackground(UIManager.getColor("CheckBox.focus"));
					
					EtiquetaJ mensaje = new EtiquetaJ("La linea '"+((CampoJ)((JPanel) panel.getComponent(0)).getComponent(1)).getText()+"' fue creada");
					mensaje.setForeground(new Color(0,150,0));
					BotonJ aceptar = new BotonJ("Aceptar");
					GridBagConstraints cons = new GridBagConstraints();
					
					cons.insets = new Insets(10,10,10,10);
					cons.gridy = 0;
					cons.anchor = GridBagConstraints.CENTER;
					completado.add(mensaje,cons);
					
					cons.gridy = 1;
					completado.add(aceptar,cons);
					
					ventanaAlta.setContentPane(completado);
					ventanaAlta.revalidate();
					ventanaAlta.repaint();
					ventanaAlta.pack();
					ventanaAlta.setLocationRelativeTo(null);
					
					aceptar.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							ventanaAlta.dispose();
							ventana.setEnabled(true);
							ventana.setVisible(true);
						}
					});
				}
			}
		});
		
		cancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaAlta.dispose();
				ventana.setEnabled(true);
				ventana.setVisible(true);
			}
		});
		
		ventanaAlta.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ventana.setEnabled(true);
				ventana.setVisible(true);
			}	
		});
		
		ventanaAlta.setVisible(true);
	}
	
	public static void modificarLinea(Integer id, String nombre, Color color) {
		GestionarLineas panelGestionar = (GestionarLineas) ventana.getContentPane();
		JDialog ventanaModificacion = new JDialog(ventana,"Modificar Linea");
		ModificarLinea panel = new ModificarLinea(panelGestionar,id,nombre,color);
		ventanaModificacion.setContentPane(panel);
		ventanaModificacion.setSize(500,350);
		ventanaModificacion.setLocationRelativeTo(null);
		ventana.setEnabled(false);
		
		BotonJ aceptar = (BotonJ) ((JPanel) panel.getComponent(1)).getComponent(1);
		BotonJ cancelar = (BotonJ) ((JPanel) panel.getComponent(1)).getComponent(2);
		
		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(panel.camposCompletos()) {
					JPanel completado = new JPanel(new GridBagLayout());
					completado.setBackground(UIManager.getColor("CheckBox.focus"));
					
					EtiquetaJ mensaje = new EtiquetaJ("La linea '"+nombre+"' fue modificada");
					mensaje.setForeground(new Color(0,150,0));
					BotonJ aceptar = new BotonJ("Aceptar");
					GridBagConstraints cons = new GridBagConstraints();
					
					cons.insets = new Insets(10,10,10,10);
					cons.gridy = 0;
					cons.anchor = GridBagConstraints.CENTER;
					completado.add(mensaje,cons);
					
					cons.gridy = 1;
					completado.add(aceptar,cons);
					
					ventanaModificacion.setContentPane(completado);
					ventanaModificacion.revalidate();
					ventanaModificacion.repaint();
					ventanaModificacion.pack();
					ventanaModificacion.setLocationRelativeTo(null);
					
					aceptar.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							ventanaModificacion.dispose();
							ventana.setEnabled(true);
							ventana.setVisible(true);
						}
					});
				}
			}
		});;
		
		cancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaModificacion.dispose();
				ventana.setEnabled(true);
				ventana.setVisible(true);
			}
		});;
		
		ventanaModificacion.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ventana.setEnabled(true);
				ventana.setVisible(true);
			}	
		});
		
		ventanaModificacion.setVisible(true);	
		
	}
	
	public static void gestionarTrayectos() {
		GestionarTrayectos panel = new GestionarTrayectos();
		ventana.setContentPane(panel);
		ventana.setTitle("SGTM - Gestionar Trayectos");
		ventana.revalidate();
		ventana.repaint();
	}
	
	public static void verTrayecto(Integer idLinea) {
		VerTrayecto panel = new VerTrayecto(idLinea);
		ventana.setContentPane(panel);
		ventana.setTitle("SGTM - Ver Trayecto");
		ventana.revalidate();
		ventana.repaint();
		
	}
	
	public static void altaTramo(Optional<Estacion> estacion, Integer idLinea, Integer lugar, Integer proxID) {
		VerTrayecto panelAnterior = (VerTrayecto) ventana.getContentPane();
		JDialog ventanaAlta  = new JDialog(ventana,"Alta Linea");
		AltaTramo panel = new AltaTramo(panelAnterior,estacion,idLinea,lugar,proxID);
		ventanaAlta.setContentPane(panel);
		ventanaAlta.setSize(500,390);
		ventanaAlta.setLocationRelativeTo(null);
		ventana.setEnabled(false);
		
		BotonJ aceptar = (BotonJ) ((JPanel) panel.getComponent(1)).getComponent(1);
		BotonJ cancelar = (BotonJ) ((JPanel) panel.getComponent(1)).getComponent(2);
		
		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(panel.camposCompletos() && panel.numericosValidos()) {
					JPanel completado = new JPanel(new GridBagLayout());
					completado.setBackground(UIManager.getColor("CheckBox.focus"));
					
					EtiquetaJ mensaje = new EtiquetaJ("La ruta fue creada");
					mensaje.setForeground(new Color(0,150,0));
					BotonJ aceptar = new BotonJ("Aceptar");
					GridBagConstraints cons = new GridBagConstraints();
					
					cons.insets = new Insets(10,10,10,10);
					cons.gridy = 0;
					cons.anchor = GridBagConstraints.CENTER;
					completado.add(mensaje,cons);
					
					cons.gridy = 1;
					completado.add(aceptar,cons);
					
					ventanaAlta.setContentPane(completado);
					ventanaAlta.revalidate();
					ventanaAlta.repaint();
					ventanaAlta.pack();
					ventanaAlta.setLocationRelativeTo(null);
					
					aceptar.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							ventanaAlta.dispose();
							ventana.setEnabled(true);
							ventana.setVisible(true);
						}
					});
				}
			}
		});
		
		cancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaAlta.dispose();
				ventana.setEnabled(true);
				ventana.setVisible(true);
			}
		});
		
		ventanaAlta.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ventana.setEnabled(true);
				ventana.setVisible(true);
			}	
		});
		
		ventanaAlta.setVisible(true);
	}

	public static void modificarTramo(Integer id, String origen, String destino, Integer idLinea, Double distancia, Integer duracion, Integer capacidad, Double costo) {
		VerTrayecto panelAnterior = (VerTrayecto) ventana.getContentPane();
		JDialog ventanaModificacion = new JDialog(ventana,"Modificar Tramo");
		ModificarTramo panel = new ModificarTramo(panelAnterior,id,origen,destino,idLinea,distancia,duracion,capacidad,costo);
		ventanaModificacion.setContentPane(panel);
		ventanaModificacion.setSize(500,250);
		System.out.println(ventanaModificacion.getSize());
		ventanaModificacion.setLocationRelativeTo(null);
		ventana.setEnabled(false);
		
		BotonJ aceptar = (BotonJ) ((JPanel) panel.getComponent(1)).getComponent(1);
		BotonJ cancelar = (BotonJ) ((JPanel) panel.getComponent(1)).getComponent(2);
		
		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(panel.camposCompletos() && panel.numericosValidos()) {
					JPanel completado = new JPanel(new GridBagLayout());
					completado.setBackground(UIManager.getColor("CheckBox.focus"));
					
					EtiquetaJ mensaje = new EtiquetaJ("El tramo de fue modificado");
					mensaje.setForeground(new Color(0,150,0));
					BotonJ aceptar = new BotonJ("Aceptar");
					GridBagConstraints cons = new GridBagConstraints();
					
					cons.insets = new Insets(10,10,10,10);
					cons.gridy = 0;
					cons.anchor = GridBagConstraints.CENTER;
					completado.add(mensaje,cons);
					
					cons.gridy = 1;
					completado.add(aceptar,cons);
					
					ventanaModificacion.setContentPane(completado);
					ventanaModificacion.revalidate();
					ventanaModificacion.repaint();
					ventanaModificacion.pack();
					ventanaModificacion.setLocationRelativeTo(null);
					
					aceptar.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							ventanaModificacion.dispose();
							ventana.setEnabled(true);
							ventana.setVisible(true);
						}
					});
				}
			}
		});;
		
		cancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaModificacion.dispose();
				ventana.setEnabled(true);
				ventana.setVisible(true);
			}
		});;
		
		ventanaModificacion.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ventana.setEnabled(true);
				ventana.setVisible(true);
			}	
		});
		
		ventanaModificacion.setVisible(true);
	}
	
	public static void infoEstaciones() {
		InformacionEstaciones panel = new InformacionEstaciones();
		ventana.setContentPane(panel);
		ventana.setTitle("SGTM - Información Estaciones");
		ventana.revalidate();
		ventana.repaint();
	}
	
	public static void pageRank() {
		PageRank panel = new PageRank();
		ventana.setContentPane(panel);
		ventana.setTitle("SGTM - Page Rank");
		ventana.revalidate();
		ventana.repaint();
	}

	public static void proximoMantenimiento() {
		ProximoMantenimiento panel = new ProximoMantenimiento();
		ventana.setContentPane(panel);
		ventana.setTitle("SGTM - Próximo Mantenimiento");
		ventana.revalidate();
		ventana.repaint();
	}
	
	public static void flujoMaximo() {
		FlujoMaximo panel = new FlujoMaximo();
		ventana.setContentPane(panel);
		ventana.setTitle("SGTM - Flujo Máximo");
		ventana.revalidate();
		ventana.repaint();
	}

	public static void venderBoleto() {
		VenderBoleto panel = new VenderBoleto();
		ventana.setContentPane(panel);
		ventana.setTitle("SGTM - Vender Boleto");
		ventana.revalidate();
		ventana.repaint();
	}

	public static void seleccionarOtroCamino(Vector<Vector<Ruta>> listaCaminos) {
		VenderBoleto panelAnterior = (VenderBoleto) ventana.getContentPane();
		SeleccionarOtroCamino panel = new SeleccionarOtroCamino(listaCaminos);
		ventana.setContentPane(panel);
		ventana.setTitle("SGTM - Vender Boleto");
		ventana.revalidate();
		ventana.repaint();
		
		BotonJ volver = panel.getVolver();
		volver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.setContentPane(panelAnterior);
			}			
		});
	}
	
	public static void finalizarVenta(Vector<Ruta> camino) {
		JPanel panelAnterior = (JPanel) ventana.getContentPane();
		FinalizarVenta panel = new FinalizarVenta(camino);
		ventana.setContentPane(panel);
		ventana.setTitle("SGTM - Vender Boleto");
		ventana.revalidate();
		ventana.repaint();
		
		BotonJ volver = panel.getVolver();
		volver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.setContentPane(panelAnterior);
			}			
		});
		
	}
	
}
