/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.peasant.util;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author 谢金光
 */
public interface Storage {
    public boolean store(String name,InputStream is);
    public OutputStream get(String name,boolean n);
    public InputStream get(String name);
}
