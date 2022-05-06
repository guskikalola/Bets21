package domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity @XmlAccessorType(XmlAccessType.FIELD)
public class Blokeoa {
	
	@Id @XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer blokeoZbkia;
	
	private static final int ARRAZOIAMIN=10;
	private static final int ARRAZOIAMAX=150;
	
	@XmlIDREF
	private Admin Nor;
	@XmlIDREF
	private Erabiltzailea Nori;
	
	private String Arrazoia;	
	private Date data;
	
	public Blokeoa() {
		this.Nor = null;
		this.Nori = null;
	}
	
	public Blokeoa(Admin nor, Erabiltzailea nori, String Arrazoia) {
		this.Nor=nor;
		this.Nori=nori;
		this.Arrazoia=Arrazoia;
		this.data= new Date();
	}

	public Integer getBlokeoZbkia() {
		return blokeoZbkia;
	}

	public void setBlokeoZbkia(Integer blokeoZbkia) {
		this.blokeoZbkia = blokeoZbkia;
	}

	public Admin getNor() {
		return Nor;
	}

	public void setNor(Admin nor) {
		Nor = nor;
	}

	public Erabiltzailea getNori() {
		return Nori;
	}

	public void setNori(Erabiltzailea nori) {
		Nori = nori;
	}

	public String getArrazoia() {
		return Arrazoia;
	}

	public void setArrazoia(String arrazoia) {
		Arrazoia = arrazoia;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public boolean arrazoiaZuzenaDa(String arrazoia) {
		if(arrazoia.length()>=ARRAZOIAMIN && arrazoia.length()<=ARRAZOIAMAX){
			return true;
		}
		return false;
	}
	

}
