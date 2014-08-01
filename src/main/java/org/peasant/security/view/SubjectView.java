/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.security.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ValidationException;
import org.apache.shiro.SecurityUtils;
import org.peasant.security.facade.UserDetailFacade;
import org.peasant.security.facade.UserFacade;
import org.peasant.security.model.User;
import org.peasant.security.model.UserDetail;

/**
 *
 * @author 谢金光
 */
@Named
@SessionScoped
public class SubjectView implements Serializable {

    @Inject
    UserFacade userFacade;

    private User curUser;
    private UserDetail curUserDetail;
    private String oldPasswd;
    private String newPasswd;

    @PostConstruct
    public void init() {
        curUser = userFacade.find(SecurityUtils.getSubject().getPrincipal());

        if (curUser != null) {
            curUserDetail = curUser.getUserDetail();
        }
    }

    public void modifyPasswd() {
        if (oldPasswd.equals(curUser.getPassword())) {
            curUser.setPassword(newPasswd);
            userFacade.edit(curUser);
        } else {

        }
    }

    public void validatePasswd(FacesContext context, UIComponent component, Object value) {
        if(! value.equals(curUser.getPassword())){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "密码错误","输入的密码不正确"));
        }
    }

    public void prepareModifyPasswd() {
        oldPasswd = null;
        newPasswd = null;
    }

    public void modifyDetails() {
        userFacade.edit(curUser);
    }

    public User getCurUser() {
        return curUser;
    }

    public UserDetail getCurUserDetail() {
        if (curUserDetail == null) {
            curUserDetail = new UserDetail(curUser.getUsername());
            curUser.setUserDetail(curUserDetail);
        }
        return curUserDetail;
    }

    public String getOldPasswd() {
        return oldPasswd;
    }

    public void setOldPasswd(String oldPasswd) {
        this.oldPasswd = oldPasswd;
    }

    public String getNewPasswd() {
        return newPasswd;
    }

    public void setNewPasswd(String newPasswd) {
        this.newPasswd = newPasswd;
    }
}
