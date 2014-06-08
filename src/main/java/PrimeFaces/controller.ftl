<#if comment>

  TEMPLATE DESCRIPTION:

  This is Java template for 'JSF Pages From Entity Beans' controller class. Templating
  is performed using FreeMaker (http://freemarker.org/) - see its documentation
  for full syntax. Variables available for templating are:

    controllerClassName - controller class name (type: String)
    controllerPackageName - controller package name (type: String)
    entityClassName - entity class name without package (type: String)
    importEntityFullClassName - whether to import entityFullClassName or not
    entityFullClassName - fully qualified entity class name (type: String)
    ejbClassName - EJB class name (type: String)
    importEjbFullClassName - whether to import ejbFullClassName or not
    ejbFullClassName - fully qualified EJB class name (type: String)
    managedBeanName - name of managed bean (type: String)
    keyEmbedded - is entity primary key is an embeddable class (type: Boolean)
    keyType - fully qualified class name of entity primary key
    keyBody - body of Controller.Converter.getKey() method
    keyStringBody - body of Controller.Converter.getStringKey() method
    keyGetter - entity getter method returning primaty key instance
    keySetter - entity setter method to set primary key instance
    embeddedIdFields - contains information about embedded primary IDs
    cdiEnabled - project contains beans.xml, so Named beans can be used
    bundle - name of the variable defined in the JSF config file for the resource bundle (type: String)

  This template is accessible via top level menu Tools->Templates and can
  be found in category JavaServer Faces->JSF from Entity.

</#if>
package ${controllerPackageName};

<#if importEntityFullClassName?? && importEntityFullClassName == true>
import ${entityFullClassName};
</#if>
import ${controllerPackageName}.util.JsfUtil;
import ${controllerPackageName}.util.JsfUtil.PersistAction;
<#if importEjbFullClassName?? && importEjbFullClassName == true>
    <#if ejbClassName??>
import ${ejbFullClassName};
    <#elseif jpaControllerClassName??>
import ${jpaControllerFullClassName};
    </#if>
</#if>

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
<#if isInjected?? && isInjected==true>
import javax.annotation.Resource;
</#if>
<#if ejbClassName??>
import javax.ejb.EJB;
</#if>
import javax.ejb.EJBException;
<#if managedBeanName??>
<#if cdiEnabled?? && cdiEnabled>
import javax.inject.Named;
import javax.faces.view.ViewScoped;
<#else>
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
</#if>
</#if>
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
<#if jpaControllerClassName??>
  <#if isInjected?? && isInjected==true>
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
  <#else>
import javax.persistence.Persistence;
  </#if>
</#if>


<#if managedBeanName??>
<#if cdiEnabled?? && cdiEnabled>
@Named("${managedBeanName}")
<#else>
@ManagedBean(name="${managedBeanName}")
</#if>
@ViewScoped
</#if>
public class ${controllerClassName} implements Serializable {

<#if isInjected?? && isInjected==true>
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit<#if persistenceUnitName??>(unitName = "${persistenceUnitName}")</#if>
    private EntityManagerFactory emf = null;
</#if>

<#if ejbClassName??>
    @EJB private ${ejbFullClassName} ejbFacade;
<#elseif jpaControllerClassName??>
    private ${jpaControllerClassName} jpaController = null;
</#if>
    private List<${entityClassName}> items = null;
    private ${entityClassName} created;
    private List<${entityClassName}> selectedItems;
    private Map<String,Object> searchCons;
    private ResourceBundle bundle;

    public ${controllerClassName}() {
    }
    
    @PostConstruct
    public void init(){
        this.searchCons = new HashMap();
        this.bundle = ResourceBundle.getBundle("${bundle}");
    }


    public ${entityClassName} getCreated() {
        return created;
    }

    public void setCreated(${entityClassName} created) {
        this.created = created;
    }

    public  List<${entityClassName}> getSelectedItems() {
        return selectedItems;
    }
    
    public void setSelectedItems(List<${entityClassName}> selectedItems){
        this.selectedItems =selectedItems;
    }

    public Map<String, Object> getSearchCons() {
        return searchCons;
    }

    public void setSearchCons(Map<String, Object> searchCons) {
        this.searchCons = searchCons;
    }


    protected void setEmbeddableKeys() {
<#list embeddedIdFields as fields>
            selected.${keyGetter}().${fields.getEmbeddedSetter()}(selected.${fields.getCodeToPopulate()});
</#list>
    }

    protected void initializeEmbeddableKey() {
<#if keyEmbedded>
        selected.${keySetter}(new ${keyType}());
</#if>
    }

<#if ejbClassName??>
    private ${ejbClassName} getFacade() {
        return ejbFacade;
    }
<#elseif jpaControllerClassName??>
    private ${jpaControllerClassName} getJpaController() {
        if (jpaController == null) {
<#if isInjected?? && isInjected==true>
            jpaController = new ${jpaControllerClassName}(utx, emf);
<#else>
            jpaController = new ${jpaControllerClassName}(Persistence.createEntityManagerFactory(<#if persistenceUnitName??>"${persistenceUnitName}"</#if>));
</#if>
        }
        return jpaController;
    }
</#if>

    public ${entityClassName} prepareCreate() {
        created = new ${entityClassName}();
        initializeEmbeddableKey();
        return created;
    }
    public List<${entityClassName}> prepareSearch(){
        this.items=null;
        return this.items;
        
}

    public void create() {
        persist(PersistAction.CREATE, bundle.getString("${entityClassName}Created"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, bundle.getString("${entityClassName}Updated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, bundle.getString("${entityClassName}Deleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedItems = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<${entityClassName}> searchItems() {
        items = getFacade().findByConditions(searchCons);
        return items;
    }

    public List<${entityClassName}> allItems() {
<#if ejbClassName??>
            items = getFacade().findAll();

<#elseif jpaControllerClassName??>
            items = getJpaController().find${entityClassName}Entities();
</#if>
        return items;
    }






    public List<${entityClassName}> getItems() {
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {

        try {

<#if ejbClassName??>
        switch (persistAction) {
            case CREATE:getFacade().edit(created);break;
            
            default:{
                for( ${entityClassName} selected : selectedItems){
                    if (selected != null) {
                        setEmbeddableKeys();
                        switch (persistAction) {
                            case DELETE: getFacade().remove(selected);break;               
                            case UPDATE: getFacade().edit(selected);break;
                        }
                    }
                }
            }

        }

<#elseif jpaControllerClassName??>
        switch (persistAction) {
            case CREATE:getJpaController().create(created);break;
            default:{
                for( ${entityClassName} selected : selectedItems){
                    if (selected != null) {
                        setEmbeddableKeys();
                        switch (persistAction) {
                            case DELETE: getJpaController().destroy(selected.getId());break;               
                            case UPDATE: getJpaController().edit(selected);break;
                        }
                    }
                }
            }

        }
</#if>
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

<#if ejbClassName?? && cdiEnabled?? && cdiEnabled>
    public ${entityClassName} get${entityClassName}(${keyType} id) {
        return getFacade().find(id);
    }
</#if>

    public List<${entityClassName}> getItemsAvailableSelectMany() {
<#if ejbClassName??>
        return getFacade().findAll();
<#elseif jpaControllerClassName??>
        return getJpaController().find${entityClassName}Entities();
</#if>
    }

    public List<${entityClassName}> getItemsAvailableSelectOne() {
<#if ejbClassName??>
        return getFacade().findAll();
<#elseif jpaControllerClassName??>
        return getJpaController().find${entityClassName}Entities();
</#if>
    }

    @FacesConverter(forClass=${entityClassName}.class)
    public static class ${controllerClassName}Converter implements Converter {
<#if keyEmbedded>

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";
</#if>

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ${controllerClassName} controller = (${controllerClassName})facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "${managedBeanName}");
<#if ejbClassName??>
<#if cdiEnabled?? && cdiEnabled>
            return controller.get${entityClassName}(getKey(value));
<#else>
            return controller.getFacade().find(getKey(value));
</#if>
<#elseif jpaControllerClassName??>
            return controller.getJpaController().find${entityClassName}(getKey(value));
</#if>
        }

        ${keyType} getKey(String value) {
            ${keyType} key;
${keyBody}
            return key;
        }

        String getStringKey(${keyType} value) {
            StringBuilder sb = new StringBuilder();
${keyStringBody}
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ${entityClassName}) {
                ${entityClassName} o = (${entityClassName}) object;
                return getStringKey(o.${keyGetter}());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ${entityClassName}.class.getName()});
                return null;
            }
        }

    }

}
