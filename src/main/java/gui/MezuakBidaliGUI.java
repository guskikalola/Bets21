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
import domain.Erabiltzailea;
import domain.Mugimendua;
import domain.Pertsona;

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

public class MezuakBidaliGUI extends JFrame {

	private JPanel contentPane;
	private static MezuakBidaliGUI frame;
	private static String izena=null;
	private DefaultTableModel administratzaileakModel;
	private DefaultTableModel erabiltzaileakModel;
	private String zutabeIzenakE[];
	private String zutabeIzenakA[];
	private JTable tableE;
	private JTable tableA;
	private JScrollPane scrollPaneErab;
	private JScrollPane scrollPaneAdm;
	private JButton mezuaBidaliButton;
	private JLabel MezuakBidaliLabel;
	private JLabel NoriBidaliLabel;
	private JButton atzeraButton;
	private Erabiltzailea aukeratutakoErabiltzailea;
	private Admin aukeratutakoAdmin;
	private Pertsona aukeratutakoPertsona;
	
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
	public MezuakBidaliGUI() {
		frame = this;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		zutabeIzenakE = new String[2];
		zutabeIzenakE[0] = ResourceBundle.getBundle("Etiquetas").getString("User");
		
		zutabeIzenakA = new String[2];
		zutabeIzenakA[0] = ResourceBundle.getBundle("Etiquetas").getString("admin");

		erabiltzaileakModel = new DefaultTableModel(null, zutabeIzenakE);
		administratzaileakModel = new DefaultTableModel(null, zutabeIzenakA);
		
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
		
		MezuakBidaliLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Send_Messages"));
		MezuakBidaliLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		MezuakBidaliLabel.setBounds(10, 10, 136, 19);
		contentPane.add(MezuakBidaliLabel);
		
		NoriBidaliLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Who_send"));		
		NoriBidaliLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		NoriBidaliLabel.setBounds(131, 70, 180, 15);
		contentPane.add(NoriBidaliLabel);
		
		scrollPaneErab = new JScrollPane(tableE);
		scrollPaneErab.setBounds(233, 95, 193, 118);
		contentPane.add(scrollPaneErab);
		
		scrollPaneAdm = new JScrollPane(tableA);
		scrollPaneAdm.setBounds(10, 95, 180, 118);
		contentPane.add(scrollPaneAdm);
		
		tableE = new JTable();
		scrollPaneErab.setViewportView(tableE);
		tableE.setModel(erabiltzaileakModel);
		
		tableA = new JTable();
		scrollPaneAdm.setViewportView(tableA);
		tableA.setModel(administratzaileakModel);
		
		tableA.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableE.clearSelection();
			}
		});
		
		tableE.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableA.clearSelection();
			}
		});
		
	
		mezuaBidaliButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Send_Messages"));
		mezuaBidaliButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				int i = tableE.getSelectedRow();
				try {
					if(izena==null) {
						izena= (String) tableE.getModel().getValueAt(i, 0);
						aukeratutakoPertsona= facade.getPertsona(izena);
						MainGUI.eguneratuHistorala(frame);
						frame.setVisible(false);
						ChatGUI chat = new ChatGUI(aukeratutakoPertsona);
						chat.setVisible(true);
					}		
				} catch (java.lang.ArrayIndexOutOfBoundsException err) {
					// Ez da ezer aukeratu, ez egiin ezer
				}
				int j= tableA.getSelectedRow();
				try {
					if(izena==null) {
						String izena= (String) tableA.getModel().getValueAt(j, 0);
						aukeratutakoPertsona= facade.getPertsona(izena);
						MainGUI.eguneratuHistorala(frame);
						frame.setVisible(false);
						ChatGUI chat = new ChatGUI(aukeratutakoPertsona);
						chat.setVisible(true);
					}	
				} catch (java.lang.ArrayIndexOutOfBoundsException err) {
					// Ez da ezer aukeratu, ez egiin ezer
				}
				izena=null;
			}
		});
		mezuaBidaliButton.setBounds(289, 220, 112, 33);
		contentPane.add(mezuaBidaliButton);
	}
	
	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			this.listatuErabiltzaileak();
			this.listatuAdministratzaileak();
		}
		super.setVisible(visible);
	}
		
	private void listatuErabiltzaileak() {
		
		Erabiltzailea erab = null;
		BLFacade facade = MainGUI.getBusinessLogic();
		if(MainGUI.getLoginErabiltzailea() instanceof Erabiltzailea) {
			erab= (Erabiltzailea) MainGUI.getLoginErabiltzailea();
		}

		erabiltzaileakModel.setDataVector(null, zutabeIzenakE);
		erabiltzaileakModel.setColumnCount(1);

	    List<Pertsona> pertsonaList = facade.getPertsonaGuztiak();
		for(Pertsona p : pertsonaList) {
			if(p instanceof Erabiltzailea) {
				if(erab!=null) {
					if(!erab.getIzena().equals(p.getIzena())) {
						Vector<Object> row = new Vector<Object>();
						String izena = p.getIzena();
						row.add(izena);
						erabiltzaileakModel.addRow(row);
					}
				}else {
					Vector<Object> row = new Vector<Object>();
					String izena = p.getIzena();
					row.add(izena);
					erabiltzaileakModel.addRow(row);
				}		    
			}	
		}
		tableE.getColumnModel().getColumn(0).setPreferredWidth(268);
	}
	private void listatuAdministratzaileak() {
		
		Admin admin= null;
		BLFacade facade = MainGUI.getBusinessLogic();
		if(MainGUI.getLoginErabiltzailea() instanceof Admin) {
			admin= (Admin) MainGUI.getLoginErabiltzailea();
		}

		administratzaileakModel.setDataVector(null, zutabeIzenakA);
		administratzaileakModel.setColumnCount(1);

	    List<Pertsona> pertsonaList = facade.getPertsonaGuztiak();
		for(Pertsona p : pertsonaList) {
			if(p instanceof Admin) {
				if(admin!=null) {
					if(!admin.getIzena().equals(p.getIzena())) {
						Vector<Object> row = new Vector<Object>();
						String izena = p.getIzena();
						row.add(izena);
						administratzaileakModel.addRow(row);
					}
				}else {
					Vector<Object> row = new Vector<Object>();
					String izena = p.getIzena();
					row.add(izena);
					administratzaileakModel.addRow(row);
				}
			    
			}	
		}
		tableA.getColumnModel().getColumn(0).setPreferredWidth(268);
	}
}
		
		

