/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web;

import javax.ejb.EJB;
import org.peasant.web.fileupdownload.Constants;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletContext;
import org.peasant.util.Repository;
import org.peasant.util.repositoryimpl.DiskNDatabaseRepository;

/**
 *
 * @author 谢金光
 */
@Singleton
public class DefaultProducer {

    @Inject
    ServletContext ctx;
}
