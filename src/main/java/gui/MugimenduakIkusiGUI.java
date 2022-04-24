package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Erabiltzailea;
import domain.Mugimendua;

public class MugimenduakIkusiGUI extends JFrame {

	private JPanel contentPane;
	private static MugimenduakIkusiGUI frame;
	private DefaultTableModel mugimenduakModel;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MugimenduakIkusiGUI frame = new MugimenduakIkusiGUI();
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
	public MugimenduakIkusiGUI() {
		frame = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		BLFacade facade = MainGUI.getBusinessLogic();

		this.listatuMugimenduak();

		JLabel lblMugimenduakikusi = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("mugimenduak_ikusi"));
		lblMugimenduakikusi.setBounds(22, 39, 165, 17);
		contentPane.add(lblMugimenduakikusi);
		
		
		// Atzera egiteko butoia
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
		contentPane.add(button);
		
		table = new JTable(mugimenduakModel);
		table.setColumnSelectionAllowed(true);
		table.setBounds(32, 68, 1, 1);
		contentPane.add(table);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane .setBounds(22, 69, 408, 177);
		contentPane.add(scrollPane);
		
	}
	@Override
	public void setVisible(boolean visible) {
		if(visible) this.listatuMugimenduak();
		super.setVisible(visible);
	}
	
	
	public void listatuMugimenduak() {
		String[] zutabeIzenak = { ResourceBundle.getBundle("Etiquetas").getString("mugimendu_zbkia"),
				ResourceBundle.getBundle("Etiquetas").getString("Amount"),
				ResourceBundle.getBundle("Etiquetas").getString("Description") };

		BLFacade facade = MainGUI.getBusinessLogic();
		if (MainGUI.getLoginErabiltzailea() instanceof Erabiltzailea) {
			Erabiltzailea er = (Erabiltzailea) MainGUI.getLoginErabiltzailea();
			// Bete mugimenduak lista
			
			List<Mugimendua> mugList = facade.mugimenduakIkusi(er);
			
			int mugimenduKopurua = mugList.size();
			String mugimenduak[][] = new String[mugimenduKopurua][3];
	
			int i = 0;
			for(Mugimendua m : mugList) {
				
				int zbkia = m.getMugimenduZbkia();
				double kantitatea = m.getKantitatea();
				
				mugimenduak[i][0] = Integer.toString(zbkia);
				mugimenduak[i][1] = Double.toString(kantitatea);
				mugimenduak[i][2] = ResourceBundle.getBundle("Etiquetas").getString(m.getArrazoia());
				
				i++;
			}
			
			mugimenduakModel = new DefaultTableModel(mugimenduak, zutabeIzenak);


		} else {
			mugimenduakModel = new DefaultTableModel(new String[0][3], zutabeIzenak);
		}
	}
}
