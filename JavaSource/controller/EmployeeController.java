package controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import model.Employee;
import model.EmployeeState;
import service.PasswordHash;
import service.PasswordHash.CannotPerformOperationException;

@Named("employeesController")
@ViewScoped
public class EmployeeController implements Serializable {

	@Inject
	private DatabaseController database;

	private List<Employee> activeEmployees;
	private List<Employee> allEmployees;

	private Employee editEmployee;

	@PostConstruct
	public void init() {
		editEmployee = null;
		activeEmployees = database.getActiveEmployees();
		setAllEmployees(database.getEmployees());
	}

	public void addEmployee(String empNo, String firstName, String lastName, String username, String password,
			String comment) {

		activeEmployees = database.getEmployees();

		String state = EmployeeState.Active.toString();

		if (validateEmployee(empNo, firstName, lastName, username, password, state, comment, false)) {
			try {
				password = PasswordHash.createHash(password);

				Employee e = new Employee(Integer.parseInt(empNo), firstName, lastName, username, password, state,
						comment);
				e.setApproEmpNo(1);
				e.setSuperEmpNo(1);

				activeEmployees.add(e);
				database.addEmployee(e);

				activeEmployees = database.getEmployees();

				PrimeFaces.current().executeScript("PF('addEmployeeDialog').hide();");
			} catch (CannotPerformOperationException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void editEmployee(String firstName, String lastName, String username, String password, String comment) {

		activeEmployees = database.getEmployees();
		String state = EmployeeState.Active.toString();

		if (validateEmployee(null, firstName, lastName, username, password, state, comment, true)) {
			try {
				password = PasswordHash.createHash(password);

				editEmployee.setFirstName(firstName);
				editEmployee.setLastName(lastName);
				editEmployee.setUserName(username);
				editEmployee.setPassword(password);
				editEmployee.setState(state);
				editEmployee.setComment(comment);

				database.updateEmployee(editEmployee);

				activeEmployees = database.getEmployees();

				PrimeFaces.current().executeScript("PF('editEmployeeDialog').hide();");

			} catch (CannotPerformOperationException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteEmployee(Employee e) {
		e.setState(EmployeeState.Retired.toString());
		activeEmployees.remove(e);
		database.updateEmployee(e);
	}

	public void deletePermanentEmployee(Employee e) {
		allEmployees.remove(e);
		if (activeEmployees.contains(e)) {
			activeEmployees.remove(e);
		}
		database.removeEmployee(e);
	}

	public List<Employee> getEmployees() {
		return activeEmployees;
	}

	public Employee getEditEmployee() {
		return editEmployee;
	}

	public void setEditEmployee(Employee editEmployee) {
		this.editEmployee = new Employee(editEmployee.getEmpNumber(), editEmployee.getFirstName(),
				editEmployee.getLastName(), editEmployee.getUserName(), "", editEmployee.getState(),
				editEmployee.getComment());

		PrimeFaces.current().executeScript("PF('editEmployeeDialog').show();");
	}

	public boolean validateEmployee(String empNo, String firstName, String lastName, String username, String password,
			String state, String comment, boolean isEdit) {

		if (isEdit ? isAnyNullOrWhitespace(firstName, lastName, username, password, state)
				: isAnyNullOrWhitespace(empNo, firstName, lastName, username, password, state)) {
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
		for (Employee e : activeEmployees) {
			if (e.getEmpNumber() == empNumber) {
				return true;
			}
		}

		return false;
	}

	private boolean isDuplicateUsername(String username, boolean isEdit) {
		for (Employee e : activeEmployees) {
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
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public List<Employee> getAllEmployees() {
		return allEmployees;
	}

	public void setAllEmployees(List<Employee> allEmployees) {
		this.allEmployees = allEmployees;
	}
}