package businessLogic;

import java.util.Vector;
import java.util.Date;
import java.util.List;

//import domain.Booking;
import domain.Question;
import domain.QuestionContainer;
import domain.Apustua;
import domain.ApustuaContainer;
import domain.Erabiltzailea;
import domain.Event;
import domain.Kuota;
import domain.KuotaContainer;
import domain.Mugimendua;
import domain.MugimenduaContainer;
import domain.Pertsona;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.swing.JFrame;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  

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
	@WebMethod Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;
	
	
	/**
	 * This method retrieves the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();
	
	@WebMethod public Pertsona existitzenDa(String izena, String pasahitza);
	
	@WebMethod public Pertsona erregistratu(String izena, String pasahitza, Date jaiotzeData);
	
	@WebMethod public Event sortuGertaera(Date data, String deskribapena);
	
	@WebMethod public KuotaContainer ipiniKuota(QuestionContainer q, String aukera, double kantitatea);
	
	@WebMethod public Pertsona getLoginErabiltzailea();
	@WebMethod public void setLoginErabiltzailea(Pertsona er);
	@WebMethod public ApustuaContainer apustuaEgin(Erabiltzailea er, KuotaContainer ki, Double diruKop);
	@WebMethod public boolean apustuaEzabatu(ApustuaContainer a);
	
	@WebMethod public boolean diruaSartu(Erabiltzailea erabiltzaile, String pasahitza, Double kantitatea);


	@WebMethod public List<MugimenduaContainer> mugimenduakIkusi(Erabiltzailea er);


	@WebMethod public boolean removeEvent(Event ev);
	@WebMethod public List<Erabiltzailea> emaitzaIpini(QuestionContainer q, KuotaContainer k );
	
}
