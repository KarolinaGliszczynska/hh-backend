package com.codecool.helpinghands.configuration;

import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FilterChainConfig implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletResponse != null){
            HttpServletResponse response = (HttpServletResponse)servletResponse;
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String requestOrigin = request.getHeader("Origin");
            response.setHeader("Access-Control-Allow-Origin", requestOrigin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
            //response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "*");
            filterChain.doFilter(request, response);
        }
    }

}