import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.KeyAdapter;


public class Interfeces extends JFrame {
	/**
	 *  LOL interfeces. (oh god, there's a poop joke in my code...)
	 */
	private boolean notifyMe;
	private dummyFrame dummy = new dummyFrame();
	private String hostname;
	private String port;
	private int refresh = 10;
	private StatusResponse status;
	private Fetcher fetch = new Fetcher();
	private String console;
	private long time = System.currentTimeMillis();
	private int max = 20;
	private int connected = 0;
	private String stringMOTD;
	private String stringMOTDold;
	private static final long serialVersionUID = -5326244311730477265L;
	private JTextField HostnameField;
	private JTextField PortField;
	private JTextArea Output;
	private String oldPort;
	private JProgressBar usersConnected;
	private JLabel Ping;
	private String pingTime;
	private JLabel lblMOTD;
	private JTextArea MOTD;
	private String oldHostname = "old";
	private JTextArea UserList;
	private String connectedPlayers;
	private int connectedOld;
	private String OldPlayerList;

	public Interfeces() {

		ML listener = new ML();
		getContentPane().setBackground(Color.GRAY);
		setBackground(Color.GRAY);
		setMinimumSize(new Dimension(80, 60));
		setPreferredSize(new Dimension(435, 520));
		setTitle("McServerChecker");
		setSize(new Dimension(435, 520));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getContentPane().setLayout(null);
		HostnameField = new JTextField();
		HostnameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getExtendedKeyCode() == 10) {
					ConsolePrint("Refreshing...");
					refresh();
				}
			}
		});
		HostnameField.setBounds(210, 10, 209, 30);
		getContentPane().add(HostnameField);
		HostnameField.setColumns(10);
		

		Output = new JTextArea();
		Output.setEditable(false);
		Output.setWrapStyleWord(true);
		Output.setText(">");
		Output.setLineWrap(true);
		Output.setFont(UIManager.getFont("Tree.font"));
		Output.setBounds(10, 301, 409, 140);
		getContentPane().add(Output);
		ConsolePrint("Starting.....");
		JLabel ServerIPLabel = new JLabel("Server Hostname/Address: ");
		ServerIPLabel.setBackground(new Color(240, 248, 255));
		ServerIPLabel.setBounds(10, 10, 190, 30);
		getContentPane().add(ServerIPLabel);

		PortField = new JTextField();
		PortField.setColumns(10);
		PortField.setBounds(210, 51, 209, 30);
		getContentPane().add(PortField);

		JLabel Port = new JLabel("Server Port (blank for default): ");
		Port.setBackground(new Color(240, 248, 255));
		Port.setBounds(10, 51, 190, 30);
		getContentPane().add(Port);
		ConsolePrint(".");
		JCheckBox NotifyChangesCheck = new JCheckBox("Notify Me Of Changes");
		NotifyChangesCheck.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				notifyMe = !notifyMe;
			}
		});
		NotifyChangesCheck.setBackground(Color.GRAY);
		NotifyChangesCheck.setActionCommand("Notify Of Changes");
		NotifyChangesCheck.setBounds(10, 260, 190, 30);
		getContentPane().add(NotifyChangesCheck);
		ConsolePrint(".");
		UserList = new JTextArea();
		UserList.setEditable(false);
		UserList.setFont(UIManager.getFont("Viewport.font"));
		UserList.setWrapStyleWord(true);
		UserList.setLineWrap(true);
		UserList.setText("Could Not Build User List");
		UserList.setBounds(210, 190, 209, 100);
		getContentPane().add(UserList);

		JLabel ConnectedUsers = new JLabel("Currently Connected Users: ");
		ConnectedUsers.setBounds(10, 187, 190, 30);
		getContentPane().add(ConnectedUsers);
		ConsolePrint(".");
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 92, 409, 2);
		getContentPane().add(separator);

		lblMOTD = new JLabel("Message Of The Day: ");
		lblMOTD.setBounds(10, 105, 190, 30);
		getContentPane().add(lblMOTD);
		MOTD = new JTextArea();
		MOTD.setEditable(false);
		MOTD.setFont(UIManager.getFont("Tree.font"));
		MOTD.setWrapStyleWord(true);
		MOTD.setText("Message Could Not Be Fetched");
		MOTD.setLineWrap(true);
		MOTD.setBounds(210, 105, 209, 30);
		getContentPane().add(MOTD);

		usersConnected = new JProgressBar();
		usersConnected.setForeground(Color.GREEN);
		usersConnected.setString("connected / max_connections");
		usersConnected.setStringPainted(true);
		usersConnected.setBounds(10, 146, 409, 30);
		getContentPane().add(usersConnected);
		usersConnected.setMinimum(0);
		usersConnected.setMaximum(max);
		usersConnected.setString(connected + "   /   " + max + "     Connected");


		ConsolePrint(".");
		Ping = new JLabel("Ping: ");
		Ping.setVerticalTextPosition(SwingConstants.TOP);
		Ping.setVerticalAlignment(SwingConstants.TOP);
		Ping.setBounds(10, 228, 190, 30);
		getContentPane().add(Ping);

		JButton Refresh = new JButton("Refresh\r\n");
		Refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//refreshMe
				ConsolePrint("Refreshing.");
				refresh();
			}


		});
		Refresh.setBounds(10, 452, 200, 30);
		getContentPane().add(Refresh);
		ConsolePrint(".");
		JButton ExitBtn = new JButton("Exit");
		ExitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		ExitBtn.setBounds(219, 452, 200, 30);
		getContentPane().add(ExitBtn);
		setVisible(true);
		this.addMouseMotionListener(listener);
		this.addKeyListener(listener);
		ConsolePrint("Started!");
		refresh();
		timer();
	}

	private void refresh() {
		try {
			if (port != oldPort || port == null || port.length() <= 0) {
				//System.err.println("port: "+port);
				port = PortField.getText();
				port = clean(port);
				ConsolePrint("Port was empty, set to default.");
				PortField.setText("25565");
			}
			hostname = HostnameField.getText();
			hostname = clean(hostname);
			if (hostname != "" && hostname.length() > 0) {
				if (hostname != oldHostname) {
					fetch.setAddress(new InetSocketAddress(hostname, Integer.parseInt(port)));
					oldHostname = hostname;
					//firstLoop = false;
				}
				status = fetch.fetchData();
				connected = status.getPlayers().getOnline();
				max = status.getPlayers().getMax();
				usersConnected.setMinimum(0);
				usersConnected.setMaximum(max);
				usersConnected.setValue(connected);
				usersConnected.setString(connected + "   /   " + max + "     Connected");
				pingTime = ("" +status.getTime());
				stringMOTD = status.getDescription();
				MOTD.setText(stringMOTD);
				connectedPlayers = "";
				if (status.getPlayers() != null) {
					if ((status.getPlayers().getSample().size()) <= 0) {
						UserList.setText("Nobody is playing currently;");
					} else {
						for (Player p : status.getPlayers().getSample()) {
							connectedPlayers += p.getName() + '\n';
						}
						UserList.setText(connectedPlayers);
					}
				}
			}
			oldPort = port.toString();
			Ping.setText("Ping: " + pingTime);
		} catch (NullPointerException nul) {
			//nul.printStackTrace(); //ignore this shit
		} catch (SocketTimeoutException conn) {
			ConsolePrint("Timed Out While Connecting!");
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		time = System.currentTimeMillis();
		if (notifyMe) {
			//throw up warning boxes when events happen
			//eg. change in connections/players
			if (stringMOTD.equalsIgnoreCase(stringMOTDold)) {
				//System.err.println("MOTD CHANGED!");
				JOptionPane.showMessageDialog(dummy, "The Message Of The Day Changed.");
				ConsolePrint("The Message Of The Day Changed.");
				stringMOTDold = stringMOTD.toLowerCase();
			} else if (connected != connectedOld) {
				//System.err.println("Someone connected or dis connected");
				JOptionPane.showMessageDialog(dummy, "Someone connected or disconnected. \nNow: "+status.getPlayers().playersToString() + "\nPrevious: "+OldPlayerList);
				ConsolePrint("Someone connected or disconnected.");
				connectedOld = connected;
				OldPlayerList = status.getPlayers().playersToString();
			} else {
				return;
			}
		}
	}
	private void timer() {
		while (true) {
			long time2 = System.currentTimeMillis();
			long elapsed = (time2 - time)/1000;
			if (elapsed > refresh) {
				refresh();
				ConsolePrint("Auto Refreshed. (every "+refresh+" secconds)");
				time = System.currentTimeMillis();
			}
		}
	}

	private void ConsolePrint(String s) {
		console = s + "\n" + console;
		Output.setText(console);
		if (console.length() > 1000000) {
			console = "";
		}
	}

	public String clean(String s) {
		String a = "";
		if (s == null) {
			return null;
		}
		a = s.trim();
		a = a.replaceAll(" ", "");
		a = a.replaceAll(",", "");
		a = a.replaceAll("\"", "");
		a = a.replaceAll("'", "");
		a = a.replaceAll(";", "");
		a = a.replaceAll(":", "");
		//a = a.replaceAll("]", "");
		//a = a.replaceAll("{", "");
		//§b§l§
		a = a.replaceAll("Ǣ, "");
		a = a.replaceAll("¢, "");
		a = a.replaceAll("|", "");
		a = a.replaceAll("`", "");
		a = a.replaceAll("~", "");
		a = a.replaceAll("", "");
		return a;
	}

	public static void main(String[] args) {
		new Interfeces();
	}
	class ML implements MouseMotionListener, KeyListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			//refresh();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			//refresh();
		}

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("Key Typed! Code: "+e.getID());
		}

		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("Key Typed! Code: "+e.getID());
		}

		@Override
		public void keyTyped(KeyEvent e) {
			System.out.println("Key Typed! Code: "+e.getID());
		}

	}
}
