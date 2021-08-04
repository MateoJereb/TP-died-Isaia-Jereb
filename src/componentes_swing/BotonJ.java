package componentes_swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;

public class BotonJ extends JButton implements FocusListener{

	public BotonJ() {
		super();
		addFocusListener(this);
		setFocusPainted(false);
		setBackground(Color.DARK_GRAY);
		setForeground(Color.WHITE);
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,13));
		
	}
	
	public BotonJ(String s) {
		super(s);
		addFocusListener(this);
		setFocusPainted(false);
		setBackground(Color.DARK_GRAY);
		setForeground(Color.WHITE);
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,13));
	}

	@Override
	public void focusGained(FocusEvent e) {
		setBackground(Color.LIGHT_GRAY);
		setForeground(Color.BLACK);
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		setBackground(Color.DARK_GRAY);
		setForeground(Color.WHITE);
		
	}
	
}
