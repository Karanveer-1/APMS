package Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import Model.Employee;
import Model.Project;
import Model.Timesheet;
import Model.TimesheetRow;
import Model.TimesheetRowPK;
import Model.WorkPackage;
import utils.DateUtils;

@Stateless
public class DatabaseController implements Serializable {
	@PersistenceContext(unitName = "apms", type = PersistenceContextType.TRANSACTION)
	private EntityManager manager;

	// #########################################################################
	// # Employee methods
	// #########################################################################
	public List<Employee> getEmployees() {
		return manager.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
	}

	public Employee getEmployeeByUsername(String username) {
		TypedQuery<Employee> query = manager.createQuery("select e from Employee e where e.userName = :username",
				Employee.class);
		query.setParameter("username", username);
		return query.getSingleResult();
	}

	public void addEmployee(Employee e) {
		manager.persist(e);
	}

	public void removeEmployee(Employee e) {
		manager.remove(manager.contains(e) ? e : manager.merge(e));
	}

	public void updateEmployee(Employee e) {
		manager.merge(e);
	}

	// #########################################################################
	// # Timesheet methods
	// #########################################################################
	public List<Timesheet> getTimesheets() {
		return manager.createQuery("SELECT t from Timesheet t", Timesheet.class).getResultList();
	}

	public void addTimesheet(Timesheet t) {
		manager.persist(t);
	}

	public void addTimesheetIfNotExist(Timesheet t, boolean merge) {
		if (manager.find(Timesheet.class, t.getTimesheetPk()) == null) {
			manager.persist(t);
		} else if (merge) {
			manager.merge(t);
		}
	}

	public void removeTimesheet(Timesheet t) {
		manager.remove(manager.contains(t) ? t : manager.merge(t));
	}

	// #########################################################################
	// # TimesheetRow methods
	// #########################################################################
	private List<TimesheetRow> getTimesheetRows() {
		return manager.createQuery("SELECT tr from TimesheetRow tr", TimesheetRow.class).getResultList();
	}

	public List<TimesheetRow> getTimesheetRows(int empNo, Date startDate) {
		List<TimesheetRow> timesheetRows = getTimesheetRows();

		List<TimesheetRow> relatedTimesheetRows = new ArrayList<TimesheetRow>();

		for (TimesheetRow row : timesheetRows) {
			TimesheetRowPK pk = row.getTimesheetRowPk();

			if (pk.getEmpNo() == empNo && DateUtils.isWithinWeekOfYear(pk.getStartDate(), startDate)) {
				relatedTimesheetRows.add(row);
			}
		}

		return relatedTimesheetRows;
	}

	// #########################################################################
	// # WorkPackage methods
	// #########################################################################
	public List<WorkPackage> getWorkPackages(final int proNo) {
		TypedQuery<WorkPackage> query = manager.createQuery("select * from workpackage where e.userName = :username",
				WorkPackage.class);
		query.setParameter("proNo", proNo);
		return query.getResultList();
	}

	public void addWorkPackage(WorkPackage wp, int proNo) {
//		manager.find
	}

	// #########################################################################
	// # Project methods
	// #########################################################################
	public List<Project> getProjects(final int proNo) {
		return manager.createQuery("SELECT e FROM Project e", Project.class).getResultList();
	}

	public void addProject(Project project) {
		Project match = manager.find(Project.class, project.getProNo());
		if (match == null) {
			manager.persist(project);
		}

	}

	public void updateProject(Project project) {
		try {
			if (manager.find(Project.class, project.getProNo()) == null) {
				manager.persist(project);
			} else {
				this.manager.merge(project);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
	}
	
//	public void deleteProject(int proNo)

}

/*
 * This is just a place holder for extra whitespace
 * 
 * f
 * 
 * g
 * 
 * g
 * 
 * g
 * 
 * g
 * 
 * g
 * 
 * g
 * 
 * g
 * 
 * g
 * 
 * g
 */