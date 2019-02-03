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
        employees.add(new Employee(empNo, firstName, lastName, username, password));
        addMessage("Welcome to Primefaces!!");
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
