package model;

public enum EmployeeState {
    Active("Active"),
    Retired("Not Active");
    
    
    private final String value;
    
    EmployeeState(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return value;
    }
}
