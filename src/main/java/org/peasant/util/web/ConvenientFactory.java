/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web;

import java.io.IOException;
import java.io.InputStream;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author 谢金光
 */
@Named
@ApplicationScoped
public class ConvenientFactory {

    @Inject
    ServletContext svc;

    public StreamedContent getResourceAs(String path) {
        InputStream is = svc.getResourceAsStream(path);
        if (is == null) {
            this.getClass().getResourceAsStream(path);
        }
        if (is != null) {
            return new DefaultStreamedContent(is, java.net.URLConnection.getFileNameMap().getContentTypeFor(path));
        }

        return null;
    }
}
