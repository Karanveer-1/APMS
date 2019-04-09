package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * WPNeedPK.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
@Embeddable
public class WPNeedPK implements Serializable {

    @Column(name = "ProNo", nullable = false)
    private int proNo;

    @Column(name = "StartDate", nullable = false)
    private Date startDate;

    @Column(name = "WPID", nullable = false)
    private String wpid;

    /**
     * Constructs an objecct of type WPNeedPK.
     */
    public WPNeedPK() {
        super();
    }

    /**
     * Constructs an objecct of type WPNeedPK.
     * @param proNo
     * @param startDate
     * @param wpid
     */
    public WPNeedPK(int proNo, Date startDate, String wpid) {
        super();
        this.proNo = proNo;
        this.startDate = startDate;
        this.wpid = wpid;
    }

    /**
     * Returns the {bare_field_name} for this WPNeedPK.
     * @return the proNo
     */
    public int getProNo() {
        return proNo;
    }

    /**
     * Sets the proNo for this WPNeedPK
     * @param proNo the proNo to set
     */
    public void setProNo(int proNo) {
        this.proNo = proNo;
    }

    /**
     * Returns the {bare_field_name} for this WPNeedPK.
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the startDate for this WPNeedPK
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns the {bare_field_name} for this WPNeedPK.
     * @return the wpid
     */
    public String getWpid() {
        return wpid;
    }

    /**
     * Sets the wpid for this WPNeedPK
     * @param wpid the wpid to set
     */
    public void setWpid(String wpid) {
        this.wpid = wpid;
    }
}
