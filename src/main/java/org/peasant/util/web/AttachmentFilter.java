/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.peasant.util.Attachment;
import org.peasant.util.Repository;
import org.peasant.util.Utils;

/**
 *
 * @author 谢金光
 */
@WebFilter(filterName = "AttachmentFilter", urlPatterns = {"/*"})
public class AttachmentFilter implements Filter {

    public static final String DEFAULT_ATTACHMENT_PATH = "/attachment";
    public static final String MOETHOD_RESOURCE = "resource";
    public static final String MOETHOD_ATTACHMENT = "attachment";
    public static final String MOETHOD_INLINE = "inline";

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    private String attachmentPath;

    @Inject
    private Repository repo;

    public AttachmentFilter() {

    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AttachmentFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
	/*
         for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
         String name = (String)en.nextElement();
         String values[] = request.getParameterValues(name);
         int n = values.length;
         StringBuffer buf = new StringBuffer();
         buf.append(name);
         buf.append("=");
         for(int i=0; i < n; i++) {
         buf.append(values[i]);
         if (i < n-1)
         buf.append(",");
         }
         log(buf.toString());
         }
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AttachmentFilter:DoAfterProcessing");
        }

	// Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
	/*
         for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
         String name = (String)en.nextElement();
         Object value = request.getAttribute(name);
         log("attribute: " + name + "=" + value.toString());

         }
         */
        // For example, a filter might append something to the response.
	/*
         PrintWriter respOut = new PrintWriter(response.getWriter());
         respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (debug) {
            log("AttachmentFilter:doFilter()");
        }

        doBeforeProcessing(request, response);

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        String pi = httpReq.getPathInfo();
        String sp = httpReq.getServletPath();
        String reqURI = httpReq.getRequestURI();
        String context = httpReq.getContextPath();
        if (sp != null && sp.contains(attachmentPath)) {

            String m = httpReq.getMethod();
            String mod, aid;
//        if (m.equalsIgnoreCase("GET")) {
//            mod = request.getParameter("method");
//        }
//        if (m.equalsIgnoreCase("POST")) {
//            
//        }
            mod = httpReq.getParameter("method");
            if (mod == null || mod.isEmpty()) {
                mod = MOETHOD_RESOURCE;
            }
            aid = httpReq.getParameter("aid");
            if (aid != null && !aid.isEmpty()) {

                Attachment a = repo.getAttachment(aid);
                if (a != null) {

                    String ce = httpResp.getCharacterEncoding();
                    httpResp.setContentType(a.getContentType());
                    httpResp.setCharacterEncoding(ce);
                    if (!mod.equalsIgnoreCase("resource")) {
                        httpResp.setHeader("Content-Disposition", "attachment;filename=\"" + HttpUtils.encodeFilename(httpReq, httpResp, a.getName(), ce));
                    }
                    OutputStream os = httpResp.getOutputStream();
                    InputStream is = a.getInputStream();
                    Utils.copy(is, os);
                    is.close();
                    
                    //response.setContentType("text/html;charset=UTF-8");      
                   
                }
            }
        }

        Throwable problem = null;
        try {
            chain.doFilter(request, response);
        } catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
            t.printStackTrace();
        }

        doAfterProcessing(request, response);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AttachmentFilter:Initializing filter");
            }
        }
        String s = filterConfig.getServletContext().getInitParameter(Constants.ATTACHMENT_PATH_PARAM);
        if (s == null || s.trim().isEmpty()) {
            this.attachmentPath = DEFAULT_ATTACHMENT_PATH;
        } else {
            this.attachmentPath = s;
        }

    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AttachmentFilter()");
        }
        StringBuffer sb = new StringBuffer("AttachmentFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

    /**
     * 获取Attachment相对于WEP应用上下文路径的URL,The path must begin with a / and is
     * interpreted as relative to the current context root
     *
     * @param request
     * @param response
     * @param attachment
     * @param method
     * @return
     */
    public static String getAttachmentURLPartPath(ServletRequest request, ServletResponse response, Attachment attachment, String method) {
        String ap = request.getServletContext().getInitParameter(Constants.ATTACHMENT_PATH_PARAM);
        if (ap == null || ap.trim().isEmpty()) {
            ap = DEFAULT_ATTACHMENT_PATH;
        }
        if (ap.endsWith("/")) {
            ap = ap.substring(0, ap.length() - 1);
        }
        StringBuffer url = new StringBuffer();
        url.append(ap);
        url.append('?');
        url.append("aid=" + attachment.getID());
        url.append("&method=" + method);
        return url.toString();
    }

}
