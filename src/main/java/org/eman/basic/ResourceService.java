/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eman.basic;

import org.eman.basic.facade.RoomspotFacade;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eman.basic.model.Roomspot;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author 谢金光
 */
@Named
@ApplicationScoped
public class ResourceService {

    @Inject
    RoomspotFacade rsf;

   public TreeNode getRoomspotTree() {
        List<Roomspot> l = rsf.findAll();
        TreeNode root = new DefaultTreeNode();        
        for (Roomspot rs : l) {
            new DefaultTreeNode(Constants.TREENODE_TYPE_ROOMSPOT, rs, findOrCreate(root, rs.getAddress().getProvince(),  rs.getAddress().getCity(),  rs.getAddress().getCounty()));
        }
        return root;

    }

    protected TreeNode findOrCreate(TreeNode root, String province, String city, String county) {
        TreeNode pn = findOrCreateChildByData(root, province, Constants.TREENODE_TYPE_PROVINCE);
        pn.setSelectable(false);
        TreeNode cn = findOrCreateChildByData(pn, city, Constants.TREENODE_TYPE_CITY);
        TreeNode countyn = findOrCreateChildByData(cn, county, Constants.TREENODE_TYPE_COUNTY);
        return countyn;
    }

    protected TreeNode findOrCreateChildByData(TreeNode parent, Object data, String type) {
        for (TreeNode t : parent.getChildren()) {
            if (t.getData().equals(data)) {
                return t;
            }
        }
        return new DefaultTreeNode(type, data, parent);

    }
}
