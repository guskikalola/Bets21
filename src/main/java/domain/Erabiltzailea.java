package domain;

import java.util.Date;
import javax.persistence.*;

@Entity
public class Erabiltzailea {
	@Id
	private String izena;
	private String rola;
	private String pasahitza;
	private int adina;
	
	public final static String ADMIN = "admin";
	public final static String ERABILTZAILEA = "erabiltzailea";
	
	public Erabiltzailea(String izena, String pasahitza, Date jaiotzeData, String rola) {
		this.izena = izena;
		this.pasahitza = pasahitza;
		
		Date gaur = new Date();
		int urteDif = gaur.getYear() - jaiotzeData.getYear();
		int hilbDif = gaur.getMonth() - jaiotzeData.getMonth();
		
		int hilabKop = (urteDif > 0 ? urteDif*12 : 0) + (hilbDif > 0 ? hilbDif : 0);
		
		this.adina = hilabKop / 12;
		
		this.rola = rola;
	}
	
	public Erabiltzailea(String izena, String pasahitza, Date jaiotzeData) {
		this(izena,pasahitza,jaiotzeData,Erabiltzailea.ERABILTZAILEA);
	}

	public String getIzena() {
		return izena;
	}

	public void setIzena(String izena) {
		this.izena = izena;
	}

	public String getRola() {
		return rola;
	}

	public void setRola(String rola) {
		this.rola = rola;
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
	
	public String toString() {
		return "Izena: " + this.izena + " Adina: " + this.adina + " Rola: " + this.rola;
	}
}
