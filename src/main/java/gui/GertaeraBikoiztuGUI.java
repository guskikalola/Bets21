package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Question;

public class GertaeraBikoiztuGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private static GertaeraBikoiztuGUI frame;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));
	private final JLabel errorLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("noDuplication")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel successLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("successLabel"));

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;


	private String[] columnNamesEvents = new String[] { ResourceBundle.getBundle("Etiquetas").getString("EventN"),
			ResourceBundle.getBundle("Etiquetas").getString("Event"),

	};
	private String[] columnNamesQueries = new String[] { ResourceBundle.getBundle("Etiquetas").getString("QueryN"),
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};

	private ArrayList<Question> questionList = new ArrayList<Question>();	
	private final JLabel descriptionLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("descriptionLabel")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JTextField descripText = new JTextField();
	private final JLabel dateLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("dateLabel")); //$NON-NLS-1$ //$NON-NLS-2$



	//-------------------------------------------------------------------------------------------------------------------------------------
	//for new date and description

	private DefaultComboBoxModel<Integer> urtea_model;
	private DefaultComboBoxModel<String> hilabetea_model;
	private DefaultComboBoxModel<Integer> eguna_model;


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

	private ArrayList <String> hilabList = new <String> ArrayList();
	




	//-------------------------------------------------------------------------------------------------------------------------------------




	public GertaeraBikoiztuGUI() {
		descripText.setFont(new Font("Tahoma", Font.PLAIN, 13));
		descripText.setText("");
		descripText.setBounds(501, 115, 255, 20);
		descripText.setColumns(10);
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setSize(new Dimension(827, 518));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("GertaeraBikoiztu"));
		getContentPane().setLayout(null);
		jLabelEventDate.setBounds(40, 24, 140, 25);

		this.getContentPane().add(jLabelEventDate);
		jLabelEvents.setBounds(40, 263, 259, 16);
		this.getContentPane().add(jLabelEvents);
		jButtonClose.setBounds(473, 410, 283, 30);



		//--------------------------------------------------------------------------------------------------------------------------
		//Close button

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose);


		//--------------------------------------------------------------------------------------------------------------------------

		//Calendar with event days

		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);
		jCalendar1.setBounds(40, 60, 225, 150);



		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {

				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					//					jCalendar1.setCalendar(calendarAct);
					Date firstDay = UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					int yearAnt = calendarAnt.get(Calendar.YEAR);
					int yearAct = calendarAct.get(Calendar.YEAR); 

					if ((monthAct != monthAnt) || (yearAnt != yearAct)) {
						if (monthAct == monthAnt + 2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2
							// de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt + 1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = MainGUI.getBusinessLogic();

						datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar1.getDate());
					}

					CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade = MainGUI.getBusinessLogic();

						Vector<domain.Event> events = facade.getEvents(firstDay);

						if (events.isEmpty())
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						else
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev : events) {
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events " + ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not
						// shown
						// in
						// JTable
					} catch (Exception e1) {

						;
					}

				}
			}
		});

		this.getContentPane().add(jCalendar1);


		//-----------------------------------------------------------------------------------------------------------------------------------

		//Table Event 


		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableEvents.getSelectedRow();
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(i, 2); // obtain ev object
				Vector<Question> queries = ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);
				tableModelQueries.setColumnCount(3);


				questionList = new ArrayList<Question>();
				for (domain.Question q : queries) {
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					row.add(q);
					tableModelQueries.addRow(row);
					questionList.add(q);
				}
				// JTable
			}
		});
		scrollPaneEvents.setBounds(40, 290, 391, 150);


		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);


		this.getContentPane().add(scrollPaneEvents);

		//--------------------------------------------------------------------------------------------------------------------------------

		//Atzera egin

		frame = this;
		JButton button = new JButton("<");
		button.setBounds(473, 351, 283, 27);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame atzekoa = MainGUI.atzeraEgin();
				frame.setVisible(false);
				atzekoa.setVisible(true);
			}
		});
		getContentPane().add(button);



		//---------------------------------------------------------------------------------------------------------------------------------------------
		//new date comboBox and new description

		urtea_model = new DefaultComboBoxModel<Integer>();
		hilabetea_model = new DefaultComboBoxModel<String>();
		eguna_model = new DefaultComboBoxModel<Integer>();

		// Bete urteak
		for(int i = 2022; i <=2035; i++) {
			urtea_model.addElement(i);
		}
		// Bete hilabeteak

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



		// Bete egunak
		for(int i = 1; i <= 31; i++) {	
			eguna_model.addElement(i);
		}

		descriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		descriptionLabel.setBounds(364, 115, 127, 18);	
		getContentPane().add(descriptionLabel);

		getContentPane().add(descripText);

		dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		dateLabel.setBounds(364, 187, 128, 14);
		getContentPane().add(dateLabel);

		JComboBox dayComboBox = new JComboBox(eguna_model);
		dayComboBox.setBounds(364, 223, 56, 22);
		getContentPane().add(dayComboBox);

		JComboBox monthComboBox = new JComboBox(hilabetea_model);
		monthComboBox.setBounds(473, 223, 127, 22);
		getContentPane().add(monthComboBox);

		JComboBox yearComboBox = new JComboBox(urtea_model);
		yearComboBox.setBounds(644, 223, 112, 22);
		getContentPane().add(yearComboBox);





		//-----------------------------------------------------------------------------------------------------------------------------------------
		
		successLabel.setBounds(473, 264, 283, 14);
		getContentPane().add(successLabel);
		successLabel.setVisible(false);
		errorLabel.setBounds(473, 264, 283, 14);
		getContentPane().add(errorLabel);
		errorLabel.setVisible(false);
		
		
		JButton duplicateButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("duplicate")); //$NON-NLS-1$ //$NON-NLS-2$
		duplicateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String deskribapena = descripText.getText();
				Date data = new Date((Integer) yearComboBox.getSelectedItem() - 1900, monthComboBox.getSelectedIndex(), (Integer) dayComboBox.getSelectedItem());
				int i = tableEvents.getSelectedRow();
				boolean ondo = false;
				if(i == -1) {
					//TODO alternativa

				}else if (i != -1){

					domain.Event  oldEvent = (domain.Event) tableModelEvents.getValueAt(i, 2);		
					BLFacade facade = MainGUI.getBusinessLogic();


					if(oldEvent != null) {
						if(facade.gertaeraBikoiztu(data, deskribapena, oldEvent)) {
							System.out.println(" Ondo exekutatu da");
							eguneratuGertaeraBikoiztuGUI(frame);
							ondo = true;
						}else {
							System.out.println(" Ez da ondo exekutatu da");
							ondo = false;
						}
						
						if(ondo) {
							successLabel.setVisible(true);
						}else{
							errorLabel.setVisible(true);
						}

					}
				}
			}

		});
		duplicateButton.setBounds(473, 289, 283, 30);
		getContentPane().add(duplicateButton);
		
		

		//------------------------------------------------------------------------------------------------------------------------------------------------		



	}

	public static void eguneratuGertaeraBikoiztuGUI(GertaeraBikoiztuGUI frame) {

		GertaeraBikoiztuGUI hurrengoa = new GertaeraBikoiztuGUI();

		frame.setVisible(false);
		hurrengoa.setVisible(true);
	}


	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
} 
