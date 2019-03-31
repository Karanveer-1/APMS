//package model;
//
//import java.io.Serializable;
//
//import javax.persistence.Column;
//import javax.persistence.Embeddable;
//
//@Embeddable
//public class ProAssiPK implements Serializable {
//	/**
//	 * Project ID as Primary Key
//	 */
//
//	@Column(name = "ProNo", nullable = false)
//	private int proNo;
//
//	/**
//	 * Project manager, must be unique
//	 */
//
//	@Column(name = "ProAssiEmpNo", nullable = false)
//	private int empNo;
//
//	public ProAssiPK() {
//		
//	}
//	public ProAssiPK(int proNo, int empNo) {
//		super();
//		this.proNo = proNo;
//		this.empNo = empNo;
//	}
//
//	public int getProNo() {
//		return proNo;
//	}
//
//	public void setProNo(int proNo) {
//		this.proNo = proNo;
//	}
//
//	public int getEmpNo() {
//		return empNo;
//	}
//
//	public void setEmpNo(int empNo) {
//		this.empNo = empNo;
//	}
//}
