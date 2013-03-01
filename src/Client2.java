import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.jdesktop.xswingx.PromptSupport;

// client-client UI
public class Client2 extends JFrame{
	
	// button color
	final Color PINK_BASE = new Color(0xAC193D);
	final Color PINK_DARK = new Color(0x85132F);
	final Color GREY_BASE = new Color(0x929191);
	final Color GREY_MID = new Color(0x7F7B7B);
	final Color GREY_DARK = new Color(0x312b2c);
	final Color BASIC_HILIGHT = new Color(0xF2F2F2);
	final Color BTN_TEXT = Color.WHITE;
	// text color
	final Color TEXT_GREY = new Color(0x555555);
	// other color
	final Color APP_BG = Color.WHITE;
	final Color RIGHT_BG = new Color(0xecedec);
	final Color LEFT_BG = new Color(0xddeff0);
	
	///static JFrame frame = new JFrame("Tic Tac Toe");
	
	// Global variables
	String currentSide; // x,o,spec
	String playerName; // your name
	String opponentName; // your opponent name when you are a player
	String anotherPlayerName; // use in spectator mode only
	String playerOName, playerXName;
	String currentTurn; // x,o,pause
	User player,opponent;
	Random random; // for random side
	
//	int playerScore;
//	int opponentScore;
	// change from playerScore and opponentScore to scoreO, scoreX to support spectator
	int scoreO, scoreX;
	
	// Panels
	JPanel transitionPane = new JPanel();
	JPanel connectPane = new JPanel();
	JPanel homePage = new JPanel();
	JPanel playPage = new JPanel();
	
	// Transition panel shared component
	JLabel transitionText;
	
	// Connect panel shared components
	JLabel profile;
	JTextField username_text/*, password_text*/;
	JPasswordField password_text;
	
	// Play panel shared a2 components
	JTextArea chatArea;
	OXButton b0,b1,b2,b3,b4,b5,b6,b7,b8;
	JLabel nameO, nameX;
	ChatText chatText;
	JButton oGiveup, xGiveup;
	
	java.net.URL img_giveup_URL = Client2.class.getResource("img/giveup_normal.png");
	BufferedImage giveup_normal_image = ImageIO.read(img_giveup_URL);
	ImageIcon giveup_icon = new ImageIcon(giveup_normal_image);
	java.net.URL img_giveup_hover_URL = Client2.class.getResource("img/giveup_hover.png");
	BufferedImage giveup_hover_image = ImageIO.read(img_giveup_hover_URL);
	ImageIcon giveup_hover_icon = new ImageIcon(giveup_hover_image);
	
	
	public Client2() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(820, 640));
		
		random = new Random();
		
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
		java.net.URL logo_URL = Client2.class.getResource("img/logo.png");
		BufferedImage logo_pic = ImageIO.read(logo_URL);
		ImageIcon logoIcon = new ImageIcon(logo_pic);
		JLabel logo = new JLabel(logoIcon);
		logo.setBounds(329, 100, 143, 143);
		
		// username, password
		username_text = new JTextField();
		username_text.setBounds(300, 300, 200, 25);
		PromptSupport.setPrompt("Username", username_text);
		
		password_text = new JPasswordField(10);
		
//		password_text = new JTextField();
		password_text.setBounds(300, 340, 200, 25);
		PromptSupport.setPrompt("Password", password_text);
		
		
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
            	try {
					connectButtonPerformed(e);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
        });     
		
		// register button
		final JButton registerButton = new JButton("REGISTER");
		registerButton.setFont(new Font("Arial", Font.BOLD, 12));
		registerButton.setBorder(null);
		registerButton.setBorderPainted(false);
		registerButton.setFocusPainted(false);
		registerButton.setBackground(GREY_BASE);
		registerButton.setBounds(300, 420, 200, 25);
		registerButton.setForeground(BTN_TEXT);
		registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	registerButton.setBackground(GREY_MID);
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	registerButton.setBackground(GREY_BASE);
		    }
		});
		registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eHost)
            {
                //Execute when button is pressed
            	registerButtonPerformed(eHost);
            }
        }); 
		
		// 
		JTextField dummy_text = new JTextField();
		dummy_text.setEditable(false);
		connectPane.setLayout(null);
		connectPane.add(logo);
		connectPane.add(dummy_text);
		connectPane.add(username_text);
		connectPane.add(password_text);
		connectPane.add(connectButton);
		connectPane.add(registerButton);
		
		
		// =======================================================================================
		// ================================== HOME PAGE ==========================================
		// =======================================================================================
		homePage.setPreferredSize(new Dimension(800, 600));
		homePage.setBackground(new Color(0xFFFFFF));
		
		// friend panel -------------------------------------------------
		JPanel friendsPanel = new JPanel();
		friendsPanel.setBackground(LEFT_BG);
		friendsPanel.setBounds(20, 20, 430, 540);
		friendsPanel.setLayout(null);
		
		java.net.URL friend_URL = Client2.class.getResource("img/friend.png");
		BufferedImage friend_pic = ImageIO.read(friend_URL);
		ImageIcon friendIcon = new ImageIcon(friend_pic);
		JLabel friendHead = new JLabel("Friends", friendIcon, SwingConstants.LEFT);
		friendHead.setBounds(5, 2, 220, 25);
		friendHead.setFont(new Font("Arial", Font.PLAIN, 14));
		friendHead.setForeground(GREY_DARK);
		
		friendsPanel.add(friendHead);
		
		final JButton joinButton = new JButton("JOIN GAME");
		joinButton.setFont(new Font("Arial", Font.PLAIN, 14));
		joinButton.setBorder(null);
		joinButton.setBorderPainted(false);
		joinButton.setFocusPainted(false);
		joinButton.setBackground(PINK_BASE);
		joinButton.setBounds(360, 560, 90, 20);
		joinButton.setForeground(BTN_TEXT);
		joinButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	joinButton.setBackground(PINK_DARK);
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	joinButton.setBackground(PINK_BASE);
		    }
		});
		joinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eSend)
            {
                //Execute when button is pressed
            	joinButtonPerformed(eSend);
            }
        });     
		
		
		// disconnect button
		final JButton logoutButton = new JButton("LOG OUT");
		logoutButton.setFont(new Font("Arial", Font.PLAIN, 12));
		logoutButton.setBorder(null);
		logoutButton.setBorderPainted(false);
		logoutButton.setFocusPainted(false);
		logoutButton.setBackground(APP_BG);
		logoutButton.setBounds(710, 20, 70, 35);
		logoutButton.setForeground(TEXT_GREY);
		logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	logoutButton.setBackground(BASIC_HILIGHT);
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	logoutButton.setBackground(APP_BG);
		    }
		});
		logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eLogout)
            {
                //Execute when button is pressed
            	logoutButtonPerformed(eLogout);
            }
        });     
		
		// display name
		java.net.URL profile_URL = Client2.class.getResource("img/profile.png");
		BufferedImage profile_pic = ImageIO.read(profile_URL);
		ImageIcon profileIcon = new ImageIcon(profile_pic);
		// for server-client
		final JButton profileButton = new JButton("John Doe", profileIcon);
		profileButton.setFont(new Font("Arial", Font.BOLD, 12));
		profileButton.setBorder(null);
		profileButton.setBorderPainted(false);
		profileButton.setFocusPainted(false);
		profileButton.setBackground(APP_BG);
		profileButton.setBounds(470, 20, 220, 35);
		profileButton.setForeground(TEXT_GREY);
		profileButton.setHorizontalAlignment(SwingConstants.LEFT);
		
//		profile = new JLabel("Username", profileIcon, SwingConstants.LEFT);
//		profile.setBounds(470, 20, 220, 35);
		
		// user online panel --------------------------------------------
		JPanel userOnlinePanel = new JPanel();
		userOnlinePanel.setBackground(RIGHT_BG);
		userOnlinePanel.setBounds(470, 55, 310, 505);
		userOnlinePanel.setLayout(null);
		
		java.net.URL online_URL = Client2.class.getResource("img/online.png");
		BufferedImage online_pic = ImageIO.read(online_URL);
		ImageIcon onlyIcon = new ImageIcon(online_pic);
		JLabel onlineHead = new JLabel("User online", onlyIcon, SwingConstants.LEFT);
		onlineHead.setBounds(5, 2, 220, 25);
		onlineHead.setFont(new Font("Arial", Font.PLAIN, 14));
		onlineHead.setForeground(GREY_DARK);
		
		userOnlinePanel.add(onlineHead);
		
		// add friend button
		final JButton addFriendButton = new JButton("ADD FRIEND");
		addFriendButton.setFont(new Font("Arial", Font.PLAIN, 14));
		addFriendButton.setBorder(null);
		addFriendButton.setBorderPainted(false);
		addFriendButton.setFocusPainted(false);
		addFriendButton.setBackground(PINK_BASE);
		addFriendButton.setBounds(680, 560, 100, 20);
		addFriendButton.setForeground(BTN_TEXT);
		addFriendButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	addFriendButton.setBackground(PINK_DARK);
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	addFriendButton.setBackground(PINK_BASE);
		    }
		});
		addFriendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eSend)
            {
                //Execute when button is pressed
            	addFriendButtonPerformed(eSend);
            }
        });     
		
		homePage.setLayout(null);
		homePage.add(friendsPanel);
		homePage.add(joinButton);
		homePage.add(logoutButton);
		homePage.add(profileButton);
		homePage.add(userOnlinePanel);
		homePage.add(addFriendButton);
		
		// =======================================================================================
		// ================================== PLAY PAGE ==========================================
		// =======================================================================================
		
		playPage.setPreferredSize(new Dimension(800, 600));
		playPage.setBackground(new Color(0xFFFFFF));
		
		// Tic Tac Toe panel -------------------------------------------------
		java.net.URL img_play_URL = Client2.class.getResource("img/bg_play.png");
		BufferedImage bg_play = ImageIO.read(img_play_URL);
		ImagePanel playPane = new ImagePanel(bg_play);
		playPane.setBackground(new Color(0xe2e2e2));
		playPane.setBounds(20, 20, 430, 430);
		
		
		b0 = new OXButton("0");
		b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eOX1)
            {
            	if(b0.currentState == OXButton.STATE_EMPTY){oxButtonPerformed(eOX1);}
            }
        });
		b1 = new OXButton("1");
		b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eOX2)
            {
            	if(b1.currentState == OXButton.STATE_EMPTY){oxButtonPerformed(eOX2);}
            }
        });
		b2 = new OXButton("2");
		b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eOX3)
            {
            	if(b2.currentState == OXButton.STATE_EMPTY){oxButtonPerformed(eOX3);}
            }
        });
		b3 = new OXButton("3");
		b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eOX4)
            {
            	if(b3.currentState == OXButton.STATE_EMPTY){oxButtonPerformed(eOX4);}
            }
        });
		b4 = new OXButton("4");
		b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eOX5)
            {
            	if(b4.currentState == OXButton.STATE_EMPTY){oxButtonPerformed(eOX5);}
            }
        });
		b5 = new OXButton("5");
		b5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eOX6)
            {
            	if(b5.currentState == OXButton.STATE_EMPTY){oxButtonPerformed(eOX6);}
            }
        });
		b6 = new OXButton("6");
		b6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eOX7)
            {
            	if(b6.currentState == OXButton.STATE_EMPTY){oxButtonPerformed(eOX7);}
            }
        });
		b7 = new OXButton("7");
		b7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eOX8)
            {
            	if(b7.currentState == OXButton.STATE_EMPTY){oxButtonPerformed(eOX8);}
            }
        });
		b8 = new OXButton("8");
		b8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eOX9)
            {
            	if(b8.currentState == OXButton.STATE_EMPTY){oxButtonPerformed(eOX9);}
            }
        });
		
		playPane.setLayout(new GridLayout(3,3));
		playPane.add(b0);
		playPane.add(b1);
		playPane.add(b2);
		playPane.add(b3);
		playPane.add(b4);
		playPane.add(b5);
		playPane.add(b6);
		playPane.add(b7);
		playPane.add(b8);
		
		
		
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
		disconnectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eDisconnect)
            {
                //Execute when button is pressed
            	disconnectButtonPerformed(eDisconnect);
            }
        });     
		
//		// display name
//		java.net.URL profile_URL = Client2.class.getResource("img/profile.png");
//		BufferedImage profile_pic = ImageIO.read(profile_URL);
//		ImageIcon profileIcon = new ImageIcon(profile_pic);
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
		JPanel textPanel = new JPanel();
		textPanel.setBackground(new Color(0xf3e9e9));
		textPanel.setBounds(470, 55, 310, 395);
		textPanel.setLayout(null);
		
		chatText = new ChatText();
		chatText.setBounds(0, 0, 310, 395);
		
//		chatText.setPreferredSize(new Dimension(100, 30));
		
		
//		textPanel.add(test4);
//		JPanel box = new JPanel();
//		box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
//		box.setBounds(0, 0, 310, 395);
////		setFixedWidth(box, 300);
//		box.add(chatText);
		
		textPanel.add(chatText);
		
		// score panel -------------------------------------------------------
		JPanel scorePane = new JPanel();
		scorePane.setBackground(APP_BG);
		scorePane.setBounds(20, 470, 430, 110);
		scorePane.setLayout(null);
			java.net.URL img_o_URL = Client2.class.getResource("img/bg_o.png");
			BufferedImage bg_o = ImageIO.read(img_o_URL);
			ImagePanel scoreOPane = new ImagePanel(bg_o);
			scoreOPane.setBounds(0, 0, 205, 110);
			nameO = new JLabel();
			nameO.setText("PopPio: 0");
			nameO.setForeground(TEXT_GREY);
//			nameO.setOpaque(true);
//			nameO.setBackground(Color.red);
			nameO.setBounds(5, 0, 205, 30);
			nameO.setFont(new Font("Arial", Font.PLAIN, 16));
			
			oGiveup = new JButton("give up",giveup_icon);
			oGiveup.setFont(new Font("Arial", Font.BOLD, 12));
			oGiveup.setForeground(TEXT_GREY);
			oGiveup.setBorder(null);
			oGiveup.setBorderPainted(false);
			oGiveup.setContentAreaFilled(false);
			oGiveup.setFocusPainted(false);
			oGiveup.setBounds(0, 80, 80, 30);
			oGiveup.addMouseListener(new java.awt.event.MouseAdapter() {
			    public void mouseEntered(java.awt.event.MouseEvent evt) {
			    	oGiveup.setForeground(PINK_BASE);
			    	oGiveup.setIcon(giveup_hover_icon);
			    }
			    public void mouseExited(java.awt.event.MouseEvent evt) {
			    	oGiveup.setForeground(TEXT_GREY);
			    	oGiveup.setIcon(giveup_icon);
			    }
			});
			oGiveup.setVisible(false);
			oGiveup.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent eDisconnect)
	            {
	            	try {
	            		// TODO send give up message
						win("x");
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        });
			
			scoreOPane.setLayout(null);
			scoreOPane.add(nameO);
			scoreOPane.add(oGiveup);
			scorePane.add(scoreOPane);
			
		
			java.net.URL img_x_URL = Client2.class.getResource("img/bg_x.png");
			BufferedImage bg_x = ImageIO.read(img_x_URL);
			ImagePanel scoreXPane = new ImagePanel(bg_x);
			scoreXPane.setBounds(225, 0, 205, 110);
			nameX = new JLabel();
			nameX.setText("PopPio: 0");
			nameX.setForeground(TEXT_GREY);
//			nameX.setOpaque(true);
//			nameX.setBackground(Color.red);
			nameX.setBounds(5, 0, 205, 30);
			nameX.setFont(new Font("Arial", Font.PLAIN, 16));
			
			xGiveup = new JButton("give up",giveup_icon);
			xGiveup.setFont(new Font("Arial", Font.BOLD, 12));
			xGiveup.setForeground(TEXT_GREY);
			xGiveup.setBorder(null);
			xGiveup.setBorderPainted(false);
			xGiveup.setContentAreaFilled(false);
			xGiveup.setFocusPainted(false);
			xGiveup.setBounds(0, 80, 80, 30);
			xGiveup.addMouseListener(new java.awt.event.MouseAdapter() {
			    public void mouseEntered(java.awt.event.MouseEvent evt) {
			    	xGiveup.setForeground(PINK_BASE);
			    	xGiveup.setIcon(giveup_hover_icon);
			    }
			    public void mouseExited(java.awt.event.MouseEvent evt) {
			    	xGiveup.setForeground(TEXT_GREY);
			    	xGiveup.setIcon(giveup_icon);
			    }
			});
			xGiveup.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent eDisconnect)
	            {
	            	try {
	            		// TODO send give up message
						win("o");
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        });
			
			xGiveup.setVisible(false);
			
			scoreXPane.setLayout(null);
			scoreXPane.add(nameX);
			scoreXPane.add(xGiveup);
			scorePane.add(scoreXPane);
		
		// chat panel -------------------------------------------------------
		JPanel chatPane = new JPanel();
		chatPane.setBackground(new Color(0xecedec));
		chatPane.setBounds(470, 470, 310, 90);
		chatArea = new JTextArea("", 5, 27);
		chatArea.setLineWrap(true);
		chatArea.setWrapStyleWord(true);
		chatArea.setBounds(0, 0, 310, 90);
		chatArea.setOpaque(false);
		chatArea.setBorder(null);
		PromptSupport.setPrompt("Send a message ...", chatArea);
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
		sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eSend)
            {
                //Execute when button is pressed
            	sendButtonPerformed(eSend);
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
		playPage.setLayout(null);
		playPage.add(playPane);
		playPage.add(disconnectButton);
		playPage.add(profile);
		playPage.add(textPanel);
		playPage.add(scorePane);
		playPage.add(chatPane);
		playPage.add(sendButton);
		playPage.add(resetButton);
		
		
		
		// ======================================
		// ==========Frame set up================
		// ======================================
		// set icon
		java.net.URL icon_URL = Client2.class.getResource("img/logo_small.png");
		BufferedImage frame_icon = ImageIO.read(icon_URL);
		setIconImage(frame_icon);
		setTitle("Extreme Tic Tac Toe");
		getContentPane().add(connectPane, BorderLayout.CENTER);
		
		pack();
		setLocationRelativeTo(null); // place JFrame in center of screen
//		setVisible(true);
	}// END initcomponent()
	
	
	
	// ************************ Action Events **************************
	private void connectButtonPerformed(ActionEvent evt) throws IOException {
		// change to play panel 
		// insert form validation if have time
		String username = username_text.getText().equalsIgnoreCase("") ? "username" : username_text.getText();
		String password = password_text.getText().equalsIgnoreCase("") ? "password" : password_text.getText();
		
		// send to server

 		// TODO perform connection , socket bla bla
		
		// server response
		playerName = "PopPio"; // edit this
		profile.setText(playerName);
		
 		
 		redirectToHomePanel();
	}
	private void registerButtonPerformed(ActionEvent evt) {
		
	}
	
	
	private void disconnectButtonPerformed(ActionEvent evt) {
		// TODO clear connection, bla bla bla
		
		System.out.println("Disconnected");
		redirectToConnectPanel();
	}
	private void logoutButtonPerformed(ActionEvent evt) {
		System.out.println("Loging out");
		redirectToHomePanel();
	}
	private void joinButtonPerformed(ActionEvent evt) {
		
	}
	private void addFriendButtonPerformed(ActionEvent evt) {
		
	}
	private void sendButtonPerformed(ActionEvent evt) {
		String text = chatArea.getText().trim();
		System.out.println("Send meesage");
		Color chatColor;
		if(currentSide.equalsIgnoreCase("x")||currentSide.equalsIgnoreCase("o")){
			chatColor = ChatText.PLAYER1;
		}else{// spec
			chatColor = ChatText.GREY_BASE;
		}
		if(!text.equals("")){
			chatText.addRow(playerName, chatColor, text);
			chatArea.setText("");
		}
		// TODO send text to server
	}
	
	
	private void oxButtonPerformed(ActionEvent evt) {
		if( (currentTurn.equalsIgnoreCase("o") && currentSide.equalsIgnoreCase("o")) || (currentTurn.equalsIgnoreCase("x") && currentSide.equalsIgnoreCase("x")) ){
			// can click
			switchTurn();
			OXButton oxClick = (OXButton) evt.getSource();
			System.out.println("press button"+oxClick.position);
			// set button to display tick
			if(currentSide.equalsIgnoreCase("x")){
				oxClick.tickX();
			}else if(currentSide.equalsIgnoreCase("o")){
				oxClick.tickO();
			}
			if(oxClick.position==6){
				try {
					win("o");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(oxClick.position==7){
				try {
					win("x");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// TODO send data to server
			
		}else{
			// click in opponent's turn, do nothing
		}
	}
	// ************************ Useful Methods ************************
	private void createGame() { // call this when receive connection
		
 		// accept connection
	 	opponentName = "Touch"; //edit this
	 	
	 	chatText.addInfo(opponentName+" connected");
		
		// random side
	 	if(random.nextBoolean()){
	 		setSide("o");
	 		// send x to opponent
	 	}else{
	 		setSide("x");
	 		// send o to opponent
	 	}
	 	
		// send to client
 		
 		setCurrentTurn("x"); // fix, x always go first
 		
 		nameO.setVisible(true);
 		nameX.setVisible(true);
	}
	private void redirectToHomePanel() {
		// for connect button
		getContentPane().removeAll();
		getContentPane().add(homePage, BorderLayout.CENTER);
		
		// show list of friends
		
		// show list of online player
		
		revalidate();
 		repaint();
	}
	private void redirectToPlayPanel() {
		
		getContentPane().removeAll();
		getContentPane().add(playPage, BorderLayout.CENTER);
 		//getContentPane().remove(connectPane);
		
		// clean up sone UI
		chatArea.setText("");
		try {
			chatText.reset();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		chatText.addInfo("Welcome, "+playerName);
 		revalidate();
 		repaint();
	}
	private void redirectToConnectPanel() {
		// for disconnect button
		getContentPane().removeAll();
		getContentPane().add(connectPane, BorderLayout.CENTER);
		
 		revalidate();
 		repaint();
	}
	private void resetAllButton() throws IOException{
		// for start new game
		b0.reset();
		b1.reset();
		b2.reset();
		b3.reset();
		b4.reset();
		b5.reset();
		b6.reset();
		b7.reset();
		b8.reset();
		
	}
	private void receiveChat(int chatType,String chat){ //chatType: 1=from opponent, 2=from spec, 3=from player1 and you are spec;
		Color color;
		if(chatType == 1){
			color = ChatText.PLAYER2;
		}else if(chatType == 2){
			color = ChatText.SPEC;
		}else{
			color = ChatText.PLAYER1;
		}
		chat = chat.trim();
		//System.out.println("Receive meesage");
		if(!chat.equals("")){
			chatText.addRow(opponentName, color, chat);
		}
	}
	/**
	 * set side of this player
	 * @param side
	 */
	private void setSide(String side){
		// set side of this player
		b0.setType(side);
		b1.setType(side);
		b2.setType(side);
		b3.setType(side);
		b4.setType(side);
		b5.setType(side);
		b6.setType(side);
		b7.setType(side);
		b8.setType(side);
		
		currentSide = side;
		if(currentSide.equalsIgnoreCase("o")){
			playerXName = opponentName;
			playerOName = playerName;
			oGiveup.setVisible(true);
			xGiveup.setVisible(false);
		}else if(currentSide.equalsIgnoreCase("x")){
			playerXName = playerName;
			playerOName = opponentName;
			oGiveup.setVisible(false);
			xGiveup.setVisible(true);
		}else{
			// spectator mode
			// server have to provide playerXName and playerOName in connect method
		}
		nameX.setText(playerXName+": "+scoreX);
		nameO.setText(playerOName+": "+scoreO);
		
	}
	public void setCurrentTurn(String currentTurn) {
		if(currentTurn.equalsIgnoreCase("o")){
			if(currentSide.equalsIgnoreCase("o")){
				setTurn(true);
			}else if(currentSide.equalsIgnoreCase("x")){
				setTurn(false);
			}
			nameO.setFont(new Font("Arial", Font.BOLD, 16));
			nameX.setFont(new Font("Arial", Font.PLAIN, 16));
			this.currentTurn = "o";
		}else if(currentTurn.equalsIgnoreCase("x")) {
			if(currentSide.equalsIgnoreCase("o")){
				setTurn(false);
			}else if(currentSide.equalsIgnoreCase("x")){
				setTurn(true);
			}
			nameO.setFont(new Font("Arial", Font.PLAIN, 16));
			nameX.setFont(new Font("Arial", Font.BOLD, 16));
			this.currentTurn = "x";
		}
	}
	private void switchTurn() {
		if(currentTurn.equalsIgnoreCase("o")){// o turn
			// switch to x turn
			currentTurn = "x";
			if(currentSide.equalsIgnoreCase("x")){
				setTurn(true);
			}else{
				setTurn(false);
			}
			nameO.setFont(new Font("Arial", Font.PLAIN, 16));
			nameX.setFont(new Font("Arial", Font.BOLD, 16));
		}else if(currentTurn.equalsIgnoreCase("x")){ // x turn
			// switch to o turn
			currentTurn = "o";
			if(currentSide.equalsIgnoreCase("o")){
				setTurn(true);
			}else{
				setTurn(false);
			}
			nameO.setFont(new Font("Arial", Font.BOLD, 16));
			nameX.setFont(new Font("Arial", Font.PLAIN, 16));
		}
	}
	private void switchSide() {
		// switch side after win
		int scoreTemp = scoreO;
		scoreO = scoreX;
		scoreX = scoreTemp;
		
		String playerNameTemp = playerOName;
		playerOName = playerXName;
		playerXName = playerNameTemp;
		
		if(currentSide.equalsIgnoreCase("o")){
			setSide("x");
		}else if(currentSide.equalsIgnoreCase("x")){
			setSide("o");
		}
	}
	/**
	 * set button state
	 * @param turn
	 */
	private void setTurn(boolean turn) {
		b0.yourTurn = turn;
		b1.yourTurn = turn;
		b2.yourTurn = turn;
		b3.yourTurn = turn;
		b4.yourTurn = turn;
		b5.yourTurn = turn;
		b6.yourTurn = turn;
		b7.yourTurn = turn;
		b8.yourTurn = turn;
		
	}
	public void win(String side) throws IOException {
		// call this when win
		System.out.println(side+" wins !!!");
		if(side.equalsIgnoreCase("o")){
			scoreO++;
			chatText.addInfo(playerOName + " wins !!!");
		}else if(side.equalsIgnoreCase("x")){
			scoreX++;
			chatText.addInfo(playerXName + " wins !!!");
		}
		switchSide();
		resetAllButton();
		setCurrentTurn("x");
		
	}
	
	public static void setFixedWidth( Component component, int width ){
		component.setSize( new Dimension( width, Short.MAX_VALUE ) );
		Dimension preferredSize = component.getPreferredSize();
		component.setPreferredSize( new Dimension( width, preferredSize.height ) );
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                
                try {
					new Client2().setVisible(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
	}

}