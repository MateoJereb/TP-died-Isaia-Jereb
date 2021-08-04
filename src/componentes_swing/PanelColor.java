package componentes_swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PanelColor extends JPanel{

	private Color color;
	
	public PanelColor() {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setPreferredSize(new Dimension(100,100));
		setBackground(Color.WHITE);
		color = Color.BLACK;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		dibujarCuadrado(g2);
	}
	
	private void dibujarCuadrado(Graphics2D g2) {
		Rectangle2D.Double cuadrado = new Rectangle2D.Double(0,0,100,100);
		g2.setColor(color);
		g2.fill(cuadrado);
	}
	
	public void actualizarColor(Integer r, Integer g, Integer b) {
		color = new Color(r,g,b);
		revalidate();
		repaint();
	}
	
	public Color getColorSeleccionado() {
		return color;
	}
}
