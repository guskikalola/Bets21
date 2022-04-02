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
	private List<Apustua> apustuaLista;
	
	public Erabiltzailea() {
		super();
		this.mugimenduak = null;
		this.apustuaLista= new ArrayList<Apustua>();
		this.saldoa = 0;
	}
	
	public Erabiltzailea(String izena, String pasahitza, Date jaiotzeData) {
		super(izena,pasahitza,jaiotzeData);
		this.mugimenduak = new ArrayList<Mugimendua>();
		this.apustuaLista= new ArrayList<Apustua>();
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
		return apustuaLista;
	}

	public void setApustuak(List<Apustua> apustuak) {
		this.apustuaLista = apustuak;
	}

	public boolean apustuaEzabatuListatik(Apustua ap) {
		Iterator<Apustua> it = this.apustuaLista.iterator();
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
	
	public boolean diruaNahikoa(Double diruKop) {
		if((this.getSaldoa()-diruKop)>=0) {
			return true;
		}else {
			return false;
		}
	}
	
	public void saldoaAldatu(Double diruKop) {
		this.setSaldoa(this.getSaldoa()+diruKop);
	}

	
	public void mugimenduaGehitu(Mugimendua m) {
		this.mugimenduak.add(m);
	}
	
	public void apustuaGehitu(Apustua a) {
		this.apustuaLista.add(a);
	}
	
	public void saldoaAldatu(double irabaziDirua) {
		
		double dirua = irabaziDirua + this.getSaldoa();
		this.setSaldoa(dirua);
	}
	
	public void gehituMugimendua(Mugimendua m) {
		this.mugimenduak.add(m);
	}
}
	
	

