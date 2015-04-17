package org.eman.basic;

import org.eman.basic.model.util.JsfUtil;
import org.eman.basic.model.util.JsfUtil.PersistAction;
import org.eman.basic.EquipmentModelFacade;

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
import org.eman.basic.model.EquipmentModel;

@Named("equipmentModelController")
@ViewScoped
public class EquipmentModelController implements Serializable {

    @EJB
    protected org.eman.basic.EquipmentModelFacade ejbFacade;
    private List<EquipmentModel> items = null;
    private EquipmentModel created;
    private List<EquipmentModel> selectedItems;
    private List<EquipmentModel> filteredValue;
    private Map<String, Object> searchCons;
    protected ResourceBundle bundle;

    public EquipmentModelController() {
    }

    @PostConstruct
    public void init() {
        this.searchCons = new HashMap();
        this.bundle = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(), "/org/eman/i18n_eman_basic");

    }

    public EquipmentModel getCreated() {
        return created;
    }

    public void setCreated(EquipmentModel created) {
        this.created = created;
    }

    public List<EquipmentModel> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<EquipmentModel> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public List<EquipmentModel> getFilteredValue() {
        return selectedItems;
    }

    public void setFilteredValue(List<EquipmentModel> selectedItems) {
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

    private EquipmentModelFacade getFacade() {
        return ejbFacade;
    }

    public EquipmentModel prepareCreate() {

        created = new EquipmentModel();
        initializeKey();
        return created;
    }

    public void create() {
        persist(PersistAction.CREATE, bundle.getString("EquipmentModelCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, bundle.getString("EquipmentModelUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, bundle.getString("EquipmentModelDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedItems = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EquipmentModel> searchItems() {

        items = findItemsByConditions(construtSearchParams(this.searchCons));
        return items;
    }

    protected List<EquipmentModel> findItemsByConditions(Map<String, Object> params) {
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

    public List<EquipmentModel> allItems() {
        items = getFacade().findAll();

        return items;
    }

    public List<EquipmentModel> getItems() {
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
                    for (EquipmentModel selected : selectedItems) {
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

    public EquipmentModel getEquipmentModel(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<EquipmentModel> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EquipmentModel> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = EquipmentModel.class, value = "EquipmentModel")
    public static class EquipmentModelFacesConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EquipmentModelController controller = (EquipmentModelController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "equipmentModelController");
            return controller.getEquipmentModel(getKey(value));
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
            if (object instanceof EquipmentModel) {
                EquipmentModel o = (EquipmentModel) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EquipmentModel.class.getName()});
                return null;
            }
        }

    }

}
