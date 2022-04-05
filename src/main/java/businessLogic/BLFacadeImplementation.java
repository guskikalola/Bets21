package businessLogic;
//hola
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
import domain.Apustua;
import domain.Erabiltzailea;
import domain.Event;
import domain.Kuota;
import domain.Mugimendua;
import domain.Pertsona;
import exceptions.ApustuaEzDaEgin;
import exceptions.EmaitzaEzinIpini;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;
	private Pertsona loginErabiltzailea;
	

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
		    dbManager=new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
		    dbManager.initializeDB();
		    } else
		     dbManager=new DataAccess();
		dbManager.close();

		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		
		
		dbManager=da;		
	}
	

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
   @WebMethod
   public Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist{
	   
	    //The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry=null;
		
	    
		if(new Date().compareTo(event.getEventDate())>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
				
		
		 qry=dbManager.createQuestion(event,question,betMinimum);		

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
	public Vector<Event> getEvents(Date date)  {
		dbManager.open(false);
		Vector<Event>  events=dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

    
	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date>  dates=dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}
	
	
	public void close() {
		DataAccess dB4oManager=new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}

	@Override @WebMethod
	public Pertsona existitzenDa(String izena, String pasahitza) {
		dbManager.open(false);
		Pertsona e = dbManager.getErabiltzailea(izena);
		dbManager.close();
		if(e != null && e.getPasahitza().equals(pasahitza)) {
			return e;
		} 
		return null;
	}

	@Override @WebMethod
	public Pertsona erregistratu(String izena, String pasahitza, Date jaiotzeData) {
		dbManager.open(false);
		Pertsona e = dbManager.erregistratu(izena,pasahitza,jaiotzeData);
		dbManager.close();
		return e;
	}
	
	
	@Override @WebMethod
	public Event sortuGertaera(Date data, String deskribapena) {
		dbManager.open(false);
		Event event = dbManager.sortuGertaera(data, deskribapena);
		dbManager.close();
		return event;
	}

	@Override @WebMethod
	public Kuota ipiniKuota(Question q, String aukera, double kantitatea) {
		dbManager.open(false);
		Kuota k = dbManager.ipiniKuota(q,aukera,kantitatea);
		dbManager.close();
		return k;
	}

	@Override @WebMethod
	public Pertsona getLoginErabiltzailea() {
		dbManager.open(false);
		Pertsona er = dbManager.getErabiltzailea(this.loginErabiltzailea.getIzena());
		dbManager.close();
		return er;
	}
	@WebMethod
	public void setLoginErabiltzailea(Pertsona er) {
		this.loginErabiltzailea = er;
	}


	@Override @WebMethod
	public Apustua apustuaEgin(Erabiltzailea er, Kuota ki, Double diruKop) throws ApustuaEzDaEgin {
		dbManager.open(false);
		Apustua a = dbManager.apustuaEgin(er, ki, diruKop);
		dbManager.close();
		return a;
	}
	
	@Override @WebMethod
	public boolean apustuaEzabatu(Apustua a) {
		dbManager.open(false);
		Boolean bool=dbManager.apustuaEzabatu(a);
		dbManager.close();
		return bool;
	}
	

	@Override @WebMethod
	public boolean diruaSartu(Erabiltzailea erabiltzaile, String pasahitza, Double kantitatea) {
		dbManager.open(false);
		boolean em = dbManager.diruaSartu(erabiltzaile, pasahitza, kantitatea);
		dbManager.close();
		return em;
	}

	@Override @WebMethod
	public List<Mugimendua> mugimenduakIkusi(Erabiltzailea er) {
		dbManager.open(false);
		List<Mugimendua> m = dbManager.mugimenduakIkusi(er);
		dbManager.close();
		return m;
	}

	@Override @WebMethod
	public boolean removeEvent(Event ev) {
		dbManager.open(false);
		boolean ezabatuta = dbManager.removeEvent(ev);
		dbManager.close();
		return ezabatuta;
	}

	
	@Override @WebMethod
	public List<Erabiltzailea> emaitzaIpini(Question q, Kuota k) throws EmaitzaEzinIpini{
		dbManager.open(false);
		List<Erabiltzailea> er = dbManager.emaitzaIpini(q, k);
		dbManager.close();
		return er;
		
	}
}

