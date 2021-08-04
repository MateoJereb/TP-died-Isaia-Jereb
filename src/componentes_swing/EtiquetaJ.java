package componentes_swing;

import java.awt.Font;

import javax.swing.JLabel;

public class EtiquetaJ extends JLabel {

	public EtiquetaJ() {
		super();
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
	}
	
	public EtiquetaJ(String texto) {
		super(texto);
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
	}
	
}
