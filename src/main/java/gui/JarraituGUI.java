package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Erabiltzailea;
import domain.Pertsona;
import domain.Jarraitzen;
import domain.JarraitzenContainer;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import java.awt.Font;

public class JarraituGUI extends JFrame {

	private JPanel contentPane;
	private JarraituGUI frame;
	private JTable tableJarraitzen;
	private JTable tableEzJarraitzen;
	private JScrollPane jarraitzenPane;
	private JScrollPane ezJarraitzenPane;
	private JButton button;
	private JButton bGehituJarraitu;
	private JButton bEzabatuJarraitu;
	private JLabel lblEzJarraitzen;
	private JLabel lblJarraitzen;
	private DefaultTableModel jarraitzenModel;
	private DefaultTableModel ezJarraitzenModel;
	private String[] zutabeIzenak;

	private Erabiltzailea aukeratutakoErabiltzailea;
	private JLabel lblErrorea;
	private JLabel lblConditions;
	private JLabel lblDiruMax;
	private JTextField txtFieldDirua;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JarraituGUI frame = new JarraituGUI();
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
	public JarraituGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		BLFacade facade = MainGUI.getBusinessLogic();
		Pertsona unekoErab = MainGUI.getLoginErabiltzailea();

		// Atzera egiteko butoia
		frame = this;
		button = new JButton("<");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				JFrame atzekoa = MainGUI.atzeraEgin();
				frame.setVisible(false);
				atzekoa.setVisible(true);
			}
		});
		button.setBounds(12, 0, 41, 27);
		this.getContentPane().add(button);

		zutabeIzenak = new String[2];
		zutabeIzenak[0] = ResourceBundle.getBundle("Etiquetas").getString("User");
		zutabeIzenak[1] = ResourceBundle.getBundle("Etiquetas").getString("apustua_irabazi");

		jarraitzenModel = new DefaultTableModel(null, zutabeIzenak);
		ezJarraitzenModel = new DefaultTableModel(null, zutabeIzenak);

		jarraitzenPane = new JScrollPane();

		jarraitzenPane.setBounds(52, 56, 134, 168);
		contentPane.add(jarraitzenPane);

		tableJarraitzen = new JTable();
		jarraitzenPane.setViewportView(tableJarraitzen);
		tableJarraitzen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableEzJarraitzen.clearSelection();
			}
		});
		tableJarraitzen.setModel(jarraitzenModel);

		ezJarraitzenPane = new JScrollPane();
		ezJarraitzenPane.setBounds(261, 56, 134, 168);
		contentPane.add(ezJarraitzenPane);

		tableEzJarraitzen = new JTable();
		ezJarraitzenPane.setViewportView(tableEzJarraitzen);
		tableEzJarraitzen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableJarraitzen.clearSelection();
			}
		});
		tableEzJarraitzen.setModel(ezJarraitzenModel);

		bGehituJarraitu = new JButton("<");
		bGehituJarraitu.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				lblErrorea.setText("");
				int i = tableEzJarraitzen.getSelectedRow();
				try {
					aukeratutakoErabiltzailea = (Erabiltzailea) tableEzJarraitzen.getModel().getValueAt(i, 2);
					String diruMaxTxt = txtFieldDirua.getText();
					float diruMax = 0;

					System.out.println(diruMaxTxt);
					if (diruMaxTxt != null && diruMaxTxt.length() > 0)
						diruMax = Float.parseFloat(diruMaxTxt);
					
					if (!(unekoErab instanceof Erabiltzailea)) {
						// Ez da erabiltzailea
					} else if (diruMax < 0) {
						// Balioa ez da zuzena
						lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorNumber"));
					} else if (aukeratutakoErabiltzailea == null) {
						// Aukeratu erabiltzaile bat
					} else if (unekoErab == null) {
						// Ez zaude logeatua

					}
					else if(unekoErab instanceof Erabiltzailea && ((Erabiltzailea)unekoErab).getBlokeoa() != null) {
						// blokeatuta zaude

					} else {
						boolean em = facade.erabiltzaileaJarraitu((Erabiltzailea) unekoErab, aukeratutakoErabiltzailea, diruMax);
						// Ezabatu ez jarraitzen taulatik errrenkada eta gehitu jarraitzen taulara
						if (em) {
							jarraitzenModel.addRow(ezJarraitzenModel.getDataVector().elementAt(i));
							ezJarraitzenModel.removeRow(i);
						}
					}
				} catch (NumberFormatException err) {
					// Sartutako balioa ez da zenbaki osoa
					lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("errorea_ez_da_zenbakia"));

				} catch (java.lang.ArrayIndexOutOfBoundsException err) {
					// ez da ezer aukeratu, ez egin ezer
				}
			}
		});
		bGehituJarraitu.setBounds(198, 69, 51, 27);
		contentPane.add(bGehituJarraitu);

		bEzabatuJarraitu = new JButton(">");
		bEzabatuJarraitu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblErrorea.setText("");
				int i = tableJarraitzen.getSelectedRow();
				try {

					aukeratutakoErabiltzailea = (Erabiltzailea) tableJarraitzen.getModel().getValueAt(i, 2);

					// Ezabatu jarraitzen taulatik errrenkada eta gehitu ez jarraitzen taulara
					boolean em = facade.erabiltzaileaJarraitu((Erabiltzailea) unekoErab, aukeratutakoErabiltzailea, 0);
					if (em) {
						ezJarraitzenModel.addRow(jarraitzenModel.getDataVector().elementAt(i));
						jarraitzenModel.removeRow(i);
					}
				} catch (java.lang.ArrayIndexOutOfBoundsException err) {
					// Ez da ezer aukeratu, ez egiin ezer
				}

			}
		});
		bEzabatuJarraitu.setBounds(198, 178, 51, 27);
		contentPane.add(bEzabatuJarraitu);

		lblJarraitzen = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("jarraitzen"));
		lblJarraitzen.setHorizontalAlignment(SwingConstants.CENTER);
		lblJarraitzen.setBounds(52, 27, 134, 17);
		contentPane.add(lblJarraitzen);

		lblEzJarraitzen = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ez_jarraitzen"));
		lblEzJarraitzen.setHorizontalAlignment(SwingConstants.CENTER);
		lblEzJarraitzen.setBounds(261, 27, 134, 17);
		contentPane.add(lblEzJarraitzen);
		
		lblConditions = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("baldintzak")); //$NON-NLS-1$ //$NON-NLS-2$
		lblConditions.setHorizontalAlignment(SwingConstants.CENTER);
		lblConditions.setBounds(12, 236, 418, 17);
		contentPane.add(lblConditions);

		lblDiruMax = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("diru_kop_baldintza")); //$NON-NLS-1$ //$NON-NLS-2$
		lblDiruMax.setBounds(12, 265, 307, 17);
		contentPane.add(lblDiruMax);

		txtFieldDirua = new JTextField();
		txtFieldDirua.setBounds(323, 265, 72, 21);
		contentPane.add(txtFieldDirua);
		txtFieldDirua.setColumns(10);

		lblErrorea = new JLabel(); // $NON-NLS-1$ //$NON-NLS-2$
		lblErrorea.setFont(new Font("Dialog", Font.BOLD, 12));
		lblErrorea.setHorizontalAlignment(SwingConstants.LEFT);
		lblErrorea.setBounds(12, 353, 418, 17);
		contentPane.add(lblErrorea);
	}

	@Override
	public void setVisible(boolean visible) {
		if (visible)
			this.listatuErabiltzaileak();
		super.setVisible(visible);
	}

	private void listatuErabiltzaileak() {

		BLFacade facade = MainGUI.getBusinessLogic();

		ezJarraitzenModel.setDataVector(null, zutabeIzenak);
		ezJarraitzenModel.setColumnCount(3);

		jarraitzenModel.setDataVector(null, zutabeIzenak);
		jarraitzenModel.setColumnCount(3);

		if (MainGUI.getLoginErabiltzailea() instanceof Erabiltzailea) {
			Erabiltzailea er = (Erabiltzailea) MainGUI.getLoginErabiltzailea();
			List<JarraitzenContainer> jarraitzenLista = facade.getJarraitzen(er);
			List<Erabiltzailea> erabiltzaileakLista = facade.getErabiltzaileaGuztiak();
			// Tratatu jarraitzen ditugun erabiltzaileak
			for (JarraitzenContainer jC : jarraitzenLista) {
				Erabiltzailea nori = jC.getNori();
				Vector<Object> row = new Vector<Object>();
				row.add(nori.getIzena());
				row.add(facade.getApustuakIrabazitak(nori));
				row.add(nori);
				jarraitzenModel.addRow(row);
			}

			tableJarraitzen.getColumnModel().getColumn(0).setPreferredWidth(268);
			tableJarraitzen.getColumnModel().getColumn(1).setPreferredWidth(268);
			tableJarraitzen.getColumnModel().removeColumn(tableJarraitzen.getColumnModel().getColumn(2));

			// Tratatu jarraitzen ez ditugun erabiltzaileak
			for (Erabiltzailea nori : erabiltzaileakLista) {
				if (facade.jarraitzenDu(er, nori) == null && !nori.getIzena().equals(er.getIzena())) {  // ez badu jarraitzen
					Vector<Object> row = new Vector<Object>();
					row.add(nori.getIzena());
					row.add(facade.getApustuakIrabazitak(nori));
					row.add(nori);
					ezJarraitzenModel.addRow(row);
				}
			}

			tableEzJarraitzen.getColumnModel().getColumn(0).setPreferredWidth(268);
			tableEzJarraitzen.getColumnModel().getColumn(1).setPreferredWidth(268);
			tableEzJarraitzen.getColumnModel().removeColumn(tableEzJarraitzen.getColumnModel().getColumn(2));

		}
	}
}
