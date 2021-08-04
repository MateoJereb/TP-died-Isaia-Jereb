package componentes_swing;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextArea;

public class AreaTextoJ extends JTextArea {

	public AreaTextoJ() {
		super();
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		setLineWrap(true);
		setWrapStyleWord(true);
		setPreferredSize(new Dimension(400,250));
		setMinimumSize(new Dimension(400,250));
	}
	
	public AreaTextoJ(String texto) {
		super(texto);
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		setLineWrap(true);
		setWrapStyleWord(true);
		setPreferredSize(new Dimension(400,250));
		setMinimumSize(new Dimension(400,250));
	}
	
}
