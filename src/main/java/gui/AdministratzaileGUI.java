package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Admin;
import domain.Erabiltzailea;
import domain.Event;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.ActionEvent;


public class AdministratzaileGUI extends JFrame {

	private JPanel contentPane;
	private static AdministratzaileGUI frame;
	private Admin erabiltzailea;
	private JButton blockUsersButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new AdministratzaileGUI(new Admin("a","b",new Date()));
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
	public AdministratzaileGUI(Admin erabiltzailea) {
		
		frame = this;
		this.erabiltzailea = erabiltzailea;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("admin"));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(25, 24, 180, 36);
		contentPane.add(lblNewLabel);
		
		JButton btnKuotakIpini = new JButton(ResourceBundle.getBundle("Etiquetas").getString("kuota_ipini"));
		btnKuotakIpini.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainGUI.eguneratuHistorala(frame);
				KuotakIpiniGUI k = new KuotakIpiniGUI();
				k.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnKuotakIpini.setBounds(25, 103, 175, 25);
		contentPane.add(btnKuotakIpini);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI.eguneratuHistorala(frame);
				CreateQuestionGUI cqGUI = new CreateQuestionGUI(new Vector<Event>());
				cqGUI.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnNewButton.setBounds(25, 140, 175, 25);
		contentPane.add(btnNewButton);
		
		JLabel lblZerEginNahi = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("what_do_you_want_to_do"));
		lblZerEginNahi.setBounds(35, 59, 214, 14);
		contentPane.add(lblZerEginNahi);
		
		JButton btnNewButton_1 = new JButton(ResourceBundle.getBundle("Etiquetas").getString("gertaera_sortu"));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI.eguneratuHistorala(frame);
				frame.setVisible(false);
				GertaeraSortuGUI ceGUI = new GertaeraSortuGUI();
				ceGUI.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(212, 103, 175, 25);
		contentPane.add(btnNewButton_1);
		
		JLabel lblKaixoErabiltzaile = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("kaixo") + ", " + this.erabiltzailea.getIzena());
		lblKaixoErabiltzaile.setBounds(311, 24, 125, 14);
		lblKaixoErabiltzaile.setText(ResourceBundle.getBundle("Etiquetas").getString("kaixo") + ", " + this.erabiltzailea.getIzena());
		contentPane.add(lblKaixoErabiltzaile);
		
		JButton btnGertaeraezabatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("gertaera_ezabatu"));
		btnGertaeraezabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				MainGUI.eguneratuHistorala(frame);
				GertaeraEzabatuGUI rg = new GertaeraEzabatuGUI();
				rg.setVisible(true);
			}
		});
		btnGertaeraezabatu.setBounds(25, 177, 175, 25);
		contentPane.add(btnGertaeraezabatu);
		
		// Atzera egiteko butoia
		JButton button = new JButton("<");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFrame atzekoa = MainGUI.atzeraEgin();
				frame.setVisible(false);
				atzekoa.setVisible(true);
			}
		});
		button.setBounds(0, 0, 51, 25);
		contentPane.add(button);
		
		JButton btnEmaitzaipin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("emaitza_ipini")); //$NON-NLS-1$ //$NON-NLS-2$
		btnEmaitzaipin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI.eguneratuHistorala(frame);
				frame.setVisible(false);
				EmaitzakIpiniGUI em = new EmaitzakIpiniGUI();
				em.setVisible(true);
				
			}
		});
		btnEmaitzaipin.setBounds(212, 139, 175, 27);
		contentPane.add(btnEmaitzaipin);
		
		JButton MezuakBidaliButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Send_Messages")); 
		MezuakBidaliButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI.eguneratuHistorala(frame);
				frame.setVisible(false);
				MezuakBidaliGUI mb = new MezuakBidaliGUI();
				mb.setVisible(true);
			}
		});
		MezuakBidaliButton.setBounds(212, 177, 175, 25);
		contentPane.add(MezuakBidaliButton);
		
		blockUsersButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Block_user")); 
		blockUsersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI.eguneratuHistorala(frame);
				frame.setVisible(false);
				ErabiltzaileaBlokeatuGUI eb = new ErabiltzaileaBlokeatuGUI();
				eb.setVisible(true);
			}
		});
		blockUsersButton.setBounds(212, 210, 175, 25);
		contentPane.add(blockUsersButton);
		
		JButton GertaeraBikoiztuButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GertaeraBikoiztu"));  //$NON-NLS-1$ //$NON-NLS-2$
		GertaeraBikoiztuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI.eguneratuHistorala(frame);
				frame.setVisible(false);
				GertaeraBikoiztuGUI mb = new GertaeraBikoiztuGUI();
				mb.setVisible(true);
			}
		});
		GertaeraBikoiztuButton.setBounds(25, 212, 175, 21);
		contentPane.add(GertaeraBikoiztuButton);
	}
}

