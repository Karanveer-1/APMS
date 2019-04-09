package controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import model.Employee;
import model.Role;
import model.RolePK;
import model.Role;

/**
 * RoleController.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
@Named("roleController")
@SessionScoped
public class RoleController implements Serializable {

    @Inject
    private DatabaseController database;

    private List<Role> roles;    
    private List<Employee> employees;
    private List<String> usernames;
    private String[] possibleRoles = {"SYSTEM ADMIN", "HUMAN RESOURCE"};

    @PostConstruct
    public void init() {
        roles = database.getRoles();
        employees = database.getEmployees();
        usernames = new ArrayList<String>();
        for(Employee e : employees) {
            usernames.add(e.getUserName());
        }
    }

    /**
     * Returns the {bare_field_name} for this RoleController.
     * @return the roles
     */
    public List<Role> getRoles() {
        roles = database.getRoles();
        return roles;
    }

    /**
     * Sets the roles for this RoleController
     * @param roles the roles to set
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    public void closeDialog() {
        PrimeFaces.current().executeScript("PF('errorDialog').hide();");
    }
    
    public void remove(Role role) {
        database.removeRole(role);
        roles = database.getRoles();
    }
    
    public Employee getEmployeeByUsername(String username) {
        for(Employee e : database.getEmployees()) {
            if(e.getUserName().equals(username)) {
                return e;
            }
        }
        return null;
        
    }
    
    public void add(String username, String role) {
        roles = database.getRoles();  
        Employee selected = getEmployeeByUsername(username);
        boolean validPK = true;
        for(Role p : roles) {
            System.out.println(p.getRolePk().getRole() + " : " + role);
            System.out.println(p.getRolePk().getRole().equals(role));
            
            if(p.getRolePk().getRole().equals(role) && p.getRolePk().getEmpNo() == selected.getEmpNumber()) {
                validPK = false;
                PrimeFaces.current().executeScript("PF('errorDialog').show();");
                break;
            }
        }
        if(validPK) {
            database.addRole(new Role(new RolePK(selected.getEmpNumber(), role)));
        }
        roles = database.getRoles();  
        PrimeFaces.current().executeScript("PF('addRoleDialog').hide();");
    }

    /**
     * Returns the {bare_field_name} for this RoleController.
     * @return the employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the employees for this RoleController
     * @param employees the employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Returns the {bare_field_name} for this RoleController.
     * @return the usernames
     */
    public List<String> getUsernames() {
        return usernames;
    }

    /**
     * Sets the usernames for this RoleController
     * @param usernames the usernames to set
     */
    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    /**
     * Returns the {bare_field_name} for this RoleController.
     * @return the possibleRoles
     */
    public String[] getPossibleRoles() {
        return possibleRoles;
    }

    /**
     * Sets the possibleRoles for this RoleController
     * @param possibleRoles the possibleRoles to set
     */
    public void setPossibleRoles(String[] possibleRoles) {
        this.possibleRoles = possibleRoles;
    }
    
}
