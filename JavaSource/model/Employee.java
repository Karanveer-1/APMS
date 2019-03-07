package model;

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
      
    @Column(name = "SuperEmpNo", nullable = false)
    private int superEmpNo;
    
    @Column(name = "ApproEmpNo", nullable = false)
    private int approEmpNo;

    @Column(name = "State", nullable = false)
    private String state;

    @Column(name = "Comment")
    private String comment;

    public Employee() {
    }

    public Employee(int empNumber, String firstName, String lastName,
            String userName, String password, String state,
            String comment) {
        super();
        this.empNumber = empNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.state = state;
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
    
    public int getSuperEmpNo() {
        return superEmpNo;
    }

    public void setSuperEmpNo(int superEmpNo) {
        this.superEmpNo = superEmpNo;
    }

    public int getApproEmpNo() {
        return approEmpNo;
    }

    public void setApproEmpNo(int approEmpNo) {
        this.approEmpNo = approEmpNo;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
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
