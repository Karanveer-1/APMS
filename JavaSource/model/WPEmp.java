package model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "WPEmp")
public class WPEmp implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WPEmpPK pk;

	public WPEmp() {

	}

	public WPEmpPK getPk() {
		return pk;
	}

	public void setPk(WPEmpPK pk) {
		this.pk = pk;
	}

	public int getProNo() {
		return pk.getProNo();
	}

	public void setProNo(int proNo) {
		pk.setProNo(proNo);
	}

	public String getWpid() {
		return pk.getWpid();
	}

	public void setWpid(String wpid) {
		pk.setWpid(wpid);
	}

	public int getEmpNo() {
		return pk.getEmpNo();
	}

	public void setEmpNo(int empNo) {
		pk.setEmpNo(empNo);
	}

}
