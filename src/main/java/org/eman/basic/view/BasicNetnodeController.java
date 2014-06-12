package org.eman.basic.view;

import org.eman.basic.model.BasicNetnode;
import org.eman.basic.view.util.JsfUtil;
import org.eman.basic.view.util.JsfUtil.PersistAction;
import org.eman.basic.facade.BasicNetnodeFacade;

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

@Named("basicNetnodeController")
@ViewScoped
public class BasicNetnodeController implements Serializable {

    @EJB
    private org.eman.basic.facade.BasicNetnodeFacade ejbFacade;
    private List<BasicNetnode> items = null;
    private BasicNetnode created;
    private List<BasicNetnode> selectedItems;
    private Map<String, Object> searchCons;
    private ResourceBundle bundle;

    public BasicNetnodeController() {
    }

    @PostConstruct
    public void init() {
        this.searchCons = new HashMap();
        this.bundle = ResourceBundle.getBundle("/org/eman/basic_i18n");
    }

    public BasicNetnode getCreated() {
        return created;
    }

    public void setCreated(BasicNetnode created) {
        this.created = created;
    }

    public List<BasicNetnode> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<BasicNetnode> selectedItems) {
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
        created.setNetNodeID(org.eman.util.Utils.generateUniqueKey());
    }

    private BasicNetnodeFacade getFacade() {
        return ejbFacade;
    }

    public BasicNetnode prepareCreate() {

        created = new BasicNetnode();
        initializeKey();
        return created;
    }

    public void create() {
        persist(PersistAction.CREATE, bundle.getString("BasicNetnodeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, bundle.getString("BasicNetnodeUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, bundle.getString("BasicNetnodeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedItems = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<BasicNetnode> searchItems() {
        construtSearchParams(this.searchCons);

        items = getFacade().findByConditions(construtSearchParams(this.searchCons));
        return items;
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

    public List<BasicNetnode> allItems() {
        items = getFacade().findAll();

        return items;
    }

    public List<BasicNetnode> getItems() {
        if (null == items) {
            //TODO,根据上次查询条件记录获取记录
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {

        try {

            switch (persistAction) {
                case CREATE:
                    getFacade().edit(created);
                    break;

                default: {
                    for (BasicNetnode selected : selectedItems) {
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

    public BasicNetnode getBasicNetnode(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<BasicNetnode> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<BasicNetnode> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = BasicNetnode.class)
    public static class BasicNetnodeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BasicNetnodeController controller = (BasicNetnodeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "basicNetnodeController");
            return controller.getBasicNetnode(getKey(value));
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
            if (object instanceof BasicNetnode) {
                BasicNetnode o = (BasicNetnode) object;
                return getStringKey(o.getNetNodeID());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BasicNetnode.class.getName()});
                return null;
            }
        }

    }

}
