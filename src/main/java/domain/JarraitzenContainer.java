package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class JarraitzenContainer {
	Jarraitzen jarraitzen;
	Erabiltzailea nori;
	
	public JarraitzenContainer() {
		jarraitzen = null;
		nori = null;
	}
	
	public JarraitzenContainer(Jarraitzen j) {
		this.jarraitzen = j;
		this.nori = j.getNori();
	}

	public Jarraitzen getJarraitzen() {
		return jarraitzen;
	}

	public void setJarraitzen(Jarraitzen jarraitzen) {
		this.jarraitzen = jarraitzen;
	}

	public Erabiltzailea getNori() {
		return nori;
	}

	public void setNori(Erabiltzailea nori) {
		this.nori = nori;
	}
}
