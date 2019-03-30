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
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import model.Employee;
import model.ProAssi;
import model.Project;
import model.WorkPackage;

@Named("pdController")
@SessionScoped
public class ProjectDetailController implements Serializable {

	@Inject
	private DatabaseController database;

	private Project project;

	private List<Employee> proAssi;

	private List<Employee> empPool;

	private TreeNode root;

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
		this.proAssi = getAssistantManager();
		this.empPool = getAllProjectEmp();
		System.out.println(this.project.getProDesc());
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

	public List<Employee> getAssistantManager() {
		List<Employee> result = new ArrayList<Employee>();
		for (ProAssi pe : this.database.getProAssiByProNo(this.project.getProNo())) {
			result.add(this.database.getEmployeeById(pe.getEmpNo()));
		}
		return result;
	}

	public List<Employee> getProAssi() {
		return proAssi;
	}

	public void setProAssi(List<Employee> proAssi) {
		this.proAssi = proAssi;
	}

	public List<Employee> getAllProjectEmp() {
		List<Employee> result = this.database.getEmployeeForProject(project.getProNo());
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

	public void addWp(WorkPackage wp) {
		WorkPackage newWp = new WorkPackage();
		newWp.setWpid("he" + wp.getWpid());
		newWp.setProNo(wp.getProNo());
		this.database.persistChildWP(wp, newWp);
		System.out.println(wp.getWpid());
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

}
