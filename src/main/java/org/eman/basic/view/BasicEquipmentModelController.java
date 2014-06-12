package org.eman.basic.view;

import org.eman.basic.model.BasicEquipmentModel;
import org.eman.basic.view.util.JsfUtil;
import org.eman.basic.view.util.JsfUtil.PersistAction;
import org.eman.basic.facade.BasicEquipmentModelFacade;

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

@Named("basicEquipmentModelController")
@ViewScoped
public class BasicEquipmentModelController implements Serializable {

    @EJB
    private org.eman.basic.facade.BasicEquipmentModelFacade ejbFacade;
    private List<BasicEquipmentModel> items = null;
    private BasicEquipmentModel created;
    private List<BasicEquipmentModel> selectedItems;
    private Map<String, Object> searchCons;
    private ResourceBundle bundle;

    public BasicEquipmentModelController() {
    }

    @PostConstruct
    public void init() {
        this.searchCons = new HashMap();
        this.bundle = ResourceBundle.getBundle("/org/eman/basic_i18n");
    }

    public BasicEquipmentModel getCreated() {
        return created;
    }

    public void setCreated(BasicEquipmentModel created) {
        this.created = created;
    }

    public List<BasicEquipmentModel> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<BasicEquipmentModel> selectedItems) {
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
        created.setEquipModelID(org.eman.util.Utils.generateUniqueKey());
    }

    private BasicEquipmentModelFacade getFacade() {
        return ejbFacade;
    }

    public BasicEquipmentModel prepareCreate() {

        created = new BasicEquipmentModel();
        initializeKey();
        return created;
    }

    public void create() {
        persist(PersistAction.CREATE, bundle.getString("BasicEquipmentModelCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, bundle.getString("BasicEquipmentModelUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, bundle.getString("BasicEquipmentModelDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedItems = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<BasicEquipmentModel> searchItems() {
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

    public List<BasicEquipmentModel> allItems() {
        items = getFacade().findAll();

        return items;
    }

    public List<BasicEquipmentModel> getItems() {
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
                    for (BasicEquipmentModel selected : selectedItems) {
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

    public BasicEquipmentModel getBasicEquipmentModel(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<BasicEquipmentModel> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<BasicEquipmentModel> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = BasicEquipmentModel.class)
    public static class BasicEquipmentModelControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BasicEquipmentModelController controller = (BasicEquipmentModelController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "basicEquipmentModelController");
            return controller.getBasicEquipmentModel(getKey(value));
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
            if (object instanceof BasicEquipmentModel) {
                BasicEquipmentModel o = (BasicEquipmentModel) object;
                return getStringKey(o.getEquipModelID());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BasicEquipmentModel.class.getName()});
                return null;
            }
        }

    }

}
