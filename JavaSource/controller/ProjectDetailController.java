package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import controller.ProjectController.StatusCreation;
import model.Employee;
//import model.ProAssi;
import model.Project;
import model.WPEmp;
import model.WorkPackage;

@Named("pdController")
@SessionScoped
public class ProjectDetailController implements Serializable {

	public enum StatusCreation {

		OPEN("Open");

		private String label;

		private StatusCreation(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

	}

	@Inject
	private DatabaseController database;

	private Project project;

	private List<Employee> proAssi;

	private List<Employee> empPool;

	private List<Employee> wpEmp;

	private TreeNode root;

	private WorkPackage addWp;

	private List<WorkPackage> wpList;

	private String suggestId;

	public ProjectDetailController() {

	}

	@PostConstruct
	public void init() {

	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String detail(Project project) {

		this.project = project;
		this.empPool = getAllEmpPool(project.getProNo());
		this.addWp = new WorkPackage();
		initWPList();
		treeInit(this.project.getProNo());
		return "ProjectDetail.xhtml?faces-redirect=true";

	}

	public void treeInit(int proNo) {
		root = new DefaultTreeNode(new WorkPackage(), null);
		this.database.getRootWPByProNo(proNo);
		for (WorkPackage wp : database.getRootWPByProNo(proNo)) {
			TreeNode wpNode = new DefaultTreeNode(wp, root);
			getTree(wpNode, wp);
		}
		expandAll();
	}

	public List<Employee> getProAssi() {
		return proAssi;
	}

	public void setProAssi(List<Employee> proAssi) {
		this.proAssi = proAssi;
	}

	public List<Employee> getAllEmpPool(int proNo) {
		List<Employee> pool = this.database.getEmployeeForProject(proNo);
		return pool;
	}

	public String addWP() {

		addWp.setProNo(project.getProNo());
		if (addWp.getParentWPID().equals("Project")) {
			addWp.setParentWPID(null);
		} else {
			addWp.setWpid(addWp.getParentWPID() + "." + addWp.getWpid());
		}

		if (addWp.isLeaf()) {
			addWp.setEditable(true);
			updateParentWP(addWp, this.database.getParentWP(addWp));
		}
		this.database.persistWP(addWp);

		addWp = new WorkPackage();

		treeInit(this.project.getProNo());
		return "WorkPackageManagement.xhtml?faces-redirect=true";

	}

	public void updateParentWP(WorkPackage wp, WorkPackage parent) {
		if (parent != null) {
			parent.addHoursFromChild(wp);
			this.database.updateWP(parent);
			updateParentWP(wp, this.database.getParentWP(parent));
		
		}

	}

	public List<Employee> getWPEmp(int proNo) {
		List<Employee> result = new ArrayList<Employee>();
		List<WPEmp> wpe = this.database.getAllWPEmpByProNo(proNo);
		for (WPEmp e : wpe) {
			Employee emp = this.database.getEmployeeById(e.getEmpNo());
			if (result.indexOf(emp) == -1) {
				result.add(emp);
			}
		}
		return result;
	}

	public void getTree(TreeNode parentNode, WorkPackage parentWp) {
		List<WorkPackage> childList = database.getLowerWP(parentWp.getWpid());
		for (WorkPackage childWp : childList) {
			TreeNode childNode = new DefaultTreeNode(childWp, parentNode);
			getTree(childNode, childWp);
		}
	}

	public TreeNode getRoot() {
		return root;
	}

	public void deleteWp(WorkPackage wp) {
		this.database.deleteWorkPackage(wp);
		treeInit(this.project.getProNo());
	}

	public void expandAll() {
		setExpandedRecursively(root, true);
	}

	private void setExpandedRecursively(final TreeNode node, final boolean expanded) {
		for (final TreeNode child : node.getChildren()) {
			setExpandedRecursively(child, expanded);
		}
		node.setExpanded(expanded);
	}

	public List<Employee> getEmpPool() {
		return empPool;
	}

	public void setEmpPool(List<Employee> empPool) {
		this.empPool = empPool;
	}

	public List<Employee> getWpEmp() {
		return wpEmp;
	}

	public void setWpEmp(List<Employee> wpEmp) {
		this.wpEmp = wpEmp;
	}

	public String getEmpName(int id) {
		return this.database.getEmployeeById(id).getUserName();
	}

	public void submit() {
		this.database.updateProject(project);
		FacesMessage msg = new FacesMessage("Project Assistant Updated");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public WorkPackage getAddWp() {
		return addWp;
	}

	public void setAddWp(WorkPackage addWp) {
		this.addWp = addWp;
	}

	public void initWPList() {
		this.wpList = this.database.getWPListByProNo(project.getProNo());
		List<WorkPackage> result = new ArrayList<WorkPackage>();
		for (WorkPackage wp : wpList) {
			if (!wp.isLeaf()) {
				result.add(wp);
			}
		}
		this.wpList = result;
		WorkPackage wp = new WorkPackage();
		wp.setWpid("Project");
		this.wpList.add(wp);
		this.suggestId = this.wpList.get(0).getWpid();
	}

	public List<WorkPackage> getWpList() {
		return wpList;
	}

	public void setWpList(List<WorkPackage> wpList) {
		this.wpList = wpList;
	}

	public void onClick() {
		suggestId = addWp.getParentWPID();
	}

	public String getSuggestId() {
		return suggestId;
	}

	public void setSuggestId(String suggestId) {
		this.suggestId = suggestId;
	}

	public StatusCreation[] getStatusCreation() {
		return StatusCreation.values();
	}

	public String createNew() {
		return "CreateWorkPackage.xhtml?faces-redirect=true";
	}

}
