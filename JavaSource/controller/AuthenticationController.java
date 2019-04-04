package controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.Employee;
import model.EmployeeRole;

@Named("authController")
@SessionScoped
public class AuthenticationController implements Serializable {
    
    @Inject
    private DatabaseController database;
    
    private Employee currentEmployee;

    @PostConstruct
    public void init() {
        currentEmployee = (Employee) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(LoginController.USER_KEY);
    }
    
    public boolean isUserSupervisor() {
        return database.checkIfSupervisor(currentEmployee.getEmpNumber());
    }
    
    public boolean isUserSystemAdmin() {
        return database.checkIfUserInRole(currentEmployee.getEmpNumber(), EmployeeRole.SYSTEM_ADMIN);
    }
    
    public boolean isUserHumanResource() {
        return database.checkIfUserInRole(currentEmployee.getEmpNumber(), EmployeeRole.HUMAN_RESOURCE);
    }
    
    public boolean isUserApprover() {
        return database.checkIfApprover(currentEmployee.getEmpNumber());
    }
}
