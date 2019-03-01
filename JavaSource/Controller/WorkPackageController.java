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

}
