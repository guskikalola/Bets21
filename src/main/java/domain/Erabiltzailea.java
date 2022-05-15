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
import javax.xml.bind.annotation.XmlIDREF;

@Entity @XmlAccessorType(XmlAccessType.FIELD)
public class Erabiltzailea extends Pertsona {
	
	private double saldoa;
	private Blokeoa blokeoa;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
	private List<Mugimendua> mugimenduak;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
	private List<Apustua> apustuaLista;
	
	//@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
	private List<Jarraitzen> jarraitzen;
	@XmlIDREF 
	private List<Erabiltzailea> jarraitzaileak;
	
	public Erabiltzailea() {
		super();
		this.mugimenduak = new ArrayList<Mugimendua>();
		this.apustuaLista= new ArrayList<Apustua>();
		this.jarraitzen = new ArrayList<Jarraitzen>();
		this.jarraitzaileak = new ArrayList<Erabiltzailea>();
		this.saldoa = 0;
		this.blokeoa=null;
		this.bidalitakoMezuak= new  ArrayList<Mezua>();
		this.jasotakoMezuak= new  ArrayList<Mezua>();
	}
	
	public Erabiltzailea(String izena, String pasahitza, Date jaiotzeData) {
		super(izena,pasahitza,jaiotzeData);
		this.mugimenduak = new ArrayList<Mugimendua>();
		this.apustuaLista= new ArrayList<Apustua>();
		this.jarraitzen = new ArrayList<Jarraitzen>();
		this.jarraitzaileak = new ArrayList<Erabiltzailea>();
		this.saldoa = 0;
		this.blokeoa= null;
		this.bidalitakoMezuak= new  ArrayList<Mezua>();
		this.jasotakoMezuak= new  ArrayList<Mezua>();
	}

	public Blokeoa getBlokeoa() {
		return blokeoa;
	}

	public void blokeoaGehitu(Blokeoa blokeoa) {
		this.blokeoa = blokeoa;
	}
	
	public void blokeoaEzabatu() {
		this.blokeoa= null;
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

	public List<Apustua> getApustuaLista() {
		return apustuaLista;
	}

	public void setApustuaLista(List<Apustua> apustuaLista) {
		this.apustuaLista = apustuaLista;
	}

	public List<Jarraitzen> getJarraitzen() {
		return jarraitzen;
	}

	public void setJarraitzen(List<Jarraitzen> jarraitzen) {
		this.jarraitzen = jarraitzen;
	}

	public List<Erabiltzailea> getJarraitzaileak() {
		return jarraitzaileak;
	}

	public void setJarraitzaileak(List<Erabiltzailea> jarraitzaileak) {
		this.jarraitzaileak = jarraitzaileak;
	}

	public int getApustuakIrabazitak() {
		int kop = 0;
		for(Apustua ap : this.apustuaLista) {
			if(ap.irabaziDu()) kop++;
		}
		return kop;
	}

	public Jarraitzen jarraitzenDu(Erabiltzailea nori) {
		if(nori == null) return null;
		for(Jarraitzen j : this.jarraitzen) {
			if(j.getNoriIzena().equals(nori.getIzena())) return j;
		}
		return null;
	}

	public boolean ezabatuJarraitzenListatik(Jarraitzen bJarraitu) {
		Iterator<Jarraitzen> it = this.jarraitzen.iterator();
		boolean ezabatuta = false;
		while(it.hasNext() && !ezabatuta) {
			Jarraitzen a = it.next();
			if(a.getJarraitzenZenbakia() == bJarraitu.getJarraitzenZenbakia()) {
				ezabatuta = true;
				it.remove();
			}
		}
		return ezabatuta;
	}
	
	public boolean ezabatuJarraitzaileakListatik(Erabiltzailea erabiltzailea) {
		Iterator<Erabiltzailea> it = this.jarraitzaileak.iterator();
		boolean ezabatuta = false;
		while(it.hasNext() && !ezabatuta) {
			Erabiltzailea a = it.next();
			if(a.getIzena().equals(erabiltzailea.getIzena())) {
				ezabatuta = true;
				it.remove();
			}
		}
		return ezabatuta;
	}

	public void gehituJarraitzenListara(Jarraitzen jB) {
		this.jarraitzen.add(jB);
	}

	public Jarraitzen jarraitu(Erabiltzailea erDB, float diruMax) {
		Jarraitzen j = new Jarraitzen(erDB, diruMax);
		this.gehituJarraitzenListara(j);		
		return j;
	}

	public void gehituJarraitzaileakListara(Erabiltzailea nor) {
		this.jarraitzaileak.add(nor);
	}

	public Mugimendua mugimenduaSortu(double d, String str) {
		Mugimendua m = new Mugimendua(this, d, str);
		this.mugimenduaGehitu(m);
		return m;
	}

	public Apustua apustuaSortu(Double diruKop, Kuota kDB) {
		Apustua ap = new Apustua(this, diruKop, kDB);
		this.apustuaGehitu(ap);
		return ap;
	}
	
	public Apustua apustuaSortu(double diruKop, List<Kuota> kDBLista) {
		Apustua apAnizkoitza = new Apustua(this, diruKop, kDBLista);
		this.apustuaGehitu(apAnizkoitza);
		return apAnizkoitza;
	}

	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		else if(!(other instanceof Erabiltzailea)) return false;
		else {
			Erabiltzailea oEr = (Erabiltzailea) other;
			return (this.getIzena().equals(oEr.getIzena()));
		}
	}


}
	
	

