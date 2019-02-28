package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Embeddable
@Table(name = "Project")
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Project ID as Primary Key
	 */
	@Id
	@Column(name = "ProNo", nullable = false)
	private int proNo;

	/**
	 * Project manager, must be unique
	 */
	@Column(name = "ProMgrEmpNo", nullable = false)
	private int empNo;

	/**
	 * Project current state
	 */
	@Column(name = "State", nullable = false)
	private String state;

	@Column(name = "Comment")
	private String comment;

	public Project() {

	}

	public Project(int proNo, int empNo, String state, String comment) {
		super();
		this.proNo = proNo;
		this.empNo = empNo;
		this.state = state;
		this.comment = comment;
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