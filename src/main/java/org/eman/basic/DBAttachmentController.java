package org.eman.basic;

import org.peasant.util.repositoryimpl.DBAttachment;
import org.eman.basic.util.JsfUtil;
import org.eman.basic.util.JsfUtil.PersistAction;
import org.eman.basic.ejb.DBAttachmentFacade;

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

@Named("dBAttachmentController")
@ViewScoped
public class DBAttachmentController implements Serializable {

    @EJB
    private org.eman.basic.ejb.DBAttachmentFacade ejbFacade;
    private List<DBAttachment> items = null;
    private DBAttachment created;
    private List<DBAttachment> selectedItems;
    private Map<String, Object> searchCons;
    private ResourceBundle bundle;

    public DBAttachmentController() {
    }

    @PostConstruct
    public void init() {
        this.searchCons = new HashMap();
        this.bundle = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(), "bundle");

    }

    public DBAttachment getCreated() {
        return created;
    }

    public void setCreated(DBAttachment created) {
        this.created = created;
    }

    public List<DBAttachment> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<DBAttachment> selectedItems) {
        this.selectedItems = selectedItems;
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

    private DBAttachmentFacade getFacade() {
        return ejbFacade;
    }

    public DBAttachment prepareCreate() {
        created = new DBAttachment();
        initializeEmbeddableKey();
        return created;
    }

    public List<DBAttachment> prepareSearch() {
        this.items = null;
        return this.items;

    }

    public void create() {
        persist(PersistAction.CREATE, bundle.getString("DBAttachmentCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, bundle.getString("DBAttachmentUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, bundle.getString("DBAttachmentDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedItems = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<DBAttachment> searchItems() {
        items = getFacade().findByConditions(searchCons);
        return items;
    }

    public List<DBAttachment> allItems() {
        items = getFacade().findAll();

        return items;
    }

    public List<DBAttachment> getItems() {
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {

        try {

            switch (persistAction) {
                case CREATE:
                    getFacade().edit(created);
                    break;

                default: {
                    for (DBAttachment selected : selectedItems) {
                        if (selected != null) {
                            setEmbeddableKeys();
                            switch (persistAction) {
                                case DELETE:
                                    getFacade().remove(selected);
                                    break;
                                case UPDATE:
                                    getFacade().edit(selected);
                                    break;
                            }
                        }
                    }
                }

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
                JsfUtil.addErrorMessage(ex, bundle.getString("PersistenceErrorOccured"));
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex, bundle.getString("PersistenceErrorOccured"));
        }

    }

    public DBAttachment getDBAttachment(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<DBAttachment> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<DBAttachment> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = DBAttachment.class)
    public static class DBAttachmentControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DBAttachmentController controller = (DBAttachmentController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "dBAttachmentController");
            return controller.getDBAttachment(getKey(value));
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
            if (object instanceof DBAttachment) {
                DBAttachment o = (DBAttachment) object;
                return getStringKey(o.getAttachmentId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DBAttachment.class.getName()});
                return null;
            }
        }

    }

}
