package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Erabiltzailea;
import domain.Pertsona;

public class DiruaSartuGUI extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	private JTextField diruKopInput;
	private JPasswordField pasInput;
	private JLabel lblErrorea ;	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DiruaSartuGUI frame = new DiruaSartuGUI();
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
	public DiruaSartuGUI() {
		frame = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.setBounds(100, 100, 450, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		contentPane.setLayout(null);

		JLabel lbldirukop = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("diru_kop"));// new
																									// JLabel("%diru_kop%");
		lbldirukop.setBounds(62, 105, 97, 17);
		contentPane.add(lbldirukop);

		JLabel lblpasahitza = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));// new
																										// JLabel("%pasahitza%");
		lblpasahitza.setBounds(62, 134, 97, 17);
		contentPane.add(lblpasahitza);

		diruKopInput = new JTextField();
		diruKopInput.setBounds(177, 103, 114, 21);
		contentPane.add(diruKopInput);
		diruKopInput.setColumns(10);

		pasInput = new JPasswordField();
		pasInput.setBounds(177, 132, 114, 21);
		contentPane.add(pasInput);
		pasInput.setColumns(10);

		JLabel lbldiruasartu = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("dirua_sartu"));// new
																											// JLabel("%DIRUA_SARTU%");
		lbldiruasartu.setBounds(28, 43, 131, 17);
		contentPane.add(lbldiruasartu);

		JButton atzeraEgin = new JButton("<");
		atzeraEgin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				JFrame atzekoa = MainGUI.atzeraEgin();
				frame.setVisible(false);
				atzekoa.setVisible(true);
			}
		});
		atzeraEgin.setBounds(12, 0, 41, 27);
		contentPane.add(atzeraEgin);

		JButton btnSartu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("sartu")); //$NON-NLS-1$ //$NON-NLS-2$
		btnSartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblErrorea.setText("");
				BLFacade facade = MainGUI.getBusinessLogic();
				// Aztertu uneko erabiltzailearen pasahitza zuzena den
				Pertsona er = MainGUI.getLoginErabiltzailea();
				String pass = pasInput.getText();
				try {
					Double kantitatea = Double.parseDouble(diruKopInput.getText());		
					if(kantitatea <= 0) {
						lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("errorea_ez_da_zenbakia"));
					} else if(er instanceof Erabiltzailea) {
						boolean em = facade.diruaSartu((Erabiltzailea)er, pass, kantitatea);
						if(!em) {
							lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("errorea_pasahitza_ez_zuzena"));
						}
					} else {
						lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("errorea_ez_da_erabiltzailea"));
					}
				} catch (NumberFormatException  err) {
					lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("errorea_ez_da_zenbakia"));
				}
			}
		});
		btnSartu.setBounds(177, 200, 105, 27);
		contentPane.add(btnSartu);
		
		lblErrorea = new JLabel();
		lblErrorea.setBounds(28, 242, 362, 17);
		contentPane.add(lblErrorea);
	}
}
