package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Admin;
import domain.Mezua;
import domain.Pertsona;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

public class ChatGUI extends JFrame {

	private JPanel contentPane;
	private static ChatGUI frame;
	private DefaultTableModel mezuakModel;
	private static Pertsona aukeratutakoPertsona;
	private String zutabeIzenakMezua[];
	private JScrollPane scrollPaneMezu;
	private JTable tableMezua;
	private JButton mezuaBidaliButton;
	private JButton atzeraButton;
	private JTextField mezuTextField;
	private JLabel ChatLabel;
	private JLabel sartuTestuaLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatGUI frame = new ChatGUI(aukeratutakoPertsona);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param aukeratutakoErabiltzailea 
	 */
	public ChatGUI(Pertsona aukeratutakoPertsona) {
		
		frame= this;
		ChatGUI.aukeratutakoPertsona= aukeratutakoPertsona;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		System.out.println(aukeratutakoPertsona.getIzena());
		
		zutabeIzenakMezua = new String[4];
		zutabeIzenakMezua[0] = ResourceBundle.getBundle("Etiquetas").getString("Direction");
		zutabeIzenakMezua[1] = ResourceBundle.getBundle("Etiquetas").getString("Number");
		zutabeIzenakMezua[2] = ResourceBundle.getBundle("Etiquetas").getString("Message");	
		zutabeIzenakMezua[3] = ResourceBundle.getBundle("Etiquetas").getString("Date");
		
		mezuakModel = new DefaultTableModel(null, zutabeIzenakMezua);
		
		scrollPaneMezu = new JScrollPane(tableMezua);
		scrollPaneMezu.setBounds(103, 38, 219, 135);
		contentPane.add(scrollPaneMezu);
		
		tableMezua = new JTable();
		scrollPaneMezu.setViewportView(tableMezua);
		tableMezua.setModel(mezuakModel);
		
		// Atzera egiteko butoia
		atzeraButton = new JButton("<");
		atzeraButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame atzekoa = MainGUI.atzeraEgin();
				frame.setVisible(false);
				atzekoa.setVisible(true);
			}
		});
		atzeraButton.setBounds(10, 38, 68, 27);
		contentPane.add(atzeraButton);
		
		mezuaBidaliButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Send_Messages"));
		mezuaBidaliButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				Pertsona m= MainGUI.getLoginErabiltzailea();
				Pertsona nori= ChatGUI.aukeratutakoPertsona;
				String mezua= sartuTestuaLabel.getText();
				Mezua mezu= facade.mezuaBidali(m, nori, mezua);
				if(mezu==null) {
					System.out.println("Mezua ez da sortu, mezua zuzena ez delako"+ mezua);
				}else {
					System.out.println("Mezua zuzen sortu da" + mezu.getMezua());
				}
			}
		});
		mezuaBidaliButton.setBounds(302, 220, 112, 33);
		contentPane.add(mezuaBidaliButton);
		
		ChatLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Chat")); 
		ChatLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ChatLabel.setBounds(10, 10, 112, 18);
		contentPane.add(ChatLabel);
		
		sartuTestuaLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("writeMessage")); 
		sartuTestuaLabel.setBounds(10, 182, 95, 13);
		contentPane.add(sartuTestuaLabel);
		
		mezuTextField = new JTextField();
		mezuTextField.setBounds(9, 205, 260, 19);
		contentPane.add(mezuTextField);
		mezuTextField.setColumns(10);
	}
	
	
	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			this.listatuMezuak();
		}
		super.setVisible(visible);
	}
	
	
    private void listatuMezuak() {
		
		BLFacade facade = MainGUI.getBusinessLogic();
		Pertsona m= MainGUI.getLoginErabiltzailea();
		String bidalketa= ">>";
		String jaso= "<<";

		mezuakModel.setDataVector(null, zutabeIzenakMezua);
		mezuakModel.setColumnCount(4);

	    List<Mezua> mezuList = facade.getMezuGuztiak(m, ChatGUI.aukeratutakoPertsona);
		for(Mezua mez : mezuList) {
				Vector<Object> row = new Vector<Object>();
				String bidalitakoMezua = mez.getMezua();
				Integer zenbaki= mez.getMezuaZenbakia();
				Date data= mez.getData();	
				if(mez.getNor().getIzena().equals(m.getIzena())) {
					row.add(bidalketa);
				}else {
					row.add(jaso);
				}
				row.add(zenbaki);
				row.add(bidalitakoMezua);
				row.add(data);
				mezuakModel.addRow(row);  
		}	
		tableMezua.getColumnModel().getColumn(0).setPreferredWidth(268);
		tableMezua.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableMezua.getColumnModel().getColumn(2).setPreferredWidth(268);
		tableMezua.getColumnModel().getColumn(3).setPreferredWidth(268);
	}
}
