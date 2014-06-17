package org.eman.assit.view;

import org.eman.assit.model.AsistCandidateValue;
import org.eman.assit.view.util.JsfUtil;
import org.eman.assit.view.util.JsfUtil.PersistAction;
import org.eman.assit.facade.AsistCandidateValueFacade;

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
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ValueChangeEvent;

@Named("asistCandidateValueController")
@ViewScoped
public class AsistCandidateValueController implements Serializable {

    @EJB
    protected org.eman.assit.facade.AsistCandidateValueFacade ejbFacade;
    private List<AsistCandidateValue> items = null;
    private AsistCandidateValue created;
    private List<AsistCandidateValue> selectedItems;
    private Map<String, Object> searchCons;
    private ResourceBundle bundle;
    private AsistCandidateValue selected;

    public void handleValueChange(ValueChangeEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ValueChanged", "oldvalue:" + event.getOldValue() + ";newvalue:" + event.getNewValue() + ";The Selected:" + this.selected));

    }

    public AsistCandidateValue getSelected() {
        return selected;
    }

    public void setSelected(AsistCandidateValue selected) {
        this.selected = selected;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", "The Selected:" + selected));
    }

    public AsistCandidateValueController() {
    }

    @PostConstruct
    public void init() {
        this.searchCons = new HashMap();
        this.bundle = ResourceBundle.getBundle("/org/eman/asist_i18n");
    }

    public AsistCandidateValue getCreated() {
        return created;
    }

    public void setCreated(AsistCandidateValue created) {
        this.created = created;
    }

    public List<AsistCandidateValue> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<AsistCandidateValue> selectedItems) {
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
        created.setId(org.eman.util.Utils.generateUniqueKey());
    }

    private AsistCandidateValueFacade getFacade() {
        return ejbFacade;
    }

    public AsistCandidateValue prepareCreate() {

        created = new AsistCandidateValue();
        initializeKey();
        return created;
    }

    public void create() {
        persist(PersistAction.CREATE, bundle.getString("AsistCandidateValueCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, bundle.getString("AsistCandidateValueUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, bundle.getString("AsistCandidateValueDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedItems = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<AsistCandidateValue> searchItems() {
        

        items = findItemsByConditions(construtSearchParams(this.searchCons));
        return items;
    }

    protected List<AsistCandidateValue> findItemsByConditions(Map<String,Object> params) {
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

    public List<AsistCandidateValue> allItems() {
        items = getFacade().findAll();

        return items;
    }

    public List<AsistCandidateValue> getItems() {
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
                    for (AsistCandidateValue selected : selectedItems) {
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

    public AsistCandidateValue getAsistCandidateValue(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<AsistCandidateValue> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<AsistCandidateValue> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = AsistCandidateValue.class)
    public static class AsistCandidateValueControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AsistCandidateValueController controller = (AsistCandidateValueController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "asistCandidateValueController");
            return controller.getAsistCandidateValue(getKey(value));
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
            if (object instanceof AsistCandidateValue) {
                AsistCandidateValue o = (AsistCandidateValue) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AsistCandidateValue.class.getName()});
                return null;
            }
        }

    }

}
