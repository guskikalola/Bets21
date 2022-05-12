package gui;

import java.awt.Dimension;
import java.awt.Rectangle;
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
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Erabiltzailea;
import domain.Kuota;
import domain.Question;
import exceptions.EmaitzaEzinIpini;

import javax.swing.ScrollPaneConstants;

public class EmaitzakIpiniGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private static EmaitzakIpiniGUI frame;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
	private final JLabel jLabelKuota = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Kuota"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private JScrollPane scrollPaneKuota = new JScrollPane();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents = new JTable();
	private JTable tableQueries = new JTable();
	private JTable tableKuota = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelKuotak;

	private String[] columnNamesEvents = new String[] { ResourceBundle.getBundle("Etiquetas").getString("EventN"),
			ResourceBundle.getBundle("Etiquetas").getString("Event"),

	};
	private String[] columnNamesQueries = new String[] { ResourceBundle.getBundle("Etiquetas").getString("QueryN"),
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	private String[] columnNamesKuota = new String[] { ResourceBundle.getBundle("Etiquetas").getString("KuotaN"),
			ResourceBundle.getBundle("Etiquetas").getString("Kuota")

	};
	private final JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Put")); //$NON-NLS-1$ //$NON-NLS-2$

	// private double kantitatea;
	private int selectedQ;
	private int selectedRow;
	private ArrayList<Question> questionList = new ArrayList<Question>();
	private ArrayList<Kuota> kuotaList = new ArrayList<Kuota>();
	private Question selectedQuestion;
	private Kuota selectedKuota;
	private final JLabel KuotakLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Kuota")); //$NON-NLS-1$ //$NON-NLS-2$

	private JLabel lblErrorea;

	public EmaitzakIpiniGUI() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(827, 518));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("emaitza_ipini"));

		jLabelEventDate.setBounds(new Rectangle(89, 15, 140, 25));
		jLabelQueries.setBounds(21, 233, 264, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);

		jButtonClose.setBounds(new Rectangle(40, 420, 223, 30));

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);

		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

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
							// Si en JCalendar est� 30 de enero y se avanza al mes siguiente, devolver�a 2
							// de marzo (se toma como equivalente a 30 de febrero)
							// Con este c�digo se dejar� como 1 de febrero en el JCalendar
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

						jLabelQueries.setText(e1.getMessage());
					}

				}
			}
		});

		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(10, 257, 387, 116));
		scrollPaneKuota.setBounds(new Rectangle(437, 257, 366, 96));

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableEvents.getSelectedRow();
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(i, 2); // obtain ev object
				Vector<Question> queries = ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);
				tableModelQueries.setColumnCount(3);
				if (queries.isEmpty())
					jLabelQueries.setText(
							ResourceBundle.getBundle("Etiquetas").getString("NoQueries") + ": " + ev.getDescription());
				else
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent") + " "
							+ ev.getDescription());

				questionList = new ArrayList<Question>();
				for (domain.Question q : queries) {
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					row.add(q);
					tableModelQueries.addRow(row);
					questionList.add(q);
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
				tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(2)); // not shown in
																										// JTable
			}
		});

		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int i = tableQueries.getSelectedRow();
				domain.Question q = (domain.Question) tableModelQueries.getValueAt(i, 2); // obtain qi object
				Vector<Kuota> kuotak = q.getKuotak();

				tableModelKuotak.setDataVector(null, columnNamesKuota);

				if (kuotak.isEmpty())
					jLabelKuota.setText(ResourceBundle.getBundle("Etiquetas").getString("NoKuota"));
				else
					jLabelKuota.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedKuota"));

				kuotaList = new ArrayList<Kuota>();
				for (domain.Kuota k : kuotak) {
					Vector<Object> row = new Vector<Object>();
					row.add(k.getKuotaZenbakia());
					row.add(k.getAukera());
					row.add(k.getKantitatea());
					tableModelKuotak.addRow(row);
					kuotaList.add(k);
				}

			}
		});

		frame = this;

		// Atzera botoia
		JButton button = new JButton("<");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame atzekoa = MainGUI.atzeraEgin();
				frame.setVisible(false);
				atzekoa.setVisible(true);
			}
		});
		button.setBounds(21, 10, 41, 27);
		getContentPane().add(button);

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);

		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);

		scrollPaneKuota.setViewportView(tableKuota);
		tableModelKuotak = new DefaultTableModel(null, columnNamesKuota);

		tableKuota.setModel(tableModelKuotak);
		tableKuota.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableKuota.getColumnModel().getColumn(1).setPreferredWidth(268);

		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		this.getContentPane().add(scrollPaneKuota, null);
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				lblErrorea.setText(" ");
				selectedRow = tableKuota.getSelectedRow();
				selectedQ = tableQueries.getSelectedRow();
				try {

					if (selectedRow != -1 && selectedQ != -1) {
						selectedKuota = kuotaList.get(selectedRow);
						selectedQuestion = questionList.get(selectedQ);
						List<Erabiltzailea> er = facade.emaitzaIpini(selectedQuestion, selectedKuota);
						if (er == null) {
							lblErrorea
									.setText(ResourceBundle.getBundle("Etiquetas").getString("errorea_emaitza_ipini"));
						}else {
							eguneratuEmaitzakIpiniGUI(frame);
						}
					}
				} catch (EmaitzaEzinIpini err) {
					lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("errorea_emaitza_ipini"));
				}

			}
		});
		btnNewButton.setBounds(520, 423, 140, 25);

		getContentPane().add(btnNewButton);
		KuotakLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		KuotakLabel.setBounds(374, 234, 195, 13);

		getContentPane().add(KuotakLabel);

		lblErrorea = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		lblErrorea.setBounds(374, 460, 415, 17);
		getContentPane().add(lblErrorea);

	}

	 public static void eguneratuEmaitzakIpiniGUI(JFrame frame) {
		 EmaitzakIpiniGUI hurrengoa = new EmaitzakIpiniGUI();

			frame.setVisible(false);
			hurrengoa.setVisible(true);
	 }
	
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
