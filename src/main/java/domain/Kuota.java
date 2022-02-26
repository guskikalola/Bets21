package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
public class Kuota {
	
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private int kuotaZenbakia;
	private double kantitatea;
	private String aukera;
	
	public Kuota (String aukera, double kantitatea) {
		this.aukera = aukera;
		this.kantitatea = kantitatea;
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
}
