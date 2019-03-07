package model;

public enum TimesheetRowState {
    Draft("Draft"),
    Pending("Pending");
    
    private final String value;
    
    TimesheetRowState(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return value;
    }
}
