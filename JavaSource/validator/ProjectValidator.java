package validator;

import java.util.List;

import javax.inject.Inject;

import controller.DatabaseController;
import model.Project;
import model.WorkPackage;

public class ProjectValidator {

	@Inject
	private static DatabaseController database;

	public static boolean isValid(Project pro) {
		return isValidDate(pro);
	}

	public static boolean isValidDate(Project pro) {
		return !pro.getStartDate().after(pro.getEndDate());
	}

	public static boolean isValidFields(Project pro) {
		if (pro.getProName().trim().equals("")) {
			return false;
		}

		if (pro.getProDesc().trim().equals("")) {
			return false;
		}

		return true;
	}

	public static boolean canDelete(Project pro) {
		List<WorkPackage> allWp = database.getAllWp();
		for (WorkPackage e : allWp) {
			if (e.getProNo() == pro.getProNo()) {
				return false;
			}
		}
		return true;
	}

}
