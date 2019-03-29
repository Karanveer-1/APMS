package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RolePK implements Serializable {

    @Column(name="EmpNo")
    private int empNo;
    
    @Column(name="Role")
    private String role;
    
    public RolePK() {
        
    }

    public RolePK(int empNo, String role) {
        super();
        this.empNo = empNo;
        this.role = role;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    
}
