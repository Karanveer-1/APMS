package controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.Employee;

@Named("timesheetAuthController")
@SessionScoped
public class TimesheetAuthenticationController implements Serializable {
    
    @Inject
    private DatabaseController database;
    
    private Employee currentEmployee;

    @PostConstruct
    public void init() {
        currentEmployee = (Employee) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(LoginController.USER_KEY);
    }
    
    public boolean isUserInRole(String role) {
        String userRole = database.getRoleById(currentEmployee.getEmpNumber());
        
        if (userRole.equalsIgnoreCase(role)) {
            return true;
        }
        
        return false;
    }
}
