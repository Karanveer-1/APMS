package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity

@Table(name = "Project")
public class Project implements Serializable {
	private static String[] STATE = { "OPEN", "CLOSED", "ARCHIVED" };

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ProNo", nullable = false)
	private int proNo;

	@Column(name = "ProMgrEmpNo", nullable = false)
	private int proMgrEmpNo;

	/**
	 * Project current state
	 */
	@Column(name = "State", nullable = false)
	private String state;

	@Column(name = "Comment")
	private String comment;

	public Project() {

	}

	/**
	 * State by default is open
	 * 
	 * @param proNo
	 * @param proMgrEmpNo
	 * @param state
	 * @param comment
	 */
	public Project(int proNo, int proMgrEmpNo, String state, String comment) {
		super();
		this.proNo = proNo;
		this.proMgrEmpNo = proMgrEmpNo;
		this.state = STATE[0];
		this.comment = comment;
	}

	public int getProNo() {
		return proNo;
	}

	public void setProNo(int proNo) {
		this.proNo = proNo;
	}

	public int getProMgrEmpNo() {
		return proMgrEmpNo;
	}

	public void setProMgrEmpNo(int proMgrEmpNo) {
		this.proMgrEmpNo = proMgrEmpNo;
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