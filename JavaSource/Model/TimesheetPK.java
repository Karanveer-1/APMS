package Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class TimesheetPK implements Serializable {

    @Column(name = "EmpNo", nullable = false)
    private int empNo;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "StartDate", nullable = false)
    private Date startDate;
    
    public TimesheetPK() {}

    public TimesheetPK(int empNo, Date startDate) {
        super();
        this.empNo = empNo;
        this.startDate = startDate;
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
}
