/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eman.assit.view;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.eman.assit.model.AsistCandidateValue;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author 谢金光
 */
@Named
@ViewScoped
public class AsistCandidateValueTreeController extends AsistCandidateValueController {
    
    private EntityManager em1;

    private TreeNode selectedNode;

    private TreeNode[] selectedNodes;
    
    private TreeNode parent;

    /**
     * Get the value of selectedNodes
     *
     * @return the value of selectedNodes
     */
    public TreeNode[] getSelectedNodes() {
        return selectedNodes;
    }

    /**
     * Set the value of selectedNodes
     *
     * @param selectedNodes new value of selectedNodes
     */
    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
    }

    /**
     * Get the value of selectedNode
     *
     * @return the value of selectedNode
     */
    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    /**
     * Set the value of selectedNode
     *
     * @param selectedNode new value of selectedNode
     */
    public void setSelectedNode(TreeNode selectedNode) {

        this.selectedNode = selectedNode;
    }

    private TreeNode treeRoot;

    /**
     * Get the value of treeRoot
     *
     * @return the value of treeRoot
     */
    public TreeNode getTreeRoot() {
         if(treeRoot ==null){
            treeRoot = new DefaultTreeNode("TEST", null);
            Map<String, Object> params = new HashMap<>();
            params.put("parentID", null);
            List<AsistCandidateValue> roots = findItemsByConditions(params);
            for (AsistCandidateValue v : roots) {
                buildTreeNodeForEntity(v, treeRoot);

            }

        }
         
        return treeRoot;
    }

    protected TreeNode buildTreeNodeForEntity(AsistCandidateValue entity, TreeNode parent) {
        TreeNode node = new DefaultTreeNode(entity, parent);
        for (AsistCandidateValue child : entity.getAsistCandidateValueCollection()) {
            buildTreeNodeForEntity(child, node);
        }
        return node;
    }

    /**
     * Set the value of treeRoot
     *
     * @param treeRoot new value of treeRoot
     */
    public void setTreeRoot(TreeNode treeRoot) {
        this.treeRoot = treeRoot;
    }
    
    

    public void synchronizeSelectedNodesToDatas() {
        if (this.selectedNodes != null && this.selectedNodes.length > 0) {
            List ss = new LinkedList();
            for (TreeNode n : this.selectedNodes) {
                ss.add(n.getData());
            }
            this.setSelectedItems(ss);
        }

    }
    
    public void prepareAddNode(TreeNode parent){
        prepareCreate();
        this.parent = parent;
        AsistCandidateValue p = (parent==null || parent.getData()==null)? null:((AsistCandidateValue)parent.getData());
        this.getCreated().setParentID(p);
    }
    
    public void addNode(){
        ((AsistCandidateValue)parent.getData()).getAsistCandidateValueCollection().add(this.getCreated());
        this.setCreated(((AsistCandidateValue)parent.getData()));
        this.create();        
        parent.getChildren().add(buildTreeNodeForEntity(this.getCreated(), parent));
    }

}
