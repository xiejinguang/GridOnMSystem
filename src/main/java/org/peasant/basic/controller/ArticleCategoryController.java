package org.peasant.basic.controller;

import org.peasant.basic.model.ArticleCategory;
import org.peasant.basic.controller.util.JsfUtil;
import org.peasant.basic.controller.util.JsfUtil.PersistAction;
import org.peasant.basic.facade.ArticleCategoryFacade;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
import javax.faces.model.SelectItem;
import javax.inject.Singleton;
import org.peasant.jpa.Logged;
import org.peasant.util.web.JsfModelBuilder;

@Named("articleCategoryController")
@ViewScoped
@Logged
public class ArticleCategoryController implements Serializable {
    
    @EJB
    protected org.peasant.basic.facade.ArticleCategoryFacade ejbFacade;
    private List<ArticleCategory> items = null;
    private ArticleCategory created;
    private List<ArticleCategory> selectedItems;
    private Map<String, Object> searchCons;
    protected ResourceBundle bundle;
    
    List<SelectItem> hierarchicalCategories;
    
    public ArticleCategoryController() {
    }
    
    @PostConstruct
    public void init() {
        this.searchCons = new HashMap();
        this.bundle = ResourceBundle.getBundle("/org/peasant/i18n_basic");
    }
    
    public ArticleCategory getCreated() {
        return created;
    }
    
    public void setCreated(ArticleCategory created) {
        this.created = created;
    }
    
    public List<ArticleCategory> getSelectedItems() {
        return selectedItems;
    }
    
    public void setSelectedItems(List<ArticleCategory> selectedItems) {
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
    
    private ArticleCategoryFacade getFacade() {
        return ejbFacade;
    }
    
    public ArticleCategory prepareCreate() {
        
        created = new ArticleCategory();
        initializeKey();
        return created;
    }
    
    public void create() {
        persist(PersistAction.CREATE, bundle.getString("ArticleCategoryCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void update() {
        persist(PersistAction.UPDATE, bundle.getString("ArticleCategoryUpdated"));
    }
    
    public void destroy() {
        persist(PersistAction.DELETE, bundle.getString("ArticleCategoryDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedItems = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public List<ArticleCategory> searchItems() {
        
        items = findItemsByConditions(construtSearchParams(this.searchCons));
        return items;
    }
    
    protected List<ArticleCategory> findItemsByConditions(Map<String, Object> params) {
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
    
    public List<ArticleCategory> allItems() {
        items = getFacade().findAll();
        
        return items;
    }
    
    public List<ArticleCategory> getItems() {
        if (null == items) {
            //TODO,根据上次查询条件记录获取记录
        }
        return items;
    }
    
    protected void persist(PersistAction persistAction, String successMessage) {
        
        try {
            
            switch (persistAction) {
                case CREATE:
//                    if (created.getSuperior() == null) {
                        getFacade().edit(created);
//                    } else {
//                        created.getSuperior().getArticleCategoryCollection().add(created);
//                        getFacade().edit(created.getSuperior());
//                    }
                    break;
                
                default: {
                    for (ArticleCategory selected : selectedItems) {
                        if (selected != null) {
                            setEmbeddableKeys();
                            switch (persistAction) {
                                case DELETE:
//                                    if (selected.getSuperior() == null) {
                                        getFacade().remove(selected);
//                                    } else {
//                                        selected.getSuperior().getArticleCategoryCollection().remove(selected);
//                                        getFacade().edit(selected.getSuperior());
//                                    }
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
    
    public ArticleCategory getArticleCategory(java.lang.String id) {
        return getFacade().find(id);
    }
    
    public List<ArticleCategory> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }
    
    public List<ArticleCategory> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    public List<SelectItem> getHierarchicalCategories() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        
        Map params = new HashMap();
        params.put("superior", null);
        List<ArticleCategory> rootCategories = getFacade().findByConditions(params);
        hierarchicalCategories = new ArrayList(rootCategories.size());
        for (ArticleCategory o : rootCategories) {
            
            hierarchicalCategories.add(JsfModelBuilder.buildHierarchicalSelectItem(ArticleCategory.class, o, "articleCategoryCollection", "name"));
        }
        
        return this.hierarchicalCategories;
    }
    
    @FacesConverter(forClass = ArticleCategory.class, value = "ArticleCategory")
    @org.peasant.jpa.EntityConverter(forClass = ArticleCategory.class, value = "org.peasant.basic.model.ArticleCategory")
    @Singleton
    public static class ArticleCategoryFacesConverter implements Converter, org.peasant.util.Converter<ArticleCategory> {
        
        @EJB
        ArticleCategoryFacade articleCategoryFacade;
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ArticleCategoryController controller = (ArticleCategoryController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "articleCategoryController");
            return controller.getArticleCategory(getKey(value));
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
            if (object instanceof ArticleCategory) {
                ArticleCategory o = (ArticleCategory) object;
                return getStringKey(o.getUuid());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ArticleCategory.class.getName()});
                return null;
            }
        }

        /**
         *
         * @param key the value of key
         * @return the T
         */
        public ArticleCategory getAsObject(String key) {
            
            ArticleCategory result = articleCategoryFacade.find(key);
            
            if (result == null) {
                Map<String, Object> params = new HashMap<>();
                params.put("name", key);
                List<ArticleCategory> rs = articleCategoryFacade.findByNamedQuery("ArticleCategory.findByName", params);
                if (rs != null && !rs.isEmpty()) {
                    result = rs.get(0);
                }
                
            }
            return result;
        }
        
        @Override
        public String getAsString(Object value) {
            if (value == null) {
                return null;
            }
            if (value instanceof ArticleCategory) {
                ArticleCategory o = (ArticleCategory) value;
                return getStringKey(o.getUuid());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{value, value.getClass().getName(), ArticleCategory.class.getName()});
                return null;
            }
        }

        /**
         *
         * @param value the value of value
         * @return the T
         */
        @Override
        public ArticleCategory convert(Object value) {
            if (value == null) {
                return null;
            }
            if (value instanceof String) {
                return getAsObject((String) value);
            }
            if (value instanceof ArticleCategory) {
                return (ArticleCategory) value;
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{value, value.getClass().getName(), ArticleCategory.class.getName()});
                return null;
            }
            
        }
        
    }
    
}
