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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pk == null) ? 0 : pk.hashCode());
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
        WPEmp other = (WPEmp) obj;
        if (pk == null) {
            if (other.pk != null)
                return false;
        } else if (!pk.equals(other.pk))
            return false;
        return true;
    }
}
