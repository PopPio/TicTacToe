import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.Utilities;


public class ChatText extends JPanel{
	final static Color GREY_BASE = new Color(0x929191);
	final static Color PLAYER1 = new Color(0x477836);
	final static Color PLAYER2 = new Color(0x3990a9);
	
	JTextPane text_panel,time_panel;
	
	public ChatText(){
		this("Etiam aliquam pulvinar pretium. Vestibulum ultricies erat laoreet felis varius eu euismod ante tincidunt. Donec felis tortor, pharetra eu pretium eu, tincidunt vel dui. Nullam rutrum placerat velit et tincidunt.");
		
	}
	
	public ChatText(String argText){
//		// text
//		JTextPane textPane = new JTextPane();
//		textPane.setOpaque(false);
//		
//		SimpleAttributeSet text = new SimpleAttributeSet();
//		StyleConstants.setFontFamily(text, "Arial");
//		StyleConstants.setFontSize(text, 12);
//		StyleConstants.setForeground(text, Color.BLACK);
//		try{
//		    textPane.getDocument().insertString(0, argText, text);
//		}catch(Exception e) {}
//		
//		SimpleAttributeSet name = new SimpleAttributeSet();
//		StyleConstants.setFontFamily(name, "Arial BOLD");
//		StyleConstants.setFontSize(name, 12);
//		StyleConstants.setForeground(name, PLAYER1);
//		try{
//		    textPane.getDocument().insertString(0, "PopPio: ", name);
//		}catch(Exception e) {}
//		setFixedWidth(textPane,260);
//		
//		
//		JTextPane timePane = new JTextPane();
//		timePane.setOpaque(false);
//		SimpleAttributeSet time = new SimpleAttributeSet();
//		StyleConstants.setFontFamily(time, "Arial");
//		StyleConstants.setFontSize(time, 12);
//		StyleConstants.setForeground(time, Color.BLACK);
//		try{
//			timePane.getDocument().insertString(0, "10:50", time);
//		}catch(Exception e) {}
//		setFixedWidth(timePane,35);
//		
////		setLayout(null);
////		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
//		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//		
////		this.setLayout(new GridBagLayout());
////		GridBagConstraints c = new GridBagConstraints();
////		c.fill = GridBagConstraints.HORIZONTAL;
////		c.weightx = 0.9;
////		c.gridx = 0;
////		c.gridy = 0;
////		this.add(textPane, c);
//		
////		c.fill = GridBagConstraints.HORIZONTAL;
////		c.weightx = 0.1;
////		c.gridx = 1;
////		c.gridy = 0;
////		this.add(timePane, c);
//		
//		add(textPane);
//		add(timePane);
////		setFixedWidth(this,310);
//		
//		setOpaque(true);
//		setBackground(Color.red);
//		if(argText.equalsIgnoreCase("bla bla")){
//			setBackground(Color.green);
//		}
////		setAlignmentY(Component.TOP_ALIGNMENT);
		
		
		text_panel = new JTextPane();
		text_panel.setEditable(false);
		text_panel.setOpaque(false);
      
		setFixedWidth(text_panel, 200);
      
		time_panel = new JTextPane();
		time_panel.setEditable(false);
		time_panel.setOpaque(false);
		
		setLayout(new BorderLayout());
		add(text_panel,BorderLayout.CENTER);
		add(time_panel,BorderLayout.LINE_END);
		
//		addRow("PopPio", Color.red, "HEllo, LOL");
//		addRow("PopPio", Color.red, "Amesome", "10:55");
//		addRow("PopPio", Color.red, "Well, not so 1337 now, huh?", "10:55");
//		addRow("PopPio", Color.red, "i will find you and i will kill you bla bla bla bla bla", "10:55");
//		addRow("PopPio", Color.red, "Well, not so 1337 now, huh?", "10:55");
		
	}
	
	public void addRow(String nameText, Color color, String chatText){
		addRow(nameText, color, chatText, "12:15");
	}
	public void addRow(String nameText, Color color, String chatText, String timeText){
    	SimpleAttributeSet name = new SimpleAttributeSet();
		StyleConstants.setFontFamily(name, "Arial BOLD");
		StyleConstants.setFontSize(name, 12);
		StyleConstants.setForeground(name, color);
		try{
			text_panel.getDocument().insertString(text_panel.getDocument().getLength(), nameText+": ", name);
		}catch(Exception e) {}
		
		SimpleAttributeSet text = new SimpleAttributeSet();
		StyleConstants.setFontFamily(text, "Arial");
		StyleConstants.setFontSize(text, 12);
		StyleConstants.setForeground(text, Color.BLACK);
		try{
			text_panel.getDocument().insertString(text_panel.getDocument().getLength(), chatText+"\n", text);
		}catch(Exception e) {}
		

		
//		SimpleAttributeSet time = new SimpleAttributeSet();
//		StyleConstants.setFontFamily(time, "Arial");
//		StyleConstants.setFontSize(time, 12);
//		StyleConstants.setForeground(time, Color.BLACK);
//		try{
//			time_panel.getDocument().insertString(time_panel.getDocument().getLength(), timeText+"\n", time);
//		}catch(Exception e) {}
	}
	public void reset() throws BadLocationException {
		text_panel.getDocument().remove(0, text_panel.getDocument().getLength());
		
	}
	
	 public static int getRow(int pos, JTextComponent editor) {
	        int rn = (pos==0) ? 1 : 0;
	        try {
	            int offs=pos;
	            while( offs>0) {
	                offs=Utilities.getRowStart(editor, offs)-1;
	                rn++;
	            }
	        } catch (BadLocationException e) {
	            e.printStackTrace();
	        }
	        return rn;
	    }
	 
	 private static int countLines(JTextComponent textArea) {
		    AttributedString text = new AttributedString(textArea.getText());
		    FontRenderContext frc = textArea.getFontMetrics(textArea.getFont())
		        .getFontRenderContext();
		    AttributedCharacterIterator charIt = text.getIterator();
		    LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(charIt, frc);
		    float formatWidth = (float) textArea.getSize().width;
		    lineMeasurer.setPosition(charIt.getBeginIndex());

		    int noLines = 0;
		    while (lineMeasurer.getPosition() < charIt.getEndIndex()) {
		      lineMeasurer.nextLayout(formatWidth);
		      noLines++;
		    }

		    return noLines;
		  }
	
	public static void setFixedWidth( Component component, int width )
	{
		component.setSize( new Dimension( width, Short.MAX_VALUE ) );
		Dimension preferredSize = component.getPreferredSize();
//		component.setPreferredSize( new Dimension( width, preferredSize.height ) );
		component.setPreferredSize( new Dimension( width, 50 ) );
	}
}
