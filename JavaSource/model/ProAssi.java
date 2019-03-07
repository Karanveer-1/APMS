package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Embeddable
@Table(name = "ProAssi")
public class ProAssi implements Serializable {
	private static final long serialVersionID = 1L;

	/**
	 * Project ID as Primary Key
	 */

	@Column(name = "ProNo", nullable = false)
	private int proNo;

	/**
	 * Project manager, must be unique
	 */

	@Column(name = "ProMgrEmpNo", nullable = false)
	private int empNo;

	public ProAssi(int proNo, int empNo) {
		super();
		this.proNo = proNo;
		this.empNo = empNo;
	}

	public int getProNo() {
		return proNo;
	}

	public void setProNo(int proNo) {
		this.proNo = proNo;
	}

	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

}
