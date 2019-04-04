package model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Role")
public class Role implements Serializable {
    
    @EmbeddedId
    private RolePK rolePk;
    
    public Role() {}
    
    public Role(RolePK rolePk) {
        this.rolePk = rolePk;
    }

    public RolePK getRolePk() {
        return rolePk;
    }

    public void setRolePk(RolePK rolePk) {
        this.rolePk = rolePk;
    }
}
