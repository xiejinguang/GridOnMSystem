/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.cms;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import org.peasant.jpa.DatedEntity;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(name = "CMS_resources")
public class CMSResource extends DatedEntity implements Serializable {

    private String url;
    private String filePath;
    private String headline;    
    private String content;
    
    @Enumerated(EnumType.STRING)
    private Type type;
    private Collection<CMSResource> resources;
    

    public static enum Type {

        html, jsp, jsf
    }
}
