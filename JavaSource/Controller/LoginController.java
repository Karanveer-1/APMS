package Controller;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import Model.Credential;
import Model.Employee;

import java.io.Serializable;
import java.util.List;

@Named("loginController")
@SessionScoped
public class LoginController implements Serializable {
    @Inject Credential credential;
    @PersistenceContext(unitName="apms") EntityManager manager;
    private Employee currentEmployee;
    
    public LoginController() {
        currentEmployee = null;
    }
    
    public String login() {
        @SuppressWarnings("unchecked")
        List<Employee> results = manager.createQuery(
           "select e from Employee e where e.userName = :username and e.password = :password")
           .setParameter("username", credential.getUserName())
           .setParameter("password", credential.getPassword())
           .getResultList();
        if (!results.isEmpty()) {
           currentEmployee = results.get(0);
        } else {
           // perhaps add code here to report a failed login
        }
        
        return "Dashboard.xhtml?faces-redirect=true";
     }

     public void logout() {
         currentEmployee = null;
     }

     public boolean isLoggedIn() {
        return currentEmployee != null;
     }
     
}
