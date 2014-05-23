package org.gmsys.view;

import org.gmsys.model.entity.FixDemand;
import org.gmsys.view.util.JsfUtil;
import org.gmsys.view.util.JsfUtil.PersistAction;
import org.gmsys.ejb.FixDemandFacade;

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


@Named("fixDemandController")
@ViewScoped
public class FixDemandController implements Serializable {


    @EJB private org.gmsys.ejb.FixDemandFacade ejbFacade;
    private List<FixDemand> items = null;
    private FixDemand selected;
    private List<FixDemand> selectedItems;
    private Map<String,Object> searchCons;

    public FixDemandController() {
    }
    
    @PostConstruct
    public void init(){
        this.searchCons = new HashMap();
    }


    public FixDemand getSelected() {
        return selected;
    }

    public void setSelected(FixDemand selected) {
        this.selected = selected;
    }

    public  List<FixDemand> getSelectedItems() {
        return selectedItems;
    }
    
    public void setSelectedItems(List<FixDemand> selectedItems){
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

    private FixDemandFacade getFacade() {
        return ejbFacade;
    }

    public FixDemand prepareCreate() {
        selected = new FixDemand();
        initializeEmbeddableKey();
        return selected;
    }
    public List<FixDemand> prepareSearch(){
        this.items=null;
        return this.items;
        
}

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("FixDemandCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("FixDemandUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("FixDemandDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<FixDemand> searchItems() {
        items = getFacade().findByConditions(searchCons);
        return items;
    }

    public List<FixDemand> allItems() {
            items = getFacade().findAll();

        return items;
    }






    public List<FixDemand> getItems() {
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
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

    public FixDemand getFixDemand(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<FixDemand> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<FixDemand> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass=FixDemand.class)
    public static class FixDemandControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FixDemandController controller = (FixDemandController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "fixDemandController");
            return controller.getFixDemand(getKey(value));
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
            if (object instanceof FixDemand) {
                FixDemand o = (FixDemand) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), FixDemand.class.getName()});
                return null;
            }
        }

    }

}
