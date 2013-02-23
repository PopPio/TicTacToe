import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Client {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		JFrame frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(820, 640));

		// main content
		JPanel mainPane = new JPanel();
		mainPane.setPreferredSize(new Dimension(800, 600));
		mainPane.setBackground(new Color(0xFFFFFF));
		
		// Tic Tac Toe panel
		JPanel playPane = new JPanel();
		playPane.setBackground(new Color(0xe2e2e2));
		playPane.setBounds(20, 20, 430, 430);
//		java.net.URL imgURL = Client.class.getResource("img/bg_o.png");
//		BufferedImage buttonIcon = ImageIO.read(imgURL);
//		JButton testButton = new JButton(new ImageIcon(buttonIcon));
//		playPane.add(testButton);
		
		// right text panel
		JPanel textPane = new JPanel();
		textPane.setBackground(new Color(0xf3e9e9));
		textPane.setBounds(470, 20, 310, 430);
		
		// score panel
		JPanel scorePane = new JPanel();
		scorePane.setBackground(new Color(0xFFFFFF));
		scorePane.setBounds(20, 470, 430, 110);
		scorePane.setLayout(null);
			java.net.URL img_o_URL = Client.class.getResource("img/bg_o.png");
			BufferedImage button_o_Icon = ImageIO.read(img_o_URL);
			ImagePanel scoreOPane = new ImagePanel(button_o_Icon);
			scoreOPane.setBackground(new Color(0xddeff0));
			scoreOPane.setBounds(0, 0, 205, 110);
			scorePane.add(scoreOPane);
		
			java.net.URL img_x_URL = Client.class.getResource("img/bg_x.png");
			BufferedImage button_x_Icon = ImageIO.read(img_x_URL);
			ImagePanel scoreXPane = new ImagePanel(button_x_Icon);
			scoreXPane.setBackground(new Color(0xddeff0));
			scoreXPane.setBounds(225, 0, 205, 110);
			scorePane.add(scoreXPane);
		
		// chat panel
		JPanel chatPane = new JPanel();
		chatPane.setBackground(new Color(0xecedec));
		chatPane.setBounds(470, 470, 310, 110);
		
		mainPane.setLayout(null);
		mainPane.add(playPane);
		mainPane.add(textPane);
		mainPane.add(scorePane);
		mainPane.add(chatPane);
		
		frame.getContentPane().add(mainPane, BorderLayout.CENTER);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
