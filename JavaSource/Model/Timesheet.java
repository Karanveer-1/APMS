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
    private int approvedEmpNo;
    
    @Column(name = "State")
    private String state;
    
    @Column(name = "Comment")
    private String comment;

    public Timesheet() {
    }

    public Timesheet(TimesheetPK timesheetPk, String signature,
            int approvedEmpNo, String state, String comment) {
        super();
        this.timesheetPk = timesheetPk;
        this.signature = signature;
        this.approvedEmpNo = approvedEmpNo;
        this.state = state;
        this.comment = comment;
    }
}
