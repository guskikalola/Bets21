package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class MugimenduaContainer {
	
	Mugimendua mugimendua;
	Erabiltzailea erabiltzailea;
	
	public MugimenduaContainer() {
		this.mugimendua = null;
		this.erabiltzailea = null;
	}
	
	public MugimenduaContainer(Mugimendua m) {
		this.mugimendua = m;
		this.erabiltzailea = m.getErabiltzailea();
	}

	public Mugimendua getMugimendua() {
		return mugimendua;
	}

	public void setMugimendua(Mugimendua mugimendua) {
		this.mugimendua = mugimendua;
	}

	public Erabiltzailea getErabiltzailea() {
		return erabiltzailea;
	}

	public void setErabiltzailea(Erabiltzailea erabiltzailea) {
		this.erabiltzailea = erabiltzailea;
	}

}
