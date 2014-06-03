/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.peasant.util.web;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import org.peasant.util.Repository;
import org.peasant.util.repositoryimpl.BasicFileRepository;

/**
 *
 * @author 谢金光
 */
public class DefaultProducer {

    @Produces
    @ApplicationScoped
    @Default
    public Repository getDefaultRepository() {
        String homePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(Constants.REPOSITORY_HOME_PARAM);
        if(homePath ==null || homePath.isEmpty())
            return new BasicFileRepository();
        return new BasicFileRepository(homePath);
    }
}
