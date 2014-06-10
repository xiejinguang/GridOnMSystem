/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.security.shiro.realm.jpa;

import java.util.HashMap;
import javax.ejb.EJB;
import javax.inject.Singleton;
import org.apache.shiro.authc.Account;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.peasant.security.facade.UserFacade;
import org.peasant.security.model.User;

/**
 *
 * @author 谢金光
 */
@Singleton
public class JpaRealm extends org.apache.shiro.realm.AuthorizingRealm {

    @EJB
    UserFacade userFacade;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //SaltedAuthenticationInfo autentInfo;//Support in the future;
        SimpleAuthenticationInfo authentInfo = null;
        String username = (String) token.getPrincipal();
        User user = userFacade.find(username);
        if (user != null) {
            authentInfo = new SimpleAuthenticationInfo();
            authentInfo.setPrincipals(new SimplePrincipalCollection(user.getUsername(), getName()));
            authentInfo.setCredentials(user.getPassword());
        }
        return authentInfo;

    }

    private Account doGetAccount(String username) {
        return null;
    }

    public JpaRealm() {
        super();
    }

    public JpaRealm(CacheManager cacheManager) {
        super(cacheManager);
    }

    public JpaRealm(CredentialsMatcher matcher) {
        super(matcher);
    }

    public JpaRealm(CacheManager cacheManager, CredentialsMatcher matcher) {
        super(cacheManager, matcher);
    }
}
