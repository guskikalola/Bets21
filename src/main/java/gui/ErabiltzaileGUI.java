package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Erabiltzailea;
import domain.Pertsona;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class ErabiltzaileGUI extends JFrame {

	private JPanel contentPane;
	private static ErabiltzaileGUI frame;
	private Erabiltzailea erabiltzailea;
	private JLabel lblSaldoa;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new ErabiltzaileGUI(new Erabiltzailea("a", "b", new Date()));
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

		frame = this;
		this.erabiltzailea = erabiltzailea;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("User"));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(35, 37, 158, 14);
		contentPane.add(lblNewLabel);

		BLFacade facade = MainGUI.getBusinessLogic();
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI.eguneratuHistorala(frame);
				frame.setVisible(false);
				FindQuestionsGUI f = new FindQuestionsGUI();
				f.setVisible(true);
			}
		});
		btnNewButton.setBounds(22, 96, 194, 36);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("what_do_you_want_to_do"));
		lblNewLabel_1.setBounds(22, 70, 194, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblKaixoErabiltzailea = new JLabel("Kaixo, erabiltzailea");
		lblKaixoErabiltzailea.setBounds(292, 38, 120, 14);
		contentPane.add(lblKaixoErabiltzailea);

		lblKaixoErabiltzailea.setText(
				ResourceBundle.getBundle("Etiquetas").getString("kaixo") + ", " + this.erabiltzailea.getIzena());

		// Atzera egiteko butoia
		JButton button = new JButton("<");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame atzekoa = MainGUI.atzeraEgin();
				frame.setVisible(false);
				atzekoa.setVisible(true);
			}
		});
		button.setBounds(12, 0, 41, 27);
		contentPane.add(button);

		JButton btnDiruaSartu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("dirua_sartu"));
		btnDiruaSartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI.eguneratuHistorala(frame);
				frame.setVisible(false);
				DiruaSartuGUI f = new DiruaSartuGUI();
				f.setVisible(true);
			}
		});
		btnDiruaSartu.setBounds(22, 144, 194, 36);
		contentPane.add(btnDiruaSartu);

		JButton btnMugimenduakikusi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("mugimenduak_ikusi"));
		btnMugimenduakikusi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI.eguneratuHistorala(frame);
				frame.setVisible(false);
				MugimenduakIkusiGUI f = new MugimenduakIkusiGUI();
				f.setVisible(true);
			}
		});
		btnMugimenduakikusi.setBounds(22, 192, 194, 36);
		contentPane.add(btnMugimenduakikusi);

		JButton btnApustuaegin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Apustu")); //$NON-NLS-1$ //$NON-NLS-2$
		btnApustuaegin.setBounds(228, 96, 184, 36);
		contentPane.add(btnApustuaegin);
		btnApustuaegin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI.eguneratuHistorala(frame);
				frame.setVisible(false);
				ApustuakEginGUI apustua = new ApustuakEginGUI();
				apustua.setVisible(true);

			}
		});

		JButton btnApustuaezabatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EzabatuApustua")); //$NON-NLS-1$ //$NON-NLS-2$
		btnApustuaezabatu.setBounds(228, 144, 184, 36);
		contentPane.add(btnApustuaezabatu);
		
		lblSaldoa = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("diru_kop") + ": " + 0); //$NON-NLS-1$ //$NON-NLS-2$
		lblSaldoa.setBounds(22, 240, 194, 17);
		contentPane.add(lblSaldoa);
		btnApustuaezabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI.eguneratuHistorala(frame);
				frame.setVisible(false);
				ApustuakEzabatuGUI apustu = new ApustuakEzabatuGUI();
				apustu.setVisible(true);
			}
		});
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		BLFacade facade = MainGUI.getBusinessLogic();
		Pertsona er = MainGUI.getLoginErabiltzailea();
		System.out.println(((Erabiltzailea)er).getSaldoa());
		if(visible && er instanceof Erabiltzailea) this.lblSaldoa.setText(ResourceBundle.getBundle("Etiquetas").getString("diru_kop") + ": " + ((Erabiltzailea)er).getSaldoa());
	}
}	

