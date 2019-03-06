package controller;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.Credential;
import model.Employee;

import java.io.Serializable;

@Named("loginController")
@SessionScoped
public class LoginController implements Serializable {

    public static final String USER_KEY = "user";

    @Inject
    Credential credential;
    @Inject
    DatabaseController database;

    private Employee currentEmployee;

    public LoginController() {
        currentEmployee = null;
    }

    public String login() {
        Employee result = database
                .getEmployeeByUsername(credential.getUserName());

        if (result != null) {
            currentEmployee = result;
            FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().put(USER_KEY, currentEmployee);
            return "Dashboard.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Failed to login.", null));
            return null;
        }
    }

    public String logout() {
        currentEmployee = null;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .remove(USER_KEY);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "Login.xhtml?faces-redirect=true";
    }

    public boolean isLoggedIn() {
        return currentEmployee != null;
    }

    public void checkIfLoggedIn() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (!isLoggedIn()) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Please login to access the page.", null));

            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) context
                    .getApplication().getNavigationHandler();
            nav.performNavigation("Login");
        }
    }

    public Employee getCurrentEmployee() {
        return currentEmployee;
    }

    public void setCurrentEmployee(Employee currentEmployee) {
        this.currentEmployee = currentEmployee;
    }
}
