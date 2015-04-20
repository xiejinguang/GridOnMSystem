package org.peasant.jpa;

import java.io.Serializable;

import java.lang.String;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class UUIDEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Size(min = 36, max = 36)
    @NotNull
    @Column(nullable = false, length = 36, name = "uuid", columnDefinition = "char(36) not null")
    private String uuid;

    public UUIDEntity() {
        this.uuid = java.util.UUID.randomUUID().toString();
    }

    public UUIDEntity(String uuid) {
        java.util.UUID.fromString(uuid);
        this.uuid = uuid;

    }

    public String getUuid() {

        return this.uuid;
    }

    public void setUuid(String uuid) {

        java.util.UUID.fromString(uuid);
        this.uuid = uuid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uuid != null ? uuid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the uuid methods are not set
        if (!super.equals(object)) {
            return false;
        }
        if (!(this.getClass().isInstance(object))) {
            return false;
        }
        UUIDEntity other = (UUIDEntity) object;
        return !((this.uuid == null && other.uuid != null) || (this.uuid != null && !this.uuid.equals(other.uuid)));
    }

    @Override
    public String toString() {
//        Method[] methods = this.getClass().getMethods();
//        StringBuilder sb = new StringBuilder();
//        sb.append(this.getClass().getName()).append('[');
//        for (Method m : methods) {
//            if (m.getParameterTypes().length == 0 && !m.getReturnType().equals(Void.class)) {
//                try {
//                    sb.append('[').append(m.getName()).append('=').append(m.invoke(this)).append(']');
//                } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException ex) {
//                    Logger.getLogger(UUIDEntity.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//        sb.append(']');
//        return sb.toString();
        return this.getClass().getName() + "[ id=" + uuid + " ]";
    }

}
