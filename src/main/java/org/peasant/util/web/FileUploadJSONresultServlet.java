/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.peasant.util.Attachment;

/**
 *
 * @author 谢金光
 */
@WebServlet(name = "FileUploadJSONresultServlet", urlPatterns = {"/ajaxupload"})
public class FileUploadJSONresultServlet extends GenericFileUploadServlet {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void constructSuccessResponse(HttpServletResponse response, Collection<Attachment> as) throws IOException {
        response.setContentType("application/json");
        try (javax.json.stream.JsonGenerator jg = javax.json.Json.createGenerator(response.getWriter())) {
            jg.writeStartArray();
            for (Attachment a : as) {
                jg.writeStartObject()
                        .write("id", a.getID())
                        .write("url", attaServ.getResourcePath(a))
                        .write("localfile", a.getName())
                        .write("size", a.getSize())
                        .write("contentType", a.getContentType())
                        .write("belonger", a.getBelonger())
                        .write("uploadtime", dateFormat.format(a.getUploadTime()))
                        .writeEnd();
            }
            jg.writeEnd();
        }
    }

    @Override
    protected void constructErrorResponse(HttpServletResponse response, Exception e) throws IOException {
        response.setContentType("application/json");
        try (javax.json.stream.JsonGenerator jg = javax.json.Json.createGenerator(response.getWriter())) {

            jg.writeStartObject()
                    .write("err", e.toString())
                    .writeEnd();
        }
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
