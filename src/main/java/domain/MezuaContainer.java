package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class MezuaContainer implements Comparable<MezuaContainer> {
	Mezua m;
	Pertsona nori;
	Pertsona nor;
	
	public MezuaContainer() {
		nor = null;
		nori = null;
	}
	
	public MezuaContainer(Mezua m) {
		this.m = m;
		this.nori = m.getNori();
		this.nor=m.getNor();
	}

	public Mezua getM() {
		return m;
	}

	public void setM(Mezua m) {
		this.m = m;
	}

	public Pertsona getNori() {
		return nori;
	}

	public void setNori(Pertsona nori) {
		this.nori = nori;
	}

	public Pertsona getNor() {
		return nor;
	}

	public void setNor(Pertsona nor) {
		this.nor = nor;
	}

	@Override
	public int compareTo(MezuaContainer o) {
		return this.getM().getMezuaZenbakia().compareTo(o.getM().getMezuaZenbakia());
	}

	

}
