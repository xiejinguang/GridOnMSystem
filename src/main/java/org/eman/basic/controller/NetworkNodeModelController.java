package org.eman.basic.controller;

import org.eman.basic.model.NetworkNodeModel;
import org.eman.basic.controller.util.JsfUtil;
import org.eman.basic.controller.util.JsfUtil.PersistAction;
import org.eman.basic.facade.NetworkNodeModelFacade;

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

@Named("networkNodeModelController")
@ViewScoped
public class NetworkNodeModelController implements Serializable {

    @EJB
    protected org.eman.basic.facade.NetworkNodeModelFacade ejbFacade;
    private List<NetworkNodeModel> items = null;
    private NetworkNodeModel created;
    private List<NetworkNodeModel> selectedItems;
    private List<NetworkNodeModel> filteredValue;
    private Map<String, Object> searchCons;
    protected ResourceBundle bundle;

    public NetworkNodeModelController() {
    }

    @PostConstruct
    public void init() {
        this.searchCons = new HashMap();
        this.bundle = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(), "i18n_eman_basic");

    }

    public NetworkNodeModel getCreated() {
        return created;
    }

    public void setCreated(NetworkNodeModel created) {
        this.created = created;
    }

    public List<NetworkNodeModel> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<NetworkNodeModel> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public List<NetworkNodeModel> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<NetworkNodeModel> filteredValue) {
        this.filteredValue = filteredValue;
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

    private NetworkNodeModelFacade getFacade() {
        return ejbFacade;
    }

    public NetworkNodeModel prepareCreate() {

        created = new NetworkNodeModel();
        initializeKey();
        return created;
    }

    public void create() {
        persist(PersistAction.CREATE, bundle.getString("NetworkNodeModelCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, bundle.getString("NetworkNodeModelUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, bundle.getString("NetworkNodeModelDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedItems = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<NetworkNodeModel> searchItems() {

        items = findItemsByConditions(construtSearchParams(this.searchCons));
        return items;
    }

    protected List<NetworkNodeModel> findItemsByConditions(Map<String, Object> params) {
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

    public List<NetworkNodeModel> allItems() {
        items = getFacade().findAll();

        return items;
    }

    public List<NetworkNodeModel> getItems() {
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
                    for (NetworkNodeModel selected : selectedItems) {
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

    public NetworkNodeModel getNetworkNodeModel(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<NetworkNodeModel> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<NetworkNodeModel> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = NetworkNodeModel.class, value = "org.eman.basic.model.NetworkNodeModel")
    public static class NetworkNodeModelFacesConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NetworkNodeModelController controller = (NetworkNodeModelController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "networkNodeModelController");
            return controller.getNetworkNodeModel(getKey(value));
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
            if (object instanceof NetworkNodeModel) {
                NetworkNodeModel o = (NetworkNodeModel) object;
                return getStringKey(o.getUuid());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), NetworkNodeModel.class.getName()});
                return null;
            }
        }

    }

}
