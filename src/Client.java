import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jdesktop.xswingx.PromptSupport;

// client-client UI
public class Client extends JFrame{
	
	// button color
	final Color PINK_BASE = new Color(0xAC193D);
	final Color PINK_DARK = new Color(0x85132F);
	final Color GREY_BASE = new Color(0x929191);
	final Color GREY_DARK = new Color(0x312b2c);
	final Color BASIC_HILIGHT = new Color(0xF2F2F2);
	final Color BTN_TEXT = Color.WHITE;
	// text color
	final Color TEXT_GREY = new Color(0x555555);
	// other color
	final Color APP_BG = Color.WHITE;
	
	///static JFrame frame = new JFrame("Tic Tac Toe");
	
	// Panels
	JPanel transitionPane = new JPanel();
	JPanel connectPane = new JPanel();
	JPanel mainPane = new JPanel();
	
	// Transition panel shared component
	JLabel transitionText;
	
	// Connect panel shared components
	JLabel profile;
	JTextField login_text;
	
	// Play panel sharedd components
	
	
	public Client() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(820, 640));
		
		initComponents();
	}
	
	private void initComponents() throws IOException {

		// =======================================================================================
		// =============================== TRANSITION PAGE =======================================
		// =======================================================================================
		transitionPane.setPreferredSize(new Dimension(800, 600));
		transitionPane.setBackground(new Color(0xFFFFFF));
		
		transitionText = new JLabel("Loading", SwingConstants.CENTER);
		transitionText.setBounds(300, 300, 200, 25);
//		transitionText.setOpaque(true);
//		transitionText.setBackground(Color.RED);
		
		transitionPane.setLayout(null);
		transitionPane.add(transitionText);
		// =======================================================================================
		// ================================= CONNECT PAGE ========================================
		// =======================================================================================
		
		connectPane.setPreferredSize(new Dimension(800, 600));
		connectPane.setBackground(new Color(0xFFFFFF));
		
		// logo
		java.net.URL logo_URL = Client.class.getResource("img/logo.png");
		BufferedImage logo_pic = ImageIO.read(logo_URL);
		ImageIcon logoIcon = new ImageIcon(logo_pic);
		JLabel logo = new JLabel(logoIcon);
		logo.setBounds(329, 100, 143, 143);
		
		// name, ip address
		login_text = new JTextField(20);
//				TextInput login_text = new TextInput(20);
		login_text.setBounds(300, 300, 200, 25);
		PromptSupport.setPrompt("name", login_text);
		JTextField ip_text = new JTextField(20);
		ip_text.setBounds(300, 340, 200, 25);
		PromptSupport.setPrompt("ip address", ip_text);
		
		// connect buttons
		final JButton connectButton = new JButton("CONNECT");
		connectButton.setFont(new Font("Arial", Font.BOLD, 12));
		connectButton.setBorder(null);
		connectButton.setBorderPainted(false);
		connectButton.setFocusPainted(false);
		connectButton.setBackground(PINK_BASE);
		connectButton.setBounds(300, 380, 200, 25);
		connectButton.setForeground(BTN_TEXT);
		connectButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	connectButton.setBackground(PINK_DARK);
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	connectButton.setBackground(PINK_BASE);
		    }
		});
		connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	ConnectButtonPerformed(e);
            }
        });      
		
		// 
		JTextField dummy_text = new JTextField();
		dummy_text.setEditable(false);
		connectPane.setLayout(null);
		connectPane.add(logo);
		connectPane.add(dummy_text);
		connectPane.add(login_text);
		connectPane.add(ip_text);
		connectPane.add(connectButton);
		
		
		// =======================================================================================
		// ================================== MAIN PAGE ==========================================
		// =======================================================================================
		
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
		
		
		// disconnect button
		final JButton disconnectButton = new JButton("DISCONNECT");
		disconnectButton.setFont(new Font("Arial", Font.PLAIN, 12));
		disconnectButton.setBorder(null);
		disconnectButton.setBorderPainted(false);
		disconnectButton.setFocusPainted(false);
		disconnectButton.setBackground(APP_BG);
		disconnectButton.setBounds(690, 20, 90, 35);
		disconnectButton.setForeground(TEXT_GREY);
		disconnectButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	disconnectButton.setBackground(BASIC_HILIGHT);
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	disconnectButton.setBackground(APP_BG);
		    }
		});
		
		
		// display name
		java.net.URL profile_URL = Client.class.getResource("img/profile.png");
		BufferedImage profile_pic = ImageIO.read(profile_URL);
		ImageIcon profileIcon = new ImageIcon(profile_pic);
		// for server-client
//				final JButton profileButton = new JButton("PopPio", profileIcon);
//				profileButton.setFont(new Font("Arial", Font.BOLD, 12));
//				profileButton.setBorder(null);
//				profileButton.setBorderPainted(false);
//				profileButton.setFocusPainted(false);
//				profileButton.setBackground(APP_BG);
//				profileButton.setBounds(470, 20, 220, 35);
//				profileButton.setForeground(TEXT_GREY);
//				profileButton.setHorizontalAlignment(SwingConstants.LEFT);
		
		profile = new JLabel("Username", profileIcon, SwingConstants.LEFT);
		profile.setBounds(470, 20, 220, 35);
		
		// right text panel --------------------------------------------------
		JPanel textPane = new JPanel();
		textPane.setBackground(new Color(0xf3e9e9));
		textPane.setBounds(470, 55, 310, 405);
		
		
		
		// score panel -------------------------------------------------------
		JPanel scorePane = new JPanel();
		scorePane.setBackground(APP_BG);
		scorePane.setBounds(20, 470, 430, 110);
		scorePane.setLayout(null);
			java.net.URL img_o_URL = Client.class.getResource("img/bg_o.png");
			BufferedImage bg_o = ImageIO.read(img_o_URL);
			ImagePanel scoreOPane = new ImagePanel(bg_o);
//					scoreOPane.setBackground(new Color(0xddeff0));
			scoreOPane.setBounds(0, 0, 205, 110);
			scorePane.add(scoreOPane);
		
			java.net.URL img_x_URL = Client.class.getResource("img/bg_x.png");
			BufferedImage bg_x = ImageIO.read(img_x_URL);
			ImagePanel scoreXPane = new ImagePanel(bg_x);
//					scoreXPane.setBackground(new Color(0xddeff0));
			scoreXPane.setBounds(225, 0, 205, 110);
			scorePane.add(scoreXPane);
		
		// chat panel -------------------------------------------------------
		JPanel chatPane = new JPanel();
		chatPane.setBackground(new Color(0xecedec));
		chatPane.setBounds(470, 470, 310, 90);
		JTextArea chatArea = new JTextArea("", 5, 27);
		chatArea.setLineWrap(true);
		chatArea.setWrapStyleWord(true);
		chatArea.setBounds(0, 0, 310, 90);
		chatArea.setOpaque(false);
		chatArea.setBorder(null);
		PromptSupport.setPrompt("send a message ...", chatArea);
		PromptSupport.setForeground(GREY_BASE, chatArea);
		JScrollPane chatAreaScroll = new JScrollPane(chatArea);
		chatAreaScroll.setBorder(null);
		chatPane.add(chatAreaScroll);
		
		// send button
		final JButton sendButton = new JButton("send");
		sendButton.setFont(new Font("Arial", Font.PLAIN, 14));
		sendButton.setBorder(null);
		sendButton.setBorderPainted(false);
		sendButton.setFocusPainted(false);
		sendButton.setBackground(PINK_BASE);
		sendButton.setBounds(735, 560, 45, 20);
		sendButton.setForeground(BTN_TEXT);
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
		resetButton.setFocusPainted(false);
		resetButton.setBackground(GREY_BASE);
		resetButton.setBounds(470, 560, 85, 20);
		resetButton.setForeground(BTN_TEXT);
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
		mainPane.add(disconnectButton);
		mainPane.add(profile);
		mainPane.add(textPane);
		mainPane.add(scorePane);
		mainPane.add(chatPane);
		mainPane.add(sendButton);
		mainPane.add(resetButton);
		
		
		
		
		// ======================================
		// ==========Frame set up================
		// ======================================
		// set icon
		java.net.URL icon_URL = Client.class.getResource("img/logo_small.png");
		BufferedImage frame_icon = ImageIO.read(icon_URL);
		setIconImage(frame_icon);
		setTitle("Awesome Tic Tac Toe");
		getContentPane().add(connectPane, BorderLayout.CENTER);
		
		pack();
		setLocationRelativeTo(null); // place JFrame in center of screen
//		setVisible(true);
	}
	
	
	
	// ****************************************************************************
	private void ConnectButtonPerformed(ActionEvent evt) {
		// change to play panel 
		// insert form validation if have time
		String name = login_text.getText();
 		profile.setText(name.equalsIgnoreCase("") ? "Name" : name);
		
 		
 		// ***** temp call to play panel *****
 		redirectToPlayPanel();
 		// ***** end ******
 		
 		
 		// ***** real app should do this *****
// 		getContentPane().removeAll();
// 		getContentPane().add(transitionPane, BorderLayout.CENTER);
// 		revalidate();
 		// ***** end ******
	}
	
	private void redirectToPlayPanel() {
		getContentPane().removeAll();
		getContentPane().add(mainPane, BorderLayout.CENTER);
 		//getContentPane().remove(connectPane);
 		revalidate();
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                
                try {
					new Client().setVisible(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
	}

}
