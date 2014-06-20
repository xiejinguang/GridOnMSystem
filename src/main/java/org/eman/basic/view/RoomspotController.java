package org.eman.basic.view;

import org.eman.basic.model.Roomspot;
import org.eman.basic.view.util.JsfUtil;
import org.eman.basic.view.util.JsfUtil.PersistAction;
import org.eman.basic.facade.RoomspotFacade;

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

@Named("roomspotController")
@ViewScoped
public class RoomspotController implements Serializable {

    @EJB
    private org.eman.basic.facade.RoomspotFacade ejbFacade;
    private List<Roomspot> items = null;
    private Roomspot created;
    private List<Roomspot> selectedItems;
    private Map<String, Object> searchCons;
    private ResourceBundle bundle;

    public RoomspotController() {
    }

    @PostConstruct
    public void init() {
        this.searchCons = new HashMap();
        this.bundle = ResourceBundle.getBundle("/org/eman/basic_i18n");
    }

    public Roomspot getCreated() {
        return created;
    }

    public void setCreated(Roomspot created) {
        this.created = created;
    }

    public List<Roomspot> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<Roomspot> selectedItems) {
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

    private RoomspotFacade getFacade() {
        return ejbFacade;
    }

    public Roomspot prepareCreate() {

        created = new Roomspot();
        initializeKey();
        return created;
    }

    public void create() {
        persist(PersistAction.CREATE, bundle.getString("RoomspotCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, bundle.getString("RoomspotUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, bundle.getString("RoomspotDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedItems = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Roomspot> searchItems() {
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

    public List<Roomspot> allItems() {
        items = getFacade().findAll();

        return items;
    }

    public List<Roomspot> getItems() {
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
                    for (Roomspot selected : selectedItems) {
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

    public Roomspot getRoomspot(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Roomspot> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Roomspot> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Roomspot.class)
    public static class RoomspotControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RoomspotController controller = (RoomspotController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "roomspotController");
            return controller.getRoomspot(getKey(value));
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
            if (object instanceof Roomspot) {
                Roomspot o = (Roomspot) object;
                return getStringKey(o.getRoomID());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Roomspot.class.getName()});
                return null;
            }
        }

    }

}
