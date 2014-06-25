/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.cc;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 * this Bean is used along with the peasant:selectDialog composite component.
 * 当返回已选择的数据时，会尝试比较选择的数据类型与接入返回的EL表达式期待的类型是否兼容，若兼容则调用EL进行赋值。
 * 若返回选择的数据类型与接入返回的EL表达式期待的类型有一方或两方是Array,Collection,List，会尝试进行封装转换，
 * 若EL表达式期待的类型是非Array,Collection,List，则只会赋予位于选择数据的第一个元素。
 *
 * @author 谢金光
 */
@Named
@ApplicationScoped
public class SelectDialogBean implements Serializable {

    public void onDataChosen(SelectEvent se) {
        if (se.getObject() == null) {
            return;
        }
        ELContext elc = FacesContext.getCurrentInstance().getELContext();
        ValueExpression ve = se.getComponent().getNamingContainer().getValueExpression("target");
        Class et = ve.getType(elc);
        Object selected = se.getObject();
        Object r = wrapOrUnWrapDataDepents(et, selected);
        ve.setValue(elc, r);

    }

    protected Object wrapOrUnWrapDataDepents(Class expectedType, Object data) {
        if (data == null) {
            return null;
        }
        Class nt = data.getClass();
        if (expectedType.isAssignableFrom(nt)) {
            return data;
        }
        if (expectedType.isArray()) {

            if (Iterable.class.isInstance(data)) {
                return toArray((Iterable) data);
            } else {
                return wrapInArray(data);
            }
        } else if (Collection.class.isAssignableFrom(expectedType) || List.class.isAssignableFrom(expectedType)) {
            if (Iterable.class.isInstance(data)) {
                return toList((Iterable) data);
            } else {
                return wrapInList(data);
            }
        } else {
            if (Iterable.class.isInstance(data)) {
                return toList((Iterable) data).get(0);
            } else {
                return data;
            }
        }

    }

    protected Object[] wrapInArray(Object data) {
        return new Object[]{data};

    }

    protected Object[] toArray(Iterable datas) {
        return toList(datas).toArray();
    }

    protected List wrapInList(Object data) {
        List l = new LinkedList();
        l.add(data);
        return l;

    }

    protected List toList(Iterable datas) {
        List l = new LinkedList();
        for (Object v : datas) {
            l.add(v);
        }
        return l;
    }
}
