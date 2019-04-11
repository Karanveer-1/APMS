package controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SessionScoped
@Named("reportNavController")
public class ReportNavController implements Serializable {
    
    @Inject
    private AuthenticationController auth;
    
    
    public String redirect() {
        if (auth.isUserResponsibleEngineer()) {
            return "WPEffortReport.xhtml?faces-redirect=true";
        } else if (auth.isUserProjectManager() || auth.isUserSystemAdmin()){
            return "WPWeeklyReport.xhtml?faces-redirect=true";
        } else {
            return null;
        }
    }
}
