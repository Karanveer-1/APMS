package Model;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("credential")
@RequestScoped
public class Credential {    
    private String userName;
    private String password;
    
    public Credential() { }
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
