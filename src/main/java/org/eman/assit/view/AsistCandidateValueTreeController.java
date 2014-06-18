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
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eman.assit.model.AsistCandidateValue;
import org.eman.assit.view.util.JsfUtil;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author 谢金光
 */
@Named
@ViewScoped
public class AsistCandidateValueTreeController extends AsistCandidateValueController {

    private TreeNode selectedNode;

    private TreeNode[] selectedNodes;

    private TreeNode parent;
    
    private String accordingKey;

    public String getAccordingKey() {
        return accordingKey;
    }

    public void setAccordingKey(String accordingKey) {
        this.accordingKey = accordingKey;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public boolean isCreateNotExist() {
        return createNotExist;
    }

    public void setCreateNotExist(boolean createNotExist) {
        this.createNotExist = createNotExist;
    }
    private String newValue;
    private boolean createNotExist = false;

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
        if (treeRoot == null) {
            if (accordingKey != null && !accordingKey.trim().isEmpty()) {
                Map<String, Object> params = new HashMap<>();
                params.put("accordingKey", accordingKey);
                List<AsistCandidateValue> roots = findItemsByConditions(params);
                if (roots == null || roots.size() == 0) {
                    if (createNotExist) {
                        this.prepareCreate();
                        AsistCandidateValue rootd = this.getCreated();
                        rootd.setAccordingKey(accordingKey);
                        rootd.setValue(newValue);
                        rootd.setParent(null);
                        this.create();
                        roots = findItemsByConditions(params);
                    } else {
                        treeRoot = null;
                        return treeRoot;
                    }
                }
                treeRoot = new DefaultTreeNode(roots.get(0), null);
                for (AsistCandidateValue v : roots.get(0).getAsistCandidateValueCollection()) {
                    buildTreeNodeForEntity(v, treeRoot);

                }

            } else {
                treeRoot = new DefaultTreeNode(null, null);
                Map<String, Object> params = new HashMap<>();
                params.put("parentID", null);
                List<AsistCandidateValue> roots = findItemsByConditions(params);
                for (AsistCandidateValue v : roots) {
                    buildTreeNodeForEntity(v, treeRoot);

                }
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

    public void prepareAddNode(TreeNode parent) {
        prepareCreate();
        this.parent = parent;
        AsistCandidateValue p = (parent == null || parent.getData() == null) ? null : ((AsistCandidateValue) parent.getData());
        if (p != null) {
            this.getCreated().setParentID(p);
            this.getCreated().setAccordingKey(p.getAccordingKey() + "_" + p.getValue());
        }
    }

    public void addNode() {
        if (parent.getData() != null) {
            this.ejbFacade.addChildren((AsistCandidateValue) parent.getData(), this.getCreated());
        } else {
            this.create();
        }

        parent.getChildren().add(buildTreeNodeForEntity(this.getCreated(), parent));
    }

    public void deleteNodes() {
        boolean hasChildren = false;
        for (TreeNode n : selectedNodes) {
            if (n.getChildCount() > 0) {
                hasChildren = true;
                JsfUtil.addErrorMessage("存在子节点，请先删除子节点");
                return;
            }
        }
        destroy();
        for (TreeNode n : selectedNodes) {

            if (n.getParent() != null) {
                n.getParent().getChildren().remove(n);
            }

        }
    }

}
