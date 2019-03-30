package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * EmpPLevelPK.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
@Embeddable
public class EmpPLevelPK implements Serializable {


    @Column(name = "EmpNo", nullable = false)
    private int empNo;

    @Column(name = "StartDate", nullable = false)
    private Date startDate;

    /**
     * Constructs an objecct of type EmpPLevelPK.
     */
    public EmpPLevelPK() {
        super();
    }

    /**
     * Constructs an objecct of type EmpPLevelPK.
     * @param empNo
     * @param startDate
     */
    public EmpPLevelPK(int empNo, Date startDate) {
        super();
        this.empNo = empNo;
        this.startDate = startDate;
    }

    /**
     * Returns the {bare_field_name} for this EmpPLevelPK.
     * @return the empNo
     */
    public int getEmpNo() {
        return empNo;
    }

    /**
     * Sets the empNo for this EmpPLevelPK
     * @param empNo the empNo to set
     */
    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    /**
     * Returns the {bare_field_name} for this EmpPLevelPK.
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the startDate for this EmpPLevelPK
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @see java.lang.Object#hashCode()
     * @return
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + empNo;
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EmpPLevelPK other = (EmpPLevelPK) obj;
        if (empNo != other.empNo)
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        return true;
    }
}
