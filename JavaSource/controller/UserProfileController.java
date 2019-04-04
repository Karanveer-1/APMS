package controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import model.Employee;
import model.Project;
import model.Role;
import model.WorkPackage;
import service.PasswordHash;
import service.PasswordHash.CannotPerformOperationException;

/**
 * PLevelController.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
@Named("userProfileController")
@SessionScoped
public class UserProfileController implements Serializable {

    @Inject
    private DatabaseController database;
    private Employee currentEmployee;
    private List<Role> roles;
    private List<Project> projects;
    private List<WorkPackage> workpackages;
    private List<Employee> superEmployees;
    private List<Employee> approEmployees;
    private String password;

    @PostConstruct
    public void init() {
        currentEmployee = (Employee) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(LoginController.USER_KEY);
        roles = database.getRolesByEmpNo(currentEmployee.getEmpNumber());
        projects = database.getAllProjectsbyEmpNo(currentEmployee.getEmpNumber());
        workpackages = database.getWPByEmpNo(currentEmployee.getEmpNumber());
        superEmployees = database.getAllSupervisedEmployees(currentEmployee.getEmpNumber());
        superEmployees = database.getAllApproEmployees(currentEmployee.getEmpNumber());
        password = "";
    }

    /**
     * Returns the {bare_field_name} for this UserProfileController.
     * @return the database
     */
    public DatabaseController getDatabase() {
        return database;
    }

    private boolean isNullOrWhitespace(String v) {
        if (v == null || v.trim().length() == 0) {
            return true;
        }

        return false;
    }

    public void changePassword() {
        if(!isNullOrWhitespace(password)) {
            try {
                password = PasswordHash.createHash(password);
            } catch (CannotPerformOperationException e) {
                e.printStackTrace();
            }
            currentEmployee.setPassword(password);
            database.updateEmployee(currentEmployee);
            PrimeFaces.current().executeScript("PF('passwordChangedDialog').show();");
        } else {
            System.out.println("WHITE SPACE PASSWORD");
        }
    }
    
    public void closeDialog() {
        PrimeFaces.current().executeScript("PF('passwordChangedDialog').hide();");
    }

    /**
     * Sets the database for this UserProfileController
     * @param database the database to set
     */
    public void setDatabase(DatabaseController database) {
        this.database = database;
    }

    /**
     * Returns the {bare_field_name} for this UserProfileController.
     * @return the currentEmployee
     */
    public Employee getCurrentEmployee() {
        return currentEmployee;
    }

    /**
     * Sets the currentEmployee for this UserProfileController
     * @param currentEmployee the currentEmployee to set
     */
    public void setCurrentEmployee(Employee currentEmployee) {
        this.currentEmployee = currentEmployee;
    }

    /**
     * Returns the {bare_field_name} for this UserProfileController.
     * @return the roles
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * Sets the roles for this UserProfileController
     * @param roles the roles to set
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * Returns the {bare_field_name} for this UserProfileController.
     * @return the projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Sets the projects for this UserProfileController
     * @param projects the projects to set
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Returns the {bare_field_name} for this UserProfileController.
     * @return the workpackages
     */
    public List<WorkPackage> getWorkpackages() {
        return workpackages;
    }

    /**
     * Sets the workpackages for this UserProfileController
     * @param workpackages the workpackages to set
     */
    public void setWorkpackages(List<WorkPackage> workpackages) {
        this.workpackages = workpackages;
    }

    /**
     * Returns the {bare_field_name} for this UserProfileController.
     * @return the superEmployees
     */
    public List<Employee> getSuperEmployees() {
        return superEmployees;
    }

    /**
     * Sets the superEmployees for this UserProfileController
     * @param superEmployees the superEmployees to set
     */
    public void setSuperEmployees(List<Employee> superEmployees) {
        this.superEmployees = superEmployees;
    }

    /**
     * Returns the {bare_field_name} for this UserProfileController.
     * @return the approEmployees
     */
    public List<Employee> getApproEmployees() {
        return approEmployees;
    }

    /**
     * Sets the approEmployees for this UserProfileController
     * @param approEmployees the approEmployees to set
     */
    public void setApproEmployees(List<Employee> approEmployees) {
        this.approEmployees = approEmployees;
    }

    /**
     * Returns the {bare_field_name} for this UserProfileController.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for this UserProfileController
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }



}