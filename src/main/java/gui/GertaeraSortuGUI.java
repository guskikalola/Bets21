package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Event;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class GertaeraSortuGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldDescription;
	private JLabel DataLabel;
	private JLabel DescriptionLabel;
	private JComboBox YearBox;
	private JComboBox MonthBox;
	private JComboBox DayBox;
	private DefaultComboBoxModel<Integer> urtea_model;
	private DefaultComboBoxModel<String> hilabetea_model;
	private DefaultComboBoxModel<Integer> eguna_model;
	private JButton CreateNewButton;
	private JLabel CreateNewEventLabel;
	
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
	
	
	private static GertaeraSortuGUI frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GertaeraSortuGUI();
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
	public GertaeraSortuGUI() {
		
		frame = this;
		
		urtea_model = new DefaultComboBoxModel<Integer>();
		hilabetea_model = new DefaultComboBoxModel<String>();
		eguna_model = new DefaultComboBoxModel<Integer>();
		

		// Bete urteak
		for(int i = 2022; i <=2035; i++) {
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
		
		
		DataLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Date"));
		DataLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		DataLabel.setBounds(45, 74, 45, 13);
		contentPane.add(DataLabel);
		
		DescriptionLabel =new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Description"));
		DescriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		DescriptionLabel.setBounds(45, 118, 86, 13);
		contentPane.add(DescriptionLabel);
		
		YearBox = new JComboBox();
		YearBox.setModel(urtea_model);
		YearBox.setBounds(130, 72, 83, 21);
		contentPane.add(YearBox);
		
		MonthBox = new JComboBox();
		MonthBox.setModel(hilabetea_model);
		MonthBox.setBounds(223, 72, 81, 21);
		contentPane.add(MonthBox);
		
		DayBox = new JComboBox();
		DayBox.setModel(eguna_model);
		DayBox.setBounds(314, 72, 81, 21);
		contentPane.add(DayBox);
		
		textFieldDescription = new JTextField();
		textFieldDescription.setBounds(141, 118, 230, 52);
		contentPane.add(textFieldDescription);
		textFieldDescription.setColumns(10);
		
		CreateNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Create"));
		CreateNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String deskribapena = textFieldDescription.getText();
				Date data = new Date((Integer) YearBox.getSelectedItem() - 1900,
						MonthBox.getSelectedIndex(), (Integer) DayBox.getSelectedItem());
				// TODO: Bete ComboBox-a
				
				BLFacade facade = MainGUI.getBusinessLogic();
				Event event = facade.sortuGertaera(data, deskribapena);
				if(event != null) {
					frame.setVisible(false);
					FindQuestionsGUI quest= new FindQuestionsGUI();
					quest.setVisible(true);
				}else {
					CreateNewEventLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("EventAlready"));
					System.out.println("Errorea: Dagoeneko gertaera hori existitzen da egun horretan");
					// Errorea
				}
			
				
			}
		});
		CreateNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		CreateNewButton.setBounds(163, 197, 97, 29);
		contentPane.add(CreateNewButton);
		
		CreateNewEventLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
		CreateNewEventLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		CreateNewEventLabel.setBounds(22, 32, 357, 13);
		contentPane.add(CreateNewEventLabel);
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
	
	public static void eguneratuGertaeraSortuGUI(JFrame frame) {

		GertaeraSortuGUI hurrengoa = new GertaeraSortuGUI();

		frame.setVisible(false);
		hurrengoa.setVisible(true);
	}
}
