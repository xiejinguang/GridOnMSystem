/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author 谢金光
 */
public class CharacterEncodingFilter implements Filter {

    public final static String CHARACTER_ENCODING_PARAM = "encoding";
    String encoding = "UTF-8";
    FilterConfig fc;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.fc = filterConfig;
        String ec = fc.getInitParameter(CHARACTER_ENCODING_PARAM);
        if (ec != null && !(ec.isEmpty())) {
            this.encoding = ec;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String oldReqEncodeing = request.getCharacterEncoding();
        request.setCharacterEncoding(this.encoding);
        String oldReqEnString2 = request.getCharacterEncoding();
        String oldRespEncodeing = response.getCharacterEncoding();
        response.setCharacterEncoding(this.encoding);
        String oldRespEncodeing2 = response.getCharacterEncoding();
        chain.doFilter(request, response);
        String oldRespEncodeing3 = response.getCharacterEncoding();
    }

    @Override
    public void destroy() {
        this.encoding = null;
        this.fc = null;
    }

}
