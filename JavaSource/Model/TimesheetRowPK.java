package Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TimesheetRowPK implements Serializable {

    @Column(name = "EmpNo", nullable = false)
    private int empNo;
    
    @Column(name = "StartDate", nullable = false)
    private Date startDate;
    
    @Column(name = "ProNo", nullable = false)
    private int proNo;
    
    @Column(name = "WPID", nullable = false)
    private String wpid;
    
    public TimesheetRowPK() {}

    public TimesheetRowPK(int empNo, Date startDate, int proNo, String wpid) {
        super();
        this.empNo = empNo;
        this.startDate = startDate;
        this.proNo = proNo;
        this.wpid = wpid;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getProNo() {
        return proNo;
    }

    public void setProNo(int proNo) {
        this.proNo = proNo;
    }

    public String getWpid() {
        return wpid;
    }

    public void setWpid(String wpid) {
        this.wpid = wpid;
    }
    
    
}
