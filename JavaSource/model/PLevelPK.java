package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PLevelPK.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
@Embeddable
public class PLevelPK implements Serializable {
    
    @Column(name = "PLevel", nullable = false)
    private String pLevel;
    
    @Column(name = "StartDate", nullable = false)
    private Date startDate;
}
