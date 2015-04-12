/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.jpa;


import javax.persistence.Cacheable;
import javax.persistence.EntityListeners;
import javax.persistence.EntityManager;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

/**
 *在JPA开启Shared Cache（二级高速缓存）的情况下，创建并持久化一个具有拥有外键
 * 关系( * {@link ManyToOne},{@link ManyToMany}, {@link OneToOne})的实体
 * （外建关系中的子表（从体）记录），当你试图在存储该外键关系字段对应的的主体
 * 实体（外键关系中的父表记录）中读取从 * 体集合，会发现该从体集合并不包含
 * 已持久化的新从体。
 * 
 * <p>这是由于持久化一个实体时，<code>{@link EntityManager}</code> 并不会在该实体
 * 外健关系中的主体中的对从体集合中添加该从体，而JPA开启SharedCache后，读取到
 * 的数据将是之前缓存的数据。
 * 解决方法有两种：
 * 1，将持久化一个新从体时，不是直接 持久化该新实体，而是将其添加主体的从休
 * 集合后，Merge主体即可 ；
 * 2，全局禁用SharedChche，或在主体的实体声明中使用{@link Cacheable}(false)
 * 禁用主体实体的缓存；
 * 3，则是本类所试图实现，的利用实体EventListener实现{@link PostPersist},{@link PostUpdate}
 * 方法，检测即将持久化的新实体是否有外键关系，并在对应的主体中的从体集合中添加
 * 该从体，条件是你必须在从体实体表明中使用{@link EntityListeners}指定该
 * EventListener；或将该类指定为default listener,在persistence.xml中指定
 * 的Mapping file中声明
 * 
 * 
 * 
 * 
 * 
 * @author Administrator
 */
public class SharedCacheEntityConsistencyKeeper {
    
}
