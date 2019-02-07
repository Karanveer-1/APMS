package Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
public class Employee implements Serializable {

    @Id
    @Column(name = "EmpNo", nullable = false)
    private int empNumber;

    @Column(name = "EmpFirstName", nullable = false)
    private String firstName;

    @Column(name = "EmpLastName", nullable = false)
    private String lastName;

    @Column(name = "EmpUserName", nullable = false)
    private String userName;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "PLevel", nullable = false)
    private String pLevel;

    @Column(name = "State", nullable = false)
    private String State;

    @Column(name = "Comment")
    private String comment;

    public Employee() {
    }

    public Employee(int empNumber, String firstName, String lastName,
            String userName, String password, String pLevel, String state,
            String comment) {
        super();
        this.empNumber = empNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.pLevel = pLevel;
        State = state;
        this.comment = comment;
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

    public String getpLevel() {
        return pLevel;
    }

    public void setpLevel(String pLevel) {
        this.pLevel = pLevel;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (!o.getClass().equals(this.getClass())) {
            return false;
        }
        
        if (!(o instanceof Employee)) {
            return false;
        }
        
        Employee e = (Employee) o;
        
        if (e.getEmpNumber() == this.getEmpNumber()) {
            return true;
        }
        
        return false;
    }
}
