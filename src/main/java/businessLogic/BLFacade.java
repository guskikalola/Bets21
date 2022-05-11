package businessLogic;

import java.util.Vector;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import domain.Booking;
import domain.Jarraitzen;
import domain.JarraitzenContainer;
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
	
	@WebMethod public Kuota ipiniKuota(Question q, String aukera, double kantitatea);
	
	@WebMethod public Apustua apustuaEgin(Erabiltzailea er, Kuota ki, Double diruKop) throws ApustuaEzDaEgin;
	@WebMethod public boolean apustuaEzabatu(Apustua a, Erabiltzailea er);
	@WebMethod public List<ApustuaContainer> getApustuakErabiltzailea(Kuota k,Erabiltzailea er);
	@WebMethod public boolean diruaSartu(Erabiltzailea erabiltzaile, String pasahitza, Double kantitatea);


	@WebMethod public List<Mugimendua> mugimenduakIkusi(Erabiltzailea er);


	@WebMethod public boolean removeEvent(Event ev);
	@WebMethod public List<Erabiltzailea> emaitzaIpini(Question q, Kuota k ) throws EmaitzaEzinIpini;


	@WebMethod List<Erabiltzailea> getErabiltzaileaGuztiak();
	@WebMethod List<Pertsona> getPertsonaGuztiak();


	@WebMethod boolean erabiltzaileaJarraitu(Erabiltzailea unekoErab, Erabiltzailea aukeratutakoErabiltzailea, float diruMax);


	@WebMethod Apustua apustuAnizkoitzaEgin(Erabiltzailea er, List<Kuota> kuotaLista, double diruKop) throws ApustuaEzDaEgin;


	// Web zerbitzaria dela eta, container ordez eskatu DB-tik datuak eskuratzea. DB-ak erreferentziak izango dituelako
	@WebMethod public Pertsona getPertsona(String izena);
	@WebMethod public Erabiltzailea getErabiltzailea(String izena);
	@WebMethod List<MezuaContainer> getMezuGuztiakContainer(Pertsona nor, Pertsona nori);
	@WebMethod int getApustuakIrabazitak(Erabiltzailea nori);
	
	
	@WebMethod public List<Mezua> getMezuGuztiak(Pertsona m, Pertsona nori);
	@WebMethod public Mezua mezuaBidali(Pertsona m, Pertsona nori, String mezua) throws MezuaEzDaZuzena;
	@WebMethod public Blokeoa erabiltzaileaBlokeatu(Admin a, Erabiltzailea ei, String arrazoia) throws MezuaEzDaZuzena;
	@WebMethod public BlokeoContainer getBlokeoContainer(Erabiltzailea e);

	@WebMethod public boolean gertaeraBikoiztu(Date data, String deskribapena, Event oldEvent);
	
	@WebMethod JarraitzenContainer jarraitzenDu(Erabiltzailea er, Erabiltzailea nori);
	@WebMethod List<JarraitzenContainer> getJarraitzen(Erabiltzailea er);
	
}
