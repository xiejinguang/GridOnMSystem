/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.web.fileupdownload;

/**
 *
 * @author 谢金光
 */
public class Constants {

    public static final String REPOSITORY_HOME_PARAM = "peasant.repository.PATH";
    public static final String ATTACHMENT_DOWN_URL_PATTERN_PARAM = "peasant.attachment.DOWNLOAD_URL_PATTERN";//请参见Servlet的URL-Pattern的格式,若URL-Pattern中出现符号‘*’则替换为attachment的唯一标识。
    public static final String DEFAULT_ATTACHMENT_DOWN_URL_PATTERN_PARAM = "*.attachment";//请参见Servlet的URL-Pattern的格式,若URL-Pattern中出现符号‘*’则替换为attachment的名称。
    public static final String MOETHOD_RESOURCE = "resource";
    public static final String MOETHOD_ATTACHMENT = "attachment";
    public static final String MOETHOD_INLINE = "inline";

}
