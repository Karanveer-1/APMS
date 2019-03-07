package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import model.Employee;
import model.Project;
import model.Timesheet;
import model.TimesheetRow;
import model.TimesheetRowPK;
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

	public List<Employee> getActiveEmployees() {
		return manager.createQuery("SELECT e FROM Employee e WHERE e.state = 'Active'", Employee.class).getResultList();
	}

	public Employee getEmployeeByUsername(String username) {
		TypedQuery<Employee> query = manager.createQuery("select e from Employee e where e.userName = :username",
				Employee.class);
		query.setParameter("username", username);
		try {
			Employee emp = query.getSingleResult();
			return emp;
		} catch (Exception exp) {
			return null;
		}

	}

	public Employee getEmployeeById(int id) {
		TypedQuery<Employee> query = manager.createQuery("select e from Employee e where e.empNumber = :empNumber",
				Employee.class);
		query.setParameter("empNumber", id);
		try {
			Employee emp = query.getSingleResult();
			return emp;
		} catch (Exception exp) {
			return null;
		}
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
	public List<Timesheet> getTimesheets(int empNo) {
		List<Timesheet> timesheets = manager.createQuery("SELECT t from Timesheet t", Timesheet.class).getResultList();

		List<Timesheet> result = new ArrayList<Timesheet>();
		for (Timesheet timesheet : timesheets) {
			if (timesheet.getTimesheetPk().getEmpNo() == empNo) {
				result.add(timesheet);
			}
		}

		return result;
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

	/**
	 * merge Category record fields into existing database record.
	 * 
	 * @param category the record to be merged.
	 */
	public void updateTimesheet(Timesheet t) {
		manager.merge(t);
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

		Date start = DateUtils.getTimesheetStartDate(startDate);
		Date end = DateUtils.getTimesheetEndDate(startDate);

		for (TimesheetRow row : timesheetRows) {
			TimesheetRowPK pk = row.getTimesheetRowPk();

			if (pk.getEmpNo() == empNo && DateUtils.isWithinRange(pk.getStartDate(), start, end)) {
				relatedTimesheetRows.add(row);
			}
		}

		return relatedTimesheetRows;
	}

	public void addIfNotExistTimesheetRows(List<TimesheetRow> rows) {
		for (TimesheetRow row : rows) {
			if (manager.find(TimesheetRow.class, row.getTimesheetRowPk()) != null) {
				manager.merge(row);
			} else {
				manager.persist(row);
			}
		}
	}

	public void removeTimesheetRows(List<TimesheetRow> rows) {
		for (TimesheetRow row : rows) {
			manager.remove(manager.contains(row) ? row : manager.merge(row));
		}
	}

	public void removeTimesheetRows(Timesheet t) {
		removeTimesheetRows(getTimesheetRows(t.getTimesheetPk().getEmpNo(), t.getTimesheetPk().getStartDate()));
	}

	// #########################################################################
	// # Project methods
	// #########################################################################

	/**
	 * GET
	 * 
	 * @return all projects
	 */
	public List<Project> getAllProjects() {
		List<Project> projects = this.manager.createQuery("SELECT p from Project p", Project.class).getResultList();
		List<Project> result = new ArrayList<Project>();
		for (Project p : projects) {
			result.add(p);
		}

		return result;

	}

	/**
	 * GET
	 * 
	 * @param proNo
	 * @return project by ID
	 */
	public Project findByProjectNo(final int proNo) {
		return this.manager.find(Project.class, proNo);
	}

	/**
	 * POST add a new project
	 * 
	 * @param newProject
	 */
	public boolean persistProject(Project newProject) {
		Project p = this.manager.find(Project.class, newProject.getProNo());
		if (p == null) {
			this.manager.persist(newProject);
			return true;
		}
		return false;

	}

	/**
	 * POST
	 * 
	 * @param project
	 * @return UPDATE A PROJECT
	 */
	public boolean updateProject(Project project) {
		Project p = this.manager.find(Project.class, project.getProNo());
		if (p != null) {
			this.manager.merge(project);
			return true;
		}
		return false;
	}

	public boolean deleteProjectByProNo(final int proNo) {
		Project p = this.findByProjectNo(proNo);
		try {
			this.manager.remove(p);
			this.manager.flush();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

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