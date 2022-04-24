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
	
	private String urtarrila= new String(ResourceBundle.getBundle("Etiquetas").getString("January"));
	private String otsaila = new String(ResourceBundle.getBundle("Etiquetas").getString("February"));
	private String martxoa = new String(ResourceBundle.getBundle("Etiquetas").getString("March"));
	private String apirila = new String(ResourceBundle.getBundle("Etiquetas").getString("April"));
	private String maiatza = new String(ResourceBundle.getBundle("Etiquetas").getString("May"));
	private String ekaina = new String(ResourceBundle.getBundle("Etiquetas").getString("June"));
	private String uztaila = new String(ResourceBundle.getBundle("Etiquetas").getString("July"));
	private String abuztua = new String(ResourceBundle.getBundle("Etiquetas").getString("August"));
	private String iraila = new String(ResourceBundle.getBundle("Etiquetas").getString("September"));
	private String urria = new String(ResourceBundle.getBundle("Etiquetas").getString("October"));
	private String azaroa = new String(ResourceBundle.getBundle("Etiquetas").getString("November"));
	private String abendua = new String(ResourceBundle.getBundle("Etiquetas").getString("December"));

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

		// Atzera egiteko butoia
		frame = this;
		JButton button = new JButton("<");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				JFrame atzekoa = MainGUI.atzeraEgin();
				frame.setVisible(false);
				atzekoa.setVisible(true);
			}
		});
		button.setBounds(12, 0, 41, 27);
		this.getContentPane().add(button);
		
		JLabel lblRegister = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Register"));
		lblRegister.setFont(new Font("Dialog", Font.BOLD, 16));
		lblRegister.setBounds(12, 30, 136, 15);
		contentPane.add(lblRegister);

		JLabel lblUser = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("User"));
		lblUser.setBounds(35, 57, 73, 15);
		contentPane.add(lblUser);

		JLabel lblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		lblPassword.setBounds(35, 96, 103, 15);
		contentPane.add(lblPassword);

		JLabel lblRepitPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ConfirmPassword"));
		lblRepitPassword.setBounds(34, 130, 146, 15);
		contentPane.add(lblRepitPassword);

		JLabel lblBirthday = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Birthday"));
		lblBirthday.setBounds(35, 163, 96, 15);
		contentPane.add(lblBirthday);

		// regist botoia
		JButton btnRegister = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Register"));
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
					MainGUI.setLoginErabiltzailea(er);
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
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		eguneratuHilabeteak();
	}
	public void eguneratuHilabeteak() {
		hilabetea_model.removeAllElements();
		hilabetea_model.addElement(urtarrila);
		hilabetea_model.addElement(otsaila);
		hilabetea_model.addElement(martxoa);
		hilabetea_model.addElement(apirila);
		hilabetea_model.addElement(maiatza);
		hilabetea_model.addElement(ekaina);
		hilabetea_model.addElement(uztaila);
		hilabetea_model.addElement(abuztua);
		hilabetea_model.addElement(iraila);
		hilabetea_model.addElement(urria);
		hilabetea_model.addElement(azaroa);
		hilabetea_model.addElement(abendua);
	}
}
