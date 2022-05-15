package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import businessLogic.BLFacade;
import domain.Admin;
import domain.Mezua;
import domain.MezuaContainer;
import domain.Pertsona;
import exceptions.MezuaEzDaZuzena;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
	private JLabel izenakLabel;

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
		scrollPaneMezu.setBounds(96, 38, 318, 156);
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
				String mezua= mezuTextField.getText();
				try {
					Mezua mezu= facade.mezuaBidali(m, nori, mezua);
					Vector<Object> row = new Vector<Object>();
					String bidalitakoMezua = mezu.getMezua();
					Integer zenbaki= mezu.getMezuaZenbakia();
					Date data= mezu.getData();	
					row.add(">>");
					row.add(zenbaki);
					row.add(bidalitakoMezua);
					row.add(data);
					mezuakModel.addRow(row);
					sartuTestuaLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("writeMessage")); 
				}catch (MezuaEzDaZuzena err) {
					String testua = err.getMessage();
					if(testua.equals("Short_message")) {
						sartuTestuaLabel.setText(ResourceBundle.getBundle("Etiquetas").getString(testua) + " (min: " + Mezua.MEZUAMIN + ")" );
					} else {
						sartuTestuaLabel.setText(ResourceBundle.getBundle("Etiquetas").getString(testua) + " (max: " + Mezua.MEZUAMAX + ")" );
					}
				} catch (NullPointerException err)
				{
					// Ez egin ezer. Hau emango da erabiltzailea blokeatuta dagoenean
				}
				
			}
		});
		mezuaBidaliButton.setBounds(280, 220, 134, 33);
		contentPane.add(mezuaBidaliButton);
		
		ChatLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Chat")); 
		ChatLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ChatLabel.setBounds(10, 10, 112, 18);
		contentPane.add(ChatLabel);
		
		sartuTestuaLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("writeMessage")); 
		sartuTestuaLabel.setBounds(10, 204, 260, 13);
		contentPane.add(sartuTestuaLabel);
		
		mezuTextField = new JTextField();
		mezuTextField.setBounds(10, 227, 260, 19);
		contentPane.add(mezuTextField);
		mezuTextField.setColumns(10);
		
		Pertsona m= MainGUI.getLoginErabiltzailea();
		
		izenakLabel = new JLabel(m.getIzena() + " - " + ChatGUI.aukeratutakoPertsona.getIzena());
		izenakLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		izenakLabel.setHorizontalAlignment(SwingConstants.CENTER);
		izenakLabel.setBounds(123, 10, 267, 18);
		contentPane.add(izenakLabel);
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

	    List<MezuaContainer> mezuList = facade.getMezuGuztiakContainer(m, ChatGUI.aukeratutakoPertsona);
	    Collections.sort(mezuList);
		for(MezuaContainer mez : mezuList) {
				Vector<Object> row = new Vector<Object>();
				Mezua mezuDB= mez.getM();
				String bidalitakoMezua = mezuDB.getMezua();
				Integer zenbaki= mezuDB.getMezuaZenbakia();
				Date data= mezuDB.getData();	
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
		
		tableMezua.getColumnModel().getColumn(2).setCellRenderer(new WordWrapCellRenderer());
		tableMezua.getColumnModel().getColumn(3).setCellRenderer(new WordWrapCellRenderer());
	}
    
    static class WordWrapCellRenderer extends JTextArea implements TableCellRenderer {
        WordWrapCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            if (table.getRowHeight(row) != getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height);
            }
            return this;
        }
    }
}
