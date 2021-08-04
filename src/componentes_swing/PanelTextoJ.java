package componentes_swing;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class PanelTextoJ extends JTextPane {

	public PanelTextoJ() {
		MutableAttributeSet set = new SimpleAttributeSet(this.getParagraphAttributes());
		StyleConstants.setLineSpacing(set, 0.4F);
		StyleConstants.setAlignment(set , StyleConstants.ALIGN_JUSTIFIED);
		
		setParagraphAttributes(set, true); //Primer parrafo
		
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,15));
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setDisabledTextColor(getForeground());
		setSize(new Dimension(450,(int)getPreferredSize().getHeight()));
		setPreferredSize(new Dimension(450,(int)getPreferredSize().getHeight()));
		setMinimumSize(new Dimension(450,(int)getPreferredSize().getHeight()));
		
		
		setParagraphAttributes(set, true); //Segundo parrafo
	}
	
}
