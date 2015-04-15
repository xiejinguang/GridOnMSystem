/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.jpa;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.Cacheable;
import javax.persistence.EntityListeners;
import javax.persistence.EntityManager;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import org.peasant.util.ReflectUtil;

/**
 * 此类仅能在CDI环境下工作,待改进。在JPA开启Shared Cache（二级高速缓存）的情况下，创建并持久化一个具有拥有外键 关系( *
 * {@link ManyToOne},{@link ManyToMany}, {@link OneToOne})的实体
 * （外建关系中的子表（从体）记录），当你试图在存储该外键关系字段对应的的主体 实体（外键关系中的父表记录）中读取从 * 体集合，会发现该从体集合并不包含
 * 已持久化的新从体。
 *
 * <p>
 * 这是由于持久化一个实体时，<code>{@link EntityManager}</code> 并不会在该实体
 * 外健关系中的主体中的对从体集合中添加该从体，而JPA开启SharedCache后，读取到 的数据将是之前缓存的数据。 解决方法有几种：
 * 1，将持久化一个新从体时，不是直接 持久化该新实体，而是将其添加主体的从休 集合后，Merge主体即可 ；
 * 2，全局禁用SharedChche，或在主体的实体声明中使用{@link Cacheable}(false) 禁用主体实体的缓存；
 * 3，则是本类所试图实现，的利用实体EventListener实现{@link PostPersist},{@link PostUpdate}
 * 方法，检测即将持久化的新实体是否有外键关系，并在对应的主体中的从体集合中添加
 * 该从体，条件是你必须在从体实体表明中使用{@link EntityListeners}指定该 EventListener；或将该类指定为default
 * listener,在persistence.xml中指定 的Mapping file中声明
 *
 *
 *
 *
 *
 * @author Administrator
 */
public class SharedCacheEntityConsistencyKeeper {

    private static SharedCacheEntityConsistencyKeeper singleton;

    private static EntityManager em;

    public SharedCacheEntityConsistencyKeeper() {
        Instance<EntityManager> instance = CDI.current().select(EntityManager.class, new Default() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return Default.class;
            }
        });
        em = instance.get();

        singleton = this;

    }

    @PostPersist
    public void postPersiste(Object entity) {
        keepConsistency(entity, Action.POST_PERSIST);
    }

    public List<Field> getFields(Object entity) throws SecurityException {
        return Arrays.asList(entity.getClass().getDeclaredFields());
    }

    @PostUpdate
    public void postUpdate(Object entity) {
        keepConsistency(entity, Action.POST_UPDATE);
    }

    /**
     * 对于操作，则必须是监听PreRemove,不能是PostRemove
     *
     * @param entity
     */
    @PostRemove
    public void postRemove(Object entity) {
        keepConsistency(entity, Action.POST_REMOVE);
    }

   synchronized protected void keepConsistency(Object entity, Action action) {
        List<Field> fields = getFields(entity);
        for (Field f : fields) {
            try {
                ManyToOne m2o = f.getAnnotation(ManyToOne.class);
                if (m2o != null) {
                    Object p = ReflectUtil.getProperty(entity, f.getName());
                    if (p != null) {
                        List<Field> opposedFields = getFields(p);
                        for (Field opf : opposedFields) {
                            OneToMany o2m = opf.getAnnotation(OneToMany.class);
                            if (o2m != null && f.getName().equals(o2m.mappedBy())) {

                                if (Class.forName("java.util.Map").isAssignableFrom(opf.getType())) {
                                    break;//如果对应字段的类型是Map,那无法进行处理。
                                }
                                Collection entitys = (Collection) ReflectUtil.getProperty(p, opf.getName());
                                if (entitys == null) {
                                    entitys = new LinkedList<>();
                                    ReflectUtil.setPropertyByName(opf.getName(), entitys, p);
                                }
//                                switch (action) {
//                                    case POST_PERSIST:
//                                    case POST_UPDATE:
//                                        if (!entitys.contains(entity)) {
//                                            entitys.add(entity);
//                                        }
//                                        em.merge(p);//最终需要
//                                        break;
//                                    case PRE_REMOVE:
//                                    case POST_REMOVE:
//                                        synchronized (entitys) {
//                                            entitys.remove(entity);//这个虽然从关系的对方的属性集合中删除了已移除的实体，但是再次查询时，对方的属性集合中仍然具有该实体，也许是缓存的作用，没有对主体调用persist
//                                        }
//                                        break;
//                                }

                            }

                        }
                    }

                    OneToOne o2o = f.getAnnotation(OneToOne.class);
                    if (o2o != null) {//@TODO

                    }
                    ManyToMany m2m = f.getAnnotation(ManyToMany.class);
                    if (m2m != null) {//@TODO

                    }
                }
            } catch (Exception ex) {
                //DoNothing,just log;
            }
        }
    }
    

    public static enum Action {

        PRE_PERSIST, POST_PERSIST, PRE_UPDATE, POST_UPDATE, PRE_REMOVE, POST_REMOVE
    }

}
