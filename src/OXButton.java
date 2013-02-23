import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class OXButton extends JButton {
	
	public OXButton (){
        this("");
    }
	
    public OXButton (String text){
        super(text);
        setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
//        setOpaque(false);
    }
    
    public void setO () {
    	try {
    		Image img = ImageIO.read(getClass().getResource("img/o.png"));
    	    setIcon(new ImageIcon(img));
    	} catch (IOException ex) {
   
    	}
    }
    public void setX () {
    	try {
    		Image img = ImageIO.read(getClass().getResource("img/x.png"));
    	    setIcon(new ImageIcon(img));
    	} catch (IOException ex) {
   
    	}
    }
}