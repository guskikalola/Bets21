package domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Apustua {

	@Id
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer apustuZenbakia;
	@XmlIDREF
	private Erabiltzailea erabiltzailea;
	@XmlIDREF @OneToMany(fetch=FetchType.EAGER)
	private List<Kuota> kuotak;
	private double diruKop;

	public Apustua() {
		this.erabiltzailea = null;
		this.kuotak = null;
	}

	public Apustua(Erabiltzailea er, double diruKop, Kuota kuota) {
		this.erabiltzailea = er;
		this.diruKop = diruKop;
		this.kuotak = new ArrayList<Kuota>();
		this.kuotak.add(kuota);
	}
	
	public Apustua(Erabiltzailea er, double diruKop, List<Kuota> kuotak) {
		this.erabiltzailea = er;
		this.diruKop = diruKop;
		this.kuotak = kuotak;
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

	public List<Kuota> getKuotak() {
		return kuotak;
	}

	public void setKuota(List<Kuota> kuota) {
		this.kuotak = kuota;
	}

	public double getDiruKop() {
		return diruKop;
	}
	
	public double getIrabazia() {
		double biderK = 0;
		for(Kuota k : this.kuotak) {
			biderK += k.getKantitatea();
		}
		return biderK * this.diruKop;
	}

	public void setDiruKop(double diruKop) {
		this.diruKop = diruKop;
	}

	public boolean ezabatuDaiteke() {
		for (Kuota k : this.kuotak) {
			Calendar gaur = Calendar.getInstance();
			Date gaurkoData = gaur.getTime();
			Event gertaera = k.getQuestion().getEvent();
			Date gertaeraData = gertaera.getEventDate();

			// Soilik ezabatu daiteke gaur gertaera data baino lehen bada eta emaitza ez
			// bada jarri
			if (!(gaurkoData.compareTo(gertaeraData) < 0 && k.getQuestion().getResult() == null)) {
				return false;
			}
		}
		return true;
	}

	public boolean irabaziDu() {
		for (Kuota k : this.kuotak) {
			if (k.getQuestion().getResult() == null || !(k.getAukera().equals(k.getQuestion().getResult())))
				return false;
		}
		return true;
	}
}
