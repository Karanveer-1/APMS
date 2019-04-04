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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + empNo;
        result = prime * result + proNo;
        result = prime * result + ((wpid == null) ? 0 : wpid.hashCode());
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
        WPEmpPK other = (WPEmpPK) obj;
        if (empNo != other.empNo)
            return false;
        if (proNo != other.proNo)
            return false;
        if (wpid == null) {
            if (other.wpid != null)
                return false;
        } else if (!wpid.equals(other.wpid))
            return false;
        return true;
    }
}
