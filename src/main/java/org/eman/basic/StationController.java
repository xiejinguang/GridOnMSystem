package org.eman.basic;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eman.asist.CandidateValueConstants;
import org.eman.asist.Values;
import org.eman.asist.facade.AsistCandidateValueFacade;
import org.eman.asist.model.CandidateValue;
import org.eman.basic.model.Station;
import org.eman.basic.util.JsfUtil;
import org.eman.basic.util.JsfUtil.PersistAction;

@Named("stationController")
@ViewScoped
public class StationController implements Serializable {

    @Inject
    protected org.eman.basic.StationFacade ejbFacade;
    private List<Station> items = null;
    private Station created;
    private List<Station> selectedItems;
    private Map<String, Object> searchCons;
    protected ResourceBundle bundle;

   
    
    
    @Inject @Values(key = CandidateValueConstants.StationStatusKey)
    private CandidateValue stationStatus;
    @Inject @Values(key = CandidateValueConstants.CompanyKey)
    private CandidateValue companys;
     @Inject @Values(key = CandidateValueConstants.StationTypekey)
    private CandidateValue types;

    public StationController() {
    }

    @PostConstruct
    public void init() {
        this.searchCons = new HashMap();
        this.bundle = ResourceBundle.getBundle("/org/eman/basic_i18n");
    }

    public Station getCreated() {
        return created;
    }

    public void setCreated(Station created) {
        this.created = created;
    }

    public List<Station> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<Station> selectedItems) {
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

    private StationFacade getFacade() {
        return ejbFacade;
    }

    public Station prepareCreate() {

        created = new Station();
        initializeKey();
        return created;
    }

    public void create() {
        persist(PersistAction.CREATE, bundle.getString("StationCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, bundle.getString("StationUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, bundle.getString("StationDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedItems = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Station> searchItems() {

        items = findItemsByConditions(construtSearchParams(this.searchCons));
        return items;
    }

    protected List<Station> findItemsByConditions(Map<String, Object> params) {
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

    public List<Station> allItems() {
        items = getFacade().findAll();

        return items;
    }

    public List<Station> getItems() {
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
                    for (Station selected : selectedItems) {
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

    public Station getStation(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Station> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Station> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public CandidateValue getStationStatus() {
        return stationStatus;
    }

    public CandidateValue getCompanys() {
        return companys;
    }

    public CandidateValue getTypes() {
        return types;
    }

    @FacesConverter(forClass = Station.class, value = "org.eman.Station")
    public static class StationControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            StationController controller = (StationController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "stationController");
            return controller.getStation(getKey(value));
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
            if (object instanceof Station) {
                Station o = (Station) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Station.class.getName()});
                return null;
            }
        }

    }

}
