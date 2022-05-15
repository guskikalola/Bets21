package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Admin;
import domain.BlokeoContainer;
import domain.Erabiltzailea;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class BlokeoGUI extends JFrame {

	private JPanel contentPane;
	private static BlokeoGUI frame;
	private JButton atzeraButton;
	private JButton mezuaBidaliButton;
	private static Admin blokeatuAdmin;
	private static Erabiltzailea aukeratutakoErabiltzailea;
	private static String arrazoia;
	private JLabel blockAccountLabel;
	private JLabel adminLabel;
	private JLabel reasonLabel;
	private JLabel adminValueLabel;
	private JLabel reasonValueLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BlokeoGUI frame = new BlokeoGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BlokeoGUI() {
		
		frame= this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		BLFacade facade = MainGUI.getBusinessLogic();
		if(MainGUI.getLoginErabiltzailea() instanceof Erabiltzailea) {
			aukeratutakoErabiltzailea= (Erabiltzailea) MainGUI.getLoginErabiltzailea();
		}	
		BlokeoContainer blC= facade.getBlokeoContainer(aukeratutakoErabiltzailea);
		arrazoia= blC.getBl().getArrazoia();
		blokeatuAdmin= blC.getNor();
		
		
		blockAccountLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Account_block"));
		blockAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		blockAccountLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		blockAccountLabel.setBounds(20, 69, 410, 27);
		contentPane.add(blockAccountLabel);
		
		adminLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("admin") + ":");
		adminLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		adminLabel.setBounds(31, 130, 104, 17);
		contentPane.add(adminLabel);
		
		reasonLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Reason") + ":");
		reasonLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		reasonLabel.setBounds(31, 158, 68, 23);
		contentPane.add(reasonLabel);
		
		adminValueLabel = new JLabel(blokeatuAdmin.getIzena());
		adminValueLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		adminValueLabel.setBounds(145, 130, 189, 17);
		contentPane.add(adminValueLabel);
		
		reasonValueLabel = new JLabel(arrazoia);
		reasonValueLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		reasonValueLabel.setBounds(145, 161, 189, 17);
		contentPane.add(reasonValueLabel);
		
		// Atzera egiteko butoia
		atzeraButton = new JButton("<");
		atzeraButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame atzekoa = MainGUI.atzeraEgin();
				frame.setVisible(false);
				atzekoa.setVisible(true);
			}
		});
		atzeraButton.setBounds(10, 24, 68, 27);
		contentPane.add(atzeraButton);
		
		
		
		mezuaBidaliButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Send_Messages") + " (" + blokeatuAdmin.getIzena() + ")");
		mezuaBidaliButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI.eguneratuHistorala(frame);
				frame.setVisible(false);
				ChatGUI chat = new ChatGUI(blokeatuAdmin);
				chat.setVisible(true);
			}	
		});
		mezuaBidaliButton.setBounds(218, 220, 183, 33);
		contentPane.add(mezuaBidaliButton);
		

	}
}
