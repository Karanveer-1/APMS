package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Timesheet")
public class Timesheet implements Serializable {

    @EmbeddedId
    private TimesheetPK timesheetPk;

    @Column(name = "Signature")
    private String signature;
    
    @Column(name = "ApprovedEmpNo")
    private Integer approvedEmpNo;
    
    @Column(name = "State", nullable = false)
    private String state;
    
    @Column(name = "Comment")
    private String comment;

    public Timesheet() {
    }

    public Timesheet(TimesheetPK timesheetPk, String signature,
            Integer approvedEmpNo, String state, String comment) {
        super();
        this.timesheetPk = timesheetPk;
        this.signature = signature;
        this.approvedEmpNo = approvedEmpNo;
        this.state = state;
        this.comment = comment;
    }

    public TimesheetPK getTimesheetPk() {
        return timesheetPk;
    }

    public void setTimesheetPk(TimesheetPK timesheetPk) {
        this.timesheetPk = timesheetPk;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getApprovedEmpNo() {
        return approvedEmpNo;
    }

    public void setApprovedEmpNo(Integer approvedEmpNo) {
        this.approvedEmpNo = approvedEmpNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
