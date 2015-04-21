/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.web.fileupdownload;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 在应用启动时，执行Attachement服务的环境配置，从web.xml中读取peasant.attachment.DOWNLOAD_SERVLET_PATH参数配置，或使用默认值
 * 在CDI环境中，该类可不使用
 *
 * @deprecated since {@link  AttachmentService }1.1
 * @author 谢金光
 */


public class AttachmentEnvironmentListener implements ServletContextListener {

    @Inject
    AttachmentService attachServ;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String path = sce.getServletContext().getInitParameter(Constants.ATTACHMENT_DOWN_URL_PATTERN_PARAM);
        if (path == null || path.trim().isEmpty()) {
            path = Constants.DEFAULT_ATTACHMENT_DOWN_URL_PATTERN_PARAM;
        }
        attachServ.downServPath = path;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //Do nothing
    }

}
