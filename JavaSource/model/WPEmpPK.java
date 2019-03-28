package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class WPEmpPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ProNo", nullable = false)
	private int proNo;

	@Column(name = "WPID", nullable = false)
	private String wpid;

	@Column(name = "EmpNo", nullable = false)
	private int empNo;

	public WPEmpPK() {

	}

	public WPEmpPK(int proNo, String WPID, int empNo) {
		super();
		this.proNo = proNo;
		this.wpid = WPID;
		this.empNo = empNo;
	}

	public int getProNo() {
		return proNo;
	}

	public void setProNo(int proNo) {
		this.proNo = proNo;
	}

	public String getWpid() {
		return wpid;
	}

	public void setWpid(String wpid) {
		this.wpid = wpid;
	}

	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

	
}
