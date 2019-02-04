package Controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import Model.Employee;

@Named("employeeListController")
@RequestScoped
public class EmployeeListController implements Serializable {
    
    @Inject DatabaseController database;
    
    private List<Employee> employees;
    
    @PostConstruct
    public void init() {
        employees = database.getEmployees();
    }
    
    public void addEmployee(Integer empNo, String firstName, String lastName, String username, String password) {
        Employee e = new Employee(empNo, firstName, lastName, username, password);
        if (isValidEmployee(e)) {
            employees.add(e);
            database.addEmployee(e);
        } else {
            addErrorMessage("Duplicate found in employee number or username");
        }
    }
    
    public void editEmployee(Employee e, Integer empNo, String firstName, String lastName, String username, String password) {
        e.setEmpNumber(empNo);
        e.setFirstName(firstName);
        e.setLastName(lastName);
        e.setUserName(username);
        e.setPassword(password);
    }
    
    public void deleteEmployee(Employee e) {
        employees.remove(e);
        database.removeEmployee(e);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
    private void addErrorMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
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







/**
 * */
