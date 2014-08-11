package org.eman.basic;

import org.eman.basic.model.Netnode;
import org.eman.basic.util.JsfUtil;
import org.eman.basic.util.JsfUtil.PersistAction;

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
import org.eman.asist.CandidateValueConstants;
import org.eman.asist.facade.AsistCandidateValueFacade;
import org.eman.asist.model.CandidateValue;

@Named("netnodeController")
@ViewScoped
public class NetnodeController implements Serializable {

    @EJB
    protected org.eman.basic.NetnodeFacade ejbFacade;
    @EJB
    AsistCandidateValueFacade candidateValueFacade;
    private List<Netnode> items = null;
    private Netnode created;
    private List<Netnode> selectedItems;
    private Map<String, Object> searchCons;
    protected ResourceBundle bundle;

    private CandidateValue statusCV;

    public NetnodeController() {
    }

    @PostConstruct
    public void init() {
        this.searchCons = new HashMap();
        this.bundle = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(), "basic_i18n");

        this.statusCV = candidateValueFacade.findBy(CandidateValueConstants.NetnodeStatusKey, CandidateValueConstants.NetnodeStatusValue, true).get(0);
    }

    public Netnode getCreated() {
        return created;
    }

    public void setCreated(Netnode created) {
        this.created = created;
    }

    public List<Netnode> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<Netnode> selectedItems) {
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

    private NetnodeFacade getFacade() {
        return ejbFacade;
    }

    public Netnode prepareCreate() {

        created = new Netnode();
        initializeKey();
        return created;
    }

    public void create() {
        persist(PersistAction.CREATE, bundle.getString("NetnodeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, bundle.getString("NetnodeUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, bundle.getString("NetnodeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedItems = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Netnode> searchItems() {

        items = findItemsByConditions(construtSearchParams(this.searchCons));
        return items;
    }

    protected List<Netnode> findItemsByConditions(Map<String, Object> params) {
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

    public List<Netnode> allItems() {
        items = getFacade().findAll();

        return items;
    }

    public List<Netnode> getItems() {
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
                    for (Netnode selected : selectedItems) {
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

    public Netnode getNetnode(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Netnode> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Netnode> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public CandidateValue getStatusCV() {
        return statusCV;
    }

    public void setStatusCV(CandidateValue statusCV) {
        this.statusCV = statusCV;
    }

    @FacesConverter(forClass = Netnode.class)
    public static class NetnodeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NetnodeController controller = (NetnodeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "netnodeController");
            return controller.getNetnode(getKey(value));
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
            if (object instanceof Netnode) {
                Netnode o = (Netnode) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Netnode.class.getName()});
                return null;
            }
        }

    }

}
