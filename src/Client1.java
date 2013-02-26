import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;


public class Client1 {
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Tic Tac Toe");
		
		ConnectPane connectPane = new ConnectPane();
		connectPane.setPreferredSize(new Dimension(300,300));
		
		
		
		PlayPane playPane = new PlayPane();
		playPane.setPreferredSize(new Dimension(300,300));
		
		
		frame.getContentPane().add(connectPane, BorderLayout.CENTER);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
