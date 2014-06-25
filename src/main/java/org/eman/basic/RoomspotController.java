package org.eman.basic;

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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eman.asist.AreaService;
import org.eman.asist.CandidateValueConstants;
import org.eman.asist.facade.AsistCandidateValueFacade;
import org.eman.asist.model.CandidateValue;
import org.eman.basic.model.Roomspot;
import org.eman.basic.util.JsfUtil;
import org.eman.basic.util.JsfUtil.PersistAction;
import org.primefaces.event.SelectEvent;

@Named("roomspotController")
@ViewScoped
public class RoomspotController implements Serializable {

    @EJB
    protected org.eman.basic.RoomspotFacade ejbFacade;
    private List<Roomspot> items = null;
    private Roomspot created;
    private List<Roomspot> selectedItems;
    private Map<String, Object> searchCons;
    protected ResourceBundle bundle;

    @EJB
    AsistCandidateValueFacade candidateValueFacade;

    private CandidateValue statusCVs;
    private CandidateValue companyCVs;
    private CandidateValue gridCVs;
    private CandidateValue areaCVs;
    
    @Inject
    private AreaService areaforSeachCons;
    @Inject
    private AreaService areaforEdit;
    @Inject
    private AreaService areaforCreate;

    public RoomspotController() {
    }

    @PostConstruct
    public void init() {
        this.searchCons = new HashMap();
        this.bundle = ResourceBundle.getBundle("/org/eman/basic_i18n");
        this.statusCVs = candidateValueFacade.findBy(CandidateValueConstants.RoomspotStatusKey, CandidateValueConstants.RoomspotStatusValue, true).get(0);
        this.companyCVs = candidateValueFacade.findBy(CandidateValueConstants.CompanyKey, CandidateValueConstants.CompanyValue, true).get(0);
        this.gridCVs = candidateValueFacade.findBy(CandidateValueConstants.GridKey, CandidateValueConstants.GridValue, true).get(0);
        this.areaCVs = candidateValueFacade.findBy(CandidateValueConstants.ProvinceKey,CandidateValueConstants.ProvinceValue,true).get(0);
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
        created.setId(org.eman.util.Utils.generateUniqueKey());
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

        items = findItemsByConditions(construtSearchParams(this.searchCons));
        return items;
    }

    protected List<Roomspot> findItemsByConditions(Map<String, Object> params) {
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

    protected void persist(PersistAction persistAction, String successMessage) {

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

    /**
     * Get the value of companyCVs
     *
     * @return the value of companyCVs
     */
    public CandidateValue getCompanyCVs() {

        return companyCVs;
    }

    /**
     * Set the value of companyCVs
     *
     * @param companyCVs new value of companyCVs
     */
    public void setCompanyCVs(CandidateValue companyCVs) {
        this.companyCVs = companyCVs;
    }

    /**
     * Get the value of statusCVs
     *
     * @return the value of statusCVs
     */
    public CandidateValue getStatusCVs() {

        return statusCVs;
    }

    /**
     * Set the value of statusCVs
     *
     * @param statusCVs new value of statusCVs
     */
    public void setStatusCVs(CandidateValue statusCVs) {
        this.statusCVs = statusCVs;
    }


   
    
    public void handleDialogReturn(SelectEvent e){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.toString()));
    }

    public CandidateValue getGridCVs() {
        return gridCVs;
    }

    public void setGridCVs(CandidateValue gridCVs) {
        this.gridCVs = gridCVs;
    }

    public AreaService getAreaforSeachCons() {
        return areaforSeachCons;
    }

    public void setAreaforSeachCons(AreaService areaforSeachCons) {
        this.areaforSeachCons = areaforSeachCons;
    }

    public AreaService getAreaforEdit() {
        return areaforEdit;
    }

    public void setAreaforEdit(AreaService areaforEdit) {
        this.areaforEdit = areaforEdit;
    }

    public AreaService getAreaforCreate() {
        return areaforCreate;
    }

    public void setAreaforCreate(AreaService areaforCreate) {
        this.areaforCreate = areaforCreate;
    }

    public CandidateValue getAreaCVs() {
        return areaCVs;
    }

    public void setAreaCVs(CandidateValue areaCVs) {
        this.areaCVs = areaCVs;
    }

    @FacesConverter(forClass = Roomspot.class, value = "org.eman.Roomspot")
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
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Roomspot.class.getName()});
                return null;
            }
        }

    }

}
