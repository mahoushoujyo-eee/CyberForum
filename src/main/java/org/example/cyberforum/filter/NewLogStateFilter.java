package org.example.cyberforum.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import stark.dataworks.boot.web.ServiceResponse;

import java.io.IOException;


public class NewLogStateFilter implements Filter
{
    private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(NewLogStateFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies)
            if (cookie.getName().equals("username"))
            {
                logger.info("There is cookie");
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

        logger.info("There is no cookie");
        filterChain.doFilter(servletRequest, servletResponse);
//        response.getWriter().write('0');
//        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
