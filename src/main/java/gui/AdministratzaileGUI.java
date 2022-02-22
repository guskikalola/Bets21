package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Event;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;


public class AdministratzaileGUI extends JFrame {

	private JPanel contentPane;
	private static AdministratzaileGUI frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new AdministratzaileGUI();
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
	public AdministratzaileGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Administratzailea");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(25, 11, 180, 36);
		contentPane.add(lblNewLabel);
		
		JButton btnKuotakIpini = new JButton("Kuotak ipini");
		btnKuotakIpini.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//KuotakGUI k = new KuotakGUI();
				//k.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnKuotakIpini.setBounds(25, 119, 111, 60);
		contentPane.add(btnKuotakIpini);
		
		JButton btnNewButton = new JButton("Galdera sortu");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateQuestionGUI cqGUI = new CreateQuestionGUI(new Vector<Event>());
				cqGUI.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnNewButton.setBounds(156, 119, 118, 60);
		contentPane.add(btnNewButton);
		
		JLabel lblZerEginNahi = new JLabel("Zer egin nahi duzu?");
		lblZerEginNahi.setBounds(35, 59, 111, 14);
		contentPane.add(lblZerEginNahi);
		
		JButton btnNewButton_1 = new JButton("Gertaera sortu");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//CreateEventGUI ceGUI = new CreateEventGUI();
				//ceGUI.setVisible(true);
				frame.setVisible(false);
				
			}
		});
		btnNewButton_1.setBounds(301, 119, 111, 60);
		contentPane.add(btnNewButton_1);
	}
}

