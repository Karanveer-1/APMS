package Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

@Embeddable
public class WPEmp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ProNo", nullable = false)
	private int proNo;

	@Column(name = "WPID", nullable = false)
	private String WPID;

	@Column(name = "EmpNo", nullable = false)
	private int empNo;

	public WPEmp(int proNo, String wPID, int empNo) {
		super();
		this.proNo = proNo;
		WPID = wPID;
		this.empNo = empNo;
	}

	public int getProNo() {
		return proNo;
	}

	public void setProNo(int proNo) {
		this.proNo = proNo;
	}

	public String getWPID() {
		return WPID;
	}

	public void setWPID(String wPID) {
		WPID = wPID;
	}

	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

}
