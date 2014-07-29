/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eman.gmsys;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eman.basic.model.Roomspot;
import org.eman.gmsys.model.StationProperty;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

/**
 *
 * @author 谢金光
 */
@Named
@ViewScoped
public class StationPropertyPatrolView {

    @Inject
    StationPropertyFacade spFacade;

    private TreeNode selectedNodes[];

    private List<Roomspot> selectedRoomspots;

    public TreeNode[] getSelectedNodes() {
        return selectedNodes;
    }

    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
    }

    public List<Roomspot> getSelectedRoomspots() {
        return selectedRoomspots;
    }

    public void setSelectedRoomspots(List<Roomspot> selectedRoomspots) {
        this.selectedRoomspots = selectedRoomspots;
    }

    public void onNodeSelect(NodeSelectEvent se) {
        selectedRoomspots = new LinkedList<>();
        for (TreeNode tn : selectedNodes) {
            if (tn.getData() instanceof Roomspot) {
                selectedRoomspots.add((Roomspot) tn.getData());
            }
        }
    }

    public List<StationProperty> getStationPropertys(Roomspot rs) {
        Map<String, Object> params = new HashMap<>();
        params.put("roomspotId", rs);
        List<StationProperty> l = spFacade.findByConditions(params);
        if (l == null) {
            l = new LinkedList<>();
        }
        adjustListSizeToFitPage(l);
        return l;
    }

    protected void adjustListSizeToFitPage(List l) {
        int firstPageRows = 15;
        int rowsPerPage = 20;
        int j = 0;
        if (l.size() <= firstPageRows) {
            j = firstPageRows - l.size();

        } else {
            j = rowsPerPage - (l.size() - firstPageRows) % rowsPerPage;
        }
        for (int n = 0; n < j; n++) {
            l.add(null);
        }

    }

}
