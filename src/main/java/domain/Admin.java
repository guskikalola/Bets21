package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


@Entity @XmlAccessorType(XmlAccessType.FIELD)
public class Admin extends Pertsona {
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
	private List<Blokeoa> blokeoak;
	
	public Admin() {
		super();
		this.bidalitakoMezuak= new  ArrayList<Mezua>();
		this.jasotakoMezuak= new  ArrayList<Mezua>();
		this.blokeoak= new ArrayList<Blokeoa>();
	}
	
	public Admin(String izena, String pasahitza, Date jaiotzeData) {
		super(izena,pasahitza,jaiotzeData);
		this.bidalitakoMezuak= new  ArrayList<Mezua>();
		this.jasotakoMezuak= new  ArrayList<Mezua>();
		this.blokeoak= new ArrayList<Blokeoa>();
	}
	
	public Admin(String izena, String pasahitza, Date jaiotzeData, Blokeoa bl) {
		super(izena,pasahitza,jaiotzeData);
		this.blokeoak.add(bl);
		this.bidalitakoMezuak= new  ArrayList<Mezua>();
		this.jasotakoMezuak= new  ArrayList<Mezua>();
	}
	
	public Admin(String izena, String pasahitza, Date jaiotzeData, List<Blokeoa> blList) {
		super(izena,pasahitza,jaiotzeData);
		this.blokeoak= blList;
		this.bidalitakoMezuak= new  ArrayList<Mezua>();
		this.jasotakoMezuak= new  ArrayList<Mezua>();
	}

	public List<Blokeoa> getBlokeoak() {
		return blokeoak;
	}

	public void setBlokeoak(List<Blokeoa> blokeoak) {
		this.blokeoak = blokeoak;
	}
	
	public void blokeoaGehituListan(Blokeoa bl) {
		this.blokeoak.add(bl);
	}
	
	public void blokeoaEzabatuListatik(Blokeoa blDB) {
		Iterator<Blokeoa> it = this.blokeoak.iterator();
		boolean ezabatuta = false;
		while(it.hasNext() && !ezabatuta) {
			Blokeoa bl = it.next();
			if(bl.equals(blDB)) {
				ezabatuta = true;
				it.remove();
			}
		}
	}
	
}
