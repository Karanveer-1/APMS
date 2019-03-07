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
     * @return the sun
     */
    public float getWage() {
        return wage;
    }

    /**
     * Sets the sun for this PLevel
     * @param sun the sun to set
     */
    public void setSun(float wage) {
        this.wage = wage;
    }

}
