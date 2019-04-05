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

import model.Employee;
//import model.ProAssi;
import model.Project;
import model.WPEmp;
import model.WorkPackage;

@Named("pdController")
@SessionScoped
public class ProjectDetailController implements Serializable {

	@Inject
	private DatabaseController database;

	private Project project;

	private List<Employee> proAssi;

	private List<Employee> empPool;

	private List<Employee> wpEmp;

	private TreeNode root;

	private WorkPackage addWp;

	private List<WorkPackage> wpList;

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
		this.wpEmp = getWPEmp(project.getProNo());
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

	public void addWP(WorkPackage wp) {
		WorkPackage newWp = new WorkPackage();
		newWp.setWpid("Hi" + wp.getWpid());
		newWp.setParentWPID(wp.getWpid());
		wp.setLeaf(false);
		this.database.persistChildWP(wp, newWp);
		treeInit(this.project.getProNo());

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

	public void initAddWp() {
		addWp = new WorkPackage();
		addWp.setProNo(project.getProNo());

	}

	public void initWPList() {
		this.wpList = this.database.getWPListByProNo(project.getProNo());
		WorkPackage wp = new WorkPackage();
		wp.setWpid("Project");
		this.wpList.add(wp);
	}

	public List<WorkPackage> getWpList() {
		return wpList;
	}

	public void setWpList(List<WorkPackage> wpList) {
		this.wpList = wpList;
	}

	public void onClick() {
		System.out.println("On Click");
	}

}
