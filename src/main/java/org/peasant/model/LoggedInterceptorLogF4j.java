/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.model;

import javax.annotation.Priority;
import javax.interceptor.Interceptor;

/**
 *
 * @author Administrator
 */
@Logged
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class LoggedInterceptorLogF4j {

}
