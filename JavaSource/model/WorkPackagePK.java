package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class WorkPackagePK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ProNo", nullable = false)
	private int proNo;

	@Column(name = "WPID", nullable = false)
	private String wpid;

	public WorkPackagePK() {

	}

	public WorkPackagePK(int proNo, String wpid) {
		super();
		this.proNo = proNo;
		this.wpid = wpid;
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

	@Override
	public String toString() {
		return "WorkPackagePK [proNo=" + proNo + ", wpid=" + wpid + "]";
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        WorkPackagePK other = (WorkPackagePK) obj;
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
