package org.eman.asist.view;

import org.eman.asist.model.KeyCode;
import org.eman.asist.view.util.JsfUtil;
import org.eman.asist.view.util.JsfUtil.PersistAction;
import org.eman.asist.facade.KeyCodeFacade;

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

@Named("keyGeneratorController")
@ViewScoped
public class KeyGeneratorController implements Serializable {

    @EJB
    protected org.eman.asist.facade.KeyCodeFacade ejbFacade;
    private List<KeyCode> items = null;
    private KeyCode created;
    private List<KeyCode> selectedItems;
    private Map<String, Object> searchCons;
    protected ResourceBundle bundle;

    public KeyGeneratorController() {
    }

    @PostConstruct
    public void init() {
        this.searchCons = new HashMap();
        this.bundle = ResourceBundle.getBundle("/org/eman/asist_i18n");
    }

    public KeyCode getCreated() {
        return created;
    }

    public void setCreated(KeyCode created) {
        this.created = created;
    }

    public List<KeyCode> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<KeyCode> selectedItems) {
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
        created.setDiscriminator(org.eman.util.Utils.generateUniqueKey());
    }

    private KeyCodeFacade getFacade() {
        return ejbFacade;
    }

    public KeyCode prepareCreate() {

        created = new KeyCode();
        initializeKey();
        return created;
    }

    public void create() {
        persist(PersistAction.CREATE, bundle.getString("KeyGeneratorCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, bundle.getString("KeyGeneratorUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, bundle.getString("KeyGeneratorDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedItems = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<KeyCode> searchItems() {

        items = findItemsByConditions(construtSearchParams(this.searchCons));
        return items;
    }

    protected List<KeyCode> findItemsByConditions(Map<String, Object> params) {
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

    public List<KeyCode> allItems() {
        items = getFacade().findAll();

        return items;
    }

    public List<KeyCode> getItems() {
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
                    for (KeyCode selected : selectedItems) {
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

    public KeyCode getKeyGenerator(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<KeyCode> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<KeyCode> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = KeyCode.class, value = "KeyGenerator")
    public static class KeyGeneratorFacesConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            KeyGeneratorController controller = (KeyGeneratorController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "keyGeneratorController");
            return controller.getKeyGenerator(getKey(value));
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
            if (object instanceof KeyCode) {
                KeyCode o = (KeyCode) object;
                return getStringKey(o.getDiscriminator());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), KeyCode.class.getName()});
                return null;
            }
        }

    }

}
