package Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {
    
    @Id
    @Column(name = "EMPLOYEE_ID")
    private int empNumber;
    
    @Column(name = "EMPLOYEE_FIRSTNAME")
    private String firstName;
    
    @Column(name = "EMPLOYEE_LASTNAME")
    private String lastName;
    
    @Column(name = "EMPLOYEE_USERNAME")
    private String userName;
    
    @Column(name = "EMPLOYEE_PASSWORD")
    private String password;
    
    public Employee() {}
    
    public Employee(int empNumber, String firstName, String lastName, String userName, String password) {
        this.empNumber = empNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }

    public int getEmpNumber() {
        return empNumber;
    }

    public void setEmpNumber(int empNumber) {
        this.empNumber = empNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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
