package Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
public class Employee {
    
    @Id
    @Column(name = "EmpNo")
    private int empNumber;
    
    @Column(name = "EmpFirstName")
    private String firstName;
    
    @Column(name = "EmpLastName")
    private String lastName;
    
    @Column(name = "EmpUserName")
    private String userName;
    
    @Column(name = "Password")
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
