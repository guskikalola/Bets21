package dataAccess;

import java.util.ArrayList;
//hello
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Admin;
import domain.Apustua;
import domain.BlokeoContainer;
import domain.Blokeoa;
import domain.Erabiltzailea;
import domain.Event;
import domain.Kuota;
import domain.Mezua;
import domain.Jarraitzen;
import domain.Mugimendua;
import domain.Pertsona;
import domain.Question;
import exceptions.ApustuaEzDaEgin;
import exceptions.EmaitzaEzinIpini;
import exceptions.MezuaEzDaZuzena;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public DataAccess(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccess() {
		this(false);
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();
		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}

			Event ev1 = new Event(1, "Atlético-Athletic", UtilDate.newDate(year, month, 17));
			Event ev2 = new Event(2, "Eibar-Barcelona", UtilDate.newDate(year, month, 17));
			Event ev3 = new Event(3, "Getafe-Celta", UtilDate.newDate(year, month, 17));
			Event ev4 = new Event(4, "Alavés-Deportivo", UtilDate.newDate(year, month, 17));
			Event ev5 = new Event(5, "Español-Villareal", UtilDate.newDate(year, month, 17));
			Event ev6 = new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
			Event ev7 = new Event(7, "Malaga-Valencia", UtilDate.newDate(year, month, 17));
			Event ev8 = new Event(8, "Girona-Leganés", UtilDate.newDate(year, month, 17));
			Event ev9 = new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
			Event ev10 = new Event(10, "Betis-Real Madrid", UtilDate.newDate(year, month, 17));

			Event ev11 = new Event(11, "Atletico-Athletic", UtilDate.newDate(year, month, 1));
			Event ev12 = new Event(12, "Eibar-Barcelona", UtilDate.newDate(year, month, 1));
			Event ev13 = new Event(13, "Getafe-Celta", UtilDate.newDate(year, month, 1));
			Event ev14 = new Event(14, "Alavés-Deportivo", UtilDate.newDate(year, month, 1));
			Event ev15 = new Event(15, "Español-Villareal", UtilDate.newDate(year, month, 1));
			Event ev16 = new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));

			Event ev17 = new Event(17, "Málaga-Valencia", UtilDate.newDate(year, month + 1, 28));
			Event ev18 = new Event(18, "Girona-Leganés", UtilDate.newDate(year, month + 1, 28));
			Event ev19 = new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year, month + 1, 28));
			Event ev20 = new Event(20, "Betis-Real Madrid", UtilDate.newDate(year, month + 1, 28));

			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
				q2 = ev1.addQuestion("¿Quién meterá el primer gol?", 2);
				q3 = ev11.addQuestion("¿Quién ganará el partido?", 1);
				q4 = ev11.addQuestion("¿Cuántos goles se marcarán?", 2);
				q5 = ev17.addQuestion("¿Quién ganará el partido?", 1);
				q6 = ev17.addQuestion("¿Habrá goles en la primera parte?", 2);
			} else if (Locale.getDefault().equals(new Locale("en"))) {
				q1 = ev1.addQuestion("Who will win the match?", 1);
				q2 = ev1.addQuestion("Who will score first?", 2);
				q3 = ev11.addQuestion("Who will win the match?", 1);
				q4 = ev11.addQuestion("How many goals will be scored in the match?", 2);
				q5 = ev17.addQuestion("Who will win the match?", 1);
				q6 = ev17.addQuestion("Will there be goals in the first half?", 2);
			} else {
				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
				q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1);
				q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2);
				q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1);
				q6 = ev17.addQuestion("Golak sartuko dira lehenengo zatian?", 2);

			}

			Admin sistema = new Admin("sistema", "en28dnMXMN2zj28", new Date()); // MEZUAK SISTEMA BEZELA BIDALTZEKO
			Admin admin2 = new Admin("admin2", "pass", new Date());
			Erabiltzailea erab = new Erabiltzailea("erab", "erab", new Date());

			/* AURKEZPENERAKO DATUAK */

			Admin admin = new Admin("admin", "pass", new Date());

			Erabiltzailea erab1 = new Erabiltzailea("a", "a", new Date());
			Erabiltzailea erab2 = new Erabiltzailea("e", "e", new Date());
			erab1.saldoaAldatu(1200);
			erab2.saldoaAldatu(1340);

			Kuota k1, k2, k3, k4,k5;
			// Zeinek irabaziko du partidoa
			k1 = q1.ipiniKuota("Atlético", 2.0);
			k2 = q1.ipiniKuota("X", 6.1);
			k3 = q1.ipiniKuota("Athletic", 6.1);
			// Zeinek sartuko du lehengo gola
			k4 = q2.ipiniKuota("Atlético", 2.0);
			k5 = q2.ipiniKuota("Athletic", 6.1);
			
			/* AURKEZPENERAKO DATUAK */

			db.persist(sistema);
			db.persist(admin);
			db.persist(admin2);
			db.persist(erab);
			db.persist(erab1);
			db.persist(erab2);

			db.persist(k1);
			db.persist(k2);
			db.persist(k3);
			db.persist(k4);
			db.persist(k5);

			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);

			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);

			db.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		// db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions
		// property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}

	public void open(boolean initializeMode) {

		System.out.println("Opening DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode) {
			fileName = fileName + ";drop";
			System.out.println("Deleting the DataBase");
		}

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}

	}

	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= " + event + " question= " + question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);
	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean existitzenDa(String izena, String pasahitza) {
		Pertsona e = db.find(Pertsona.class, izena);
		if (e == null)
			return false;
		else {
			return e.pasahitzaZuzena(pasahitza);
		}
	}

	public Pertsona getErabiltzailea(String izena) {
		return db.find(Pertsona.class, izena);
	}

	public Erabiltzailea getErabiltzaileaIzenarekin(String izena) {
		return db.find(Erabiltzailea.class, izena);
	}

	private boolean adinaDu(Date jaiotzeData) {
		Calendar gaur = Calendar.getInstance();
		// TODO: Aldatu
		int urteDif = Math.abs(gaur.get(Calendar.YEAR) - jaiotzeData.getYear());
		int hilbDif = gaur.get(Calendar.MONTH) - jaiotzeData.getMonth();

		int hilabKop = (urteDif * 12) + (hilbDif > 0 ? hilbDif : 0);

		int urteKop = hilabKop / 12;
		System.out.println(urteKop);

		return (urteKop >= 18);
	}

	public Pertsona erregistratu(String izena, String pasahitza, Date jaiotzeData) {
		// Aztertu ea aurretik existitzen den erabiltzailea izen horrekin
		Pertsona e = this.getErabiltzailea(izena);
		if (e == null) {
			// Erabiltzailerik ez da existitzen
			// Aztertu ea adina >= 18 den
			boolean adinaNahikoa = this.adinaDu(jaiotzeData);
			if (adinaNahikoa) {
				Pertsona er = this.sortuErabiltzailea(izena, pasahitza, jaiotzeData);
				return er;
			} else
				return null;
		} else
			return null;
	}

	private Pertsona sortuErabiltzailea(String izena, String pasahitza, Date jaiotzeData) {
		db.getTransaction().begin();
		// TODO: Soilik Erabiltzaileak sortu daitezke.
		Pertsona er = new Erabiltzailea(izena, pasahitza, jaiotzeData);
		db.persist(er);
		db.getTransaction().commit();
		return er;
	}

	public Kuota ipiniKuota(Question q, String aukera, double kantitatea) {
		db.getTransaction().begin();
		Question qDB = db.find(Question.class, q.getQuestionNumber());
		Kuota k = null;
		if (qDB != null && !qDB.kuotaExistitzenDa(aukera)) {
			// Kuota ez da existitzen, sortu.
			k = qDB.ipiniKuota(aukera, kantitatea);
			db.persist(k);
		}

		db.getTransaction().commit();
		return k;
	}

	public Event sortuGertaera(Date data, String deskripzioa) {
		boolean exists = this.gertaeraExistitzenDa(data, deskripzioa);
		if (!exists) {
			Event event = this.createEvent(data, deskripzioa);
			event.toString();
			return event;
		} else {
			return null;
		}
	}

	private boolean gertaeraExistitzenDa(Date data, String deskripzioa) {
		Query query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1 AND ev.description=?2");
		query.setParameter(1, data);
		query.setParameter(2, deskripzioa);
		List<Event> eventsList = query.getResultList();
		if (eventsList.isEmpty()) {
			return false;
		} else {
			return true;
		}

	}

	private Event createEvent(Date data, String deskripzioa) {
		db.getTransaction().begin();
		Event event = new Event(deskripzioa, data);
		db.persist(event);
		event.toString();
		db.getTransaction().commit();
		return event;
	}

	public boolean diruaSartu(Erabiltzailea erabiltzaile, String pasahitza, Double kantitatea) {
		Erabiltzailea e = db.find(Erabiltzailea.class, erabiltzaile.getIzena());
		if (e == null)
			return false;
		else if (!e.pasahitzaZuzena(pasahitza))
			return false;
		else {
			if (e.getBlokeoa() == null) {
				db.getTransaction().begin();
				e.saldoaAldatu(kantitatea);
				Mugimendua m = new Mugimendua(erabiltzaile, kantitatea, "dirua_sartu");
				e.mugimenduaGehitu(m);
				db.persist(m);
				db.getTransaction().commit();
				return true;
			} else
				return false;
		}
	}

	public List<Mugimendua> mugimenduakIkusi(Erabiltzailea er) {
		if (!(er instanceof Erabiltzailea))
			return null;
		else {
			Erabiltzailea e = db.find(Erabiltzailea.class, er.getIzena());
			List<Mugimendua> m = e.getMugimenduak();
			return m;
		}
	}

	public boolean removeEvent(Event ev) {
		db.getTransaction().begin();
		Event evDB = db.find(Event.class, ev.getEventNumber());
		if (evDB == null)
			return false;
		List<Question> galderak = evDB.getQuestions();
		for (Question q : galderak) {
			List<Kuota> kuotak = q.getKuotak();
			for (Kuota k : kuotak) {
				List<Apustua> apustuak = k.getApustuak();
				for (Apustua ap : apustuak) {
					Erabiltzailea er = ap.getErabiltzailea();
					if (ap.ezabatuDaiteke()) {
						double diruKopurua = ap.getDiruKop();
						er.saldoaAldatu(diruKopurua);
						Mugimendua g = new Mugimendua(er, diruKopurua, "gertaera_ezabatuta");
						er.mugimenduaGehitu(g);
						db.persist(g);
					}
					er.apustuaEzabatuListatik(ap);
					db.remove(ap);
				}
				db.remove(k);
			}
			db.remove(q);
		}
		db.remove(evDB);
		db.getTransaction().commit();
		return true;
	}

	public Apustua apustuaEgin(Erabiltzailea er, Kuota ki, Double diruKop) throws ApustuaEzDaEgin {
		db.getTransaction().begin();
		String izena = er.getIzena();
		Erabiltzailea erDB = db.find(Erabiltzailea.class, izena);
		Kuota kDB = db.find(Kuota.class, ki.getKuotaZenbakia());
		if (erDB != null && erDB.getBlokeoa() == null) {
			Boolean nahikoa = erDB.diruaNahikoa(diruKop);
			Boolean minimoaGaindtu = diruKop >= kDB.getQuestion().getBetMinimum();
			if (kDB.galderaEmaitzaDu()) {
				throw new ApustuaEzDaEgin("galdera_emaitza_du");
			} else if (nahikoa && minimoaGaindtu) {
				erDB.saldoaAldatu((-1) * diruKop);
				Mugimendua mugi = erDB.mugimenduaSortu((-1) * diruKop, "apustua_eginda");
				db.persist(mugi);
				Apustua apustua = erDB.apustuaSortu(diruKop, kDB);
				db.persist(apustua);
				kDB.apustuaGehitu(apustua);
				db.getTransaction().commit();
				this.apustuaJarraitu(apustua);
				return apustua;
			} else {
				if (!nahikoa)
					throw new ApustuaEzDaEgin("NoMoney");
				else if (!minimoaGaindtu)
					throw new ApustuaEzDaEgin("errorea_minimoa_gainditu");
			}
			return null;
		} else {
			db.getTransaction().commit();
			return null;
		}
	}

	public boolean apustuaEzabatu(Apustua a, Erabiltzailea er) {
		db.getTransaction().begin();
		Apustua aDB = db.find(Apustua.class, a.getApustuZenbakia());
		if (aDB != null) {
			Erabiltzailea erDB = aDB.getErabiltzailea();
			if (erDB.getIzena().equals(er.getIzena()) && aDB.ezabatuDaiteke() && erDB.getBlokeoa() == null) {
				List<Kuota> kDBLista = aDB.getKuotak();
				Double diruKop = aDB.getDiruKop();
				erDB.saldoaAldatu(diruKop);
				Mugimendua m = erDB.mugimenduaSortu(diruKop, "apustua_ezabatuta");
				db.persist(m);
				erDB.apustuaEzabatuListatik(aDB);
				for (Kuota kiDB : kDBLista) {
					kiDB.apustuaEzabatuListatik(aDB);
				}
				db.remove(aDB);
				db.getTransaction().commit();
				return true;
			}
		}
		db.getTransaction().commit();
		return false;
	}

	public List<Erabiltzailea> emaitzaIpini(Question q, Kuota k) throws EmaitzaEzinIpini {
		db.getTransaction().begin();
		Integer questionNumber = q.getQuestionNumber();
		List<Erabiltzailea> erlist = new ArrayList<Erabiltzailea>();
		Question qDB = db.find(Question.class, questionNumber);
		if (qDB != null && qDB.getResult() == null) {
			String aukera = k.getAukera();
			Kuota kuota = qDB.getAukeraDuenKuota(aukera);
			if (kuota != null) {
				qDB.setResult(aukera);
				List<Apustua> alist = kuota.getApustuak();

				for (Apustua ap : alist) {
					Erabiltzailea erab = ap.getErabiltzailea();
					if (ap.irabaziDu() && erab.getBlokeoa() == null) {
						double irabaziDirua = ap.getIrabazia();
						erab.saldoaAldatu(irabaziDirua);
						Mugimendua m = erab.mugimenduaSortu(irabaziDirua, "apustua_irabazi");
						db.persist(m);
						erlist.add(erab);
					}
				}
			} else {
				return null;
			}

		} else {
			throw new EmaitzaEzinIpini("errorea_emaitza_du");
		}
		db.getTransaction().commit();
		return erlist;
	}

	public List<Apustua> getApustuakErabiltzailea(Kuota k, Erabiltzailea er) {
		Kuota kDB = db.find(Kuota.class, k.getKuotaZenbakia());
		if (kDB != null)
			return kDB.getApustuak(er);
		else
			return null;

	}

	public List<Erabiltzailea> getErabiltzaileaGuztiak() {

		TypedQuery<Erabiltzailea> q = db.createQuery("SELECT er FROM Erabiltzailea er", Erabiltzailea.class);
		return q.getResultList();
	}

	public List<Pertsona> getPertsonaGuztiak() {
		TypedQuery<Pertsona> p = db.createQuery("SELECT p FROM Pertsona p", Pertsona.class);
		return p.getResultList();
	}

	public Blokeoa getBlokeoContainer(Erabiltzailea e) {
		Erabiltzailea eDB = db.find(Erabiltzailea.class, e.getIzena());
		Query query = db.createQuery("SELECT bl FROM Blokeoa bl WHERE bl.Nori=?1");
		query.setParameter(1, eDB);
		Blokeoa blokeo = (Blokeoa) query.getResultList().get(0);
		return blokeo;
	}

	public List<Mezua> getMezuGuztiak(Pertsona m, Pertsona nori) {
		db.getTransaction().begin();
		Pertsona mezulari = db.find(Pertsona.class, m.getIzena());
		Pertsona noriDB = db.find(Pertsona.class, nori.getIzena());
		ArrayList<Mezua> mezuBidali = mezulari.BidalitakoMezuakEskuratu(noriDB);
		ArrayList<Mezua> mezuJaso = mezulari.jasotakoMezuakEskuratu(noriDB);
		if (mezuJaso != null) {
			for (Mezua me : mezuJaso) {
				me.setIrakurrita(true);
				mezuBidali.add(me);
			}
		}
		db.getTransaction().commit();
		return mezuBidali;
	}

	public boolean erabiltzaileaJarraitu(Erabiltzailea unekoErab, Erabiltzailea aukeratutakoErabiltzailea,
			float diruMax) {
		if (unekoErab == null || aukeratutakoErabiltzailea == null || unekoErab.equals(aukeratutakoErabiltzailea)) {
			return false; // ezin duzu zure burua jarraitu
		}
		db.getTransaction().begin();
		Erabiltzailea unErDB = db.find(Erabiltzailea.class, unekoErab.getIzena());
		Erabiltzailea erDB = db.find(Erabiltzailea.class, aukeratutakoErabiltzailea.getIzena());
		if (unErDB == null || erDB == null || unErDB.getBlokeoa() != null) {
			return false;
		} else {
			Jarraitzen bJarraitu = unErDB.jarraitzenDu(erDB);
			if (bJarraitu != null) { // Jarraizten utzi
				unErDB.ezabatuJarraitzenListatik(bJarraitu);
				erDB.ezabatuJarraitzaileakListatik(unErDB);
			} else {
				Jarraitzen jB = unErDB.jarraitu(erDB, diruMax);
				erDB.gehituJarraitzaileakListara(unErDB);
				db.persist(jB);
			}
		}
		db.getTransaction().commit();

		return true;
	}

	public Apustua apustuAnizkoitzaEgin(Erabiltzailea er, List<Kuota> kuotaLista, double diruKop,
			boolean apustuaJarraitu) throws ApustuaEzDaEgin {
		db.getTransaction().begin();
		String izena = er.getIzena();
		Erabiltzailea erDB = db.find(Erabiltzailea.class, izena);
		List<Kuota> kDBLista = new ArrayList<Kuota>();

		double minBet = 0;

		if (kuotaLista == null || kuotaLista.size() == 0)
			return null; // apustua egiteko, minimo kuota bat aukeratu behar da

		for (Kuota ki : kuotaLista) {
			Kuota kiDB = db.find(Kuota.class, ki.getKuotaZenbakia());

			if (kiDB.galderaEmaitzaDu()) {
				throw new ApustuaEzDaEgin("galdera_emaitza_du");
			}

			if (kiDB == null)
				return null;
			else {
				kDBLista.add(kiDB);
				minBet += kiDB.getQuestion().getBetMinimum();
			}
		}

		if (erDB != null && erDB.getBlokeoa() == null) {
			Boolean nahikoa = erDB.diruaNahikoa(diruKop);
			Boolean minimoaGaindtu = diruKop >= minBet;
			if (nahikoa && minimoaGaindtu) {
				erDB.saldoaAldatu((-1) * diruKop);
				Mugimendua mugi = erDB.mugimenduaSortu((-1) * diruKop, "apustua_eginda");
				db.persist(mugi);
				Apustua apustua = erDB.apustuaSortu(diruKop, kDBLista);
				db.persist(apustua);
				for (Kuota kDB : kDBLista) {
					kDB.apustuaGehitu(apustua);
				}
				db.getTransaction().commit();
				if (apustuaJarraitu)
					this.apustuaJarraitu(apustua);
				return apustua;
			} else {
				if (!nahikoa) {
					db.getTransaction().rollback();
					throw new ApustuaEzDaEgin("NoMoney");
				} else if (!minimoaGaindtu) {
					db.getTransaction().rollback();
					throw new ApustuaEzDaEgin("errorea_minimoa_gainditu");
				}
			}
			return null;
		} else {
			db.getTransaction().commit();
			return null;
		}

	}

	public Apustua apustuAnizkoitzaEgin(Erabiltzailea er, List<Kuota> kuotaLista, double diruKop)
			throws ApustuaEzDaEgin {
		return this.apustuAnizkoitzaEgin(er, kuotaLista, diruKop, true);
	}

	private void apustuaJarraitu(Apustua apustua) {
		Erabiltzailea egilea = apustua.getErabiltzailea();
		List<Erabiltzailea> jarraitzaileak = egilea.getJarraitzaileak();
		Pertsona sistema = db.find(Pertsona.class, "sistema");
		for (Erabiltzailea jarraitzailea : jarraitzaileak) {
			Jarraitzen jarraitzen = jarraitzailea.jarraitzenDu(egilea);
			if (jarraitzen != null && jarraitzailea.getBlokeoa() == null) {
				try {
					// Aztertu baldintzak
					boolean b = jarraitzen.baldintzakBetetzenDitu(apustua);
					if (b) {
						Apustua ap = this.apustuAnizkoitzaEgin(jarraitzailea, apustua.getKuotak(), apustua.getDiruKop(),
								false);
						this.mezuaBidali(sistema, jarraitzailea,
								String.format("%s egindako apustua jarraituta. Apustua: %d", egilea.getIzena(),
										ap.getApustuZenbakia()));
					} else {
						this.mezuaBidali(sistema, jarraitzailea, String.format(
								"%s egindako apustua ez ditu baldintzak bete. Ez da jarraitu.", egilea.getIzena()));
					}
				} catch (ApustuaEzDaEgin e) {
					// ? Ez tratatu momentuz, agian mezua bidali
					try {
						this.mezuaBidali(sistema, jarraitzailea,
								String.format("%s egindako apustua ezin izan da jarraitu. Errorea: %s",
										egilea.getIzena(), e.getMessage()));
					} catch (MezuaEzDaZuzena e1) {
						// Ez egin ezer
					}
				} catch (MezuaEzDaZuzena e) {
					// Ez egin ezer
				}
			}
		}
	}

	public Mezua mezuaBidali(Pertsona m, Pertsona nori, String mezua) throws MezuaEzDaZuzena {
		db.getTransaction().begin();
		Mezua mez = null;
		Pertsona mezulariDB = db.find(Pertsona.class, m.getIzena());
		Pertsona noriDB = db.find(Pertsona.class, nori.getIzena());
		Boolean zuzenaMIN = Mezua.mezuaZuzenaDaMIN(mezua);
		Boolean zuzenaMAX = Mezua.mezuaZuzenaDaMAX(mezua);
		if (zuzenaMIN && zuzenaMAX) {
			if (mezulariDB instanceof Erabiltzailea && ((Erabiltzailea) mezulariDB).getBlokeoa() != null && !((Erabiltzailea) mezulariDB).getBlokeoa().getNor().equals(nori)) {
				db.getTransaction().rollback();
				return null;
			}
			mez = new Mezua(mezulariDB, noriDB, mezua);
			mezulariDB.gehituBidaliLista(mez);
			noriDB.gehituJasotakoLista(mez);
			db.persist(mez);
		} else {
			if (!zuzenaMIN) {
				db.getTransaction().rollback();
				throw new MezuaEzDaZuzena("Short_message");
			} else if (!zuzenaMAX) {
				db.getTransaction().rollback();
				throw new MezuaEzDaZuzena("Long_message");
			}
		}
		db.getTransaction().commit();
		return mez;
	}

	public Blokeoa erabiltzaileaBlokeatu(Admin a, Erabiltzailea ei, String arrazoia) throws MezuaEzDaZuzena {
		db.getTransaction().begin();
		Blokeoa bl = null;
		Admin aDB = db.find(Admin.class, a.getIzena());
		Boolean zuzenaMIN = false;
		Boolean zuzenaMAX = false;
		Erabiltzailea eDB = db.find(Erabiltzailea.class, ei.getIzena());
		if (arrazoia == null) {
			zuzenaMIN = true;
			zuzenaMAX = true;
		} else {
			zuzenaMIN = Mezua.mezuaZuzenaDaMIN(arrazoia);
			zuzenaMAX = Mezua.mezuaZuzenaDaMAX(arrazoia);
		}
		if (zuzenaMIN && zuzenaMAX) {
			if (eDB.getBlokeoa() == null) {
				bl = new Blokeoa(aDB, eDB, arrazoia);
				aDB.blokeoaGehituListan(bl);
				eDB.blokeoaGehitu(bl);
				db.persist(bl);
			} else {
				bl = eDB.getBlokeoa();
				aDB.blokeoaEzabatuListatik(bl);
				eDB.blokeoaEzabatu();
				db.remove(bl);
			}
		} else {
			if (!zuzenaMIN) {
				throw new MezuaEzDaZuzena("Short_reason");
			} else if (!zuzenaMAX) {
				throw new MezuaEzDaZuzena("Long_reason");
			}
		}
		db.getTransaction().commit();
		return bl;
	}

	public boolean gertaeraBikoiztu(Date data, String deskribapena, Event oldEvent) {
		db.getTransaction().begin();
		boolean emaitza = true;
		Event evDB = db.find(Event.class, oldEvent.getEventNumber());
		List<Question> qlist = evDB.getQuestions();
		Event newEvent = new Event(deskribapena, data);

		for (Question q : qlist) {
			List<Kuota> klist = q.getKuotak();
			String ques = q.getQuestion();
			float betMin = q.getBetMinimum();
			Question qnew = new Question(ques, betMin, newEvent);

			for (Kuota k : klist) {
				String auk = k.getAukera();
				double kant = k.getKantitatea();
				Kuota knew = new Kuota(auk, kant, qnew);
				qnew.getKuotak().add(knew);
				db.persist(knew);
			}
			db.persist(qnew);
			newEvent.getQuestions().add(qnew);
		}
		try {
			db.persist(newEvent);
		} catch (IndexOutOfBoundsException e) {
			emaitza = false;
		}

		db.getTransaction().commit();
		return emaitza;
	}

}
