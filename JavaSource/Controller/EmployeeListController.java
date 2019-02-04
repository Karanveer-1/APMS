package Controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import Model.Employee;

@Named("employeeListController")
@SessionScoped
public class EmployeeListController implements Serializable {
    
    @PersistenceContext(unitName="apms")
    private EntityManager manager;
    
    private List<Employee> employees;
    
    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() {
        employees = manager.createQuery("select e from Employee e").getResultList();
    }
    
    public void addEmployee(Integer empNo, String firstName, String lastName, String username, String password) {
        Employee e = new Employee(empNo, firstName, lastName, username, password);
        if (isValidEmployee(e)) {
            employees.add(e);
        } else {
            addErrorMessage("Duplicate found in employee number or username");
        }
    }
    
    public void deleteEmployee(Employee e) {
        employees.remove(e);
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
