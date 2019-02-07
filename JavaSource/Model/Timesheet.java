package Model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Timesheet")
public class Timesheet {
    
    @EmbeddedId
    private TimesheetPK timesheetPk;
    
    @Column(name = "Wage")
    private float wage;
    
    @Column(name = "Signature")
    private String signature;
    
    @Column(name = "ApprovedEmpNo")
    private int approvedEmpNumber;
    
    @Column(name = "State")
    private String state;
    
    @Column(name = "Comment")
    private String comment;
    
    public Timesheet() {}

    public Timesheet(TimesheetPK timesheetPk, float wage, String signature,
            int approvedEmpNumber, String state, String comment) {
        super();
        this.timesheetPk = timesheetPk;
        this.wage = wage;
        this.signature = signature;
        this.approvedEmpNumber = approvedEmpNumber;
        this.state = state;
        this.comment = comment;
    }

    public TimesheetPK getTimesheetPk() {
        return timesheetPk;
    }

    public void setTimesheetPk(TimesheetPK pk) {
        this.timesheetPk = pk;
    }

    public float getWage() {
        return wage;
    }

    public void setWage(float wage) {
        this.wage = wage;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getApprovedEmpNumber() {
        return approvedEmpNumber;
    }

    public void setApprovedEmpNumber(int approvedEmpNumber) {
        this.approvedEmpNumber = approvedEmpNumber;
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
