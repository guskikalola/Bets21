package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Admin;
import domain.Erabiltzailea;
import domain.Pertsona;

import javax.swing.JLabel;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private JPanel contentPane;
	private JPasswordField password;
	private JPasswordField confirm_passwordField;
	private JTextField user;
	private JComboBox urtea_comboBox;
	private DefaultComboBoxModel<Integer> urtea_model;
	private JComboBox hilabetea_comboBox;
	private DefaultComboBoxModel<String> hilabetea_model;
	private JComboBox eguna_comboBox;
	private DefaultComboBoxModel<Integer> eguna_model;
	private JLabel lblErrorea;
	public static JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new RegisterGUI();
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
	public RegisterGUI() {

		frame = this;
		
		urtea_model = new DefaultComboBoxModel<Integer>();
		hilabetea_model = new DefaultComboBoxModel<String>();
		eguna_model = new DefaultComboBoxModel<Integer>();
		
		// Bete urteak
		for(int i = 2022; i >= 1950; i--) {
			urtea_model.addElement(i);
		}
		// Bete hilabeteak
		// TODO: Bete hilabeteak
		hilabetea_model.addElement("Urtarrila");
		
		// Bete egunak
		for(int i = 1; i <= 31; i++) {	
			eguna_model.addElement(i);
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblRegister = new JLabel("Register");
		lblRegister.setFont(new Font("Dialog", Font.BOLD, 16));
		lblRegister.setBounds(12, 12, 136, 15);
		contentPane.add(lblRegister);

		JLabel lblUser = new JLabel("User:");
		lblUser.setBounds(35, 57, 73, 15);
		contentPane.add(lblUser);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(35, 96, 103, 15);
		contentPane.add(lblPassword);

		JLabel lblRepitPassword = new JLabel("Confirm Password:");
		lblRepitPassword.setBounds(34, 130, 146, 15);
		contentPane.add(lblRepitPassword);

		JLabel lblBirthday = new JLabel("Birthday: ");
		lblBirthday.setBounds(35, 163, 96, 15);
		contentPane.add(lblBirthday);

		// regist botoia
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblErrorea.setText("");
				String izena = user.getText();
				String pasahitza = password.getText();
				String pasahitzaErrepikatu = confirm_passwordField.getText();
				Date jaiotzeData = new Date();
				// TODO: Bete ComboBox-a
				jaiotzeData.setYear((Integer)urtea_comboBox.getSelectedItem());
				jaiotzeData.setMonth(hilabetea_comboBox.getSelectedIndex());
				// eguna
				BLFacade facade = MainGUI.getBusinessLogic();

				if (!pasahitza.equals(pasahitzaErrepikatu)) {
					System.out.println("Errorea: Pasahitzak ez dira berdinak");
					lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("errorea_pasahitza_ez_zuzena"));
				} else {

					Pertsona er = facade.erregistratu(izena, pasahitza, jaiotzeData);
					facade.setLoginErabiltzailea(er);
					if (er != null) {
						System.out.println(e);
						if (er instanceof Erabiltzailea) {
							frame.setVisible(false);
							ErabiltzaileGUI era = new ErabiltzaileGUI((Erabiltzailea) er);
							era.setVisible(true);
						} else if (er instanceof Admin) {
							frame.setVisible(false);
							AdministratzaileGUI adm = new AdministratzaileGUI((Admin) er);
							adm.setVisible(true);
						}
					} else {

						System.out.println("Errorea: Erabiltzailea existitzen da DBan edo ez du adin nahikoa");
						// Errorea
					}
				}
			}
		});
		btnRegister.setBounds(161, 216, 117, 25);
		contentPane.add(btnRegister);

		// urtea comboBox
		urtea_comboBox = new JComboBox();
		urtea_comboBox.setModel(urtea_model);
		urtea_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		urtea_comboBox.setBounds(138, 163, 84, 15);
		contentPane.add(urtea_comboBox);

		// hilabetea comboBox
		hilabetea_comboBox = new JComboBox();
		hilabetea_comboBox.setModel(hilabetea_model);
		hilabetea_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		hilabetea_comboBox.setBounds(233, 163, 117, 15);
		contentPane.add(hilabetea_comboBox);

		// eguna comboBox
		eguna_comboBox = new JComboBox();
		eguna_comboBox.setModel(eguna_model);
		eguna_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		eguna_comboBox.setBounds(362, 163, 60, 15);
		contentPane.add(eguna_comboBox);

		password = new JPasswordField();
		password.setBounds(185, 94, 237, 19);
		contentPane.add(password);

		confirm_passwordField = new JPasswordField();
		confirm_passwordField.setBounds(185, 128, 237, 19);
		contentPane.add(confirm_passwordField);

		user = new JTextField();
		user.setBounds(185, 55, 237, 19);
		contentPane.add(user);
		user.setColumns(10);
		
		lblErrorea = new JLabel();
		lblErrorea.setBounds(28, 242, 362, 17);
		contentPane.add(lblErrorea);
	}
}
