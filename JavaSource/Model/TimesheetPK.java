package Model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TimesheetPK {

    @Column(name = "EmpNo", nullable = false)
    private int empNumber;

    @Column(name = "Year", nullable = false)
    private int year;

    @Column(name = "WeekNo", nullable = false)
    private int weekNumber;

    public TimesheetPK(int empNumber, int year, int weekNumber) {
        super();
        this.empNumber = empNumber;
        this.year = year;
        this.weekNumber = weekNumber;
    }
}
