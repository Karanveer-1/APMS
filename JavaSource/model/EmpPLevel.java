package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * EmpPLevel.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
@Entity
@Table(name = "EmpPLevel")
public class EmpPLevel implements Serializable {

    @EmbeddedId
    private EmpPLevelPK empPLevelPK;

    @Column(name = "PLevel")
    private String pLevel;

    /**
     * Returns the {bare_field_name} for this EmpPLevel.
     * @return the empPLevelPK
     */
    public EmpPLevelPK getEmpPLevelPK() {
        return empPLevelPK;
    }

    /**
     * Sets the empPLevelPK for this EmpPLevel
     * @param empPLevelPK the empPLevelPK to set
     */
    public void setEmpPLevelPK(EmpPLevelPK empPLevelPK) {
        this.empPLevelPK = empPLevelPK;
    }

    /**
     * Returns the {bare_field_name} for this EmpPLevel.
     * @return the pLevel
     */
    public String getpLevel() {
        return pLevel;
    }

    /**
     * Sets the pLevel for this EmpPLevel
     * @param pLevel the pLevel to set
     */
    public void setpLevel(String pLevel) {
        this.pLevel = pLevel;
    }

    /**
     * Constructs an objecct of type EmpPLevel.
     * @param empPLevelPK
     * @param pLevel
     */
    public EmpPLevel(EmpPLevelPK empPLevelPK, String pLevel) {
        super();
        this.empPLevelPK = empPLevelPK;
        this.pLevel = pLevel;
    }

    /**
     * Constructs an objecct of type EmpPLevel.
     */
    public EmpPLevel() {
        super();
    }

    /**
     * @see java.lang.Object#hashCode()
     * @return
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((empPLevelPK == null) ? 0 : empPLevelPK.hashCode());
        result = prime * result + ((pLevel == null) ? 0 : pLevel.hashCode());
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
        EmpPLevel other = (EmpPLevel) obj;
        if (empPLevelPK == null) {
            if (other.empPLevelPK != null)
                return false;
        } else if (!empPLevelPK.equals(other.empPLevelPK))
            return false;
        if (pLevel == null) {
            if (other.pLevel != null)
                return false;
        } else if (!pLevel.equals(other.pLevel))
            return false;
        return true;
    }

    
}
