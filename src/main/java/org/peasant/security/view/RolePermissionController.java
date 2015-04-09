package org.peasant.security.view;

import org.peasant.security.model.RolePermission;
import org.peasant.security.view.util.JsfUtil;
import org.peasant.security.view.util.JsfUtil.PersistAction;
import org.peasant.security.facade.RolePermissionFacade;

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

@Named("rolePermissionController")
@ViewScoped
public class RolePermissionController implements Serializable {

    @EJB
    protected org.peasant.security.facade.RolePermissionFacade ejbFacade;
    private List<RolePermission> items = null;
    private RolePermission created;
    private List<RolePermission> selectedItems;
    private Map<String, Object> searchCons;
    protected ResourceBundle bundle;

    public RolePermissionController() {
    }

    @PostConstruct
    public void init() {
        this.searchCons = new HashMap();
        this.bundle = ResourceBundle.getBundle("/org/peasant/security_i18n");
    }

    public RolePermission getCreated() {
        return created;
    }

    public void setCreated(RolePermission created) {
        this.created = created;
    }

    public List<RolePermission> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<RolePermission> selectedItems) {
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
        created.setIdrolePerm(org.peasant.util.Utils.generateUniqueKey());
    }

    private RolePermissionFacade getFacade() {
        return ejbFacade;
    }

    public RolePermission prepareCreate() {

        created = new RolePermission();
        initializeKey();
        return created;
    }

    public void create() {
        persist(PersistAction.CREATE, bundle.getString("RolePermissionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, bundle.getString("RolePermissionUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, bundle.getString("RolePermissionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedItems = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RolePermission> searchItems() {

        items = findItemsByConditions(construtSearchParams(this.searchCons));
        return items;
    }

    protected List<RolePermission> findItemsByConditions(Map<String, Object> params) {
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

    public List<RolePermission> allItems() {
        items = getFacade().findAll();

        return items;
    }

    public List<RolePermission> getItems() {
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
                    for (RolePermission selected : selectedItems) {
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

    public RolePermission getRolePermission(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<RolePermission> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RolePermission> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RolePermission.class, value = "org.peasant.security.RolePermission")
    public static class RolePermissionFacesConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RolePermissionController controller = (RolePermissionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rolePermissionController");
            return controller.getRolePermission(getKey(value));
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
            if (object instanceof RolePermission) {
                RolePermission o = (RolePermission) object;
                return getStringKey(o.getIdrolePerm());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RolePermission.class.getName()});
                return null;
            }
        }

    }

}
