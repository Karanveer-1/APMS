package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * PLevel.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
@Entity
@Table(name = "PLevel")
public class PLevel implements Serializable {
    
    @EmbeddedId
    private PLevelPK pLevelPK;

    @Column(name = "Wage")
    private float wage;

    /**
     * Returns the {bare_field_name} for this PLevel.
     * @return the pLevelPK
     */
    public PLevelPK getpLevelPK() {
        return pLevelPK;
    }

    /**
     * Sets the pLevelPK for this PLevel
     * @param pLevelPK the pLevelPK to set
     */
    public void setpLevelPK(PLevelPK pLevelPK) {
        this.pLevelPK = pLevelPK;
    }

    /**
     * Returns the {bare_field_name} for this PLevel.
     * @return the wage
     */
    public float getWage() {
        return wage;
    }

    /**
     * Sets the wage for this PLevel
     * @param wage the wage to set
     */
    public void setWage(float wage) {
        this.wage = wage;
    }

    
    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof PLevel)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        PLevel c = (PLevel) o; 
          
        // Compare the data members and return accordingly  
        return c.getpLevelPK().getpLevel().equals(this.getpLevelPK().getpLevel()) && c.getpLevelPK().getStartDate().equals(this.getpLevelPK().getStartDate());
    } 


}
