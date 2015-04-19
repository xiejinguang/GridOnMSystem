/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.jpa;

import java.io.Serializable;
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
 *@T
 * @author 谢金光@peasant.org
 */
public class EntityLoggerListener implements Serializable {

    Logger logger = LoggerFactory.getLogger(LoggedInterceptor.class.getSimpleName());

    public EntityLoggerListener() {
    }

    @PrePersist
    public void prePersist(Object object) {
        logger.info("EntityLoggerListener :: Lifecycle Callback Method PrePersist Invoked Upon Entity ::{} ", object);
    }

    @PostPersist
    public void postPersist(Object object) {
        logger.info("EntityLoggerListener :: Lifecycle Callback Method PostPersist Invoked Upon Entity ::{} ", object);
    }

    @PreRemove
    public void preRemove(Object object) {
        logger.info("EntityLoggerListener :: Lifecycle Callback Method PreRemove Invoked Upon Entity ::{} ", object);
    }

    @PostRemove
    public void postRemove(Object object) {
        logger.info("EntityLoggerListener :: Lifecycle Callback Method PostRemove Invoked Upon Entity ::{} ", object);
    }

    @PreUpdate
    public void preUpdate(Object object) {
        logger.info("EntityLoggerListener :: Lifecycle Callback Method PreUpdate Invoked Upon Entity ::{} ", object);
    }

    @PostUpdate
    public void postUpdate(Object object) {
        logger.info("EntityLoggerListener :: Lifecycle Callback Method PostUpdate Invoked Upon Entity ::{} ", object);
    }

    @PostLoad
    public void postLoad(Object object) {
        logger.info("EntityLoggerListener :: Lifecycle Callback Method PostLoad Invoked Upon Entity ::{} ", object);
    }
}
