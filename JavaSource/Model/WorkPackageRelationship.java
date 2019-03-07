package Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "WorkPackageRelationship")
public class WorkPackageRelationship implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WorkPackageRelationshipPK wpRelationshipPK;

	@Column(name = "ParentWPID", nullable = false)
	private String parentWPID;

	public WorkPackageRelationship() {

	}

	public WorkPackageRelationship(WorkPackageRelationshipPK wpRelationshipPK, String parentWPID) {
		super();
		this.wpRelationshipPK = wpRelationshipPK;
		this.parentWPID = parentWPID;
	}

	public WorkPackageRelationshipPK getWpRelationshipPK() {
		return wpRelationshipPK;
	}

	public void setWpRelationshipPK(WorkPackageRelationshipPK wpRelationshipPK) {
		this.wpRelationshipPK = wpRelationshipPK;
	}

	public String getParentWPID() {
		return parentWPID;
	}

	public void setParentWPID(String parentWPID) {
		this.parentWPID = parentWPID;
	}

}
