package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
public class Kuota {
	
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private int kuotaZenbakia;
	private double kantitatea;
	@XmlIDREF
	private Question question;
	private String aukera;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
	private List<Apustua> apustuak;
	
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

	public void setApustuak(List<Apustua> apustuak) {
		this.apustuak = apustuak;
	}
}
