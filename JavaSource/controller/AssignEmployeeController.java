package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import model.Employee;

@Named("assign")
@ConversationScoped
public class AssignEmployeeController implements Serializable {
    @Inject
    private DatabaseController database;
    @Inject 
    private Conversation convo;
    @Inject
    private LoginController login;
    
    private List<Employee> activeEmployees;
    private Employee editEmployee;

    @PostConstruct
    public void init() {
        activeEmployees = database.getActiveEmployees();
    }

    public List<Employee> getActiveEmployees() {
        return activeEmployees;
    }
    
    public List<Employee> getEmployeesAssignedToSupervisor() {
        List<Employee> assignedEmployees = new ArrayList<Employee>();
        for(Employee e : activeEmployees) {
            if (e.getSuperEmpNo() == login.getCurrentEmployee().getEmpNumber()) {
                assignedEmployees.add(e);
            }
        }
        
        return assignedEmployees;
    }
    
    public Employee getEmployeeById(int id) {
        return database.getEmployeeById(id);
    }
    
    public void setAssignEmployee(Employee e) {
        convo.begin();
        this.setEditEmployee(e);
        PrimeFaces.current().executeScript("PF('editEmployeeDialog').show();");
    }
    
    public void updateApprover(int approverId) {
        this.editEmployee.setApproEmpNo(approverId);
        database.updateEmployee(editEmployee);
        PrimeFaces.current().executeScript("PF('editEmployeeDialog').hide();");
        convo.end();
    }

    public Employee getEditEmployee() {
        return editEmployee;
    }

    public void setEditEmployee(Employee editEmployee) {
        this.editEmployee = editEmployee;
    }
}