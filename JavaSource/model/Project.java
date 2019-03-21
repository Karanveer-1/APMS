package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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

	@Column(name = "ProName", nullable = false)
	private String proName;

	@Column(name = "ProDescription", nullable = false)
	private String proDesc;

	@Column(name = "Budget", nullable = false)
	private float budget;

	@Column(name = "State", nullable = false)
	private String state;

	@Column(name = "Comment")
	private String comment;

	public Project() {
		proMgrEmpNo = 1;
		proName = "Project";
		budget = 0;
		state = STATE[0];
		proDesc = "Project";

	}

	public Project(int proNo, int proMgrEmpNo, String proName, String proDesc, float budget, String state,
			String comment) {
		super();
		this.proNo = proNo;
		this.proMgrEmpNo = proMgrEmpNo;
		this.proName = proName;
		this.proDesc = proDesc;
		this.budget = budget;
		this.state = state;
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

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProDesc() {
		return proDesc;
	}

	public void setProDesc(String proDesc) {
		this.proDesc = proDesc;
	}

	public float getBudget() {
		return budget;
	}

	public void setBudget(float budget) {
		this.budget = budget;
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

	@Override
	public String toString() {
		return "Project [proNo=" + proNo + ", proMgrEmpNo=" + proMgrEmpNo + ", proName=" + proName + ", proDesc="
				+ proDesc + ", budget=" + budget + ", state=" + state + ", comment=" + comment + "]";
	}

}
