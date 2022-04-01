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
import java.util.Vector;
import java.awt.event.ActionEvent;


public class AdministratzaileGUI extends JFrame {

	private JPanel contentPane;
	private static AdministratzaileGUI frame;
	private Admin erabiltzailea;

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
		
		JLabel lblNewLabel = new JLabel("Administratzailea");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(25, 24, 180, 36);
		contentPane.add(lblNewLabel);
		
		JButton btnKuotakIpini = new JButton("Kuotak ipini");
		btnKuotakIpini.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				KuotakIpiniGUI k = new KuotakIpiniGUI();
				k.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnKuotakIpini.setBounds(25, 85, 111, 60);
		contentPane.add(btnKuotakIpini);
		
		JButton btnNewButton = new JButton("Galdera sortu");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateQuestionGUI cqGUI = new CreateQuestionGUI(new Vector<Event>());
				cqGUI.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnNewButton.setBounds(158, 85, 118, 60);
		contentPane.add(btnNewButton);
		
		JLabel lblZerEginNahi = new JLabel("Zer egin nahi duzu?");
		lblZerEginNahi.setBounds(35, 59, 111, 14);
		contentPane.add(lblZerEginNahi);
		
		JButton btnNewButton_1 = new JButton("Gertaera sortu");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				GertaeraSortuGUI ceGUI = new GertaeraSortuGUI();
				ceGUI.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(298, 85, 111, 60);
		contentPane.add(btnNewButton_1);
		
		JLabel lblKaixoErabiltzaile = new JLabel("Kaixo, erabiltzaile");
		lblKaixoErabiltzaile.setBounds(311, 24, 125, 14);
		lblKaixoErabiltzaile.setText("Kaixo, " + this.erabiltzailea.getIzena());
		contentPane.add(lblKaixoErabiltzaile);
		
		JButton btnGertaeraezabatu = new JButton("gertaera_ezabatu");
		btnGertaeraezabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				BLFacade facade = MainGUI.getBusinessLogic();
				facade.eguneratuHistorala(frame);
				GertaeraEzabatuGUI rg = new GertaeraEzabatuGUI();
				rg.setVisible(true);
			}
		});
		btnGertaeraezabatu.setBounds(123, 211, 175, 25);
		contentPane.add(btnGertaeraezabatu);
		
		// Atzera egiteko butoia
		JButton button = new JButton("<");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				JFrame atzekoa = facade.atzeraEgin();
				frame.setVisible(false);
				atzekoa.setVisible(true);
			}
		});
		button.setBounds(12, 0, 41, 27);
		contentPane.add(button);
	}
}

