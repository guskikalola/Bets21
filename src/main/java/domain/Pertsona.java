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
	protected ArrayList<Mezua> jasotakoMezuak;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
	protected ArrayList<Mezua> bidalitakoMezuak;

	public Pertsona() {
		this.pasahitza = null;
		this.izena = null;
		this.adina = -1;
		this.jasotakoMezuak= new  ArrayList<Mezua>();
		this.bidalitakoMezuak= new ArrayList<Mezua>();
	}

	public Pertsona(String izena, String pasahitza, int adina) {
		this.izena = izena; 
		this.pasahitza = pasahitza;
		this.adina = adina;
		this.jasotakoMezuak= new ArrayList<Mezua>();
		this.bidalitakoMezuak= new ArrayList<Mezua>();
	}

	public ArrayList<Mezua> getJasotakoMezuak() {
		return jasotakoMezuak;
	}

	public void setJasotakoMezuak(ArrayList<Mezua> jasotakoMezuak) {
		this.jasotakoMezuak = jasotakoMezuak;
	}

	public ArrayList<Mezua> getBidalitakoMezuak() {
		return bidalitakoMezuak;
	}

	public void setBidalitakoMezuak(ArrayList<Mezua> bidalitakoMezuak) {
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
	
	public void gehituBidaliLista(Mezua me) {
		this.bidalitakoMezuak.add(me);
	}
	
	public void gehituJasotakoLista(Mezua me) {
		this.jasotakoMezuak.add(me);
	}
	
	public ArrayList<Mezua> jasotakoMezuakEskuratu(Pertsona norengandik) {
		ArrayList<Mezua> mezuList= new  ArrayList<Mezua>();
		if(this.jasotakoMezuak!=null) {
			for(Mezua m: this.jasotakoMezuak) {
				if(m.getNor().getIzena().equals(norengandik.getIzena())) {
					mezuList.add(m);
				}
			}
		}
		return mezuList;
	}
	
	public ArrayList<Mezua> BidalitakoMezuakEskuratu(Pertsona nori) {
		ArrayList<Mezua> mezuList= new  ArrayList<Mezua>();
		if(this.bidalitakoMezuak!=null) {
			for(Mezua m: this.bidalitakoMezuak) {
				if(m.getNori().getIzena().equals(nori.getIzena())) {
					mezuList.add(m);
				}
			}
		}
		return mezuList;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		else if(!(other instanceof Pertsona)) return false;
		else {
			Pertsona oEr = (Pertsona) other;
			return (this.getIzena().equals(oEr.getIzena()));
		}
	}

}
