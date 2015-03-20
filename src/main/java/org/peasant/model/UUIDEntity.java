package org.peasant.model;

import java.io.Serializable;

import java.lang.String;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class UUIDEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Size(min = 36, max = 36)
    @NotNull
    @Column(nullable = false, length = 36)

    protected String id;

    public UUIDEntity() {
        id = java.util.UUID.randomUUID().toString();        
    }

    public UUIDEntity(String uuid) {

        if (null == uuid || uuid.trim().equals("")) {
            id = java.util.UUID.randomUUID().toString();
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        if (null == id || id.trim().equals("")) {
            id = java.util.UUID.randomUUID().toString();
        }
        this.id = id;
    }

}
