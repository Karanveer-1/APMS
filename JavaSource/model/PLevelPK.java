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
}
