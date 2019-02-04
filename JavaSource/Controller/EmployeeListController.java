package Controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;

import Model.Employee;

@Named("employeeListController")
@ViewScoped
public class EmployeeListController implements Serializable {

    @Inject
    private DatabaseController database;

    private List<Employee> employees;

    private Employee editEmployee;

    @PostConstruct
    public void init() {
        editEmployee = null;
        employees = database.getEmployees();
    }

    public void addEmployee(Integer empNo, String firstName, String lastName,
            String username, String password) {
        Employee e = new Employee(empNo, firstName, lastName, username,
                password);
        if (isValidEmployee(e)) {
            employees.add(e);
            database.addEmployee(e);
            PrimeFaces.current()
                    .executeScript("PF('addEmployeeDialog').hide();");
        } else {
            addErrorMessage("Duplicate found in employee number or username");
        }
    }

    public void editEmployee(Integer empNo, String firstName, String lastName,
            String username, String password) {
        Employee e = editEmployee;
        e.setEmpNumber(empNo);
        e.setFirstName(firstName);
        e.setLastName(lastName);
        e.setUserName(username);
        e.setPassword(password);
        
        database.updateEmployee(e);
        
        PrimeFaces.current().executeScript("PF('editEmployeeDialog').hide();");
    }

    public void deleteEmployee(Employee e) {
        employees.remove(e);
        database.removeEmployee(e);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Employee getEditEmployee() {
        return editEmployee;
    }

    public void setEditEmployee(Employee editEmployee) {
        this.editEmployee = editEmployee;

        PrimeFaces.current().executeScript("PF('editEmployeeDialog').show();");
    }

    private void addErrorMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private boolean isValidEmployee(Employee employee) {
        for (Employee e : employees) {
            if (e.getEmpNumber() == employee.getEmpNumber()) {
                return false;
            }

            if (e.getUserName().equals(employee.getUserName())) {
                return false;
            }
        }

        return true;
    }
}
