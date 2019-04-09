package validator;

import java.util.List;

import javax.inject.Inject;

import controller.DatabaseController;
import model.WorkPackage;

public class WorkPackageValidator {

	@Inject
	private static DatabaseController database;

	public static boolean isValid(WorkPackage wp) {
		return isValidDate(wp) && isValidFields(wp);
	}

	public static boolean isValidDate(WorkPackage wp) {
		return !wp.getStartDate().after(wp.getEndDate());

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

	public static boolean canDelete(WorkPackage wp) {
		if (wp.isLeaf() && !wp.isCharged()) {
			return true;
		}

		if (!wp.isLeaf() && !hasChild(wp)) {
			return true;
		}
		return false;
	}

	public static boolean hasChild(WorkPackage wp) {
		List<WorkPackage> allWp = database.getAllWp();
		for (WorkPackage e : allWp) {
			if (e.getParentWPID().equals(wp.getParentWPID()) && e.getProNo() == wp.getProNo()) {
				return true;
			}
		}
		return false;
	}

	public static boolean canAddChild(WorkPackage wp) {
		return wp.getState().equals("OPEN");
	}

}
