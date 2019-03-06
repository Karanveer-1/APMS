package model;

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
    private Integer proNo;
    
    @Column(name = "WPID", nullable = false)
    private String wpid;
    
    public TimesheetRowPK() {}

    public TimesheetRowPK(int empNo, Date startDate, Integer proNo, String wpid) {
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

    public Integer getProNo() {
        return proNo;
    }

    public void setProNo(Integer proNo) {
        this.proNo = proNo;
    }

    public String getWpid() {
        return wpid;
    }

    public void setWpid(String wpid) {
        this.wpid = wpid;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + empNo;
        result = prime * result + ((proNo == null) ? 0 : proNo.hashCode());
        result = prime * result
                + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((wpid == null) ? 0 : wpid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TimesheetRowPK other = (TimesheetRowPK) obj;
        if (empNo != other.empNo)
            return false;
        if (proNo == null) {
            if (other.proNo != null)
                return false;
        } else if (!proNo.equals(other.proNo))
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        if (wpid == null) {
            if (other.wpid != null)
                return false;
        } else if (!wpid.equals(other.wpid))
            return false;
        return true;
    }
    
}
