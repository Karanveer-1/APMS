package controller;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import model.Employee;
import model.Project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("assignProject")
@SessionScoped
public class AssignProjectController implements Serializable {
    @Inject
    private DatabaseController database;
    @Inject 
    private Conversation convo;
    @Inject
    private AssignEmployeeController assign;
    
    private Project project;
    
    public String addEmployees(Project p) {
        convo.begin();
        this.project = p;
        return "AddEmployeeToProject.xhtml?faces-redirect=true";
    }
    
    public void assignEmployee(int employeeNumber) {
        database.addNewEmployeeToProject(employeeNumber, project.getProNo());
        PrimeFaces.current().executeScript("PF('addEmployeeDialog').hide();");
    }
    
    public void checkIfHaveAnyEmployee() {
        if(getSupervisorEmployee().isEmpty()) {
            addErrorMessage("No employees to assign");
            return;
        }
        PrimeFaces.current().executeScript("PF('addEmployeeDialog').show();");
    }
    
    public List<Employee> getSupervisorEmployee() {
        List<Employee> supervisorEmployees = assign.getEmployeesAssignedToSupervisor();
        List<Employee> alreadyAssigned = getAssignedEmployee();
        List<Employee> temp = new ArrayList<>(); 
        
        for(Employee e : supervisorEmployees) {
            if(!alreadyAssigned.contains(e) && e.getEmpNumber() != project.getProMgrEmpNo()) {
                temp.add(e);
            }
        }
        return temp;
    }
    
    private void addErrorMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public List<Employee> getAssignedEmployee() {
        return database.getEmployeeForProject(project.getProNo());
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
    
    
}
