import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

// for O and X
public class OXButton extends JButton {
	private boolean checked;
	private int type;
	
	public static int TYPE_O = 1;
	public static int TYPE_X = 2;
	
	public OXButton (){
        this("X");
    }
	
    public OXButton (String text){
    	super("");
        setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
//        setOpaque(false);
        
        if(text.equalsIgnoreCase("o")) {
        	type = TYPE_O;
        }else{
        	type = TYPE_X;
        }
        checked = false;
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	if(checked == false) {
	            	try {
	            		Image img;
	            		if(type == TYPE_O) {
	            			img = ImageIO.read(getClass().getResource("img/o_hover.png"));
	            		}else{
	            			img = ImageIO.read(getClass().getResource("img/x_hover.png"));
	            		}
	            		setIcon(new ImageIcon(img));
	            	} catch (IOException ex) {
	           
	            	}
            	}
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	if(checked == false) {
	            	try {
	            		Image img = ImageIO.read(getClass().getResource("img/blank.png"));
	            	    setIcon(new ImageIcon(img));
	            	} catch (IOException ex) {
	           
	            	}
            	}
            }
        });
        
    }
    /**
     * player select button (will be O or X based on constructor parameter.
     */
    public void check() {
		if(type == TYPE_O) {
			setO();
		}else{
			setX();
		}
    }
    
    /**
     * Set current button to O
     */
    public void setO () {
    	try {
    		Image img = ImageIO.read(getClass().getResource("img/o.png"));
    	    setIcon(new ImageIcon(img));
    	    checked = true;
    	} catch (IOException ex) {
   
    	}
    }
    /**
     * Set current button to X
     */
    public void setX () {
    	try {
    		Image img = ImageIO.read(getClass().getResource("img/x.png"));
    	    setIcon(new ImageIcon(img));
    	    checked = true;
    	} catch (IOException ex) {
   
    	}
    }
}