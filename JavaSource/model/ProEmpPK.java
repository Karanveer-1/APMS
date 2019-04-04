package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable

public class ProEmpPK implements Serializable {

	@Column(name = "ProNo", nullable = false)

	private int proNo;

	@Column(name = "EmpNo", nullable = false)
	private int empNo;

	public ProEmpPK() {
	}

	public ProEmpPK(int proNo, int empNo) {

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + empNo;
		result = prime * result + proNo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProEmpPK other = (ProEmpPK) obj;
		if (empNo != other.empNo)
			return false;
		if (proNo != other.proNo)
			return false;
		return true;
	}

}
