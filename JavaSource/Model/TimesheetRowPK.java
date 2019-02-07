package Model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TimesheetRowPK {

    @Column(name = "EmpNo", nullable = false)
    private int empNumber;

    @Column(name = "Year", nullable = false)
    private int year;

    @Column(name = "WeekNo", nullable = false)
    private int weekNo;

    @Column(name = "ProNo", nullable = false)
    private int projectNumber;

    @Column(name = "WPID", nullable = false)
    private String WPID;

    public TimesheetRowPK(int empNumber, int year, int weekNo,
            int projectNumber, String wPID) {
        super();
        this.empNumber = empNumber;
        this.year = year;
        this.weekNo = weekNo;
        this.projectNumber = projectNumber;
        WPID = wPID;
    }
}
