/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.web.fileupdownload;

import org.peasant.web.fileupdownload.GenericAttachmentUploadServlet;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import org.peasant.util.Attachment;

/**
 *
 * @author 谢金光
 */
@WebServlet(name = "XheditorUploadServlet", urlPatterns = {"/upload_xheditor"})
public class XheditorUploadServlet extends GenericAttachmentUploadServlet {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void constructSuccessResponse(HttpServletResponse response, Collection<Attachment> as) throws IOException {
        response.setContentType("application/json");
        try (javax.json.stream.JsonGenerator jg = javax.json.Json.createGenerator(response.getWriter())) {
           //jg.writeStartArray();
          //  for (Attachment a : as) {
           Attachment a = (Attachment) as.toArray()[0];
           String url = attaServ.getResourcePath(a);
           if(url.startsWith("/")){
               url=url.substring(1);
           }
                jg.writeStartObject()
                            .write("err", "")
                            .writeStartObject("msg")
                            .write("id", a.getID())
                            .write("url", url)
                            .write("localfile", a.getName())
                            .write("size", a.getSize())
                            // .write("contentType", a.getContentType())
                            .write("belonger", a.getBelonger())
                            .write("uploadtime", dateFormat.format(a.getUploadTime()))
                            .writeEnd()
                    .writeEnd();
         //   }
         //   jg.writeEnd();
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

}
