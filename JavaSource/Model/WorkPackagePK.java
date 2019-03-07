package Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

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

}
