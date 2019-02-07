package Access;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import Model.Employee;

@Stateless
public class DatabaseController implements Serializable {
    @PersistenceContext(unitName = "apms", type = PersistenceContextType.TRANSACTION)
    private EntityManager manager;

    public List<Employee> getEmployees() {
        return manager.createQuery("SELECT e FROM Employee e", Employee.class)
                .getResultList();
    }
    
    public void addEmployee(Employee e) {
        manager.persist(e);
    }
    
    public void removeEmployee(Employee e) {
        manager.remove(manager.contains(e) ? e : manager.merge(e));
    }
    
    public void updateEmployee(Employee e) {
        manager.merge(e);
    }
}
