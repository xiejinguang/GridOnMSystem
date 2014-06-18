/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman.assit.model;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author 谢金光
 */
public interface CandidateValue extends Serializable{

    boolean equals(Object object);

    String getAccordingKey();

    Collection<CandidateValue> getChildren();

    String getId();

    CandidateValue getParent();

    String getValue();

    void setAccordingKey(String accordingKey);

    void setChildren(Collection<CandidateValue> candidateValueCollection);

    void setId(String id);

    void setParent(CandidateValue parentID);

    void setValue(String value);
    
}
