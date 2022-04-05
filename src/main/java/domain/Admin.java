package domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


@Entity @XmlAccessorType(XmlAccessType.FIELD)
public class Admin extends Pertsona {
	
	public Admin() {
		super();
	}
	
	public Admin(String izena, String pasahitza, Date jaiotzeData) {
		super(izena,pasahitza,jaiotzeData);
	}
}
