package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Jarraitzen {
	
	@Id
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer jarraitzenZenbakia;
	private float diruKop;
	@XmlIDREF
	private Erabiltzailea nori;
	
	public Jarraitzen()
	{
		this.jarraitzenZenbakia = 0;
		this.diruKop = 0f;
		this.nori = null;
	}
	
	public Jarraitzen(Erabiltzailea nori, float diruKop) {
		this.diruKop = diruKop;
		this.nori = nori;
	}

	public float getDiruKop() {
		return diruKop;
	}

	public void setDiruKop(float diruKop) {
		this.diruKop = diruKop;
	}

	public Erabiltzailea getNori() {
		return nori;
	}

	public void setNori(Erabiltzailea nori) {
		this.nori = nori;
	}

	public int getJarraitzenZenbakia() {
		return jarraitzenZenbakia;
	}

	public void setJarraitzenZenbakia(int jarraitzenZenbakia) {
		this.jarraitzenZenbakia = jarraitzenZenbakia;
	}

	public void setJarraitzenZenbakia(Integer jarraitzenZenbakia) {
		this.jarraitzenZenbakia = jarraitzenZenbakia;
	}

	public String getNoriIzena() {
		return this.getNori().getIzena();
	}

	public boolean baldintzakBetetzenDitu(Apustua apustua) {
		double diruKop = apustua.getDiruKop();
		return this.diruKop >= diruKop;
	}
}
