package Controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import Model.Employee;

@Named("employeeListController")
@ViewScoped
public class EmployeeListController implements Serializable {

    @Inject
    private DatabaseController database;

    private List<Employee> employees;

    private Employee editEmployee;

    @PostConstruct
    public void init() {
        editEmployee = null;
        employees = database.getEmployees();
    }

    public void addEmployee(String empNo, String firstName, String lastName,
            String username, String password, String state,
            String comment) {

        employees = database.getEmployees();
        
        if (validateEmployee(empNo, firstName, lastName, username, password, state, comment, false)) {
            Employee e = new Employee(Integer.parseInt(empNo), firstName,
                    lastName, username, password, state, comment);
            employees.add(e);
            database.addEmployee(e);
            
            employees = database.getEmployees();
            
            PrimeFaces.current()
                    .executeScript("PF('addEmployeeDialog').hide();");
        }
    }

    public void editEmployee(String firstName, String lastName, String username,
            String password, String state, String comment) {
        
        employees = database.getEmployees();
        
        if (validateEmployee(null, firstName, lastName, username, password, state, comment, true)) {

            editEmployee.setFirstName(firstName);
            editEmployee.setLastName(lastName);
            editEmployee.setUserName(username);
            editEmployee.setPassword(password);
            editEmployee.setState(state);
            editEmployee.setComment(comment);

            database.updateEmployee(editEmployee);
            
            employees = database.getEmployees();

            PrimeFaces.current()
                    .executeScript("PF('editEmployeeDialog').hide();");
        }
    }

    public void deleteEmployee(Employee e) {
        employees.remove(e);
        database.removeEmployee(e);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Employee getEditEmployee() {
        return editEmployee;
    }

    public void setEditEmployee(Employee editEmployee) {        
        this.editEmployee = new Employee(editEmployee.getEmpNumber(),
                editEmployee.getFirstName(), editEmployee.getLastName(),
                editEmployee.getUserName(), editEmployee.getPassword(),
                editEmployee.getState(), editEmployee.getComment());
                

        PrimeFaces.current().executeScript("PF('editEmployeeDialog').show();");
    }

    public boolean validateEmployee(String empNo, String firstName,
            String lastName, String username, String password,
            String state, String comment, boolean isEdit) {

        if (isEdit
                ? isAnyNullOrWhitespace(firstName, lastName, username, password, state)
                : isAnyNullOrWhitespace(empNo, firstName, lastName, username,
                        password, state)) {
            addErrorMessage("All fields except for comment must be filled in");
            return false;
        } else if (!isEdit && !isInteger(empNo)) {
            addErrorMessage("Employee number must be an integer");
            return false;
        } else if (!isEdit && isDuplicateEmpNumber(Integer.parseInt(empNo))) {
            addErrorMessage("Duplicate employee number found");
            return false;
        } else if (isDuplicateUsername(username, isEdit)) {
            addErrorMessage("Duplicate username found");
            return false;
        }

        return true;
    }

    private boolean isDuplicateEmpNumber(int empNumber) {
        for (Employee e : employees) {
            if (e.getEmpNumber() == empNumber) {
                return true;
            }
        }

        return false;
    }

    private boolean isDuplicateUsername(String username, boolean isEdit) {
        for (Employee e : employees) {
            if (e.equals(editEmployee) && isEdit) {
                continue;
            }

            if (e.getUserName().equalsIgnoreCase(username)) {
                return true;
            }
        }

        return false;
    }

    private boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private boolean isAnyNullOrWhitespace(String... values) {
        for (String s : values) {
            if (s == null || s.trim().length() == 0) {
                return true;
            }
        }

        return false;
    }

    private void addErrorMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
