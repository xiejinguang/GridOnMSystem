/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.jpa;

import java.io.Serializable;
import javax.annotation.Priority;
import javax.interceptor.AroundConstruct;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.slf4j.LoggerFactory;

/**
 * 在beans.xml中必须将bean-discovery-mode设备为all,否则在Glassfish
 * 4.0中将无法发现定义的Interceptor,即使你在beans.xml用使用interceptors元素列出Interceptor。
 *
 * @author Administrator
 */
@Interceptor
@Logged
@Priority(Interceptor.Priority.APPLICATION)
public class LoggedInterceptor implements Serializable {

    org.slf4j.Logger logger = LoggerFactory.getLogger(LoggedInterceptor.class.getSimpleName());

    public LoggedInterceptor() {
    }

    @AroundInvoke
    public Object logInvocation(InvocationContext ctx) throws Exception {

        String clazz = ctx.getMethod().getDeclaringClass().getName();
        String method = ctx.getMethod().getName();

        logger.debug("Entering the Method[{}] of Class[{}] with Parameter[{}]", new Object[]{method, clazz, ctx.getParameters()});
        try {
            Object result = ctx.proceed();
            logger.debug("Exiting the Method[{}] of Class[{}] with result[{}]", new Object[]{method, clazz, result});
            return result;
        } catch (Exception e) {
            logger.debug("Exiting the Method[{}] of Class[{}] with Exception[{}]", new Object[]{method, clazz, e});
            throw e;
        }

    }

    @AroundConstruct
    public Object logConstruction(InvocationContext ctx) throws Exception {

        logger.info("Initializing a new instance of Class[{}] using constructor[{}] with parameters[{}]!",
                new Object[]{ctx.getConstructor().getDeclaringClass(), ctx.getConstructor(), ctx.getParameters()});
        Object result = ctx.proceed();
        logger.info("Successfully constructed an instance [{}] using constructor[{}] with parameters[{}]!",
                new Object[]{ctx.getTarget(), ctx.getConstructor(), ctx.getParameters()});
        return result;
    }

}
