package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * PLevel.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
@Entity
@Table(name = "PLevel")
public class PLevel implements Serializable {
    
    @EmbeddedId
    private PLevelPK pLevelPK;

    @Column(name = "Wage")
    private float sun;

}
