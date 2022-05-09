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
public class Mezua {
	
	@Id @XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer MezuaZenbakia;

	public static final int MEZUAMIN=2;
	public static final int MEZUAMAX=100;
	
	@XmlIDREF
	private Pertsona Nor;
	@XmlIDREF
	private Pertsona Nori;
	
	private String Mezua;	
	private boolean irakurrita;
	private Date data;
	
	public Mezua() {
		this.Nor = null;
		this.Nori = null;
	}
	
	public Mezua(Pertsona nor, Pertsona nori, String Mezua) {
		this.Nor=nor;
		this.Nori=nori;
		this.Mezua=Mezua;
		this.irakurrita=false;
		this.data= new Date();
	}

	public Pertsona getNor() {
		return this.Nor;
	}

	public void setNor(Pertsona nor) {
		this.Nor = nor;
	}

	public Pertsona getNori() {
		return this.Nori;
	}

	public void setNori(Pertsona nori) {
		this.Nori = nori;
	}

	public String getMezua() {
		return Mezua;
	}

	public void setMezua(String mezua) {
		Mezua = mezua;
	}

	public boolean isIrakurrita() {
		return irakurrita;
	}

	public void setIrakurrita(boolean irakurrita) {
		this.irakurrita = irakurrita;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public Integer getMezuaZenbakia() {
		return MezuaZenbakia;
	}

	public void setMezuaZenbakia(Integer mezuaZenbakia) {
		MezuaZenbakia = mezuaZenbakia;
	}

	public static int getMezuamin() {
		return MEZUAMIN;
	}

	public static int getMezuamax() {
		return MEZUAMAX;
	}
	
	public static boolean mezuaZuzenaDaMIN(String mezua) {
		if(mezua.length()>=MEZUAMIN){
			return true;
		}
		return false;
	}
	public static boolean mezuaZuzenaDaMAX(String mezua) {
		if(mezua.length()<=MEZUAMAX) {
			return true;
		}
		return false;
	}
	
	
	
	

}
