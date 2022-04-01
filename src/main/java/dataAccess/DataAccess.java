package dataAccess;

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
import domain.Erabiltzailea;
import domain.Event;
import domain.Kuota;
import domain.Mugimendua;
import domain.Pertsona;
import domain.Question;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	protected static EntityManager  db;
	protected static EntityManagerFactory emf;


	ConfigXML c=ConfigXML.getInstance();

     public DataAccess(boolean initializeMode)  {
		
		System.out.println("Creating DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		open(initializeMode);
		
	}

	public DataAccess()  {	
		 this(false);
	}
	
	
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();
		try {

			
		   Calendar today = Calendar.getInstance();
		   
		   int month=today.get(Calendar.MONTH);
		   month+=1;
		   int year=today.get(Calendar.YEAR);
		   if (month==12) { month=0; year+=1;}  
	    
			Event ev1=new Event(1, "Atlético-Athletic", UtilDate.newDate(year,month,17));
			Event ev2=new Event(2, "Eibar-Barcelona", UtilDate.newDate(year,month,17));
			Event ev3=new Event(3, "Getafe-Celta", UtilDate.newDate(year,month,17));
			Event ev4=new Event(4, "Alavés-Deportivo", UtilDate.newDate(year,month,17));
			Event ev5=new Event(5, "Español-Villareal", UtilDate.newDate(year,month,17));
			Event ev6=new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year,month,17));
			Event ev7=new Event(7, "Malaga-Valencia", UtilDate.newDate(year,month,17));
			Event ev8=new Event(8, "Girona-Leganés", UtilDate.newDate(year,month,17));
			Event ev9=new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year,month,17));
			Event ev10=new Event(10, "Betis-Real Madrid", UtilDate.newDate(year,month,17));

			Event ev11=new Event(11, "Atletico-Athletic", UtilDate.newDate(year,month,1));
			Event ev12=new Event(12, "Eibar-Barcelona", UtilDate.newDate(year,month,1));
			Event ev13=new Event(13, "Getafe-Celta", UtilDate.newDate(year,month,1));
			Event ev14=new Event(14, "Alavés-Deportivo", UtilDate.newDate(year,month,1));
			Event ev15=new Event(15, "Español-Villareal", UtilDate.newDate(year,month,1));
			Event ev16=new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year,month,1));
			

			Event ev17=new Event(17, "Málaga-Valencia", UtilDate.newDate(year,month+1,28));
			Event ev18=new Event(18, "Girona-Leganés", UtilDate.newDate(year,month+1,28));
			Event ev19=new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year,month+1,28));
			Event ev20=new Event(20, "Betis-Real Madrid", UtilDate.newDate(year,month+1,28));
			
			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;
					
			if (Locale.getDefault().equals(new Locale("es"))) {
				q1=ev1.addQuestion("¿Quién ganará el partido?",1);
				q2=ev1.addQuestion("¿Quién meterá el primer gol?",2);
				q3=ev11.addQuestion("¿Quién ganará el partido?",1);
				q4=ev11.addQuestion("¿Cuántos goles se marcarán?",2);
				q5=ev17.addQuestion("¿Quién ganará el partido?",1);
				q6=ev17.addQuestion("¿Habrá goles en la primera parte?",2);
			}
			else if (Locale.getDefault().equals(new Locale("en"))) {
				q1=ev1.addQuestion("Who will win the match?",1);
				q2=ev1.addQuestion("Who will score first?",2);
				q3=ev11.addQuestion("Who will win the match?",1);
				q4=ev11.addQuestion("How many goals will be scored in the match?",2);
				q5=ev17.addQuestion("Who will win the match?",1);
				q6=ev17.addQuestion("Will there be goals in the first half?",2);
			}			
			else {
				q1=ev1.addQuestion("Zeinek irabaziko du partidua?",1);
				q2=ev1.addQuestion("Zeinek sartuko du lehenengo gola?",2);
				q3=ev11.addQuestion("Zeinek irabaziko du partidua?",1);
				q4=ev11.addQuestion("Zenbat gol sartuko dira?",2);
				q5=ev17.addQuestion("Zeinek irabaziko du partidua?",1);
				q6=ev17.addQuestion("Golak sartuko dira lehenengo zatian?",2);
				
			}
			
			// TODO: Ezabatu ( Probako login )
			Admin admin = new Admin("admin","pass",new Date());
			
			db.persist(admin);
			
			
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
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws  QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= "+event+" question= "+question+" betMinimum="+betMinimum);
		
			Event ev = db.find(Event.class, event.getEventNumber());
			
			if (ev.DoesQuestionExists(question)) throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));
			
			db.getTransaction().begin();
			Question q = ev.addQuestion(question, betMinimum);
			//db.persist(q);
			db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions property of Event class
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
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1",Event.class);   
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
	 	 for (Event ev:events){
	 	   System.out.println(ev.toString());		 
		   res.add(ev);
		  }
	 	return res;
	}
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
				
		
		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2",Date.class);   
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
	 	 for (Date d:dates){
	 	   System.out.println(d.toString());		 
		   res.add(d);
		  }
	 	return res;
	}
	

	public void open(boolean initializeMode){
		
		System.out.println("Opening DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		String fileName=c.getDbFilename();
		if (initializeMode) {
			fileName=fileName+";drop";
			System.out.println("Deleting the DataBase");
		}
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		
	}
	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= "+event+" question= "+question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);
	}
	
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean existitzenDa(String izena, String pasahitza) {
		Pertsona e = db.find(Pertsona.class, izena);
		if(e == null) return false;
		else {
			return e.pasahitzaZuzena(pasahitza);
		}
	}

	public Pertsona getErabiltzailea(String izena) {
		return db.find(Pertsona.class, izena);
	}

	private boolean adinaDu(Date jaiotzeData) {
		Calendar gaur = Calendar.getInstance();
		// TODO: Aldatu
		int urteDif = Math.abs(gaur.get(Calendar.YEAR) - jaiotzeData.getYear());
		int hilbDif = gaur.get(Calendar.MONTH) - jaiotzeData.getMonth();
		
		int hilabKop = (urteDif*12) + (hilbDif > 0 ? hilbDif : 0);
		
		int urteKop = hilabKop / 12;
		System.out.println(urteKop);
		
		return (urteKop>=18);
}
	
	public Pertsona erregistratu(String izena, String pasahitza, Date jaiotzeData) {
		// Aztertu ea aurretik existitzen den erabiltzailea izen horrekin
		Pertsona e = this.getErabiltzailea(izena);
		if(e == null) {
			// Erabiltzailerik ez da existitzen
			// Aztertu ea adina >= 18 den
			boolean adinaNahikoa = this.adinaDu(jaiotzeData);
			if(adinaNahikoa) {
				Pertsona er = this.sortuErabiltzailea(izena,pasahitza,jaiotzeData);
				return er;
			} else return null;
		} else return null;
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
		if(qDB != null && !qDB.kuotaExistitzenDa(aukera)) {
			// Kuota ez da existitzen, sortu.
			k = qDB.ipiniKuota(aukera, kantitatea);
			db.persist(k);
		} 
		
		db.getTransaction().commit();
		return k;
	}
	
	
	public Event sortuGertaera(Date data, String deskripzioa) {
		boolean exists= this.gertaeraExistitzenDa(data, deskripzioa);
		if (!exists) {
			Event event = this.createEvent(data, deskripzioa);
			event.toString();
			return event;
		}else {
			return null;
		}
	}
	
	private boolean gertaeraExistitzenDa(Date data, String deskripzioa) {
		Query query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1 AND ev.description=?2");
		query.setParameter(1, data);
		query.setParameter(2, deskripzioa);
		List<Event> eventsList = query.getResultList();
		if(eventsList.isEmpty()) {
			return false;
		}else{
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
		if(e==null) return false;
		else if(!e.pasahitzaZuzena(pasahitza)) return false;
		else {
			db.getTransaction().begin();
			e.saldoaAldatu(kantitatea);
			Mugimendua m = new Mugimendua(erabiltzaile, kantitatea, "dirua_sartu");
			e.mugimenduaGehitu(m);
			db.persist(m);
			db.getTransaction().commit();
			return true;
		}
	}

	public List<Mugimendua> mugimenduakIkusi(Erabiltzailea er) {
		if(!(er instanceof Erabiltzailea)) return null;
		else {
			Erabiltzailea e = db.find(Erabiltzailea.class, er.getIzena());
			List<Mugimendua> m = e.getMugimenduak();
			return m;
		}
	}

	public boolean removeEvent(Event ev) {
		db.getTransaction().begin();
		Event evDB = db.find(Event.class, ev.getEventNumber());
		if(evDB == null) return false;
		List<Question> galderak = evDB.getQuestions();
		for(Question q : galderak) {
			List<Kuota> kuotak = q.getKuotak();
			for(Kuota k : kuotak) {
				List<Apustua> apustuak = k.getApustuak();
				for(Apustua ap : apustuak) {
					Erabiltzailea er = ap.getErabiltzailea();
					double diruKopurua = ap.getDiruKop();
					er.saldoaAldatu(diruKopurua);
					Mugimendua g = new Mugimendua(er,diruKopurua,"gertaera_ezabatuta");
					db.persist(g);
					er.ezabatuApustua(ap);
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
	public Apustua apustuaEgin(Erabiltzailea er, Kuota ki, Double diruKop) {
		db.getTransaction().begin();
		String izena= er.getIzena();
		Erabiltzailea erDB= db.find(Erabiltzailea.class, izena);
		Kuota kDB=db.find(Kuota.class, ki.getKuotaZenbakia());
		if(erDB!=null) {
			Boolean nahikoa=erDB.diruaNahikoa(diruKop);
			if(nahikoa) {
				erDB.saldoaAldatu((-1)*diruKop);
				Mugimendua mugi= new Mugimendua(erDB, (-1)*diruKop, "Apustua eginda");
				db.persist(mugi);
				erDB.mugimenduaGehitu(mugi);
				Apustua apustua= new Apustua(erDB, diruKop, kDB);
				db.persist(apustua);
				kDB.apustuaGehitu(apustua);
				erDB.apustuaGehitu(apustua);
				db.getTransaction().commit();
				return apustua;
			}
			return null;
		}else {
			db.getTransaction().commit();
			return null;
		}
	}
	
	public boolean apustuaEzabatu(Apustua a) {
		db.getTransaction().begin();
		Apustua aDB= db.find(Apustua.class, a.getApustuZenbakia());
		if(aDB!=null) {
			if(aDB.ezabatuDaiteke()) {
				Erabiltzailea erDB= aDB.getErabiltzailea();
				Kuota kDB = aDB.getKuota();
				Question qDB= kDB.getQuestion();
				Event eDB= qDB.getEvent();
				Date date= new Date();
				Double diruKop = aDB.getDiruKop();
				erDB.saldoaAldatu(diruKop);
				Mugimendua m= new Mugimendua(erDB, diruKop, "Apustua Ezabatuta");
				db.persist(m);
				erDB.mugimenduaGehitu(m);
				erDB.apustuaEzabatuListatik(aDB);
				kDB.apustuaEzabatuListatik(aDB);
				db.remove(aDB);
				db.getTransaction().commit();
				return true;
			}
			
		}
		db.getTransaction().commit();
		return false;
	}
}
