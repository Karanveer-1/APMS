package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * WPNeed.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
@Entity
@Table(name = "WPNeed")
public class WPNeed implements Serializable {

    @EmbeddedId
    private WPNeedPK wpNeedPK;

    @Column(name = "RENeedP1")
    private int reNeedP1;
    
    @Column(name = "RENeedP2")
    private int reNeedP2;
    
    @Column(name = "RENeedP3")
    private int reNeedP3;
    
    @Column(name = "RENeedP4")
    private int reNeedP4;
    
    @Column(name = "RENeedP5")
    private int reNeedP5;
    
    @Column(name = "RENeedP6")
    private int reNeedP6;
    
    @Column(name = "RENeedDS")
    private int reNeedDS;
    
    @Column(name = "RENeedJS")
    private int reNeedJS;
    
    @Column(name = "RENeedSS")
    private int reNeedSS;
    
    @Column(name = "State")
    private String state;
    
    @Column(name = "Comment")
    private String comment;

    /**
     * Constructs an objecct of type WPNeed.
     */
    public WPNeed() {
        super();
    }

    /**
     * Constructs an objecct of type WPNeed.
     * @param wpNeedPK
     * @param reNeedP1
     * @param reNeedP2
     * @param reNeedP3
     * @param reNeedP4
     * @param reNeedP5
     * @param reNeedP6
     * @param reNeedDS
     * @param reNeedJS
     * @param reNeedSS
     * @param state
     * @param comment
     */
    public WPNeed(WPNeedPK wpNeedPK, int reNeedP1, int reNeedP2, int reNeedP3, int reNeedP4, int reNeedP5, int reNeedP6,
            int reNeedDS, int reNeedJS, int reNeedSS, String state, String comment) {
        super();
        this.wpNeedPK = wpNeedPK;
        this.reNeedP1 = reNeedP1;
        this.reNeedP2 = reNeedP2;
        this.reNeedP3 = reNeedP3;
        this.reNeedP4 = reNeedP4;
        this.reNeedP5 = reNeedP5;
        this.reNeedP6 = reNeedP6;
        this.reNeedDS = reNeedDS;
        this.reNeedJS = reNeedJS;
        this.reNeedSS = reNeedSS;
        this.state = state;
        this.comment = comment;
    }



    /**
     * Constructs an objecct of type WPNeed.
     * @param wpNeedPK
     */
    public WPNeed(WPNeedPK wpNeedPK) {
        super();
        this.wpNeedPK = wpNeedPK;
    }

    /**
     * Returns the {bare_field_name} for this WPNeed.
     * @return the wpNeedPK
     */
    public WPNeedPK getWpNeedPK() {
        return wpNeedPK;
    }

    /**
     * Sets the wpNeedPK for this WPNeed
     * @param wpNeedPK the wpNeedPK to set
     */
    public void setWpNeedPK(WPNeedPK wpNeedPK) {
        this.wpNeedPK = wpNeedPK;
    }

    /**
     * Returns the {bare_field_name} for this WPNeed.
     * @return the reNeedP1
     */
    public int getReNeedP1() {
        return reNeedP1;
    }

    /**
     * Sets the reNeedP1 for this WPNeed
     * @param reNeedP1 the reNeedP1 to set
     */
    public void setReNeedP1(int reNeedP1) {
        this.reNeedP1 = reNeedP1;
    }

    /**
     * Returns the {bare_field_name} for this WPNeed.
     * @return the reNeedP2
     */
    public int getReNeedP2() {
        return reNeedP2;
    }

    /**
     * Sets the reNeedP2 for this WPNeed
     * @param reNeedP2 the reNeedP2 to set
     */
    public void setReNeedP2(int reNeedP2) {
        this.reNeedP2 = reNeedP2;
    }

    /**
     * Returns the {bare_field_name} for this WPNeed.
     * @return the reNeedP3
     */
    public int getReNeedP3() {
        return reNeedP3;
    }

    /**
     * Sets the reNeedP3 for this WPNeed
     * @param reNeedP3 the reNeedP3 to set
     */
    public void setReNeedP3(int reNeedP3) {
        this.reNeedP3 = reNeedP3;
    }

    /**
     * Returns the {bare_field_name} for this WPNeed.
     * @return the reNeedP4
     */
    public int getReNeedP4() {
        return reNeedP4;
    }

    /**
     * Sets the reNeedP4 for this WPNeed
     * @param reNeedP4 the reNeedP4 to set
     */
    public void setReNeedP4(int reNeedP4) {
        this.reNeedP4 = reNeedP4;
    }

    /**
     * Returns the {bare_field_name} for this WPNeed.
     * @return the reNeedP5
     */
    public int getReNeedP5() {
        return reNeedP5;
    }

    /**
     * Sets the reNeedP5 for this WPNeed
     * @param reNeedP5 the reNeedP5 to set
     */
    public void setReNeedP5(int reNeedP5) {
        this.reNeedP5 = reNeedP5;
    }

    /**
     * Returns the {bare_field_name} for this WPNeed.
     * @return the reNeedP6
     */
    public int getReNeedP6() {
        return reNeedP6;
    }

    /**
     * Sets the reNeedP6 for this WPNeed
     * @param reNeedP6 the reNeedP6 to set
     */
    public void setReNeedP6(int reNeedP6) {
        this.reNeedP6 = reNeedP6;
    }

    /**
     * Returns the {bare_field_name} for this WPNeed.
     * @return the reNeedDS
     */
    public int getReNeedDS() {
        return reNeedDS;
    }

    /**
     * Sets the reNeedDS for this WPNeed
     * @param reNeedDS the reNeedDS to set
     */
    public void setReNeedDS(int reNeedDS) {
        this.reNeedDS = reNeedDS;
    }

    /**
     * Returns the {bare_field_name} for this WPNeed.
     * @return the reNeedJS
     */
    public int getReNeedJS() {
        return reNeedJS;
    }

    /**
     * Sets the reNeedJS for this WPNeed
     * @param reNeedJS the reNeedJS to set
     */
    public void setReNeedJS(int reNeedJS) {
        this.reNeedJS = reNeedJS;
    }

    /**
     * Returns the {bare_field_name} for this WPNeed.
     * @return the reNeedSS
     */
    public int getReNeedSS() {
        return reNeedSS;
    }

    /**
     * Sets the reNeedSS for this WPNeed
     * @param reNeedSS the reNeedSS to set
     */
    public void setReNeedSS(int reNeedSS) {
        this.reNeedSS = reNeedSS;
    }

    /**
     * Returns the {bare_field_name} for this WPNeed.
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state for this WPNeed
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Returns the {bare_field_name} for this WPNeed.
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment for this WPNeed
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
}
