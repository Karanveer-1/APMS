package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ProEmp")
public class ProEmp implements Serializable {

	@EmbeddedId
	private ProEmpPK pk;

	public ProEmp() {
	}
	
	public ProEmpPK getPk() {
		return pk;
	}
	
	

}
