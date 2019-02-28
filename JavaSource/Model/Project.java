package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity

@Table(name = "Project")
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProAssi proAssi;

	/**
	 * Project current state
	 */
	@Column(name = "State", nullable = false)
	private String state;

	@Column(name = "Comment")
	private String comment;

	public Project() {

	}

	public Project(ProAssi proAssi, String state, String comment) {
		super();
		this.proAssi = proAssi;
		this.state = state;
		this.comment = comment;
	}

	public ProAssi getProAssi() {
		return proAssi;
	}

	public void setProAssi(ProAssi proAssi) {
		this.proAssi = proAssi;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}