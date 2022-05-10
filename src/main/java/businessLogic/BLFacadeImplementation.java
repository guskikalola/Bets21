package businessLogic;

import java.util.ArrayList;
//hola
import domain.Jarraitzen;
import domain.JarraitzenContainer;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.swing.JFrame;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Question;
import domain.Admin;
import domain.Apustua;
import domain.ApustuaContainer;
import domain.BlokeoContainer;
import domain.Blokeoa;
import domain.Erabiltzailea;
import domain.Event;
import domain.Kuota;
import domain.Mezua;
import domain.MezuaContainer;
import domain.Mugimendua;
import domain.Pertsona;
import exceptions.ApustuaEzDaEgin;
import exceptions.EmaitzaEzinIpini;
import exceptions.EventFinished;
import exceptions.MezuaEzDaZuzena;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			dbManager = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
		} else
			dbManager = new DataAccess();
		dbManager.close();

	}

	public BLFacadeImplementation(DataAccess da) {

		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			da.close();

		}

		dbManager = da;
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished        if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	@WebMethod
	public Question createQuestion(Event event, String question, float betMinimum)
			throws EventFinished, QuestionAlreadyExist {

		// The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

		qry = dbManager.createQuestion(event, question, betMinimum);

		dbManager.close();

		return qry;
	};

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public Vector<Event> getEvents(Date date) {
		dbManager.open(false);
		Vector<Event> events = dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

	/**
	 * This method invokes the data access to retrieve the dates a month for which
	 * there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date> dates = dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}

	public void close() {
		DataAccess dB4oManager = new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some
	 * events and questions. It is invoked only when the option "initialize" is
	 * declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD() {
		dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}

	@Override
	@WebMethod
	public Pertsona existitzenDa(String izena, String pasahitza) {
		dbManager.open(false);
		Pertsona e = dbManager.getErabiltzailea(izena);
		dbManager.close();
		if (e != null && e.getPasahitza().equals(pasahitza)) {
			return e;
		}
		return null;
	}

	@Override
	@WebMethod
	public Pertsona erregistratu(String izena, String pasahitza, Date jaiotzeData) {
		dbManager.open(false);
		Pertsona e = dbManager.erregistratu(izena, pasahitza, jaiotzeData);
		dbManager.close();
		return e;
	}

	@Override
	@WebMethod
	public Event sortuGertaera(Date data, String deskribapena) {
		dbManager.open(false);
		Event event = dbManager.sortuGertaera(data, deskribapena);
		dbManager.close();
		return event;
	}

	@Override
	@WebMethod
	public Kuota ipiniKuota(Question q, String aukera, double kantitatea) {
		dbManager.open(false);
		Kuota k = dbManager.ipiniKuota(q, aukera, kantitatea);
		dbManager.close();
		return k;
	}

	@Override
	@WebMethod
	public Apustua apustuaEgin(Erabiltzailea er, Kuota ki, Double diruKop) throws ApustuaEzDaEgin {
		dbManager.open(false);
		Apustua a = dbManager.apustuaEgin(er, ki, diruKop);
		dbManager.close();
		return a;
	}

	@Override
	@WebMethod
	public boolean apustuaEzabatu(Apustua a, Erabiltzailea er) {
		dbManager.open(false);
		Boolean bool = dbManager.apustuaEzabatu(a, er);
		dbManager.close();
		return bool;
	}

	@Override
	@WebMethod
	public boolean diruaSartu(Erabiltzailea erabiltzaile, String pasahitza, Double kantitatea) {
		dbManager.open(false);
		boolean em = dbManager.diruaSartu(erabiltzaile, pasahitza, kantitatea);
		dbManager.close();
		return em;
	}

	@Override
	@WebMethod
	public List<Mugimendua> mugimenduakIkusi(Erabiltzailea er) {
		dbManager.open(false);
		List<Mugimendua> m = dbManager.mugimenduakIkusi(er);
		dbManager.close();
		return m;
	}

	@Override
	@WebMethod
	public boolean removeEvent(Event ev) {
		dbManager.open(false);
		boolean ezabatuta = dbManager.removeEvent(ev);
		dbManager.close();
		return ezabatuta;
	}

	@Override
	@WebMethod
	public List<Erabiltzailea> emaitzaIpini(Question q, Kuota k) throws EmaitzaEzinIpini {
		dbManager.open(false);
		List<Erabiltzailea> er = dbManager.emaitzaIpini(q, k);
		dbManager.close();
		return er;

	}

	@Override
	@WebMethod
	public List<ApustuaContainer> getApustuakErabiltzailea(Kuota k, Erabiltzailea er) {
		dbManager.open(false);
		List<Apustua> em = dbManager.getApustuakErabiltzailea(k, er);
		List<ApustuaContainer> emC = new ArrayList<ApustuaContainer>();
		if (em != null) {
			for (Apustua ap : em) {
				emC.add(new ApustuaContainer(ap));
			}
		}
		dbManager.close();
		return em != null ? emC : null;
	}

	@Override
	@WebMethod
	public List<Erabiltzailea> getErabiltzaileaGuztiak() {
		dbManager.open(false);
		List<Erabiltzailea> er = dbManager.getErabiltzaileaGuztiak();
		dbManager.close();
		return er;
	}
	
	@Override
	@WebMethod
	public List<Pertsona> getPertsonaGuztiak() {
		dbManager.open(false);
		List<Pertsona> er = dbManager.getPertsonaGuztiak();
		dbManager.close();
		return er;
	}
	
	@Override
	@WebMethod
	public boolean gertaeraBikoiztu(Date data, String deskribapena, Event oldEvent) {
		dbManager.open(false);
		boolean emaitza = false;
		emaitza = dbManager.gertaeraBikoiztu( data, deskribapena,  oldEvent);
		dbManager.close();
		return emaitza;
	}
	

	@Override
	public boolean erabiltzaileaJarraitu(Erabiltzailea unekoErab, Erabiltzailea aukeratutakoErabiltzailea,
			float diruMax) {
		dbManager.open(false);
		boolean em = dbManager.erabiltzaileaJarraitu(unekoErab, aukeratutakoErabiltzailea, diruMax);
		dbManager.close();
		return em;
	}
	

	@Override
	public JarraitzenContainer jarraitzenDu(Erabiltzailea er, Erabiltzailea nori) {
		dbManager.open(false);
		JarraitzenContainer em = null;
		Erabiltzailea er1 = (Erabiltzailea) dbManager.getErabiltzailea(er.getIzena());
		Jarraitzen j = er1.jarraitzenDu(nori);
		if (j != null)
			em = new JarraitzenContainer(j);
		dbManager.close();
		return em;
	}

	@Override
	public List<JarraitzenContainer> getJarraitzen(Erabiltzailea er) {
		dbManager.open(false);
		List<JarraitzenContainer> em = new ArrayList<JarraitzenContainer>();
		Erabiltzailea erDB = (Erabiltzailea) dbManager.getErabiltzailea(er.getIzena());
		for (Jarraitzen j : erDB.getJarraitzen()) {
			em.add(new JarraitzenContainer(j));
		}
		dbManager.close();
		return em;
	}



	@Override
	public Apustua apustuAnizkoitzaEgin(Erabiltzailea er, List<Kuota> kuotaLista, double diruKop)
			throws ApustuaEzDaEgin {
		dbManager.open(false);
		Apustua em = dbManager.apustuAnizkoitzaEgin(er, kuotaLista, diruKop);
		dbManager.close();
		return em;
	}

	@Override
	public Pertsona getPertsona(String izena) {
		dbManager.open(false);
		Pertsona p = dbManager.getErabiltzailea(izena);
		dbManager.close();
		return p;
	}
	
	@Override
	public Erabiltzailea getErabiltzailea(String izena) {
		dbManager.open(false);
		Erabiltzailea e = dbManager.getErabiltzaileaIzenarekin(izena);
		dbManager.close();
		return e;
	}

	@Override
	public int getApustuakIrabazitak(Erabiltzailea er) {
		dbManager.open(false);
		int kop = 0;
		Pertsona p = dbManager.getErabiltzailea(er.getIzena());
		if (p instanceof Erabiltzailea)
			kop = ((Erabiltzailea) p).getApustuakIrabazitak();
		dbManager.close();
		return kop;
	}
	
	@Override
	public List<Mezua> getMezuGuztiak(Pertsona m, Pertsona nori) {
		dbManager.open(false);
		List<Mezua> mezu= dbManager.getMezuGuztiak(m, nori);
		dbManager.close();
		return mezu;
	}
	
	@Override
	public List<MezuaContainer> getMezuGuztiakContainer(Pertsona m, Pertsona nori) {
		dbManager.open(false);
		List<MezuaContainer> mezuList = new ArrayList<MezuaContainer>();
		List<Mezua> me=  dbManager.getMezuGuztiak(m, nori);
		for(Mezua mez: me) {
			MezuaContainer mezuC= new MezuaContainer();
			mezuC.setM(mez);
			mezuC.setNor(mez.getNor());
			mezuC.setNori(mez.getNori());
			mezuList.add(mezuC);
		}
		dbManager.close();
		return mezuList;
	}

	@Override
	public Mezua mezuaBidali(Pertsona m, Pertsona nori, String mezua) throws MezuaEzDaZuzena {
		dbManager.open(false);
		Mezua mezu= dbManager.mezuaBidali(m, nori, mezua);
		dbManager.close();
		return mezu;
	}
	
	@Override
	public Blokeoa erabiltzaileaBlokeatu(Admin a, Erabiltzailea ei, String arrazoia) throws MezuaEzDaZuzena {
		dbManager.open(false);
		Blokeoa bl= dbManager.erabiltzaileaBlokeatu(a, ei, arrazoia);
		dbManager.close();
		return bl;
	}
	
	@Override 
	public BlokeoContainer getBlokeoContainer(Erabiltzailea e) {
		dbManager.open(false);
		BlokeoContainer blC= new BlokeoContainer();
		Blokeoa blokeo=  dbManager.getBlokeoContainer(e);
		if(blC!=null) {
			blC.setBl(blokeo);
			blC.setNor(blokeo.getNor());
			blC.setNori(blokeo.getNori());
		}
		dbManager.close();
		return blC;
	}
}
