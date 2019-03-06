package model;

public enum TimesheetState {
    Draft("Draft"),
    Pending("Pending"),
    Approved("Approved"),
    Submitted("Submitted");
    
    
    private final String value;
    
    TimesheetState(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return value;
    }
}
