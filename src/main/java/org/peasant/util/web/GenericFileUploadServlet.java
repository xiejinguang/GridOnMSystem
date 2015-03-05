/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Collection;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.peasant.util.Attachment;
import org.peasant.util.Repository;

/**
 *
 * @author 谢金光
 */
@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"}, initParams = {
    @WebInitParam(name = "belonger", value = "null")})
@MultipartConfig
public class GenericFileUploadServlet extends HttpServlet {

    public static final String PARAM_BELONGER = "belonger";
    public static final String PARAM_ATTACHER = "attacher";

    @EJB
    Repository repo;

    @Inject
    AttachmentUPnDownloadService attaServ;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Collection<Attachment> as = attaServ.handleUploadFileRequest(request, response, request.getParameter(PARAM_BELONGER), request.getRemoteUser());
        this.constructResponse(response, as);
    }

    protected void constructResponse(HttpServletResponse response, Collection<Attachment> as) throws IOException {
        response.setContentType("text/html");
        try(PrintWriter out = response.getWriter()){
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>文件上传</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<ul>");
        for(Attachment a : as){
            out.println("<li>filename："+a.getName()+",Size:"+a.getSize());
        }        
        out.println("</ul>");
        out.println("<br>");
        out.println("共"+as.size()+"个文件上传成功！！！");
        out.println("</body>");
        out.println("</html>");
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "FileUploadServlet";
    }// </editor-fold>

}
