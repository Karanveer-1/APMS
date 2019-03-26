package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProEmpPK  implements Serializable{

	@Column(name = "ProNo", nullable = false)
	private int proNo;

	@Column(name = "EmpNo", nullable = false)
	private int empNo;

	public ProEmpPK() {
	}

	public ProEmpPK(int proNo, int empNo) {
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
