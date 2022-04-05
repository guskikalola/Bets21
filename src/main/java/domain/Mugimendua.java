package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity @XmlAccessorType(XmlAccessType.FIELD)
public class Mugimendua {
	@Id @XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer mugimenduZbkia;
	private double kantitatea;
	private String arrazoia;
	@XmlIDREF
	private Erabiltzailea erabiltzailea;
	
	public Mugimendua() {
		this.erabiltzailea = null;
		this.kantitatea = 0;
		this.arrazoia = null;
	}
	
	public Mugimendua(Erabiltzailea erabiltzailea, double kantitatea, String arrazoia) {
		this.erabiltzailea = erabiltzailea;
		this.kantitatea = kantitatea;
		this.arrazoia = arrazoia;
	}
	
	public int getMugimenduZbkia() {
		return mugimenduZbkia;
	}
	public void setMugimenduZbkia(int mugimenduZbkia) {
		this.mugimenduZbkia = mugimenduZbkia;
	}
	public double getKantitatea() {
		return kantitatea;
	}
	public void setKantitatea(double kantitatea) {
		this.kantitatea = kantitatea;
	}
	public String getArrazoia() {
		return arrazoia;
	}
	public void setArrazoia(String arrazoia) {
		this.arrazoia = arrazoia;
	}
	public Erabiltzailea getErabiltzailea() {
		return erabiltzailea;
	}
	public void setErabiltzailea(Erabiltzailea erabiltzailea) {
		this.erabiltzailea = erabiltzailea;
	}
	
}
