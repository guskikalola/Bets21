package domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ApustuaContainer {
	Apustua apustua;
	List<Kuota> kuotak;
	
	public ApustuaContainer() {
		apustua = null;
		kuotak = null;
	}

	public ApustuaContainer(Apustua a) {
		apustua = a;
		kuotak = a.getKuotak();
	}

	public Apustua getApustua() {
		return apustua;
	}

	public void setApustua(Apustua apustua) {
		this.apustua = apustua;
	}

	public List<Kuota> getKuotak() {
		return kuotak;
	}

	public void setKuotak(List<Kuota> kuotak) {
		this.kuotak = kuotak;
	}
}
