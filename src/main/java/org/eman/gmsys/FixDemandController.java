package org.eman.gmsys;

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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eman.SpecialCodeGenerator;
import org.eman.asist.CandidateValueConstants;
import org.eman.asist.HierarchicalValuesPopulater;
import org.eman.asist.facade.AsistCandidateValueFacade;
import org.eman.asist.model.CandidateValue;
import org.eman.basic.model.Roomspot;
import org.eman.gmsys.model.FixDemand;
import org.eman.gmsys.util.JsfUtil;
import org.eman.gmsys.util.JsfUtil.PersistAction;

@Named("fixDemandController")
@ViewScoped
public class FixDemandController implements Serializable {

    @EJB
    protected org.eman.gmsys.FixDemandFacade ejbFacade;

    @Inject
    protected SpecialCodeGenerator scg;

    private List<FixDemand> items = null;
    private FixDemand created;
    private List<FixDemand> selectedItems;
    private Map<String, Object> searchCons;
    protected ResourceBundle bundle;

    @EJB
    AsistCandidateValueFacade candidateValueFacade;

    private CandidateValue statusCV;
    private CandidateValue sourceCV;
    private HierarchicalValuesPopulater problemKind;
    private HierarchicalValuesPopulater problemSubKind;
    private CandidateValue fixkindCV;

    public FixDemandController() {

    }

    @PostConstruct
    public void init() {
        this.searchCons = new HashMap();
        this.bundle = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(), "gmsys_i18n");

        statusCV = candidateValueFacade.findBy(CandidateValueConstants.GMSYS_FixDemandStatusKey, CandidateValueConstants.GMSYS_FixDemandStatusValue, true).get(0);
        sourceCV = candidateValueFacade.findBy(CandidateValueConstants.GMSYS_FixDemandSourceKey, CandidateValueConstants.GMSYS_FixDemandSourceValue, true).get(0);
        fixkindCV = candidateValueFacade.findBy(CandidateValueConstants.GMSYS_FixDemandFixKindKey, CandidateValueConstants.GMSYS_FixDemandFixKindValue, true).get(0);
        CandidateValue problemKindCV = candidateValueFacade.findBy(CandidateValueConstants.GMSYS_FixDemandProblemKindKey, CandidateValueConstants.GMSYS_FixDemandProblemKindValue, true).get(0);
        problemKind = new HierarchicalValuesPopulater(null, null, problemKindCV);
        problemSubKind = new HierarchicalValuesPopulater(problemKind, null, null);

    }

    public void setFixDemandCodeFor(FixDemand fd) {
        fd.setDemandCode(genFixDemandCode(fd.getStationId().getRoomspot()));
    }

    public String genFixDemandCode(Roomspot rs) {
        return scg.genFixDemandCode(rs.getProvince(), rs.getCity());
    }

    public FixDemand getCreated() {
        return created;
    }

    public void setCreated(FixDemand created) {
        this.created = created;
    }

    public List<FixDemand> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<FixDemand> selectedItems) {
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

    private FixDemandFacade getFacade() {
        return ejbFacade;
    }

    public FixDemand prepareCreate() {

        created = new FixDemand();
        initializeKey();
        return created;
    }

    public void create() {
        persist(PersistAction.CREATE, bundle.getString("FixDemandCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, bundle.getString("FixDemandUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, bundle.getString("FixDemandDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedItems = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<FixDemand> searchItems() {

        items = findItemsByConditions(construtSearchParams(this.searchCons));
        return items;
    }

    protected List<FixDemand> findItemsByConditions(Map<String, Object> params) {
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

    public List<FixDemand> allItems() {
        items = getFacade().findAll();

        return items;
    }

    public List<FixDemand> getItems() {
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
                    for (FixDemand selected : selectedItems) {
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

    public FixDemand getFixDemand(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<FixDemand> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<FixDemand> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public HierarchicalValuesPopulater getProblemKind() {
        return problemKind;
    }

    public HierarchicalValuesPopulater getProblemSubKind() {
        return problemSubKind;
    }

    public CandidateValue getStatusCV() {
        return statusCV;
    }

    public CandidateValue getSourceCV() {
        return sourceCV;
    }

    public CandidateValue getFixkindCV() {
        return fixkindCV;
    }

    @FacesConverter(forClass = FixDemand.class)
    public static class FixDemandControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FixDemandController controller = (FixDemandController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "fixDemandController");
            return controller.getFixDemand(getKey(value));
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
            if (object instanceof FixDemand) {
                FixDemand o = (FixDemand) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), FixDemand.class.getName()});
                return null;
            }
        }

    }

}
