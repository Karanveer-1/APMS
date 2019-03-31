//package controller;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.PostConstruct;
//import javax.enterprise.context.ConversationScoped;
//import javax.enterprise.context.RequestScoped;
//import javax.enterprise.context.SessionScoped;
//import javax.faces.application.FacesMessage;
//import javax.faces.context.FacesContext;
//import javax.faces.view.ViewScoped;
//import javax.inject.Inject;
//import javax.inject.Named;
//import javax.json.JsonObject;
//import javax.websocket.server.PathParam;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//
//import org.primefaces.model.DefaultTreeNode;
//import org.primefaces.model.TreeNode;
//
//import model.Employee;
//import model.Project;
//import model.WorkPackage;
//
//@Named("vpwpController")
//@SessionScoped
//public class ViewProjectWPController implements Serializable {
//
//	@Inject
//	private DatabaseController database;
//
//	private TreeNode root;
//
//	int proNo;
//
//	public ViewProjectWPController() {
//
//	}
//
//	public ViewProjectWPController(int proNo) {
//		this.proNo = proNo;
//	}
//
//	@PostConstruct
//	public void init() {
//
//		for (WorkPackage wp : database.getRootWPByProNo(proNo)) {
//			TreeNode wpNode = new DefaultTreeNode(wp, root);
//			getTree(wpNode, wp);
//		}
//
//	}
//
//	public void getTree(TreeNode parentNode, WorkPackage parentWp) {
//		List<WorkPackage> childList = database.getLowerWP(parentWp.getWpid());
//		for (WorkPackage childWp : childList) {
//			TreeNode childNode = new DefaultTreeNode(childWp, parentNode);
//			getTree(childNode, childWp);
//		}
//	}
//
//	public TreeNode getRoot() {
//		return root;
//	}
//
//	public void deleteWp(WorkPackage wp) {
//
//		this.database.deleteWorkPackage(wp.getWorkPackagePk());
//	}
//
//}
