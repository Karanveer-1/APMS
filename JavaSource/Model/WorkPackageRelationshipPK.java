package Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class WorkPackageRelationshipPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ProNo", nullable = false)
	private int proNo;

	@Column(name = "ChildWPID", nullable = false)
	private String childWPID;

	public WorkPackageRelationshipPK(int proNo, String childWPID) {
		super();
		this.proNo = proNo;
		this.childWPID = childWPID;
	}

	public int getProNo() {
		return proNo;
	}

	public void setProNo(int proNo) {
		this.proNo = proNo;
	}

	public String getChildWPID() {
		return childWPID;
	}

	public void setChildWPID(String childWPID) {
		this.childWPID = childWPID;
	}

}
