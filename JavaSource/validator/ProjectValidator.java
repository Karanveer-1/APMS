package validator;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import controller.DatabaseController;
import model.Project;
import model.WorkPackage;

public class ProjectValidator {

	public static boolean isValid(Project pro) {
		return isValidDate(pro) && isValidFields(pro);
	}

	public static boolean isValidDate(Project pro) {
		if (pro.getStartDate().before(new Date())) {
			return false;
		}
		return pro.getStartDate().before(pro.getEndDate());
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

	public static boolean canDelete(DatabaseController database, Project pro) {
		List<WorkPackage> allWp = database.getAllWp();
		for (WorkPackage e : allWp) {
			if (e.getProNo() == pro.getProNo() || !WorkPackageValidator.canDelete(database, e)) {
				return false;
			}

		}
		return true;
	}

	public static boolean canModify(Project pro) {
		if (pro.getState().equals("OPEN")) {
			return true;
		}
		return false;
	}

}
