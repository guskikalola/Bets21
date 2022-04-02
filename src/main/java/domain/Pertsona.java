package domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@Entity @XmlAccessorType(XmlAccessType.FIELD)
public class Pertsona implements Serializable {
	@Id @XmlID
	private String izena;
	private String pasahitza;
	private int adina;

	public Pertsona() {
		this.pasahitza = null;
		this.izena = null;
		this.adina = -1;
	}

	public Pertsona(String izena, String pasahitza, int adina) {
		this.izena = izena;
		this.pasahitza = pasahitza;
		this.adina = adina;
	}

	public Pertsona(String izena, String pasahitza, Date jaiotzeData) {
		Calendar gaur = Calendar.getInstance();
		int urteDif = Math.abs(gaur.get(Calendar.YEAR) - jaiotzeData.getYear());
		int hilbDif = gaur.get(Calendar.MONTH) - jaiotzeData.getMonth();

		int hilabKop = urteDif * 12 + (hilbDif > 0 ? hilbDif : 0);

		int adina = hilabKop / 12;
		
		this.izena = izena;
		this.pasahitza = pasahitza;
		this.adina = adina;

		
	}

	public String toString() {
		return "Izena: " + this.izena + " Adina: " + this.adina;
	}

	public String getIzena() {
		return izena;
	}

	public void setIzena(String izena) {
		this.izena = izena;
	}

	public String getPasahitza() {
		return pasahitza;
	}

	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}

	public int getAdina() {
		return adina;
	}

	public void setAdina(int adina) {
		this.adina = adina;
	}

	public boolean pasahitzaZuzena(String pasahitza) {
		return (pasahitza.equals(this.pasahitza));
	}

}
