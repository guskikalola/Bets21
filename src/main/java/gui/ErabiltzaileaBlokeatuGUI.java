package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Admin;
import domain.Blokeoa;
import domain.Erabiltzailea;
import domain.Mezua;
import domain.Mugimendua;
import domain.Pertsona;
import exceptions.MezuaEzDaZuzena;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ErabiltzaileaBlokeatuGUI extends JFrame {

	private JPanel contentPane;
	private static ErabiltzaileaBlokeatuGUI frame;
	private DefaultTableModel blokeatuGabeModel;
	private DefaultTableModel blokeatuakModel;
	private String zutabeIzenakBGabe[];
	private String zutabeIzenakB[];
	private JTable tableBGabe;
	private JTable tableB;
	private JScrollPane scrollPaneBGabe;
	private JScrollPane scrollPaneB;
	private JButton blockUsersButton;
	private JLabel blockUsersLabel;
	private JLabel whoBlockLabel;
	private JButton atzeraButton;
	private Erabiltzailea aukeratutakoErabiltzailea;
	private Pertsona aukeratutakoPertsona;
	private JLabel ReasonLabel;
	private JTextField textField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MezuakBidaliGUI frame = new MezuakBidaliGUI();
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
	public ErabiltzaileaBlokeatuGUI() {
		frame = this;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		zutabeIzenakBGabe = new String[2];
		zutabeIzenakBGabe[0] = ResourceBundle.getBundle("Etiquetas").getString("Non_blocked_users");
		
		zutabeIzenakB = new String[2];
		zutabeIzenakB[0] = ResourceBundle.getBundle("Etiquetas").getString("Blocked_users");

		blokeatuakModel = new DefaultTableModel(null, zutabeIzenakB);
		blokeatuGabeModel = new DefaultTableModel(null, zutabeIzenakBGabe);
		
		// Atzera egiteko butoia
		atzeraButton = new JButton("<");
		atzeraButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame atzekoa = MainGUI.atzeraEgin();
				frame.setVisible(false);
				atzekoa.setVisible(true);
			}
		});
		
		atzeraButton.setBounds(10, 39, 68, 27);
		contentPane.add(atzeraButton);
		
		blockUsersLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Block_user"));
		blockUsersLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		blockUsersLabel.setBounds(10, 10, 258, 19);
		contentPane.add(blockUsersLabel);
		
		whoBlockLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Who_do_you_want_to_block"));		
		whoBlockLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		whoBlockLabel.setBounds(131, 44, 180, 15);
		contentPane.add(whoBlockLabel);
		
		scrollPaneB = new JScrollPane(tableB);
		scrollPaneB.setBounds(233, 76, 193, 118);
		contentPane.add(scrollPaneB);
		
		scrollPaneBGabe = new JScrollPane(tableBGabe);
		scrollPaneBGabe.setBounds(10, 76, 180, 118);
		contentPane.add(scrollPaneBGabe);
		
		tableBGabe = new JTable();
		scrollPaneBGabe.setViewportView(tableBGabe);
		tableBGabe.setModel(blokeatuGabeModel);
		
		tableB = new JTable();
		scrollPaneB.setViewportView(tableB);
		tableB.setModel(blokeatuakModel);
		
		tableBGabe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				blockUsersButton.setText(ResourceBundle.getBundle("Etiquetas").getString("Block_user"));
				whoBlockLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("Who_do_you_want_to_block"));
				textField.setVisible(true);
				textField.setText("");
				ReasonLabel.setVisible(true);
				tableB.clearSelection();
			}
		});
		
		tableB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {	
				blockUsersButton.setText(ResourceBundle.getBundle("Etiquetas").getString("Unlock_user"));
				whoBlockLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("Who_do_you_want_to_unlock"));
				textField.setVisible(false);
				textField.setText("");
				ReasonLabel.setVisible(false);
				tableBGabe.clearSelection();
			}
		});
		
	
		blockUsersButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Block_user"));
		blockUsersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				Admin adminBlokeo= null;
				if(MainGUI.getLoginErabiltzailea() instanceof Admin) {
					adminBlokeo= (Admin) MainGUI.getLoginErabiltzailea();
				}
				int i = tableBGabe.getSelectedRow();
				try {
					String arrazoia= textField.getText();
					String izena= (String) tableBGabe.getModel().getValueAt(i, 0);
					Erabiltzailea erab= facade.getErabiltzailea(izena);
					blockUsersButton.setText(ResourceBundle.getBundle("Etiquetas").getString("Block_user"));
					whoBlockLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("Who_do_you_want_to_block"));		
					Blokeoa bl= facade.erabiltzaileaBlokeatu(adminBlokeo, erab, arrazoia);
					if(bl!=null) {
						Vector<Object> row = new Vector<Object>();
						String izenaBerri = erab.getIzena();
						row.add(izenaBerri);
						blokeatuakModel.addRow(row);
						blokeatuGabeModel.removeRow(i);
					}
					
				} catch (MezuaEzDaZuzena err) {
					String testua = err.getMessage();
					if(testua.equals("Short_reason")) {
						ReasonLabel.setText(ResourceBundle.getBundle("Etiquetas").getString(testua) + " (min: " + Mezua.MEZUAMIN + ")" );
					} else {
						ReasonLabel.setText(ResourceBundle.getBundle("Etiquetas").getString(testua) + " (max: " + Mezua.MEZUAMAX + ")" );
					}
				} catch (java.lang.ArrayIndexOutOfBoundsException err) {
					// Ez da ezer aukeratu, ez egiin ezer
				}
				int j= tableB.getSelectedRow();
				try {
					String izena= (String) tableB.getModel().getValueAt(j, 0);
					Erabiltzailea erab= facade.getErabiltzailea(izena);
					blockUsersButton.setText(ResourceBundle.getBundle("Etiquetas").getString("Unlock_user"));
					whoBlockLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("Who_do_you_want_to_unlock"));				
					Blokeoa bl= facade.erabiltzaileaBlokeatu(adminBlokeo, erab, null);
					if(bl!=null) {
						Vector<Object> row = new Vector<Object>();
						String izenaBerri = erab.getIzena();
						row.add(izenaBerri);
						blokeatuGabeModel.addRow(row);
						blokeatuakModel.removeRow(j);
					}
					
				} catch (MezuaEzDaZuzena err) {
					String testua = err.getMessage();
					if(testua.equals("Short_reason")) {
						ReasonLabel.setText(ResourceBundle.getBundle("Etiquetas").getString(testua) + " (min: " + Mezua.MEZUAMIN + ")" );
					} else {
						ReasonLabel.setText(ResourceBundle.getBundle("Etiquetas").getString(testua) + " (max: " + Mezua.MEZUAMAX + ")" );
					}
				}catch (java.lang.ArrayIndexOutOfBoundsException err) {
					// Ez da ezer aukeratu, ez egiin ezer
				}
			}
		});
		blockUsersButton.setBounds(233, 220, 193, 33);
		contentPane.add(blockUsersButton);
		
		ReasonLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Reason")); 
		ReasonLabel.setBounds(20, 204, 248, 13);
		contentPane.add(ReasonLabel);
		
		textField = new JTextField();
		textField.setBounds(10, 220, 193, 19);
		contentPane.add(textField);
		textField.setColumns(10);
	}
	
	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			this.listatuErabiltzaileak();
		}
		super.setVisible(visible);
	}
		
	private void listatuErabiltzaileak() {
		
		BLFacade facade = MainGUI.getBusinessLogic();

		blokeatuakModel.setDataVector(null, zutabeIzenakB);
		blokeatuakModel.setColumnCount(1);
		
		blokeatuGabeModel.setDataVector(null, zutabeIzenakBGabe);
		blokeatuGabeModel.setColumnCount(1);

	    List<Erabiltzailea> erabiltzaileList = facade.getErabiltzaileaGuztiak();
		for(Erabiltzailea er : erabiltzaileList) {
				if(er.getBlokeoa()==null) {
					Vector<Object> row = new Vector<Object>();
					String izena = er.getIzena();
					row.add(izena);
					blokeatuGabeModel.addRow(row);
				}else {
					Vector<Object> row = new Vector<Object>();
					String izena = er.getIzena();
					row.add(izena);
					blokeatuakModel.addRow(row);
				}		    	
		}
		tableBGabe.getColumnModel().getColumn(0).setPreferredWidth(268);
		tableB.getColumnModel().getColumn(0).setPreferredWidth(268);
	}
}
		
		

