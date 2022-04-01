package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Erabiltzailea extends Pertsona {
	
	private double saldoa;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
	private List<Mugimendua> mugimenduak;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
	private List<Apustua> apustuak;
	
	public Erabiltzailea() {
		super();
		this.mugimenduak = null;
		this.saldoa = 0;
	}
	
	public Erabiltzailea(String izena, String pasahitza, Date jaiotzeData) {
		super(izena,pasahitza,jaiotzeData);
		this.mugimenduak = new ArrayList<Mugimendua>();
		this.saldoa = 0;
	}

	public double getSaldoa() {
		return saldoa;
	}

	public void setSaldoa(double saldoa) {
		this.saldoa = saldoa;
	}

	public List<Mugimendua> getMugimenduak() {
		return mugimenduak;
	}

	public void setMugimenduak(List<Mugimendua> mugimenduak) {
		this.mugimenduak = mugimenduak;
	}

	public List<Apustua> getApustuak() {
		return apustuak;
	}

	public void setApustuak(List<Apustua> apustuak) {
		this.apustuak = apustuak;
	}

	public void saldoaAldatu(Double kantitatea) {
		this.saldoa += kantitatea;
	}

	public void mugimenduaGehitu(Mugimendua m) {
		this.mugimenduak.add(m);
	}

	public boolean ezabatuApustua(Apustua ap) {
		Iterator<Apustua> it = this.apustuak.iterator();
		boolean ezabatuta = false;
		while(it.hasNext() && !ezabatuta) {
			Apustua a = it.next();
			if(a.getApustuZenbakia() == ap.getApustuZenbakia()) {
				ezabatuta = true;
				it.remove();
			}
		}
		return ezabatuta;
		
	}
	
}
