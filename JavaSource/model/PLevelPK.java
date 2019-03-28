package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PLevelPK.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
@Embeddable
public class PLevelPK implements Serializable {
    
    @Column(name = "PLevel", nullable = false)
    private String pLevel;
    
    @Column(name = "StartDate", nullable = false)
    private Date startDate;

    
    public PLevelPK(String pk, Date sd) {
        this.pLevel = pk;
        this.startDate = sd;
    }
    
    public PLevelPK() {
        
    }
    /**
     * Returns the {bare_field_name} for this PLevelPK.
     * @return the pLevel
     */
    public String getpLevel() {
        return pLevel;
    }

    /**
     * Sets the pLevel for this PLevelPK
     * @param pLevel the pLevel to set
     */
    public void setpLevel(String pLevel) {
        this.pLevel = pLevel;
    }

    /**
     * Returns the {bare_field_name} for this PLevelPK.
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the startDate for this PLevelPK
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pLevel == null) ? 0 : pLevel.hashCode());
        result = prime * result
                + ((startDate == null) ? 0 : startDate.hashCode());
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
        PLevelPK other = (PLevelPK) obj;
        if (pLevel == null) {
            if (other.pLevel != null)
                return false;
        } else if (!pLevel.equals(other.pLevel))
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        return true;
    }
}
