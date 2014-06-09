/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.security.shiro.cdi.web;

import java.util.Collection;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.env.EnvironmentLoader;

/**
 * Web application lifecycle listener.
 *
 * @author 谢金光
 */
public class EnvironmentLoaderListener extends EnvironmentLoader implements ServletContextListener {

    @Inject
    @Any
    Instance<Realm> realms;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DefaultSecurityManager sm = (DefaultSecurityManager) initEnvironment(sce.getServletContext()).getWebSecurityManager();
        Collection<Realm> rs = new java.util.LinkedList<>();
        for(Realm realm:realms){
            rs.add(realm);
        }
        sm.setRealms(rs);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        destroyEnvironment(sce.getServletContext());
    }
}
