package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import model.EmpPLevel;
import model.Employee;
import model.PLevel;
import model.ProEmp;
import model.ProEmpPK;
import model.Project;
import model.Role;
import model.Signature;
import model.Timesheet;
import model.TimesheetPK;
import model.TimesheetRow;
import model.TimesheetRowPK;
import model.TimesheetState;
import model.WPEmp;
import model.WPEmpPK;
import model.WPNeed;
import model.WPNeedPK;
import model.WorkPackage;
import model.WorkPackagePK;
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

	public long countEmployees() {
		String sql = "SELECT COUNT(e) FROM Employee e";
		Query q = manager.createQuery(sql);
		return (long) q.getSingleResult();
	}

	public List<Employee> getActiveEmployees() {
		return manager.createQuery("SELECT e FROM Employee e WHERE e.state = 'Active'", Employee.class).getResultList();
	}

	public List<Employee> getInActiveEmployees() {
		return manager.createQuery("SELECT e FROM Employee e WHERE e.state = 'Not Active'", Employee.class)
				.getResultList();
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
	public List<Timesheet> getTimesheets() {
		return manager.createQuery("SELECT t from Timesheet t", Timesheet.class).getResultList();
	}

	public long countTimesheets() {
		String sql = "SELECT COUNT(t) FROM Timesheet t";
		Query q = manager.createQuery(sql);
		return (long) q.getSingleResult();
	}

	public long countApprovedTimesheets() {
		String sql = "SELECT COUNT(t) FROM Timesheet t WHERE t.state = 'Approved'";
		Query q = manager.createQuery(sql);
		return (long) q.getSingleResult();
	}

	public long countRejectedTimesheets() {
		String sql = "SELECT COUNT(t) FROM Timesheet t WHERE t.state = 'Returned'";
		Query q = manager.createQuery(sql);
		return (long) q.getSingleResult();
	}

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

	public List<Timesheet> getSubmittedTimesheets(int empNo) {
		List<Timesheet> timesheets = manager.createQuery("SELECT t from Timesheet t", Timesheet.class).getResultList();

		List<Timesheet> result = new ArrayList<Timesheet>();
		for (Timesheet timesheet : timesheets) {
			Employee e = getEmployeeById(timesheet.getTimesheetPk().getEmpNo());

			if (e.getApproEmpNo() == empNo && timesheet.getState().equalsIgnoreCase(TimesheetState.PENDING)) {
				result.add(timesheet);
			}
		}

		return result;
	}

	public List<Timesheet> getSubmittedTimesheets() {
		List<Timesheet> timesheets = manager.createQuery("SELECT t from Timesheet t", Timesheet.class).getResultList();

		List<Timesheet> result = new ArrayList<Timesheet>();
		for (Timesheet timesheet : timesheets) {
			if (timesheet.getState().equalsIgnoreCase(TimesheetState.PENDING)) {
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

	public void updateTimesheet(Timesheet t) {
		if (manager.contains(t)) {
			removeTimesheet(t);
		}

		manager.merge(t);
	}

	// #########################################################################
	// # TimesheetRow methods
	// #########################################################################
	public List<TimesheetRow> getTimesheetRows() {
		return manager.createQuery("SELECT tr from TimesheetRow tr", TimesheetRow.class).getResultList();
	}

	public List<TimesheetRow> getTimesheetRows(Timesheet t) {
		int empNo = t.getTimesheetPk().getEmpNo();
		Date startDate = t.getTimesheetPk().getStartDate();
		return getTimesheetRows(empNo, startDate);
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

	public void updateTimesheetRows(List<TimesheetRow> rows) {
		for (TimesheetRow row : rows) {
			manager.merge(row);
		}
	}

	// #########################################################################
	// # Project methods
	// #########################################################################

	public List<Project> getAllProjects() {
		List<Project> projects = this.manager.createQuery("SELECT p from Project p", Project.class).getResultList();

		return projects;
	}

	public long countProjects() {
		String sql = "SELECT COUNT(p) FROM Project p";
		Query q = manager.createQuery(sql);
		return (long) q.getSingleResult();
	}

	public List<Project> getAllProjectsbyEmpNo(int empNo) {
		List<Project> projects = getAllProjects();

		TypedQuery<ProEmp> query = manager.createQuery("select p from ProEmp p where p.proEmp.empNo = :empNo",
				ProEmp.class);
		query.setParameter("empNo", empNo);
		List<ProEmp> proEmps = query.getResultList();
		List<Project> temp = new ArrayList<Project>();
		for (ProEmp p : proEmps) {
			for (Project pro : projects) {
				if (p.getProEmp().getProNo() == pro.getProNo()) {
					temp.add(pro);
				}
			}
		}
		return temp;
	}

	public List<Integer> getAllProjectNo() {
		List<Integer> ids = manager.createQuery("SELECT p.proNo from Project p", Integer.class).getResultList();

		return ids;
	}

	public List<Integer> getAllProjectManagerEmpNos() {
		return manager.createQuery("SELECT p.proMgrEmpNo FROM Project p", Integer.class).getResultList();
	}

	public List<Integer> getAllProjectNoForProjectManager(Integer proNo) {
		List<Integer> ids = manager
				.createQuery("SELECT p.proNo from Project p where p.proMgrEmpNo = :no", Integer.class)
				.setParameter("no", proNo).getResultList();

		return ids;
	}

	public Project findByProjectNo(final int proNo) {
		return this.manager.find(Project.class, proNo);
	}

	/**
	 * Also add the project manager to the proemp database
	 *
	 * @param newProject
	 * @return
	 */
	public boolean persistProject(Project newProject) {
		Project p = this.manager.find(Project.class, newProject.getProNo());
		addNewEmployeeToProject(newProject.getProMgrEmpNo(), newProject.getProNo());
		if (newProject.getProAssiEmpNo() != newProject.getProMgrEmpNo()) {
			addNewEmployeeToProject(newProject.getProAssiEmpNo(), newProject.getProNo());
		}
		if (p == null) {
			this.manager.persist(newProject);
			return true;
		}

		return false;
	}

	public boolean updateProject(Project project) {
		Project p = this.manager.find(Project.class, project.getProNo());

		if (p != null) {
			updateEmployeeToProject(project.getProMgrEmpNo(), project.getProNo());
			this.manager.merge(project);
			return true;
		}

		return false;
	}

	public boolean deleteProjectByProNo(final int proNo) {
		Project p = this.findByProjectNo(proNo);
		List<ProEmp> pe = this.manager.createQuery("select p from ProEmp p ", ProEmp.class).getResultList();
		try {
			for (ProEmp pro : pe) {
				this.deleteEmpFromProject(proNo, pro.getProEmp().getEmpNo());
			}
			this.manager.remove(p);
			this.manager.flush();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean deleteEmpFromProject(int proNo, int empNo) {
		TypedQuery<ProEmp> query = manager.createQuery(
				"select p from ProEmp p where p.proEmp.proNo = :proNo and p.proEmp.empNo=:empNo", ProEmp.class);
		query.setParameter("proNo", proNo);
		query.setParameter("empNo", empNo);
		ProEmp pe = query.getSingleResult();
		try {
			this.manager.remove(pe);
			this.manager.flush();
			return true;
		} catch (Exception e) {

			return false;
		}

	}

	public List<Role> getRolesByEmpNo(int empNo) {
		TypedQuery<Role> query = manager.createQuery("select p from Role p where p.rolePk.empNo = :empNo", Role.class);
		query.setParameter("empNo", empNo);
		List<Role> data = query.getResultList();
		return data;
	}

	public List<Employee> getEmployeeForProject(int projectId) {
		TypedQuery<ProEmp> query = manager.createQuery("select p from ProEmp p where p.proEmp.proNo = :projectId",
				ProEmp.class);
		query.setParameter("projectId", projectId);
		List<ProEmp> data = query.getResultList();

		List<Employee> empList = new ArrayList<>();
		for (ProEmp e : data) {
			empList.add(getEmployeeById(e.getProEmp().getEmpNo()));
		}

		return empList;
	}

	public List<Employee> getAllSupervisedEmployees(int empNo) {
		TypedQuery<Employee> query = manager.createQuery("select p from Employee p where p.superEmpNo = :empNo",
				Employee.class);
		query.setParameter("empNo", empNo);
		List<Employee> data = query.getResultList();
		return data;
	}

	public List<Employee> getAllApproEmployees(int empNo) {
		TypedQuery<Employee> query = manager.createQuery("select p from Employee p where p.approEmpNo = :empNo",
				Employee.class);
		query.setParameter("empNo", empNo);
		List<Employee> data = query.getResultList();
		return data;
	}

	public void addNewEmployeeToProject(int employeeNumber, int proNo) {
		ProEmp temp = new ProEmp();
		temp.setProEmp(new ProEmpPK(proNo, employeeNumber));

		manager.persist(temp);
	}

	public void updateEmployeeToProject(int employeeNumber, int proNo) {
		ProEmp temp = new ProEmp();
		temp.setProEmp(new ProEmpPK(proNo, employeeNumber));

		manager.merge(temp);
	}

	public List<Project> getProjectsBySupervisor(int id) {
		List<ProEmp> proemp = new ArrayList<ProEmp>();
		TypedQuery<ProEmp> query = this.manager.createQuery("SELECT p from ProEmp p WHERE p.pk.empNo =:empNo",
				ProEmp.class);
		query.setParameter("empNo", id);
		try {
			proemp = query.getResultList();
			List<Project> projects = new ArrayList<Project>();
			for (ProEmp pe : proemp) {
				projects.add(findByProjectNo(pe.getProEmp().getProNo()));
			}

			return projects;
		} catch (Exception exp) {
			return null;
		}
	}

	public List<Project> getProjectsByManagerNo(int id) {
		List<Project> allP = getAllProjects();
		List<Project> result = new ArrayList<Project>();
		for (Project p : allP) {
			if (p.getProMgrEmpNo() == id) {
				result.add(p);
			}
		}
		return result;
	}

	public List<Project> getProjectByAssignedEmpNo(int id) {
		List<ProEmp> pe = getAllProEmp();

		List<Project> result = new ArrayList<Project>();
		for (ProEmp p : pe) {
			if (p.getProEmp().getEmpNo() == id) {
				result.add(findByProjectNo(p.getProEmp().getProNo()));
			}
		}
		return result;
	}

	public List<ProEmp> getAllProEmp() {
		return this.manager.createQuery("SELECT p from ProEmp p", ProEmp.class).getResultList();
	}

	// #########################################################################
	// # WorkPackage methods
	// #########################################################################

	public List<WorkPackage> getAllWp() {
		return this.manager.createQuery("SELECT wp from WorkPackage wp", WorkPackage.class).getResultList();
	}

	public List<String> getWpIdByProjectNo(int proNo) {
		List<WorkPackage> wps = getAllWp();
		List<String> ids = new ArrayList<String>();

		for (WorkPackage wp : wps) {
			if (wp.getProNo() == proNo) {
				ids.add(wp.getWpid());
			}
		}
		return ids;
	}

	public List<WorkPackage> getWPListByProNo(int proNo) {
		List<WorkPackage> result = new ArrayList<WorkPackage>();
		List<WorkPackage> wps = getAllWp();
		for (WorkPackage wp : wps) {
			if (wp.getProNo() == proNo) {
				result.add(wp);
			}
		}
		return result;
	}

	public List<WorkPackage> getLowerWP(String wpid) {
		List<WorkPackage> result = new ArrayList<WorkPackage>();
		if (wpid != null) {
			for (WorkPackage wp : getAllWp()) {
				String parentId = wp.getParentWPID();
				if (parentId != null && parentId.equals(wpid)) {
					result.add(wp);
				}
			}
		}
		return result;
	}

	public WorkPackage getParentWP(WorkPackage wp) {
		if (wp.getParentWPID() == null) {
			return null;
		}
		return this.manager.find(WorkPackage.class, new WorkPackagePK(wp.getProNo(), wp.getParentWPID()));
	}

	public List<WorkPackage> getRootWP() {
		List<WorkPackage> result = new ArrayList<WorkPackage>();
		for (WorkPackage wp : getAllWp()) {
			if (wp.getParentWPID() == null) {
				result.add(wp);
			}
		}
		return result;
	}

	public List<WorkPackage> getRootWPByProNo(int proNo) {
		System.out.println("PRO NO" + proNo);
		List<WorkPackage> result = new ArrayList<WorkPackage>();
		for (WorkPackage wp : getAllWp()) {
			if (wp.getParentWPID() == null && wp.getProNo() == proNo) {
				result.add(wp);

			}
		}

		return result;
	}

	public boolean persistWP(WorkPackage wp) {
		WorkPackage checkWp = this.manager.find(WorkPackage.class, wp.getWorkPackagePk());
		if (checkWp == null) {
			addEmpToWp(getEmployeeById(wp.getReEmpNo()), wp);
			this.manager.persist(wp);
			return true;
		}
		return false;
	}

	public boolean persistChildWP(WorkPackage parent, WorkPackage child) {
		child.setParentWPID(parent.getWpid());
		this.manager.persist(child);
		return true;
	}

	public boolean updateWP(WorkPackage wp) {
		WorkPackage checkWp = this.manager.find(WorkPackage.class, wp.getWorkPackagePk());
		System.out.println("what can be wrong" + checkWp);
		System.out.println("This is the OG wp" + wp);
		if (checkWp != null) {
			this.manager.merge(wp);
			return true;
		}
		return false;
	}

	public boolean deleteWorkPackage(final WorkPackage wp) {
		WorkPackage workpackage = this.manager.find(WorkPackage.class, wp.getWorkPackagePk());
		try {
			this.manager.remove(workpackage);
			this.manager.flush();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public List<WorkPackage> getWPByEmpNo(int empNo) {
		List<WorkPackage> wpList = new ArrayList<WorkPackage>();
		for (WorkPackage wp : getAllWp()) {
			if (wp.getReEmpNo() == empNo) {
				wpList.add(wp);
			}
		}
		return wpList;
	}

	public List<WPEmp> getAllWPEmp() {
		return this.manager.createQuery("SELECT wp from WPEmp wp", WPEmp.class).getResultList();
	}

	public WorkPackage getWPByID(WorkPackagePK pk) {
		return this.manager.find(WorkPackage.class, pk);
	}

	public List<WPEmp> getAllWPEmpByProNo(int proNo) {
		List<WPEmp> wpList = getAllWPEmp();
		for (WPEmp wp : wpList) {
			if (wp.getProNo() == proNo) {
				wpList.add(wp);
			}
		}
		return wpList;
	}

	public void addEmpToWp(Employee emp, WorkPackage wp) {
		WPEmp wpemp = new WPEmp();
		wpemp.setEmpNo(emp.getEmpNumber());
		wpemp.setProNo(wp.getProNo());
		wpemp.setWpid(wp.getWpid());
		this.manager.persist(wpemp);
	}

	public boolean deleteEmpFromWp(Employee emp, WorkPackage wp) {
		WPEmp wpemp = this.manager.find(WPEmp.class, new WPEmpPK(wp.getProNo(), wp.getWpid(), emp.getEmpNumber()));
		try {
			this.manager.remove(wpemp);
			this.manager.flush();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean persistWPEmp(WPEmp wpe) {
		WPEmp checkWp = this.manager.find(WPEmp.class, wpe.getPk());
		if (checkWp == null) {
			this.manager.persist(wpe);
			return true;
		}
		return false;
	}

	public boolean updateWPEmp(WPEmp wpe) {
		WPEmp checkWp = this.manager.find(WPEmp.class, wpe.getPk());
		if (checkWp == null) {
			this.manager.merge(wpe);
			return true;
		}
		return false;
	}

	public List<Employee> getEmpListByWP(WorkPackage wp) {
		List<WPEmp> wpeList = this.manager
				.createQuery("SELECT wp from WPEmp wp WHERE wp.pk.wpid = :wpid AND wp.pk.proNo = :proNo", WPEmp.class)
				.setParameter("proNo", wp.getProNo()).setParameter("wpid", wp.getWpid()).getResultList();

		List<Employee> result = new ArrayList<Employee>();
		for (WPEmp e : wpeList) {
			result.add(this.getEmployeeById(e.getEmpNo()));
		}
		return result;
	}

	public List<String> getAllEmpAssignedWpid(int proNo, int empNo) {
		return manager
				.createQuery("SELECT wp FROM WPEmp wp WHERE wp.pk.empNo = :empNo AND wp.pk.proNo = :proNo", WPEmp.class)
				.setParameter("proNo", proNo).setParameter("empNo", empNo).getResultList().stream().map(wp -> {
					return wp.getWpid();
				}).collect(Collectors.toList());
	}

	public WorkPackage getWPById(String wpid) {
		try {
			return manager
					.createQuery("SELECT wp FROM WorkPackage wp WHERE wp.workPackagePk.wpid = :wpid", WorkPackage.class)
					.setParameter("wpid", wpid).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// #########################################################################
	// # PLevel methods
	// #########################################################################
	public List<PLevel> getPLevels() {
		return manager.createQuery("SELECT p FROM PLevel p", PLevel.class).getResultList();
	}

	public List<PLevel> getPLevelByPK(Date startDate, String pLevel) {
		TypedQuery<PLevel> query = manager.createQuery(
				"select p from PLevel p where p.pLevelPK.startDate = :StartDate AND p.pLevelPK.pLevel = :PLevel",
				PLevel.class);
		query.setParameter("StartDate", startDate);
		query.setParameter("PLevel", pLevel);
		return query.getResultList();
	}

	public List<PLevel> getPLevelByLevel(String pLevel) {
		TypedQuery<PLevel> query = manager.createQuery("select p from PLevel p where  p.pLevelPK.pLevel = :PLevel",
				PLevel.class);

		query.setParameter("PLevel", pLevel);
		return query.getResultList();
	}

	public void updatePLevel(PLevel e) {
		manager.merge(e);
	}

	public void addPLevel(PLevel e) {
		manager.persist(e);
	}

	public void removePLevel(PLevel e) {
		manager.remove(manager.contains(e) ? e : manager.merge(e));
	}

	public Signature findSignature(TimesheetPK tpk) {
		return manager.find(Signature.class, tpk);
	}

	public void removeSignature(Signature sig) {
		manager.remove(manager.contains(sig) ? sig : manager.merge(sig));
	}

	public void addSignature(final Signature newSignature) {
		manager.persist(newSignature);
	}

	// #########################################################################
	// # Role methods
	// #########################################################################
	public boolean checkIfUserInRole(int empNo, String role) {
		List<Role> list = manager
				.createQuery("SELECT r FROM Role r WHERE r.rolePk.empNo = :empNo AND r.rolePk.role = :role", Role.class)
				.setParameter("empNo", empNo).setParameter("role", role).getResultList();

		return !list.isEmpty();
	}

	public boolean checkIfSupervisor(int empNumber) {
		List<Employee> list = manager
				.createQuery("SELECT e FROM Employee e WHERE e.superEmpNo = :number", Employee.class)
				.setParameter("number", empNumber).getResultList();
		if (list.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public boolean checkIfApprover(int empNo) {
		List<Employee> list = manager
				.createQuery("SELECT e FROM Employee e WHERE e.approEmpNo = :number", Employee.class)
				.setParameter("number", empNo).getResultList();

		return !list.isEmpty();
	}

	public List<EmpPLevel> getEmpPLevels() {
		return manager.createQuery("SELECT p FROM EmpPLevel p", EmpPLevel.class).getResultList();
	}

	public void addEmpPLevel(EmpPLevel e) {
		manager.persist(e);
	}

	public void updateEmpPLevel(EmpPLevel e) {
		manager.merge(e);
	}

	public void removeEmpPLevel(EmpPLevel ep) {
		manager.remove(manager.contains(ep) ? ep : manager.merge(ep));
	}

	public PLevel getPLevelByStartDate(Date startDate, String pLevel) {
		TypedQuery<PLevel> query = manager.createQuery(
				"select p from PLevel p where p.pLevelPK.startDate <= :StartDate AND p.pLevelPK.pLevel = :PLevel order by p.pLevelPK.startDate desc",
				PLevel.class);
		query.setParameter("StartDate", startDate);
		query.setParameter("PLevel", pLevel);
		query.setMaxResults(1);
		query.setFirstResult(0);
		return query.getSingleResult();
	}

	public List<Role> getRoles() {
		return manager.createQuery("SELECT p FROM Role p", Role.class).getResultList();
	}

	public void addRole(Role e) {
		manager.persist(e);
	}

	public void updateRole(Role e) {
		manager.merge(e);
	}

	public void removeRole(Role ep) {
		manager.remove(manager.contains(ep) ? ep : manager.merge(ep));
	}

	public WorkPackage getWpByPk(Integer proNo, String wpid) {
		TypedQuery<WorkPackage> query = manager.createQuery(
				"select p from WorkPackage p where p.workPackagePk.proNo = :proNo AND p.workPackagePk.wpid = :wpid",
				WorkPackage.class);
		query.setParameter("proNo", proNo);
		query.setParameter("wpid", wpid);
		return query.getSingleResult();
	}

	public List<WPNeed> getWPNeeds() {
		return manager.createQuery("SELECT p FROM WPNeed p", WPNeed.class).getResultList();
	}

	public WPNeed getWPNeedsByPK(Date startDate, String wpid, int proNo) {
		TypedQuery<WPNeed> query = manager.createQuery(
				"select p from WPNeed p where p.wpNeedPK.startDate = :StartDate AND p.wpNeedPK.proNo = :ProNo AND p.wpNeedPK.wpid = :WPID",
				WPNeed.class);
		query.setParameter("StartDate", startDate);
		query.setParameter("ProNo", proNo);
		query.setParameter("WPID", wpid);

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			WPNeed temp = new WPNeed(new WPNeedPK(proNo, startDate, wpid));
			addWPNeed(temp);
			return temp;
		}
	}

	public void updateWPNeed(WPNeed e) {
		manager.merge(e);
	}

	public void addWPNeed(WPNeed e) {
		manager.persist(e);
	}

	public void removeWPNeed(WPNeed e) {
		manager.remove(manager.contains(e) ? e : manager.merge(e));
	}

	public List<Integer> getAllProjectNoByRe() {
		Employee engineer = (Employee) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get(LoginController.USER_KEY);
		int reEmpNo = engineer.getEmpNumber();
		List<Integer> ids = manager
				.createQuery("SELECT p.workPackagePk.proNo from WorkPackage p where p.reEmpNo = :engineer",
						Integer.class)
				.setParameter("engineer", reEmpNo).getResultList();
		System.out.println(ids);
		List<Integer> result = new ArrayList<Integer>();
		for (Integer i : ids) {
			if (!result.contains(i)) {
				result.add(i);
			}
		}
		return result;
	}

	public long countTimesheets(int id) {
		Query q = manager.createQuery("SELECT COUNT(t) FROM Timesheet t WHERE t.timesheetPk.empNo = :empNumber");
		q.setParameter("empNumber", id);
		return (long) q.getSingleResult();
	}

	public long countApprovedTimesheets(int id) {
		Query q = manager.createQuery(
				"SELECT COUNT(t) FROM Timesheet t WHERE t.state = 'Approved' AND t.timesheetPk.empNo = :empNumber");
		q.setParameter("empNumber", id);
		return (long) q.getSingleResult();
	}

	public long countRejectedTimesheets(int id) {
		Query q = manager.createQuery(
				"SELECT COUNT(t) FROM Timesheet t WHERE t.state = 'Returned' AND t.timesheetPk.empNo = :empNumber");
		q.setParameter("empNumber", id);
		return (long) q.getSingleResult();
	}

	public List<WorkPackage> getAssignedWPByEmpNo(int empNo) {
		List<WPEmp> wpList = getAllWPEmp();
		List<WorkPackage> result = new ArrayList<WorkPackage>();
		for (WPEmp wp : wpList) {
			if (wp.getEmpNo() == empNo) {
				result.add(getWpByPk(wp.getProNo(), wp.getWpid()));
			}
		}
		return result;
	}
}
