package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name = "Project")
public class Project implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ProNo", nullable = false)
	private int proNo;

	@Column(name = "ProMgrEmpNo", nullable = false)
	private int proMgrEmpNo;

	@Column(name = "ProAssiEmpNo", nullable = false)
	private int proAssiEmpNo;

	@Column(name = "ProName", nullable = false)
	private String proName;

	@Column(name = "ProDescription", nullable = false)
	private String proDesc;

	@Column(name = "StartDate", nullable = false)
	private Date startDate;

	@Column(name = "EndDate", nullable = false)
	private Date endDate;

	@Column(name = "State", nullable = false)
	private String state;

	@Column(name = "Comment")
	private String comment;

	public Project() {
		

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

	public int getProAssiEmpNo() {
		return proAssiEmpNo;
	}

	public void setProAssiEmpNo(int proAssiEmpNo) {
		this.proAssiEmpNo = proAssiEmpNo;
	}

	@Override
	public String toString() {
		return "Project [proNo=" + proNo + ", proMgrEmpNo=" + proMgrEmpNo + ", proAssiEmpNo=" + proAssiEmpNo
				+ ", proName=" + proName + ", proDesc=" + proDesc + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", state=" + state + ", comment=" + comment + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (!o.getClass().equals(this.getClass())) {
			return false;
		}

		if (!(o instanceof Project)) {
			return false;
		}

		Project p = (Project) o;

		if (p.getProNo() == this.getProNo()) {
			return true;
		}

		return false;
	}

}
