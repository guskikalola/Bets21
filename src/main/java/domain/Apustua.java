package domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
public class Apustua {
	
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private int apustuZenbakia;
	@XmlIDREF
	private Erabiltzailea erabiltzailea;
	@XmlIDREF
	private Kuota kuota;
	private double diruKop;
	
	public Apustua() {
		this.erabiltzailea = null;
		this.kuota = null;
	}
	
	public Apustua(Erabiltzailea er, double diruKop, Kuota kuota) {
		this.erabiltzailea = er;
		this.diruKop = diruKop;
		this.kuota = kuota;
	}

	public int getApustuZenbakia() {
		return apustuZenbakia;
	}

	public void setApustuZenbakia(int apustuZenbakia) {
		this.apustuZenbakia = apustuZenbakia;
	}

	public Erabiltzailea getErabiltzailea() {
		return erabiltzailea;
	}

	public void setErabiltzailea(Erabiltzailea erabiltzailea) {
		this.erabiltzailea = erabiltzailea;
	}

	public Kuota getKuota() {
		return kuota;
	}

	public void setKuota(Kuota kuota) {
		this.kuota = kuota;
	}

	public double getDiruKop() {
		return diruKop;
	}

	public void setDiruKop(double diruKop) {
		this.diruKop = diruKop;
	}
	
	public boolean ezabatuDaiteke() {
		Calendar gaur = Calendar.getInstance();
		Date gaurkoData = gaur.getTime();
		Event gertaera = this.kuota.getQuestion().getEvent();
		Date gertaeraData = gertaera.getEventDate();
		
		// Soilik ezabatu daiteke gaur gertaera data baino lehen bada
		return gaurkoData.compareTo(gertaeraData) < 0;
	}
}