package model;

import java.io.Serializable;
import java.util.Date;

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
	private Integer reEmpNo;

	@Column(name = "WPTitle", nullable = false)
	private String title;

	@Column(name = "WPDescription", nullable = false)
	private String description;

	@Column(name = "ParentWPID")
	private String parentWPID;

	@Column(name = "State", nullable = false)
	private String state;

	@Column(name = "Comment")
	private String comment;

	@Column(name = "IsLeaf", nullable = false)
	private boolean isLeaf;

	@Column(name = "StartDate", nullable = false)
	private Date startDate;

	@Column(name = "EndDate", nullable = false)
	private Date endDate;
	
	@Column(name="Editable", nullable = false)
	private boolean editable;

	@Column(name = "PMEstP1")
	private Integer pmEstP1;

	@Column(name = "PMEstP2")
	private Integer pmEstP2;

	@Column(name = "PMEstP3")
	private Integer pmEstP3;

	@Column(name = "PMEstP4")
	private Integer pmEstP4;

	@Column(name = "PMEstP5")
	private Integer pmEstP5;

	@Column(name = "PMEstP6")
	private Integer pmEstP6;

	@Column(name = "PMEstDS")
	private Integer pmEstDS;

	@Column(name = "PMEstSS")
	private Integer pmEstSS;

	@Column(name = "PMEstJS")
	private Integer pmEstJS;

	@Column(name = "REEstP1")
	private Integer reEstP1;

	@Column(name = "REEstP2")
	private Integer reEstP2;

	@Column(name = "REEstP3")
	private Integer reEstP3;

	@Column(name = "REEstP4")
	private Integer reEstP4;

	@Column(name = "REEstP5")
	private Integer reEstP5;

	@Column(name = "REEstP6")
	private Integer reEstP6;

	@Column(name = "REEstDS")
	private Integer reEstDS;

	@Column(name = "REEstSS")
	private Integer reEstSS;

	@Column(name = "REEstJS")
	private Integer reEstJS;

	public WorkPackage() {
		this.workPackagePk = new WorkPackagePK();
		this.description = "hihihi";
		this.state = "OPEN";
		this.title = "okayyy";
		this.reEmpNo = 1;
		this.startDate = new Date();
		this.endDate = new Date();
		this.isLeaf = false;
		

	}

	public WorkPackage(WorkPackagePK workPackagePk, int reEmpNo, String title, String description, String parentWPID,
			String state, String comment, boolean isLeaf, Date startDate, Date endDate, Integer pmEstP1,
			Integer pmEstP2, Integer pmEstP3, Integer pmEstP4, Integer pmEstP5, Integer pmEstP6, Integer pmEstDS,
			Integer pmEstSS, Integer pmEstJS, Integer reEstP1, Integer reEstP2, Integer reEstP3, Integer reEstP4,
			Integer reEstP5, Integer reEstP6, Integer reEstDS, Integer reEstSS, Integer reEstJS) {
		super();
		this.workPackagePk = workPackagePk;
		this.reEmpNo = reEmpNo;
		this.title = title;
		this.description = description;
		this.parentWPID = parentWPID;
		this.state = state;
		this.comment = comment;
		this.isLeaf = isLeaf;
		this.startDate = startDate;
		this.endDate = endDate;
		this.pmEstP1 = pmEstP1;
		this.pmEstP2 = pmEstP2;
		this.pmEstP3 = pmEstP3;
		this.pmEstP4 = pmEstP4;
		this.pmEstP5 = pmEstP5;
		this.pmEstP6 = pmEstP6;
		this.pmEstDS = pmEstDS;
		this.pmEstSS = pmEstSS;
		this.pmEstJS = pmEstJS;
		this.reEstP1 = reEstP1;
		this.reEstP2 = reEstP2;
		this.reEstP3 = reEstP3;
		this.reEstP4 = reEstP4;
		this.reEstP5 = reEstP5;
		this.reEstP6 = reEstP6;
		this.reEstDS = reEstDS;
		this.reEstSS = reEstSS;
		this.reEstJS = reEstJS;
	}

	public WorkPackagePK getWorkPackagePk() {
		return workPackagePk;
	}

	public void setWorkPackagePk(WorkPackagePK workPackagePk) {
		this.workPackagePk = workPackagePk;
	}

	public Integer getReEmpNo() {
		return reEmpNo;
	}

	public void setReEmpNo(Integer reEmpNo) {
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {

		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getPmEstP1() {
		return pmEstP1;
	}

	public void setPmEstP1(Integer pmEstP1) {
		this.pmEstP1 = pmEstP1;
	}

	public Integer getPmEstP2() {
		return pmEstP2;
	}

	public void setPmEstP2(Integer pmEstP2) {
		this.pmEstP2 = pmEstP2;
	}

	public Integer getPmEstP3() {
		return pmEstP3;
	}

	public void setPmEstP3(Integer pmEstP3) {
		this.pmEstP3 = pmEstP3;
	}

	public Integer getPmEstP4() {
		return pmEstP4;
	}

	public void setPmEstP4(Integer pmEstP4) {
		this.pmEstP4 = pmEstP4;
	}

	public Integer getPmEstP5() {
		return pmEstP5;
	}

	public void setPmEstP5(Integer pmEstP5) {
		this.pmEstP5 = pmEstP5;
	}

	public Integer getPmEstP6() {
		return pmEstP6;
	}

	public void setPmEstP6(Integer pmEstP6) {
		this.pmEstP6 = pmEstP6;
	}

	public Integer getPmEstDS() {
		return pmEstDS;
	}

	public void setPmEstDS(Integer pmEstDS) {
		this.pmEstDS = pmEstDS;
	}

	public Integer getPmEstSS() {
		return pmEstSS;
	}

	public void setPmEstSS(Integer pmEstSS) {
		this.pmEstSS = pmEstSS;
	}

	public Integer getPmEstJS() {
		return pmEstJS;
	}

	public void setPmEstJS(Integer pmEstJS) {
		this.pmEstJS = pmEstJS;
	}

	public Integer getReEstP1() {
		return reEstP1;
	}

	public void setReEstP1(Integer reEstP1) {
		this.reEstP1 = reEstP1;
	}

	public Integer getReEstP2() {
		return reEstP2;
	}

	public void setReEstP2(Integer reEstP2) {
		this.reEstP2 = reEstP2;
	}

	public Integer getReEstP3() {
		return reEstP3;
	}

	public void setReEstP3(Integer reEstP3) {
		this.reEstP3 = reEstP3;
	}

	public Integer getReEstP4() {
		return reEstP4;
	}

	public void setReEstP4(Integer reEstP4) {
		this.reEstP4 = reEstP4;
	}

	public Integer getReEstP5() {
		return reEstP5;
	}

	public void setReEstP5(Integer reEstP5) {
		this.reEstP5 = reEstP5;
	}

	public Integer getReEstP6() {
		return reEstP6;
	}

	public void setReEstP6(Integer reEstP6) {
		this.reEstP6 = reEstP6;
	}

	public Integer getReEstDS() {
		return reEstDS;
	}

	public void setReEstDS(Integer reEstDS) {
		this.reEstDS = reEstDS;
	}

	public Integer getReEstSS() {
		return reEstSS;
	}

	public void setReEstSS(Integer reEstSS) {
		this.reEstSS = reEstSS;
	}

	public Integer getReEstJS() {
		return reEstJS;
	}

	public void setReEstJS(Integer reEstJS) {
		this.reEstJS = reEstJS;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Override
	public String toString() {
		return "WorkPackage [workPackagePk=" + workPackagePk + ", reEmpNo=" + reEmpNo + ", title=" + title
				+ ", description=" + description + ", parentWPID=" + parentWPID + ", state=" + state + ", comment="
				+ comment + ", isLeaf=" + isLeaf + ", startDate=" + startDate + ", endDate=" + endDate + ", pmEstP1="
				+ pmEstP1 + ", pmEstP2=" + pmEstP2 + ", pmEstP3=" + pmEstP3 + ", pmEstP4=" + pmEstP4 + ", pmEstP5="
				+ pmEstP5 + ", pmEstP6=" + pmEstP6 + ", pmEstDS=" + pmEstDS + ", pmEstSS=" + pmEstSS + ", pmEstJS="
				+ pmEstJS + ", reEstP1=" + reEstP1 + ", reEstP2=" + reEstP2 + ", reEstP3=" + reEstP3 + ", reEstP4="
				+ reEstP4 + ", reEstP5=" + reEstP5 + ", reEstP6=" + reEstP6 + ", reEstDS=" + reEstDS + ", reEstSS="
				+ reEstSS + ", reEstJS=" + reEstJS + "]";
	}

}
