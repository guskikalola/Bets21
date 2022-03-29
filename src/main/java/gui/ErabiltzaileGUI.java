package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Erabiltzailea;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.event.ActionEvent;

public class ErabiltzaileGUI extends JFrame {

	private JPanel contentPane;
	private static ErabiltzaileGUI frame;
	private Erabiltzailea erabiltzailea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new ErabiltzaileGUI(new Erabiltzailea("a","b",new Date()));
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
	public ErabiltzaileGUI(Erabiltzailea erabiltzailea) {
		
		frame= this;
		this.erabiltzailea = erabiltzailea;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ERABILTZAILEA");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(22, 37, 158, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Gertaerak ikusi");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				FindQuestionsGUI f= new FindQuestionsGUI();
				f.setVisible(true);
		}});
		btnNewButton.setBounds(61, 124, 148, 50);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Zer egin nahi duzu?");
		lblNewLabel_1.setBounds(75, 98, 120, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblKaixoErabiltzailea = new JLabel("Kaixo, erabiltzailea");
		lblKaixoErabiltzailea.setBounds(322, 38, 120, 14);
		contentPane.add(lblKaixoErabiltzailea);
		
		lblKaixoErabiltzailea.setText(this.erabiltzailea.getIzena());
		
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
		
		JButton btnDiruaSartu = new JButton("Dirua Sartu");
		btnDiruaSartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				facade.eguneratuHistorala(frame);
				frame.setVisible(false);
				DiruaSartuGUI f = new DiruaSartuGUI();
				f.setVisible(true);
				
				
			}
		});
		btnDiruaSartu.setBounds(245, 123, 148, 51);
		contentPane.add(btnDiruaSartu);
		
		JButton btnEmaitzaIpini = new JButton("Emaitza Ipini");
		btnEmaitzaIpini.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				facade.eguneratuHistorala(frame);
				frame.setVisible(false);
				EmaitzakIpiniGUI f = new EmaitzakIpiniGUI();
				f.setVisible(true);
				
				
			}
		});
		btnEmaitzaIpini.setBounds(132, 214, 201, 25);
		contentPane.add(btnEmaitzaIpini);
	}
}	

