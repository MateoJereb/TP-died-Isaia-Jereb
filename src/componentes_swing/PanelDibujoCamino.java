package componentes_swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clases.*;

public class PanelDibujoCamino extends JPanel{

	private Vector<Ruta> camino;
	private final Integer ancho = 700;
	private final Integer alto = 70;
	
	public PanelDibujoCamino(Vector<Ruta> camino) {
		this.camino = camino;
		setPreferredSize(new Dimension(750,80));
		setMaximumSize(new Dimension(750,80));
		setMinimumSize(new Dimension(750,80));
		setBackground(Color.WHITE);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		dibujarCamino(g2);
	}
	
	private void dibujarCamino(Graphics2D g2) {
		Integer cantRutas = camino.size();
		Integer cantNodos = cantRutas+1;
		
		if(cantRutas > 0) {
			Integer posicionActual = 25;
			Vector<Estacion> estaciones = new Vector<Estacion>();
			
			for(Ruta r : camino) estaciones.add(r.getOrigen());
			estaciones.add(camino.get(camino.size()-1).getDestino());
			
			Integer incremento = ancho/cantRutas;
			
			//Dibujar lineas
			Stroke strokeAnterior = g2.getStroke();
			g2.setStroke(new BasicStroke(10));
			
			for(int i = 0; i<cantRutas; i++) {
				Point2D.Double inicio = new Point2D.Double(posicionActual,alto/2);
				Point2D.Double fin = new Point2D.Double(posicionActual+incremento,alto/2);
				Line2D.Double ruta = new Line2D.Double(inicio,fin);
				
				g2.setColor(camino.get(i).getLinea().getColor());
				g2.draw(ruta);
				
				posicionActual+=incremento;
			}
			
			posicionActual = 25;
			
			//Dibujar nodos
			g2.setStroke(strokeAnterior);
			for(int i = 0; i<cantNodos; i++) {
				Ellipse2D.Double nodo = new Ellipse2D.Double(posicionActual-15,(alto/2)-15,30,30);
				
				//Nombre estacion
				g2.setColor(Color.BLACK);
				if(i%2 == 0) g2.drawString(estaciones.get(i).getNombre(), posicionActual-25, (alto/2)-20);
				else g2.drawString(estaciones.get(i).getNombre(), posicionActual-25, (alto/2)+30);
				
				//Nodo
				g2.setColor(new Color(0,100,150));
				g2.fill(nodo);
				
				posicionActual+=incremento;
			}			
		}		
	}
	
	public void actualizarCamino(Vector<Ruta> camino) {
		this.camino = camino;
		revalidate();
		repaint();
	}
	
}
