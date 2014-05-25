package org.gmsys.view;

import org.gmsys.model.entity.GridInfo;
import org.gmsys.view.util.JsfUtil;
import org.gmsys.view.util.JsfUtil.PersistAction;
import org.gmsys.ejb.GridInfoFacade;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.transaction.Transactional;


@Named("gridInfoController")
@ViewScoped
public class GridInfoController implements Serializable {


    @EJB private org.gmsys.ejb.GridInfoFacade ejbFacade;
    private List<GridInfo> items = null;
    private GridInfo selected;
    private List<GridInfo> selectedItems;
    private Map<String,Object> searchCons;

    public GridInfoController() {
    }
    
    @PostConstruct
    public void init(){
        this.searchCons = new HashMap();
    }


    public GridInfo getSelected() {
        return selected;
    }

    public void setSelected(GridInfo selected) {
        this.selected = selected;
    }

    public  List<GridInfo> getSelectedItems() {
        return selectedItems;
    }
    
    public void setSelectedItems(List<GridInfo> selectedItems){
        this.selectedItems =selectedItems;
    }

    public Map<String, Object> getSearchCons() {
        return searchCons;
    }

    public void setSearchCons(Map<String, Object> searchCons) {
        this.searchCons = searchCons;
    }


    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private GridInfoFacade getFacade() {
        return ejbFacade;
    }

    public GridInfo prepareCreate() {
        selected = new GridInfo();
        initializeEmbeddableKey();
        return selected;
    }
    public List<GridInfo> prepareSearch(){
        this.items=null;
        return this.items;
        
}

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("GridInfoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("GridInfoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("GridInfoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<GridInfo> searchItems() {
        items = getFacade().findByConditions(searchCons);
        return items;
    }

    public List<GridInfo> allItems() {
            items = getFacade().findAll();

        return items;
    }






    public List<GridInfo> getItems() {
        return items;
    }
    @Transactional
    private void persist(PersistAction persistAction, String successMessage) {
        for(GridInfo selected:selectedItems)
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public GridInfo getGridInfo(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<GridInfo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<GridInfo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass=GridInfo.class)
    public static class GridInfoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GridInfoController controller = (GridInfoController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "gridInfoController");
            return controller.getGridInfo(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof GridInfo) {
                GridInfo o = (GridInfo) object;
                return getStringKey(o.getGridId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), GridInfo.class.getName()});
                return null;
            }
        }

    }

}
