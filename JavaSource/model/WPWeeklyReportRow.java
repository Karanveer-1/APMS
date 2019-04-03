package model;

import java.util.Date;

public class WPWeeklyReportRow {
    private int empNo;
    private String pLevel;
    private Date weekNo;
    private float total;

    public WPWeeklyReportRow() {
    }

    public WPWeeklyReportRow(int empNo, String pLevel, Date weekNo,
            float total) {
        super();
        this.empNo = empNo;
        this.pLevel = pLevel;
        this.weekNo = weekNo;
        this.total = total;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getpLevel() {
        return pLevel;
    }

    public void setpLevel(String pLevel) {
        this.pLevel = pLevel;
    }

    public Date getWeekNo() {
        return weekNo;
    }

    public void setWeekNo(Date weekNo) {
        this.weekNo = weekNo;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

}
