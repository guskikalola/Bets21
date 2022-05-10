package domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity @XmlAccessorType(XmlAccessType.FIELD)
public class Kuota {
	
	@Id @XmlID 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer kuotaZenbakia;
	private double kantitatea;
	@XmlIDREF
	private Question question;
	private String aukera;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
	private List<Apustua> apustuak;
	
	public Kuota() {
		this.aukera = null;
		this.kantitatea = 0;
		this.apustuak = null;
		this.question = null;
	}
	
	public Kuota (String aukera, double kantitatea,Question galdera) {
		this.aukera = aukera;
		this.kantitatea = kantitatea;
		this.apustuak = new ArrayList<Apustua>();
		this.question = galdera;
	}

	public int getKuotaZenbakia() {
		return kuotaZenbakia;
	}

	public double getKantitatea() {
		return kantitatea;
	}

	public void setKantitatea(double kantitatea) {
		this.kantitatea = kantitatea;
	}

	public String getAukera() {
		return aukera;
	}

	public void setAukera(String aukera) {
		this.aukera = aukera;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public List<Apustua> getApustuak() {
		return apustuak;
	}
	
	public List<Apustua> getApustuak(Erabiltzailea er) {
		List<Apustua> em = new ArrayList<Apustua>();
		for(Apustua ap : this.apustuak) {
			if(ap.getErabiltzailea().getIzena().equals(er.getIzena())) em.add(ap);
		}
		return em;
	}

	public void setApustuak(List<Apustua> apustuak) {
		this.apustuak = apustuak;
	}
	
	public void apustuaGehitu(Apustua a) {
		this.apustuak.add(a);
	}
	
	public void apustuaEzabatuListatik(Apustua a) {

		Iterator<Apustua> it = this.apustuak.iterator();
		while(it.hasNext()) {
			Apustua ap= it.next();
			if(ap.equals(a)) {
				it.remove();
			}
		}
	}

	public boolean galderaEmaitzaDu() {
		return (this.getQuestion().emaitzaDu());
	}
}
