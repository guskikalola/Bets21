package domain;

import java.util.Date;

import javax.persistence.Entity;


@Entity
public class Admin extends Pertsona {
	
	public Admin() {
		super();
	}
	
	public Admin(String izena, String pasahitza, Date jaiotzeData) {
		super(izena,pasahitza,jaiotzeData);
	}
}
