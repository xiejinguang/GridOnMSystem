/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.jpa;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default Entity Listener.用于记录PersistenceContext中的Entity操作事件。
 *
 * @author Administrator
 */
public class PersistenceContextListener {

    public PersistenceContextListener() {
    }

    Logger logger = LoggerFactory.getLogger(LoggedInterceptor.class.getSimpleName());

    @PrePersist
    public void prePersist(Object object) {
        logger.info("PersistenceContextListener :: Lifecycle Callback Method PrePersist Invoked Upon Entity ::{} ", object);
    }

    @PostPersist
    public void postPersist(Object object) {
        logger.info("PersistenceContextListener :: Lifecycle Callback Method PostPersist Invoked Upon Entity ::{} ", object);
    }

    @PreRemove
    public void preRemove(Object object) {
        logger.info("PersistenceContextListener :: Lifecycle Callback Method PreRemove Invoked Upon Entity ::{} ", object);
    }

    @PostRemove
    public void postRemove(Object object) {
        logger.info("PersistenceContextListener :: Lifecycle Callback Method PostRemove Invoked Upon Entity ::{} ", object);
    }

    @PreUpdate
    public void preUpdate(Object object) {
        logger.info("PersistenceContextListener :: Lifecycle Callback Method PreUpdate Invoked Upon Entity ::{} ", object);
    }

    @PostUpdate
    public void postUpdate(Object object) {
        logger.info("PersistenceContextListener :: Lifecycle Callback Method PostUpdate Invoked Upon Entity ::{} ", object);
    }

    @PostLoad
    public void postLoad(Object object) {
        logger.info("PersistenceContextListener :: Lifecycle Callback Method PostLoad Invoked Upon Entity ::{} ", object);
    }
   
}
