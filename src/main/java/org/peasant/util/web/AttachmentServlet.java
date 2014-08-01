/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.peasant.util.Attachment;
import org.peasant.util.Repository;
import org.peasant.util.Utils;

/**
 *
 * @author 谢金光
 */
//@WebServlet(name = "AttachmentServlet", urlPatterns = {"/attachment,/resource,/download"})
public class AttachmentServlet extends HttpServlet {
    
    @Inject
    private Repository repo;

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
        String m = request.getMethod();
        String mod, aid;
//        if (m.equalsIgnoreCase("GET")) {
//            mod = request.getParameter("method");
//        }
//        if (m.equalsIgnoreCase("POST")) {
//            
//        }
        mod = request.getParameter("method");
        if (mod == null|| mod.isEmpty()) {
            mod = "resource";
        }
        aid = request.getParameter("aid");
        if (aid == null || aid.isEmpty()) {
            //
            return;
        }
        Attachment a = repo.getAttachment(aid);
        if(a == null )
            return;
        String  ce = response.getCharacterEncoding();
        response.setContentType(a.getContentType());
        response.setCharacterEncoding(ce);
        if(!mod.equalsIgnoreCase("resource")){
            response.setHeader("Content-Disposition", "attachment;filename=\""+HttpUtils.encodeFilename(request, response, a.getName(), ce));
        }
        OutputStream os = response.getOutputStream();
        InputStream is = a.getInputStream();
        Utils.copy(is, os);
        is.close();
        os.close();
        String sp = request.getServletPath();
        //response.setContentType("text/html;charset=UTF-8");       

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
        return "Short description";
    }// </editor-fold>

}
