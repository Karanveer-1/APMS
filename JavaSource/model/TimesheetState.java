package model;

public enum TimesheetState {
    Draft("Draft"),
    Pending("Pending");
    
    private final String value;
    
    TimesheetState(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return value;
    }
}
