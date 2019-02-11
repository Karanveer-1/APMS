package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Project")
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Project status
	 * 
	 * @author mike
	 *
	 */
	enum Status {
		OPEN, CLOSED, ARCHIVED;
	}

	/**
	 * Project ID as Primary Key
	 */
	@GeneratedValue
	@Id
	@Column(name = "ProjectId", nullable = false)
	private int projectId;

	/**
	 * Project manager, must be unique
	 */
	@OneToOne
	@Column(name = "ProjectManager", nullable = false)
	private Employee projectManager;

	/**
	 * Project current status
	 */
	@Column(name = "Status", nullable = false)
	private int status;

	/**
	 * List of work packages in a project
	 */
	@OneToMany
	@Column(name = "WorkPackages", nullable = false)
	private ArrayList<WorkPackage> workPackages;



	/**
	 * Project start date
	 */
	@Column(name = "StartDate", nullable = false)
	private Date startDate;

	/**
	 * Project end date
	 */
	@Column(name = "endDate", nullable = false)
	private Date endDate;

	public Project() {

	}
	
	public Project(int projectId, Employee projectManager, int status, ArrayList<WorkPackage> workPackages,
			Date startDate, Date endDate) {
		super();
		this.projectId = projectId;
		this.projectManager = projectManager;
		this.status = status;
		this.workPackages = workPackages;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
}