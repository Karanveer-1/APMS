package Controller;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import Model.Employee;
import Model.WorkPackage;

@Named("workPackageController")
public class WorkPackageController implements Serializable {
	@PersistenceContext(unitName = "apms", type = PersistenceContextType.TRANSACTION)
	private EntityManager manager;

//	@Inject
//	private WorkPackage workPackage;
//
//	private Employee employee;
//
//	private Integer parentWPID;

	public WorkPackage find(final Integer id) {
		if (id != null)
			return manager.find(WorkPackage.class, id);
		else
			return null;
	}

//	public void remove(final WorkPackage workPackage) {
//		WorkPackage wp = manager.find(workPackage.getWorkPackagePk());
//		manager.remove(wp);
//	}

	public void merge(final WorkPackage workPackage) {
		manager.merge(workPackage);
	}

//	public void persist(final WorkPackage newWorkPackage) {
//		WorkPackage match = manager.find(WorkPackage.class, newWorkPackage.getId());
//
//		if (match != null) {
//			logger.warn("Record(WorkPackage) already exist! ");
//		} else {
//			if (!newWorkPackage.isLeaf())
//				newWorkPackage.setBudget(null);
//			manager.persist(newWorkPackage);
//			logger.info("Workpackage added: " + newWorkPackage.getId() + ", " + newWorkPackage.getName());
//		}

	// }

}
