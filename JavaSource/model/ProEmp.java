package model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ProEmp")
public class ProEmp {
    
    @EmbeddedId
    private ProEmpPK proEmp;

    public ProEmp() { }

    public ProEmpPK getProEmp() {
        return proEmp;
    }

    public void setProEmp(ProEmpPK proEmp) {
        this.proEmp = proEmp;
    }

    
}
