/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author 谢金光
 */
public class Utils {

    public static String generateUniqueKey() {
        return java.util.UUID.randomUUID().toString();
    }

    public static int copy(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[1024000];
        int size = 0;
        int s = 0;

        while ((s = is.read(buffer)) > -1) {
            size += s;
            os.write(buffer, 0, s);
        }
        os.flush();
        return size;

    }
}
