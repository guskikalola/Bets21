package domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSeeAlso;

@Entity @XmlAccessorType(XmlAccessType.FIELD) @XmlSeeAlso ({Erabiltzailea.class, Admin.class})
public abstract class Pertsona {
	@Id @XmlID
	private String izena;
	private String pasahitza;
	private int adina;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
	private HashMap<String, ArrayList<Mezua>> jasotakoMezuak;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
	private HashMap<String, ArrayList<Mezua>> bidalitakoMezuak;

	public Pertsona() {
		this.pasahitza = null;
		this.izena = null;
		this.adina = -1;
		this.jasotakoMezuak= new HashMap<String, ArrayList<Mezua>>();
		this.bidalitakoMezuak= new HashMap<String, ArrayList<Mezua>>();
	}

	public Pertsona(String izena, String pasahitza, int adina) {
		this.izena = izena; 
		this.pasahitza = pasahitza;
		this.adina = adina;
		this.jasotakoMezuak= new HashMap<String, ArrayList<Mezua>>();
		this.bidalitakoMezuak= new HashMap<String, ArrayList<Mezua>>();
	}

	public HashMap<String, ArrayList<Mezua>> getJasotakoMezuak() {
		return jasotakoMezuak;
	}

	public void setJasotakoMezuak(HashMap<String, ArrayList<Mezua>> jasotakoMezuak) {
		this.jasotakoMezuak = jasotakoMezuak;
	}

	public HashMap<String, ArrayList<Mezua>> getBidalitakoMezuak() {
		return bidalitakoMezuak;
	}

	public void setBidalitakoMezuak(HashMap<String, ArrayList<Mezua>> bidalitakoMezuak) {
		this.bidalitakoMezuak = bidalitakoMezuak;
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
	
	public void gehituBidaliLista(Mezua me, Pertsona p) {
		ArrayList<Mezua> mezuList=null;
		if(!bidalitakoMezuak.containsKey(p.getIzena())) {
			mezuList.add(me);
			this.bidalitakoMezuak.put(this.getIzena(), mezuList);
		}else {
			mezuList= this.bidalitakoMezuak.get(p.getIzena());
			mezuList.add(me);
			this.bidalitakoMezuak.put(this.getIzena(), mezuList);
		}
	}
	
	public void gehituJasotakoLista(Mezua me, Pertsona p) {
		ArrayList<Mezua> mezuList=null;
		if(!jasotakoMezuak.containsKey(p.getIzena())) {
			mezuList.add(me);
			this.jasotakoMezuak.put(this.getIzena(), mezuList);
		}else {
			mezuList= this.jasotakoMezuak.get(p.getIzena());
			mezuList.add(me);
			this.jasotakoMezuak.put(this.getIzena(), mezuList);
		}
		
		
	}
	
	public List<Mezua> jasotakoMezuakEskuratu(Pertsona norengandik) {
		List<Mezua> jasotakoMezuList= this.bidalitakoMezuak.get(norengandik.getIzena());
		return jasotakoMezuList;
	}
	
	public List<Mezua> BidalitakoMezuakEskuratu(Pertsona nori) {
		List<Mezua> bidalitakoMezuList= this.bidalitakoMezuak.get(nori.getIzena());
		return bidalitakoMezuList;
	}

}
