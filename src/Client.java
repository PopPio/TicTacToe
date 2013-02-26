import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Client {
	static final Color PINK_BASE = new Color(0xAC193D);
	static final Color PINK_DARK = new Color(0x85132F);
	static final Color GREY_BASE = new Color(0x929191);
	static final Color GREY_DARK = new Color(0x312b2c);
	
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
		
		// Tic Tac Toe panel -------------------------------------------------
		java.net.URL img_play_URL = Client.class.getResource("img/bg_play.png");
		BufferedImage bg_play = ImageIO.read(img_play_URL);
		ImagePanel playPane = new ImagePanel(bg_play);
		playPane.setBackground(new Color(0xe2e2e2));
		playPane.setBounds(20, 20, 430, 430);
		
		
		OXButton b1 = new OXButton();
		OXButton b2 = new OXButton();
		OXButton b3 = new OXButton();
		OXButton b4 = new OXButton();
		OXButton b5 = new OXButton();
		OXButton b6 = new OXButton();
		OXButton b7 = new OXButton();
		OXButton b8 = new OXButton();
		OXButton b9 = new OXButton();
		
		playPane.setLayout(new GridLayout(3,3));
		playPane.add(b1);
		playPane.add(b2);
		playPane.add(b3);
		playPane.add(b4);
		playPane.add(b5);
		playPane.add(b6);
		playPane.add(b7);
		playPane.add(b8);
		playPane.add(b9);
		
		
		//-------------TEST OXButton--------------
		b1.check();
		b5.setO();
		//-----------END TEST OXButton------------
		
		
		// top bar
		JPanel topPane = new JPanel();
		topPane.setBackground(Color.WHITE);
		topPane.setBounds(470, 20, 310, 25);

		// right text panel --------------------------------------------------
		JPanel textPane = new JPanel();
		textPane.setBackground(new Color(0xf3e9e9));
		textPane.setBounds(470, 45, 310, 405);
		
		
		
		// score panel -------------------------------------------------------
		JPanel scorePane = new JPanel();
		scorePane.setBackground(new Color(0xFFFFFF));
		scorePane.setBounds(20, 470, 430, 110);
		scorePane.setLayout(null);
			java.net.URL img_o_URL = Client.class.getResource("img/bg_o.png");
			BufferedImage bg_o = ImageIO.read(img_o_URL);
			ImagePanel scoreOPane = new ImagePanel(bg_o);
//			scoreOPane.setBackground(new Color(0xddeff0));
			scoreOPane.setBounds(0, 0, 205, 110);
			scorePane.add(scoreOPane);
		
			java.net.URL img_x_URL = Client.class.getResource("img/bg_x.png");
			BufferedImage bg_x = ImageIO.read(img_x_URL);
			ImagePanel scoreXPane = new ImagePanel(bg_x);
//			scoreXPane.setBackground(new Color(0xddeff0));
			scoreXPane.setBounds(225, 0, 205, 110);
			scorePane.add(scoreXPane);
		
		// chat panel -------------------------------------------------------
		JPanel chatPane = new JPanel();
		chatPane.setBackground(new Color(0xecedec));
		chatPane.setBounds(470, 470, 310, 90);
		
		// send button
		final JButton sendButton = new JButton("send");
		sendButton.setFont(new Font("Arial", Font.PLAIN, 14));
		sendButton.setBorder(null);
		sendButton.setBorderPainted(false);
		sendButton.setBackground(PINK_BASE);
		sendButton.setBounds(735, 560, 45, 20);
		sendButton.setForeground(Color.WHITE);
		sendButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	sendButton.setBackground(PINK_DARK);
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	sendButton.setBackground(PINK_BASE);
		    }
		});
		
		// reset button
		final JButton resetButton = new JButton("reset score");
		resetButton.setFont(new Font("Arial", Font.PLAIN, 14));
		resetButton.setBorder(null);
		resetButton.setBorderPainted(false);
		resetButton.setBackground(GREY_BASE);
		resetButton.setBounds(470, 560, 90, 20);
		resetButton.setForeground(Color.WHITE);
		resetButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	resetButton.setBackground(GREY_DARK);
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	resetButton.setBackground(GREY_BASE);
		    }
		});
		
		// add them all
		mainPane.setLayout(null);
		mainPane.add(playPane);
		mainPane.add(topPane);
		mainPane.add(textPane);
		mainPane.add(scorePane);
		mainPane.add(chatPane);
		mainPane.add(sendButton);
		mainPane.add(resetButton);
		
		
		java.net.URL icon_URL = Client.class.getResource("img/logo_tiny.png");
		BufferedImage frame_icon = ImageIO.read(icon_URL);
		frame.setIconImage(frame_icon);
		frame.getContentPane().add(mainPane, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
