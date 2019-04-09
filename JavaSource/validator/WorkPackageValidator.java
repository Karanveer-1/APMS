package validator;

import java.util.List;

import javax.inject.Inject;

import controller.DatabaseController;
import model.WorkPackage;

public class WorkPackageValidator {



	public static boolean isValid(WorkPackage wp) {
		return isValidDate(wp) && isValidFields(wp);
	}

	public static boolean isValidDate(WorkPackage wp) {
		return wp.getStartDate().before(wp.getEndDate());

	}

	public static boolean isValidFields(WorkPackage wp) {
		if (wp.getWpid().trim().equals("")) {
			return false;
		}

		if (wp.getTitle().trim().equals("")) {
			return false;
		}

		if (wp.getDescription().trim().equals("")) {
			return false;
		}

		return true;

	}

	public static boolean canDelete(DatabaseController database, WorkPackage wp) {
		if (wp.isLeaf() && !wp.isCharged()) {
			return true;
		}

		if (!wp.isLeaf() && !hasChild(database, wp)) {
			return true;
		}
		return false;
	}

	public static boolean hasChild(DatabaseController database, WorkPackage wp) {

		List<WorkPackage> allWp = database.getAllWp();
		System.out.println("ALL WP " + database.getAllWp());
		for (WorkPackage e : allWp) {
			if (e.getParentWPID() != null && e.getParentWPID().equals(wp.getWpid()) && e.getProNo() == wp.getProNo()) {
				return true;
			}
		}
		return false;
	}

	public static boolean canAddChild(WorkPackage wp) {
		return wp.getState().equals("OPEN");
	}

}
