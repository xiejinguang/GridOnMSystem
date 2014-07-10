package org.eman.gmsys.model;

import java.io.Serializable;

import java.lang.String;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class UUIDIdentity implements Serializable {
    
    @Id
    @Basic(optional = false)
    @Size(min = 1, max = 36)
    @NotNull
    @Column(nullable = false, length = 36)
    
    protected String id;

    public UUIDIdentity() {
        id = java.util.UUID.randomUUID().toString();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
