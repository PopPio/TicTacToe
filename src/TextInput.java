import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.FocusManager;
import javax.swing.JTextField;
import javax.swing.text.Document;

public class TextInput extends JTextField{

@Override
protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    
    if(getText().isEmpty() && ! (FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)){
//        Graphics2D g2 = (Graphics2D)g.create();
//        g2.setBackground(Color.gray);
//        g2.setFont(getFont().deriveFont(Font.ITALIC));
//        g2.drawString("nameeeeeeeee", 15, 5); //figure out x, y from font's FontMetrics and size of component.
//        g2.dispose();
        System.out.println("paintCom");
    	this.setText("fuc");
    }
  }

public TextInput() {
	super();
	// TODO Auto-generated constructor stub
}

public TextInput(Document arg0, String arg1, int arg2) {
	super(arg0, arg1, arg2);
	// TODO Auto-generated constructor stub
}

public TextInput(int arg0) {
	super(arg0);
	// TODO Auto-generated constructor stub
}

public TextInput(String arg0, int arg1) {
	super(arg0, arg1);
	// TODO Auto-generated constructor stub
}

public TextInput(String arg0) {
	super(arg0);
	// TODO Auto-generated constructor stub
}

}