import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

// for O and X
public class OXButton extends JButton {
	private int type;
	private int currentState;
	
	// constants for type
	final int TYPE_O = 1;
	final int TYPE_X = 2;
	final int TYPE_SPEC = 3;
	
	// constants for current state
	final int STATE_EMPTY = 0;
	final int STATE_O = 1;
	final int STATE_X = 2;
	
	public OXButton (){
        this("spec");
        // default is spec
    }
	
    public OXButton (String text){
    	super("");
        setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
//        setOpaque(false);
        
        if(text.equalsIgnoreCase("o")) {
        	type = TYPE_O;
        }else if(text.equalsIgnoreCase("x")){
        	type = TYPE_X;
        }else{
        	type = TYPE_SPEC;
        }
        //checked = false;
        currentState = 0;
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	if(currentState == 0 && type < TYPE_SPEC) {
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
            	if(currentState == 0 && type < TYPE_SPEC) {
	            	try {
	            		Image img = ImageIO.read(getClass().getResource("img/blank.png"));
	            	    setIcon(new ImageIcon(img));
	            	} catch (IOException ex) {
	           
	            	}
            	}
            }
        });
        
    }
    public void setType(String side) {
    	if(side.equalsIgnoreCase("x")){
    		this.type = TYPE_X;
		}else if(side.equalsIgnoreCase("o")){
			this.type = TYPE_O;
		}else{
			System.err.println("Invalid side type (only accept o or x)");
		}
    }
    public void reset() {
    	currentState = STATE_EMPTY;
    }
    
    /**
     * player select button (will be O or X based on constructor parameter.
     */
    public void check() {
		if(type == TYPE_O) {
			tickO();
		}else{
			tickX();
		}
    }
    public void opponentCheck() {
    	if(type == TYPE_O) {
    		tickX();
		}else{
			tickO();
		}
    }
    /**
     * Set current button to O
     */
    public void tickO () {
    	try {
    		Image img = ImageIO.read(getClass().getResource("img/o.png"));
    	    setIcon(new ImageIcon(img));
    	    currentState = STATE_O;
    	} catch (IOException ex) {
   
    	}
    }
    /**
     * Set current button to X
     */
    public void tickX () {
    	try {
    		Image img = ImageIO.read(getClass().getResource("img/x.png"));
    	    setIcon(new ImageIcon(img));
    	    //checked = true;
    	    currentState = STATE_X;
    	} catch (IOException ex) {
   
    	}
    }
}