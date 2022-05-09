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
		lblNewLabel.setBounds(71, 13, 158, 14);
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
		btnNewButton.setBounds(22, 59, 194, 27);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("what_do_you_want_to_do"));
		lblNewLabel_1.setBounds(35, 39, 194, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblKaixoErabiltzailea = new JLabel("Kaixo, erabiltzailea");
		lblKaixoErabiltzailea.setBounds(292, 14, 120, 14);
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
		btnDiruaSartu.setBounds(22, 98, 194, 27);
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
		btnMugimenduakikusi.setBounds(22, 137, 194, 27);
		contentPane.add(btnMugimenduakikusi);

		JButton btnApustuaegin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Apustu")); //$NON-NLS-1$ //$NON-NLS-2$
		btnApustuaegin.setBounds(228, 59, 184, 27);
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
		btnApustuaezabatu.setBounds(228, 98, 184, 27);
		contentPane.add(btnApustuaezabatu);
		
		lblSaldoa = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("diru_kop") + ": " + 0); //$NON-NLS-1$ //$NON-NLS-2$
		lblSaldoa.setBounds(22, 252, 194, 17);
		contentPane.add(lblSaldoa);
		
		JButton btnErabiltzaileajarraitu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("erabiltzailea_jarraitu")); //$NON-NLS-1$ //$NON-NLS-2$
		btnErabiltzaileajarraitu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI.eguneratuHistorala(frame);
				frame.setVisible(false);
				JarraituGUI jarraitu = new JarraituGUI();
				jarraitu.setVisible(true);
			}
		});
		btnErabiltzaileajarraitu.setBounds(228, 137, 184, 27);
		contentPane.add(btnErabiltzaileajarraitu);
		
		JButton btnApustuanizkoitza = new JButton(ResourceBundle.getBundle("Etiquetas").getString("apustu_anizkoitza")); //$NON-NLS-1$ //$NON-NLS-2$
		btnApustuanizkoitza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI.eguneratuHistorala(frame);
				frame.setVisible(false);
				ApustuAnizkoitzaGUI apustua = new ApustuAnizkoitzaGUI();
				apustua.setVisible(true);
			}
		});
		btnApustuanizkoitza.setBounds(22, 176, 194, 27);
		contentPane.add(btnApustuanizkoitza);
		
		JButton btnMezuabidali = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Send_Messages")); //$NON-NLS-1$ //$NON-NLS-2$
		btnMezuabidali.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI.eguneratuHistorala(frame);
				frame.setVisible(false);
				MezuakBidaliGUI mezu = new MezuakBidaliGUI();
				mezu.setVisible(true);
			}
		});
		btnMezuabidali.setBounds(228, 176, 184, 27);
		contentPane.add(btnMezuabidali);

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

