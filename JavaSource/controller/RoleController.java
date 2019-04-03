package controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

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

    @PostConstruct
    public void init() {
        roles = database.getRoles();
        System.out.println(roles);
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
    
    public void add(int empNo, String role) {
        roles = database.getRoles();  
        boolean validPK = true;
        for(Role p : roles) {
            System.out.println(p.getRolePk().getRole() + " : " + role);
            System.out.println(p.getRolePk().getRole().equals(role));
            
            if(p.getRolePk().getRole().equals(role) && p.getRolePk().getEmpNo() == empNo) {
                validPK = false;
                PrimeFaces.current().executeScript("PF('errorDialog').show();");
                break;
            }
        }
        if(validPK) {
            database.addRole(new Role(new RolePK(empNo, role)));
        }
        roles = database.getRoles();  
        PrimeFaces.current().executeScript("PF('addRoleDialog').hide();");
    }
    
}
