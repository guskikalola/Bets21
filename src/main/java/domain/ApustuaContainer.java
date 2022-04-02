package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ApustuaContainer {
	Apustua apustua;
	Kuota kuota;
	Erabiltzailea erabiltzailea;
	
	public ApustuaContainer() {
		apustua = null;
		kuota = null;
		erabiltzailea = null;
	}
	
	public ApustuaContainer(Apustua ap) {
		apustua = ap;
		kuota = ap.getKuota();
		erabiltzailea = ap.getErabiltzailea();
	}

	public Apustua getApustua() {
		return apustua;
	}

	public void setApustua(Apustua apustua) {
		this.apustua = apustua;
	}

	public Kuota getKuota() {
		return kuota;
	}

	public void setKuota(Kuota kuota) {
		this.kuota = kuota;
	}

	public Erabiltzailea getErabiltzailea() {
		return erabiltzailea;
	}

	public void setErabiltzailea(Erabiltzailea erabiltzailea) {
		this.erabiltzailea = erabiltzailea;
	}
}
