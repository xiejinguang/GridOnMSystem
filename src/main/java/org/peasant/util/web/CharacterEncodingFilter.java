/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web;

import java.io.IOException;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 谢金光
 */
public class CharacterEncodingFilter implements Filter {

    public final static String PARAM_ENCODING = "encoding";
    String encoding = "UTF-8";
    FilterConfig fc;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.fc = filterConfig;
        String ec = fc.getInitParameter(PARAM_ENCODING);
        if (ec != null && !(ec.isEmpty())) {
            this.encoding = ec;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(this.encoding);
        response.setCharacterEncoding(this.encoding);
        request = new MyHttpServletRequestWrapper((HttpServletRequest) request);
        response = new MyHttpServletResponseWrapper((HttpServletResponse) response);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        this.encoding = null;
        this.fc = null;
    }

}
