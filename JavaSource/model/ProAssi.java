//package model;
//
//import java.io.Serializable;
//
//import javax.persistence.Column;
//import javax.persistence.Embeddable;
//import javax.persistence.EmbeddedId;
//import javax.persistence.Entity;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "ProAssi")
//public class ProAssi implements Serializable {
//
//	@EmbeddedId
//	private ProAssiPK pk;
//
//	public ProAssi() {
//
//	}
//
//	public ProAssi(int proNo, int empNo) {
//		super();
//		this.pk = new ProAssiPK(proNo, empNo);
//	}
//
//	public ProAssiPK getPk() {
//		return pk;
//	}
//
//	public int getProNo() {
//		return pk.getProNo();
//	}
//
//	public void setProNo(int proNo) {
//		pk.setProNo(proNo);
//	}
//
//	public int getEmpNo() {
//		return pk.getEmpNo();
//	}
//
//	public void setEmpNo(int empNo) {
//		pk.setEmpNo(empNo);
//	}
//}
