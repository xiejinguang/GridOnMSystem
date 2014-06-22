/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eman.assit.facade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.eman.Asist;
import org.eman.Module;
import org.eman.assit.model.AsistCandidateValue;
import org.eman.assit.model.CandidateValue;
import org.slf4j.Logger;

/**
 *
 * @author 谢金光
 */
@Stateless
public class AsistCandidateValueFacade extends AbstractFacade<AsistCandidateValue> {

    @Inject
    @Module(name = "asist")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AsistCandidateValueFacade() {
        super(AsistCandidateValue.class);
    }

    public void addChildren(CandidateValue parent, CandidateValue... children) {
        for (CandidateValue child : children) {
            parent.getChildren().add(child);
        }
        this.edit((AsistCandidateValue) parent);

    }

    @Override
    @Transactional
    public void remove(AsistCandidateValue entity) {
        super.remove(entity);
        if (entity.getParentID() != null) {
            entity.getParentID().getAsistCandidateValueCollection().remove(entity);
            this.edit(entity.getParentID());
        }

    }

    public List<AsistCandidateValue> findBy(String accordingKey) {
        Map<String, Object> params = new HashMap<>();
        params.put("accordingKey", accordingKey);
        return findByConditions(params);

    }

    public List<AsistCandidateValue> findBy(String accordingKey, String value) {
        Map<String, Object> params = new HashMap<>();
        params.put("accordingKey", accordingKey);
        params.put("value", value);
        return findByConditions(params);
    }

    public List<AsistCandidateValue> findBy(String accordingKey, String value, boolean notExistCreate) {
        Map<String, Object> params = new HashMap<>();
        params.put("accordingKey", accordingKey);
        params.put("value", value);
        List<AsistCandidateValue> result = findByConditions(params);
        if ((result == null || result.isEmpty()) && notExistCreate) {
            AsistCandidateValue v = new AsistCandidateValue(java.util.UUID.randomUUID().toString(), accordingKey, value);
            edit(v);
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(v);
        }
        return result;
    }

}
