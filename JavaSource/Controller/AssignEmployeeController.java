package controller;

import java.io.Serializable;
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
    
    private List<Employee> activeEmployees;
    private Employee editEmployee;

    @PostConstruct
    public void init() {
        activeEmployees = database.getActiveEmployees();
    }

    public List<Employee> getActiveEmployees() {
        return activeEmployees;
    }
    
    public Employee getEmployeeById(int id) {
        return database.getEmployeeById(id);
    }
    
    public void setAssignEmployee(Employee e) {
        convo.begin();
        this.setEditEmployee(e);
        PrimeFaces.current().executeScript("PF('editEmployeeDialog').show();");
    }
    
    public void updateApprover(int supervisorId, int approverId) {
        this.editEmployee.setApproEmpNo(approverId);
        this.editEmployee.setSuperEmpNo(supervisorId);
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