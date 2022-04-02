package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class KuotaContainer {
	Kuota kuota;
	Question question;
	
	public KuotaContainer() {
		kuota = null;
		question = null;
	}
	
	public KuotaContainer(Kuota k) {
		kuota = k;
		question = k.getQuestion();
	}
	public Kuota getKuota() {
		return kuota;
	}
	public void setKuota(Kuota kuota) {
		this.kuota = kuota;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
}
