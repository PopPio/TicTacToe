import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class ChatText2 {
	static JTextPane text_panel;
	static JTextPane time_panel;
    /**
     * @param args
     * @throws IOException 
     * @throws BadLocationException 
     */
    public static void main(String[] args) throws BadLocationException, IOException {
        JFrame failFrame = new JFrame();
        JPanel failPanel = new JPanel();
        
        text_panel = new JTextPane();
//        text_panel.setPreferredSize(new Dimension(400,200));
        text_panel.setEditable(false);
        text_panel.setOpaque(true);
        
        setFixedWidth(text_panel, 200);
//        HTMLEditorKit kit = new HTMLEditorKit();
//        HTMLDocument doc = new HTMLDocument();
//        text_panel.setEditorKit(kit);
//        text_panel.setDocument(doc);
////        kit.insertHTML(doc, doc.getLength(), "<b>hello", 0, 0, HTML.Tag.B);
////        kit.insertHTML(doc, doc.getLength(), "<b>PopPio:<b>", 0, 0, null);
//        kit.insertHTML(doc, doc.getLength(), "<table>", 0, 0, null);
//        kit.insertHTML(doc, doc.getLength(), "<tr><td><b style=\"color:blue;\">PopPio: </b>test</td><td><span style=\"padding-left=120px\">10:50</span></td></tr>", 0, 0, null);
//        kit.insertHTML(doc, doc.getLength(), "<tr><td><font color='red'><u>world</u></font></td><td><span>test<span></td><tr>", 0, 0, null);
//        kit.insertHTML(doc, doc.getLength(), "</table>", 0, 0, null);
        
        
		
		time_panel = new JTextPane();
		time_panel.setEditable(false);
		time_panel.setOpaque(true);
		
		failPanel.setLayout(new BorderLayout());
        failPanel.add(text_panel,BorderLayout.CENTER);
        failPanel.add(time_panel,BorderLayout.LINE_END);
        
        
        addRow();
        addRow();
        
        JScrollPane scrollpane = new JScrollPane(failPanel);
        scrollpane.setPreferredSize(new Dimension(400,200));
        scrollpane.setOpaque(true);
        scrollpane.setBackground(Color.green);
        scrollpane.setBounds(20,25, 400, 200);
        
        failFrame.setLayout(null);
        failFrame.add(scrollpane);
//        failFrame.add(failPanel);
        failFrame.setSize(800, 800);
        failFrame.setLocationRelativeTo(null);
        failFrame.setVisible(true);
    }
    
    
    public static void addRow(){
    	SimpleAttributeSet name = new SimpleAttributeSet();
		StyleConstants.setFontFamily(name, "Arial BOLD");
		StyleConstants.setFontSize(name, 12);
		StyleConstants.setForeground(name, Color.BLACK);
		try{
			text_panel.getDocument().insertString(text_panel.getDocument().getLength(), "PopPio: ", name);
		}catch(Exception e) {}
		
		SimpleAttributeSet text = new SimpleAttributeSet();
		StyleConstants.setFontFamily(text, "Arial");
		StyleConstants.setFontSize(text, 12);
		StyleConstants.setForeground(text, Color.BLACK);
		try{
			text_panel.getDocument().insertString(text_panel.getDocument().getLength(), "Nullam quis metus in tellus vehicula imperdiet at ut neque. Integer lacinia interdum hendrerit."+"\n", text);
		}catch(Exception e) {}
		
		
		SimpleAttributeSet time = new SimpleAttributeSet();
		StyleConstants.setFontFamily(time, "Arial");
		StyleConstants.setFontSize(time, 12);
		StyleConstants.setForeground(time, Color.BLACK);
		try{
			time_panel.getDocument().insertString(time_panel.getDocument().getLength(), "10:55"+"\n", time);
		}catch(Exception e) {}
    }
    
    
    public static void setFixedWidth( Component component, int width )
	{
		component.setSize( new Dimension( width, Short.MAX_VALUE ) );
		Dimension preferredSize = component.getPreferredSize();
		component.setPreferredSize( new Dimension( width, preferredSize.height ) );
//		component.setPreferredSize( new Dimension( width, 50 ) );
	}

}