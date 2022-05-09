package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class BlokeoContainer {
	Blokeoa bl;
	Admin nor;
	Erabiltzailea nori;
	
	public BlokeoContainer() {
		bl = null;
		nor= null;
		nori = null;
	}
	
	public BlokeoContainer(Blokeoa bl) {
		this.bl = bl;
		this.nor= bl.getNor();
		this.nori = bl.getNori();
	}

	public Blokeoa getBl() {
		return bl;
	}

	public void setBl(Blokeoa bl) {
		this.bl = bl;
	}

	public Admin getNor() {
		return nor;
	}

	public void setNor(Admin nor) {
		this.nor = nor;
	}

	public Erabiltzailea getNori() {
		return nori;
	}

	public void setNori(Erabiltzailea nori) {
		this.nori = nori;
	}

}