/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.security;

import java.security.Principal;
import javax.faces.context.FacesContext;

/**
 *
 * @author 谢金光
 */
public class SecurityUtils {

    public static boolean ifGranted(String role) {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role);
    }

    public static boolean ifAllGranted(String value) {
        String[] roles = value.split(",");
        boolean isAuthorized = false;

        for (String role : roles) {
            if (ifGranted(role.trim())) {
                isAuthorized = true;
            } else {
                isAuthorized = false;
                break;
            }
        }

        return isAuthorized;
    }

    public static boolean ifAnyGranted(String value) {
        String[] roles = value.split(",");
        boolean isAuthorized = false;

        for (String role : roles) {
            if (ifGranted(role.trim())) {
                isAuthorized = true;
                break;
            }
        }

        return isAuthorized;
    }

    public static boolean ifNoneGranted(String value) {
        String[] roles = value.split(",");
        boolean isAuthorized = false;

        for (String role : roles) {
            if (ifGranted(role.trim())) {
                isAuthorized = false;
                break;
            } else {
                isAuthorized = true;
            }
        }

        return isAuthorized;
    }

    public static String remoteUser() {
        return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
    }

    public static Principal userPrincipal() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
    }

}
