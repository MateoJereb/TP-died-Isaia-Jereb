package componentes_swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JCheckBox;
import javax.swing.UIManager;

public class CheckBoxJ extends JCheckBox implements FocusListener{

	public CheckBoxJ() {
		super();
		addFocusListener(this);
		setFocusPainted(false);
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		setBackground(Color.WHITE);
	}
	
	public CheckBoxJ(String texto) {
		super(texto);
		addFocusListener(this);
		setFocusPainted(false);
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		setBackground(Color.WHITE);
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		setForeground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		setForeground(UIManager.getColor("CheckBoxMenuItem.foreground"));
	}
	
}
