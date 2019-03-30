package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "WorkPackage")
public class WorkPackage implements Serializable {
	private static String[] STATE = { "OPEN", "CLOSED", "ARCHIVED" };

	@EmbeddedId
	private WorkPackagePK workPackagePk;

	@Column(name = "REEmpNo", nullable = false)
	private int reEmpNo;

	@Column(name = "WPTitle", nullable = false)
	private String title;

	@Column(name = "WPDescription", nullable = false)
	private String description;

	@Column(name = "ParentWPID")
	private String parentWPID;

	@Column(name = "Budget", nullable = false)
	private float budget;

	@Column(name = "State", nullable = false)
	private String state;

	@Column(name = "Comment")
	private String comment;

	public WorkPackage() {
		this.workPackagePk = new WorkPackagePK();
		this.description = "hihihi";
		this.budget = 12;
		this.state = "OPEN";
		this.title = "okayyy";
		this.reEmpNo = 1;

	}

	public WorkPackage(WorkPackagePK workPackagePk, int reEmpNo, String title, String description, String parentWPID,
			float budget, String state, String comment) {
		super();
		this.workPackagePk = workPackagePk;
		this.reEmpNo = reEmpNo;
		this.title = title;
		this.description = description;
		this.parentWPID = parentWPID;
		this.budget = budget;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentWPID() {
		return parentWPID;
	}

	public void setParentWPID(String parentWPID) {
		this.parentWPID = parentWPID;
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

	
	
	public int getProNo() {
		return workPackagePk.getProNo();
	}

	public void setProNo(int proNo) {
		workPackagePk.setProNo(proNo);
	}

	
	public String getWpid() {
		return workPackagePk.getWpid();
	}

	public void setWpid(String wpid) {
		workPackagePk.setWpid(wpid);
	}

	@Override
	public String toString() {
		return "WorkPackage [workPackagePk=" + workPackagePk + ", reEmpNo=" + reEmpNo + ", title=" + title
				+ ", description=" + description + ", parentWPID=" + parentWPID + ", budget=" + budget + ", state="
				+ state + ", comment=" + comment + "]";
	}
	
}
