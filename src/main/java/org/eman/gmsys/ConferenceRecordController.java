package org.eman.gmsys;

import org.eman.gmsys.model.ConferenceRecord;
import org.eman.gmsys.util.JsfUtil;
import org.eman.gmsys.util.JsfUtil.PersistAction;

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

@Named("conferenceRecordController")
@ViewScoped
public class ConferenceRecordController implements Serializable {

    @EJB
    protected org.eman.gmsys.ConferenceRecordFacade ejbFacade;
    private List<ConferenceRecord> items = null;
    private ConferenceRecord created;
    private List<ConferenceRecord> selectedItems;
    private Map<String, Object> searchCons;
    protected ResourceBundle bundle;

    public ConferenceRecordController() {
    }

    @PostConstruct
    public void init() {
        this.searchCons = new HashMap();
        this.bundle = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(), "gmsys_i18n");

    }

    public ConferenceRecord getCreated() {
        return created;
    }

    public void setCreated(ConferenceRecord created) {
        this.created = created;
    }

    public List<ConferenceRecord> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<ConferenceRecord> selectedItems) {
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
        created.setUuid(org.peasant.util.Utils.generateUniqueKey());
    }

    private ConferenceRecordFacade getFacade() {
        return ejbFacade;
    }

    public ConferenceRecord prepareCreate() {

        created = new ConferenceRecord();
        initializeKey();
        return created;
    }

    public void create() {
        persist(PersistAction.CREATE, bundle.getString("ConferenceRecordCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, bundle.getString("ConferenceRecordUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, bundle.getString("ConferenceRecordDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedItems = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ConferenceRecord> searchItems() {

        items = findItemsByConditions(construtSearchParams(this.searchCons));
        return items;
    }

    protected List<ConferenceRecord> findItemsByConditions(Map<String, Object> params) {
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

    public List<ConferenceRecord> allItems() {
        items = getFacade().findAll();

        return items;
    }

    public List<ConferenceRecord> getItems() {
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
                    for (ConferenceRecord selected : selectedItems) {
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

    public ConferenceRecord getConferenceRecord(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<ConferenceRecord> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ConferenceRecord> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ConferenceRecord.class)
    public static class ConferenceRecordControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ConferenceRecordController controller = (ConferenceRecordController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "conferenceRecordController");
            return controller.getConferenceRecord(getKey(value));
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
            if (object instanceof ConferenceRecord) {
                ConferenceRecord o = (ConferenceRecord) object;
                return getStringKey(o.getUuid());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ConferenceRecord.class.getName()});
                return null;
            }
        }

    }

}
