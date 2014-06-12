/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman.util;

/**
 *
 * @author 谢金光
 */
public class Utils {
    public static String generateUniqueKey(){
        return java.util.UUID.randomUUID().toString();
    }
}
