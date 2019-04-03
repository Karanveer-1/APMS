package controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.WorkPackage;

@Named("wpdController")
@SessionScoped
public class WorkPackageDetailController implements Serializable {

	@Inject
	private DatabaseController database;

	private WorkPackage wp;

	private WorkPackage ogwp;

	public WorkPackageDetailController() {

	}

	@PostConstruct
	public void init() {

	}

	public String viewWP(WorkPackage wp) {
		this.wp = wp;
		this.ogwp = wp;
		return "WorkPackageDetail.xhtml?faces-redirect=true";
	}

	public WorkPackage getWp() {
		return wp;
	}

	public void setWp(WorkPackage wp) {
		this.wp = wp;
	}

	public void save() {
		System.out.println("WP " + wp);
		if (this.database.updateWP(wp)) {
			wp = this.database.getWPByID(wp);
			ogwp = wp;
		} else {
			wp = ogwp;
		}

	}
}
