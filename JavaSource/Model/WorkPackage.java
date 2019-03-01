package Model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "WorkPackage")
public class WorkPackage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WorkPackagePK workPackagePk;

	@Column(name = "REEmpNo", nullable = false)
	private int reEmpNo;

	@Column(name = "State", nullable = false)
	private String state;

	@Column(name = "Comment")
	private String comment;

	public WorkPackage(WorkPackagePK workPackagePk, int reEmpNo, String state, String comment) {
		super();
		this.workPackagePk = workPackagePk;
		this.reEmpNo = reEmpNo;
		this.state = state;
		this.comment = comment;
	}

	public WorkPackagePK getWorkPackagePk() {
		return workPackagePk;
	}

	public void setWorkPackagePk(WorkPackagePK workPackagePk) {
		this.workPackagePk = workPackagePk;
	}

	public int getReEmpNo() {
		return reEmpNo;
	}

	public void setReEmpNo(int reEmpNo) {
		this.reEmpNo = reEmpNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
