package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
		lblNewLabel.setBounds(37, 25, 158, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Gertaerak ikusi");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				FindQuestionsGUI f= new FindQuestionsGUI();
				f.setVisible(true);
		}});
		btnNewButton.setBounds(142, 126, 148, 50);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Zer egin nahi duzu?");
		lblNewLabel_1.setBounds(75, 98, 120, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblKaixoErabiltzailea = new JLabel("Kaixo, erabiltzailea");
		lblKaixoErabiltzailea.setBounds(326, 26, 120, 14);
		contentPane.add(lblKaixoErabiltzailea);
		
		lblKaixoErabiltzailea.setText(this.erabiltzailea.getIzena());
	}
}
