package Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name = "ProjectForm")
public class ProjectForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ProNo", nullable = false)
	private int proNo;

	@Column(name = "Name", nullable = false)
	private String name;

	@Column(name = "Position")
	private String position;

	@Column(name = "ReportsTo")
	private String reportsTo;

	@Column(name = "ProName")
	private String proName;

	@Column(name = "ProMgrEmpNo")
	private int proMgrEmpNo;

	@Column(name = "ProMgr")
	private String proMgr;

	@Column(name = "JobDescription")
	private String jobDescription;

	@Column(name = "Duration")
	private String Duration;

	@Column(name = "Milestone")
	private String milestone;

	@Column(name = "Training")
	private String training;

	@Column(name = "Special")
	private String special;

	@Column(name = "Criteria")
	private String criteria;

	@Column(name = "Signatures")
	private String signatures;

	@Column(name = "Date", nullable = false)
	private Date dates;

	public ProjectForm() {
	}

	public ProjectForm(int proNo, String name, String position, String reportsTo, String proName, int proMgrEmpNo,
			String proMgr, String jobDescription, String duration, String milestone, String training, String special,
			String criteria, String signatures, Date dates) {
		super();
		this.proNo = proNo;
		this.name = name;
		this.position = position;
		this.reportsTo = reportsTo;
		this.proName = proName;
		this.proMgrEmpNo = proMgrEmpNo;
		this.proMgr = proMgr;
		this.jobDescription = jobDescription;
		Duration = duration;
		this.milestone = milestone;
		this.training = training;
		this.special = special;
		this.criteria = criteria;
		this.signatures = signatures;
		this.dates = dates;
	}

	public int getProNo() {
		return proNo;
	}

	public void setProNo(int proNo) {
		this.proNo = proNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getReportsTo() {
		return reportsTo;
	}

	public void setReportsTo(String reportsTo) {
		this.reportsTo = reportsTo;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public int getProMgrEmpNo() {
		return proMgrEmpNo;
	}

	public void setProMgrEmpNo(int proMgrEmpNo) {
		this.proMgrEmpNo = proMgrEmpNo;
	}

	public String getProMgr() {
		return proMgr;
	}

	public void setProMgr(String proMgr) {
		this.proMgr = proMgr;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getDuration() {
		return Duration;
	}

	public void setDuration(String duration) {
		Duration = duration;
	}

	public String getMilestone() {
		return milestone;
	}

	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}

	public String getTraining() {
		return training;
	}

	public void setTraining(String training) {
		this.training = training;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public String getSignatures() {
		return signatures;
	}

	public void setSignatures(String signatures) {
		this.signatures = signatures;
	}

	public Date getDates() {
		return dates;
	}

	public void setDates(Date dates) {
		this.dates = dates;
	}

}
