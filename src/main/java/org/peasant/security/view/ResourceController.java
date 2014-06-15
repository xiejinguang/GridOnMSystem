package org.peasant.security.view;

import org.peasant.security.model.Resource;
import org.peasant.security.view.util.JsfUtil;
import org.peasant.security.view.util.JsfUtil.PersistAction;
import org.peasant.security.facade.ResourceFacade;

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

@Named("resourceController")
@ViewScoped
public class ResourceController implements Serializable {

    @EJB
    private org.peasant.security.facade.ResourceFacade ejbFacade;
    private List<Resource> items = null;
    private Resource created;
    private List<Resource> selectedItems;
    private Map<String, Object> searchCons;
    private ResourceBundle bundle;

    public ResourceController() {
    }

    @PostConstruct
    public void init() {
        this.searchCons = new HashMap();
        this.bundle = ResourceBundle.getBundle("/org/peasant/security_i18n");
    }

    public Resource getCreated() {
        return created;
    }

    public void setCreated(Resource created) {
        this.created = created;
    }

    public List<Resource> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<Resource> selectedItems) {
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

    private ResourceFacade getFacade() {
        return ejbFacade;
    }

    public Resource prepareCreate() {
        created = new Resource();
        initializeEmbeddableKey();
        return created;
    }

    public void create() {
        persist(PersistAction.CREATE, bundle.getString("ResourceCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, bundle.getString("ResourceUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, bundle.getString("ResourceDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedItems = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Resource> searchItems() {
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

    public List<Resource> allItems() {
        items = getFacade().findAll();

        return items;
    }

    public List<Resource> getItems() {
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
                    for (Resource selected : selectedItems) {
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

    public Resource getResource(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Resource> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Resource> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Resource.class)
    public static class ResourceControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ResourceController controller = (ResourceController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "resourceController");
            return controller.getResource(getKey(value));
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
            if (object instanceof Resource) {
                Resource o = (Resource) object;
                return getStringKey(o.getResourceId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Resource.class.getName()});
                return null;
            }
        }

    }

}