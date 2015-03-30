/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 谢金光
 */
public class HttpUtils {

    public final static String HTTP_REQUEST_HEADER_USER_AGENT = "User-Agent";

    public static String encodeString(HttpServletRequest req, HttpServletResponse resp, String strToEncoding, String charset) {
        String ua = req.getHeader(HTTP_REQUEST_HEADER_USER_AGENT);//当使用HTTP进行下载时，必须使用URLEncoder对文件名进行编号（若是firefox则必须使用new String(filename.getBytes("UTF-8"),"ISO-8859-1")，否则在下载保存对话框中的中文文件名将是乱码，请参见HTTP Header： Content-Disposition

        if (strToEncoding == null || strToEncoding.isEmpty()) {
            return strToEncoding;
        }

        String encodedStr = strToEncoding;
        try {

            if (ua.toLowerCase().contains("firefox")) {
                encodedStr = new String(strToEncoding.getBytes(charset), "ISO8859-1");//firefox浏览器            
            } else {
                encodedStr = java.net.URLEncoder.encode(strToEncoding, charset);//IE或Chrome浏览器
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(HttpUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return encodedStr;
        }

    }

    //当使用HTTP进行下载时，必须使用URLEncoder对文件名进行编号（若是firefox则必须使用new String(filename.getBytes("UTF-8"),"ISO-8859-1")，否则在下载保存对话框中的中文文件名将是乱码，请参见HTTP Header： Content-Disposition
    public static String encodeFilename(HttpServletRequest req, HttpServletResponse resp, String filename) {

        return encodeFilename(req, resp, filename, "UTF-8");
    }

    //当使用HTTP进行下载时，必须使用URLEncoder对文件名进行编号（若是firefox则必须使用new String(filename.getBytes("UTF-8"),"ISO-8859-1")，否则在下载保存对话框中的中文文件名将是乱码，请参见HTTP Header： Content-Disposition
    public static String encodeFilename(HttpServletRequest req, HttpServletResponse resp, String filename, String charset) {
        return encodeString(req, resp, filename, charset);
    }

    public static boolean isMultipartRequest(HttpServletRequest req) {
        if (ContentTypes.MULTIPART.equalsIgnoreCase(req.getContentType())) {
            return true;
        }
        return false;//TODO
    }

}
