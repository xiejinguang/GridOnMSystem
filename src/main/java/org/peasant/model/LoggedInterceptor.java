/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.model;

import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.interceptor.AroundConstruct;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author Administrator
 */
@Logged
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class LoggedInterceptor {

    @AroundInvoke
    public Object logInvocation(InvocationContext ctx) throws Exception {

        String clazz = ctx.getMethod().getDeclaringClass().getName();
        String method = ctx.getMethod().getName();
        Logger.global.entering(clazz, method, ctx.getParameters());
        try {
            Object result = ctx.proceed();
            Logger.global.exiting(clazz, method, result);
            return result;
        } catch (Exception e) {
            Logger.global.throwing(clazz, method, e);
            throw e;
        }

    }

    @AroundConstruct
    public Object created(InvocationContext ctx) {
        return null;
    }

}
