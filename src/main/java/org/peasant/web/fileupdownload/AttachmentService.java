/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.web.fileupdownload;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.peasant.model.Logged;
import org.peasant.model.Permission;
import org.peasant.util.Attachment;
import org.peasant.util.Repository;
import org.peasant.util.Utils;
import org.peasant.util.web.ContentTypes;
import org.peasant.util.web.HttpUtils;
import static org.peasant.web.fileupdownload.Constants.MOETHOD_ATTACHMENT;
import static org.peasant.web.fileupdownload.Constants.MOETHOD_INLINE;
import static org.peasant.web.fileupdownload.Constants.MOETHOD_RESOURCE;

/**
 *
 * @author 谢金光
 * @version 1.1
 */
@Logged
@Singleton
public class AttachmentService implements Serializable {

    @Inject
    Repository attachRepo;

    @Inject
    ServletContext svc;

    protected String downServPath; //在contextInitialized方法中初始化

    /**
     *
     */
    @PostConstruct
    public void init() {
        if (svc != null) {
            String path = svc.getInitParameter(Constants.ATTACHMENT_DOWN_SERVLET_PATH_PARAM);
            if (path == null || path.trim().isEmpty()) {
                path = Constants.DEFAULT_ATTACHMENT_DOWN_SERVLET_PATH;
            }
            this.downServPath = path;
        }
    }

    public String getResourcePath(String servletPath, Attachment a) {
        return getAttachmentURLPath(servletPath, a, Constants.MOETHOD_RESOURCE);
    }

    public String getDownloadPath(String servletPath, Attachment a) {
        return getAttachmentURLPath(servletPath, a, Constants.MOETHOD_ATTACHMENT);
    }

    public String getInlinePath(String servletPath, Attachment a) {
        return getAttachmentURLPath(servletPath, a, Constants.MOETHOD_INLINE);
    }

    public String getResourcePath(Attachment a) {
        return getAttachmentURLPath(a, Constants.MOETHOD_RESOURCE);
    }

    public String getDownloadPath(Attachment a) {
        return getAttachmentURLPath(a, Constants.MOETHOD_ATTACHMENT);
    }

    public String getInlinePath(Attachment a) {
        return getAttachmentURLPath(a, Constants.MOETHOD_INLINE);
    }
//
//    public void asDownload(HttpServletResponse httpResp, Attachment am) {
//
//    }
//
//    public void asInline(HttpServletResponse httpResp, Attachment am) {
//
//    }
//
//    public void asResource(HttpServletResponse httpResp, Attachment am) {
//
//    }

    public boolean handleDownloadRequest(HttpServletRequest httpReq, HttpServletResponse httpResp) throws IOException {
//        String pi = httpReq.getPathInfo();
//        String sp = httpReq.getServletPath();
//        String hm = httpReq.getMethod();
        String mod, aid;

        mod = httpReq.getParameter("method");
        if (mod == null || mod.isEmpty()) {
            mod = MOETHOD_RESOURCE;
        }
        aid = httpReq.getParameter("aid");
        if (aid != null && !aid.isEmpty()) {

            Attachment a = attachRepo.getAttachment(aid);
            if (a != null) {

                String ce = httpResp.getCharacterEncoding();
                httpResp.setContentType(a.getContentType());
                //httpResp.setCharacterEncoding(ce);
                switch (mod) {
                    case MOETHOD_RESOURCE:
                        httpResp.setContentType(a.getContentType());
                    case MOETHOD_ATTACHMENT:
                        httpResp.setContentType("application/octet-stream");
                        httpResp.setHeader("Content-Disposition", MOETHOD_ATTACHMENT
                                + ";filename=\"" + HttpUtils.encodeFilename(httpReq, httpResp, a.getName(), ce) + "\""//解决下载时文件名乱码的问题
                                + ";finename*=utf-8''" + java.net.URLEncoder.encode(a.getName(), "UTF-8"));//解决下载时文件名乱码的问题

                    case MOETHOD_INLINE:
                        httpResp.setContentType("application/octet-stream");
                        httpResp.setHeader("Content-Disposition", MOETHOD_INLINE
                                + ";filename=\"" + HttpUtils.encodeFilename(httpReq, httpResp, a.getName(), ce) + "\""//解决下载时文件名乱码的问题
                                + ";finename*=utf-8''" + java.net.URLEncoder.encode(a.getName(), "UTF-8"));//解决下载时文件名乱码的问题
                }

                try (OutputStream os = httpResp.getOutputStream(); InputStream is = a.getInputStream()) {
                    Utils.copy(is, os);
                }

                return true;
                //response.setContentType("text/html;charset=UTF-8");

            }
        }

        return false;
    }

    /**
     *
     * @param request
     * @param response
     * @param belonger the value of belonger
     * @param attacher the value of attacher
     * @return the java.util.Collection<org.peasant.util.Attachment>
     */
    public Collection<Attachment> handleUploadFileRequest(HttpServletRequest request, HttpServletResponse response, String belonger, String attacher) throws IOException, ServletException {
        Collection<Attachment> attachs = new java.util.LinkedList<>();

        // response.setContentType("application/json;charset=UTF-8");
        // try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
        Attachment a = null;
        String c = request.getContentType();
        if (c.contains(ContentTypes.MULTIPART)) {
            Collection<Part> parts = request.getParts();
            for (Part p : parts) {
                a = attachRepo.storeFromStream(p.getInputStream(), p.getName(), p.getContentType(), belonger, attacher, Calendar.getInstance().getTime());
                if (a != null) {
                    attachs.add(a);
                }
            }

        } else {
            String cd = request.getHeader("Content-Disposition");
            String filename = null;

            String[] ss = cd.split(";");
            for (String s : ss) {
                if (s.contains("filename=")) {
                    filename = s.substring(s.indexOf("filename=") + 10, s.length() - 1);
                }
            }

            if (null != filename) {
                a = attachRepo.storeFromStream(request.getInputStream(), filename, null, belonger, attacher, Calendar.getInstance().getTime());
                attachs.add(a);
            }
            //a = attachRepo.storeFromStream(request.getInputStream(), p.getName(), p.getContentType(), belonger, attacher, Calendar.getInstance().getTime());
            //TODO
        }
        return attachs;

    }

    public Attachment storeFromStream(InputStream is, String fname, String contentType, String owner, String attacher, Date uploadTime) throws IOException {

        return attachRepo.storeFromStream(is, fname, contentType, owner, attacher, uploadTime);

    }

    /**
     * 获取附件的URL路径，该路径为以servletPath开头的相对WEB应用根的相对路径，同时应设置一个用于处理@param servletPath
     * 请求的 {@link HttpServlet }以确保浏览器能够正确请问附件资源。
     *
     * @param servletPath 以"/"开始的servletPath，请参见ServletContext中关于ServletPath的定义
     * @param attachment
     * @param method
     * @return the java.lang.String a URL absolute path that represents the
     * <i>attachment</i>
     */
    public String getAttachmentURLPath(String servletPath, Attachment attachment, String method) {
        if (servletPath == null || servletPath.trim().isEmpty()) {
            servletPath = Constants.DEFAULT_ATTACHMENT_DOWN_SERVLET_PATH;
        }
        if (servletPath.endsWith("/")) {
            servletPath = servletPath.substring(0, servletPath.length() - 1);
        }

        StringBuilder url = new StringBuilder();
        url.append(servletPath);
        url.append('?');
        url.append("aid=").append(attachment.getID());
        url.append("&method=").append(method);
        return url.toString();
    }

    public String getAttachmentQueryParams(Attachment attachment, String method) {

        StringBuilder params = new StringBuilder();

        params.append("aid=").append(attachment.getID());
        params.append("&method=").append(method);
        return params.toString();
    }

    protected String getAttachmentURLPath(Attachment attachment, String method) {
        //TODO
        return getAttachmentURLPath(downServPath, attachment, method);
    }

    public Repository getRepository() {
        return this.attachRepo;
    }

}
