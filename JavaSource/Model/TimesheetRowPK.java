package Model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TimesheetRowPK {
    
    @Column(name = "EmpNo")
    private int empNumber;
    
    @Column(name = "Year")
    private int year;
    
    @Column(name = "WeekNo")
    private int weekNo;
    
    @Column(name = "ProNo")
    private int projectNumber;
    
    @Column(name = "WPID")
    private String WPID;
}
