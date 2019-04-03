package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import model.EmpPLevel;
import model.Employee;
import model.PLevel;
//import model.ProAssi;
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
import model.WorkPackage;
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
    private List<TimesheetRow> getTimesheetRows() {
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
    
    public List<Project> getAllProjectsbyEmpNo(int empNo) {
        List<Project> projects = getAllProjects();
        
        TypedQuery<ProEmp> query = manager.createQuery("select p from ProEmp p where p.proEmp.empNo = :empNo",
                ProEmp.class);
        query.setParameter("empNo", empNo);
        List<ProEmp> proEmps = query.getResultList();
        List<Project> temp = new ArrayList<Project>();
        for(ProEmp p : proEmps) {
          for(Project pro : projects) {
              if(p.getProEmp().getProNo() == pro.getProNo()) {
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

    public Project findByProjectNo(final int proNo) {
        return this.manager.find(Project.class, proNo);
    }

    public boolean persistProject(Project newProject) {
        Project p = this.manager.find(Project.class, newProject.getProNo());

        if (p == null) {
            this.manager.persist(newProject);
            return true;
        }

        return false;
    }

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

    public List<Role> getRolesByEmpNo(int empNo) {
        TypedQuery<Role> query = manager.createQuery("select p from Role p where p.rolePk.empNo = :empNo",
            Role.class);
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

    // public List<ProAssi> getAllProAssi() {
    // List<ProAssi> projects = this.manager.createQuery("SELECT p from ProAssi p",
    // ProAssi.class).getResultList();
    // return projects;
    // }
    //
    // public List<ProAssi> getProAssiByProNo(int proNo) {
    // List<ProAssi> pa = new ArrayList<ProAssi>();
    // for (ProAssi p : getAllProAssi()) {
    // if (p.getPk().getProNo() == proNo) {
    // pa.add(p);
    // }
    // }
    // return pa;
    //
    // }
    //
    // public List<Employee> getEmployeeByProAssi(int proNo) {
    // List<Employee> el = new ArrayList<Employee>();
    // System.out.println("Hel" + getProAssiByProNo(proNo));
    // for (ProAssi proass : getProAssiByProNo(proNo)) {
    // System.out.println("Emp" + getEmployeeById(proass.getPk().getEmpNo()));
    // el.add(getEmployeeById(proass.getPk().getEmpNo()));
    // }
    // return el;
    // }
    //
    // public boolean persistProAss(ProAssi proass) {
    // ProAssi p = this.manager.find(ProAssi.class, proass);
    // if (p == null) {
    // this.manager.persist(proass);
    // return true;
    // }
    // return false;
    // }
    //
    // public boolean deleteProAss(ProAssi proAssi) {
    // if (getProAssiByProNo(proAssi.getPk().getProNo()) != null) {
    // try {
    // this.manager.remove(proAssi);
    // this.manager.flush();
    // return true;
    //
    // } catch (Exception e) {
    // return false;
    // }
    // }
    // return false;
    // }

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

    // public List<Project> getProjectsByAssistantNo(int id) {
    // List<Project> result = new ArrayList<Project>();
    //
    // List<ProAssi> allPa = getAllProAssi();
    // for (ProAssi pa : allPa) {
    // if (pa.getEmpNo() == id) {
    // result.add(findByProjectNo(pa.getProNo()));
    // }
    // }
    // return result;
    //
    // }

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

    // #########################################################################
    // # PLevel methods
    // #########################################################################
    public List<PLevel> getPLevels() {
        return manager.createQuery("SELECT p FROM PLevel p", PLevel.class).getResultList();
    }

    public PLevel getPLevelByPK(Date startDate, String pLevel) {
        TypedQuery<PLevel> query = manager.createQuery(
            "select p from PLevel p where p.StartDate = :StartDate AND p.PLevel = :PLevel", PLevel.class);
        query.setParameter("StartDate", startDate);
        query.setParameter("PLevel", pLevel);
        return query.getSingleResult();
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
            .setParameter("empNo", empNo)
            .setParameter("role", role)
            .getResultList();

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
        return manager.createQuery("SELECT p FROM EmpPLevel p", EmpPLevel.class)
            .getResultList();
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

    public List<Role> getRoles() {
        return manager.createQuery("SELECT p FROM Role p", Role.class)
            .getResultList();
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


}
