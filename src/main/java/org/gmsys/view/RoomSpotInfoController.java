package org.gmsys.view;

import org.gmsys.model.entity.RoomSpotInfo;
import org.gmsys.view.util.JsfUtil;
import org.gmsys.view.util.JsfUtil.PersistAction;
import org.gmsys.ejb.RoomSpotInfoFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("roomSpotInfoController")
@SessionScoped
public class RoomSpotInfoController implements Serializable {

    @EJB
    private org.gmsys.ejb.RoomSpotInfoFacade ejbFacade;
    private List<RoomSpotInfo> items = null;
    private RoomSpotInfo selected;

    public RoomSpotInfoController() {
    }

    public RoomSpotInfo getSelected() {
        return selected;
    }

    public void setSelected(RoomSpotInfo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RoomSpotInfoFacade getFacade() {
        return ejbFacade;
    }

    public RoomSpotInfo prepareCreate() {
        selected = new RoomSpotInfo();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RoomSpotInfoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RoomSpotInfoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RoomSpotInfoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RoomSpotInfo> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public RoomSpotInfo getRoomSpotInfo(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<RoomSpotInfo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RoomSpotInfo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RoomSpotInfo.class)
    public static class RoomSpotInfoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RoomSpotInfoController controller = (RoomSpotInfoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "roomSpotInfoController");
            return controller.getRoomSpotInfo(getKey(value));
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
            if (object instanceof RoomSpotInfo) {
                RoomSpotInfo o = (RoomSpotInfo) object;
                return getStringKey(o.getRoomId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RoomSpotInfo.class.getName()});
                return null;
            }
        }

    }

}
