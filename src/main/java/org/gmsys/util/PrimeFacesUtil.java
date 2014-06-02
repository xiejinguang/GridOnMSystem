/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gmsys.util;

import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.peasant.util.Attachment;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author 谢金光
 */
@Named(value ="primeFacesUtil")
@ApplicationScoped
public class PrimeFacesUtil {
     public StreamedContent newStreamContentForAttachment(Attachment a ) throws IOException{
        return new DefaultStreamedContent(a.getInputStream(), a.getContentType(), a.getName());
    }  
    
}
