package org.eman.basic;

import org.eman.basic.model.util.JsfUtil;
import org.eman.basic.model.util.JsfUtil.PersistAction;
import org.eman.basic.BTSnodeFacade;

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
import org.eman.basic.model.BTSnode;

@Named("bTSnodeController")
@ViewScoped
public class BTSnodeController implements Serializable {

    @EJB
    protected org.eman.basic.BTSnodeFacade ejbFacade;
    private List<BTSnode> items = null;
    private BTSnode created;
    private List<BTSnode> selectedItems;
    private List<BTSnode> filteredValue;
    private Map<String, Object> searchCons;
    protected ResourceBundle bundle;

    public BTSnodeController() {
    }

    @PostConstruct
    public void init() {
        this.searchCons = new HashMap();
        this.bundle = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(), "/org/eman/i18n_eman_basic");

    }

    public BTSnode getCreated() {
        return created;
    }

    public void setCreated(BTSnode created) {
        this.created = created;
    }

    public List<BTSnode> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<BTSnode> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public List<BTSnode> getFilteredValue() {
        return selectedItems;
    }

    public void setFilteredValue(List<BTSnode> selectedItems) {
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

    protected void initializeKey() {
        created.setId(org.peasant.util.Utils.generateUniqueKey());
    }

    private BTSnodeFacade getFacade() {
        return ejbFacade;
    }

    public BTSnode prepareCreate() {

        created = new BTSnode();
        initializeKey();
        return created;
    }

    public void create() {
        persist(PersistAction.CREATE, bundle.getString("BTSnodeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, bundle.getString("BTSnodeUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, bundle.getString("BTSnodeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedItems = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<BTSnode> searchItems() {

        items = findItemsByConditions(construtSearchParams(this.searchCons));
        return items;
    }

    protected List<BTSnode> findItemsByConditions(Map<String, Object> params) {
        return getFacade().findByConditions(params);
    }

    protected Map<String, Object> construtSearchParams(Map<String, Object> params) {
        Map<String, Object> newparams = new HashMap<>();
        for (String param : searchCons.keySet()) {
            Object value = searchCons.get(param);
            if (value != null) {
                if (value instanceof String) {
                    if (((String) value).isEmpty()) {
                        continue;
                    } else {
                        newparams.put(param, '%' + ((String) value) + '%');
                        continue;
                    }
                } else {
                    newparams.put(param, value);
                }
            }
        }
        return newparams;
    }

    public List<BTSnode> allItems() {
        items = getFacade().findAll();

        return items;
    }

    public List<BTSnode> getItems() {
        if (null == items) {
            //TODO,根据上次查询条件记录获取记录
        }
        return items;
    }

    protected void persist(PersistAction persistAction, String successMessage) {

        try {

            switch (persistAction) {
                case CREATE:
                    getFacade().edit(created);
                    break;

                default: {
                    for (BTSnode selected : selectedItems) {
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

    public BTSnode getBTSnode(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<BTSnode> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<BTSnode> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = BTSnode.class, value = "org.eman.basic.model.BTSnode")
    public static class BTSnodeFacesConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BTSnodeController controller = (BTSnodeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "bTSnodeController");
            return controller.getBTSnode(getKey(value));
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
            if (object instanceof BTSnode) {
                BTSnode o = (BTSnode) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BTSnode.class.getName()});
                return null;
            }
        }

    }

}
