package componentes_swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CampoJ extends JTextField {

	public CampoJ() {
		super();
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		
		getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				cambiar();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				cambiar();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				cambiar();
			}
			
			public void cambiar() {
				setBackground(Color.WHITE);
			}
		});
	}
	
	public CampoJ(String texto) {
		super(texto);
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		
		getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				cambiar();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				cambiar();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				cambiar();
			}
			
			public void cambiar() {
				setBackground(Color.WHITE);
			}
		});
	}
	
	
}
