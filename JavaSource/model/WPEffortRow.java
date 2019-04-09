package model;

import java.util.Arrays;
import java.util.Date;

/**
 * WPEffortRow.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
public class WPEffortRow {

    Date startDate;
    String[] pLevels;
    float[] calculatedHours;
    WPNeed wpNeed;
    /**
     * Constructs an objecct of type WPEffortRow.
     * @param startDate
     * @param pLevels
     * @param calculatedHours
     */
    public WPEffortRow(Date startDate, String[] pLevels, float[] calculatedHours, int proNo, String wpid) {
        super();
        this.startDate = startDate;
        this.pLevels = pLevels;
        this.calculatedHours = calculatedHours;
        this.wpNeed = new WPNeed(new WPNeedPK(proNo, startDate, wpid));
    }

    /**
     * Constructs an objecct of type WPEffortRow.
     * @param startDate
     * @param pLevels
     * @param calculatedHours
     * @param wpNeed
     */
    public WPEffortRow(Date startDate, String[] pLevels, float[] calculatedHours, WPNeed wpNeed) {
        super();
        this.startDate = startDate;
        this.pLevels = pLevels;
        this.calculatedHours = calculatedHours;
        this.wpNeed = wpNeed;
    }
    /**
     * Returns the {bare_field_name} for this WPEffortRow.
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }
    /**
     * Sets the startDate for this WPEffortRow
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    /**
     * Returns the {bare_field_name} for this WPEffortRow.
     * @return the pLevels
     */
    public String[] getpLevels() {
        return pLevels;
    }
    /**
     * Sets the pLevels for this WPEffortRow
     * @param pLevels the pLevels to set
     */
    public void setpLevels(String[] pLevels) {
        this.pLevels = pLevels;
    }
    /**
     * Returns the {bare_field_name} for this WPEffortRow.
     * @return the calculatedHours
     */
    public float[] getCalculatedHours() {
        return calculatedHours;
    }
    /**
     * Sets the calculatedHours for this WPEffortRow
     * @param calculatedHours the calculatedHours to set
     */
    public void setCalculatedHours(float[] calculatedHours) {
        this.calculatedHours = calculatedHours;
    }

    /**
     * Returns the {bare_field_name} for this WPEffortRow.
     * @return the wpNeed
     */
    public WPNeed getWpNeed() {
        return wpNeed;
    }

    /**
     * Sets the wpNeed for this WPEffortRow
     * @param wpNeed the wpNeed to set
     */
    public void setWpNeed(WPNeed wpNeed) {
        this.wpNeed = wpNeed;
    }

    /**
     * @see java.lang.Object#toString()
     * @return
     */
    @Override
    public String toString() {
        return "WPEffortRow [startDate=" + startDate + ", pLevels=" + Arrays.toString(pLevels) + ", calculatedHours="
                + Arrays.toString(calculatedHours) + ", wpNeed=" + wpNeed + "]";
    }
    

    
    
}
