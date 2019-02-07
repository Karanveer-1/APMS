package Model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TimesheetPK {
    
    @Column(name = "EmpNo")
    private int empNumber;
    
    @Column(name = "Year")
    private int year;
    
    @Column(name = "WeekNo")
    private int weekNumber;
}
