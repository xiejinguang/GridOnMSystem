/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.web.fileupdownload;

import java.io.IOException;
import java.util.Collection;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import org.peasant.util.Attachment;

/**
 *
 * @author 谢金光@peasant.org
 */
@WebServlet(name = "UEditorUploadServlet", urlPatterns = {"/upload_uditor"})
public class UEditorUploadServlet extends HttpServlet {

    public static final String PARAM_BELONGER = "belonger";
    public static final String PARAM_ATTACHER = "attacher";
    public static final String PARAM_ACTION = "action";

    @Inject
    protected AttachmentService attaServ;

    public UEditorUploadServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.       
    }

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
        String actionStr = request.getParameter(PARAM_ACTION);
        Action action;
        if (actionStr == null || actionStr.isEmpty()) {
            action = Action.config;
        } else {
            action = Action.valueOf(actionStr);
        }
        switch (action) {

            case uploadimage:
            case uploadscrawl:
            case uploadvideo:
            case uploadfile:
            case listimage:
            case catchimage:
            case config:
            default:

        }

        try {
            Collection<Attachment> as = attaServ.handleUploadFileRequest(request, response, request.getParameter(PARAM_BELONGER), request.getRemoteUser());
            Entity.json(this);

//            {
//                "state"
//            
//         : "SUCCESS",
//    "url": "upload/demo.jpg",
//    "title": "demo.jpg",
//    "original": "demo.jpg"
//}
        } catch (IOException | ServletException e) {

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
        return "UEditorUploadServlet";
    }// </editor-fold>
    //TODO

    public static enum Action {

        config, uploadimage, uploadscrawl, uploadvideo, uploadfile, listimage, catchimage;
        String toString;

    }

    public static class Result {

        String state;
        String url;
        String title;
        String original;
    }
}
