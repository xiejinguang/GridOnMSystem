package org.eman.basic.view;

import org.eman.basic.model.BasicRoomspot;
import org.eman.basic.view.util.JsfUtil;
import org.eman.basic.view.util.JsfUtil.PersistAction;
import org.eman.basic.facade.BasicRoomspotFacade;

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

@Named("basicRoomspotController")
@ViewScoped
public class BasicRoomspotController implements Serializable {

    @EJB
    private org.eman.basic.facade.BasicRoomspotFacade ejbFacade;
    private List<BasicRoomspot> items = null;
    private BasicRoomspot created;
    private List<BasicRoomspot> selectedItems;
    private Map<String, Object> searchCons;
    private ResourceBundle bundle;

    public BasicRoomspotController() {
    }

    @PostConstruct
    public void init() {
        this.searchCons = new HashMap();
        this.bundle = ResourceBundle.getBundle("/org/eman/basic_i18n");
    }

    public BasicRoomspot getCreated() {
        return created;
    }

    public void setCreated(BasicRoomspot created) {
        this.created = created;
    }

    public List<BasicRoomspot> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<BasicRoomspot> selectedItems) {
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
        created.setRoomID(org.eman.util.Utils.generateUniqueKey());
    }

    private BasicRoomspotFacade getFacade() {
        return ejbFacade;
    }

    public BasicRoomspot prepareCreate() {

        created = new BasicRoomspot();
        initializeKey();
        return created;
    }

    public void create() {
        persist(PersistAction.CREATE, bundle.getString("BasicRoomspotCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, bundle.getString("BasicRoomspotUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, bundle.getString("BasicRoomspotDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedItems = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<BasicRoomspot> searchItems() {
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

    public List<BasicRoomspot> allItems() {
        items = getFacade().findAll();

        return items;
    }

    public List<BasicRoomspot> getItems() {
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
                    for (BasicRoomspot selected : selectedItems) {
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

    public BasicRoomspot getBasicRoomspot(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<BasicRoomspot> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<BasicRoomspot> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = BasicRoomspot.class)
    public static class BasicRoomspotControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BasicRoomspotController controller = (BasicRoomspotController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "basicRoomspotController");
            return controller.getBasicRoomspot(getKey(value));
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
            if (object instanceof BasicRoomspot) {
                BasicRoomspot o = (BasicRoomspot) object;
                return getStringKey(o.getRoomID());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BasicRoomspot.class.getName()});
                return null;
            }
        }

    }

}
